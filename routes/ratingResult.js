// Connect to MySQL
var express = require('express');
var router = express.Router();

var mysql = require('mysql');
var connection = mysql.createConnection({
  host     : 'olympics.caxfxzsga7pz.us-east-1.rds.amazonaws.com',
  user     : 'group11',
  password : 'cis550project',
  database : 'Olympics',
  multipleStatements: true
});

/////
// Query the MySQL database, and call output_actors on the results
//
// res = HTTP result object sent back to the client
// year = Year to query for
// rate = new rating from user
function query_db_mascot(res,year,rate) {
	query = "SELECT * FROM Mascot WHERE Mascot.Year = '"+year+"'";
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var x = rows[0];
			console.log(x.Rating);
			console.log(x.numOfUser);
			var newAvg = (x.Rating * x.numOfUser); 
			console.log(newAvg);
			newAvg = newAvg + parseInt(rate);
			console.log(newAvg);
			newAvg = Math.round(newAvg / (x.numOfUser + 1));
			console.log(newAvg);
			console.log(x.numOfUser);
			var num = x.numOfUser+1;
			update_db(res, year,newAvg, num);
		}
	});
}

function update_db(res, year, newAvg,numOfUser) {
	query2 = "UPDATE Mascot SET Rating = '" + newAvg + "' WHERE Year = '" + year +"';";
	query2 = query2 + "UPDATE Mascot SET numOfUser = '" + numOfUser + "' WHERE Year = '" + year +"';";
	connection.query(query2, function(err) {
		if (err) console.log(err);
		else {
			result_db_mascot(res);
        }
     });
}

function result_db_mascot(res){
	query3 = "SELECT * FROM Mascot ORDER BY Rating DESC";
	connection.query(query3, function(err, rows) {
		if (err) console.log(err);
		else {
			output_mascot(res, rows, "Mascot");
		}			
	});
}


function query_db_theme(res,year,rate) {
	query = "SELECT * FROM Theme WHERE year = '"+year+"'";
	//if (HostYear) query = query + " WHERE HostYear='" + HostYear + "'";
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var x = rows[0];
			var newAvg = (x.rating * x.numOfUser); 
			newAvg = newAvg + parseInt(rate);
			newAvg = Math.round(newAvg / (x.numOfUser + 1));
			var num = x.numOfUser+1;
			update_db_theme(res, year,newAvg, num);
		}
	});
}

function update_db_theme(res, year, newAvg,numOfUser) {
	query2 = "UPDATE Theme SET rating = '" + newAvg + "' WHERE year = '" + year +"';";
	query2 = query2 + "UPDATE Theme SET numOfUser = '" + numOfUser + "' WHERE year = '" + year +"';";
	connection.query(query2, function(err) {
		if (err) console.log(err);
		else {
			result_db_theme(res);
        }
     });
}

function result_db_theme(res){
	query3 = "SELECT * FROM Theme ORDER BY rating DESC";
	connection.query(query3, function(err, rows) {
		if (err) console.log(err);
		else {
			output_mascot(res, rows, "Theme Song");
		}			
	});
}


function query_db_stadium(res,year,rate,venue) {
	query = "SELECT * FROM Stadiums WHERE Year = '"+year+"' AND Venue = '"+venue+"'";
	//if (HostYear) query = query + " WHERE HostYear='" + HostYear + "'";
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var x = rows[0];
			var newAvg = (x.Rating * x.numOfUser); 
			newAvg = newAvg + parseInt(rate);
			newAvg = Math.round(newAvg / (x.numOfUser + 1));
			var num = x.numOfUser+1;
			update_db_stadium(res, year,newAvg, num, venue);
		}
	});
}

function update_db_stadium(res, year, newAvg,numOfUser, venue) {
	query2 = "UPDATE Stadiums SET Rating = '" + newAvg + "' WHERE Year = '" + year +"' AND Venue = '"+venue+"';";
	query2 = query2 + "UPDATE Stadiums SET numOfUser = '" + numOfUser + "' WHERE Year = '" + year +"' AND Venue = '"+venue+"'";
	console.log(query2);
	connection.query(query2, function(err) {
		if (err) console.log(err);
		else {
			result_db_stadium(res);
        }
     });
}

function result_db_stadium(res){
	query3 = "SELECT * FROM Stadiums ORDER BY Rating DESC";
	connection.query(query3, function(err, rows) {
		if (err) console.log(err);
		else {
			output_mascot(res, rows, "Stadium");
		}			
	});
}


// ///
// Given a set of query results, output a table
//
// res = HTTP result object sent back to the client
// name = Name to query for
// results = List object of query results
function output_mascot(res, x, title) {
	res.render('ratingResult.jade',
		   {result1: x, title: title}
	  );
}

function isLoggedIn(req, res, next) {

    // if user is authenticated in the session, carry on 
    if (req.isAuthenticated())
        return next();

    // if they aren't redirect them to the home page
    res.redirect('/login');
}

router.get('/', isLoggedIn, function(req, res, next) {
  var str = req.query.name;
  var venue = str.substring(5, str.length);
  var year = str.substring(0,4);
  str = req.query.rate;
  var rate = str.substring(str.length-1, str.length);
  var item = str.substring(0, str.length-1);
  if(item == "Mascot"){
  	query_db_mascot(res, year, rate);
  }else if(item == "Stadium"){
  	query_db_stadium(res, year, rate, venue);
  }else{
  	query_db_theme(res, year, rate);
  }
});
/////
// This is what's called by the main app 

module.exports = router;


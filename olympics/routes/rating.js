// Connect string to MySQL
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
// The following three tables do queries on table Mascot, Theme and Stadiums separately
// Query the MySQL database, and call output_actors on the results
//
// res = HTTP result object sent back to the client
function query_db_mascot(res) {
	query = "SELECT * FROM Mascot;"
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			output_mascot(res, rows, "Mascot");
		}
	});
}

function query_db_theme(res) {
	query = "SELECT * FROM Theme;"
	//if (HostYear) query = query + " WHERE HostYear='" + HostYear + "'";
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			output_mascot(res, rows, "Theme Song");
		}
	});
}

function query_db_stadium(res) {
	query = "SELECT * FROM Stadiums;"
	connection.query(query, function(err, rows) {
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
// x = List object of query results
function output_mascot(res,x,str) {
	res.render('rating.jade',
		   {result1: x, title: str}
	  );
}


router.get('/', function(req, res, next) {
	var str = req.query.name;
	if(str == "Mascot"){
  		query_db_mascot(res);
	}else if (str == "Stadium"){
		query_db_stadium(res);
	}else {
        query_db_theme(res);
	}
});

/////
// This is what's called by the main app 

module.exports = router;


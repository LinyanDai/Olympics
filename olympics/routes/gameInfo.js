var express = require('express');
var router = express.Router();

// Connect string to MySQL
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
function query_db(res, year) {
	query = "SELECT * FROM Mascot WHERE Mascot.Year = '"+year+"';";
	query = query + "SELECT * FROM Host WHERE Host.year = '"+year+"';";
	query = query + "SELECT * FROM Stadiums WHERE Stadiums.Year = '"+year+"';";
	query = query + "SELECT * FROM CompeteIn WHERE CompeteIn.year = '"+year+"';";
	query = query + "SELECT * FROM Theme WHERE Theme.year = '"+year+"'";
	console.log(query);
	connection.query(query, function(err, rows, fields) {
		if (err) console.log(err);
		else {
			output_gameInfo(res, year, rows[0], rows[1], rows[2], rows[3],rows[4]);
		}
	});
}
// ///
// Given a set of query results, pass to gameInfo.jade
//
// res = HTTP result object sent back to the client
// year = year to query for
// x, y,  z, m, n = List object of query results
function output_gameInfo(res,year,x,y,z,m,n) {
	res.render('gameInfo.jade',
		   { result1: x, result2: y, result3: z, result4: m, result5: n});
}


router.get('/', function(req, res, next) {
  var str = req.query.name;
	query_db(res,str.substring(0, 4));
});

module.exports = router;

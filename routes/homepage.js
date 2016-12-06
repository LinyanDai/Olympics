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
// Query the oracle database, and call output_actors on the results
//
// res = HTTP result object sent back to the client
// name = Name to query for
function query_db_mascot(res) {
	query = "SELECT * FROM Mascot; SELECT * FROM Host";
	//if (HostYear) query = query + " WHERE HostYear='" + HostYear + "'";
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var x = rows[0];
			var y = rows[1];
			output_mascot(res, x, y);
		}
	});
}



// ///
// Given a set of query results, output a table
//
// res = HTTP result object sent back to the client
// name = Name to query for
// results = List object of query results
function output_mascot(res,x,y) {
	res.render('index.jade',
		   {result1: x, result2: y}
	  );
}


router.get('/', function(req, res, next) {
  query_db_mascot(res);
});
/////
// This is what's called by the main app 

module.exports = router;


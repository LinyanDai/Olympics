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

var monk = require('monk');
var db = monk('mongodb://readOnlyUser:cis550project@ds119608.mlab.com:19608/olympicsdb');

/////
// Query the mysql database, and call output_mascot on the results
//
// res = HTTP result object sent back to the client
function query_db_mascot(res) {
	query = "SELECT * FROM Mascot; SELECT * FROM Host";
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var x = rows[0];
			var y = rows[1];

    		var collection = db.get('Stories');
    		var ran = Math.floor((Math.random() * 10) + 1);
    		collection.find({"storyid": ran}, function(err, Stories){
        		if (err) throw err;
        		else{
              output_mascot(res, x, y, Stories[0]);
        		}
    		});

		}
	});
}

// ///
// Given a set of query results, output a table
//
// res = HTTP result object sent back to the client
// x, y, z  = List object of query results
function output_mascot(res,x,y,z) {
	res.render('index.jade',
		   {result1: x, result2: y, stories:z}
	  );
}


router.get('/', function(req, res, next) {
  query_db_mascot(res);
});

module.exports = router;


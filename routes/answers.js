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
// the following 9 functions do the queries for the 9 questions shown on Q & A page
// Query the MySQL database, and call output_actors on the results
//
// res = HTTP result object sent back to the client
// name = Name to query for
function query_db1(res) {
	query = "select Host.country, Host.year, Host.city " + "from Host inner join Theme on Host.year = Theme.year"+
	" where Host.year in( select min(Host.year) "+"from Host inner join Theme on Host.year = Theme.year);"
	console.log(query);
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var xyzz = rows[0].year + "  " +rows[0].country + "  " + rows[0].city;
			output_db(res, xyzz);
		}
	});
}

function query_db2(res) {
	query = "select t.country, count(t.medal) as numOfMedal "+
			"from(select year, country, medal "+
     		"from CompeteIn "+
     		"where country not in(select country from Host)"+
			") as t "+
			"group by t.country "+
			"order by numOfMedal desc;"
	console.log(query);
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var sbb = rows[0].country + " and " +rows[1].country;
			output_db(res, sbb);
		}
	});
}

function query_db3(res) {
	query = "SELECT a.country, sum(a.gold)"+
	" from (select c.country, c.gold "+
	 "from Theme t inner join Host h on t.year = h.year "+
                   "inner join CompeteIn c on c.country = h.country "+
") as a "+
"group by a.country "+
"having sum(a.gold) > 200;"
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var ss = rows[0].country +", " + rows[1].country + " and " + rows[2].country; 
			output_db(res, ss);
		}
	});
}

function query_db4(res) {
	query = "select Host.country, Host.year, Host.city " + "from Host inner join Mascot on Host.year = Mascot.year"+
	" where Host.year in( select min(Host.year) "+"from Host inner join Mascot on Host.year = Mascot.year);"
	console.log(query);
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var xyzz = rows[0].year + "  " +rows[0].country + "  " + rows[0].city;
			output_db(res, xyzz);
		}
	});
}

function query_db5(res) {
	output_db(res, "2008 Beijing China, five Fuwas");
}

function query_db6(res) {
	query = "select count(distinct Host.country) as c from Host;";
	console.log(query);
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var xyzz = rows[0].c;
			output_db(res, xyzz);
		}
	});
}

function query_db7(res) {
	query = "select Host.country, count(*) as c from Host where Host.year <> 1916 and Host.year <> 1940 and Host.year <> 1944 group by Host.country order by c DESC;"
	console.log(query);
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var xyzz = rows[0].country + "  " + rows[0].c + " times";
			output_db(res, xyzz);
		}
	});
}

function query_db8(res) {
	query = "select * from Host order by Host.year DESC;"
	console.log(query);
	connection.query(query, function(err, rows) {
		if (err) console.log(err);
		else {
			var xyzz = rows[0].year + "  " +rows[0].country + "  " + rows[0].city;
			output_db(res, xyzz);
		}
	});
}

function query_db9(res) {
	output_db(res, "2020 Tokyo Japan");
}

// ///
// Given a set of query results, output a table
//
// res = HTTP result object sent back to the client
// x = List object of query results
function output_db(res,x) {
	res.render('question.jade',
		   {result: x}
	  );
}

// decides which function to call
router.get('/', function(req, res, next) {
	var str = req.query.name;
	var star = str.substring(0,1);
	if(star == "1"){
  		query_db1(res);
	}else if (star == "2"){
		query_db2(res);
	}else if (star == "3"){
        query_db3(res);
	}else if (star == "4"){
		query_db4(res);
	}else if (star == "5"){
		query_db5(res);
	}else if (star == "6"){
		query_db6(res);
	}else if (star == "7"){
		query_db7(res);
	}else if (star == "8"){
		query_db8(res);
	}else if (star == "9"){
		query_db9(res);
	}
});

module.exports = router;
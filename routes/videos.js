var express = require('express');
var router = express.Router();

var monk = require('monk');
var db = monk('mongodb://group11:cis550project@ds119608.mlab.com:19608/olympicsdb');

router.get('/', function(req, res) {
    var collection = db.get('Stories');
    collection.find({}, function(err, Stories){
        if (err) throw err;
      	res.json(Stories);
    });
});


module.exports = router;
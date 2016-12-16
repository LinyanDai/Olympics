var express = require('express');
var router = express.Router();

/* GET Q & A page */
router.get('/', function(req, res, next) {
  res.render('question.jade');
});

module.exports = router;

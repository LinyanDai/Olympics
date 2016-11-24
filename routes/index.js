
/*
 * GET home page, which is specified in Jade.
 */

exports.do_ref = function(req, res){
  res.render('reference.jade', {});
};
exports.do_log = function(req, res){
  res.render('login.jade', {});
};

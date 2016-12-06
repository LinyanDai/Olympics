var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var port     = process.env.PORT || 8080;
var mongoose = require('mongoose');
var passport = require('passport');
var flash    = require('connect-flash');

var index = require('./routes/index');
var users = require('./routes/users');
var videos = require('./routes/videos');
var homepage = require('./routes/homepage');
var gameInfo = require('./routes/gameInfo');

var app = express();

var configDB = require('./config/database.js');
mongoose.connect(configDB.url); 
var session      = require('express-session');

require('./config/passport')(passport); // pass passport for configuration

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
//app.use(morgan('dev')); // log every request to the console

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// required for passport
    app.use(session({ secret: 'ilovescotchscotchyscotchscotch' })); // session secret
    app.use(passport.initialize());
    app.use(passport.session()); // persistent login sessions
    app.use(flash()); // use connect-flash for flash messages stored in session

// routes ======================================================================
  //require('./app/routes.js')(app, passport); // load our routes and pass in our app and fully configured passport


app.use('/', homepage);
app.use('/gameInfo', gameInfo);
app.use('/api/videos', videos);
app.get('/login', function(req, res) {
        //console.log('The magic happens again and again on port ' + port);
        res.render('login.jade'); // load the index.ejs file
    });
app.get('/localLogin', function(req, res) {

        // render the page and pass in any flash data if it exists
        res.render('localLogin.jade',{message: req.flash('loginMessage')}); 
    });

app.get('/localSignup', function(req, res) {

        // render the page and pass in any flash data if it exists
        res.render('localSignup.jade',{message: req.flash('signupMessage')});
    });
app.post('/localSignup', passport.authenticate('local-signup', {
        successRedirect : '/profile', // redirect to the secure profile section
        failureRedirect : '/localSignup', // redirect back to the signup page if there is an error
        failureFlash : true // allow flash messages
    }));
app.get('/profile', isLoggedIn, function(req, res) {
        res.render('profile.jade', {
            user : req.user // get the user out of session and pass to template
        });
    });

// process the login form
app.post('/localLogin', passport.authenticate('local-login', {
        successRedirect : '/profile', // redirect to the secure profile section
        failureRedirect : '/localLogin', // redirect back to the signup page if there is an error
        failureFlash : true // allow flash messages
    }));

function isLoggedIn(req, res, next) {

    // if user is authenticated in the session, carry on 
    if (req.isAuthenticated())
        return next();

    // if they aren't redirect them to the home page
    res.redirect('/login');
}

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});


// launch ======================================================================
app.listen(port);
console.log('The magic happens on port ' + port);

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;

// define variables
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

var rating = require('./routes/rating');
var ratingResult = require('./routes/ratingResult');
var homepage = require('./routes/homepage');
var gameInfo = require('./routes/gameInfo');
var question = require('./routes/question');
var answers = require('./routes/answers');

var app = express();

var configDB = require('./config/database.js');
mongoose.connect(configDB.url);         // connect to mongodb database
var session = require('express-session');

require('./config/passport')(passport); // pass passport for configuration

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));     // log every request to the console

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// required for passport
app.use(session({ secret: 'ilovecis550project' })); // session secret
app.use(passport.initialize());
app.use(passport.session()); // persistent login sessions
app.use(flash()); // use connect-flash for flash messages stored in session

app.use('/question',question);
app.use('/answers',answers);
app.use('/', homepage);
app.use('/gameInfo', gameInfo);

app.use('/rating',rating);
app.use('/ratingresult',ratingResult);
app.get('/login', function(req, res) {
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

    // =====================================
    // FACEBOOK ROUTES =====================
    // =====================================
    // route for facebook authentication and login
app.get('/auth/facebook', passport.authenticate('facebook', { scope : 'email' }));

    // handle the callback after facebook has authenticated the user
app.get('/auth/facebook/callback',
        passport.authenticate('facebook', {
            successRedirect : '/profile',
            failureRedirect : '/'
        }));

    // =====================================
    // LOGOUT ==============================
    // =====================================
app.get('/logout', function(req, res) {
        req.logout();
        res.redirect('/');
    });

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

require("dotenv").config()
require("./config/database").connect();
const createError = require('http-errors');
const express = require('express');
const http = require('http')
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');
const debug = require('debug')('qytap-backend:server');

const authRouter = require("./routes/auth")
const indexRouter = require('./routes/index');
const booksRouter = require('./routes/books')

// Setting port
const port = process.env.PORT || '8000'

const app = express();

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/auth', authRouter)
app.use('/', indexRouter);
app.use('/books', booksRouter)

app.use((req, res, next) => {
  next(createError(404));
});

app.use((err, req, res, next) => {
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  res.status(err.status || 500);
  res.render('error');
});

const server = http.createServer(app)
server.listen(port)

module.exports = app;

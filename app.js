'use strict';
const express = require('express');
const app = express();
const userRoutes = require('./src/routes/users');
const categoryRoutes = require('./src/routes/categories');
const productRoutes = require('./src/routes/products');
const demandRoutes = require('./src/routes/demands');
const morgan = require('morgan');
const bodyParser = require('body-parser');

// Request Logs Store in Morgan 
app.use(morgan('dev'));

// Url Parser to Json
app.use(
    bodyParser.urlencoded({
        extended: false,
    }));
app.use(bodyParser.json());

// Routes
app.use('/users', userRoutes);
app.use('/categories', categoryRoutes);
app.use('/products', productRoutes);
app.use('/demands', demandRoutes);


// Error Handling
app.use((req, res, next) => {
    const error = new Error('Endpoint Not Found');
    error .status = 404;
    next(error);
});

app.use((error, req, res, next) => {
    res.status(error.status || 500);
    res.json({
        error: {
            message: error.message
        }
    });
});

module.exports = app;
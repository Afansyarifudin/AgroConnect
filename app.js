const express = require('express');
const app = express();
const userRoutes = require('./src/routes/users');
const morgan = require('morgan');
const bodyParser = require('body-parser');

// app.use('/', (req, res, next) => {
//     res.status(200).json({
//         message:"AgroConnect API Success"
//     });
// });

// Request Logs
app.use(morgan('dev'));

// Url Parser to JSOn
app.use(
    bodyParser.urlencoded({
        extended: false,
    }));
app.use(bodyParser.json());

// Routes
app.use('/users', userRoutes);

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
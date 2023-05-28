'use strict';
require('dotenv').config();
const http = require('http');

const app = require('./app');
const port = process.env.APP_PORT || 3001;

const server = http.createServer(app);
server.listen(port, ()=> {
    console.log(`Server Started at ${port}`)
});


'use strict';
const express = require('express');
const http = require('http');

const app = require('./app');
const port = process.env.PORT || 3000;

const server = http.createServer(app);
server.listen(port, ()=> {
    console.log(`Server Started at ${port}`)
});


// app.use(express.json());
// app.use(
//     express.urlencoded({
//         extended: true,
//     })
// );

// app.get("/", (req, res) => {
//     res.json({ message: "Success" });
// });

// app.listen(3000, () => {
//     console.log(`Server Started at ${port}`)
// })


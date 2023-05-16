const express = require('express');

const app = express();
const port = 3000;

app.use(express.json());
app.use(
    express.urlencoded({
        extended: true,
    })
);

app.get("/", (req, res) => {
    res.json({ message: "Success" });
});

app.listen(3000, () => {
    console.log(`Server Started at ${port}`)
})
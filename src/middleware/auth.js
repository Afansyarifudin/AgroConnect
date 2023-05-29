const jwt = require('jsonwebtoken');

const auth = async (req, res, next) => {
    const token = req.body.token || req.query.token || req.headers["x-access-token"];

    if (!token) {
        return res.status(403).send("You need to autenticate to access this");
    }
    try {
        const decoded = jwt.verify(token, "verySecret");
        req.user = decoded;
        // req.email = decoded;
    } catch (err) {
        return res.status(401).send("Invalid Token");
    }
    return next();
};

module.exports = auth;
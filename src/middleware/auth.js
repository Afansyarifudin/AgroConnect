const jwt = require('jsonwebtoken');

const auth = async (req, res, next) => {
    try {
        const token = req.header("x-auth-token");
        if (!token) {
            return res.status(401).json({
                message: "Required authentication, access denied!"
            });
        }
        const verified = jwt.verify(token, "verySecret");
        if (!verified) {
            return res.status(401).json({
                message: "Token authentication failed, authorization denied"
            });
        }
        req.user = verified.id;
        req.token = token;
        next();
    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
};

module.exports = auth;
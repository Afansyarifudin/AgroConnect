const router = require('express').Router();
const controller = require('../controllers/users_controller')
const auth = require('../middleware/auth');

router.get('/', auth, (req, res, next) => {
    res.status(200).json({
        message: "Method Get User"
    });
});

router.post('/register', controller.userRegister);
router.post('/login', controller.userLogin);


module.exports = router;
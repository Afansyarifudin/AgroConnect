const router = require('express').Router();
const controller = require('../controllers/categories_controller')

router.get('/', controller.findAllCategories);      //get all categories
router.get('/:id', );                               //get category by id 

module.exports = router;
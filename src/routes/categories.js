const router = require('express').Router();
const controller = require('../controllers/categories_controller');
const auth = require('../middleware/auth');

router.get('/', controller.findAllCategories);      //get all categories
router.get('/:categoryId', controller.getCategoryById);         //get category by id 
router.post('/', controller.createCategory);        //create category
router.put('/:categoryId', controller.updateCategory);      //update category
router.delete('/:categoryId', controller.deleteCategory);   //delete category           

module.exports = router;
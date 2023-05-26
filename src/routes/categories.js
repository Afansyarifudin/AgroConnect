const router = require('express').Router();
const controller = require('../controllers/categories_controller')

router.get('/', controller.findAllCategories);      //get all categories
router.get('/:categoryId', controller.getBookById);         //get category by id 
router.post('/', controller.createCategory);        //create category
router.put('/:categoryId', controller.updateCategory);      //update category
router.delete('/:categoryId', controller.deleteCategory);   //delete category           

module.exports = router;
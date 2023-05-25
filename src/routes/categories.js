const router = require('express').Router();
const controller = require('../controllers/categories_controller')

router.get('/', controller.findAllCategories);      //get all categories
router.get('/:id', controller.getBookById);         //get category by id 
router.post('/', controller.createCategory);        //create category
router.put('/:id', controller.updateCategory);      //update category
router.delete('/:id', controller.deleteCategory);   //delete category           

module.exports = router;
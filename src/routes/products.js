const router = require('express').Router();
const controller = require('../controllers/products_controller');

router.get('/', controller.getAllProduct);
router.get('/:productId', controller.getProductById);
router.post('/', controller.createProduct);
router.put('/:productId', controller.updateProduct);
router.delete('/:productId', controller.deleteProduct);

module.exports = router;
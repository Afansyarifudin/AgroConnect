const router = require('express').Router();
const controller = require('../controllers/products_controller');
const auth = require('../middleware/auth');

router.get('/', controller.getAllProduct);
router.get('/search', controller.getProductByName);
router.get('/:productId', controller.getProductById);
router.post('/', auth, controller.createProduct);
router.put('/:productId', auth, controller.updateProduct);
router.delete('/:productId', auth, controller.deleteProduct);

module.exports = router;
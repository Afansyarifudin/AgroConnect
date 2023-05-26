const router = require('express').Router();
const controller = require('../controllers/demands_controller');

router.get('/', controller.getAllDemand);
router.get('/:demandId', controller.getDemandById);
router.post('/', controller.createDemand);
router.put('/:demandId', controller.updateDemand);
router.delete('/:demandId', controller.deleteDemand);

module.exports = router;
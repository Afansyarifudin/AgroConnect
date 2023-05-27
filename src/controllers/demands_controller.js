const {Demand} = require('../models');
const controller = {};

controller.getAllDemand = async (req, res) => {
    try {
        const data = await Demand.findAll({
            include: 'Category'
        });

        res.status(200).json({
            message: "Success Get All Demands",
            data: data
        })
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

controller.getDemandById = async (req, res) => {

}

controller.createDemand = async (req, res) => {
    try {
        // Check if Content-Type is set to JSON
        if (req.headers['content-type'] !== 'application/json') {
            return res.status(400).json({ 
                error: 'Invalid content type. Only JSON is supported.' 
            });
        }

        const {name, amount, location, category_id} = req.body;

        const newDemand = await Demand.create({
            name: name,
            amount: amount,
            location: location,
            category_id: category_id
        })

        res.status(201).json({
            message: "New Demand Created",
            data: newDemand
        })

    } catch (error) {
        
    }
}

controller.updateDemand = async (req, res) => {

}

controller.deleteDemand = async (req,res) => {
    
}


module.exports = controller;
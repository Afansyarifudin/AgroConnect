const {Category} = require('../models');
const controller = {};

controller.findAllCategories = async (req, res) => {
    try {
        const data = await Category.findAll();

        res.status(200).json({
            data: data
        })
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

controller.getBookById = async (req, res) => {

}

controller.createCategory = async (req, res) => {

}

controller.updateCategory = async (req, res) => {

}

controller.deleteCategory = async function(req, res) {

}

module.exports = controller;


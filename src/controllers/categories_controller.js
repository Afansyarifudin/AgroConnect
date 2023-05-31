const {Category} = require('../models');
const controller = {};

controller.findAllCategories = async (req, res) => {
    try {
        const data = await Category.findAll();

        res.status(200).json({
            message: "Success Get All Categories",
            data: data
        })
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

controller.getBookById = async (req, res) => {
    try {
        const data = await Category.findOne({
            where: {
                id: req.params.id
            }
        });

        res.status(200).json({
            message: "Success Get Category By Id",
            data: data
        })
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

controller.createCategory = async (req, res) => {
    try {
        const data = await Category.create(req.body);

        res.status(200).json({
            message: "Success Create Category",
            data: data
        })
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

//update the category
controller.updateCategory = async (req, res) => {
    try {
        const data = await Category.update(req.body, {
            where: {
                id: req.params.id
            }
        });

        res.status(200).json({
            message: "Success Update Category",
            data: data
        })
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

controller.deleteCategory = async function(req, res) {
    try {
        //jika id category ditemukan maka hapus data
        const {categoryId} = req.params;

        const rowsAffected = await Category.destroy({
            where: {
                id: categoryId
            }
        });

        if (rowsAffected > 0) {
            return res.status(200).json({
                status: "Ok",
                message: "Success Delete Category"
            });
        } else {
            return res.status(404).json({
                status: "Not Found",
                message: "Product Not Found"
            });
        }
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

module.exports = controller;
const {Category} = require('../models');
const controller = {};
const { Op } = require('sequelize');

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

controller.getCategoryById = async (req, res) => {
    try {
        const {categoryId} = req.params;
        
        const data = await Category.findOne({
            where: {
                id: categoryId
            }
        });

        if (data === null){
            return res.status(404).json({
                status: "Not Found",
                message: `Data with id ${categoryId} Not Found`
            });
        }
        
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

controller.getCategoriesbyName = async (req, res) => {
    try {
        const categoryName = req.query.name;

        const categories = await Category.findAll({
            where: {
                name: {
                    [Op.iLike]: `%${categoryName}%`
                }
            }
        });

        if (categories.length === 0) {
            return res.status(404).json({
                status: "Not Found",
                message: "Category Not Found"
            });
        }

        return res.status(200).json({
            status: "Ok",
            message: "Success Get Categories",
            categories
        });

    } catch (error) {
        res.status(500).json({
            message: error
        });
    }
}

//update the category
controller.updateCategory = async (req, res) => {
    try {
        // Check if Content-Type is set to JSON
        if (req.headers['content-type'] !== 'application/json') {
            return res.status(400).json({ 
                error: 'Invalid content type. Only JSON is supported.' 
            });
        }

        const {categoryId} = req.params;
        const {name} = req.body;

        const oldCategory = await Category.update({
            name: name
        }, {
            where: {
                id: categoryId
            }
        });

        if (oldCategory[0] === 1) {
            res.status(200).json({
                status: "Ok",
                message: "Success Update Category"
            })
        } else {
            res.status(404).json({
                status: "Not Found",
                message: "Category Not Found"
            })
        }
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
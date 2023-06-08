const {Product} = require('../models');
const controller = {};
const { Op } = require('sequelize');

controller.getAllProduct = async (req, res) => {
    try {
        const data = await Product.findAll({
            include: ['Category', 'User']
        });

        res.status(200).json({
            status: "Ok",
            message: "Success Get All Product",
            data: data
        });
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

// get product by name

controller.getProductByName = async (req, res) => {
    try {
        const productName = req.query.name;

        var condition = productName ? { name: { [Op.like]: `%${productName}%` } } : null;

        const data = await Product.findAll({
            where: condition,
            include: ['Category', 'User']
        });

        if (data.length === 0) {
            return res.status(404).json({
                status: "Not Found",
                message: "Product Not Found"
            });
        }

        return res.status(200).json({
            status: "Ok",
            message: "Success Get Data",
            data
        });

    } catch (error) {
        res.status(500).json({
            message: error
        });
    }
}

controller.getProductById = async (req, res) => {
    try {
        const {productId} = req.params;

        const data = await Product.findByPk(productId, {include: ['Category', 'User']});

        if (data === null) {
            return res.status(404).json({
                status: "Not Found",
                message: `Data with id ${productId} Not Found`
            })
        }

        res.status(200).json({
            status: "Ok",
            message: "Success Get a Product",
            data: data
        });

    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

controller.createProduct = async (req, res) => {
    try {
        // Check if Content-Type is set to JSON
        if (req.headers['content-type'] !== 'application/json') {
            return res.status(400).json({ 
                error: 'Invalid content type. Only JSON is supported.' 
            });
        }

        console.log(req.user_id);

        const {name, amount, location, crop_date, estimate_exp, category_id} = req.body;
        const newProduct = await Product.create({
            name: name,
            amount: amount,
            location: location,
            crop_date: crop_date,
            estimate_exp: estimate_exp,
            category_id:category_id,
            user_id: req.user.user_id
        });

        return res.status(201).json({
            status: "Created",
            message: "New Product Created",
            data: newProduct
        })
    } catch (error) {
        res.status(404).json({
            message: error
        })
    }
}

controller.updateProduct = async (req, res) => {
    try {
        // Check if Content-Type is set to JSON
        if (req.headers['content-type'] !== 'application/json') {
            return res.status(400).json({ 
                error: 'Invalid content type. Only JSON is supported.' 
            });
        }

        console.log(req.user.user_id);

        const {productId} = req.params;
        const {name, amount, location, crop_date, estimate_exp, category_id} = req.body;
        const {userId} = req.user.user_id;

        const oldProduct = await Product.update({
            name: name,
            amount: amount,
            location: location,
            crop_date: crop_date,
            estimate_exp: estimate_exp,
            category_id: category_id
        }, 
        {
            where: {
                id: productId
            }
        });

        if (oldProduct[0] === 1) {
            return res.status(200).json({
                status: "Ok",
                message: "Success Update Product"
            })
        } else {
            return res.status(404).json({
                status: "Not Found",
                message: "Product Not Found"
            });
        }

    } catch (error) {
        res.status(404).json({
            message: error
        });
    }
}

controller.deleteProduct = async (req,res) => {
    try {
        const {productId} = req.params;

        const rowsAffected = await Product.destroy({
            where: {
                id: productId
            }
        });

        if (rowsAffected > 0) {
            return res.status(200).json({
                status: "Ok",
                message: "Success Delete Product"
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
        });
    }
}

module.exports = controller;
'use strict';
const {User} = require('../models')
const bcryptjs = require('bcrypt');
const jwt = require("jsonwebtoken");
const controller = {};

// Register controller
controller.userRegister = async (req,res) => {
    try {
        const {username, email, password, role} = req.body;

        if (!(username && email && password)) {
            res.status(400).json({message:"username, email, and password are required"});
        }

        // validate user 
        const exixtingUser = await User.findOne({ where: { email } });
        if (exixtingUser) {
            return res.status(409).json({
                message: "User already exist"
            });
        }

        const hashedPassword = await bcryptjs.hash(password, 8);

        let user = new User({
            email: email.toLowerCase(),
            password: hashedPassword,
            username,
            role,
        });

        user = await user.save();
        res.status(200).json(user);

    } catch (error) {
        res.status(500).json({
            message: error.message
        });
    }

}

controller.userLogin = async (req,res) => {
    try {
        const {email, password} = req.body;

        const user = await User.findOne({email});
        if (!user) {
            return res.status(400).json({
                message: "User doesn't exist. Please register first!"
            });
        }

        const isMatch = await bcryptjs.compare(password, user.password);
        if (!isMatch) {
            return res.status(400).json({
                message: "Incorrect pasword"
            });
        }

        const token = jwt.sign({
            user_id: user._id, 
            email 
        }, "verySecret", {
            expiresIn: "2h",
        });
        res.status(200).json({
            token
            // ...user._doc
        });

    } catch (error) {
        res.status(500).json({
            message: error.message
        });
    }
}

module.exports = controller;
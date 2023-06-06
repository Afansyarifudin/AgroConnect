'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Product extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Product.belongsTo(models.Category, {foreignKey:'category_id'});
      Product.belongsTo(models.User, {foreignKey:'user_id'});
    }
  }
  Product.init({
    name: DataTypes.STRING,
    amount: DataTypes.INTEGER,
    location: DataTypes.STRING,
    crop_date: DataTypes.STRING,
    estimate_exp: DataTypes.STRING,
    category_id: DataTypes.INTEGER,
    user_id: DataTypes.INTEGER
  }, {
    sequelize,
    modelName: 'Product',
  });
  return Product;
};
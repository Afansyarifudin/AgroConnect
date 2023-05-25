'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Demand extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Demand.belongsTo(models.Category, {foreignKey:'category_id'})
    }
  }
  Demand.init({
    name: DataTypes.STRING,
    amount: DataTypes.INTEGER,
    location: DataTypes.STRING,
    category_id: DataTypes.INTEGER
  }, {
    sequelize,
    modelName: 'Demand',
  });
  return Demand;
};
'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    return queryInterface.bulkInsert('Categories', [
      {
      name: "Susu",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Sayur",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Serealia",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Daging",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Ikan",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Telur",
      createdAt: new Date(),
      updatedAt: new Date()
      }
  ]);
  },

  async down (queryInterface, Sequelize) {
    return queryInterface.bulkDelete('Categories', null, {});
  }
};

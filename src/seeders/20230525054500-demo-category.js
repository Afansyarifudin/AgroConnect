'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    return queryInterface.bulkInsert('Categories', [
      {
      name: "Sayur",
      avatar: "https://akcdn.detik.net.id/visual/2021/06/08/ilustrasi-sayur_169.jpeg?w=650",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Buah",
      avatar: "https://blue.kumparan.com/image/upload/fl_progressive,fl_lossy,c_fill,q_auto:best,w_640/v1511061488/bgnpcpctorsnspupszlr.jpg",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Rempah-Rempah",
      avatar: "https://img.kurio.network/OPMaFNXYrkG_zmldtH49YbHB6Fc=/1200x900/filters:quality(80)/https://kurio-img.kurioapps.com/21/01/26/b4cfc046-eeb4-4acc-9268-919b99a03b2a.jpe",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Daging, Susu, Ikan",
      avatar: "https://cdn2.tstatic.net/tribunkaltimtravel/foto/bank/images/daging-susu-telur-ikan.jpg",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Padi, Jagung, Sagu",
      avatar: "https://4.bp.blogspot.com/-TnJWd18fFM8/WJsf0PZvAEI/AAAAAAAAAcs/12vq-VL0E0YVHgx6SXIYhF0rpP49GkzIACLcB/s400/tanaman-pangan.jpg",
      createdAt: new Date(),
      updatedAt: new Date()
      },
      {
      name: "Umbi",
      avatar: "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/08/26032716/X-Kandungan-Nutrisi-Umbi-umbian-yang-Baik-untuk-Kesehatan.jpg",
      createdAt: new Date(),
      updatedAt: new Date()
      }
  ]);
  },

  async down (queryInterface, Sequelize) {
    return queryInterface.bulkDelete('Categories', null, {});
  }
};

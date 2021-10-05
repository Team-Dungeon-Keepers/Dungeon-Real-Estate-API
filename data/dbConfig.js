const dbEngine = process.env.DB_ENVIRONMENT || 'production';
const config = require('../knexfile')[dbEngine];

module.exports = require("knex")(config);
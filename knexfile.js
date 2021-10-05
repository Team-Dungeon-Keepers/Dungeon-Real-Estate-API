const { DATABASE_URL } = require('./config/secrets')

module.exports = {
    production: {
        client: 'pg',
        connection: DATABASE_URL,
        pool: {
            min: 2,
            max: 10
        },
        migrations: {
            tablename: 'knex_migrations',
            directory: './migrations'
        }
    }
}
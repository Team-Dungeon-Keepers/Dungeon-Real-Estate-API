require("dotenv").config();
const pg = require('pg');
const server = require('./api/server');

if (process.env.DATABASE_URL) {
    pg.defaults.ssl = {
        rejectUnauthorized: false
    }
}

const port = process.env.PORT || 8080;

const sayHello = () => {
    console.log(`server is listening on port: ${port}`);
}

server.listen(port, sayHello);

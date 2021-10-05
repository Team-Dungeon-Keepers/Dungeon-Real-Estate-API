const express = require('express');
const cors = require('cors');
const helmet = require('helmet');

const authRouter = require('./auth/auth-routes');
const reimbRouter = require('./reimbursements/reimb-router');
const userRouter = require('./users/users-router');

const { logger } = require('./logger/logger');
const { verifyToken } = require('./auth/auth-middleware');

const server = express();
server.use(helmet() );
server.use(express.json() );
server.use(cors() );

server.use(logger);

server.use("/api/auth", authRouter);
server.use("/api/reimb", [verifyToken], reimbRouter);
server.use("/api/users", [verifyToken], userRouter);

server.get("/", (req,res) => {
    res.status(201).json({message: "Yip, yip, Appa!"});
})

// server.use('*', (req, res) => {
//     res.status(404).json({
//         message: "That endpoint is not set up yet."
//     })
// })

server.use((err, req, res, next) => {
    const status = err.status || 500;
    res.status(status).json({
        message: "Unknown server error",
        err: err.message
    })

    if(1 === 0) next();
})

module.exports = server;
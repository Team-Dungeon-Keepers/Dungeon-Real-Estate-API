const express = require('express');
const bcrypt = require('bcryptjs');

const users = require("../users/user-model");
const router = express.Router()

const { generateToken } = require('./auth-token');
const { verifyToken } = require('./auth-middleware');
const { verifyUserPayload } = require('../users/users-middleware');

router.post('/login', (req, res, next) => {
    const { username, password } = req.body;

    if (!username || !password) {
        res.status(400).json({ message: "username and password both required" })
    } else {
        users.findUserByUsername(username)
            .then((user) => {
                if (!user) {
                    res.status(400).json({ message: "No such username exists." });
                } else { 
                    if (bcrypt.compareSync(password, user.password)) {
                        const token = generateToken(user);

                        res.status(200).json({ message: `Welcome, ${user.username}`, token, user })
                    } else {
                        res.status(500).json({ message: "Username and password do not match." })
                    }
                }
            }).catch(next);
    }

})

router.post('/register', [verifyUserPayload], (req, res, next) => {
    const neoUser = req.body;

    const hash = bcrypt.hashSync(neoUser.password, 12);
    neoUser.password = hash;

    users.registerUser(neoUser)
        .then((resp) => {
            res.status(201).json(resp);
        }).catch(next);
})

router.post("/testToken", [verifyToken], (req, res, next) => {
    res.status(200).json({ message: "Proper token detected." });
})

module.exports = router;
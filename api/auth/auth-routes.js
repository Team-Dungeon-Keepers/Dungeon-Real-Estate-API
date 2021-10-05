const express = require('express');
const bcrypt = require('bcryptjs');

const users = require("../users/user-model");
const router = express.Router()

const { generateToken } = require('./auth-token');
const { verifyToken } = require('./auth-middleware');
const { verifyUserPayload } = require('../users/users-middleware');

router.post('/login', (req, res, next) => {
    const { ERS_USERNAME, ERS_PASSWORD } = req.body;

    if (!ERS_USERNAME || !ERS_PASSWORD) {
        res.status(400).json({ message: "username and password both required" })
    } else {
        users.findUserByUsername(ERS_USERNAME)
            .then((user) => {
                if (!user) {
                    res.status(400).json({ message: "No such username exists." });
                } else { 
                    if (bcrypt.compareSync(ERS_PASSWORD, user.ERS_PASSWORD)) {
                        const token = generateToken(user);

                        res.status(200).json({ message: `Welcome, ${user.ERS_USERNAME}`, token, user })
                    } else {
                        res.status(500).json({ message: "Username and password do not match." })
                    }
                }
            }).catch(next);
    }

})

router.post('/register', [verifyUserPayload], (req, res, next) => {
    const neoUser = req.body;

    const hash = bcrypt.hashSync(neoUser.ERS_PASSWORD, 12);
    neoUser.ERS_PASSWORD = hash;

    users.registerUser(neoUser)
        .then((resp) => {
            res.status(201).json(resp);
        }).catch(next);
})

router.post("/testToken", [verifyToken], (req, res, next) => {
    res.status(200).json({ message: "Proper token detected." });
})

module.exports = router;
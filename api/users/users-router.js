const express = require('express');

const users = require("./user-model");
const router = express.Router()

router.get('/', (req, res, next) => {
    users.findUsers()
    .then(resp => {
        res.status(200).json(resp);
    }).catch(next);
})

router.get('/:userID',
    (req, res, next) => {
        let {userID} = req.params;

        users.findUserByID(userID)
        .then(resp => {
            res.status(200).json(resp);
        }).catch(next);
    }
)

router.put('/:USER_ID',
    (req, res, next) => {
        let {USER_ID} = req.params;
        let neoUser = req.body;
        neoUser.userID = USER_ID;

        users.updateUser(neoUser)
            .then(resp => {
                res.status(201).json(resp)
            }).catch(next);
})

module.exports = router;
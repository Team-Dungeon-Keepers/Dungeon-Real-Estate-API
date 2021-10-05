const express = require('express');

const persona = require("./persona-model");
const router = express.Router()

router.get('/', (req, res, next) => {
    let { id } = req.decoded;
    persona.findPersonaByOwnerID(id)
    .then(resp => {
        res.status(200).json(resp);
    }).catch(next);
})

module.exports = router;
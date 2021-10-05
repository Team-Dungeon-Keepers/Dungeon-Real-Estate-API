const express = require('express');

const reimb = require("./reimb-model");
const router = express.Router()

router.get('/', (req, res, next) => {
    reimb.findReimb()
    .then(resp => {
        res.status(200).json(resp);
    }).catch(next);
})

router.get('/user/:USER_ID', (req, res, next) => {
    const { USER_ID } = req.params;
    
    reimb.findReimbByUserID(USER_ID)
    .then(resp => {
        res.status(200).json(resp);
    }).catch(next);
})

router.get('/status/:reimb_status', (req, res, next) => {
    const { reimb_status } = req.params;
    
    reimb.findReimbByStatus(reimb_status)
    .then(resp => {
        res.status(200).json(resp);
    }).catch(next);
})

router.post("/", (req, res, next) => {
    const ownerID = req.decoded.id;
    let neoReimb = req.body;

    neoReimb.REIMB_AUTHOR = ownerID;
    neoReimb.REIMB_SUBMITTED = Date.now();
 
    reimb.createReimb(neoReimb)
        .then(() => {
            res.status(201).json(neoReimb);
        }).catch(next);
})

router.put("/:REIMB_ID", 
    //[checkPlantID, secureByOwnerID], 
    (req, res, next) => {
        const { REIMB_ID } = req.params;
        let neoReimb = req.body;
        neoReimb.REIMB_ID = REIMB_ID;
        
        reimb.updateReimb(neoReimb)
            .then((resp) => {
                res.status(201).json(resp);
            }).catch(next);
})

router.delete("/:REIMB_ID", 
    //[checkPlantID, secureByOwnerID], 
    (req, res, next) => {
        const { REIMB_ID } = req.params;

    reimb.removeReimb(REIMB_ID)
        .then(() => {
            res.status(200).json({
                message: `Reimbursement ID ${REIMB_ID} has been deleted.` 
            });
        }).catch(next);
})

module.exports = router;
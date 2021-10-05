const db = require('../../data/dbConfig');

// add
async function createReimb(neoReimb) {
    neoReimb.REIMB_ID = Date.now();

    return await db('ERS_REIMBURSEMENT').insert(neoReimb)
}

// findAll
async function findReimb() {
    return await db('ERS_REIMBURSEMENT');
}

async function findReimbByID(key) {
    key = parseInt(key);

    return await db('ERS_REIMBURSEMENT')
        .where({REIMB_ID: key})
}

// findByID
async function findReimbByUserID(key) { 
    key = parseInt(key);
    return await db('ERS_REIMBURSEMENT')
        .where({REIMB_AUTHOR: key})
}

// findByUsername
async function findReimbByStatus(key) {
    key = parseInt(key); 
    return await db("ERS_REIMBURSEMENT")
        .where({REIMB_STATUS_ID: key})
}

const updateReimb = async (neoReimb) => {
    await db("ERS_REIMBURSEMENT")
        .where({ REIMB_ID: neoReimb.REIMB_ID})
        .update(neoReimb);
    return await findReimbByID(neoReimb.REIMB_ID);
}

const removeReimb = async (REIMB_ID) => {
    return await db("ERS_REIMBURSEMENT")
        .where({REIMB_ID})
        .del();
}

module.exports = {
    findReimb,
    findReimbByUserID,
    findReimbByStatus,
    createReimb,
    removeReimb,
    updateReimb
}

const db = require('../../data/dbConfig');

// add
async function registerUser(neoUser) {
    neoUser.ERS_USER_ID = Date.now();

    return await db('ERS_USERS').insert(neoUser, ['ERS_USER_ID','ERS_USERNAME', 'USER_ROLE_ID'])
}

// findAll
async function findUsers() {
    return await db('ERS_USERS');
}

// findByID
async function findUserByID(key) { 
    key = parseInt(key);
    return await db('ERS_USERS')
        .where({ERS_USER_ID: key})
        .first();
}

// findByUsername
async function findUserByUsername(key) { 
    return await db("ERS_USERS")
        .where({ERS_USERNAME: key})
        .first();
}

const updateUser = async (neoUser) => {
    await db("ERS_USERS")
        .where({ ERS_USER_ID: neoUser.ERS_USER_ID})
        .update(neoUser);
    return await findUserByID(neoUser.ERS_USER_ID);
}

module.exports = {
    findUsers,
    findUserByID,
    findUserByUsername,
    registerUser,
    updateUser
}

const db = require('../../data/dbConfig');

// add
async function registerUser(neoUser) {
    neoUser.userID = Date.now();

    return await db('users')
        .insert(neoUser, ['userID','username'])
}

// findAll
async function findUsers() {
    return await db('users');
}

// findByID
async function findUserByID(key) { 
    key = parseInt(key);
    return await db('users')
        .where({userID: key})
        .first();
}

// findByUsername
async function findUserByUsername(key) { 
    return await db("users")
        .where({username: key})
        .first();
}

const updateUser = async (neoUser) => {
    await db("users")
        .where({ userID: neoUser.userID})
        .update(neoUser);
    return await findUserByID(neoUser.userID);
}

module.exports = {
    findUsers,
    findUserByID,
    findUserByUsername,
    registerUser,
    updateUser
}

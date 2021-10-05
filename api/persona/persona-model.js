const db = require('../../data/dbConfig');

module.exports = {
    findPersona,
    findPersonaByID,
    findPersonaByOwnerID,
    createPersona,
    removePersona,
    updatePersona
}

// add
async function createPersona(neoPersona) {
    neoPersona.personaID = Date.now();

    return await db('persona')
        .insert(neoPersona, ['personaID','ownerID', 'personaName'])
}

// findAll
async function findPersona() {
    return await db("persona");
}

// findByID
async function findPersonaByID(key) { 
    key = parseInt(key);
    return await db("persona")
        .where({personaID: key})
        .first();
}

// findByUsername
async function findPersonaByOwnerID(key) { 
    return await db("persona")
        .where({ownerID: key})
}

//removePersona
async function removePersona(key) {
    key = parseInt(key);
    return await db("persona")
        .where({personaID: key})
        .del();
}

//updatePersona
async function updatePersona(neoPersona) {
    let { personaID } = neoPersona;
    return await db("persona")
        .where(personaID)
        .update(neoPersona)
}
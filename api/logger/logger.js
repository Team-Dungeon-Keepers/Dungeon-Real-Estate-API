const db = require('../../data/dbConfig');

const { getUserID } = require('../auth/auth-middleware')

function logger(req, res, next) {
    //request method, request url, and a timestamp
    if (req) {
        const method = req.method || GET;
        const url = req.url || "/";
        const time = Date.now();

        let neoID = getUserID(req, res, next);

        const neoLogMessage = {
            id: Date.now(),
            who: neoID,
            when: time,
            what: method,
            where: url
        }

        return db('logs')
        .insert(neoLogMessage)
        .then(() => {
            next();
        }).catch(next);
    } 
    next();
}
  
module.exports = {
    logger
}  
const jwt = require('jsonwebtoken');
const { TOKEN_SECRET } = require('../../config/secrets');

function getUserID(req, res) {
    let neoID =0 
    if (req) {
        if (req.headers && req.headers.authorization) {
            const token = req.headers.authorization;
            const secret = TOKEN_SECRET;
        
            if (token) {
                jwt.verify(token, secret, (err, decoded) => {
                    if (err) {
                        res.status(401).json({ message: "auth token corrupted or expired"})
                    } else {
                        neoID = decoded.id;
                    }
                })
            } 
        } 
    }
    return neoID;
}

function verifyToken(req, res, next) {
    const token = req.headers.authorization;
    const secret = TOKEN_SECRET;

    if (token) {
        jwt.verify(token, secret, (err, decoded) => {
            if (err) {
                res.status(401).json({ message: "auth token corrupted or expired"})
            } else {
                req.decoded = decoded;
            }
        })
    } else { 
        res.status(401).json({ message: "improper or expired auth token"})
    }
    next();
}

module.exports = {
    getUserID,
    verifyToken
}
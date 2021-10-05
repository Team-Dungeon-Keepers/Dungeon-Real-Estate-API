const { phone } = require('phone');

const validPhone = (req, res, next) => {
    const { phoneNumber } = req.body;
    const response = phone(phoneNumber);

    if (response.isValid) {
        next();
    } else {
        res.status(400).json({
            message: "Improper phone number format",
            phoneNumber: phoneNumber
        })
    }
}

const verifyUserPayload = (req, res, next) => {
    const neoUser = req.body;

    if (!neoUser.ERS_USERNAME || typeof(neoUser.ERS_USERNAME) !== "string"
        || !neoUser.ERS_PASSWORD || typeof(neoUser.ERS_PASSWORD) !== "string"
       ) {
        res.status(400).json({ 
            message: "username and password required" 
        });
    } else {
        next();
    }
}

module.exports = {
    verifyUserPayload,
    validPhone
}

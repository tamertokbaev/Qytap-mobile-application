const express = require('express')
const router = express.Router()

router.get('', (req, res, next) => {
    res.json([
        {
            name: "Tamerlan",
            lastname: "Tokbayev",
        }
    ])
})

module.exports = router

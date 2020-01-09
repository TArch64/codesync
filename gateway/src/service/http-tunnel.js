const ngrok = require('ngrok');
const { PORT } = require('../config');
const { tryAsyncAction } = require('../utils');

function openHttpTunnel() {
    return tryAsyncAction(10, async () => {
        return {
            url: await ngrok.connect({ addr: PORT })
        };
    });
}

module.exports = { openHttpTunnel };

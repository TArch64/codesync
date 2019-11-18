const localtunnel = require('localtunnel');
const { PORT } = require('../config');
const { tryAsyncAction } = require('../utils');

async function openHttpTunnel() {
    return tryAsyncAction(10, () => {
        return localtunnel({ port: PORT }).then(tunnel => ({ url: tunnel.url }));
    });
}

module.exports = { openHttpTunnel };

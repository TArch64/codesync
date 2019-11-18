const localtunnel = require('localtunnel');
const { GATEWAY_PORT} = require('../../service-config');

async function openHttpTunnel() {
    const tunnel = await localtunnel({ port: GATEWAY_PORT });

    return { url: tunnel.url };
}

module.exports = { openHttpTunnel };

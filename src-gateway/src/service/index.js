const { upRoomsDispatcher } = require('./rooms-dispatcher');
const { openHttpTunnel } = require('./http-tunnel');

async function run() {
    return upRoomsDispatcher().then(openHttpTunnel).then(displayServicePublicUrl);
}

function displayServicePublicUrl({ url }) {
    console.log('Hey! Copy & Paste follow URL to connect window inside your IDE:');
    console.log(url);
}

module.exports = { run };

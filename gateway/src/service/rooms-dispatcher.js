const upSoket = require('socket.io');
const { createServer } = require('http');
const { GATEWAY_PORT, DISPATCHER } = require('../../service-config');
const { showDebugMessage } = require('../utils');

async function upRoomsDispatcher() {
    const io = upSoket({ serveClient: false });
    const server = createServer();

    io.on('connection', handleClientConnection);
    io.attach(server);

    return new Promise(resolve => server.listen(GATEWAY_PORT, resolve));
}

function handleClientConnection(client) {
    showDebugMessage(`Connected: ${client.id}`);

    client.on(DISPATCHER.INPUT_EVENT_NAME, event => {
        showDebugMessage(event);
        client.broadcast.emit(DISPATCHER.OUTPUT_EVENT_NAME, event);
    });

    client.once('disconnect', () => showDebugMessage(`Disconnected: ${client.id}`));
}

module.exports = { upRoomsDispatcher };

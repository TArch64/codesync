module.exports = { upRoomsDispatcher };

const upSoket = require('socket.io');
const { createServer } = require('http');
const { GATEWAY_PORT, DISPATCHER } = require('./config');

async function upRoomsDispatcher() {
    const io = upSoket({ serveClient: false });
    const server = createServer();

    io.on('connection', handleClientConnection);
    io.attach(server);

    return new Promise(resolve => server.listen(GATEWAY_PORT, resolve));
}

function handleClientConnection(client) {
    client.on(DISPATCHER.INPUT_EVENT_NAME, event => {
        client.broadcast.emit(DISPATCHER.OUTPUT_EVENT_NAME, event);
    });
}

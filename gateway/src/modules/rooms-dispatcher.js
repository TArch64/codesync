const { BaseModule } = require('./base-module');
const useSocket = require('socket.io');
const { createServer } = require('http');

class RoomsDispatcher extends BaseModule {
    async up() {
        const io = useSocket({
            serveClient: false
        });
        const server = createServer();

        io.on('connection', this._handleSocketConnection.bind(this));
        io.attach(server);

        return new Promise(resolve => server.listen(this._config.port, resolve));
    }

    _handleSocketConnection(socket) {
        this._environment.printDebugMessage(`Connected: ${socket.id}`);

        socket.on(this._config.dispatcher.events.input, this._onInputEvent(socket));
        socket.once('disconnect', this._onDisconnected(socket));
    }

    _onInputEvent(socket) {
        return event => {
            this._environment.printDebugMessage(event);
            socket.broadcast.emit(this._config.dispatcher.events.output, event);
        }
    }

    _onDisconnected(socket) {
        return () => this._environment.printDebugMessage(`Disconnected: ${socket.id}`);
    }
}

module.exports = { RoomsDispatcher };

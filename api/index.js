const io = require('socket.io')(3030);

io.on('connection', socket => {
    function broadcastEvents(from, to) {
        socket.on(from, value => socket.broadcast.emit(to, value));
    }

    broadcastEvents('SEND_DOCUMENT_CHANGES', 'DOCUMENT_CHANGED');
});
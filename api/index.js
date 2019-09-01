const io = require('socket.io')(3030);

io.on('connection', socket => {
    socket.on('emit', value => socket.broadcast.emit('test', value));
});
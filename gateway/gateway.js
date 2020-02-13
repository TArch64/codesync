const { ServiceRoot } = require('./src');

// new ServiceRoot().run().catch(error => {
//     console.error(error);
//     process.exit(1);
// });

require('http').createServer((req, res) => {
    res.write('Hello World!');
    res.end();
}).listen(2000);
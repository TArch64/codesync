const { ServiceRoot } = require('./src');

// new ServiceRoot().run().catch(error => {
//     console.error(error);
//     process.exit(1);
// });

const port = process.env.PORT || 2000;

require('http').createServer((req, res) => {
    res.write('Hello World!');
    res.end();
}).listen(port, () => console.log('Gateway successfully started at ' + port));
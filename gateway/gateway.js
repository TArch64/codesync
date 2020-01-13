const { ServiceRoot } = require('./src');

new ServiceRoot().run().catch(error => {
    console.error(error);
    process.exit(1);
});

const config = require('../service-config');
const { fetchCommandLineArg } = require('./utils');

module.exports = {
    ...config,
    PORT: fetchCommandLineArg('port') || config.DEFAULT_PORT
};

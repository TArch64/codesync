const { BaseModule } = require('./base-module');
const ngrok = require('ngrok');

class HttpTunnel extends BaseModule {
    /**
     * @param asyncActionRetrier { typeof AsyncActionRetrier }
     */
    constructor({ asyncActionRetrier }) {
        super(...arguments);
        this._asyncActionRetrier = asyncActionRetrier;
    }

   async open() {
        const connectParams = this._formatConnectParams();

        return this._asyncActionRetrier.do(10, async (tryIndex) => {
            if ( tryIndex > 1 ) this._environment.printMessage('Trying to connect...');
            return { url: await ngrok.connect(connectParams) };
        });
    }

    _formatConnectParams() {
        const { port, authtoken } = this._config;

        const params = { addr: port };
        if ( authtoken ) params.authtoken = authtoken;
        return params;
    }
}

module.exports = { HttpTunnel };

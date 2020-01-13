const { Config, Environment, AsyncActionRetrier} = require('./shared');
const { RoomsDispatcher, HttpTunnel } = require('./modules');

class ServiceRoot {
    constructor() {
        this._config = new Config();
        this._asyncActionRetrier = AsyncActionRetrier;

        this._environment = new Environment({ config: this._config });
        this._environment.printDebugMessage('Service started in debug mode');

        this._roomsDispatcher = new RoomsDispatcher({
            config: this._config,
            environment: this._environment
        });

        this._httpTunnel = new HttpTunnel({
            config: this._config,
            environment: this._environment,
            asyncActionRetrier: this._asyncActionRetrier
        });
    }

    async run() {
        await this._roomsDispatcher.up();
        const { url } = await this._httpTunnel.open();
        this._displayServicePublicUrl(url);
    }

    _displayServicePublicUrl(url) {
        this._environment.printMessage('Hey! Copy & Paste follow URL to connect window inside your IDE:');
        this._environment.printMessage(url);
    }
}

module.exports = { ServiceRoot };

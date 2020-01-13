class Environment {
    /**
     * @param config { Config }
     */
    constructor({ config }) {
        this._config = config;
    }

    printDebugMessage(message) {
        this.runDebugAction(() => this.printMessage(message))
    }

    runDebugAction(action) {
        if ( this._config.isDebugMode ) action();
    }

    printMessage(...args) {
        console.log.apply(console, args);
    }
}

module.exports = { Environment };

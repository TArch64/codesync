class BaseModule {
    /**
     * @param config { Config }
     * @param environment { Environment }
     */
    constructor({ config, environment}) {
        this._config = config;
        this._environment = environment;
    }
}

module.exports = { BaseModule };

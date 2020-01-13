class Config {
    constructor() {
        this.defaultPort = 9574;
        this.port = this._fetchCommandLineArg('port', { defaultValue: this.defaultPort });
        this.authtoken = this._fetchCommandLineArg('auth');
        this.isDebugMode = this._fetchCommandLineArg('debug', { isBoolean: true });
        this.dispatcher = {
            events: {
                input: 'document::sendChanges',
                output: 'document::update'
            }
        };
    }

    _fetchCommandLineArg(name, { defaultValue, isBoolean } = {}) {
        const argMask = new RegExp(`^--${name}(=.+)?$`);
        const arg = process.argv.find(arg => argMask.test(arg));

        if ( isBoolean ) return !!arg;
        if ( !arg ) return defaultValue;

        return arg.split('=')[1] || defaultValue;
    }
}

module.exports = { Config };

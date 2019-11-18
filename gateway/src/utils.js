function isDevelopment() {
    return process.env.NODE_ENV === 'development';
}

function showDebugMessage(message) {
    if ( isDevelopment() ) console.log(message);
}

function fetchCommandLineArg(name) {
    const argMask = new RegExp(`^--${name}=.+$`);
    const arg = process.argv.find(arg => argMask.test(arg));

    if ( !arg ) return;

    return arg.split('=')[1];
}

function tryAsyncAction(triesLimit, action) {
    const asyncAction = async () => action();

    const tryCall = tries => asyncAction().catch(error => {
        if (tries < triesLimit) return tryCall(tries + 1);
        throw error;
    });

    return tryCall(1);
}

module.exports = {
    showDebugMessage,
    fetchCommandLineArg,
    tryAsyncAction
};
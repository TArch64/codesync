function isDevelopment() {
    return process.env.NODE_ENV === 'development';
}

function showDebugMessage(message) {
    if ( isDevelopment() ) console.log(message);
}

module.exports = { showDebugMessage };
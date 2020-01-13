class AsyncActionRetrier {
    static do(triesLimit, action) {
        return new AsyncActionRetrier({ triesLimit, action }).tryDo();
    }

    constructor({ triesLimit, action }) {
        this.tries = 0;
        this.triesLimit = triesLimit;
        this.action = this._makeAsyncAction(action);
    }

    _makeAsyncAction(action) {
        return async () => action(this.tries);
    }

    tryDo() {
        this.tries++;
        return this.action().catch(error => {
            if ( this.tries < this.triesLimit ) return this.tryDo();
            throw error;
        });
    }
}

module.exports = { AsyncActionRetrier };
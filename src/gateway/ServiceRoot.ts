import { Config } from './config';
import { ApiRoot } from './api';

export class ServiceRoot {
    private readonly config: Config;
    private readonly api: ApiRoot;

    constructor() {
        this.config = new Config();
        this.api = new ApiRoot(this.config);
    }

    async up(): Promise<void> {
        await this.api.up();
        console.log(`Service successfully started at ${this.config.appUrl}`);
    }
}
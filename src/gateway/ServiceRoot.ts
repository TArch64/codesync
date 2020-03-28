import { Config } from './config';
import { ApiRoot } from './api';
import { Logger } from "./Logger";

export class ServiceRoot {
    private readonly config: Config;
    private readonly logger: Logger;
    private readonly api: ApiRoot;

    constructor() {
        this.config = new Config();
        this.logger = new Logger();
        this.api = new ApiRoot(this.config, this.logger);
    }

    async up(): Promise<void> {
        await this.api.up();
        console.log(`Service successfully started at ${this.config.server.appUrl}`);
    }
}
export class ServerConfig {
    public defaultPort = 2000;
    public port: number = Number(process.env.PORT) || this.defaultPort;

    public defaultAppUrl = `http://localhost:${this.port}`;
    public appUrl: string = process.env.APP_URL || this.defaultAppUrl;

    get isSecureConnection(): boolean {
        return /^https:\/\//i.test(this.appUrl);
    }
}
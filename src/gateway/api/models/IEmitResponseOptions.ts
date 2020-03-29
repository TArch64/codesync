export interface IEmitResponseOptions {
    eventName: string;
    payload?: any;
    inCurrentRoom?: boolean;
    broadcast?: boolean;
    useCurrentNamespace?: boolean;
}
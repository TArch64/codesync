export interface IEmitResponseOptions {
    eventName: string;
    payload?: any;
    room?: string;
    broadcast?: boolean;
    useCurrentNamespace?: boolean;
}
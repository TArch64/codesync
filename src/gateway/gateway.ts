import { ServiceRoot } from './ServiceRoot';

new ServiceRoot().up().catch(error => {
    console.error(error);
    process.exit(1);
});
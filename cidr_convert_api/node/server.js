import restify from 'restify';
import * as routes from './routes';

let app = restify.createServer();


routes.init(app);

app.use(restify.plugins.queryParser());

export default app;

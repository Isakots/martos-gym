const fs = require('fs');
const JSON_SERVER_PORT = 3001;
const jsonServer = require('json-server');
const server = jsonServer.create();
const router = jsonServer.router('mock-server/mock-db.json');
const middleware = jsonServer.defaults();
const rewriteRules = JSON.parse(fs.readFileSync('mock-server/mock-db.json', 'UTF-8'));
server.use(middleware);
server.use(jsonServer.rewriter(rewriteRules));
server.use(jsonServer.bodyParser);

server.use('/api', router);

server.listen(JSON_SERVER_PORT, () => {
    console.log('JSON Server is running on ' + JSON_SERVER_PORT)
});

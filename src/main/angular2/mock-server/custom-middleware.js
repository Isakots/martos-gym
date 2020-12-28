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

// uncomment this in order to auth as User
// server.post('/api/login', (req, res) => {
//     res.jsonp({"token": 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QudGVzdCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdfQ.Sme0P6eLLUsAxAE1-8v5ZuYuyRTsn5eDYgIsKCEUmh4});
// });

// uncomment this in order to auth as Admin
server.post('/api/login', (req, res) => {
    if(req.body.password === '123') {
        return res.status(401).end();
    }
    res.jsonp({"token": 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QudGVzdCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfTUVNQkVSIl19.0ZcaIVLHdHbya5aEJET2y7JDCoorBbirogjf6i41LQ8'});
});

server.post('/api/register', (req, res) => {
    if(req.body.password === '123') {
        return res.status(400).end();
    }
    res.status(200).end();
});

server.use('/api', router);

server.listen(JSON_SERVER_PORT, () => {
    console.log('JSON Server is running on ' + JSON_SERVER_PORT)
});

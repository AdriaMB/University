const http = require('http');
const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer( (req, res) => {
    // res is a ServerResponse.
    // Its writeHead() method sets the response header.
    res.writeHead(200, {'Content-Type': 'text/plain'});
    // The end() method is needed to communicate that both the header
    // and body of the response have already been sent. As a result, the response can
    // be considered complete. Its optional argument may be used for including the last
    // part of the body section.
    res.end('Hello World\n');
})

// listen() is used in an http.Server in order to start listening for
// new connections. It sets the port and (optionally) the IP address.
server.listen(port, hostname, () => {
	console.log('Server running at http://'+hostname+':' + port+'/')
})

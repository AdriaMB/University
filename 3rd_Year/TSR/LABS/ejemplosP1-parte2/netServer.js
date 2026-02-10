const net = require('net');

let i = 0;

const server = net.createServer( function(c) { //the connection listener is the hole function / c is a Socket in communication with the client
	console.log('server: client connected');
 	c.on('end', function() {
		console.log('server: client disconnected');
 	});
 
 	c.on('data', function(data) {
		c.write('Hello\r\n'+ data.toString()); // send resp

		//setInterval(()=>{ i++; c.write("Hello " + i)}, i*1000)
 		//c.end(); // close socket
 	});
});

server.listen(8002, function() { //listening listener
	console.log('server bound');
});

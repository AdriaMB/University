const net = require('net');
const LOCAL_PORT = 8001;
const LOCAL_IP = '127.0.0.1';
//
//const REMOTE_IP="cdt.gva.es";
//const REMOTE_IP="apache.rediris.es"
const REMOTE_IP="localhost"

const REMOTE_PORT=8002
//const REMOTE_IP = '158.42.4.23'; // www.upv.es
const server = net.createServer(function (socket) {

	const serviceSocket = new net.Socket(); // creates the serviceSocket, which is a client socket

	serviceSocket.connect(parseInt(REMOTE_PORT), REMOTE_IP, function () {

		socket.on('data', function (msg) { // listener to the event of receiving data from client
			console.log("Hi. I detected this from my socket")
			console.log (msg + "")
			serviceSocket.write(msg); // write what you obtained from the client to the server
		});
		serviceSocket.on('data', function (data) { // another listener for the event of receiving data, this time from the second socket
			socket.write(data);
		});

	});
}).listen(LOCAL_PORT, LOCAL_IP);

console.log("TCP server accepting connection on port: " + LOCAL_PORT);

// socket.connect( port, IP, function(){....})
// server is an EventEmitter, same as with emitter in the Events module:

// const server = net.createServer(...).listen(LOCAL_PORT, LOCAL_IP)
// is similar to
// const emitter = new ev.EventEmitter()

// When we established event listeners in Event, we did it like:
// emitter.on(e1, function))
// but now we specify where do we create the listener:
// SOCKET.on(e1, function), or SERVICESOCKET.on(...)



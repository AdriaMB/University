const net = require('net');
const LOCAL_PORT = 8000;
const LOCAL_IP = '127.0.0.1';

if(process.argv.length < 4){
	console.error("There are not enough arguments: we need the remoteIP and remotePort");
	process.exit(-1);
}
//console.log(process.argv)
const arg = process.argv.slice(2);
const REMOTE_IP = arg[0];// console.log(REMOTE_IP);
const REMOTE_PORT=arg[1];// console.log(REMOTE_PORT);

const server = net.createServer(function (socket) {

	const serviceSocket = new net.Socket(); // creates the serviceSocket

	serviceSocket.connect(parseInt(REMOTE_PORT), REMOTE_IP, function () {

		socket.on('data', function (msg) { // listener to the event of receiving data
		console.log (msg + "")
		serviceSocket.write(msg);
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



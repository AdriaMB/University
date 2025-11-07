const net = require('net');
const LOCAL_PORT = 8000; 		//This is the port that will be accessed by the normal clients
const LOCAL_IP = '127.0.0.1';

if(process.argv.length < 4){
	console.error("There are not enough arguments: we need the remoteIP and remotePort");
	process.exit(-1);
}
//console.log(process.argv)
const arg = process.argv.slice(2);
const REMOTE_IP = arg[0];// console.log(REMOTE_IP);
const REMOTE_PORT=arg[1];// console.log(REMOTE_PORT);

let server;

function createSocket(ip, port){
	if(server){
		// disconnect the socket that points to the previous server
		server.close(()=>{
			console.log("Restarting proxy server")
		})
	}
	server = net.createServer(function (socket)
	{
		const serviceSocket = new net.Socket(); // creates the serviceSocket
		serviceSocket.connect(parseInt(ip), port, function () {
			serviceSocket.write(JSON.parse({"address":8000}))

			socket.on('data', function (msg) { // listener to the event of receiving data
				console.log (msg + "")
				var input = JSON.parse(msg);
				serviceSocket.write(msg);
			});

			serviceSocket.on('data', function (data) { // another listener for the event of receiving data, this time from the second socket
				socket.write(data);
			});

		});
	}).listen(LOCAL_PORT, LOCAL_IP);
	console.log("TCP server accepting connection on port: " + LOCAL_PORT);
}



const programmerServer = net.createServer(function(socket){
	socket.on('data', function(msg){
		var input = JSON.parse(msg)

		createSocket(input.remote_ip, input.remote_port); // we create a new socket for the next server.

	});


}).listen(8001, LOCAL_IP)

createSocket(REMOTE_IP, REMOTE_PORT);



		/**
		 const controlServer = net.createServer(function(socket){
			const serviceSocket = new net.Socket();
			serviceSocket.connect(parseInt(REMOTE_PORT), REMOTE_IP. function(){
				socket.on('data', function(msg){
					let payload = JSON.parse(msg)
					REMOTE_IP = payload.remote_ip;
					REMOTE_PORT = payload.remote_port;
					socket.write(" Ok, updated to ${REMOTE_IP}: ${REMOTE_PORT}\n");
					console.log("Nuevo destino => " + REMOTE_IP + ":" + REMOTE_PORT);

				});
				serviceSocket.on('data', function(data){
					socket.write(data);
				)};


			});
		});
	}).listen(CONTROL_PORT, LOCAL_IP)
	console.log("TCP server accepting connection on port: " + LOCAL_PORT);



		 */

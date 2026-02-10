const net = require('net');
let arg = process.argv.slice(2);
if(process.argv.length < 3){
	console.error("I need my Ip and the IP of the server")
	process.exit(-1);
}
let ipServer = arg[0]
let ipClient = arg[1]



const client = net.connect({host: ipServer, port:8000}, function() { //connect listener
	console.log('client connected');
	let ip = JSON.stringify({address: ipClient});
	client.write(ip);
});

//Defines the listeners to events from server:
client.on('data', function(data) {

 	let object = JSON.parse(data)
	console.log(object.load)
	console.log(object.address)

// 	client.end(); //no more data written to the stream
});

client.on('end', function() {
	console.log('client disconnected');
});

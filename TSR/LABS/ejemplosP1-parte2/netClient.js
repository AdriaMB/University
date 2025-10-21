const net = require('net');
const client = net.connect({port:8000}, function() { //connect listener
	console.log('client connected');
	client.write('world!\r\n');
});

//Defines the listeners to events from server:
client.on('data', function(data) {
 	console.log(data.toString());
 	client.end(); //no more data written to the stream
});

client.on('end', function() {
	console.log('client disconnected');
});

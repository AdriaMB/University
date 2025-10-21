const net = require('net');
const fs  = require('fs');


if(process.argv.length < 3){
	console.error("There are not enough arguments: we need the remotePort");
	process.exit(-1);
}

// server is an EventEmitter with the following events:
// close()
// connection()
// error()
// listening()
// address(): returns the address, family name and port of the servers
const server = net.createServer( function(c) { //connection listener
	console.log('server: client connected');

 	c.on('end', function() {
		console.log('server: client disconnected');
 	});
 
 	c.on('data', function(data) {
		let object = JSON.parse(data)
		console.log('The client IP is: ' + object.address);
		let info = JSON.stringify({load: getLoad(), address: c.localAddress})
 		c.write(info)
 	});
});
//			process.argv[2] == port listening to
server.listen(process.argv[2], function() { //listening listener
	console.log('server bound');
});


function getLoad(){
	data = fs.readFileSync("/proc/loadavg"); // require fs
	var tokens = data.toString().split(" 	");
	console.log(tokens)
	var min1 = parseFloat(tokens[0]) + 0.01; console.log(tokens[0]);
	var min5 = parseFloat(tokens[1]) + 0.01; console.log(tokens[1]);
	var min15 = parseFloat(tokens[2]) + 0.01;console.log(tokens[2]);
	return min1*10 + min5*2 + min15;

}

console.log(getLoad())

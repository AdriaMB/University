const net = require('net')
let cont = 0
// The server is in our same machine.
let client = net.connect({port: 9000},
	function() {
		console.log('client connected')
		client.write(cont + ' world!')
	})

client.on('data', function(data) {
	console.log(data.toString())
	if (cont > 1000) client.end()
	else client.write((++cont) + ' world!')
})
client.on('end', function() {
	console.log('client disconnected')
})
client.on('error', function() {
	console.log('some connect. error')
})

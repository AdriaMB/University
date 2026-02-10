const net = require('net')

// The server is in our same machine.
let client = net.connect({port: 9000},
	function() { //'connect' listener
		console.log('client connected')
		// This will be echoed by the server.
		client.write('world!\r\n')
	})

client.on('data', function(data) {
    // Write the received data to stdout.
    console.log(data.toString())
    // This says that no more data will be
    // written to the Socket.
    client.end()
});

client.on('end', function() {
    console.log('client disconnected')
})

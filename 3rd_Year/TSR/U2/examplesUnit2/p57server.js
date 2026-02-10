const net = require('net')
let server = net.createServer(
    function(c) { //'connection' listener
		console.log('server connected')
		c.on('end', function() {
			console.log('server disconnected')
		})
		// Send "Hello" to the client.
		c.write('Hello\r\n')
		// With pipe() we write to Socket 'c'
		// what is read from 'c'.
		c.pipe(c)
	}) // End of net.createServer()

server.listen(9000,
	function() { //'listening' listener
		console.log('server bound')
	})
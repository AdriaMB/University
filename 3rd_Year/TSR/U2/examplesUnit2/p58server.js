const net = require('net')
let server = net.createServer(
	function(c) {
		console.log('server connected')
		c.on('end', function() {
			console.log('server disconnected')
		})
		c.on('error', function() {
			console.log('some connect. error')
		})
		c.on('data', function(data) {
			console.log('data from client: '
				+ data.toString())
			c.write(data)
		})
}) // End of net.createServer()
server.listen(9000,
	function() {
		console.log('server bound')
})

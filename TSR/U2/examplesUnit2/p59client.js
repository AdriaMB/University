const net = require('net')
if (process.argv.length != 4) {
	console.error("Two arguments are needed:")
	console.error(" - function name (either 'fibo' or 'fact').")
	console.error(" - number (argument for that function).")
	process.exit(1)
}
let fun = process.argv[2]
let num = Math.abs(parseInt(process.argv[3]))

let client = net.connect({port: 9000},
	function() {
		console.log('client connected')
		let request = {"fun":fun, "num":num}
		client.write(JSON.stringify(request))
	})
client.on('data', function(data) {
	console.log(data.toString())
	client.end()
})
client.on('end', function() {
	console.log('client disconnected')
})
client.on('error', function() {
	console.log('some connection error')
})

const net = require('net')
let myF = require('./myFunctions')
let end_listener = function() {console.log('Server disconnected!')}
let error_listener = function() {console.log('Error in server!')}
let bound_listener = function() {console.log('Server bound!')}

let server = net.createServer(function(c) {
	c.on('end', end_listener)
	c.on('error', error_listener)
	c.on('data', function(data) {
		let p = JSON.parse(data)
		let q
		if (typeof(p.num) != 'number') q = NaN
		else { switch (p.fun) {
			case 'fibo': q = myF.fibo(p.num); break
			case 'fact': q = myF.fact(p.num); break
			default: q = NaN
		}}
		c.write(p.fun+'('+p.num+') = '+q)
	})
})
server.listen(9000, bound_listener)

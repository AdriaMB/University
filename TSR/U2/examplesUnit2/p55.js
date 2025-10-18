const st = require('./Circle.js')
const os = require('os')
process.stdout.write("Radius of the circle: ")
// Needed for initiating the reads
// from stdin.
process.stdin.resume()
// Needed for reading strings instead of
// "Buffers".
process.stdin.setEncoding("utf8")
// Implemented as an endless loop.
// Every time we read a radius, its
// circumference is printed and a new
// radius is requested.
process.stdin.on("data", function(str) {
	// The string that has been read is "str".
	// Remove its trailing endline.
	let rd = str.slice(0,str.length-os.EOL.length)
	console.log("Circumference for radius " +
	             rd + " is " + st.circumference(rd))
	console.log(" ")
	process.stdout.write("Radius of the circle: ")
})
// The "end" event is generated when
// STDIN is closed. [Ctrl]+[D] in UNIX.
process.stdin.on("end", function() {
console.log("Terminating...")
})
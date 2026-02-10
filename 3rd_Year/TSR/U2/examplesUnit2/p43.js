const fs=require('fs')

function readFilePromise(filename) {
	return new Promise( (resolve,reject) => {
		fs.readFile(filename, (err,data) => {
			if (err) reject(err+'')
			else resolve(data+'')
		})
	})
}

/* Original code snippet...
readFilePromise("p43.js").then(console.log, console.error)
readFilePromise("doesntExist.js").then(console.log, console.error)
*/

// In the original promise-based program, we cannot control the
// order in which the output messages are shown. Indeed, in many
// cases, the error generated when we try to read "doesntExist.js" 
// shows before the contents of "readfile.js" are printed on screen.
//
// With async/await, programmers control the order in which
// promise results are managed, using "await" to this end.
//
// Note, however, that in this example the execution is broken
// as soon as the first error is detected. Switch the order of 
// the readFilePromise() calls and see what is the result...
async function readTwoFiles() {
	try {
		console.log(await readFilePromise("p43.js"))
		console.log(await readFilePromise("doesntExist.js"))
	} catch (err) {
		console.error(err+'')
	}
}

readTwoFiles()
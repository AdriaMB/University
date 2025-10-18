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
readFilePromise("p44.js").then(console.log, console.error)
readFilePromise("doesntExist.js").then(console.log, console.error)
*/

// In the original promise-based program, we cannot control the
// order in which the output messages are shown. Indeed, in many
// cases, the error generated when we try to read "doesntExist.js" 
// shows before the contents of "readfile.js" are printed on screen.
//
// With async/await, programmers control the order in which
// promise results are managed, using "await" to this end, but
// the execution breaks as soon as the first error happens.
// Switch these readFilePromise() calls in order to check this.
async function readTwoFiles() {
	console.log(await readFilePromise("p44.js"))
	console.log(await readFilePromise("doesntExist.js"))
}

// We may use catch() here, instead of try/catch in readTwoFiles()
readTwoFiles().catch( console.error )
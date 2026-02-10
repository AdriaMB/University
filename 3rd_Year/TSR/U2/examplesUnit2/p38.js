const fs=require('fs')

function readFilePromise(filename) {
	return new Promise( (resolve,reject) => {
		fs.readFile(filename, (err,data) => {
			if (err) reject(err+'')
			else resolve(data+'')
		})
	})
}

readFilePromise("p38.js").then(console.log, console.error)
readFilePromise("doesntExist.js").then(console.log, console.error)

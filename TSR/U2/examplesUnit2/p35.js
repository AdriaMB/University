const fs=require('fs')
const fileName='p35.js'

fs.access(fileName, function(err) {
	if (err) {
		console.log(fileName + " does not exist!")
	} else {
		fs.stat(fileName, function(error, stats) {
			fs.open(fileName, "r", function(error, fd) {
				let buffer = Buffer.alloc(stats.size)
				fs.read(fd, buffer, 0, buffer.length, null, function(error,  bytesRead, buffer) {
					let data = buffer.toString("utf8", 0, buffer.length)
					console.log(data)
					// fs.closeSync(fd)
					fs.close(fd, function(er) {if (er) console.error(er)})
				})
			})
		})
	}
})

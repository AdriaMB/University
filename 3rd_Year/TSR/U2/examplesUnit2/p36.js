const fs=require('fs')

fs.readdir('.', function(err, files) {
	let count = files.length
	let results = {}

	files.forEach(function(filename) {
		fs.readFile(filename, function(err, data) {
			console.log(filename, ' has been read')
			results[filename] = data
			count--
			if (count <= 0) {
				console.log('\nTOTAL: ', files.length, 
					' files have been read')
			}
		})
	})
})

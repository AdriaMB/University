const fs = require('fs')
fs.writeFileSync('data1.txt', 'Hello NodeJS')
fs.writeFileSync('data2.txt', 'Hello everybody!')

function callback(err,data) {
	if (err) console.error('---\n' + err.stack)
	else console.log('---\nFile content is:\n'+data.toString())
}

setTimeout(function(){fs.readFile('data1.txt',callback)}, 3000)
fs.readFile('data2.txt', callback)
fs.readFile('data3.txt', callback)
console.log("root(2) = ", Math.sqrt(2))

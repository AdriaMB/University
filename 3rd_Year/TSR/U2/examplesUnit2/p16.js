function writing(x) {
	console.log("---\nWriting after "+x+" seconds")
}

function writingClosure(x) {
	return function() {
		console.log("---\nWriting after " + x + " seconds")
	}
}

setTimeout( function() {writing(6)}, 6000 )
setTimeout( writing, 3000 )
setTimeout( writingClosure(4), 4000 )
console.log("root(2) = ", Math.sqrt(2))

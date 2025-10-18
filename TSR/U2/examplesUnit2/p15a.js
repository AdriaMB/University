function createFunc() {
	let name="Mozilla"
	return function() {console.log(name)}
}

let myFunc = createFunc()
myFunc()

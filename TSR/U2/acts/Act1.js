function table(x) {  // Prints column x of a (1..10) multiplication table
	console.log("HI")
	for (let j=1; j<11; j++)
		console.log("%d * %d = %d", x, j, x*j)
	console.log("")
}

function allTables() {
	for (let i=1; i<11; i++)
		table(i)
}

//allTables(table(30),table(20),table(10))
//table(table(2))
allTables(table(2), table(20), table(10)) // JAVASCRIPT USES STRIC EVALUATION, WHERE IT FIRSTS CALCULATES THE
				// REAL VALUES OF THE ARGUMENTS BEFORE PASSING THEM TO THE FUNCTION
let a
console.log(typeof(a))
console.log(a * 43)

function a3(x) {
	//console.log(x)
	return function(y) {
		return x*y
	}
}

function add(v) {
	let sum=0
	for (let i=0; i<v.length; i++){
		sum += v[i]
		console.log("The sum is: " + sum)
	}
	console.log("The final sum is: " + sum)
	return sum
}

function iterate(num, f, vec) {
	let amount = num

	console.log(f)
	let result = 0
	if (vec.length<amount)
		amount=vec.length
	for (let i=0; i<amount; i++)
		result += f(vec[i])
	return result
}

let myArray = [3, 5, 7, 11]
console.log(iterate(2, a3, myArray))
console.log(iterate(2, a3(2), myArray))
console.log(iterate(2, add, myArray))
console.log(add(myArray))
console.log(iterate(5, a3(3), myArray))
console.log(iterate(5, a3(1), myArray))

//console.log(a3 + a3)

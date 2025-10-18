// Receive a set of file names, show the length of each one of them, and the global length.
const  fsp = require("fs").promises
let names=process.argv.slice(2) // Array of file names.
let allLength=0 // Accumulated length

// This generator provides the file name to the callback.
// The callback, given the file contents, computes the file length and shows it.
// It also adds that length to the global length.
function genCallback(n) {
	return (data) => 
		{allLength+=data.length; console.log( n +": " + data.length)}
}
// Generate the promises...
// Next uncommented line is equivalent to this fragment:
/*
let myFiles
for(let i in names)
    myFiles[i] = fsp.readFile(names[i],"utf8").then( genCallback(names[i]) )
*/
let myFiles = names.map((name)=>fsp.readFile(name,"utf8").then(genCallback(name)))

// Use all() in order to report the global length once all files have been read.
Promise.all(myFiles).then(()=>console.log("Total length: "+allLength))
			.catch(()=>console.error("Error reading some files!"))

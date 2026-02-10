// Prints the length of multiple files on screen.  All file names are given as arguments.
const  fsp = require("fs").promises
// Check for the names of the files to be read. 
if (process.argv.length < 3) {console.log("Introduce several file names as arguments, please!");  return}
let myFiles=[], names=process.argv.slice(2) // Array of promises. Array of file names.
let numFiles=names.length, allLength=0 // Number of files to be processed. Accumulated length.

// Generate the promises.
for(let i=0;i<numFiles;i++) {
    myFiles[i] = fsp.readFile(names[i],"utf8")
}
// Function that prints the total length.
function showAll( ) {console.log( "Total: " + allLength )}
// Function that prints the length of the file contents.
function showLength( name ) {
  return function( contents ) {
    allLength+=contents.length;
    // Print the name of the corresponding file and its length.
    console.log( name + ": " + contents.length )
}}
// Function that prints information about errors.
function showError( err ) {console.log(err.message)}
// The same, but for the ".all()" case. 
function showFinalError( err ) {console.log( "Errors accessing some files. Unable to compute total length.")}
Promise.all(myFiles).then(showAll, showFinalError) // Show the summary
// Show the results.
for (let i=0;i<numFiles;i++) {myFiles[i].then(showLength( names[i] ), showError)}
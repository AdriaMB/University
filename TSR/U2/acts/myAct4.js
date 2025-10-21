const fs = require('fs') // module fs is needed to invoque the readFile function
if(process.argv.length <= 2){
    // there aren't enough arguments
    console.error("We need at least 1 file name!!")
    process.exit(1)
}
let arrayFiles = process.argv.slice(2) // will create a subarray from position 2 till the end



// Callback function: data is automatically passed by fs.readFile
function callback(err, data){ //
    if(err){
        console.error("The file couldn't be opened: " + err)
        return;
    }
    //let fileData = data.split("\n")
    //fileData.forEach((element) => process.stdout.write(element))// process.stdout is like console.log

    //console.log(typeof(data))
    console.log("\n###    ASYNCHRONOUS   ####################################")
    console.log(data.toString())
    console.log("###########################################################")
}


// Function that will read the file in a synchronous way and then display its contents
function displayContentSync(file){
    let contents = fs.readFileSync(file)
    console.log("\n||||    SYNCHRONOUS   ||||||||||||||||||||||||||||||||||||")
    console.log(contents.toString())
    console.log("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||")
}



//ASYNCHRONOUS VARIANT: fs.readFile()
//  This variant will read the file in a non-blocking manner and return the conteny in a callback function
arrayFiles.forEach((file) => fs.readFile(file, callback))

//SYNCHRONOUS VARIANT: fs.readFileSync()
//  This variant will read the file synch, blocking the thread until the process is completed, and returns the result as a value
arrayFiles.forEach((file) => displayContentSync(file))




console.log("==========\n"+"This is the end of the program"+"\n==========")





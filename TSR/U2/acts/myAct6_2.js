const fs = require('fs')
if(process.argv.length < 3){
    console.error("At least 1 file")
    return -1;
}

const files = process.argv.slice(2) // take the arguments from position 2 onwards

let largestFile = ""
let largestLength = 0
let count = 0


function generator(element){


    return function(err, data){
        if(err){
            console.error("Couldn't open file " + element)
            count++

        }
        else{
            if(largestLength < data.length){
                largestFile = element
                largestLength = data.length
            }
            count++
            console.log(count)
        }


        if(count >=  files.length){
            console.log("Largest file " + largestFile + " with length " + largestLength)
        }


    }

}


files.forEach( (element)=> fs.readFile(element, "utf8", generator(element)))



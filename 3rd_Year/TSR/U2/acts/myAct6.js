const fs = require('fs')
if(process.argv.length < 3){
    console.error("At least 1 file")
    return -1;
}

const files = process.argv.slice(2) // take the arguments from position 2 onwards

let largestFile = ""
let largestLength = 0
let count = 0

function processFile(element){


    fs.readFile(element, (err, data)=>{

       if(err){
           console.error("We couldn't open the file " + element)
            count++
           // console.log(count)
        }
        else{
            console.log("The file " + element + " was opened")
            let length = data.length
            if(largestLength < length){
                largestFile = element
                largestLength = length
                //console.log("The file " + largestFile + " was the largest")
                //console.log("Its length is " + largestLength)

            }
            count++
            //console.log(count)
        }

        if(count >= files.length)
            finalResult()

    })



}


function finalResult(){
    console.log("The file " + largestFile + " was the largest")
    console.log("Its length is " + largestLength)

}

files.forEach( (element)=> processFile(element))



const fs = require('fs')


function readFilePromise(){
    return new Promise( (resolve, reject)=>{
        fs.readFile ('/etc/hosts','utf8',(err, data) =>{
            if(err) reject(err+"")
            else resolve(data+"")
        })
    })

}

// we use wait if we want to establish certain order in the execution
// of asynch events, as the program waits until it obtains the result of
// the function and continues

async function readFile(){
    try{
        console.log("HI before the read\n")
        console.log( await readFilePromise() )
        console.log("                   HI after the read")
    }
    catch(err){
        console.error(err+"")

    }


}

readFile()
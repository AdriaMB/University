const fs = require('fs');

// Calling directly to the promise like this does not order
// the execution of events: the prints HI will be executed first
// and then the print of the readFilePromise will be performed

function readFilePromise(){
    return new Promise( (resolve, reject)=>{
        fs.readFile ('/etc/hosts','utf8',(err, data) =>{
            if(err) reject(err+"")
            else resolve(data+"")
        })
    })

}

console.log("HI before the promise")
readFilePromise().then(console.log, console.error)
console.log("HI after the promise")
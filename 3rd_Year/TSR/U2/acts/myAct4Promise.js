const fsp = require('fs').promises
if(process.argv.length <= 2){
    console.error("There are not enough files")
    return;
}
let names = process.argv.slice(2)

function onFulfilled(data){
    console.log("The data to print:" )
    console.log(data.toString())

}

function onRejected(error){
    console.error("There was an error: " + error)

}

function readFilePromise(filename){
    return new Promise((resolve, reject) =>{
        fsp.readFile(filename, (err,data)=>{
            if(err) reject(err)
            else    resolve(data)
        })
    })
}

names.forEach((file) => readFilePromise(file).then(onFulfilled, onRejected))

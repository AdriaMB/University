/**

function counter(initialValue) {
    return function () {
        return ++initialValue
    }
}
let increase = counter(0)
process.stdout.write(increase()+"")
let another = counter(increase())
process.stdout.write(another()+"")
process.stdout.write(increase()+"")

*/






// EJEMPLO CON TIMEOUT: el tiempo de TimeOut no puede ser cambiado externamente. Una vez se ejecuta TimeOut, su cuentra atrás será inmutable (si establecemos que tras 6 segundos se ejecute la función, tras min 6 segundos esta se ejecutará, y no podemos cambiar ese valor a 5, por ejemplo).

// En este caso, lo que alteran es la variable t global, que es un valor distinto al que ponemos en los timers. Cuando estos acaban, y las funciones de dentro se lanzan, se printeará el valor que en ese momento la variable GLOBAL t tiene.
/**
t = 0
debugger
setTimeout(() => {debugger; console.log(t)}, t)
debugger
setTimeout(() => {debugger; console.log(t); t+= 1000}, t)
debugger
console.log(t)
debugger
t+= 1000
*/






/*
const fs = require('fs')
fs.readdir('.', function(err, files) {
    let count = files.length; let results = {}
    files.forEach(function(filename) {
        fs.readFile(filename, function(err, data) {
            console.log(filename, 'has been read')
            results[filename] = data
            if (--count <= 0)
                console.log('\nTOTAL:', files.length, 'files have been read')
        })
    })
})
*/






/**
const fs=require('fs').promises
let filenames = process.argv.slice(2)
async function showContents() {
    console.log( await fs.readFile(filenames[0])+"")
    console.log( await fs.readFile(filenames[1])+"")
}
showContents().catch(console.log)
*/




/**
const fsp=require('fs').promises
let filename = process.argv[2]
let word = process.argv[3]
function linesWithWord(w) {
    return function(contents) {
        result = []
        contents+=""
        contents.split("\n").forEach(function(line) {
            if (line.indexOf(w)>-1)
                result.push(line)
        })
        return result
    }
}
function lineCount(lineArray) {
    return lineArray.length
}
// The code to be used would be written here…
fsp.readFile(filename).then(linesWithWord).then(lineCount).then(console.log)
*/




/**
function test(x){

    console.log(x)

}
setTimeout(test, 1000)
//setTimeout(test(2), 2000)
setTimeout(() => {test(3)}, 3000)
*/





/**
function g(args){
    let sum = 0
    console.log(args.length)
    for (let j = 0; j< args.length; j++)
        sum += args[j]
    return sum

}

console.log(  g(1,2,3,4,4,5,5,6,67,7)  )
*/




const fs=require('fs')

let filenames = process.argv.slice(2)
let totalLength = 0

if (filenames.length<1){
    console.error("At least 1 filenames are needed")
    process.exit(-1)

}

filenames.forEach(function(name){
    try{
        console.log(name)
        totalLength += (fs.readFileSync(name) + "").length

    }catch(e){console.log("The file" + name + " doesn't exist")}
})

console.log("Total length ", totalLength)

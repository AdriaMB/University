const fs = require("fs")
const EventEmitter = require("events")
const emitter = new EventEmitter();

const e1 = "e1", e2 = "e2"
let inc = 0, t
var count = 0;

//console.log(typeof(e1))


function rand(){
    return Math.floor(Math.random()*(5000-2000)+2000)
}


function handler(e, n){


    return (inc) =>{

        n += inc
        console.log(e + " --> " + n)
        count++;
        if(count == 2){
            count = 0
            console.log("stage " + inc + " started after " + t + " s")

        }

    }

}


emitter.on(e1, handler(e1, 0))
emitter.on(e2, handler(e2, ""))

//console.log(typeof(e1))

function stage(){


    if(inc == 0){
        setInterval( ()=> {emitter.emit(e1, inc)}, 2000)
        setInterval( ()=> {emitter.emit(e2, inc)}, 5000)
    }


    inc++
    t = rand()
    setTimeout(stage, t)


}

setTimeout(stage, t = rand())

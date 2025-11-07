const ev = require("events")
const emitter = new ev.EventEmitter()

const e1 = "print"

function createListener(eventName){

    console.log("I am the listener")
}
emitter.on(e1, () => {createListener(e1)})

for(i = 0; i<10000;i++)
    emitter.emit(e1)

setInterval(function(){emitter.emit(e1)}, 2000)

while(true){
    console.log("-")
}

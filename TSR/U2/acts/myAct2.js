const ev = require('events')        // ev is the object that we will use to create the event emitter
const emitter = new ev.EventEmitter // emitter is the object that we will use to generate events and asign listener to events
const e1 = "one", e2 = "two", e3 = "three" // name of events. EVENTS CAN HAVE ANY NAME
let count1 = 0, count2 = 0, count3 = 0
let interval2 = 2000


function eventListener(eventName){

        return function () {
            if(eventName == e1){
                count1++
                console.log("Event " + eventName)
            }
            else if(eventName == e2){
                count2++
                if(count2 > count1)
                    console.log("Event "  + eventName)
                else
                    console.log("I have received more events of type 'one'")
            }
            else if(eventName == e3){
                count3++
                console.log("Event " + eventName)
                clearInterval(int2)
                interval2 = (interval2 >= 18000 ? 18000 : interval2*3)

                int2 = setInterval(() => emitter.emit(e2), interval2)

            }

            console.log("E1 = " + count1)
            console.log("E2 = " + count2)
            console.log("E3 = " + count3)
            console.log("Now the intervale of 2 is: " + interval2)

        }

}

// Listeners are registerd in the event emitter
emitter.on(e1, eventListener(e1))
emitter.on(e2, eventListener(e2))
emitter.on(e3, eventListener(e3))

//generate events periodically
setInterval(() => emitter.emit(e1), 1000)
let int2 = setInterval(() => emitter.emit(e2), interval2)
setInterval(() => emitter.emit(e3), 10000)
// an emitted event doesn't have to be a real function

for(let i = 0; i < 1000; i++){
    console.log("....")

}

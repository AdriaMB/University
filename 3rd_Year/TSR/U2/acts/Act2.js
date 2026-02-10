const ev = require('events')
const emitter = new ev.EventEmitter
const e1 = "print"
const e2 = "read"
const books = [ "Walk Me Home", "When I Found You", "Jane's Melody", "Pulse" ]

// Function that creates the intended event listeners.
function createListener( eventName ) {
	let num = 0
	return function (arg) {
		let book = ""
		if (arg) 
			book = ", now with book title '" + arg + "',"
		console.log("Event " + eventName + book + " has " +
			"happened " + ++num + " times.")
	}
}

// Listeners are registered in the event emitter.
emitter.on(e1, createListener(e1))
emitter.on(e2, createListener(e2))
// There might be more than one listener for the same event.
emitter.on(e1, () => console.log("Something has been printed!!"))

function emitE2() {
	let counter=0
	return function () {
		// This second argument provides the argument for the "e2" 
		// (i.e., read) listener.
		emitter.emit(e2,books[counter++ % books.length])
	}
}
// Generate the events periodically...
// First event generated every 2 seconds.
setInterval( () =>	emitter.emit(e1), 2000 )
// Second event generated every 3 seconds.
// Note that the first argument gets the result of a call to the
// emitE2 function. With this, its returned function provides the
// instructions to be executed every 3 seconds. Their closure contains
// the "counter" variable, that is increased in each call, generating
// a different book title each time. That book title is the second
// argument of the emit() call, and such value is provided as the
// argument for the "read" event listener.
setInterval( emitE2(), 3000 )
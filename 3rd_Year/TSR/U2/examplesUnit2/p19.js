function fibo(n) {
    return (n<2) ? 1 : fibo(n-2) + fibo(n-1);
}

console.log("Starting...");

// Wait for 10 ms to write the message.
// A new event is generated.
setTimeout( function() {
  console.log( "M1: First message..." );
}, 10 );

// More than 5 seconds needed.
let j = fibo(40);
function anotherMessage(m,u) {
   console.log( m + ": Result is: " + u );
}

// M2 is written before M1 since the main
// thread has not been suspended...
anotherMessage("M2",j);

// M3 is written after M1.
// It is scheduled in 1 ms.
setTimeout( function() {
 anotherMessage("M3",j);
}, 1 );

function alert(x) {	// Needed in Node.js in order 
   console.log(x);	// to print messages to stdout.
}

let global = 'this is global';

function scopeFunction() {
  alsoGlobal = 'This is also global!';
  let notGlobal = 'This is private to scopeFunction!';

  function subFunction() {
    alert(notGlobal);	// We can still access notGlobal
			// in this child function.
    stillGlobal = 'No var keyword so this is global!';
    let isPrivate = 'This is private to subFunction!';
  }

  alert(stillGlobal);	// This is an error since we
			// haven't executed subfunction.
  subFunction();	// Execute subfunction.
  alert(stillGlobal);	// This will output 'No var
			// keyword so this is global!'
  alert(isPrivate);	// This generates an error since
			// isPrivate is private to
			// subfunction().
  alert(global);	// It outputs: 'this is global'
}

alert(global);		// It outputs: 'this is global 

alert(alsoGlobal);	// It generates an error since 
			// we haven't run scopeFunction yet.

scopeFunction();

alert(alsoGlobal);	// It outputs: 'This is also global!';

alert(notGlobal);	// This generates an error.

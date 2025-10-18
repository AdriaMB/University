/* repl_show.js */

const repl = require('repl')
const f = function(x) {console.log(x)}
repl.start('$> ').context.show = f

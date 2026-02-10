// Solution to first part of Activity 10. It provides the auxiliary sort_lines function.
var fs=require('fs');
file=process.argv[2];

function sort_lines(mystring)
{ return mystring.toString().split("\n").sort().join("\n");}

var r=fs.readFileSync(file, 'utf-8');
fs.writeFileSync(file+"2", sort_lines(r), 'utf-8');
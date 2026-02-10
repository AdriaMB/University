function fibo(n) { return (n<2) ? 1 : fibo(n-2) + fibo(n-1) }

function fact(n) { return (n<2) ? 1 : n * fact(n-1) }

module.exports.fibo=fibo
module.exports.fact=fact

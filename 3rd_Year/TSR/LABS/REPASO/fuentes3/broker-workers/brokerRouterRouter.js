const {lineaOrdenes, traza, error, adios, creaPuntoConexion} = require('../tsr')
const zmq = require('zeromq/v5-compat');
lineaOrdenes("frontendPort backendPort")
let workers  =[] // workers disponibles
let pendiente=[] // peticiones no enviadas a ningun worker
let frontend = zmq.socket('router')
let backend  = zmq.socket('router')
creaPuntoConexion(frontend, frontendPort)
creaPuntoConexion(backend,  backendPort)
let lista = {}
let msgAtendidos = 0	;

function procesaPeticion(cliente,sep,msg) { // llega peticion desde cliente
	traza('procesaPeticion','cliente sep msg',[cliente,sep,msg])
	if (workers.length) {
		backend.send([workers.shift(),'',cliente,msg])
	}
	else pendiente.push([cliente,msg])
}
function procesaMsgWorker(worker,sep,cliente,resp) {
	traza('procesaMsgWorker','worker sep cliente resp',[worker,sep,cliente,resp])
	if(lista[worker] == null)
		lista[worker] = -1;

	if (pendiente.length) { // hay trabajos pendientes. Le pasamos el mas antiguo al worker
		let [c,m] = pendiente.shift()
		backend.send([worker,'',c,m])
	}
	else
		workers.push(worker) // añadimos al worker como disponible
	if (cliente){
		frontend.send([cliente,'',resp]) // habia un cliente esperando esa respuesta
		lista[worker] = lista[worker] + 1;
		msgAtendidos++;
	}
}

frontend.on('message', procesaPeticion)
frontend.on('error'  , (msg) => {error(`${msg}`)})
 backend.on('message', procesaMsgWorker)
 backend.on('error'  , (msg) => {error(`${msg}`)})
 process.on('SIGINT' , adios([frontend, backend],"abortado con CTRL-C"))

//setInterval(representRequestsPerWorker, 5000);

function representRequestsPerWorker(){

	console.log(lista)
	console.log("Total requests: " + msgAtendidos + "\n")

}

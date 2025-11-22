const {lineaOrdenes, traza, error, adios, creaPuntoConexion} = require('../tsr')
const zmq = require('zeromq/v5-compat')


lineaOrdenes("frontendPort backendPort")
let workers  = 0 // workers disponibles
let pendiente=[] // peticiones no enviadas a ningun worker
let frontend = zmq.socket('router')
let backend  = zmq.socket('dealer')
creaPuntoConexion(frontend, frontendPort)
creaPuntoConexion(backend,  backendPort)

function procesaPeticion(cliente,sep,msg) { // llega peticion desde cliente
	traza('procesaPeticion','cliente sep msg',[cliente,sep,msg])
	//  If there are workers
	if (workers>0) {
		backend.send([cliente,msg]) // we send to DEALER of the broker2
		workers --;
		console.log("-------------------------\n" + workers + "\n-----------------------\n")

	}
	else pendiente.push([cliente,msg]) // In case there aren't
}
function procesaMsgWorker(cliente,resp) {
	traza('procesaMsgWorker','worker sep cliente resp',[cliente,resp])
	if (pendiente.length) { // hay trabajos pendientes. Le pasamos el mas antiguo al worker
		let [c,m] = pendiente.shift()
		backend.send([c,m])
	}
	else workers++; // añadimos al worker como disponible
	console.log("-------------------------\n" + workers + "\n-----------------------\n")
	if (cliente) frontend.send([cliente,'',resp]) // habia un cliente esperando esa respuesta
}

frontend.on('message', procesaPeticion)
frontend.on('error'  , (msg) => {error(`${msg}`)})
 backend.on('message', procesaMsgWorker)
 backend.on('error'  , (msg) => {error(`${msg}`)})
 process.on('SIGINT' , adios([frontend, backend],"abortado con CTRL-C"))


 function displayInfo(){



}

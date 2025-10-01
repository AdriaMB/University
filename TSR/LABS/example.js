let x = 6
console.log(x)
x = "Helloo"

console.log(x)
console.log(x)

x = []
console.log(x)

x[1] = 0
console.log(x)
console.log(x[0])
console.log(x[1])

y = x[0] + 3
console.log(y)

console.log(typeof y)
y = "heelo"
console.log(typeof y)

let result
console.log(result)
for(let i = 0; i <  10; i++){
    result = result + i

}
console.log(result)


function personaTest(){
    let person
    if(person !== null && person !== undefined){
        console.log("Person exists")
    }
    else{
        console.log("Person does not exist")
    }


    let users = ["Alice", "Bob", "Charlie"]
    
    for (c = 0; c < users.length; c++){
        console.log(users[c])
    }

}

let locations = []
locations[2] = "Valencia"
console.log(locations[0])
console.log(locations[20])

console.log(locations)
let places = locations
places[0] = "Madrid"
console.log(locations)  

places = locations.slice()
places[1] = "Sevilla"
console.log(locations)
console.log(places)


places.push("Barcelona")
console.log(places)
console.log(locations)

let a = 5
let b = a
b = 10
console.log(a)
console.log(b)



let anotherPerson = {}
anotherPerson.name = "Bob"
anotherPerson.age = 25
console.log(anotherPerson.district)



let person = {
    name: "Aline",
    age: 30,
    address: {
        city: "Lumière",
        zip: "33"
    }
}
console.log(person)

console.log(JSON.stringify(person))
for( let i in person){
    console.log("Property: "  + i + ": " + person[i])
    console.log(i)
}
for (let i in person.address){
    console.log("Property: "  + i + ": " + person.address[i])
    console.log(typeof i)
}




function add(...others){
    let sum = 0
    for(let i = 0; i < others.length; i++){
        sum += others[i]
    }
    return sum

}

console.log(add(1,2,3))
console.log(add(1,2))
console.log(add(1))
console.log(add())
console.log(add(1,2,3,4,5,6,7,8,9))
console.log(add({prop1: 12}, 2, 3))


function changeColour(car, newColour){
    return car.colour = newColour
}
function byFerrari(car){
    car = {brand: "Ferrari", colour: "red"}

}


let myCar = {brand: "Toyota", model: "Corolla", colour: "red"}
changeColour(myCar, "blue")
console.log(myCar)
byFerrari(myCar)
console.log(myCar)
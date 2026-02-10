module Excercises where
--Ex1
    --Return the binary value of a non-negative integer
    decBin :: Int -> [Int]
    decBin x =  if x < 2 then [x]
                         else (x `mod` 2):(decBin (div x 2))
--Ex2
    --Return the decimal value of a binary number
    binDec :: [Int] -> Int
    binDec(x : []) = x
    binDec(x : y) = x + binDec y * 2

--Ex3
    --Returns the divisors of x
    divisors :: Int -> [Int]
    divisors x = [a | a <- [1..x], (mod x a) == 0]

--Ex4
    --Returns whether an integer belons to a list of integers
    member :: Int -> [Int] -> Bool
    member x xs = length[() | a <- xs, a == x] /= 0

--EX5
    --Checks whether a given number is a prime number
    primeN :: Int -> Bool
    primeN x =  if x >= 2 then divisors x == [1, x]
                          else True
    --Computes the list of the  first prime numbers
    primes :: Int -> [Int]
    primes n = take n [x | x <-[2..], primeN x]

--EX 6
    --Returns from a list all the even elements
    onlyEven :: [Int] -> [Int]
    onlyEven xs = [x | x <- xs, (mod x 2) == 0]

--EX 7
    --Returns the elements that occupy "even positions" of a list of int
    selectEvenPos :: [Int] -> [Int]
    selectEvenPos xs = [xs!!n |n <- [0..(length xs - 1)], (n `mod` 2) == 0]

--EX 8
    --Inserts an element in an ordered list keeping the list ordered
    ins :: Int -> [Int] -> [Int]
    ins a [] = a : []
    ins a (x:xs) =  if a <= x then a:x:xs
                              else x : (ins a xs)

    --Sorts in ascending order
    iSort :: [Int] -> [Int]
    iSort [] = []
    iSort (a:xs) = ins a (iSort xs)

--EX 9
    --Using map, define a function that multiplies by 2 all the elements of a list
    doubleAll :: [Int] -> [Int]
    doubleAll xs = map (*2) xs



--EX 10
    --Write the definitions with intensional lists of map and filter
    mapC :: (a->b) -> [a] -> [b]
    --mapC f [] = []
    --mapC f(x:xs) = f x : (map f xs)
    mapC f xs = [f x | x <- xs]
--¡¡¡Si pones mapC f(x:xs) = [f x | x <- xs], te saltas el primer elemento!!!

    filterC :: (a->Bool) -> [a] -> [a]
    --filterC p [] = []
    --filterC p (x:xs) = if p x then x:(filter p xs) else (filter p xs)
    filterC p (x:xs) = [a|a <- xs, p a]


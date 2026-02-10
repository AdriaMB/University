module Excercises2 where

    type Person = String
    type Book = String
    type Database = [(Person, Book)]

    --Obtain the books borrowed by a person
    obtain :: Database -> Person -> [Book]
    obtain dBase thisPerson =
        [book | (person, book) <- dBase, person == thisPerson]

--EX 11
    --Registers when a person borrows a book
    borrow :: Database -> Book -> Person -> Database
    borrow dBase thisBook thisPerson = (thisPerson, thisBook) : dBase

    --Registers when a persona returns a book
    returns :: Database -> (Person, Book) -> Database
    returns dBase (thisPerson, thisBook) =

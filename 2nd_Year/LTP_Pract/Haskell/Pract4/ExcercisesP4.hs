module ExcercisesP4 where
    import Data.Char

    nextchar' :: Char -> Char
    nextchar' c = chr((ord c) + 1)

    numCbetw2 :: Char -> Char -> Int
    numCbetw2 a b
        | (ord a) == (ord b)    = 0
        | (ord a) > (ord b)     = numCbetw2 b a
        | otherwise             = (ord b) - (ord a) - 1

    addRange :: Int -> Int -> Int

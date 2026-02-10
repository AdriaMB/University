module Ex1 where

    --Return the position of an element within a list
    --
    --
    --
    position :: a -> [a] -> Int
    position a xs = if null posX then -1 else head posX
        where posX = [pos | (x, pos) <- zip xs [1..], x == a]

    --Computes the length of a path (list of points, coordinates)
    --type Point = (Float, Float)
    --type Path = [Point]
    --
    --Trick:
    --init [p,q,r,s] = [p,q,r]
    --tail [p,q,r,s] = [q,r,s]
    --zip... = [(p,q), (q,r), (r,s)]
    pathLength :: Path -> Float
    pathLength xs = sum'[distance p q | (p,q) <- zip(init xs)(tail xs)]

    sum'::[Float] -> Float
    sum' [] = 0
    sum' (x:xs) = x + sum' xs

    distance :: Point -> Point -> Float
    distance (p1,p2)(q1,q2) = sqrt(sqr (p1 - q1) + sqr(p2-q2))





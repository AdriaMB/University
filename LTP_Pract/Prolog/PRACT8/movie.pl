% movie(X,Y,Z) indicates that the film entitled X
%              was directed by Y in the year Z
movie("Casablanca", "Michael Curtiz", 1943).
movie("El hombre que pudo reinar", "John Huston", 1975).
movie("Apocalypse now", "F. Ford Coppola", 1979).
movie("El nombre de la rosa", "J.J. Annaudd", 1986).
movie("La burla del diablo", "John Huston", 1953).
movie("Wall Street", "Oliver Stone", 1987).
movie("El honor de los Prizzi", "John Huston", 1985).
movie("El sueÃ±o eterno", "Howard Hawks", 1946).
movie("Cayo Largo", "John Huston", 1948).

% cast(X,Y) indicates that the movie entitled X is cast
%           with the actors / actresses of the list Y
cast("El honor de los Prizzi",
     ["Jack Nicholson", "Anjelica Huston"]).
cast("Casablanca",
     ["Humphrey Bogart", "Ingrid Bergman"]).
cast("La burla del diablo",
     ["Humphrey Bogart", "Gina Lollobrigida"]).
cast("Apocalypse now",
     ["Marlon Brando", "Robert Duvall", "Martin Sheen"]).
cast("Wall Street",
     ["Michael Douglas", "Charlie Sheen", "Daryl Hannah", "Martin Sheen"]).
cast("El hombre que pudo reinar",
     ["Sean Connery", "Michael Caine"]).
cast("El nombre de la rosa",
     ["Sean Connery", "F. Murray Abraham"]).
cast("El sueÃ±o eterno",
	     ["Humphrey Bogart", "Lauren Bacall"]).
cast("Cayo Largo",
     ["Humphrey Bogart", "Edward G. Robinson", "Lauren Bacall", "Lionel Barrymore"]).

%%%%%%%%%%%%%

% Exercise 8

subset([],_).
subset([A|X],Y) :- member(A,Y), ???? .


%%%%%%%%%%%%%

% Exercise 9

subcast(M1,C1,M2,C2) :- ???? .


%%%%%%%%%%%%%

% Exercise 10

sorted([]).
sorted([_]).
????  :- X =< Y, sorted([Y|Ys]).


%%%%%%%%%%%%%

% Exercise 11

strings_sorted([]).
strings_sorted([_]).
????  :- X @< Y, strings_sorted([Y|Ys]).


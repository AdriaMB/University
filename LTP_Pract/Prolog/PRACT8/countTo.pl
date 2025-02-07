countTo(1,[1]).
countTo(2,[1,2]).
countTo(3,[1,2,3]).
countTo(4,[1,2,3,4]).

mymember(E, [E|_]).
mymember(E, [_|L]) :- mymember(E,L).

myappend([],L,L).
myappend([E|L1], L2, [X|L3]) :- X = E, myappend(L1,L2,L3).

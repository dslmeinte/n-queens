# n-queens

Java implementation of a solution for the [n-queens problem](https://en.wikipedia.org/wiki/Eight_queens_puzzle) -or rather: the (n, m)-queens problem in our case- using bit masks and unrolling (tail-)recursion for good performance in very limited memory.


## Contents

This repository contains an Eclipse project which should be importable into a recent Eclipse and any Java8.

The `com.dslconsultancy.nqueens.Chessboard` class exposes a static method `mapSolutions` which takes the following parameters:

* `n`: the number of queens to place (1 &leq; `n` &leq; m),
* `m`: the lengths of the sides of the board (1 &leq; `m` &leq; 31),
* `solutionFunction`: a function taking a `Chessboard` instance representing a solution to the (n, m)-queens problem which it is supposed to process immediately/synchronously on account of `Chessboard` being mutable (and stateful).
	To abort traversal of the solution space: have this function return `true`.


## Running the tests

The project contains two JUnit tests which can be run using the launch configuration in the `.launch` folder.
Running the `ChessboardTest` class should produce all greens and the following Console output (note that ordering may be different):

```
first solution for n = m = 11: [0, 2, 4, 6, 8, 10, 1, 3, 5, 7, 9]
first solution for n = m = 12: [0, 2, 4, 7, 9, 11, 5, 10, 1, 6, 8, 3]
first solution for n = m = 13: [0, 2, 4, 1, 8, 11, 9, 12, 3, 5, 7, 10, 6]
first solution for n = m = 14: [0, 2, 4, 6, 11, 9, 12, 3, 13, 8, 1, 5, 7, 10]
first solution for n = m = 15: [0, 2, 4, 1, 9, 11, 13, 3, 12, 8, 5, 14, 6, 10, 7]
first solution for n = m = 16: [0, 2, 4, 1, 12, 8, 13, 11, 14, 5, 15, 6, 3, 10, 7, 9]
first solution for n = m = 17: [0, 2, 4, 1, 7, 10, 14, 6, 15, 13, 16, 3, 5, 8, 11, 9, 12]
first solution for n = m = 18: [0, 2, 4, 1, 7, 14, 11, 15, 12, 16, 5, 17, 6, 3, 10, 8, 13, 9]
first solution for n = m = 19: [0, 2, 4, 1, 3, 8, 12, 14, 16, 18, 6, 15, 17, 10, 5, 7, 9, 11, 13]
first solution for n = m = 20: [0, 2, 4, 1, 3, 12, 14, 11, 17, 19, 16, 8, 15, 18, 7, 9, 6, 13, 5, 10]
first solution for n = m = 21: [0, 2, 4, 1, 3, 8, 10, 14, 20, 17, 19, 16, 18, 6, 11, 9, 7, 5, 13, 15, 12]
first solution for n = m = 22: [0, 2, 4, 1, 3, 9, 13, 16, 19, 12, 18, 21, 17, 7, 20, 11, 8, 5, 15, 6, 10, 14]
first solution for n = m = 23: [0, 2, 4, 1, 3, 8, 10, 12, 17, 19, 21, 18, 20, 9, 7, 5, 22, 6, 15, 11, 14, 16, 13]
first solution for n = m = 24: [0, 2, 4, 1, 3, 8, 10, 13, 17, 21, 18, 22, 19, 23, 9, 20, 5, 7, 11, 15, 12, 6, 16, 14]
first solution for n = m = 25: [0, 2, 4, 1, 3, 8, 10, 12, 14, 18, 20, 23, 19, 24, 22, 5, 7, 9, 6, 13, 15, 17, 11, 16, 21]
first solution for n = m = 26: [0, 2, 4, 1, 3, 8, 10, 12, 14, 20, 22, 24, 19, 21, 23, 25, 9, 6, 15, 11, 7, 5, 17, 13, 18, 16]
first solution for n = m = 27: [0, 2, 4, 1, 3, 8, 10, 12, 14, 16, 18, 22, 24, 26, 23, 25, 5, 9, 6, 15, 7, 11, 13, 20, 17, 19, 21]
first solution for n = m = 28: [0, 2, 4, 1, 3, 8, 10, 12, 14, 16, 22, 24, 21, 27, 25, 23, 26, 6, 11, 15, 17, 7, 9, 13, 19, 5, 20, 18]
first solution for n = m = 29: [0, 2, 4, 1, 3, 8, 10, 12, 14, 5, 19, 23, 25, 20, 28, 26, 24, 27, 7, 11, 6, 15, 9, 16, 21, 13, 17, 22, 18]
first solution for n = m = 30: [0, 2, 4, 1, 3, 8, 10, 12, 14, 6, 22, 25, 27, 24, 21, 23, 29, 26, 28, 15, 11, 9, 7, 5, 17, 19, 16, 13, 20, 18]
first solution for n = m = 31: [0, 2, 4, 1, 3, 8, 10, 12, 14, 5, 17, 22, 25, 27, 30, 24, 26, 29, 6, 16, 28, 13, 9, 7, 19, 11, 15, 18, 21, 23, 20]
n=1, m=1 => 1 solution in 3 iterations
n=1, m=2 => 2 solutions in 5 iterations
n=2, m=2 => 0 solutions in 5 iterations
n=2, m=3 => 2 solutions in 11 iterations
n=3, m=3 => 0 solutions in 11 iterations
n=5, m=5 => 10 solutions in 107 iterations
n=6, m=6 => 4 solutions in 305 iterations
n=7, m=7 => 40 solutions in 1103 iterations
n=8, m=8 => 92 solutions in 4113 iterations
n=9, m=9 => 352 solutions in 16787 iterations
n=10, m=10 => 724 solutions in 71077 iterations
```

As you can see, the solution algorithm performs better (for sufficiently large n, m) than the naieve brute force method, which would test m^n configurations.


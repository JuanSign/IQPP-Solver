# IQ Puzzler Pro Solver (Brute Force)

This repository contains a **Java-based brute force solver** for the **IQ Puzzler Pro** game. The program systematically tries all possible placements of the puzzle pieces to find a valid solution.

## Features
- **Brute Force Algorithm:** Iterates through all possible piece placements until a valid solution is found.
- **Optimized Pruning:** Reduces unnecessary calculations by eliminating impossible placements early.
- **Command-Line Interface (CLI):** Runs directly from the terminal.

## Requirements
- **Java 11 or higher**

## Installation
Clone the repository and navigate to the project directory:
```sh
git clone https://github.com/JuanSign/IQPP-Solver.git
cd IQPP-Solver
```

## Compilation & Execution
### Using Java Compiler
```sh
javac -d bin src/*.java
java -cp bin Main
```

## Roadmap
- Implement **heuristic pruning** to speed up brute force searches.
- Add **GUI visualization** for better interaction.
- Support **custom puzzle configurations** via JSON.
- Support **pyramid configurations**.

-J.S.A.S


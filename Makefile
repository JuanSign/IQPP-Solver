ifdef ComSpec
    OS = Windows
else
    OS = Unix
endif

all: compile run

compile:
ifeq ($(OS), Windows)
	@if not exist bin mkdir bin
	javac -d bin src/Main.java src/utils/State.java src/structs/Piece.java
else
	mkdir -p bin
	javac -d bin src/Main.java src/utils/State.java src/structs/Piece.java
endif

run:
ifeq ($(OS), Windows)
	@java -cp bin main.Main
else
	java -cp bin main.Main
endif

clean:
ifeq ($(OS), Windows)
	@if exist bin rmdir /s /q bin
else
	rm -rf bin
endif

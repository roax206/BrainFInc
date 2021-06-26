# BrainFunc
An interpreter for a language called BrainFunc based on the esoteric BrainFuck programming language in order to implement code reuse (ie functions) as well as conditional branching (if statements).
See design spec for language instructions(both those taken from the original BrainFuck language as well as those added in the derivative language).
The main component of the interpreter is the BrainFuncInterpreter class which implements the actual language and reads the BrainFunc code files (using the fileParser) where the Run class is used to give a executable program that can be run from the terminal(ie command prompt/IDE).

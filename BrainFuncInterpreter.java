import java.io.PrintStream;

import java.util.Scanner;

import java.io.EOFException;
import java.io.IOException;

import fileParser.ver1.CharParser;

public class BrainFuncInterpreter
{
	//memory
	private char[] memory;
	private int ptr = 0;

	//IO
	private Scanner input = new Scanner(System.in);
	private PrintStream output = new PrintStream(System.out);

	private boolean exceptionOnError = true;

	private String inputBuffer = "";
	int inputIndex = 0;
	
	public BrainFuncInterpreter()
	{
		memory = new char[256];
		input.useDelimiter("");
	}
	
	public BrainFuncInterpreter(int memSize, Scanner input, PrintStream output, boolean exceptionOnError)
	{
		this.exceptionOnError = exceptionOnError;
		//check that the given memory size is valid.
		if(memSize > 0)
		{
			memory = new char[memSize];
		}
		else if(exceptionOnError)
		{
			throw new RuntimeException("Interpreter: invalid memory size: " + memSize + " given. memory size must be a positive integer");
		}
		else
		{
			System.out.println("Interpreter: invalid memory size: " + memSize + " given. memory size must be a positive integer");
			memory = new char[256];
			System.out.println("memory size set to 256");
		}
		
		//check that the given input is valid.
		if(input != null)
		{
			this.input = input;
		}
		else if(exceptionOnError)
		{
			throw new RuntimeException("Interpreter: null input provided");
		}
		else
		{
			System.out.println("Interpreter: null input provided, using system's default input");
		}
			
		//check that the given output is valid.
		if(output != null)
		{
			this.output = output;
		}
		else if(exceptionOnError)
		{
			throw new RuntimeException("Interpreter: null output provided");
		}
		else
		{
			System.out.println("Interpreter: null output provided, using system's default output");
		}

		input.useDelimiter("");
	}
	
	public void process(String src) throws IOException
	{
		CharParser stream = new CharParser(src);
		boolean running = true;
		while(running)
		{
			try
			{
				switch(stream.getNextChar())
				{
					//INSTRUCTION PAIR: pointer movement:
					case '<':
					--ptr;
					break;
			
					case '>':
					++ptr;
					break;
				
				
				
					//INSTRUCTION PAIR: memory modification
					case '+':
					++memory[ptr];
					break;
				
					case '-':
					--memory[ptr];
					break;
				
				
				
					//INSTRUCTION PAIR: input/output
					case ',':
					if(inputIndex >= inputBuffer.length())
					{
						inputBuffer = input.next();
						inputIndex = 0;
					}
					memory[ptr] = (char)inputBuffer.charAt(inputIndex);
					++inputIndex;
					break;
				
					case '.':
					output.append(memory[ptr]);
					break;
				
				
				
					//INSTRUCTION PAIR: looping
					case '[':
					//implement loop start
					stream.setReturnPoint();
					break;
				
					case ']':
					//implement loop end
					if(memory[ptr] == 0)
					{
						stream.dropLastPoint();
					}
					else
					{
						stream.returnToLastPoint();
					}
					break;

					//INSTRUCTION PAIR: function(include)
					case '{':
					// '}' symbol also handled by this case.
					try
					{
						//generate the file name of the imported file by appending characters until a '}' is found.
						StringBuffer fileName = new StringBuffer();
						char fileNameChar = stream.getNextChar();
						while(fileNameChar != '}')
						{
							fileName.append(fileNameChar);
							fileNameChar = stream.getNextChar();
						}

						//call this method on the generated file name
						process(fileName.toString());
					}
					catch(IOException e)
					{
						//if an IOException occurs (ie imported file cannot be read or end of file is found before '}')
						//return exception or notify system.out then ignore import depending on whether exceptionOnError is true.
						if(exceptionOnError)
						{
							throw new RuntimeException("interpreter: import file could not be read");
						}
						else
						{
							System.out.println("interpreter: import file could not be read, ignoring import");
						}
					}
					break;

					case '?':
					if(memory[ptr] == 0)
					{
						while(stream.getNextChar() != ';')
						{
							//loop condition iterates through source code until ';' is found.
						}
					}
				}
			}
			catch(EOFException e)
			{
				running = false;
			}
		}
	}

	public void printMemory()
	{
		System.out.print("[");
		System.out.print(memory);
		System.out.println("]");
	}
}
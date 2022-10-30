import java.io.File;
import java.io.PrintStream;
import java.io.IOException;

import java.util.Scanner;

public class Run
{
	public static Scanner input = new Scanner(System.in);
	public static PrintStream output = System.out;
	
	public static void main(String[] args) throws IOException
	{
		String src = null;
		
		if(args.length > 0 && args.length <=3)
		{
			try{
				switch(args.length)
				{
					case 3:
					output = new PrintStream(args[2]);
					case 2:
					input = new Scanner(new File(args[1]));
					case 1:
					src = args[0];
				}
			}
			catch(IOException e)
			{
				//the program will check for null 
			}
			
			if(src == null)
			{
				System.out.println("source file: \"" + args[0] + "\" could not be read");
				return;
			}
			else
			{
				BrainFuncInterpreter proc = new BrainFuncInterpreter(256, input, output, true);
				proc.process(src);
			}
		}
		else
		{
			System.out.println("no source file specified");
		}
	}
}
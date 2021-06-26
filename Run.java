import java.io.File;
import java.io.PrintStream;

import java.util.Scanner;

public class Run
{
	public Scanner input = null;
	public PrintStream output = null;
	public char[] memory = null;
	
	public static void main(String[] args)
	{
		char[] memory = new char[256]();
		Scanner src = null;
		
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
					src = new Scanner(new File(args[0]));
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
		}
		else
		{
			System.out.println("no source file specified");
		}
	}
}
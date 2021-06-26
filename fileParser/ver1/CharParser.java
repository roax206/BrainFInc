package fileParser.ver1;

import java.io.File;
import java.io.IOException;
import java.io.EOFException;

import java.util.Scanner;
import java.util.LinkedList;

public class CharParser
{
	private Scanner stream = null;
	private StringBuffer buffer = new StringBuffer();
	private int index = 0;
	private LinkedList<Integer> returnStack = new LinkedList<Integer>();

	public CharParser(File file) throws IOException
	{
		stream = new Scanner(file);
	}

	public CharParser(String fileName) throws IOException
	{
		stream = new Scanner(new File(fileName));
	}

	public char getNextChar() throws EOFException
	{
		//if the index trying to be read does not yet exist in the buffer, try to read in the next token
		if(index == buffer.length())
		{
			//if data need not be held, clear the buffer and reset the index.
			if(returnStack.size() < 1)
			{
				buffer.setLength(0);
				index = 0;
			}

			//if the file has another token, append it to the buffer, else throw an EOFException to signal the end of the file.
			if(stream.hasNext())
			{
				buffer.append(stream.next());
			}
			else
			{
				throw new EOFException("file contents exausted");
			}
		}

		//return the character at the current index and increment the index for the next iteration.
		return buffer.charAt(index++);
	}

	public void setReturnPoint()
	{
		//if we start recording (ie returnStack goes from being empty to containing a return point index)
		//clear the contents of the buffer before the current index and reset the index to 0.
		//this way the buffer should contain only the data after any active return point.
		if(returnStack.size() < 1)
		{
			String trimmedBuffer = buffer.substring(index);
			buffer.setLength(0);
			buffer.append(trimmedBuffer);
			index = 0;
		}

		//add the current index as a return point to the return point stack.
		returnStack.add(index);
	}

	public void dropLastPoint()
	{
		returnStack.removeLast();
	}

	public void returnToLastPoint()
	{
		index = returnStack.getLast();
	}

	public String toString()
	{
		return "Buffer:\"" + buffer.toString() 
			+ "\"\nIndex: " + index
			+ "\nReturn Points: " + returnStack.toString();
	}

	public void dispose()
	{
		stream.close();
	}
}
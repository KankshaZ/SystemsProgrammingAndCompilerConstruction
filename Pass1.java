import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;

public class Pass1{

	public static Boolean isMachineOp(String operation){
		//System.out.println("operation " + operation);
		if(operation.equals("L")||operation.equals("A")||operation.equals("ST"))
			return true;
		else
			return false;
	}

	public static void printMachineOp(String operation, Writer writer) throws IOException{
		writer.write(operation);
		writer.write("\t");
		writer.write("-");
		writer.write("\t");
		writer.write("4");
		writer.write("\t");
		writer.write("RX");
		writer.write("\n");
	}


	public static Boolean isPseudoOp(String operation){
		//System.out.println("operation " + operation);
		if(operation.equals("Start")||operation.equals("Using")||operation.equals("DC")||operation.equals("DS")||operation.equals("End"))
			return true;
		else
			return false;
	}

	public static void printPseudoOp(String operation, Writer writer) throws IOException{
		writer.write(operation);
		writer.write("\t");
		writer.write("-");
		writer.write("\t");
		writer.write("\n");
	}

	public static Boolean isSymbol(String operation){
		//System.out.println("operation " + operation);
		if(operation.equals("Prog")||operation.equals("Four")||operation.equals("Five")||operation.equals("Temp"))
			return true;
		else
			return false;
	}

	public static void printSymbol(String operation, int relativeAddress, Writer writer) throws IOException{
		writer.write(operation);
		writer.write("\t");
		writer.write(Integer.toString(relativeAddress));
		writer.write("\t");
		writer.write("4");
		writer.write("\t");
		writer.write("R");
		writer.write("\n");
	}


	public static Boolean isLiteral(String operation){
		//System.out.println("operation " + operation);
		if(operation.contains("="))
			return true;
		else
			return false;
	}

	public static void printLiteral(String operation, Writer writer) throws IOException{
		writer.write(operation.substring(operation.indexOf("=")+1, operation.length()));
		writer.write("\t");
		writer.write("-");
		writer.write("\t");
		writer.write("4");
		writer.write("\t");
		writer.write("R");
		writer.write("\n");
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		int relativeAddress=0;
		int count = 0;
		BufferedReader reader;
		PrintWriter writerMOT = new PrintWriter("MOT.txt", "UTF-8");
		PrintWriter writerPOT = new PrintWriter("POT.txt", "UTF-8");
		PrintWriter writerST = new PrintWriter("ST.txt", "UTF-8");
		PrintWriter writerLT = new PrintWriter("LT.txt", "UTF-8");
		try {
			reader = new BufferedReader(new FileReader("pass1.txt"));
			String line = reader.readLine();
			while (line != null) {
				count++;
				if(count==3)
					relativeAddress = 0;
				else if(count>3)
					relativeAddress+=4;
				String array1[] = line.split("\t");
		       	for (String temp: array1){
		       		if(isMachineOp(temp))
		       		{
		       			printMachineOp(temp, writerMOT);
		       		}
		       		else if(isPseudoOp(temp))
		       			printPseudoOp(temp, writerPOT);
		       		else if(isSymbol(temp))
		       			printSymbol(temp, relativeAddress, writerST);
		       		else if(isLiteral(temp))
		       			printLiteral(temp, writerLT);
		       	}
		       	
		       	line = reader.readLine();
			}
			reader.close();
			writerMOT.close();
			writerPOT.close();
			writerST.close();
			writerLT.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
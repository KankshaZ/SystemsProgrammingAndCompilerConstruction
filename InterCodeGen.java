import java.util.*;

public class InterCodeGen
{

	public static String replaceStuff(String equation, int i, String toReplace)
	{
		int midIndex = equation.indexOf(toReplace);
		/*System.out.println(midIndex);
		System.out.println(toReplace);*/
		String newEquation = "";
		String toFind = "";
		if(!(equation.charAt(midIndex-2)=='t')&&!(equation.charAt(midIndex+1)=='t'))
		{
			toFind = equation.substring(midIndex-1, midIndex+2);
			newEquation = equation.substring(0, midIndex-1) + "t" + Integer.toString(i) + equation.substring(midIndex+2, equation.length());
		}
		else if(equation.charAt(midIndex-2)=='t')
		{
			if(equation.charAt(midIndex+1)=='t')
			{
				toFind = equation.substring(midIndex-2, midIndex+3);
				newEquation = equation.substring(0, midIndex-2) + "t" + Integer.toString(i) + equation.substring(midIndex+3, equation.length());
			}
			else
			{
				toFind = equation.substring(midIndex-2, midIndex+2);
				newEquation = equation.substring(0, midIndex-2) + "t" + Integer.toString(i) + equation.substring(midIndex+2, equation.length());
			}
		}	
		else
		{
			toFind = equation.substring(midIndex-1, midIndex+3);
			newEquation = equation.substring(0, midIndex-1) + "t" + Integer.toString(i) + equation.substring(midIndex+3, equation.length());
		}
		System.out.println("t"+i + "=" + toFind);
		return newEquation;
	}

	public static void assignment(String equation)
	{
		int i = 1;
		while(equation.contains("^")||equation.contains("*")||equation.contains("/")||equation.contains("+")||equation.contains("-"))
		{
			if(equation.contains("^"))
			{
				String toReplace = "^";
				int midIndex = equation.lastIndexOf(toReplace);
				String toFind;
				String newEquation = "";
				if(!(equation.charAt(midIndex-2)=='t')&&!(equation.charAt(midIndex+1)=='t'))
				{
					toFind = equation.substring(midIndex-1, midIndex+2);
					newEquation = equation.substring(0, midIndex-1) + "t" + Integer.toString(i) + equation.substring(midIndex+2, equation.length());
				}
				else if(equation.charAt(midIndex-2)=='t')
				{
					if(equation.charAt(midIndex+1)=='t')
					{
						toFind = equation.substring(midIndex-2, midIndex+3);
						newEquation = equation.substring(0, midIndex-2) + "t" + Integer.toString(i) + equation.substring(midIndex+3, equation.length());
					}
					else
					{
						toFind = equation.substring(midIndex-2, midIndex+2);
						newEquation = equation.substring(0, midIndex-2) + "t" + Integer.toString(i) + equation.substring(midIndex+2, equation.length());
					}
				}	
				else
				{
					toFind = equation.substring(midIndex-1, midIndex+3);
					newEquation = equation.substring(0, midIndex-1) + "t" + Integer.toString(i) + equation.substring(midIndex+3, equation.length());
				}
				System.out.println("t"+i + "=" + toFind);
				equation = newEquation;
			}
			else if(equation.contains("*")||equation.contains("/"))
			{
				if(equation.contains("*")&&equation.contains("/"))
				{
					if(equation.indexOf("*")<equation.indexOf("/"))
					{
						String toReplace = "*";
						equation = replaceStuff(equation, i, toReplace);
					}
					else
					{
						String toReplace = "/";
						equation = replaceStuff(equation, i, toReplace);
					}
				}
				else if(equation.contains("*"))
				{
					String toReplace = "*";
					equation = replaceStuff(equation, i, toReplace);
				}
				else
				{
					String toReplace = "/";
					equation = replaceStuff(equation, i, toReplace);
				}
			} 
			else
			{
				if(equation.contains("+")&&equation.contains("-"))
				{
					if(equation.indexOf("+")<equation.indexOf("-"))
					{
						String toReplace = "+";
						equation = replaceStuff(equation, i, toReplace);
					}
					else
					{
						String toReplace = "-";
						equation = replaceStuff(equation, i, toReplace);
					}
				}
				else if(equation.contains("+"))
				{
					String toReplace = "+";
					equation = replaceStuff(equation, i, toReplace);
				}
				else
				{
					String toReplace = "-";
					equation = replaceStuff(equation, i, toReplace);
				}
			}

			System.out.println(equation);
			i = i + 1;
			
		}	
	}

	public static int replaceBool(int index, String equation, int total)
	{
		String condition = equation.substring(0, 3);
		System.out.println(Integer.toString(index) + ": if " + condition + " go to " + Integer.toString(index + 3));
		index = index + 1;
		System.out.println(Integer.toString(index) + ": t" + Integer.toString(total) + "=1");
		index++;
		System.out.println(Integer.toString(index) + ": go to" + Integer.toString(index + 2));
		index++;
		System.out.println(Integer.toString(index) + ": t" + Integer.toString(total) + "=0");
		index++;
		return index;
	}


	public static void boolState(String equation)
	{
		int index = 100;
		int total = 0;
		int[] andCount = new int[5];
			for(int i=0; i<5; i++)
				andCount[i] = 0;
		while(equation.contains("&&")||equation.contains("||"))
		{			
			String operand = equation.substring(3, 5);
			if(operand.equals("&&"))
				andCount[total] = 1;
			total = total + 1;
			equation = equation.substring(5, equation.length());
			index = replaceBool(index, equation, total);
		}
		total = total + 1;
		index = replaceBool(index, equation, total);

		int totalConditions = total;
		for(int i=0; i<totalConditions-1; i++)
		{
			total = total + 1;
			if(andCount[i]==1)
				System.out.println(Integer.toString(index) + ": t" + Integer.toString(total) + " = t" + Integer.toString(i+1) + " and t" + Integer.toString(i+2));
			else
				System.out.println(Integer.toString(index) + ": t" + Integer.toString(total) + " = t" + Integer.toString(i+1) + " or t" + Integer.toString(i+2));		
			index++;
		}
	}

	public static void controlState(String[] equations, int number)
	{
		int index = 100;
		for(int i=0; i<number; i++)
		{
			String equation = equations[i];
			if(equation.equals("{"));
			else if(equation.contains("while"))
			{
				String condition = equation.substring(equation.indexOf("(")+1, equation.indexOf(")"));
				System.out.println("Lstart if " + condition + " go to Lstop");
			}
			else if(equation.equals("}"))
			{
				System.out.println(Integer.toString(index) + ": go to Lstart");
				System.out.println("Lstop");	
			}
			else
			{
				System.out.println(Integer.toString(index) + ": " + equation.substring(0, equation.length()-1));
				index++;
			}
		}
	}

	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the assignment equation.");
		String equation = sc.next();
		assignment(equation);
		System.out.println("Enter the boolean equation.");
		equation = sc.next();
		boolState(equation);
		System.out.println("Enter number of lines of the control statement. { and } on one line each.");
		int n = sc.nextInt();
		String[] control = new String[n];
		for(int i=0; i<n; i++)
		{
			control[i] = sc.next();
		}
		controlState(control, n);
	}
}

/*
OUTPUT:

Enter the assignment equation.
k=a-b^c^d/f
t1=c^d
k=a-b^t1/f
t2=b^t1
k=a-t2/f
t3=t2/f
k=a-t3
t4=a-t3
k=t4
Enter the boolean equation.
p<q&&r>s||u>v
100: if r>s go to 103
101: t1=1
102: go to104
103: t1=0
104: if u>v go to 107
105: t2=1
106: go to108
107: t2=0
108: if u>v go to 111
109: t3=1
110: go to112
111: t3=0
112: t4 = t1 and t2
113: t5 = t2 or t3
Enter number of lines of the control statement. { and } on one line each.
5
while(i<10)
{
x=10;
i=i+1;
}
Lstart if i<10 go to Lstop
100: x=10
101: i=i+1
102: go to Lstart
Lstop
*/
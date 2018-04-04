/**
 * Input all the productions singularily
 * E->E+T | F| a|episolon must be inputted as this ::
 * E->E+T
 * E->F
 * E->a
 * E->#
 *
 * # indicates epsillon ... 
 */


import java.util.*;
import java.io.*;

public class LeftFactoring{

	public static String [] productions;
	public static String temp = null;
	public static List<String> rules = null;

	public static void main(String[] args) {
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		int n;
		// n = Integer.parseInt(bfr.readLine());
		// productions = new String [] {"E->aaaA", "E->aaaAB", "E->aaB"};
		// productions = new String[] {"E->aaA", "E->aaAB", "E->a"};
		productions = new String[] {"E->aA", "E->aAB"};
		rules = new ArrayList<String>();
		for(String x : productions){
			rules.add(x.split("->")[1]);
		}
		int c= get_best_substring();
		System.out.println("best string " + temp);
		System.out.println("min length = "+c+ " string == " + temp.substring(0,c));
		factor_it(c);
	}

	private static int get_best_substring(){
		int i=0;
		getShortestString();
		String shortest_production =temp;
		while(i<shortest_production.length()){
			String check_letter = Character.toString(rules.get(0).charAt(i));
			// System.out.println(rules.size());
			for(int c = 1;c < rules.size();c++){
				System.out.println(c);
				if(!check_letter.equals(Character.toString(rules.get(c).charAt(i)))){
					System.out.println("break return ");
					return i;
				}
			}
			i++;
			
		}
		System.out.println("returning from fn end");
		return i;

	}

	private static void getShortestString(){
		int length = 1000;
		
		for(int i=0;i<rules.size();i++){
			String x = rules.get(i);
			if(x.length() < length){
				length = x.length();
				temp = x;
			}
		}
			
	}

	private static void factor_it(int lim){
		String [] remaining_letters = new String[productions.length];
		for(int i=0;i<rules.size();i++){
			String t = rules.get(i);
			remaining_letters[i] = t.substring(lim);
		}

		System.out.println("Left Factoring of " + Arrays.toString(productions));
		System.out.print("E->");
		System.out.print(temp.substring(0,lim)+"P\nP->");
		System.out.println(Arrays.toString(remaining_letters));

	}

}

/*OUTPUT:

1
1
returning from fn end
best string aA
min length = 2 string == aA
Left Factoring of [E->aA, E->aAB]
E->aAP
P->[, B]
*/
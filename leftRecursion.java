import java.util.*;
import java.io.*;

class leftRecursion{
	public static void main(String[] args)throws IOException {
		System.out.println("Enter the production");
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String x = bfr.readLine();
		String [] pr = x.split("->");
		String [] prods = pr[1].split("/");
		String root = pr[0];
		int [] flags = new int[prods.length];
		System.out.print(root + "->" );
		String dashOne = "P";
		for(int i=0;i<prods.length;i++){
			if(prods[i].indexOf(root) == 0){
				flags[i] = 1;
			}else{
				System.out.print(prods[i]+dashOne+"/");
			}
		}
		System.out.println();
		// now we apply left recursion
		System.out.print(dashOne + "->");
		for(int i=0;i<prods.length;i++){
			if(flags[i] == 1){
				System.out.print(prods[i].substring(1) + dashOne+"/");
			}
		}
		System.out.print("#");
		System.out.println();
	}
}

/*OUTPUT:

Enter the production
E->E+T/T
E->TP
P->+TP/#
*/
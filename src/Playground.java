import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Playground {
	
	public static void bestRoute(int[][] cost){
		int[] hops = new int[cost.length];
		int[] reward = new int[cost.length];
		
		for(int i=1; i<cost.length; ++i) {
			reward[i] = Integer.MIN_VALUE;
			for(int j=i-3; j<i; ++j) {
				if(j < 0) continue;
				if(cost[j][i] + reward[j] > reward[i]) {
					reward[i] = cost[j][i] + reward[j];
					hops[i] = i-j;
				}
			}
		}
		
		System.out.println("The best reward possible is $" + reward[reward.length-1]);
		System.out.println(Playground.print(hops, hops.length-1, hops.length));
	}
	
	private static String print(int[] arr, int index, int curr) {
		if(index == 0) return "vertex 1";
		return print(arr, index - arr[index], curr-arr[index]) + " -> vertex " + curr;
	}
	
	public static void main(String[] args) {
	
		//get input
		Scanner scan = new Scanner(System.in);
		System.out.print("Specify an input file: ");
		String in = scan.nextLine();
		scan.close();

		File fin = new File(in);
		FileInputStream fis = null;
		String line = null;
		int numTests;
		int[][] costs;
		ArrayList<int[][]> tests = new ArrayList<int[][]>();

		try {
			fis = new FileInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			
			if((line = br.readLine()) != null){
				numTests = Integer.parseInt(line);
			} else {
				br.close();
				throw new IllegalArgumentException("File input format is incorrect");
			}
			
			int j = 0;
			while(j < numTests) { //for every test...
				br.readLine(); //skip a line
				int i = 0;
				line = br.readLine();
				costs = new int[line.split(" ").length][line.split(" ").length];
				while((i < costs.length) && (line != null)) { //for every row in text
					for(int k=0; k<line.split(" ").length; ++k) { //for every column in row
						costs[i][k] = Integer.parseInt(line.split(" ")[k]);
					}
					line = br.readLine();
					++i;
				}
				tests.add(costs);
				++j;
			}
			
			br.close();
		} catch(FileNotFoundException e) {
			System.out.println("No file exists by that name!\nTerminating Program...");
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long before = System.currentTimeMillis();
		for(int i=0; i<tests.size(); ++i) {
			System.out.println("\nCase #" + (i+1) + ": \n");
			Playground.bestRoute(tests.get(i));
		}
		long after = System.currentTimeMillis();
		
		System.out.println("\nTotal Time: " + (after - before) + "ms");
	}
}
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Playground {
	
public static void bestRoute(LinkedList<LinkedList<Integer>> cost){
	int[] hops = new int[cost.size() + 1];
	int[] reward = new int[cost.size() + 1];
	
	for(int i=1; i<cost.size()+1; ++i) {
		reward[i] = Integer.MIN_VALUE;
		for(int j=0; j<3; ++j) {
			if(i-j < 1) continue;
			if(cost.get(i-1).get(j) + reward[i-j-1] > reward[i]) {
				reward[i] = cost.get(i-1).get(j) + reward[i-j-1];
				hops[i] = j+1;
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
		
		ArrayList<LinkedList<LinkedList<Integer>>> costs = new ArrayList<LinkedList<LinkedList<Integer>>>();
		int numTests = 0,
			numVerticies = 0;
		File fin = new File(in);
		FileInputStream fis = null;
		String line = null;

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
			while(j < numTests) {
				costs.add(new LinkedList<LinkedList<Integer>>());
				numVerticies = Integer.parseInt(br.readLine());
				int i = 0;
				while((i < numVerticies-1) && ((line = br.readLine()) != null)) {
					costs.get(j).add(new LinkedList<Integer>());
					for(String item : line.split(" ")) {
						costs.get(j).get(i).add(Integer.parseInt(item));
					}
					++i;
				}
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
		for(int i=0; i<costs.size(); ++i) {
			System.out.println("\nCase #" + (i+1) + ": \n");
			Playground.bestRoute(costs.get(i));
		}
		long after = System.currentTimeMillis();
		
		System.out.println("\nTotal Time: " + (after - before) + "ms");
	}
}
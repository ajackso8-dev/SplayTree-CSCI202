package edu.unca.csci202;

import java.util.Iterator;
import java.util.Random;

/**
 * @author Brian Drawert
 * @date Nov 16th, 2020
 * 
 */
public class Main {


	public static void main(String[] args) {		
		
		// Test the LinkedBST implement 
		BinarySearchTreeADT<Integer> bst = new SplayTree<Integer>();
		
		// insert numbers
		//int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		int[] numbers = {8,4,2,1,3,6,5,7,12,10,9,11,14,13,15};
		for(int i=0;i<numbers.length;i++) {
			System.out.println("_____________________________________________________________");
			System.out.println("inserting "+ numbers[i]+" and printing tree:");
			bst.insert(numbers[i]);
			System.out.println(bst);
		}
		
		// test methods
		System.out.println("_____________________________________________________________");
		System.out.println("height:"+bst.height());
		System.out.println("InOrder Traversal:");
		Iterator<Integer> itr = bst.iteratorInOrder();
		while(itr.hasNext()) {
			System.out.print(itr.next() + ", ");
		}
		System.out.println();
		System.out.println("_____________________________________________________________");
		System.out.println("max: "+bst.maximum());
		System.out.println(bst);
		System.out.println("height:"+bst.height());
		System.out.println("_____________________________________________________________");
		System.out.println("min: "+bst.minimum());
		System.out.println(bst);
		System.out.println("height:"+bst.height());


		

		
		// 
		int[] numbers2 = {7,11,3,9,5,13,8,10,4,12,6,1,15,4,2,14};
		for(int i=0;i<numbers2.length;i++) {
			System.out.println("_____________________________________________________________");
			System.out.println("find "+ numbers2[i]+":" + bst.find(numbers2[i]));
			System.out.println(bst);
		}
		
		//System.exit(0);
		
		
		/*int[] numbers2 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for(int i=0;i<numbers2.length;i++) {
			System.out.println("_____________________________________________________________");
			System.out.println("find "+ numbers2[i]+":" + bst.find(numbers2[i]));
			System.out.println("_____________________________________________________________");
			System.out.println("deleting "+ numbers2[i]+" and printing tree:");
			bst.delete(numbers2[i]);
			System.out.println(bst);
			System.out.println("_____________________________________________________________");
			System.out.println("contains "+ numbers2[i]+":" + bst.contains(numbers2[i]));
		}*/
		Random rnd = new Random();
		while(!bst.isEmpty()) {
			System.out.println("_____________________________________________________________");
			int min = bst.minimum();
			System.out.println("min="+min);
			//System.out.println(bst);System.out.println("========");
			
			int max = bst.maximum();
			System.out.println("max="+max);
			//System.out.println(bst);System.out.println("========");
			
			int x = rnd.nextInt(max-min+1) + min;
			System.out.println("deleting "+ x+" and printing tree:");
			bst.delete(x);
			System.out.println(bst);
		}
			
		
	
		System.exit(0);
		
	
	}
}
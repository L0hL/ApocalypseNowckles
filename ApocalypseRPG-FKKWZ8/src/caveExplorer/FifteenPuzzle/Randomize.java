package CaveExplorer.FifteenPuzzles;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
public class Randomize {
	private static final String _ = null;
	public static void main(String[] args) {
		String[] [] arr = { 
				{"1", "2", "3", "4"} , 
				{ "5", "6", "7","8"},
				{ "9","10","11","12",},
				{"13", "14", "15", "_"},
				};
		System.out.println("You win the game if you can make the board look like"
				+ " this.");
		printArray(arr);
		System.out.println("Here is the randomized puzzle."
				);
		solution(arr);
		printArray(arr);
		
	}
		
		
		
		
		private static void shuffleArray(String[][] arr) {		
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					int shuffleRow = (int)(Math.random()*arr.length);
					int shuffleCol = (int)(Math.random()*arr[i].length);
					String holder = arr[i][j];
					arr[i][j] = arr[shuffleRow][shuffleCol];
					arr[shuffleRow][shuffleCol] = holder;
				}
				
			}
		
	}
	private static void solution(String[][] a){
		shuffleArray(a);
		int positionX = 0;
		int positionY = 0;
		String[][]placer = a;
		
		
		
		
		for (int row = 0; row < placer.length; row++) {
			for (int col = 0; col < placer[row].length; col++) {
				if(placer[row][col].equals("_")){
					placer[row][col] = "16";
					positionX =row;
					positionY = col;
				}
			}
		
		}
		String[] container = new String [16];
		int count = 0;
		for (int row = 0; row < placer.length; row++) {
			
			for (int col = 0; col < placer[row].length; col++) {
				container[count] = placer[row][col];
				count++;
			}
		}
		
		
		int[] intArr = new int [container.length];
		for (int i = 0; i < intArr.length; i++) {
			intArr[i] = Integer.parseInt(container[i]);
		}
		int inversions = 0;
		for (int i = 0; i < intArr.length - 1; i++){
		    int tempLowIndex = i;
		    for (int j = i + 1; j < intArr.length; j++){
		        if (intArr[j] <intArr[tempLowIndex]){
		            tempLowIndex = j;
		        }
		    }
		   if(tempLowIndex!=i){
		         swap(intArr, tempLowIndex, i);
		        inversions++;
		   } 
		   }
		if(((positionX+2)%2 == 0 && inversions%2 ==0) ||((positionY+2)%2 ==1 && inversions%2 ==1)  ){
			solution(a);
		}
		//
		
		//a[positionX][positionY] = _;
	}
	
	private static void swap(int[] arr, int idx1, int idx2) {
		int temp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = temp;
		
	}
	
	
	public static void printArray(String[][] arr){
		for (int row = 0; row < arr.length; row++) {
			for (int col = 0; col < arr[row].length; col++) {
				System.out.print(arr[row][col] + " ");
				
			}
			System.out.println();
		}
	}
		// Checks to see if container is working.
	public static void printArr(int ar[]){
		for (int i = 0; i < ar.length; i++) {
			System.out.print(ar[i] + " ");
		}
	System.out.println();
	}
	
}

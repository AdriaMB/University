import java.util.*;
import java.io.*;

public class HM{
    public static void main(String[]args){
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        String[] characters = input.split("\\+");		//Redex pattern for the + symbol
		
		int[] numbers = new int[characters.length];
		for(int i = 0; i < numbers.length; i++){
			
			numbers[i] = Integer.parseInt(characters[i]);
		}
		
		//MergeSort for sorting the array
		mergeSort(numbers);
		String res = "";
		for(int i = 0; i < numbers.length-1; i++){
			res += numbers[i] + "+";
		}
		res+=numbers[numbers.length-1];
		System.out.println(res);
		

    }
	
	static void mergeSort(int v[]){
		mergeSort(v, 0, v.length -1);		//method that we will call
	}
	
	static void mergeSort(int v[], int start, int end){
		if(start < end){
				int half = (start + end) / 2;
				mergeSort(v, start, half);
				mergeSort(v, half +1, end);
				naturalMerge(v, start, half, end);
				
		}
	}
	
	static void naturalMerge(int v[], int start, int half, int end){
		int aux[] = new int[end-start+1];
		int a = start, b = half+1, c = 0;
		while(a <= half && b <= end){
			if(v[a] < v[b]) aux[c++] = v[a++];
			else			aux[c++] = v[b++];
			
		}
		while(a <= half) 	aux[c++] = v[a++];
		while(b <= end) 	aux[c++] = v[b++];
		
		for(int i=0; i <aux.length; i++){
			v[start+i] = aux[i];
			
		}
	}


}

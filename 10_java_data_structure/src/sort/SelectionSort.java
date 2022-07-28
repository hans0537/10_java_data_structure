package sort;

public class SelectionSort {
	
	private static void selectionSort(int[] arr) {
		selectionSort(arr, 0);
	}

	private static void selectionSort(int[] arr, int start) {
		if(start < arr.length - 1) {
			int min_idx = start;
			for (int i = start; i < arr.length; i++) {
				if(arr[i] < arr[min_idx]) min_idx = i;
			}
			int tmp = arr[start];
			arr[start] = arr[min_idx];
			arr[min_idx] = tmp;
			selectionSort(arr, start + 1);
		}
	}
	
	private static void printArray(int[] arr) {
		for(int data:arr) {
			System.out.print(data + ", ");
		}
		System.out.println();
	}
	 
	public static void main(String[] args) {
		int[] arr = {3,9,4,7,5,0,1,6,8,2};
		printArray(arr);
		selectionSort(arr);
		printArray(arr);
	}
}

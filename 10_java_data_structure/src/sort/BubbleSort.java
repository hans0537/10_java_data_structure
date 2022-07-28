package sort;

public class BubbleSort {
	private static void bubbleSort(int[] arr) {
		bubbleSort(arr, arr.length - 1);
	}
	
	private static void bubbleSort(int[] arr, int last) {
		if(last > 0) {
			for (int i = 1; i <= last; i++) {
				if(arr[i - 1] > arr[i]) {
					int tmp = arr[i];
					arr[i] = arr[i-1];
					arr[i-1] = tmp;
				}
			}
			bubbleSort(arr, last-1);
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
		bubbleSort(arr);
		printArray(arr);
	}
}

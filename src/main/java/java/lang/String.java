import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        sc.close();
    }

    private static void sort(int[] arr) {
        heap(arr);
        for (int i = arr.length - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            makeMaxHeap(arr, 0, i);
        }
    }

    private static void makeMaxHeap(int arr[], int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left >= n) {
            return;
        }
        int min = left;
        if (right >= n) {
            min = left;
        } else {
            if (arr[left] > arr[right]) {
                min = right;
            }
        }
        if (arr[i] < arr[min]) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[min];
        arr[min] = temp;
        makeMaxHeap(arr, min, n);
    }

    private static void heap(int[] arr) {
        int n = arr.length;
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            makeMaxHeap(arr, i, n);
        }
    }
}
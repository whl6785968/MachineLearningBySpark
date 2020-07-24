package Leetcode.ArrayDemo;

public class BinarySearch {
    public int binarySearch(int[] arr,int n,int target){
        int l = 0,r = n - 1;
        int mid = -1;
        while (l <= r){
            mid = (l+r) / 2;
            if(arr[mid] == target){
                return mid;
            }
            else if(arr[mid] < target){
                l = mid + 1;
            }
            else{
                r = mid - 1;
            }
        }

        return mid;
    }

    public int binarySearch(int[] arr,int target,int l,int r){
        if(l > r){
            return -1;
        }

        int mid = l + (r-l)/2;

        if(arr[mid] == target){
            return mid;
        }
        else if(arr[mid] < target){
            return binarySearch(arr,target,mid+1,r);
        }
        else{
            return binarySearch(arr,target,l,mid-1);
        }

    }

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        int[] arr = {1,3,5,6,8};
//        int i = binarySearch.binarySearch(arr, 5, 5);
//        System.out.println(i);
        int i = binarySearch.binarySearch(arr, 5, 0, 4);
        System.out.println(i);
    }
}

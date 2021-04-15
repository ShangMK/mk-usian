import java.util.Arrays;

public class Lunc {
    public static void main(String[] args) {
        int[] arr={3,5,7,9,6,84,2,5};
        int minsize = 0;
        int maxsize=0;
        for (int k = 0; k < arr.length/2; k++) {
           if (k+1+(arr.length-1)!=8){
               minsize=k;
               for (int i = k+1; i < arr.length; i++) {
                   minsize=arr[minsize]<arr[i]?minsize:i;

               }
               int temp = arr[k];
               arr[k]=arr[minsize];
               arr[minsize]=temp;
               maxsize = k;
               for (int i = k+1; i < arr.length; i++) {
                   maxsize = arr[maxsize] > arr[i] ? maxsize : i;
               }


               int max = arr[arr.length-(k+1)];
               arr[arr.length-(k+1)]=arr[maxsize];
               arr[maxsize]=max;
           }
        }
        System.out.println("最终排序"+Arrays.toString(arr));


/*        for (int k = 0; k < arr.length; k++) {
            maxsize = k;
            for (int i = k+1; i < arr.length; i++) {
                maxsize = arr[maxsize] > arr[i] ? maxsize : i;
            }
            int temp = arr[k];
            arr[k]=arr[maxsize];
            arr[maxsize]=temp;
        }*/
    }


}

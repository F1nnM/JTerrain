import java.util.concurrent.*;
public class Quicksort
{
    static ThreadPoolExecutor exec;
    public static void sort(Point3D[] points, int threads) {
       //exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
       subSort(points, 0, points.length - 1); 
    }

    static int partition(Point3D[] points, int left, int right) {
        int i = left, j = right;
        Point3D tmp, pivot = points[(left+right)/2];
        while(i<=j) {
            while(points[i].getX() < pivot.getX())
                i++;
            while(points[j].getX() > pivot.getX())
                j--;
            if(i<=j){
                tmp = points[i];
                points[i] = points [j];
                points[j] = tmp;
                i++;
                j--;
            }
        }
        return i;
    }
    
    static void subSort(Point3D[] points, final int left, final int right){
        /*exec.submit(new Runnable(){
            @Override
            public void run(){*/
                int index = partition(points, left, right);
                if(left < index -1) 
                    subSort(points, left, index-1);
                if(index < right)
                    subSort(points, index, right);
            }
       /* });
    }*/
}

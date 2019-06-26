import java.util.*;

public class SortedList {
    
    private LinkedList<Point3D> sortedPoints;
    
    public SortedList() {
        sortedPoints = new LinkedList<Point3D>();
    }
    
    public void orderedAdd(Point3D p) {
        ListIterator<Point3D> it = sortedPoints.listIterator();
        while(true){
            if(!it.hasNext()){
                it.add(p);
                return;
            }
            if(it.next().getX()>p.getX()){
                it.previous();
                it.add(p);
                return;
            }
        }
    }
    
    public ListIterator getIterator(){
        return sortedPoints.listIterator();
    }
}

package libraries.dataStructures.linear;


/**
 * Write a description of class SortedLinkedListPOI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SortedLinkedListPOI<E extends Comparable<E>> extends LinkedListPOI<E>
{
    public void add(E e){
        if(!this.isEmpty()){
            this.begin();
            while(!this.isEnd() && e.compareTo(this.get()) > 0){
                this.next();
            }
        }
        super.add(e);
    }
}

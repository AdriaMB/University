package applications.census;

import libraries.dataStructures.models.ListPOI;
import libraries.dataStructures.linear.LinkedListPOI;
import libraries.dataStructures.linear.SortedLinkedListPOI;

/**
 * VoterList: represents a list of residents registered in
 *            the census, and, thus, voters
 *
 * @author  EDA Professors
 * @version September 2023 (Translation Feb. 24)
 */

public class VoterList {

    private ListPOI<Resident> census;
    private int size;

    /**
     * Field getter methods
     */
    public ListPOI<Resident> getCensus() { return census; }
    public int getSize() { return size; }

    /**
     * Returns the String that represents a VoterList
     *
     * @return the String with the VoterList in the given textual format.
     */
    public String toString() {
        String res = "";
        if (size == 0) return res;
        census.begin();
        for (int pos = 0; pos <= census.size() - 2; pos++) {
            res += census.get() + ", \n";
            census.next();
        }
        res += census.get();
        return res;
    }

    /**
     * Creates a VoterList...
     *
     * @param sorted A boolean that shows whether the census must be
     *               sorted in ascending order (true) or not (false).
     * @param n     An int that shows the size (number of elements) of the list
     */
    public VoterList(boolean sorted, int n) {
        size = n;
        //census = sorted ? new SortedLinkedListPOI() : new LinkedListPOI();
        if (sorted) {census = new SortedLinkedListPOI();}
        
        else        {census = new LinkedListPOI();}
        
        int i = 0;
        while(i < n){
            Resident evil = new Resident();
            if(this.index(evil) == -1){
                census.add(evil);
                i++;
            }
        }
    }

    /**
     * Returns the index or position of Resident r in a VoterList,
     * or -1 if r doesn't belong to the list.
     *
     * @param r a Resident
     * @return r's index in a census, an int, 0 or positive
     *         if r is in the census, or -1 otherwise.
     */
    protected int index(Resident r) {
        census.begin();
        int i = 0;
        while(!census.isEnd()){
            if(census.get().compareTo(r) == 0) return i;
            else census.next(); i++;
        }
        return -1;
    }
    
    public int getNumOfRepeatedResidents(VoterList other){
        int counter = 0;
        other.census.begin();
        if(!this.census.isEmpty()){
            for(this.census.begin(); !this.census.isEnd(); this.census.next()){
                Resident aux = census.get();
                int index = other.index(aux);
                if(index != -1) counter++;
            }  
        }

        return counter;
    }
    
    public static void testNumRepeated()
    {
        VoterList l1 = new VoterList(true, 4);
        VoterList l2 = new VoterList(true, 0);
        VoterList l3 = new VoterList(true, 0);
        VoterList l4 = new VoterList(true, 0);
        VoterList l5 = new VoterList(true, 0);
        
        l1.getCensus().begin();
        l2.getCensus().add(l1.getCensus().get());
        l1.getCensus().next();
        l3.getCensus().add(l1.getCensus().get());
        l1.getCensus().next();
        l4.getCensus().add(l1.getCensus().get());
        l1.getCensus().next();
        l5.getCensus().add(l1.getCensus().get());
        
        boolean ok = 
            l1.getNumOfRepeatedResidents(l2) == 1 &&
            l1.getNumOfRepeatedResidents(l3) == 1 &&
            l1.getNumOfRepeatedResidents(l4) == 1 &&
            l1.getNumOfRepeatedResidents(l5) == 1;
            
        if (ok)
        {
            System.out.println("TEST PASSED: 'getNumRepeated' works correctly");
        }
        else
        {
            System.out.println("ERROR: 'getNumRepeated' does NOT work correctly");
        }        
    }
}

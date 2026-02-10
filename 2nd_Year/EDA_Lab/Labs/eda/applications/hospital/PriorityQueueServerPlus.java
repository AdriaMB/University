package applications.hospital;


/**
 * Write a description of class PriorityQueueServerPlus here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PriorityQueueServerPlus extends PriorityQueueServer implements SurgeryServerPlus
{
    private int size;
    
    public PriorityQueueServerPlus(){
        super();
        size = 0;
    }
    
    /** Obtains the number of patients currently waiting on this server. */
    public int numPatients(){
        return size;
    }
    
    
    /** Removes the next patient to be operated, so that it can be
     *  transferred to another server. */
    public Patient transferPatient(){
        if(this.hasPatients()){
            Patient aux = pQ.removeMin();
            size--;
            return aux;
        }
        return null;
    }

    /** Splits the patients between this and the given server s. */
    public void distributePatients(SurgeryServerPlus s){
        Patient theArray[] = new Patient[this.size];
        for(int i = 0; i < theArray.length; i++){
            theArray[i] = this.transferPatient();
        }
        for(int i = 0; i < theArray.length; i++){
            Patient p = theArray[i];
            if(i%2 == 0){
                this.addWaiting(p);
            }
            else{
                s.addWaiting(p);
            }
        }
    }
    
    @Override
    public Patient operatePatient(int h){
        Patient p = super.operatePatient(h);
        if(p != null){
            size--;
        }
        return p;
    }
    
    @Override
    public void addWaiting(Patient j){
        super.addWaiting(j);
        size++;
    }
}

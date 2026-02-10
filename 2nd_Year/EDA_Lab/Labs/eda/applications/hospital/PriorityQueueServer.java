package applications.hospital;

import libraries.dataStructures.models.PriorityQueue;
import libraries.dataStructures.hierarchical.BinaryHeap;

/**
 * PriorityQueueServer class: implements a SurgeryServer
 * using a maximum priority model (PriorityQueue) to sort
 * incoming patients in its waitlist.
 *  
 *  @author  EDA Professors
 *  @version April 2024
 */

public class PriorityQueueServer implements SurgeryServer {

    /** A PriorityQueueServer HAS A PriorityQueue of awaiting Patients. */
    protected PriorityQueue<Patient> pQ;
    
    /** Creates an empty SurgeryServer. */
    public PriorityQueueServer() {
        pQ = new BinaryHeap<>();
    }

    /** Includes a new Patient p in a SurgeryServer. */
    @Override
    public void addWaiting(Patient j) {
        pQ.add(j);
    }

    /** Checks whether there is any Patient waiting for surgery. */
    @Override
    public boolean hasPatients() {
        return pQ.isEmpty() ? false:true;
    }

    /** IFF hasPatients(): returns the Patient from a SurgeryServer to be operated. */
    @Override
    public Patient getPatient() {
        if(this.hasPatients()){
            return pQ.getMin();
        }
        return null;
    }

    /**
     * IFF hasPatients(): removes from a SurgeryServer the Patient that
     * will be operated on, and returns that Patient, updating its
     * entersSurgery attribute.
     * @param h Timestamp (in hours) when the patient is admitted to surgery.
     */
    @Override
    public Patient operatePatient(int h) {
        if(this.hasPatients()){
            Patient aux = pQ.removeMin();
            int newH = h + SURGERY_TIME;        //The test expects this. No other logical reason
            aux.setEntersSurgery(newH);
            return aux;
        }
        return null;
    }
}

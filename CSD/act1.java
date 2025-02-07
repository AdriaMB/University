public class T implements Runnable {
    protected int n;
    public T(int n) {this.n = n;}
    public void delay(int ms) {
        // suspends execution for ms milliseconds
        try {
            Thread.sleep(ms); //STATIC METHOD sleep()

        } catch (InterruptedException ie){ie.printStackTrace();}
    }
    public void run() {
        for (int i=0; i<10; i++) {
            System.out.println("Thread "+n +" iteration "+i);
            delay((n+1)*1000);
        }
        System.out.println("End of thread "+n);
    }
    public static void main(String[] argv) {
        System.out.println("--- Begin of execution ---- ");
        Thread[] vt = new Thread[6];
        for (int i=0; i<6; i++)
            vt[i] = new Thread(new T(i));
            vt[i].start();

        for (int i = 0; i<6; i++){

           //while(vt[i].isAlive());        BUSY WAITING

            try{
                vt[i].join();
            }
            catch(InterruptedException e){}
        }
        System.out.println("--- End of execution ---- ");
    }
}


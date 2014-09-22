/**
 * $Id: ATMThread.java, v 1.0 14/12/13 10:23 oscarfabra Exp $
 * {@code ATMThread} Class that extends Thread, used to execute client requests
 * on the server side.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 14/12/13
 */

package cscie55.hw6;

import java.util.PriorityQueue;

/**
 * Thread class used to execute client requests concurrently.
 */
public class ATMThread extends Thread
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Class variable that stores a reference to the Server's request queue.
     */
    private static PriorityQueue<ATMRunnable> requestQueue = null;

    /**
     * Variable stored for simulation purposes.
     */
    private int threadNumber;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMThread object given a reference to the Server's request
     * queue.
     * @param requestQueue Reference to the Server's request queue.
     * @param threadNumber int variable stored for simulation purposes.
     */
    public ATMThread(PriorityQueue<ATMRunnable> requestQueue, int threadNumber)
    {
        if(ATMThread.requestQueue == null)
        {
            ATMThread.requestQueue = requestQueue;
        }
        this.threadNumber = threadNumber;
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Waits for an ATMRunnable object to be added to the request queue, then
     * retrieves it from the list and executes the request contained in it.
     */
    @Override
    public void run()
    {
        try
        {
            synchronized (this)
            {
                // Wait while request queue is empty
                while(ATMThread.requestQueue.isEmpty())
                {
                    System.out.println("Waiting to execute a request...");
                    this.wait();
                }

                // Retrieves and removes the head of the request queue
                ATMRunnable atmRunnable = ATMThread.requestQueue.poll();

                // Executes the request contained in the request queue
                atmRunnable.run();

                this.notifyAll();
            }
        }
        catch(InterruptedException ie)
        { }
    }

    /**
     * Returns a message to show on standard output with the thread's number.
     *
     * @return String with a message indicating the thread's number.
     */
    public String toString()
    {
        return "Running request in thread: " + this.threadNumber;
    }
}

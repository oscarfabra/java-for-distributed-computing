/**
 * $Id: ATMListener.java, v 1.0 27/12/13 10:28 oscarfabra Exp $
 * {@code ATMListener} The ATMListener interface equips Clients to receive
 * TransactionNotification objects.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 27/12/13
 */

package cscie55.project;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Equips the Client to receive TransactionNotification messages from the ATM.
 */
public interface ATMListener extends Remote, Serializable
{
    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Receives a TransactionNotification from the ATM and prints it in the
     * standard output.
     *
     * @throws RemoteException If operation couldn't be made.
     */
    public void getTransactionNotification(TransactionNotification transaction)
            throws RemoteException;

    /**
     * Prints a TransactionNotification object.
     *
     * @param transaction TransactionNotification object to print.
     * @throws RemoteException If operation couldn't be made.
     */
    public void printTransactionNotification(TransactionNotification transaction)
            throws RemoteException;

    /**
     * Sets the listener's id from the ATM.
     *
     * @throws RemoteException If operation couldn't be made.
     */
    public void setListenerId(int listenerId) throws RemoteException;
}

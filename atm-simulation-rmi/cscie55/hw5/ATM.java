/**
 * $Id: ATM.java, v 1.0 5/12/13 10:28 oscarfabra Exp $
 * {@code ATM} The ATM interface to be used in the ATM distributed application
 * using Java RMI.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 5/12/13
 */

package cscie55.hw5;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ATM interface that contains the methods to be used between client and server
 * in the ATM distributed application using Java RMI.
 */
public interface ATM extends Remote, Serializable
{
    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Gets an account's balance.
     * @return Account's balance.
     * @throws RemoteException If balance is negative.
     */
    public Float getBalance(int account) throws RemoteException;

    /**
     * Deposits the given amount in the given account.
     * @param amount Amount to deposit.
     * @throws RemoteException If the given value is not valid.
     */
    public void deposit(int account, float amount) throws RemoteException;

    /**
     * Withdraws the given amount from the account.
     * @param amount Amount to withdraw.
     * @throws RemoteException If couldn't make the withdrawal.
     */
    public void withdraw(int account, float amount) throws RemoteException;
}

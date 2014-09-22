/**
 * $Id: ATM.java, v 1.0 26/12/13 10:28 oscarfabra Exp $
 * {@code ATM} The ATM interface to be used in the ATM distributed application
 * using Java RMI.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

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
     * Registers a new remote client to listen for TransactionNotification
     * objects remotely.
     *
     * @param listener Client as an ATMListener object.
     * @return Integer with a listener's id.
     * @throws RemoteException If operation couldn't be made successfully.
     */
    public int registerListener(ATMListener listener) throws RemoteException;

    /**
     * Notifies all listeners of the ATM about the transaction sending remote
     * messages to each of them.
     *
     * @throws RemoteException If operation couldn't be done successfully.
     */
    public void notifyListeners() throws RemoteException;

    /**
     * Inquires an account's balance.
     *
     * @param accountInfo Details of the account.
     * @return Balance of the account.
     * @throws RemoteException If balance couldn't be retrieved.
     */
    public Double getBalance(AccountInfo accountInfo) throws RemoteException;

    /**
     * Deposits an amount into a given account.
     *
     * @param accountInfo Details of the account.
     * @param amount Amount to deposit.
     * @throws RemoteException If deposit couldn't be made.
     */
    public void deposit(AccountInfo accountInfo, double amount)
            throws RemoteException;

    /**
     * Withdraws an amount from a given account.
     *
     * @param accountInfo Details of the account.
     * @param amount Amount to withdraw.
     * @throws RemoteException If withdrawal couldn't be made.
     */
    public void withdraw(AccountInfo accountInfo, double amount)
            throws RemoteException;

    /**
     * Withdraws the given amount from account1 and deposits it onto account2.
     *
     * @param accountInfo1 Source Account details to withdraw funds from.
     * @param accountInfo2 Destination Account details onto which deposit the funds.
     * @param amount Amount to transfer.
     * @throws RemoteException If the transaction couldn't be made.
     */
    public void transfer(AccountInfo accountInfo1, AccountInfo accountInfo2,
                         double amount) throws RemoteException;
}

/**
 * $Id: Security.java, v 1.0 27/12/13 10:28 oscarfabra Exp $
 * {@code Security} The Security class is a remote service that authenticates
 * AccountInfo objects and authorizes operations on individual accounts.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 27/12/13
 */

package cscie55.project;

import cscie55.project.exceptions.ATMSecurityException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface that has methods for authenticating AccountInfo objects and
 * for authorizing specific operations on individual accounts.
 * @see cscie55.project.Security
 */
public interface Security extends Remote, Serializable
{
    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Determines if a given AccountInfo object corresponds with an available
     * Bank account.
     *
     * @param accountInfo AccountInfo object to authenticate.
     * @return Whether it is authentic or not.
     * @throws ATMSecurityException If a security exception occurs.
     */
    public boolean authenticateAccount(AccountInfo accountInfo)
            throws RemoteException;

    /**
     * Determines if it should authorize a given transaction.
     *
     * @param transaction TransactionNotification to examine.
     * @return Whether the transaction is permitted or not.
     * @throws ATMSecurityException If a security exception occurs.
     */
    public boolean authorizeTransaction(TransactionNotification transaction)
            throws RemoteException;

    /**
     * Searches and returns an ATMAuthorization object by its corresponding
     * account number.
     *
     * @param accountNumber Account number of the authorization.
     * @return ATMAuthorization object.
     * @throws ATMSecurityException If search couldn't be done successfully.
     */
    public ATMAuthorization searchAuthorization(int accountNumber)
            throws RemoteException;
}

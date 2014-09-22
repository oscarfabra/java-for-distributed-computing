/**
 * $Id: Bank.java, v 1.0 26/12/13 10:28 oscarfabra Exp $
 * {@code Bank} The Bank interface to be used in the Bank distributed
 * application using Java RMI.
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
 * Bank interface that contains the methods that the Bank remote object is
 * capable of doing.
 */
public interface Bank extends Remote, Serializable
{
    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Performs a basic linear search for a bank account given an AccountInfo
     * object.
     *
     * @param accountInfo Object containing the account's number and pin.
     * @return Account object that corresponds with the given parameters.
     * @throws RemoteException If operation couldn't be done.
     */
    public Account getAccount(AccountInfo accountInfo) throws RemoteException;

    /**
     * Updates a given account.
     *
     * @param accountInfo Object containing the account's number and pin.
     * @param account Account object to set.
     * @throws RemoteException If operation couldn't be done.
     */
    public void setAccount(AccountInfo accountInfo, Account account)
            throws RemoteException;
}

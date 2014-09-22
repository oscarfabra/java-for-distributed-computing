/**
 * $Id: BankImpl.java, v 1.0 26/12/13 10:28 oscarfabra Exp $
 * {@code BankImpl} BankImpl implements the Bank interface on the server side
 * of our distributed application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import cscie55.project.exceptions.ATMException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

/**
 * BankImpl implements the Bank interface on the server side of our distributed
 * application.
 */
public class BankImpl extends UnicastRemoteObject implements Bank
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Collection of accounts.
     */
    private List<Account> accounts = null;

    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Creates a new BankImpl.
     */
    public BankImpl() throws RemoteException
    {
        if(accounts == null)
        {
            accounts = new Vector<Account>();

            // Adds 3 new accounts with their respective initial balance
            accounts.add(new Account(0000001, 1234, 0));
            accounts.add(new Account(0000002, 2345, 100));
            accounts.add(new Account(0000003, 3456, 500));
        }
    }

    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Performs a basic linear search for a bank account given an AccountInfo
     * object.
     *
     * @param accountInfo Object containing the account's number and pin to
     *                    search for.
     * @return Account object that corresponds with the given parameters.
     * @throws ATMException If operation couldn't be done successfully.
     */
    @Override
    public Account getAccount(AccountInfo accountInfo) throws RemoteException
    {
        Account account = null;
        boolean found = false;
        int numberOfAccounts = this.accounts.size();

        for (int i=0; i < numberOfAccounts; i++)
        {
            account = this.accounts.get(i);
            if(account.corresponds(accountInfo))
            {
                found = true;
                break;
            }
        }

        // Throws an exception if the account couldn't be found.
        if (!found)
        {
            throw new RemoteException("Given account couldn't be found.");
        }

        return account;
    }

    /**
     * Updates an account given its corresponding AccountInfo object and
     * the new Account object to store.
     *
     * @param accountInfo Object containing the account's number and pin.
     * @param account Account object to set.
     * @throws RemoteException If the operation couldn't be made.
     */
    @Override
    public void setAccount(AccountInfo accountInfo, Account account)
            throws RemoteException
    {
        Account tempAccount = null;
        boolean found = false;
        int numberOfAccounts = this.accounts.size();

        for (int i=0; i < numberOfAccounts; i++)
        {
            tempAccount = this.accounts.get(i);
            if(tempAccount.corresponds(accountInfo))
            {
                // Updates the account on the ppropriate position.
                this.accounts.set(i,account);
                found = true;
            }
        }

        // Throws aan exception if the account couldn't be found.
        if(!found)
        {
            throw new RemoteException("Couldn't update the account.");
        }
    }
}

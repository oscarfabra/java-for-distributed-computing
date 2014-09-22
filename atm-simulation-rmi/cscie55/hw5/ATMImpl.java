/**
 * $Id: ATMImpl.java, v 1.0 5/12/13 10:35 oscarfabra Exp $
 * {@code ATMImpl} Class that implements the ATM interface on the server side.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 5/12/13
 */

package cscie55.hw5;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * ATMImpl class that implements the ATM interface on the server side.
 */
public class ATMImpl extends UnicastRemoteObject implements ATM
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Array of client accounts.
     */
    private static ArrayList<Account> accounts = null;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMImpl with an ArrayList of accounts.
     */
    public ATMImpl() throws RemoteException
    {
        if(accounts == null)
        {
            accounts = new ArrayList<Account>();

            // Adds 3 new accounts with their respective initial balance
            accounts.add(0, new Account(0));
            accounts.add(1, new Account(100));
            accounts.add(2, new Account(500));
        }
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Returns the account's balance.
     *
     * @param account Account's id.
     * @return Float with the account's balance.
     * @throws RemoteException If the account's balance equals 0.
     */
    @Override
    public Float getBalance(int account) throws RemoteException
    {
        return accounts.get(account-1).getBalance();
    }

    /**
     * Adds the given amount to the account's balance.
     *
     * @param account Account's id.
     * @param amount Number to add to the account's balance.
     * @throws RemoteException If the given amount is not a valid amount.
     */
    @Override
    public void deposit(int account, float amount) throws RemoteException
    {
        accounts.get(account-1).deposit(amount);
    }

    /**
     * Subtracts the given amount from the account's balance.
     *
     * @param account Account's id.
     * @param amount Number to subtract from the account's balance.
     * @throws RemoteException If the given amount is bigger than the account's
     * balance.
     */
    @Override
    public void withdraw(int account, float amount) throws RemoteException
    {
        accounts.get(account-1).withdraw(amount);
    }
}

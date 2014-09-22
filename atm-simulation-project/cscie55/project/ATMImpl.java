/**
 * $Id: ATMImpl.java, v 1.0 26/12/13 10:35 oscarfabra Exp $
 * {@code ATMImpl} Class that implements the ATM interface on the server side.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import cscie55.project.commands.Commands;
import cscie55.project.exceptions.ATMException;
import cscie55.project.exceptions.ATMInsufficientFundsException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

/**
 * ATMImpl class that implements the ATM interface on the server side.
 */
public class ATMImpl extends UnicastRemoteObject implements ATM
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Class variable that contains the ATM's available cash to be withdrawn.
     */
    private double cashOnHand = 500;

    /**
     * Class variable that stores the current transaction notification that is
     * being executed.
     */
    private TransactionNotification currentTransaction;

    /**
     * Variable to store a reference to the application's Bank object.
     */
    private final Bank bank;

    /**
     * Variable to store a reference to the Security service object.
     */
    private final Security security;

    /**
     * Collection of clients who are listening to this ATM implementation for
     * TransactionNotification objects.
     */
    private List<ATMListener> listeners;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMImpl object.
     *
     * @param bank Reference to the Bank remote object.
     * @param security Reference to the Security remote object.
     * @throws RemoteException If the creation couldn't be made successfully.
     */
    public ATMImpl(Bank bank, Security security) throws RemoteException
    {
        super();
        // Creates a default initial transaction to avoid a NullPointerException
        this.currentTransaction = new TransactionNotification(Commands.INQUIRE,
                        0, new AccountInfo(0,0));
        this.bank = bank;
        this.security = security;
        this.listeners = new Vector<ATMListener>();
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Registers a new remote client with the registry to listen for
     * TransactionNotification objects remotely.
     *
     * @param listener Client as an ATMListener object.
     * @throws RemoteException If operation couldn't be made successfully.
     */
    @Override
    public synchronized int registerListener(ATMListener listener)
            throws RemoteException
    {
        try
        {
            LocateRegistry.createRegistry(1099);
            System.out.println("About to register client...");
        }
        catch(Exception ex)
        {
            System.out.println("Java RMI registry not found.");
        }

        int id = 0;

        try
        {
            id = this.listeners.size() + 1;
            // Bind this object instance to the name "listener_id" where id is
            // an integer to uniquely identify the client in the atm
            Naming.rebind("//localhost/listener_" + id, listener);

            System.out.println("listener_" + id + " bound in registry.");
        }
        catch (Exception ex)
        {
            System.out.println("ATMImpl error: " + ex.getMessage());
            ex.printStackTrace();
        }
        this.listeners.add(listener);

        // Returns the id to the client
        return id;
    }

    /**
     * Notifies all listeners of the ATM about the transaction.
     *
     * @throws RemoteException If operation couldn't be done successfully.
     */
    @Override
    public synchronized void notifyListeners() throws RemoteException
    {
        for(ATMListener listener : this.listeners)
        {
            listener.getTransactionNotification(this.currentTransaction);
        }
    }

    /**
     * Inquires an account's balance.
     *
     * @param accountInfo Details of the account.
     * @return Balance of the account.
     * @throws RemoteException If balance couldn't be retrieved.
     */
    @Override
    public synchronized Double getBalance(AccountInfo accountInfo)
            throws RemoteException
    {
        Account account = null;
        try
        {
            // Populates the currentTransaction class variable
            this.currentTransaction = new TransactionNotification(Commands.INQUIRE,
                    0,accountInfo);

            // Notifies all registered listeners about the transaction
            this.notifyListeners();

            if(this.security.authenticateAccount(accountInfo) &&
                    (this.security.authorizeTransaction(currentTransaction)))
            {
                account = this.bank.getAccount(accountInfo);
            }
        }
        catch (Exception e)
        {}

        if(account == null)
        {
            throw new ATMException("Balance couldn't be retrieved.");
        }

        double balance = account.getBalance();

        // Advices threads to acquire the monitor of the object
        this.notifyAll();

        return balance;
    }

    /**
     * Deposits an amount into a given account.
     *
     * @param accountInfo Details of the account.
     * @param amount Amount to deposit.
     * @throws RemoteException If deposit couldn't be made.
     */
    @Override
    public synchronized void deposit(AccountInfo accountInfo, double amount)
            throws RemoteException
    {
        Account account = null;
        try
        {
            // Populates the currentTransaction class variable
            this.currentTransaction = new TransactionNotification(Commands.DEPOSIT,
                    amount,accountInfo);

            // Notifies all registered listeners about the transaction
            this.notifyListeners();

            if(this.security.authenticateAccount(accountInfo) &&
                    (this.security.authorizeTransaction(currentTransaction)))
            {
                account = this.bank.getAccount(accountInfo);
            }
        }
        catch (Exception e)
        {}

        if(account == null)
        {
            throw new ATMException("Deposit couldn't be made.");
        }

        // Performs the deposit into the corresponding account
        account.addAmount(amount);

        // Updates the account in the Bank
        this.bank.setAccount(accountInfo, account);

        // Advices threads to acquire the monitor of the object
        this.notifyAll();
    }

    /**
     * Withdraws an amount from a given account.
     *
     * @param accountInfo Details of the account.
     * @param amount Amount to withdraw.
     * @throws RemoteException If withdrawal couldn't be made.
     */
    @Override
    public synchronized void withdraw(AccountInfo accountInfo, double amount)
            throws RemoteException
    {
        Account account = null;
        try
        {
            // Populates the currentTransaction class variable
            this.currentTransaction = new TransactionNotification(Commands.WITHDRAW,
                    amount,accountInfo);

            // Notifies all registered listeners about the transaction
            this.notifyListeners();

            // Checks if there's enough cash on hand to give to the user
            if(this.cashOnHand < amount)
            {
                throw new ATMInsufficientFundsException("ATM is out of cash.");
            }
            else
            {
                if(this.security.authenticateAccount(accountInfo) &&
                        (this.security.authorizeTransaction(currentTransaction)))
                {
                    account = this.bank.getAccount(accountInfo);
                }
            }
        }
        catch (Exception e)
        {}

        if(account == null)
        {
            throw new ATMException("Withdrawal couldn't be made.");
        }

        // Performs the withdrawal and gives the cash to the user
        account.subtractAmount(amount);
        this.cashOnHand-=amount;

        // Updates the account in the Bank
        this.bank.setAccount(accountInfo, account);

        // Advices threads to acquire the monitor of the object
        this.notifyAll();
    }

    /**
     * Withdraws the given amount from account1 and deposits it into account2.
     *
     * @param accountInfo1 Source Account number to withdraw funds from.
     * @param accountInfo2 Destination Account number onto which deposit the funds.
     * @param amount Amount to transfer.
     * @throws RemoteException If the transaction couldn't be made.
     */
    @Override
    public synchronized void transfer(AccountInfo accountInfo1,
                                      AccountInfo accountInfo2,
                                      double amount) throws RemoteException
    {
        Account account1 = null;
        Account account2 = null;

        try
        {
            // Populates the currentTransaction class variable
            this.currentTransaction = new TransactionNotification(Commands.TRANSFER,
                    amount, accountInfo1, accountInfo2);

            // Notifies all registered listeners about the transaction
            this.notifyListeners();

            if(this.security.authenticateAccount(accountInfo1) &&
                    this.security.authenticateAccount(accountInfo2) &&
                    (this.security.authorizeTransaction(currentTransaction)))
            {
                account1 = this.bank.getAccount(accountInfo1);
                account2 = this.bank.getAccount(accountInfo2);
            }
        }
        catch (Exception e)
        {}

        if(account1 == null)
        {
            throw new ATMException("Account 1 couldn't be retrieved.");
        }
        if(account2 == null)
        {
            throw new ATMException("Account 2 couldn't be retrieved.");
        }
        // Withdraws the given amount from account1 and deposits it
        // into account2
        account1.subtractAmount(amount);
        account2.addAmount(amount);

        // Updates account 1 in the Bank
        this.bank.setAccount(accountInfo1, account1);

        // Updates account 2 in the Bank
        this.bank.setAccount(accountInfo2, account2);

        // Advices threads to acquire the monitor of the object
        this.notifyAll();
    }
}

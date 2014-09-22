/**
 * $Id: Account.java, v 1.0 21/11/13 23:06 oscarfabra Exp $
 * {@code Account} Is a class that represents the account of one client.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 21/11/13
 * @see cscie55.hw6.ATMImplementation
 */


package cscie55.hw6;

import cscie55.hw6.exceptions.ATMException;

/**
 * Class that represents the account of one client.
 */
public class Account
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Contains the balance of the client.
     */
    private float balance;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new account with a starting balance of 0
     */
    public Account()
    {
        this.balance = 0;
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Returns the account's balance.
     *
     * @return Float number with the account's balance.
     * @throws ATMException If current balance is negative.
     */
    public synchronized float getBalance() throws ATMException
    {
        if(this.balance < 0)
        {
            throw new ATMException("No funds available.");
        }
        return this.balance;
    }

    /**
     * Adds a given amount to the account's balance.
     *
     * @param amount Amount to add to the balance.
     * @throws ATMException If the given amount is not valid.
     */
    public synchronized void deposit(float amount) throws ATMException
    {
        if(amount <= 0)
        {
            throw new ATMException("Invalid amount given.");
        }
        this.balance += amount;
    }

    /**
     * Withdraws a given amount from the account's balance.
     *
     * @param amount Amount to subtract from balance.
     * @throws ATMException If current balance is less than the given amount.
     */
    public synchronized void withdraw(float amount) throws ATMException
    {
        if (amount > this.balance)
        {
            throw new ATMException("Insufficient funds to make the" +
                    " transaction");
        }
        this.balance -= amount;
    }

}

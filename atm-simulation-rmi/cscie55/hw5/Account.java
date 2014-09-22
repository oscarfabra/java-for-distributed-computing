/**
 * $Id: Account.java, v 1.0 05/12/13 23:06 oscarfabra Exp $
 * {@code Account} Is a class that represents one account in the Java RMI
 * distributed application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 05/12/13
 * @see cscie55.hw5.ATMImplementation
 */


package cscie55.hw5;

import cscie55.hw5.exceptions.ATMException;

import java.io.Serializable;

/**
 * Class that represents one account in the ATM Distributed Application using
 * Java RMI.
 */
public class Account implements Serializable
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Contains the balance of the account.
     */
    private float balance;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new account with a starting balance of 0
     *
     * @param initialBalance Account's initial balance.
     */
    public Account(float initialBalance)
    {
        this.balance = initialBalance;
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
    public float getBalance() throws ATMException
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
     * @throws ATMException If the given amount is not a valid.
     */
    public void deposit(float amount) throws ATMException
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
    public void withdraw(float amount) throws ATMException
    {
        if (amount > this.balance)
        {
            throw new ATMException("Insufficient funds to make the" +
                    " transaction");
        }
        this.balance -= amount;
    }

}

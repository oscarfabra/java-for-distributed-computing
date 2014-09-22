/**
 * $Id: Account.java, v 1.0 26/12/13 23:06 oscarfabra Exp $
 * {@code Account} Is a class that represents one account in the Java RMI
 * distributed application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 * @see cscie55.project.ATMImpl
 */

package cscie55.project;

import cscie55.project.exceptions.ATMException;
import cscie55.project.exceptions.ATMInsufficientFundsException;
import cscie55.project.exceptions.ATMInvalidAmountException;

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
     * Account's number.
     */
    private int accountNumber;

    /**
     * Personal identification number of the owner of this account.
     */
    private int pin;

    /**
     * Contains the balance of the account.
     */
    private double balance;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new account with the given starting balance.
     *
     * @param initialBalance Account's initial balance.
     */
    public Account(int accountNumber, int pin, double initialBalance)
    {
        this.accountNumber = accountNumber;
        this.pin = pin;
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
    public double getBalance() throws ATMException
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
    public void addAmount(double amount) throws ATMInvalidAmountException
    {
        if(amount <= 0)
        {
            throw new ATMInvalidAmountException("Invalid amount given.");
        }
        this.balance += amount;
    }

    /**
     * Subtracts the given amount from the account's balance.
     *
     * @param amount Amount to subtract from balance.
     * @throws ATMException If current balance is less than the given amount.
     */
    public void subtractAmount(double amount)
            throws ATMInsufficientFundsException
    {
        if (amount > this.balance)
        {
            throw new ATMInsufficientFundsException("Insufficient funds to " +
                    "make the transaction.");
        }
        this.balance -= amount;
    }

    /**
     * Determines if the given AccountInfo corresponds with the current
     * account.
     *
     * @param accountInfo AccountInfo object to examine.
     * @return Whether the current Account corresponds with the given
     * AccountInfo.
     */
    public boolean corresponds(AccountInfo accountInfo)
    {
        return (this.accountNumber == accountInfo.getAccountNumber()
                && this.pin == accountInfo.getPin());
    }

}

/**
 * $Id: ATMImplementation.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMImplementation} Is a class that implements the ATM interface and
 * allows the Server to communicate with Client processes.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 25/11/13
 * @see cscie55.hw4.ATM
 */

package cscie55.hw4;

import cscie55.hw4.exceptions.ATMException;

/**
 * Class that implements the ATM interface.
 */
public class ATMImplementation implements ATM
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Account containing the balance of one client.
     */
    private Account account;

    //-------------------------------------------------------------------------
    // CONSTRUCTORS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMImplementation with an account that has no balance.
     */
    public ATMImplementation()
    {
        this.account = new Account();
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Returns the account's balance.
     * @return Float with the account's balance.
     * @throws ATMException If the account's balance equals 0.
     */
    @Override
    public Float getBalance() throws ATMException
    {
        return this.account.getBalance();
    }

    /**
     * Adds the given amount to the account's balance.
     * @param amount Number to add to the account's balance.
     * @throws ATMException If the given amount is not a valid amount.
     */
    @Override
    public void deposit(float amount) throws ATMException
    {
        this.account.deposit(amount);
    }

    /**
     * Subtracts the given amount from the account's balance.
     * @param amount Number to subtract from the account's balance.
     * @throws ATMException If the given amount is bigger than the account's
     * balance.
     */
    @Override
    public void withdraw(float amount) throws ATMException
    {
        this.account.withdraw(amount);
    }
}

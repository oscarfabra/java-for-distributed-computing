/**
 * $Id: ATM.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATM} Is an interface that displays the methods that the ATM can
 * perform.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw4.ATMImplementation
 */


package cscie55.hw4;

import cscie55.hw4.exceptions.ATMException;

/**
 * Interface that contains the methods that the ATM can perform.
 */
public interface ATM
{
    //-------------------------------------------------------------------------
    // METHODS
    //-------------------------------------------------------------------------

    /**
     * Gets account's balance.
     * @return Account's balance.
     * @throws ATMException If balance is negative.
     */
    public Float getBalance() throws ATMException;

    /**
     * Deposits the given amount in the account.
     * @param amount Amount to deposit.
     * @throws ATMException If the given value is not valid.
     */
    public void deposit(float amount) throws ATMException;

    /**
     * Withdraws the given amount from the account.
     * @param amount Amount to withdraw.
     * @throws ATMException If couldn't make the withdrawal.
     */
    public void withdraw(float amount) throws ATMException;
}

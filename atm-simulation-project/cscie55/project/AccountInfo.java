/**
 * $Id: AccountInfo.java, v 1.0 26/12/13 23:06 oscarfabra Exp $
 * {@code AccountInfo} Is a class that includes information to identify an
 * Account in our distributed application.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 * @see cscie55.project.Account
 */

package cscie55.project;

import java.io.Serializable;

/**
 * AccountInfo is a class that includes information to identify an Account in
 * our distributed application.
 */
public final class AccountInfo implements Serializable
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Account's number.
     */
    private final int accountNumber;

    /**
     * Personal identification number of the account.
     */
    private final int pin;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new AccountInfo object.
     *
     * @param accountNumber Account's number.
     * @param pin Personal identification number of the account.
     */
    public AccountInfo(int accountNumber, int pin)
    {
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Returns the accountNumber attribute.
     * @return Account's Number.
     */
    public int getAccountNumber()
    {
        return this.accountNumber;
    }

    /**
     * Returns the pin attribute.
     * @return Personal Identification Number of the account.
     */
    public int getPin()
    {
        return this.pin;
    }

    public String toString()
    {
        return "AccountInfo object: " + this.accountNumber + ", " + this.pin;
    }
}

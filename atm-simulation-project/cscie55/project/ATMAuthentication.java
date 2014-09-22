/**
 * $Id: ATMAuthentication.java, v 1.0 26/12/13 10:58 oscarfabra Exp $
 * {@code ATMAuthentication} ATMAuthentication is a class whose instances
 * represent each of the authorized (accountNumber, pin) pairs that are
 * available as bank accounts.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

/**
 * ATMAuthentication is a class whose instances represent each of the
 * authorized (accountNumber, pin) pairs that are available as bank accounts.
 */
public class ATMAuthentication
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Account's number.
     */
    private int accountNumber;

    /**
     * Personal identification number that corresponds with the account.
     */
    private int pin;

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMAuthentication object.
     *
     * @param accountNumber Account's number.
     * @param pin Personal identification number of the account.
     */
    public ATMAuthentication(int accountNumber, int pin)
    {
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    /**
     * Determines if the given AccountInfo objects corresponds with this
     * authentication's account number and personal identification number.
     *
     * @param accountInfo AccountInfo object to examine.
     * @return Whether it corresponds or not.
     */
    public boolean corresponds(AccountInfo accountInfo)
    {
        return (this.accountNumber == accountInfo.getAccountNumber()
                && this.pin == accountInfo.getPin());
    }
}

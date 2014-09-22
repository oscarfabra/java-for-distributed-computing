/**
 * $Id: ATMException.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMException} Represents an exception used to report issues
 * regarding the ATM Processes.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw6.ATMImplementation
 */

package cscie55.hw6.exceptions;

/**
 * Exception used for reporting issues with the ATM Processes.
 */
public class ATMException extends Throwable
{
    public ATMException(String msg)
    {
        super(msg);
    }

}

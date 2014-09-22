/**
 * $Id: ATMInquireCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMInquireCommand} The inquire command returns the client's balance.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw4.commands.ATMCommand
 */
package cscie55.hw4.commands;

import cscie55.hw4.ATM;
import cscie55.hw4.exceptions.ATMException;

/**
 * Class that represents the ATM's Inquire Command.
 */
public class ATMInquireCommand extends ATMCommand
{
    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMInquireCommand object calling the constructor of its
     * superclass.
     * @param argument The argument to write on the super class.
     */
    public ATMInquireCommand(String argument)
    {
        super(argument);
    }

    //-------------------------------------------------------------------------
    // PROTECTED METHODS
    //-------------------------------------------------------------------------

    /**
     * Performs the action of the command.
     * @param atmImplementation ATMImplementation on which to perform the action.
     * @return String to send to the other process.
     * @throws ATMException If the account's balance equals 0.
     */
    @Override
    public String doCommand(ATM atmImplementation) throws ATMException
    {
        String result = Float.toString(atmImplementation.getBalance());
        return result;
    }
}

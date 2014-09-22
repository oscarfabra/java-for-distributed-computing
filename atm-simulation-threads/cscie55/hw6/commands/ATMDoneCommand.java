/**
 * $Id: ATMDoneCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMDoneCommand} Is a class that represents the ATM's Done Command.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw6.commands.ATMCommand
 */

package cscie55.hw6.commands;

import cscie55.hw6.ATM;

/**
 * Class that represents the ATM's Done Command.
 */
public class ATMDoneCommand extends ATMCommand
{

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMDoneCommand object calling the constructor of its
     * superclass.
     * @param argument The argument to write on the super class.
     */
    public ATMDoneCommand(String argument)
    {
        super(argument);
    }

    //-------------------------------------------------------------------------
    // PROTECTED METHODS
    //-------------------------------------------------------------------------

    /**
     * Performs the action of the command.
     *
     * @param atmImplementation ATMImplementation on which to perform the action.
     * @return String to send to the other process.
     */
    @Override
    public String doCommand(ATM atmImplementation)
    {
        String result = "Done.";
        return result;
    }
}

/**
 * $Id: ATMExitCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMExitCommand} The Exit Command tells the server to close its
 * connection and end its processes.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw4.commands.ATMCommand
 */

package cscie55.hw4.commands;

import cscie55.hw4.ATM;

/**
 * Class that represents the ATM's Exit Command.
 */
public class ATMExitCommand extends ATMCommand
{

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMExitCommand object calling the constructor of its
     * superclass.
     * @param argument The argument to write on the super class.
     */
    public ATMExitCommand(String argument)
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
        String result = "Start: " + this.getArgument();
        return result;
    }
}

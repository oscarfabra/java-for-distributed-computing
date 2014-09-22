/**
 * $Id: ATMStartCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMStartCommand} The start command indicates the server that a series
 * of client requests is coming.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw6.commands.ATMCommand
 */

package cscie55.hw6.commands;

import cscie55.hw6.ATM;

/**
 * Class that represents the ATM's Start Command.
 */
public class ATMStartCommand extends ATMCommand
{
    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMStartCommand object calling the constructor of its
     * superclass.
     */
    public ATMStartCommand()
    {
        super();
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
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
        String result = "Starting transaction...";
        return result;
    }
}

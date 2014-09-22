/**
 * $Id: ATMBogusCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMBogusCommand} Represents an ATM Bogus Command, extends ATMCommand.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw6.commands.ATMCommand
 */

package cscie55.hw6.commands;

import cscie55.hw6.ATM;

/**
 * Class that represents an ATM Bogus command.
 */
public class ATMBogusCommand extends ATMCommand
{

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMBogusCommand object calling the constructor of its
     * superclass.
     * @param argument The argument to write on the super class.
     */
    public ATMBogusCommand(String argument)
    {
        super(argument);
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
        String result = "Bogus: " + this.getArgument();
        return result;
    }

}

/**
 * $Id: ATMDepositCommand.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMDepositCommand} Is a class that extends ATMCommand and allows
 * the server to execute a Deposit in a client's Account.
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
 * Class that represents the ATM's Deposit Command.
 */
public class ATMDepositCommand extends ATMCommand
{

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMDepositCommand object calling the constructor of its
     * superclass.
     * @param argument The argument to write on the super class.
     */
    public ATMDepositCommand(String argument)
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
     * @throws ATMException If the argument can't be parsed into a valid float
     */
    @Override
    public String doCommand(ATM atmImplementation) throws ATMException
    {
        String result = "Success.";
        atmImplementation.deposit(Float.parseFloat(this.getArgument()));
        return result;
    }
}

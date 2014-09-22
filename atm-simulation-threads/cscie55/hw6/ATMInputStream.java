/**
 * $Id: ATMInputStream.java, v 1.0 25/11/13 23:08 oscarfabra Exp $
 * {@code ATMInputStream} Is a class that represents an input stream for the
 * ATM distributed application. It simply encapsulates a BufferedReader and
 * calls different ATMCommand objects depending on the command received from
 * a Client process.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 25/11/13
 * @see cscie55.hw6.Account
 */

package cscie55.hw6;

import cscie55.hw6.commands.ATMBogusCommand;
import cscie55.hw6.commands.ATMCommand;
import cscie55.hw6.commands.ATMDepositCommand;
import cscie55.hw6.commands.ATMDoneCommand;
import cscie55.hw6.commands.ATMExitCommand;
import cscie55.hw6.commands.ATMInquireCommand;
import cscie55.hw6.commands.ATMStartCommand;
import cscie55.hw6.commands.ATMWithdrawCommand;
import cscie55.hw6.commands.Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Class that represents an input stream for the ATM distributed application.
 */
public class ATMInputStream
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * BufferedReader used to read the input stream
     */
    BufferedReader bufferedReader;

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMInputStream
     * @param socket The connection to the other party
     * @throws IOException If the socket's input stream couldn't be read
     */
    public ATMInputStream(Socket socket) throws IOException
    {
        InputStream inputStream = socket.getInputStream();

        this.bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * Reads the command that was received and returns the appropriate
     * ATMCommand object.
     *
     * @return An ATMCommand object to be executed.
     * @throws IOException If bufferedReader couldn't be read.
     */
    public ATMCommand readCommand() throws IOException
    {
        // Obtains the command line
        String commandLine = this.bufferedReader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(commandLine);

        // Reads the command, which is the first token
        String command = tokenizer.nextToken();

        System.out.println("---------------------------------------");
        System.out.println("Command read: "+command);

        String argument = null;

        // Returns a new command according to the String that was read
        if (command.equalsIgnoreCase(Commands.START.toString()))
        {
            return new ATMStartCommand();
        }
        else if (command.equalsIgnoreCase(Commands.DEPOSIT.toString()))
        {
            try
            {
                // Reads the argument
                argument = tokenizer.nextToken();
            }
            catch(NoSuchElementException nse)
            {
                System.out.
                        println("Missing amount for command \""+command+"\".");
                nse.printStackTrace();
            }
            return new ATMDepositCommand(argument);
        }
        else if(command.equalsIgnoreCase(Commands.WITHDRAW.toString()))
        {
            try
            {
                // Reads the argument
                argument = tokenizer.nextToken();
            }
            catch(NoSuchElementException nse)
            {
                System.out.
                        println("Missing amount for command \""+command+"\".");
                nse.printStackTrace();
            }

            return new ATMWithdrawCommand(argument);
        }
        else if(command.equalsIgnoreCase(Commands.INQUIRE.toString()))
        {
            return new ATMInquireCommand(argument);
        }
        else if(command.equalsIgnoreCase(Commands.DONE.toString()))
        {
            return new ATMDoneCommand(argument);
        }
        else if(command.equalsIgnoreCase(Commands.WITHDRAW.toString()))
        {
            try
            {   // Reads the argument
                argument = tokenizer.nextToken();
            }
            catch(NoSuchElementException nse)
            {
                System.out.
                        println("Missing amount for command \""+command+"\".");
                nse.printStackTrace();
            }
            return new ATMExitCommand(argument);
        }
        else
        {
            return new ATMBogusCommand(argument);
        }
    }
}

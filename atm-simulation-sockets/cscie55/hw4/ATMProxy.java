/**
 * $Id: ATMProxy.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMProxy} Is a class that enables a Client to dispatch each method
 * of the ATM interface to the remote ATMImplementation.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw4.ATM
 * @see cscie55.hw4.ATMImplementation
 */

package cscie55.hw4;

import cscie55.hw4.commands.Commands;
import cscie55.hw4.exceptions.ATMException;

import java.io.IOException;


/**
 * ATM Proxy is a class that enables the client to dispatch each method of the
 * ATM interface to the remote ATMImplementation.
 */
public class ATMProxy extends ATMClient implements ATM
{

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMProxy object.
     *
     * @param host Physical host name where the server is located.
     * @param port Physical port number where the server is located.
     */
    public ATMProxy(String host, int port)
    {
        // Calls the ATMClient's constructor
        super(host,port);
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Gets the balance of the account.
     *
     * @return Float amount that the account has.
     * @throws ATMException If the withdrawal couldn't be made.
     */
    @Override
    public Float getBalance() throws ATMException
    {
        String serverResponse = null;

        try
        {
            System.out.println("About to ask for account balance...");

            this.printStream.println(Commands.INQUIRE.toString());

            serverResponse = this.bufferedReader.readLine();

            System.out.println("Server returned: " + serverResponse);

            // Pauses for 3 seconds, for printing purposes
            try
            {
                Thread.sleep(3000);
            }
            catch (Exception e)
            { }
        }
        catch (IOException e)
        {
            throw new ATMException("Couldn't get the balance from the ATM server.");
        }

        return Float.parseFloat(serverResponse.trim());
    }

    /**
     * Makes a deposit of the given amount.
     *
     * @param amount Quantity to deposit into the ATM.
     * @throws ATMException If the deposit couldn't be made.
     */
    @Override
    public void deposit(float amount) throws ATMException
    {
        try
        {
            System.out.println("About to make deposit...");

            this.printStream.
                    println(Commands.DEPOSIT.toString() + " " + amount);

            String serverResponse = this.bufferedReader.readLine();

            System.out.println("Server returned: " + serverResponse);

            // Pauses for 3 seconds, for printing purposes
            try
            {
                Thread.sleep(3000);
            }
            catch (Exception e)
            { }
        }
        catch (IOException e)
        {
            throw new ATMException("Couldn't make the deposit to the ATM server.");
        }

    }

    /**
     * Withdraws the given amount from the server.
     *
     * @param amount Quantity to withdraw from the ATM.
     * @throws ATMException If the withdrawal couldn't be made.
     */
    @Override
    public void withdraw(float amount) throws ATMException
    {
        String serverResponse = null;

        try
        {
            System.out.println("About to make withdrawal...");

            this.printStream.println(Commands.WITHDRAW.toString() + " " + amount);

            serverResponse = this.bufferedReader.readLine();

            System.out.println("Server returned: " + serverResponse);

            // Pauses for 3 seconds, for printing purposes
            try
            {
                Thread.sleep(3000);
            }
            catch (Exception e)
            { }
        }
        catch (IOException e)
        {
            throw new ATMException("Couldn't make the withdrawal from the ATM server.");
        }
    }
}

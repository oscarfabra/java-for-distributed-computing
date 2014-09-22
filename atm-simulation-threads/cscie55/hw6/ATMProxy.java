/**
 * $Id: ATMProxy.java, v 1.0 18/11/13 23:08 oscarfabra Exp $
 * {@code ATMProxy} Is a class that enables a Client to dispatch each method
 * of the ATM interface to the remote ATMImplementation.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw6.ATM
 * @see cscie55.hw6.ATMImplementation
 */

package cscie55.hw6;

import cscie55.hw6.commands.Commands;
import cscie55.hw6.exceptions.ATMException;

import java.io.*;
import java.net.Socket;


/**
 * ATM Proxy is a class that enables the client to dispatch each method of the
 * ATM interface to the remote ATMImplementation.
 */
public class ATMProxy implements ATM
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Socket connection to the server.
     */
    protected Socket serverConnection;

    /**
     * Stream to send messages to the server
     */
    protected PrintStream printStream;

    /**
     * BufferedReader used to read the input stream from the server.
     */
    BufferedReader bufferedReader;

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
        try
        {
            System.out.println("Trying to connect to " + host + ":" + port);
            this.serverConnection = new Socket(host,port);

            // Gets printStream to send messages to the server
            this.printStream =
                    new PrintStream(this.serverConnection.getOutputStream());

            // Gets bufferedReader to read messages from the server
            InputStream inputStream = this.serverConnection.getInputStream();
            this.bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream));
        }
        catch (IOException ioe)
        {
            throw new IllegalArgumentException("Bad host name given.");
        }

        System.out.println("Established server connection.");
    }

    //-------------------------------------------------------------------------
    // MAIN METHODS
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

    //-------------------------------------------------------------------------
    // ADDITIONAL METHODS
    //-------------------------------------------------------------------------

    /**
     * Closes the connection to the server.
     */
    public void close()
    {
        try
        {
            if(this.serverConnection != null)
            {
                this.serverConnection.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("Exception gotten while trying to close " +
                    "connection to the server.");
        }
    }

    /**
     * Sends the DONE command, which indicates to the server that a series of
     * transactions for a given client have been done.
     */
    public void done()
    {
        try
        {
            System.out.println("Indicating that transaction has been done...");

            this.printStream.println(Commands.DONE);

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
            System.out.println("Client: Error getting I/O streams.");
        }
    }
}

/**
 * $Id: ATMClient.java, v 1.0 26/11/13 23:06 oscarfabra Exp $
 * {@code ATMClient} Is a class that contains common attributes and methods of
 * the ATM's clients. Is used to add state and behavior to the ATMProxy class.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/11/13
 * @see cscie55.hw4.ATMProxy
 */

package cscie55.hw4;

import cscie55.hw4.commands.Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Abstract class used to add attributes and methods to the ATMProxy class.
 */
public abstract class ATMClient
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
    public ATMClient(String host, int port)
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
    // METHODS
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

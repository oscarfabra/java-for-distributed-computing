/**
 * $Id: Server.java, v 1.0 18/11/13 23:06 oscarfabra Exp $
 * {@code Server} Is a class that represents an ATM server for client requests.
 * It relies on an ATMImplementation object to perform the available tasks.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw4.Client
 */


package cscie55.hw4;

import cscie55.hw4.commands.ATMCommand;
import cscie55.hw4.commands.ATMDoneCommand;
import cscie55.hw4.commands.ATMExitCommand;
import cscie55.hw4.exceptions.ATMException;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class that represents an ATM server for client requests.
 */
public class Server
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * ServerSocket to attend client requests.
     */
    private ServerSocket serverSocket;

    /**
     * ATMImplementation to read and perform commands for a client.
     */
    private ATM atmImplementation;

    /**
     * ATMInputStream to read commands from clients.
     */
    private ATMInputStream atmInputStream;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new server given the port to listen to for client requests.
     *
     * @param port Port number to listen for client requests
     * @throws IllegalArgumentException If the given port is not valid
     * @throws IOException If server is unable to create a ServerSocket
     */
    public Server(int port) throws IllegalArgumentException, IOException
    {
        if (port <= 0) {
            throw new IllegalArgumentException("Bad port number given to " +
                    "Server constructor: " + port);
        }

        // Tries to make a ServerSocket to the given port
        System.out.println("Connecting server socket to port...");
        this.serverSocket = new ServerSocket(port);

        atmImplementation = new ATMImplementation();
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Listens to port for client connection requests.
     */
    public void listen()
    {
        // Listens to port for client connection requests
        try
        {
            // Defaults to false
            boolean exit = false;

            while (!exit)
            {
                System.out.println("Waiting for the client...");
                Socket clientConnection = this.serverSocket.accept();

                System.out.println("Connected to the client...");

                // Serves the client
                exit = this.serveClient(clientConnection);
                clientConnection.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println();
            System.out.println("Client disconnected from server.");
        }
    }

    /**
     * Serves the client by reading and executing its commands.
     *
     * @param clientConnection Socket connection to the client
     * @return boolean Whether to close the connection with the client
     * @throws IOException  If server was unable to get input stream from client
     */
    public boolean serveClient(Socket clientConnection) throws IOException
    {
        // atmInputStream reads commands from the client
        this.atmInputStream =
                this.configureInput(clientConnection);

        if (this.atmInputStream == null)
        {
            System.err.
                    println("Unable to get input stream from client socket.");
            return true;
        }

        // outputStream writes responses back to client
        PrintStream printStream = this.configureOutput(clientConnection);

        if (printStream == null)
        {
            System.err.println("Unable to get output stream from client socket.");
            return true;
        }

        System.out.println("Attempting to read commands...");
        ATMCommand cmd = null;

        // Does this while server hasn't received an EXIT or a DONE command
        while (true)
        {
            // Reads a command from the client
            cmd = this.atmInputStream.readCommand();

            String result = "";

            try
            {
                // Performs the command using polymorphism
                result = cmd.doCommand(this.atmImplementation);
            }
            catch(ATMException ae)
            {
                // Prints the message given to the exception on the account
                System.out.println(ae.toString());
            }

            System.out.println("Sending back: " + result);

            // Returns the result to the client
            printStream.println(result);

            // If it's an EXIT command, returns true to close connection with
            // the client.
            if (cmd instanceof ATMExitCommand)
            {
                return true;
            }

            // If it's a DONE command, server won't end communication with the
            // client yet.
            if (cmd instanceof ATMDoneCommand)
            {
                return false;
            }
        }
    }

    /**
     * Creates a new ATMInputStream to read commands from client.
     *
     * @param clientConnection Socket connection to the client
     * @return ATMInputStream object to read commands from client
     */
    public ATMInputStream configureInput(Socket clientConnection)
    {
        ATMInputStream result = null;

        // Attempts to create a new ATMInputStream to read commands from client
        try
        {
            result = new ATMInputStream(clientConnection);
        }
        catch (IOException ioe)
        {
            System.out.println("Server: Error getting I/O streams.");
        }

        return result;
    }

    /**
     * Creates a new PrintStream object to respond to the client.
     *
     * @param clientConnection Connection to the client.
     * @return PrintStream object on which to respond to the client.
     */
    public PrintStream configureOutput(Socket clientConnection)
    {
        PrintStream printStream = null;

        try
        {
            printStream = new PrintStream(clientConnection.getOutputStream());
        }
        catch (IOException e)
        {
            System.out.println("Server: Error getting I/O streams.");
        }

        return printStream;
    }

    /**
     * Closes the server's connection.
     */
    public void close()
    {
        try
        {
            if (this.serverSocket != null)
            {
                this.serverSocket.close();
            }
        }
        catch (IOException ioe)
        { }
    }

    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method that instantiates the server and starts listening to
     * requests from clients.
     *
     * @param args Array containing the server's port number on args[0]
     */
    public static void main(String [] args)
    {
        int port = 0;

        if (args.length > 0)
        {
            try
            {
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException nfe)
            { }
        }

        // Creates the server and starts listening for commands
        Server server = null;

        try
        {
            server = new Server(port);
            System.out.println("Server running on port " + port + "...");
            server.listen();
        }
        catch (IOException ioe)
        {
            System.out.println(ioe);
        }
        finally
        {
            server.close();
        }
    }
}

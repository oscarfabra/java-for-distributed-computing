/**
 * $Id: Server.java, v 1.0 18/11/13 23:06 oscarfabra Exp $
 * {@code Server} Is a class that represents an ATM server for client requests.
 * It relies on an ATMImplementation object to perform the available tasks.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 18/11/13
 * @see cscie55.hw6.Client
 */


package cscie55.hw6;

import cscie55.hw6.commands.ATMCommand;
import cscie55.hw6.commands.ATMDoneCommand;
import cscie55.hw6.commands.ATMExitCommand;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Vector;

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
     * Collection of requests from clients to execute concurrently.
     */
    private PriorityQueue<ATMRunnable> requestQueue;

    /**
     * Pool of threads to attend client requests.
     */
    private Vector<ATMThread> threadPool;

    /**
     * Variable to indicate the number of threads created, for illustration
     * purposes.
     */
    private int createdThreads;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new server given the port to listen to for client requests.
     *
     * @param port Port number to listen to for client requests
     * @throws IllegalArgumentException If the given port is not valid
     * @throws IOException If server is unable to create a ServerSocket
     */
    public Server(int port) throws IllegalArgumentException, IOException
    {
        if (port <= 0)
        {
            throw new IllegalArgumentException("Bad port number given to " +
                    "Server constructor: " + port);
        }

        // Tries to make a ServerSocket to the given port
        System.out.println("Connecting server socket to port...");
        this.serverSocket = new ServerSocket(port);

        // Instantiates the remaining attributes
        this.atmImplementation = new ATMImplementation();
        this.requestQueue = new PriorityQueue<ATMRunnable>();
        this.threadPool = new Vector<ATMThread>();
        this.createdThreads = 0;
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Listens to port for client connection requests and serves them.
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
                System.out.println("Waiting for a client...");
                Socket clientConnection = this.serverSocket.accept();

                System.out.println("Connected to a client...");

                // Serves the client
                exit = this.serveClient(clientConnection);

                // Closes the connection to the client
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
     * @throws IOException If server was unable to get input stream from client
     */
    public boolean serveClient(Socket clientConnection) throws IOException
    {
        // atmInputStream reads commands from the client
        ATMInputStream atmInputStream = this.configureInput(clientConnection);

        if (atmInputStream == null)
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
        ATMCommand atmCommand = null;

        // Does this while server hasn't received an EXIT or a DONE command
        while (true)
        {
            // Reads a command from the client
            atmCommand = atmInputStream.readCommand();

            // Creates the Runnable object passing by the command read from the
            // socket, a reference to the ATM object, and the printStream onto
            // which it can write the result to the client.
            ATMRunnable atmRunnable = new ATMRunnable(atmCommand,
                    this.atmImplementation, printStream);

            // Adds the Runnable object to the request queue
            this.requestQueue.add(atmRunnable);
            this.createdThreads++;

            // Creates a new Thread with a reference to the request queue and
            // adds it to the request pool.
            this.threadPool.add(new ATMThread(this.requestQueue,this.createdThreads));

            // Serves client requests and removes the attended request to avoid
            // an IllegalThreadStateException due to trying to start the same
            // Thread twice.
            for(int i=0; i < this.threadPool.size(); i++)
            {
                ATMThread atmThread = this.threadPool.get(i);
                atmThread.start();

                // Prints a message indicating the number of the thread used.
                System.out.println(atmThread);

                this.threadPool.remove(i);
            }

            // If it's an EXIT command, returns true to close connection with
            // the client.
            if (atmCommand instanceof ATMExitCommand)
            {
                return true;
            }

            // If it's a DONE command, server won't end communication with the
            // client yet.
            if (atmCommand instanceof ATMDoneCommand)
            {
                return false;
            }
        }
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
    // PRIVATE HELPER METHODS
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMInputStream to read commands from client.
     *
     * @param clientConnection Socket connection to the client
     * @return ATMInputStream object to read commands from client
     */
    private ATMInputStream configureInput(Socket clientConnection)
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
    private PrintStream configureOutput(Socket clientConnection)
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

    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method that instantiates the server and starts listening to
     * requests from clients.
     *
     * @param args Array containing its port number on args[0]
     */
    public static void main(String args[])
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

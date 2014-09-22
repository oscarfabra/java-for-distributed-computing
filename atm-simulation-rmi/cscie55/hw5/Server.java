/**
 * $Id: Server.java, v 1.0 5/12/13 17:48 oscarfabra Exp $
 * {@code Server} The Server class is used to start-up the server process.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 5/12/13
 */

package cscie55.hw5;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;

/**
 * The Server class is used to start-up the server process.
 */
public class Server
{
    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method of the Server.
     *
     * @param args Empty array.
     */
    public static void main(String [] args)
    {
        // Assigns the policy file to the VM
        System.setProperty("java.security.policy","file:cscie55/hw5/server.policy");

        // Create and install a security manager
        if(System.getSecurityManager()==null)
        {
            System.setSecurityManager(new RMISecurityManager());
            System.out.println("Security manager installed.");
        }

        try
        {
            LocateRegistry.createRegistry(1099);
            System.out.println("Java RMI registry created.");
        }
        catch(Exception ex)
        {
            System.out.println("Java RMI registry already exists.");
        }

        try
        {
            // Instantiates the ATMFactoryImpl
            ATMFactoryImpl atmFactory = new ATMFactoryImpl();

            // Bind this object instance to the name "ATMServer"
            Naming.rebind("//localhost/atmfactory", atmFactory);

            System.out.println("ATMFactory bound in registry.");
        }
        catch (Exception ex)
        {
            System.out.println("ATMFactory error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

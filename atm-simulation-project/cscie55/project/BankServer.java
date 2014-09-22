/**
 * $Id: ATMServer.java, v 1.0 26/12/13 17:48 oscarfabra Exp $
 * {@code BankServer} The BankServer class starts up and creates a Bank
 * instance and a Security instance and registers them with the RMI Registry.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;

/**
 * The BankServer class starts up and creates a Bank instance and a Security
 * instance and registers each of them with the RMI Registry.
 */
public class BankServer
{
    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main method of the BankServer.
     *
     * @param args Empty array.
     */
    public static void main(String [] args)
    {
        // Assigns the policy file to the VM
        System.setProperty("java.security.policy",
            "file:cscie55/project/bank.policy");

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
            // Instantiates the BankImpl
            BankImpl bank = new BankImpl();

            // Bind this object instance to the name "bank"
            Naming.rebind("//localhost/bank", bank);

            System.out.println("Bank bound in registry.");
        }
        catch (Exception ex)
        {
            System.out.println("Bank error: " + ex.getMessage());
            ex.printStackTrace();
        }

        try
        {
            // Instantiates the SecurityImpl
            SecurityImpl security = new SecurityImpl();

            // Bind this object instance to the name "security"
            Naming.rebind("//localhost/security", security);

            System.out.println("Security bound in registry.");
        }
        catch (Exception ex)
        {
            System.out.println("Security error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}

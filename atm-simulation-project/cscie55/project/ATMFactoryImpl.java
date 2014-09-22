/**
 * $Id: ATMFactoryImpl.java, v 1.0 26/12/13 17:33 oscarfabra Exp $
 * {@code ATMFactoryImpl} RMI-ready implementation of the ATMFactory interface.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import cscie55.project.exceptions.ATMException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI-ready implementation of the ATMFactory interface.
 */
public class ATMFactoryImpl extends UnicastRemoteObject implements ATMFactory
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * ATM instance to be called.
     */
    private ATM atm = null;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new ATMFactoryImpl object.
     *
     * @param bank Reference to the Bank object.
     * @param security Reference to the Security object.
     * @throws RemoteException If the object couldn't be created.
     */
    public ATMFactoryImpl(Bank bank, Security security) throws RemoteException
    {
        if(this.atm == null)
        {
            this.atm = new ATMImpl(bank, security);
            System.out.println("Created new ATMFactoryImpl.");
        }
    }

    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Gets the ATM stub to be used by the ATMServer process.
     *
     * @return ATM stub to be used by the Server
     * @throws ATMException If the ATM stub couldn't be retrieved
     */
    @Override
    public ATM getATM() throws RemoteException
    {
        return this.atm;
    }
}

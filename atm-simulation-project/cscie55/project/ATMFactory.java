/**
 * $Id: ATMFactory.java, v 1.0 26/12/13 17:24 oscarfabra Exp $
 * {@code ATMFactory} Interface that is RMI ready and supports a single
 * getATM() method.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import cscie55.project.exceptions.ATMException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ATMFactory is a class that returns ATM stubs to be used by the server
 * process to attend different client processes.
 */
public interface ATMFactory extends Remote, Serializable
{
    /**
     * Gets an ATM stub to be used by the ATMServer process.
     *
     * @return ATM stub to be used by the ATMServer
     * @throws ATMException If the ATM stub couldn't be retrieved
     */
    public ATM getATM() throws RemoteException;
}

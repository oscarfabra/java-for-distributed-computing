/**
 * $Id: Commands.java, v 1.0 21/11/13 22:20 oscarfabra Exp $
 * {@code Commands} An enum containing all the possible ATM Commands' values.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 21/11/13
 */

package cscie55.project.commands;

/**
 * Enumeration containing the possible commands between the ATM clients
 * and a server.
 */
public enum Commands
{
    /**
     * Command to indicate the beginning of a transaction between a client
     * and the server.
     */
    START,

    /**
     * Command to indicate the deposit of an amount of money into a client's
     * account.
     */
    DEPOSIT,

    /**
     * Command to indicate the withdrawal of an amount of money into a client's
     * account.
     */
    WITHDRAW,

    /**
     * Command to indicate the query of the balance of a client's account.
     */
    INQUIRE,

    /**
     * Command to indicate the transfer of money from one account to another.
     */
    TRANSFER,

    /**
     * Command from a client to indicate to the server that it has finished
     * attending its transactions.
     */
    DONE,

    /**
     * Command to indicate to the server that it has finished attending all
     * transactions.
     */
    EXIT
}

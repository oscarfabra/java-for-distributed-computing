/**
 * $Id: TransactionNotification.java, v 1.0 26/12/13 10:58 oscarfabra Exp $
 * {@code TransactionNotification} TransactionNotification is a class that
 * includes pertinent information about a particular transaction including
 * target accounts and amounts.
 *
 * @author <a href="mailto:fabrasuarez@g.harvard.edu">Oscar Fabra</a>
 * @version 1.0
 * @since 26/12/13
 */

package cscie55.project;

import cscie55.project.commands.Commands;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * TransactionNotification is a class that includes pertinent information about
 * a particular transaction including target accounts and amounts.
 */
public final class TransactionNotification implements Serializable
{
    //-------------------------------------------------------------------------
    // ATTRIBUTES
    //-------------------------------------------------------------------------

    /**
     * Command that is going to be performed.
     */
    private final Commands command;

    /**
     * Amount that is going to be used, in case there is.
     */
    private final double amount;

    /**
     * Account's info for the intervening accounts.
     */
    private final List<AccountInfo> accountsInfo;

    //-------------------------------------------------------------------------
    // CONSTRUCTOR
    //-------------------------------------------------------------------------

    /**
     * Creates a new TransactionNotification object.
     *
     * @param command Command to be executed.
     * @param amount Amount intervening.
     * @param accountsInfo AccountInfo object(s) intervening.
     */
    public TransactionNotification(Commands command, double amount,
                                   Object... accountsInfo)
    {
        this.command = command;
        this.amount = amount;
        this.accountsInfo = new Vector<AccountInfo>();
        for(Object accountInfo: accountsInfo)
        {
            this.accountsInfo.add((AccountInfo)accountInfo);
        }
    }

    /**
     * Returns the transaction's command.
     * @return Command to be executed.
     */
    public Commands getCommand()
    {
        return this.command;
    }

    /**
     * Returns the transaction's intervening account(s).
     * @return Accounts intervening in the transaction.
     */
    public List<AccountInfo> getAccountsInfo()
    {
        return this.accountsInfo;
    }

    /**
     * Returns a String summarizing this TransactionNotification's values.
     * @return String summarizing this TransactionNotification's values.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("TransactionNotification: ");
        buffer.append(this.command.toString()).append(", ");
        if(this.command.equals(Commands.TRANSFER))
        {
            buffer.append("Account 1: ");
            buffer.append(this.accountsInfo.get(0).getAccountNumber());
            buffer.append("Account 2: ");
            buffer.append(this.accountsInfo.get(1).getAccountNumber());
            buffer.append(", ");
            buffer.append("Amount: ").append(this.amount);
        }
        else
        {
            buffer.append("Account: ");
            buffer.append(this.accountsInfo.get(0).getAccountNumber());
            if(this.command.equals(Commands.DEPOSIT) ||
                    this.command.equals(Commands.WITHDRAW))
            {
                buffer.append(", ");
                buffer.append("Amount: ").append(this.amount);
            }
        }
        buffer.append("\n");
        return buffer.toString();
    }
}

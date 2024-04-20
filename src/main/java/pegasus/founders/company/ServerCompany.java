package pegasus.founders.company;

import org.bukkit.OfflinePlayer;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.UUID;

/**
 * A server-owned company.
 */
public class ServerCompany extends AbstractCompany {
    /**
     * Creates a new server-owned company.
     * @param uniqueId The unique identifier of this company
     * @param name The name of this company
     * @param symbol The symbol of this company
     * @param creationDate The creation date of this company
     * @param ceo The CEO of this company
     * @param balance The balance of this company
     * @param employment The employment of this company
     */
    public ServerCompany(
            UUID uniqueId,
            String name,
            String symbol,
            DateTime creationDate,
            OfflinePlayer ceo,
            double balance,
            Map<OfflinePlayer, Double> employment
    ) {
        super(uniqueId, name, symbol, CompanyType.SERVER, creationDate, ceo, balance, employment);
    }
}

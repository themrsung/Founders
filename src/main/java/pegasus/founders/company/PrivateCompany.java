package pegasus.founders.company;

import org.bukkit.OfflinePlayer;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.UUID;

/**
 * A private company. The CEO is the owner of the company.
 */
public class PrivateCompany extends AbstractCompany {
    /**
     * Creates a new privately-owned company.
     * @param uniqueId The unique identifier of this company
     * @param name The name of this company
     * @param symbol The symbol of this company
     * @param creationDate The creation date of this company
     * @param ceo The CEO & owner of this company
     * @param balance The balance of this company
     * @param employment The employment of this company
     */
    public PrivateCompany(
            UUID uniqueId,
            String name,
            String symbol,
            DateTime creationDate,
            OfflinePlayer ceo,
            double balance,
            Map<OfflinePlayer, Double> employment
    ) {
        super(uniqueId, name, symbol, CompanyType.PRIVATE, creationDate, ceo, balance, employment);
    }
}

package pegasus.founders.company;

import org.bukkit.OfflinePlayer;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * The abstract class for all companies.
 */
public abstract class AbstractCompany implements Company {
    protected final UUID uniqueId;
    protected String name;
    protected String symbol;
    protected final CompanyType type;
    protected final DateTime creationDate;
    protected OfflinePlayer ceo;
    protected double balance;
    protected final Map<OfflinePlayer, Double> employment;

    protected AbstractCompany(
            UUID uniqueId,
            String name,
            String symbol,
            CompanyType type,
            DateTime creationDate,
            OfflinePlayer ceo,
            double balance,
            Map<OfflinePlayer, Double> employment
    ) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.symbol = symbol;
        this.type = type;
        this.creationDate = creationDate;
        this.ceo = ceo;
        this.balance = balance;
        this.employment = employment;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public CompanyType getType() {
        return type;
    }

    @Override
    public DateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public OfflinePlayer getCEO() {
        return ceo;
    }

    @Override
    public void setCEO(OfflinePlayer ceo) {
        this.ceo = ceo;
    }

    @Override
    public Map<OfflinePlayer, Double> getEmployment() {
        return Map.copyOf(employment);
    }

    @Override
    public Stream<OfflinePlayer> getEmployees() {
        return employment.keySet().stream();
    }

    @Override
    public double getHourlyPay(OfflinePlayer employee) {
        return employment.getOrDefault(employee, 0d);
    }

    @Override
    public void setHourlyPay(OfflinePlayer employee, double pay) {
        employment.put(employee, pay);
    }

    @Override
    public void fire(OfflinePlayer employee) {
        employment.remove(employee);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }
}

package pegasus.founders.company;

import org.bukkit.OfflinePlayer;
import pegasus.founders.Founders;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class Companies {
    /**
     * The set of companies.
     */
    private final Set<Company> companies;

    /**
     * Creates an empty state.
     */
    public Companies() {
        this.companies = new HashSet<>();
    }

    /**
     * Creates a state.
     *
     * @param companies The set of companies
     */
    public Companies(Set<Company> companies) {
        this.companies = companies;
    }

    /**
     * Returns a stream of the companies.
     *
     * @return A stream of the companies
     */
    public Stream<Company> getCompanies() {
        return companies.stream();
    }

    /**
     * Finds a company by its unique identifier.
     *
     * @param uniqueId The unique identifier
     * @return The company if found, {@code null} otherwise
     */
    public Company getCompany(UUID uniqueId) {
        for (var company : companies) {
            if (Objects.equals(company.getUniqueId(), uniqueId)) return company;
        }

        return null;
    }

    /**
     * Adds a company to this state.
     *
     * @param company The company to add
     * @return {@code true} if the operation was successful
     */
    public boolean addCompany(Company company) {
        return companies.add(company);
    }

    /**
     * Removes a company from this state.
     *
     * @param company The company to remove
     * @return {@code true} if the operation was successful
     */
    public boolean removeCompany(Company company) {
        return companies.remove(company);
    }

    /**
     * Checks if the company has the provided amount.
     *
     * @param company The company to check
     * @param amount  The amount to check
     * @return {@code true} if the company can pay the amount
     */
    public boolean has(Company company, double amount) {
        if (company.getType() == CompanyType.SERVER) return true;

        // TODO guarantees and line of credit

        return company.getBalance() >= amount;
    }

    /**
     * Transfers money from a company to a company.
     *
     * @param sender    The sender company
     * @param recipient The recipient company
     * @param amount    The amount to send
     * @return {@code true} if successful, {@code false} otherwise
     */
    public boolean transfer(Company sender, Company recipient, double amount) {
        if (!has(sender, amount)) return false;

        var senderBefore = sender.getBalance();
        var senderAfter = senderBefore - amount;

        var recipientBefore = recipient.getBalance();
        var recipientAfter = recipientBefore + amount;

        var sumBefore = senderBefore + recipientBefore;
        var sumAfter = senderAfter + recipientAfter;

        if (sumBefore != sumAfter) return false;

        sender.setBalance(senderAfter);
        recipient.setBalance(recipientAfter);

        return true;
    }

    /**
     * Transfers money from a company to a player.
     *
     * @param sender    The sender company
     * @param recipient The recipient player
     * @param amount    The amount to send
     * @return {@code true} if successful, {@code false} otherwise
     */
    public boolean transfer(Company sender, OfflinePlayer recipient, double amount) {
        var economy = Founders.getEconomy();

        if (!has(sender, amount)) return false;

        var senderBefore = sender.getBalance();
        var senderAfter = senderBefore - amount;

        var recipientBefore = economy.getBalance(recipient);
        var recipientAfter = recipientBefore + amount;

        var sumBefore = senderBefore + recipientBefore;
        var sumAfter = senderAfter + recipientAfter;

        if (sumBefore != sumAfter) return false;

        var result = economy.depositPlayer(recipient, amount);
        if (!result.transactionSuccess()) return false;

        sender.setBalance(senderAfter);

        return true;
    }

    /**
     * Transfers money from a player to a company.
     *
     * @param sender    The sender player
     * @param recipient The recipient company
     * @param amount    The amount to send
     * @return {@code true} if successful, {@code false} otherwise
     */
    public boolean transfer(OfflinePlayer sender, Company recipient, double amount) {
        var economy = Founders.getEconomy();

        if (!economy.has(sender, amount)) return false;

        var senderBefore = economy.getBalance(sender);
        var senderAfter = senderBefore - amount;

        var recipientBefore = recipient.getBalance();
        var recipientAfter = recipientBefore + amount;

        var sumBefore = senderBefore + recipientBefore;
        var sumAfter = senderAfter + recipientAfter;

        if (sumBefore != sumAfter) return false;

        var result = economy.withdrawPlayer(sender, amount);
        if (!result.transactionSuccess()) return false;

        recipient.setBalance(recipientAfter);

        return true;
    }
}

package pegasus.founders.company;

import org.bukkit.OfflinePlayer;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * A shareholder-owned company.
 */
public class Corporation extends AbstractCompany {
    protected final Map<OfflinePlayer, Long> ownership;

    /**
     * Creates a new shareholder-owned company.
     *
     * @param uniqueId     The unique identifier of this company
     * @param name         The name of this company
     * @param symbol       The symbol of this company
     * @param creationDate The creation date of this company
     * @param ceo          The CEO of this company
     * @param balance      The balance of this company
     * @param employment   The employment of this company
     * @param ownership    The ownership of this company
     */
    public Corporation(
            UUID uniqueId,
            String name,
            String symbol,
            DateTime creationDate,
            OfflinePlayer ceo,
            double balance,
            Map<OfflinePlayer, Double> employment,
            Map<OfflinePlayer, Long> ownership
    ) {
        super(uniqueId, name, symbol, CompanyType.CORPORATION, creationDate, ceo, balance, employment);
        this.ownership = ownership;
    }

    /**
     * Returns the number of outstanding shares.
     *
     * @return The number of outstanding shares
     */
    public long getOutstandingShares() {
        return ownership.keySet().stream().mapToLong(this::getNumberOfShares).sum();
    }

    /**
     * Returns the ownership data of this company.
     *
     * @return The ownership data of this company
     */
    public Map<OfflinePlayer, Long> getOwnership() {
        return Map.copyOf(ownership);
    }

    /**
     * Returns the majority shareholder(s).
     *
     * @return The majority shareholder(s)
     */
    public Map<OfflinePlayer, Long> getMajorityShareholders() {
        Map<OfflinePlayer, Long> owners = new HashMap<>();

        var majorityNumShares = ownership.keySet().stream().mapToLong(this::getNumberOfShares).reduce(0, Math::max);

        ownership.keySet().forEach(s -> {
            var shares = getNumberOfShares(s);
            if (shares < majorityNumShares) return;

            owners.put(s, shares);
        });

        return owners;
    }

    /**
     * Returns the stream of shareholders of this company.
     *
     * @return The shareholders of this company
     */
    public Stream<OfflinePlayer> getShareholders() {
        return ownership.keySet().stream();
    }

    /**
     * Returns the number of shares the given player owns. If the player is not a shareholder,
     * this will return zero.
     *
     * @param shareholder The shareholder to query
     * @return The number of shares the player owns
     */
    public long getNumberOfShares(OfflinePlayer shareholder) {
        return ownership.getOrDefault(shareholder, 0L);
    }

    /**
     * Transfers shares between shareholders.
     *
     * @param sender    The sender
     * @param recipient The recipient
     * @param shares    The number of shares to transfer
     * @return {@code true} if the transfer was successful, {@code false} otherwise
     */
    public boolean transferShares(OfflinePlayer sender, OfflinePlayer recipient, long shares) {
        if (Objects.equals(sender.getUniqueId(), recipient.getUniqueId())) return false;

        var senderBefore = getNumberOfShares(sender);
        var senderAfter = senderBefore - shares;

        var recipientBefore = getNumberOfShares(recipient);
        var recipientAfter = recipientBefore + shares;

        if (senderAfter < 0) return false;

        var sumBefore = senderBefore + recipientBefore;
        var sumAfter = senderAfter + recipientAfter;

        if (sumBefore != sumAfter) return false;

        ownership.put(sender, senderAfter);
        ownership.put(recipient, recipientAfter);

        return true;
    }

    /**
     * Issues new shares to the recipient.
     *
     * @param recipient The recipient
     * @param shares    The number of shares to issue
     */
    public void issueShares(OfflinePlayer recipient, long shares) {
        var sharesBefore = getNumberOfShares(recipient);
        var sharesAfter = sharesBefore + shares;

        ownership.put(recipient, sharesAfter);
    }

    /**
     * Retires shares at the expense of the sender.
     *
     * @param sender The sender
     * @param shares The number of shares to retire
     * @return {@code true} if the operation was successful
     */
    public boolean retireShares(OfflinePlayer sender, long shares) {
        var sharesBefore = getNumberOfShares(sender);
        var sharesAfter = sharesBefore - shares;

        if (sharesAfter < 0) return false;

        var totalSharesBefore = getOutstandingShares();
        var totalSharesAfter = totalSharesBefore - shares;

        if (totalSharesAfter < 1) return false;

        ownership.put(sender, sharesAfter);
        return true;
    }
}

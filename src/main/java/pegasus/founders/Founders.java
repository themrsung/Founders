package pegasus.founders;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Founders extends JavaPlugin {
    //
    // Vault
    //

    /**
     * Returns the economy.
     * @return The economy
     * @throws NullPointerException When the Vault economy provider is not found, or has not been loaded
     */
    public static Economy getEconomy() {
        return Objects.requireNonNull(economy, "Vault Economy provider not found.");
    }

    /**
     * The server economy.
     */
    private static Economy economy;

    @Override
    public void onEnable() {
        getLogger().info("Enabling Founders...");

        // Vault setup

        var rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().warning("VaultAPI economy not found. Plugin has failed to start.");
            Bukkit.getPluginManager().disablePlugin(this);

            return;
        }

        economy = rsp.getProvider();

        getLogger().info("Founders enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling Founders...");

        getLogger().info("Founders disabled!");
    }
}

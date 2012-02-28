package fr.aumgn.motd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MotdsManager {

    private List<MotdsProvider> providers;
    private List<ConditionalMotdsProvider> conditionalProviders;
    private int totalSize;

    public MotdsManager() {
        this.providers = new ArrayList<MotdsProvider>();
        this.conditionalProviders = new ArrayList<ConditionalMotdsProvider>();
        load();
    }

    public void load() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin instanceof RandomMotdPlugin) {
                RandomMotdPlugin pl = (RandomMotdPlugin) plugin;
                List<? extends MotdsProvider> list = pl.getMotdsProviders();
                if (list != null) {
                    providers.addAll(list);
                }
                List<? extends ConditionalMotdsProvider> cList = pl.getConditionalMotdsProviders();
                if (cList != null) {
                    conditionalProviders.addAll(cList);
                }
            }
        }
        update();
    }

    public void update() {
        totalSize = 0;
        for (MotdsProvider provider : providers) {
            totalSize += provider.size();
        }
    }

    public int size() {
        return totalSize;
    }

    public String get() {
        // Handles conditional providers.
        for (ConditionalMotdsProvider provider : conditionalProviders) {
            if (provider.isActive()) {
                return provider.get();
            }
        }

        // Handles regular providers.
        if (totalSize > 0) {
            int i = RandomMotd.getRand().nextInt(totalSize);
            for (MotdsProvider provider : providers) {
                int size = provider.size();
                if (i < size) {
                    return provider.get(i);
                } else {
                    i -= size;
                }
            }
        }

        // Should never be reached.
        return null;
    }

}

package fr.aumgn.motd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import fr.aumgn.motd.source.ConditionalMotdsSource;
import fr.aumgn.motd.source.MotdsSource;
import fr.aumgn.motd.source.MotdsSourceProvider;

public class MotdsManager {

    private List<MotdsSource> providers;
    private List<ConditionalMotdsSource> conditionalProviders;
    private int totalSize;

    public MotdsManager() {
        this.providers = new ArrayList<MotdsSource>();
        this.conditionalProviders = new ArrayList<ConditionalMotdsSource>();
        load();
    }

    public void load() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin instanceof MotdsSourceProvider) {
                MotdsSourceProvider pl = (MotdsSourceProvider) plugin;
                List<? extends MotdsSource> list = pl.getMotdsProviders();
                if (list != null) {
                    providers.addAll(list);
                }
                List<? extends ConditionalMotdsSource> cList = pl.getConditionalMotdsProviders();
                if (cList != null) {
                    conditionalProviders.addAll(cList);
                }
            }
        }
        update();
    }

    public void update() {
        totalSize = 0;
        for (MotdsSource provider : providers) {
            totalSize += provider.size();
        }
    }

    public int size() {
        return totalSize;
    }

    public String get() {
        // Handles conditional providers.
        for (ConditionalMotdsSource provider : conditionalProviders) {
            if (provider.isActive()) {
                return provider.get();
            }
        }

        // Handles regular providers.
        if (totalSize > 0) {
            int i = RandomMotd.getRand().nextInt(totalSize);
            for (MotdsSource provider : providers) {
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

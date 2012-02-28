package fr.aumgn.motd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.motd.provider.ConfigMotdsProvider;

public class RandomMotd extends JavaPlugin implements Listener, RandomMotdPlugin {

    private static Random rand = new Random();
    private static MotdsManager motdsManager;

    public static Random getRand() {
        return rand;
    }

    public static void update() {
        motdsManager.update();
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String motd = motdsManager.get();
        if (motd != null) {
            event.setMotd(motd);
        }
    }

    @Override
    public void onEnable() {
        motdsManager = new MotdsManager();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled (" + motdsManager.size() + " motds loaded).");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    @Override
    public List<MotdsProvider> getMotdsProviders() {
        List<Object> list = getConfig().getList("motds");
        if (list != null) {
            List<MotdsProvider> providerList = new ArrayList<MotdsProvider>(1);
            providerList.add(new ConfigMotdsProvider(list));
            return providerList;
        }
        return null;
    }

    @Override
    public List<ConditionalMotdsProvider> getConditionalMotdsProviders() {
        return null;
    }

}

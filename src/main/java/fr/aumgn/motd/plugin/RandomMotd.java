package fr.aumgn.motd.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.motd.api.MotdsSource;
import fr.aumgn.motd.api.MotdsSourcesProvider;

public class RandomMotd extends JavaPlugin implements Listener, MotdsSourcesProvider {

    private static Random rand = new Random();
    private static MotdsManager motdsManager;

    public static Random getRand() {
        return rand;
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
    public List<MotdsSource> getMotdsSources() {
        List<Object> list = getConfig().getList("motds");
        if (list != null) {
            List<MotdsSource> providerList = new ArrayList<MotdsSource>(1);
            providerList.add(new DefaultMotdsSource(list));
            return providerList;
        }
        return null;
    }

}

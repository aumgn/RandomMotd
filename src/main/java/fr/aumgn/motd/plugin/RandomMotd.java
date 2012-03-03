package fr.aumgn.motd.plugin;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.motd.api.Motd;
import fr.aumgn.motd.api.MotdsLookupEvent;
import fr.aumgn.motd.api.MotdsLookupEvent.MotdPriority;

public class RandomMotd extends JavaPlugin implements Listener {

    private Random rand = new Random();
    private List<Motd> motds;
    private Motd forcedMotd; 

    @Override
    public void onEnable() {
        int size = reloadMotds();
        forcedMotd = null;
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginCommand("motd").setExecutor(new MotdCommand(this));
        getLogger().info("Enabled (" + size + " motds loaded in config.yml).");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        MotdsLookupEvent motdEvent = new MotdsLookupEvent();
        Bukkit.getPluginManager().callEvent(motdEvent);
        String motdContent = null;
        Collection<Motd> motds = motdEvent.getMotds();
        int index = rand.nextInt(motds.size());
        for (Motd motd : motds) {
            if (index < motd.getWeight()) {
                motdContent = motd.getContent();
                break;
            } else {
                index -= motd.getWeight();
            }
        }
        if (motdContent != null) {
            event.setMotd(motdContent);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onForcedMotdLookup(MotdsLookupEvent event) {
        if (forcedMotd != null) {
            event.add(forcedMotd, MotdPriority.Highest);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMotdLookup(MotdsLookupEvent event) {
        event.add(motds);
    }

    int reloadMotds() {
        reloadConfig();
        motds = new ConfigMotdsReader(getConfig()).getMotds();
        return motds.size();
    }

    void forceMotd(Motd motd) {
        forcedMotd = motd;
    }

}

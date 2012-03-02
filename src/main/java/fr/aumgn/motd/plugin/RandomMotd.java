package fr.aumgn.motd.plugin;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.motd.api.Motd;
import fr.aumgn.motd.api.MotdLookupEvent;

public class RandomMotd extends JavaPlugin implements Listener {

    private Random rand = new Random();
    private List<Motd> motds;

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        MotdLookupEvent motdEvent = new MotdLookupEvent();
        Bukkit.getPluginManager().callEvent(motdEvent);
        String motdContent = null;
        Collection<Motd> motds = motdEvent.getMotds();
        int index = rand.nextInt(motds.size());
        for (Motd motd : motdEvent.getMotds()) {
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

    @EventHandler
    public void onMotdLookup(MotdLookupEvent event) {
        event.registerMotds(motds);
    }

    @Override
    public void onEnable() {
        motds = new ConfigMotdsReader(getConfig()).getMotds();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled (" + motds.size() + " motds loaded in config.yml).");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

}

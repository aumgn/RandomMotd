package fr.aumgn.motd.provider;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import fr.aumgn.motd.MotdsProvider;

public class ConfigProvider implements MotdsProvider {

    private class Motd {
        public String content;
        public int weight;
    }

    private Motd[] motds;
    private int totalSize;

    public ConfigProvider(ConfigurationSection section) {
        load(section);
    }

    public void load(ConfigurationSection section) {
        List<Motd> motdsList = new ArrayList<Motd>();
        for (String key : section.getKeys(false)) {
            if (section.isString(key)) {
                motdsList.add(createMotd(section.getString(key)));
            } else if (section.isConfigurationSection(key)) {
                ConfigurationSection motdSection = section.getConfigurationSection(key);
                if (motdSection.isString("content")) {
                    String content = motdSection.getString("content");
                    if (section.isInt("weight")) {
                        motdsList.add(createMotd(content, section.getInt("weight")));
                    } else {
                        motdsList.add(createMotd(content));
                    }
                }                
            }
        }
        motds = (Motd[]) motdsList.toArray();
    }

    private Motd createMotd(String content, int weight) {
        Motd motd = new Motd();
        motd.content = content;
        motd.weight = weight;
        totalSize += weight;
        return motd;
    }

    private Motd createMotd(String content) {
        return createMotd(content, 1);
    }

    @Override
    public int size() {
        return totalSize;
    }

    @Override
    public String get(int index) {
        int i = index;
        for (Motd motd : motds) {
            if (i < motd.weight) {
                return motd.content;
            } else {
                i -= motd.weight;
            }
        }
        return null;
    }

}

package fr.aumgn.motd.provider;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import fr.aumgn.motd.MotdsProvider;

public class ConfigMotdsProvider implements MotdsProvider {

    private class Motd {
        public String content;
        public int weight;
    }

    private List<Motd> motds;
    private int totalSize;

    public ConfigMotdsProvider(List<Object> list) {
        load(list);
    }

    public void load(List<Object> list) {
        motds = new ArrayList<Motd>();
        for (Object motd : list) {
            if (motd instanceof String) {
                motds.add(createMotd((String) motd));
            } else if (motd instanceof ConfigurationSection) {
                ConfigurationSection motdSection = (ConfigurationSection) motd;
                if (motdSection.isString("content")) {
                    String content = motdSection.getString("content");
                    if (motdSection.isInt("weight")) {
                        motds.add(createMotd(content, motdSection.getInt("weight")));
                    } else {
                        motds.add(createMotd(content));
                    }
                }
            }
        }
        System.out.println(motds.size());
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

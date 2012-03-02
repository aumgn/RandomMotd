package fr.aumgn.motd.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.Configuration;

import fr.aumgn.motd.api.Motd;

public class ConfigMotdsReader {

    private static final String MOTD_KEY = "motds";
    private static final String CONTENT_KEY = "content";
    private static final String WEIGHT_KEY = "weight";

    private List<Motd> motds;

    public ConfigMotdsReader(Configuration config) {
        if (config.isList(MOTD_KEY)) {
            load(config.getList(MOTD_KEY));
        } else {
            motds = new ArrayList<Motd>();
        }
    }

    public void load(List<?> list) {
        motds = new ArrayList<Motd>();
        for (Object obj : list) {
            Motd motd = loadMotd(obj);
            if (motd != null) {
                motds.add(motd);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Motd loadMotd(Object obj) {
        if (obj instanceof String) {
            return loadMotdFromString((String) obj);
        }
        if (obj instanceof Map) {
            return loadMotdFromMap((Map<String, Object>) obj);
        }
        return null;
    }

    private Motd loadMotdFromString(String str) {
        return new Motd(str);
    }

    private Motd loadMotdFromMap(Map<String, Object> map) {
        if (map.containsKey(CONTENT_KEY) && map.get(CONTENT_KEY) instanceof String) {
            String content = (String) map.get(CONTENT_KEY);
            if (map.containsKey(WEIGHT_KEY) && map.get(WEIGHT_KEY) instanceof Integer) {
                return new Motd(content, (Integer) map.get(WEIGHT_KEY));
            } else {
                return new Motd(content);
            }
        }
        return null;
    }

    public List<Motd> getMotds() {
        return motds;
    }

}

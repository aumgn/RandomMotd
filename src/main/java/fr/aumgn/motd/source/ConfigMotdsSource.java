package fr.aumgn.motd.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigMotdsSource extends SimpleMotdsSource {

    private static final String CONTENT_KEY = "content";
    private static final String WEIGHT_KEY = "weight";

    public ConfigMotdsSource(List<Object> list) {
        load(list);
    }

    public void load(List<Object> list) {
        motds = new ArrayList<Motd>();
        for (Object obj : list) {
            Motd motd = loadMotd(obj);
            if (motd != null) {
                motds.add(motd);
                totalWeight += motd.weight;
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

}

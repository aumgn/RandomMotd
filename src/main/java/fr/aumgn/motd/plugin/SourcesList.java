package fr.aumgn.motd.plugin;

import java.util.ArrayList;
import java.util.List;

import fr.aumgn.motd.api.MotdsSource;

class SourcesList {

    private List<MotdsSource> sources;

    public SourcesList() {
        this.sources = new ArrayList<MotdsSource>();
    }

    public void add(MotdsSource source) {
        sources.add(source);
    }

    public int size() {
        int size = 0;
        for (MotdsSource source : sources) {
            size += source.size();
        }
        return size;
    }

    public int weight() {
        int weight = 0;
        for (MotdsSource source : sources) {
            weight += source.weight();
        }
        return weight;
    }

    public String get(int index) {
        int i = index;
        for (MotdsSource source : sources) {
            int weight = source.weight();
            if (i < weight) {
                return source.get(i);
            } else {
                i -= weight;
            }
        }
        return null;
    }

}

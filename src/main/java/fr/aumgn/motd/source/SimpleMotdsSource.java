package fr.aumgn.motd.source;

import java.util.List;

public abstract class SimpleMotdsSource implements MotdsSource {

    protected static class Motd {

        public String content;
        public int weight;

        public Motd(String content, int weight) {
            this.content = content;
            this.weight = weight;
        }

        public Motd(String content) {
            this(content, 1);
        }

    }

    protected List<Motd> motds;
    protected int totalWeight;

    @Override
    public int size() {
        return motds.size();
    }

    @Override
    public int weight() {
        return totalWeight;
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

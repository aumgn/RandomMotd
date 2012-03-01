package fr.aumgn.motd.source;

public interface MotdsSource {

    public enum Priority {
        Normal, 
        High,
        Highest
    }

    int size();

    int weight();

    String get(int index);

}

package fr.aumgn.motd.api;

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

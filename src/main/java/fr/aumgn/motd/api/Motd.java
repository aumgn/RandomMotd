package fr.aumgn.motd.api;

public class Motd {

    private String content;
    private int weight;

    public Motd(String content) {
        this(content, 1);
    }

    public Motd(String content, int weight) {
        this.content = content;
        this.weight = weight;
    }

    public String getContent() {
        return content;
    }

    public int getWeight() {
        return weight;
    }

}

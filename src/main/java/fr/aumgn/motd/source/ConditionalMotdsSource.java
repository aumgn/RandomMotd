package fr.aumgn.motd.source;

public interface ConditionalMotdsSource {

    public boolean isActive();

    public String get();

}

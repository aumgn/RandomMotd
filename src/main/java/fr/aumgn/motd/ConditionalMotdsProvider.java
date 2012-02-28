package fr.aumgn.motd;

public interface ConditionalMotdsProvider {

    public boolean isActive();

    public String get();

}

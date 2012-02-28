package fr.aumgn.motd;

public interface MotdsProvider {

    int size();

    String get(int index);

}

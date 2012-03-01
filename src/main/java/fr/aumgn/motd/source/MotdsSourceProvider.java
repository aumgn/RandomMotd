package fr.aumgn.motd.source;

import java.util.List;

public interface MotdsSourceProvider {

    List<MotdsSource> getMotdsProviders();

    List<ConditionalMotdsSource> getConditionalMotdsProviders();

}

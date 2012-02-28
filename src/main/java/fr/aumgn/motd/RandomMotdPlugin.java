package fr.aumgn.motd;

import java.util.List;

public interface RandomMotdPlugin {

    List<MotdsProvider> getMotdsProviders();

    List<ConditionalMotdsProvider> getConditionalMotdsProviders();

}

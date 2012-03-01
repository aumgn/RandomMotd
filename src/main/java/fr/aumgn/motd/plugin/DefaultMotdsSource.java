package fr.aumgn.motd.plugin;

import java.util.List;

import fr.aumgn.motd.api.ConfigMotdsSource;
import fr.aumgn.motd.api.MotdsSourcePriority;
import fr.aumgn.motd.api.MotdsSource.Priority;

@MotdsSourcePriority(Priority.Normal)
public class DefaultMotdsSource extends ConfigMotdsSource {

    public DefaultMotdsSource(List<Object> list) {
        super(list);
    }

}

package fr.aumgn.motd;

import java.util.List;

import fr.aumgn.motd.source.MotdsSource.Priority;
import fr.aumgn.motd.source.ConfigMotdsSource;
import fr.aumgn.motd.source.MotdsSourcePriority;

@MotdsSourcePriority(Priority.Normal)
public class DefaultMotdsSource extends ConfigMotdsSource {

    public DefaultMotdsSource(List<Object> list) {
        super(list);
    }

}

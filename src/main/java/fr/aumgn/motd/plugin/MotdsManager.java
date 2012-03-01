package fr.aumgn.motd.plugin;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import fr.aumgn.motd.api.MotdsSource;
import fr.aumgn.motd.api.MotdsSourcePriority;
import fr.aumgn.motd.api.MotdsSourcesProvider;
import fr.aumgn.motd.api.MotdsSource.Priority;

class MotdsManager {

    private SourcesList[] sourcesByPriority;

    public MotdsManager() {
        Priority[] priorities = Priority.values();
        this.sourcesByPriority = new SourcesList[priorities.length];
        for (Priority priority : priorities) {
            this.sourcesByPriority[getPriorityIndexFor(priority)] = new SourcesList();
        }
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin instanceof MotdsSourcesProvider) {
                loadProvider((MotdsSourcesProvider) plugin);
            }
        }
    }

    private int getPriorityIndexFor(Priority priority) {
        switch (priority) {
        case Highest:
            return 0;
        case High:
            return 1;
        default:
            return 2;
        }
    }

    private int getPriorityIndexFor(MotdsSource source) {
        MotdsSourcePriority priorityAnnotation = source.getClass().getAnnotation(MotdsSourcePriority.class);
        MotdsSource.Priority priority;
        if (priorityAnnotation == null) {
            priority = MotdsSource.Priority.Normal;
        } else {
            priority = priorityAnnotation.value();
        }
        return getPriorityIndexFor(priority);        
    }

    private void loadProvider(MotdsSourcesProvider plugin) {
        MotdsSourcesProvider sourcesProvider = (MotdsSourcesProvider) plugin;
        List<? extends MotdsSource> sources = sourcesProvider.getMotdsSources();
        if (sources != null) {
            for (MotdsSource source : sources) {
                int index = getPriorityIndexFor(source);
                sourcesByPriority[index].add(source);
            }
        }
    }

    public int size() {
        int totalSize = 0;
        for (SourcesList sources : sourcesByPriority) {
            totalSize += sources.size();
        }
        return totalSize;
    }

    public String get() {
        for (SourcesList source : sourcesByPriority) {
            int size = source.weight();
            if (size > 0) {
                return source.get(RandomMotd.getRand().nextInt(size));
            }
        }

        // Should never be reached.
        return null;
    }

}

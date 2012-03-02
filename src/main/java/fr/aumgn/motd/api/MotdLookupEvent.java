package fr.aumgn.motd.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("serial")
public class MotdLookupEvent extends Event {

    public enum MotdPriority {
        Normal(0),
        High(1),
        Highest(2);

        private char value;

        private MotdPriority(int value) {
            this.value = (char) value;
        }
    }

    private static final HandlerList handlers = new HandlerList();
    private MotdPriority currentPriority;
    private List<Motd> motds;

    public MotdLookupEvent() {
        super();
        motds = new ArrayList<Motd>();
        currentPriority = MotdPriority.Normal;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void registerMotds(List<Motd> motdsToAdd, MotdPriority priority) {
        if (priority.value < currentPriority.value) {
            return;
        }
        if (priority.value > currentPriority.value) {
            motds = new ArrayList<Motd>();
            currentPriority = priority;
        }
        motds.addAll(motdsToAdd);
    }

    public void registerMotds(List<Motd> motdsToAdd) {
        this.registerMotds(motdsToAdd, MotdPriority.Normal);
    }

    public Collection<Motd> getMotds() {
        return Collections.unmodifiableCollection(motds);
    }

}

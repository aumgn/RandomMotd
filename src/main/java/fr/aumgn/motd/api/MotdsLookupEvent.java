package fr.aumgn.motd.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("serial")
public class MotdsLookupEvent extends Event {

    public enum MotdPriority {
        Lowest(0),
        Low(1),
        Normal(2),
        High(3),
        Highest(4);

        private char value;

        private MotdPriority(int value) {
            this.value = (char) value;
        }

    }

    private static final HandlerList handlers = new HandlerList();
    private MotdPriority currentPriority;
    private List<Motd> motds;

    public MotdsLookupEvent() {
        super();
        motds = new ArrayList<Motd>();
        currentPriority = MotdPriority.Lowest;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private boolean handlePriority(MotdPriority priority) {
        if (priority.value < currentPriority.value) {
            return false;
        }
        if (priority.value > currentPriority.value) {
            motds = new ArrayList<Motd>();
            currentPriority = priority;
        }
        return true;
    }

    public void add(Motd motdToAdd, MotdPriority priority) {
        if (handlePriority(priority)) {
            motds.add(motdToAdd);
        }
    }

    public void add(Motd motdToAdd) {
        this.add(motdToAdd, MotdPriority.Lowest);
    }

    public void add(List<Motd> motdsToAdd, MotdPriority priority) {
        if (handlePriority(priority)) {
            motds.addAll(motdsToAdd);
        }
    }

    public void add(List<Motd> motdsToAdd) {
        this.add(motdsToAdd, MotdPriority.Lowest);
    }

    public Collection<Motd> getMotds() {
        return Collections.unmodifiableCollection(motds);
    }

}

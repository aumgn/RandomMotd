package fr.aumgn.motd.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.aumgn.motd.api.Motd;

public class MotdCommand implements CommandExecutor {

    private final RandomMotd plugin; 

    public MotdCommand(RandomMotd plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equals("reload")) {
            return reloadCommand(sender, cmd, lbl, args);
        } else if (args[0].equals("set")) {
            return setCommand(sender, cmd, lbl, args);
        }
        return false;
    }

    private boolean reloadCommand(CommandSender sender, Command cmd, String lbl, String[] args) { 
        if (args.length > 1) {
            return false;
        }

        int size = plugin.reloadMotds();
        sender.sendMessage(size + " motds chargés avec succés.");
        return true;
    }

    private boolean setCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (args.length == 1) {
            plugin.forceMotd(null);
            sender.sendMessage("Motd principal supprimé.");
        } else {
            StringBuilder builder = new StringBuilder();
            int i = 1;
            for (; i < args.length-1; i++) {
                builder.append(args[i]);
                builder.append(" ");
            }
            builder.append(args[i]);
            plugin.forceMotd(new Motd(builder.toString()));
            sender.sendMessage("Motd mise a jour avec succés.");
        }
        return true;
    }

}

package me.qintinator.sleepmost.commands.subcommands;

import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisableSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public DisableSubCommand(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            messageService.sendMessage(sender, Message.commandOnlyForPlayers, true);
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if (!sleepService.enabledForWorld(world)) {
            messageService.sendMessage(sender, Message.alreadyDisabledForWorld, true);
            return true;
        }

        sleepService.disableForWorld(world);
        messageService.sendMessage(sender, Message.disabledForWorld, true);
        return true;
    }
}


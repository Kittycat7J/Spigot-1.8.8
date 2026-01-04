package net.minecraft.command;

import net.custom.DBLogger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandRNGLog extends CommandBase {

    @Override
    public String getCommandName() {
        return "RNGLog";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/RNGLog <start|stop>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.addChatMessage(new ChatComponentText("§cUsage: /RNGLog <start|stop>"));
            return;
        }

        if ("start".equalsIgnoreCase(args[0])) {
            if (DBLogger.isRunning()) {
                sender.addChatMessage(new ChatComponentText("§eRNG logging is already running."));
            } else {
                DBLogger.start();
                sender.addChatMessage(new ChatComponentText("§aRNG logging started."));
            }
            return;
        }

        if ("stop".equalsIgnoreCase(args[0])) {
            if (!DBLogger.isRunning()) {
                sender.addChatMessage(new ChatComponentText("§eRNG logging is not running."));
            } else {
                DBLogger.stop();
                sender.addChatMessage(new ChatComponentText("§cRNG logging stopped."));
            }
            return;
        }

        sender.addChatMessage(new ChatComponentText("§cInvalid argument. Use start or stop."));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // OP only
    }
}

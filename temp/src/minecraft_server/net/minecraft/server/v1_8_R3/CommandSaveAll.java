package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.IProgressUpdate;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldServer;

public class CommandSaveAll extends CommandAbstract {
   public String getCommand() {
      return "save-all";
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.save.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      p_execute_1_.sendMessage(new ChatMessage("commands.save.start", new Object[0]));
      if(minecraftserver.getPlayerList() != null) {
         minecraftserver.getPlayerList().savePlayers();
      }

      try {
         for(int i = 0; i < minecraftserver.worldServer.length; ++i) {
            if(minecraftserver.worldServer[i] != null) {
               WorldServer worldserver = minecraftserver.worldServer[i];
               boolean flag = worldserver.savingDisabled;
               worldserver.savingDisabled = false;
               worldserver.save(true, (IProgressUpdate)null);
               worldserver.savingDisabled = flag;
            }
         }

         if(p_execute_2_.length > 0 && "flush".equals(p_execute_2_[0])) {
            p_execute_1_.sendMessage(new ChatMessage("commands.save.flushStart", new Object[0]));

            for(int j = 0; j < minecraftserver.worldServer.length; ++j) {
               if(minecraftserver.worldServer[j] != null) {
                  WorldServer worldserver1 = minecraftserver.worldServer[j];
                  boolean flag1 = worldserver1.savingDisabled;
                  worldserver1.savingDisabled = false;
                  worldserver1.flushSave();
                  worldserver1.savingDisabled = flag1;
               }
            }

            p_execute_1_.sendMessage(new ChatMessage("commands.save.flushEnd", new Object[0]));
         }
      } catch (ExceptionWorldConflict exceptionworldconflict) {
         a(p_execute_1_, this, "commands.save.failed", new Object[]{exceptionworldconflict.getMessage()});
         return;
      }

      a(p_execute_1_, this, "commands.save.success", new Object[0]);
   }
}

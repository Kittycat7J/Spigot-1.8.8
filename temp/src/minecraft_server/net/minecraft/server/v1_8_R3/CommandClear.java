package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class CommandClear extends CommandAbstract {
   public String getCommand() {
      return "clear";
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.clear.usage";
   }

   public int a() {
      return 2;
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      EntityPlayer entityplayer = p_execute_2_.length == 0?b(p_execute_1_):a(p_execute_1_, p_execute_2_[0]);
      Item item = p_execute_2_.length >= 2?f(p_execute_1_, p_execute_2_[1]):null;
      int i = p_execute_2_.length >= 3?a(p_execute_2_[2], -1):-1;
      int j = p_execute_2_.length >= 4?a(p_execute_2_[3], -1):-1;
      NBTTagCompound nbttagcompound = null;
      if(p_execute_2_.length >= 5) {
         try {
            nbttagcompound = MojangsonParser.parse(a(p_execute_2_, 4));
         } catch (MojangsonParseException mojangsonparseexception) {
            throw new CommandException("commands.clear.tagError", new Object[]{mojangsonparseexception.getMessage()});
         }
      }

      if(p_execute_2_.length >= 2 && item == null) {
         throw new CommandException("commands.clear.failure", new Object[]{entityplayer.getName()});
      } else {
         int k = entityplayer.inventory.a(item, i, j, nbttagcompound);
         entityplayer.defaultContainer.b();
         if(!entityplayer.abilities.canInstantlyBuild) {
            entityplayer.broadcastCarriedItem();
         }

         p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, k);
         if(k == 0) {
            throw new CommandException("commands.clear.failure", new Object[]{entityplayer.getName()});
         } else {
            if(j == 0) {
               p_execute_1_.sendMessage(new ChatMessage("commands.clear.testing", new Object[]{entityplayer.getName(), Integer.valueOf(k)}));
            } else {
               a(p_execute_1_, this, "commands.clear.success", new Object[]{entityplayer.getName(), Integer.valueOf(k)});
            }

         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, this.d()):(p_tabComplete_2_.length == 2?a(p_tabComplete_2_, Item.REGISTRY.keySet()):null);
   }

   protected String[] d() {
      return MinecraftServer.getServer().getPlayers();
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 0;
   }
}

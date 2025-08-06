package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentUtils;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionInvalidSyntax;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CommandTellRaw extends CommandAbstract {
   public String getCommand() {
      return "tellraw";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.tellraw.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 2) {
         throw new ExceptionUsage("commands.tellraw.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer = a(p_execute_1_, p_execute_2_[0]);
         String s = a(p_execute_2_, 1);

         try {
            IChatBaseComponent ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(s);
            entityplayer.sendMessage(ChatComponentUtils.filterForDisplay(p_execute_1_, ichatbasecomponent, entityplayer));
         } catch (JsonParseException jsonparseexception) {
            Throwable throwable = ExceptionUtils.getRootCause(jsonparseexception);
            throw new ExceptionInvalidSyntax("commands.tellraw.jsonException", new Object[]{throwable == null?"":throwable.getMessage()});
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()):null;
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 0;
   }
}

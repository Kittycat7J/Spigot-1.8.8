package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.ExceptionPlayerNotFound;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandTell extends CommandAbstract {
   public List<String> b() {
      return Arrays.asList(new String[]{"w", "msg"});
   }

   public String getCommand() {
      return "tell";
   }

   public int a() {
      return 0;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.message.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 2) {
         throw new ExceptionUsage("commands.message.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer = a(p_execute_1_, p_execute_2_[0]);
         if(entityplayer == p_execute_1_) {
            throw new ExceptionPlayerNotFound("commands.message.sameTarget", new Object[0]);
         } else {
            IChatBaseComponent ichatbasecomponent = b(p_execute_1_, p_execute_2_, 1, !(p_execute_1_ instanceof EntityHuman));
            ChatMessage chatmessage = new ChatMessage("commands.message.display.incoming", new Object[]{p_execute_1_.getScoreboardDisplayName(), ichatbasecomponent.f()});
            ChatMessage chatmessage1 = new ChatMessage("commands.message.display.outgoing", new Object[]{entityplayer.getScoreboardDisplayName(), ichatbasecomponent.f()});
            chatmessage.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
            chatmessage1.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
            entityplayer.sendMessage(chatmessage);
            p_execute_1_.sendMessage(chatmessage1);
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 0;
   }
}

package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.ExceptionInvalidNumber;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandDifficulty extends CommandAbstract {
   public String getCommand() {
      return "difficulty";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.difficulty.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length <= 0) {
         throw new ExceptionUsage("commands.difficulty.usage", new Object[0]);
      } else {
         EnumDifficulty enumdifficulty = this.e(p_execute_2_[0]);
         MinecraftServer.getServer().a(enumdifficulty);
         a(p_execute_1_, this, "commands.difficulty.success", new Object[]{new ChatMessage(enumdifficulty.b(), new Object[0])});
      }
   }

   protected EnumDifficulty e(String p_e_1_) throws ExceptionInvalidNumber {
      return !p_e_1_.equalsIgnoreCase("peaceful") && !p_e_1_.equalsIgnoreCase("p")?(!p_e_1_.equalsIgnoreCase("easy") && !p_e_1_.equalsIgnoreCase("e")?(!p_e_1_.equalsIgnoreCase("normal") && !p_e_1_.equalsIgnoreCase("n")?(!p_e_1_.equalsIgnoreCase("hard") && !p_e_1_.equalsIgnoreCase("h")?EnumDifficulty.getById(a(p_e_1_, 0, 3)):EnumDifficulty.HARD):EnumDifficulty.NORMAL):EnumDifficulty.EASY):EnumDifficulty.PEACEFUL;
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, new String[]{"peaceful", "easy", "normal", "hard"}):null;
   }
}

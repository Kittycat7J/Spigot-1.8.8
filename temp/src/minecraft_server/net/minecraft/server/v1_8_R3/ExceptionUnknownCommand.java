package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CommandException;

public class ExceptionUnknownCommand extends CommandException {
   public ExceptionUnknownCommand() {
      this("commands.generic.notFound", new Object[0]);
   }

   public ExceptionUnknownCommand(String p_i875_1_, Object... p_i875_2_) {
      super(p_i875_1_, p_i875_2_);
   }
}

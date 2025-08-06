package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CommandException;

public class ExceptionPlayerNotFound extends CommandException {
   public ExceptionPlayerNotFound() {
      this("commands.generic.player.notFound", new Object[0]);
   }

   public ExceptionPlayerNotFound(String p_i874_1_, Object... p_i874_2_) {
      super(p_i874_1_, p_i874_2_);
   }
}

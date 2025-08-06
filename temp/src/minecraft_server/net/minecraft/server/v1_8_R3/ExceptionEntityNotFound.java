package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CommandException;

public class ExceptionEntityNotFound extends CommandException {
   public ExceptionEntityNotFound() {
      this("commands.generic.entity.notFound", new Object[0]);
   }

   public ExceptionEntityNotFound(String p_i871_1_, Object... p_i871_2_) {
      super(p_i871_1_, p_i871_2_);
   }
}

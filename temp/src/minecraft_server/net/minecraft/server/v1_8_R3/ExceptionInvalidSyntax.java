package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CommandException;

public class ExceptionInvalidSyntax extends CommandException {
   public ExceptionInvalidSyntax() {
      this("commands.generic.snytax", new Object[0]);
   }

   public ExceptionInvalidSyntax(String p_i873_1_, Object... p_i873_2_) {
      super(p_i873_1_, p_i873_2_);
   }
}

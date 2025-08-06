package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ICommand;
import net.minecraft.server.v1_8_R3.ICommandListener;

public interface ICommandDispatcher {
   void a(ICommandListener var1, ICommand var2, int var3, String var4, Object... var5);
}

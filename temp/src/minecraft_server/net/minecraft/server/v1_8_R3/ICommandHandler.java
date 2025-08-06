package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ICommand;
import net.minecraft.server.v1_8_R3.ICommandListener;

public interface ICommandHandler {
   int a(ICommandListener var1, String var2);

   List<String> a(ICommandListener var1, String var2, BlockPosition var3);

   List<ICommand> a(ICommandListener var1);

   Map<String, ICommand> getCommands();
}

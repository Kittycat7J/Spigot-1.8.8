package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class CommandBlockData extends CommandAbstract {
   public String getCommand() {
      return "blockdata";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.blockdata.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 4) {
         throw new ExceptionUsage("commands.blockdata.usage", new Object[0]);
      } else {
         p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
         BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 0, false);
         World world = p_execute_1_.getWorld();
         if(!world.isLoaded(blockposition)) {
            throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
         } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if(tileentity == null) {
               throw new CommandException("commands.blockdata.notValid", new Object[0]);
            } else {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               tileentity.b(nbttagcompound);
               NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.clone();

               NBTTagCompound nbttagcompound2;
               try {
                  nbttagcompound2 = MojangsonParser.parse(a(p_execute_1_, p_execute_2_, 3).c());
               } catch (MojangsonParseException mojangsonparseexception) {
                  throw new CommandException("commands.blockdata.tagError", new Object[]{mojangsonparseexception.getMessage()});
               }

               nbttagcompound.a(nbttagcompound2);
               nbttagcompound.setInt("x", blockposition.getX());
               nbttagcompound.setInt("y", blockposition.getY());
               nbttagcompound.setInt("z", blockposition.getZ());
               if(nbttagcompound.equals(nbttagcompound1)) {
                  throw new CommandException("commands.blockdata.failed", new Object[]{nbttagcompound.toString()});
               } else {
                  tileentity.a(nbttagcompound);
                  tileentity.update();
                  world.notify(blockposition);
                  p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 1);
                  a(p_execute_1_, this, "commands.blockdata.success", new Object[]{nbttagcompound.toString()});
               }
            }
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length > 0 && p_tabComplete_2_.length <= 3?a(p_tabComplete_2_, 0, p_tabComplete_3_):null;
   }
}

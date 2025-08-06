package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.StructureBoundingBox;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class CommandTestForBlocks extends CommandAbstract {
   public String getCommand() {
      return "testforblocks";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.compare.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 9) {
         throw new ExceptionUsage("commands.compare.usage", new Object[0]);
      } else {
         p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
         BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 0, false);
         BlockPosition blockposition1 = a(p_execute_1_, p_execute_2_, 3, false);
         BlockPosition blockposition2 = a(p_execute_1_, p_execute_2_, 6, false);
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(blockposition, blockposition1);
         StructureBoundingBox structureboundingbox1 = new StructureBoundingBox(blockposition2, blockposition2.a(structureboundingbox.b()));
         int i = structureboundingbox.c() * structureboundingbox.d() * structureboundingbox.e();
         if(i > 524288) {
            throw new CommandException("commands.compare.tooManyBlocks", new Object[]{Integer.valueOf(i), Integer.valueOf(524288)});
         } else if(structureboundingbox.b >= 0 && structureboundingbox.e < 256 && structureboundingbox1.b >= 0 && structureboundingbox1.e < 256) {
            World world = p_execute_1_.getWorld();
            if(world.a(structureboundingbox) && world.a(structureboundingbox1)) {
               boolean flag = false;
               if(p_execute_2_.length > 9 && p_execute_2_[9].equals("masked")) {
                  flag = true;
               }

               i = 0;
               BlockPosition blockposition3 = new BlockPosition(structureboundingbox1.a - structureboundingbox.a, structureboundingbox1.b - structureboundingbox.b, structureboundingbox1.c - structureboundingbox.c);
               BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
               BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();

               for(int j = structureboundingbox.c; j <= structureboundingbox.f; ++j) {
                  for(int k = structureboundingbox.b; k <= structureboundingbox.e; ++k) {
                     for(int l = structureboundingbox.a; l <= structureboundingbox.d; ++l) {
                        blockposition$mutableblockposition.c(l, k, j);
                        blockposition$mutableblockposition1.c(l + blockposition3.getX(), k + blockposition3.getY(), j + blockposition3.getZ());
                        boolean flag1 = false;
                        IBlockData iblockdata = world.getType(blockposition$mutableblockposition);
                        if(!flag || iblockdata.getBlock() != Blocks.AIR) {
                           if(iblockdata == world.getType(blockposition$mutableblockposition1)) {
                              TileEntity tileentity = world.getTileEntity(blockposition$mutableblockposition);
                              TileEntity tileentity1 = world.getTileEntity(blockposition$mutableblockposition1);
                              if(tileentity != null && tileentity1 != null) {
                                 NBTTagCompound nbttagcompound = new NBTTagCompound();
                                 tileentity.b(nbttagcompound);
                                 nbttagcompound.remove("x");
                                 nbttagcompound.remove("y");
                                 nbttagcompound.remove("z");
                                 NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                                 tileentity1.b(nbttagcompound1);
                                 nbttagcompound1.remove("x");
                                 nbttagcompound1.remove("y");
                                 nbttagcompound1.remove("z");
                                 if(!nbttagcompound.equals(nbttagcompound1)) {
                                    flag1 = true;
                                 }
                              } else if(tileentity != null) {
                                 flag1 = true;
                              }
                           } else {
                              flag1 = true;
                           }

                           ++i;
                           if(flag1) {
                              throw new CommandException("commands.compare.failed", new Object[0]);
                           }
                        }
                     }
                  }
               }

               p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, i);
               a(p_execute_1_, this, "commands.compare.success", new Object[]{Integer.valueOf(i)});
            } else {
               throw new CommandException("commands.compare.outOfWorld", new Object[0]);
            }
         } else {
            throw new CommandException("commands.compare.outOfWorld", new Object[0]);
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length > 0 && p_tabComplete_2_.length <= 3?a(p_tabComplete_2_, 0, p_tabComplete_3_):(p_tabComplete_2_.length > 3 && p_tabComplete_2_.length <= 6?a(p_tabComplete_2_, 3, p_tabComplete_3_):(p_tabComplete_2_.length > 6 && p_tabComplete_2_.length <= 9?a(p_tabComplete_2_, 6, p_tabComplete_3_):(p_tabComplete_2_.length == 10?a(p_tabComplete_2_, new String[]{"masked", "all"}):null)));
   }
}

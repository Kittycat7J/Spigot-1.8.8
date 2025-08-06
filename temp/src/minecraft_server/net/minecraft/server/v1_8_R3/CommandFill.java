package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class CommandFill extends CommandAbstract {
   public String getCommand() {
      return "fill";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.fill.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 7) {
         throw new ExceptionUsage("commands.fill.usage", new Object[0]);
      } else {
         p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
         BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 0, false);
         BlockPosition blockposition1 = a(p_execute_1_, p_execute_2_, 3, false);
         Block block = CommandAbstract.g(p_execute_1_, p_execute_2_[6]);
         int i = 0;
         if(p_execute_2_.length >= 8) {
            i = a(p_execute_2_[7], 0, 15);
         }

         BlockPosition blockposition2 = new BlockPosition(Math.min(blockposition.getX(), blockposition1.getX()), Math.min(blockposition.getY(), blockposition1.getY()), Math.min(blockposition.getZ(), blockposition1.getZ()));
         BlockPosition blockposition3 = new BlockPosition(Math.max(blockposition.getX(), blockposition1.getX()), Math.max(blockposition.getY(), blockposition1.getY()), Math.max(blockposition.getZ(), blockposition1.getZ()));
         int j = (blockposition3.getX() - blockposition2.getX() + 1) * (blockposition3.getY() - blockposition2.getY() + 1) * (blockposition3.getZ() - blockposition2.getZ() + 1);
         if(j > '\u8000') {
            throw new CommandException("commands.fill.tooManyBlocks", new Object[]{Integer.valueOf(j), Integer.valueOf('\u8000')});
         } else if(blockposition2.getY() >= 0 && blockposition3.getY() < 256) {
            World world = p_execute_1_.getWorld();

            for(int k = blockposition2.getZ(); k < blockposition3.getZ() + 16; k += 16) {
               for(int l = blockposition2.getX(); l < blockposition3.getX() + 16; l += 16) {
                  if(!world.isLoaded(new BlockPosition(l, blockposition3.getY() - blockposition2.getY(), k))) {
                     throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                  }
               }
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            boolean flag = false;
            if(p_execute_2_.length >= 10 && block.isTileEntity()) {
               String s = a(p_execute_1_, p_execute_2_, 9).c();

               try {
                  nbttagcompound = MojangsonParser.parse(s);
                  flag = true;
               } catch (MojangsonParseException mojangsonparseexception) {
                  throw new CommandException("commands.fill.tagError", new Object[]{mojangsonparseexception.getMessage()});
               }
            }

            ArrayList arraylist = Lists.newArrayList();
            j = 0;

            for(int i1 = blockposition2.getZ(); i1 <= blockposition3.getZ(); ++i1) {
               for(int j1 = blockposition2.getY(); j1 <= blockposition3.getY(); ++j1) {
                  for(int k1 = blockposition2.getX(); k1 <= blockposition3.getX(); ++k1) {
                     BlockPosition blockposition4 = new BlockPosition(k1, j1, i1);
                     if(p_execute_2_.length >= 9) {
                        if(!p_execute_2_[8].equals("outline") && !p_execute_2_[8].equals("hollow")) {
                           if(p_execute_2_[8].equals("destroy")) {
                              world.setAir(blockposition4, true);
                           } else if(p_execute_2_[8].equals("keep")) {
                              if(!world.isEmpty(blockposition4)) {
                                 continue;
                              }
                           } else if(p_execute_2_[8].equals("replace") && !block.isTileEntity()) {
                              if(p_execute_2_.length > 9) {
                                 Block block1 = CommandAbstract.g(p_execute_1_, p_execute_2_[9]);
                                 if(world.getType(blockposition4).getBlock() != block1) {
                                    continue;
                                 }
                              }

                              if(p_execute_2_.length > 10) {
                                 int l1 = CommandAbstract.a(p_execute_2_[10]);
                                 IBlockData iblockdata = world.getType(blockposition4);
                                 if(iblockdata.getBlock().toLegacyData(iblockdata) != l1) {
                                    continue;
                                 }
                              }
                           }
                        } else if(k1 != blockposition2.getX() && k1 != blockposition3.getX() && j1 != blockposition2.getY() && j1 != blockposition3.getY() && i1 != blockposition2.getZ() && i1 != blockposition3.getZ()) {
                           if(p_execute_2_[8].equals("hollow")) {
                              world.setTypeAndData(blockposition4, Blocks.AIR.getBlockData(), 2);
                              arraylist.add(blockposition4);
                           }
                           continue;
                        }
                     }

                     TileEntity tileentity1 = world.getTileEntity(blockposition4);
                     if(tileentity1 != null) {
                        if(tileentity1 instanceof IInventory) {
                           ((IInventory)tileentity1).l();
                        }

                        world.setTypeAndData(blockposition4, Blocks.BARRIER.getBlockData(), block == Blocks.BARRIER?2:4);
                     }

                     IBlockData iblockdata1 = block.fromLegacyData(i);
                     if(world.setTypeAndData(blockposition4, iblockdata1, 2)) {
                        arraylist.add(blockposition4);
                        ++j;
                        if(flag) {
                           TileEntity tileentity = world.getTileEntity(blockposition4);
                           if(tileentity != null) {
                              nbttagcompound.setInt("x", blockposition4.getX());
                              nbttagcompound.setInt("y", blockposition4.getY());
                              nbttagcompound.setInt("z", blockposition4.getZ());
                              tileentity.a(nbttagcompound);
                           }
                        }
                     }
                  }
               }
            }

            for(BlockPosition blockposition5 : arraylist) {
               Block block2 = world.getType(blockposition5).getBlock();
               world.update(blockposition5, block2);
            }

            if(j <= 0) {
               throw new CommandException("commands.fill.failed", new Object[0]);
            } else {
               p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, j);
               a(p_execute_1_, this, "commands.fill.success", new Object[]{Integer.valueOf(j)});
            }
         } else {
            throw new CommandException("commands.fill.outOfWorld", new Object[0]);
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length > 0 && p_tabComplete_2_.length <= 3?a(p_tabComplete_2_, 0, p_tabComplete_3_):(p_tabComplete_2_.length > 3 && p_tabComplete_2_.length <= 6?a(p_tabComplete_2_, 3, p_tabComplete_3_):(p_tabComplete_2_.length == 7?a(p_tabComplete_2_, Block.REGISTRY.keySet()):(p_tabComplete_2_.length == 9?a(p_tabComplete_2_, new String[]{"replace", "destroy", "keep", "hollow", "outline"}):(p_tabComplete_2_.length == 10 && "replace".equals(p_tabComplete_2_[8])?a(p_tabComplete_2_, Block.REGISTRY.keySet()):null))));
   }
}

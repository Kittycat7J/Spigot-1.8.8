package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.v1_8_R3.BaseBlockPosition;
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
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NextTickListEntry;
import net.minecraft.server.v1_8_R3.StructureBoundingBox;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class CommandClone extends CommandAbstract {
   public String getCommand() {
      return "clone";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.clone.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 9) {
         throw new ExceptionUsage("commands.clone.usage", new Object[0]);
      } else {
         p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
         BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 0, false);
         BlockPosition blockposition1 = a(p_execute_1_, p_execute_2_, 3, false);
         BlockPosition blockposition2 = a(p_execute_1_, p_execute_2_, 6, false);
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(blockposition, blockposition1);
         StructureBoundingBox structureboundingbox1 = new StructureBoundingBox(blockposition2, blockposition2.a(structureboundingbox.b()));
         int i = structureboundingbox.c() * structureboundingbox.d() * structureboundingbox.e();
         if(i > '\u8000') {
            throw new CommandException("commands.clone.tooManyBlocks", new Object[]{Integer.valueOf(i), Integer.valueOf('\u8000')});
         } else {
            boolean flag = false;
            Block block = null;
            int j = -1;
            if((p_execute_2_.length < 11 || !p_execute_2_[10].equals("force") && !p_execute_2_[10].equals("move")) && structureboundingbox.a(structureboundingbox1)) {
               throw new CommandException("commands.clone.noOverlap", new Object[0]);
            } else {
               if(p_execute_2_.length >= 11 && p_execute_2_[10].equals("move")) {
                  flag = true;
               }

               if(structureboundingbox.b >= 0 && structureboundingbox.e < 256 && structureboundingbox1.b >= 0 && structureboundingbox1.e < 256) {
                  World world = p_execute_1_.getWorld();
                  if(world.a(structureboundingbox) && world.a(structureboundingbox1)) {
                     boolean flag1 = false;
                     if(p_execute_2_.length >= 10) {
                        if(p_execute_2_[9].equals("masked")) {
                           flag1 = true;
                        } else if(p_execute_2_[9].equals("filtered")) {
                           if(p_execute_2_.length < 12) {
                              throw new ExceptionUsage("commands.clone.usage", new Object[0]);
                           }

                           block = g(p_execute_1_, p_execute_2_[11]);
                           if(p_execute_2_.length >= 13) {
                              j = a(p_execute_2_[12], 0, 15);
                           }
                        }
                     }

                     ArrayList arraylist = Lists.newArrayList();
                     ArrayList arraylist1 = Lists.newArrayList();
                     ArrayList arraylist2 = Lists.newArrayList();
                     LinkedList linkedlist = Lists.newLinkedList();
                     BlockPosition blockposition3 = new BlockPosition(structureboundingbox1.a - structureboundingbox.a, structureboundingbox1.b - structureboundingbox.b, structureboundingbox1.c - structureboundingbox.c);

                     for(int k = structureboundingbox.c; k <= structureboundingbox.f; ++k) {
                        for(int l = structureboundingbox.b; l <= structureboundingbox.e; ++l) {
                           for(int i1 = structureboundingbox.a; i1 <= structureboundingbox.d; ++i1) {
                              BlockPosition blockposition4 = new BlockPosition(i1, l, k);
                              BlockPosition blockposition5 = blockposition4.a(blockposition3);
                              IBlockData iblockdata = world.getType(blockposition4);
                              if((!flag1 || iblockdata.getBlock() != Blocks.AIR) && (block == null || iblockdata.getBlock() == block && (j < 0 || iblockdata.getBlock().toLegacyData(iblockdata) == j))) {
                                 TileEntity tileentity = world.getTileEntity(blockposition4);
                                 if(tileentity != null) {
                                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                                    tileentity.b(nbttagcompound);
                                    arraylist1.add(new CommandClone.CommandCloneStoredTileEntity(blockposition5, iblockdata, nbttagcompound));
                                    linkedlist.addLast(blockposition4);
                                 } else if(!iblockdata.getBlock().o() && !iblockdata.getBlock().d()) {
                                    arraylist2.add(new CommandClone.CommandCloneStoredTileEntity(blockposition5, iblockdata, (NBTTagCompound)null));
                                    linkedlist.addFirst(blockposition4);
                                 } else {
                                    arraylist.add(new CommandClone.CommandCloneStoredTileEntity(blockposition5, iblockdata, (NBTTagCompound)null));
                                    linkedlist.addLast(blockposition4);
                                 }
                              }
                           }
                        }
                     }

                     if(flag) {
                        for(BlockPosition blockposition6 : linkedlist) {
                           TileEntity tileentity1 = world.getTileEntity(blockposition6);
                           if(tileentity1 instanceof IInventory) {
                              ((IInventory)tileentity1).l();
                           }

                           world.setTypeAndData(blockposition6, Blocks.BARRIER.getBlockData(), 2);
                        }

                        for(BlockPosition blockposition7 : linkedlist) {
                           world.setTypeAndData(blockposition7, Blocks.AIR.getBlockData(), 3);
                        }
                     }

                     ArrayList arraylist3 = Lists.newArrayList();
                     arraylist3.addAll(arraylist);
                     arraylist3.addAll(arraylist1);
                     arraylist3.addAll(arraylist2);
                     List list = Lists.reverse(arraylist3);

                     for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity : list) {
                        TileEntity tileentity2 = world.getTileEntity(commandclone$commandclonestoredtileentity.a);
                        if(tileentity2 instanceof IInventory) {
                           ((IInventory)tileentity2).l();
                        }

                        world.setTypeAndData(commandclone$commandclonestoredtileentity.a, Blocks.BARRIER.getBlockData(), 2);
                     }

                     i = 0;

                     for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity1 : arraylist3) {
                        if(world.setTypeAndData(commandclone$commandclonestoredtileentity1.a, commandclone$commandclonestoredtileentity1.b, 2)) {
                           ++i;
                        }
                     }

                     for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity2 : arraylist1) {
                        TileEntity tileentity3 = world.getTileEntity(commandclone$commandclonestoredtileentity2.a);
                        if(commandclone$commandclonestoredtileentity2.c != null && tileentity3 != null) {
                           commandclone$commandclonestoredtileentity2.c.setInt("x", commandclone$commandclonestoredtileentity2.a.getX());
                           commandclone$commandclonestoredtileentity2.c.setInt("y", commandclone$commandclonestoredtileentity2.a.getY());
                           commandclone$commandclonestoredtileentity2.c.setInt("z", commandclone$commandclonestoredtileentity2.a.getZ());
                           tileentity3.a(commandclone$commandclonestoredtileentity2.c);
                           tileentity3.update();
                        }

                        world.setTypeAndData(commandclone$commandclonestoredtileentity2.a, commandclone$commandclonestoredtileentity2.b, 2);
                     }

                     for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity3 : list) {
                        world.update(commandclone$commandclonestoredtileentity3.a, commandclone$commandclonestoredtileentity3.b.getBlock());
                     }

                     List list1 = world.a(structureboundingbox, false);
                     if(list1 != null) {
                        for(NextTickListEntry nextticklistentry : list1) {
                           if(structureboundingbox.b((BaseBlockPosition)nextticklistentry.a)) {
                              BlockPosition blockposition8 = nextticklistentry.a.a(blockposition3);
                              world.b(blockposition8, nextticklistentry.a(), (int)(nextticklistentry.b - world.getWorldData().getTime()), nextticklistentry.c);
                           }
                        }
                     }

                     if(i <= 0) {
                        throw new CommandException("commands.clone.failed", new Object[0]);
                     } else {
                        p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, i);
                        a(p_execute_1_, this, "commands.clone.success", new Object[]{Integer.valueOf(i)});
                     }
                  } else {
                     throw new CommandException("commands.clone.outOfWorld", new Object[0]);
                  }
               } else {
                  throw new CommandException("commands.clone.outOfWorld", new Object[0]);
               }
            }
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length > 0 && p_tabComplete_2_.length <= 3?a(p_tabComplete_2_, 0, p_tabComplete_3_):(p_tabComplete_2_.length > 3 && p_tabComplete_2_.length <= 6?a(p_tabComplete_2_, 3, p_tabComplete_3_):(p_tabComplete_2_.length > 6 && p_tabComplete_2_.length <= 9?a(p_tabComplete_2_, 6, p_tabComplete_3_):(p_tabComplete_2_.length == 10?a(p_tabComplete_2_, new String[]{"replace", "masked", "filtered"}):(p_tabComplete_2_.length == 11?a(p_tabComplete_2_, new String[]{"normal", "force", "move"}):(p_tabComplete_2_.length == 12 && "filtered".equals(p_tabComplete_2_[9])?a(p_tabComplete_2_, Block.REGISTRY.keySet()):null)))));
   }

   static class CommandCloneStoredTileEntity {
      public final BlockPosition a;
      public final IBlockData b;
      public final NBTTagCompound c;

      public CommandCloneStoredTileEntity(BlockPosition p_i1213_1_, IBlockData p_i1213_2_, NBTTagCompound p_i1213_3_) {
         this.a = p_i1213_1_;
         this.b = p_i1213_2_;
         this.c = p_i1213_3_;
      }
   }
}

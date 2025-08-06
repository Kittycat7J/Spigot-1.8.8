package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockTorch;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.PluginManager;

public class BlockRedstoneTorch extends BlockTorch {
   private static Map<World, List<BlockRedstoneTorch.RedstoneUpdateInfo>> b = new WeakHashMap();
   private final boolean isOn;

   private boolean a(World p_a_1_, BlockPosition p_a_2_, boolean p_a_3_) {
      if(!b.containsKey(p_a_1_)) {
         b.put(p_a_1_, Lists.newArrayList());
      }

      List list = (List)b.get(p_a_1_);
      if(p_a_3_) {
         list.add(new BlockRedstoneTorch.RedstoneUpdateInfo(p_a_2_, p_a_1_.getTime()));
      }

      int i = 0;

      for(int j = 0; j < list.size(); ++j) {
         BlockRedstoneTorch.RedstoneUpdateInfo blockredstonetorch$redstoneupdateinfo = (BlockRedstoneTorch.RedstoneUpdateInfo)list.get(j);
         if(blockredstonetorch$redstoneupdateinfo.a.equals(p_a_2_)) {
            ++i;
            if(i >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   protected BlockRedstoneTorch(boolean p_i444_1_) {
      this.isOn = p_i444_1_;
      this.a(true);
      this.a((CreativeModeTab)null);
   }

   public int a(World p_a_1_) {
      return 2;
   }

   public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_) {
      if(this.isOn) {
         for(EnumDirection enumdirection : EnumDirection.values()) {
            p_onPlace_1_.applyPhysics(p_onPlace_2_.shift(enumdirection), this);
         }
      }

   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      if(this.isOn) {
         for(EnumDirection enumdirection : EnumDirection.values()) {
            p_remove_1_.applyPhysics(p_remove_2_.shift(enumdirection), this);
         }
      }

   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return this.isOn && p_a_3_.get(FACING) != p_a_4_?15:0;
   }

   private boolean g(World p_g_1_, BlockPosition p_g_2_, IBlockData p_g_3_) {
      EnumDirection enumdirection = ((EnumDirection)p_g_3_.get(FACING)).opposite();
      return p_g_1_.isBlockFacePowered(p_g_2_.shift(enumdirection), enumdirection);
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_) {
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      boolean flag = this.g(p_b_1_, p_b_2_, p_b_3_);
      List list = (List)b.get(p_b_1_);

      while(list != null && !list.isEmpty() && p_b_1_.getTime() - ((BlockRedstoneTorch.RedstoneUpdateInfo)list.get(0)).b > 60L) {
         list.remove(0);
      }

      PluginManager pluginmanager = p_b_1_.getServer().getPluginManager();
      org.bukkit.block.Block block = p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
      int i = this.isOn?15:0;
      BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, i, i);
      if(this.isOn) {
         if(flag) {
            if(i != 0) {
               blockredstoneevent.setNewCurrent(0);
               pluginmanager.callEvent(blockredstoneevent);
               if(blockredstoneevent.getNewCurrent() != 0) {
                  return;
               }
            }

            p_b_1_.setTypeAndData(p_b_2_, Blocks.UNLIT_REDSTONE_TORCH.getBlockData().set(FACING, (EnumDirection)p_b_3_.get(FACING)), 3);
            if(this.a(p_b_1_, p_b_2_, true)) {
               p_b_1_.makeSound((double)((float)p_b_2_.getX() + 0.5F), (double)((float)p_b_2_.getY() + 0.5F), (double)((float)p_b_2_.getZ() + 0.5F), "random.fizz", 0.5F, 2.6F + (p_b_1_.random.nextFloat() - p_b_1_.random.nextFloat()) * 0.8F);

               for(int j = 0; j < 5; ++j) {
                  double d0 = (double)p_b_2_.getX() + p_b_4_.nextDouble() * 0.6D + 0.2D;
                  double d1 = (double)p_b_2_.getY() + p_b_4_.nextDouble() * 0.6D + 0.2D;
                  double d2 = (double)p_b_2_.getZ() + p_b_4_.nextDouble() * 0.6D + 0.2D;
                  p_b_1_.addParticle(EnumParticle.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
               }

               p_b_1_.a((BlockPosition)p_b_2_, (Block)p_b_1_.getType(p_b_2_).getBlock(), 160);
            }
         }
      } else if(!flag && !this.a(p_b_1_, p_b_2_, false)) {
         if(i != 15) {
            blockredstoneevent.setNewCurrent(15);
            pluginmanager.callEvent(blockredstoneevent);
            if(blockredstoneevent.getNewCurrent() != 15) {
               return;
            }
         }

         p_b_1_.setTypeAndData(p_b_2_, Blocks.REDSTONE_TORCH.getBlockData().set(FACING, (EnumDirection)p_b_3_.get(FACING)), 3);
      }

   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_) && this.isOn == this.g(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_)) {
         p_doPhysics_1_.a((BlockPosition)p_doPhysics_2_, (Block)this, this.a(p_doPhysics_1_));
      }

   }

   public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_) {
      return p_b_4_ == EnumDirection.DOWN?this.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_):0;
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(Blocks.REDSTONE_TORCH);
   }

   public boolean isPowerSource() {
      return true;
   }

   public boolean b(Block p_b_1_) {
      return p_b_1_ == Blocks.UNLIT_REDSTONE_TORCH || p_b_1_ == Blocks.REDSTONE_TORCH;
   }

   static class RedstoneUpdateInfo {
      BlockPosition a;
      long b;

      public RedstoneUpdateInfo(BlockPosition p_i27_1_, long p_i27_2_) {
         this.a = p_i27_1_;
         this.b = p_i27_2_;
      }
   }
}

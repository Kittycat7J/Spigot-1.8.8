package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockCrops;
import net.minecraft.server.v1_8_R3.BlockDirt;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockStem;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;

public class BlockSoil extends Block {
   public static final BlockStateInteger MOISTURE = BlockStateInteger.of("moisture", 0, 7);

   protected BlockSoil() {
      super(Material.EARTH);
      this.j(this.blockStateList.getBlockData().set(MOISTURE, Integer.valueOf(0)));
      this.a(true);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      this.e(255);
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return new AxisAlignedBB((double)p_a_2_.getX(), (double)p_a_2_.getY(), (double)p_a_2_.getZ(), (double)(p_a_2_.getX() + 1), (double)(p_a_2_.getY() + 1), (double)(p_a_2_.getZ() + 1));
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      int i = ((Integer)p_b_3_.get(MOISTURE)).intValue();
      if(!this.f(p_b_1_, p_b_2_) && !p_b_1_.isRainingAt(p_b_2_.up())) {
         if(i > 0) {
            p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(MOISTURE, Integer.valueOf(i - 1)), 2);
         } else if(!this.e(p_b_1_, p_b_2_)) {
            org.bukkit.block.Block block = p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
            if(CraftEventFactory.callBlockFadeEvent(block, Blocks.DIRT).isCancelled()) {
               return;
            }

            p_b_1_.setTypeUpdate(p_b_2_, Blocks.DIRT.getBlockData());
         }
      } else if(i < 7) {
         p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(MOISTURE, Integer.valueOf(7)), 2);
      }

   }

   public void fallOn(World p_fallOn_1_, BlockPosition p_fallOn_2_, Entity p_fallOn_3_, float p_fallOn_4_) {
      super.fallOn(p_fallOn_1_, p_fallOn_2_, p_fallOn_3_, p_fallOn_4_);
      if(p_fallOn_3_ instanceof EntityLiving && !p_fallOn_1_.isClientSide && p_fallOn_1_.random.nextFloat() < p_fallOn_4_ - 0.5F) {
         if(!(p_fallOn_3_ instanceof EntityHuman) && !p_fallOn_1_.getGameRules().getBoolean("mobGriefing")) {
            return;
         }

         Cancellable cancellable;
         if(p_fallOn_3_ instanceof EntityHuman) {
            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)p_fallOn_3_, Action.PHYSICAL, p_fallOn_2_, (EnumDirection)null, (ItemStack)null);
         } else {
            cancellable = new EntityInteractEvent(p_fallOn_3_.getBukkitEntity(), p_fallOn_1_.getWorld().getBlockAt(p_fallOn_2_.getX(), p_fallOn_2_.getY(), p_fallOn_2_.getZ()));
            p_fallOn_1_.getServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
         }

         if(cancellable.isCancelled()) {
            return;
         }

         if(CraftEventFactory.callEntityChangeBlockEvent(p_fallOn_3_, p_fallOn_2_.getX(), p_fallOn_2_.getY(), p_fallOn_2_.getZ(), Blocks.DIRT, 0).isCancelled()) {
            return;
         }

         p_fallOn_1_.setTypeUpdate(p_fallOn_2_, Blocks.DIRT.getBlockData());
      }

   }

   private boolean e(World p_e_1_, BlockPosition p_e_2_) {
      Block block = p_e_1_.getType(p_e_2_.up()).getBlock();
      return block instanceof BlockCrops || block instanceof BlockStem;
   }

   private boolean f(World p_f_1_, BlockPosition p_f_2_) {
      for(BlockPosition.MutableBlockPosition blockposition$mutableblockposition : BlockPosition.b(p_f_2_.a(-4, 0, -4), p_f_2_.a(4, 1, 4))) {
         if(p_f_1_.getType(blockposition$mutableblockposition).getBlock().getMaterial() == Material.WATER) {
            return true;
         }
      }

      return false;
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      super.doPhysics(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
      if(p_doPhysics_1_.getType(p_doPhysics_2_.up()).getBlock().getMaterial().isBuildable()) {
         p_doPhysics_1_.setTypeUpdate(p_doPhysics_2_, Blocks.DIRT.getBlockData());
      }

   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), p_getDropType_2_, p_getDropType_3_);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(MOISTURE, Integer.valueOf(p_fromLegacyData_1_ & 7));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((Integer)p_toLegacyData_1_.get(MOISTURE)).intValue();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{MOISTURE});
   }
}

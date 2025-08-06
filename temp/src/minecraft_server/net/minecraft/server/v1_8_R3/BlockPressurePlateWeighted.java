package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockPressurePlateAbstract;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;

public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract {
   public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
   private final int weight;

   protected BlockPressurePlateWeighted(Material p_i280_1_, int p_i280_2_) {
      this(p_i280_1_, p_i280_2_, p_i280_1_.r());
   }

   protected BlockPressurePlateWeighted(Material p_i281_1_, int p_i281_2_, MaterialMapColor p_i281_3_) {
      super(p_i281_1_, p_i281_3_);
      this.j(this.blockStateList.getBlockData().set(POWER, Integer.valueOf(0)));
      this.weight = p_i281_2_;
   }

   protected int f(World p_f_1_, BlockPosition p_f_2_) {
      int i = 0;

      for(Entity entity : p_f_1_.a(Entity.class, this.getBoundingBox(p_f_2_))) {
         Cancellable cancellable;
         if(entity instanceof EntityHuman) {
            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, p_f_2_, (EnumDirection)null, (ItemStack)null);
         } else {
            cancellable = new EntityInteractEvent(entity.getBukkitEntity(), p_f_1_.getWorld().getBlockAt(p_f_2_.getX(), p_f_2_.getY(), p_f_2_.getZ()));
            p_f_1_.getServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
         }

         if(!cancellable.isCancelled()) {
            ++i;
         }
      }

      i = Math.min(i, this.weight);
      if(i > 0) {
         float f = (float)Math.min(this.weight, i) / (float)this.weight;
         return MathHelper.f(f * 15.0F);
      } else {
         return 0;
      }
   }

   protected int e(IBlockData p_e_1_) {
      return ((Integer)p_e_1_.get(POWER)).intValue();
   }

   protected IBlockData a(IBlockData p_a_1_, int p_a_2_) {
      return p_a_1_.set(POWER, Integer.valueOf(p_a_2_));
   }

   public int a(World p_a_1_) {
      return 10;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(POWER, Integer.valueOf(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((Integer)p_toLegacyData_1_.get(POWER)).intValue();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{POWER});
   }
}

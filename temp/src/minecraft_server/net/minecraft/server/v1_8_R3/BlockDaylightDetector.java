package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockContainer;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumSkyBlock;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityLightDetector;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockDaylightDetector extends BlockContainer {
   public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
   private final boolean b;

   public BlockDaylightDetector(boolean p_i337_1_) {
      super(Material.WOOD);
      this.b = p_i337_1_;
      this.j(this.blockStateList.getBlockData().set(POWER, Integer.valueOf(0)));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
      this.a(CreativeModeTab.d);
      this.c(0.2F);
      this.a(f);
      this.c("daylightDetector");
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return ((Integer)p_a_3_.get(POWER)).intValue();
   }

   public void f(World p_f_1_, BlockPosition p_f_2_) {
      if(!p_f_1_.worldProvider.o()) {
         IBlockData iblockdata = p_f_1_.getType(p_f_2_);
         int i = p_f_1_.b(EnumSkyBlock.SKY, p_f_2_) - p_f_1_.ab();
         float f = p_f_1_.d(1.0F);
         float f1 = f < 3.1415927F?0.0F:6.2831855F;
         f = f + (f1 - f) * 0.2F;
         i = Math.round((float)i * MathHelper.cos(f));
         i = MathHelper.clamp(i, 0, 15);
         if(this.b) {
            i = 15 - i;
         }

         if(((Integer)iblockdata.get(POWER)).intValue() != i) {
            i = CraftEventFactory.callRedstoneChange(p_f_1_, p_f_2_.getX(), p_f_2_.getY(), p_f_2_.getZ(), ((Integer)iblockdata.get(POWER)).intValue(), i).getNewCurrent();
            p_f_1_.setTypeAndData(p_f_2_, iblockdata.set(POWER, Integer.valueOf(i)), 3);
         }
      }

   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(p_interact_4_.cn()) {
         if(p_interact_1_.isClientSide) {
            return true;
         } else {
            if(this.b) {
               p_interact_1_.setTypeAndData(p_interact_2_, Blocks.DAYLIGHT_DETECTOR.getBlockData().set(POWER, (Integer)p_interact_3_.get(POWER)), 4);
               Blocks.DAYLIGHT_DETECTOR.f(p_interact_1_, p_interact_2_);
            } else {
               p_interact_1_.setTypeAndData(p_interact_2_, Blocks.DAYLIGHT_DETECTOR_INVERTED.getBlockData().set(POWER, (Integer)p_interact_3_.get(POWER)), 4);
               Blocks.DAYLIGHT_DETECTOR_INVERTED.f(p_interact_1_, p_interact_2_);
            }

            return true;
         }
      } else {
         return super.interact(p_interact_1_, p_interact_2_, p_interact_3_, p_interact_4_, p_interact_5_, p_interact_6_, p_interact_7_, p_interact_8_);
      }
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(Blocks.DAYLIGHT_DETECTOR);
   }

   public boolean d() {
      return false;
   }

   public boolean c() {
      return false;
   }

   public int b() {
      return 3;
   }

   public boolean isPowerSource() {
      return true;
   }

   public TileEntity a(World p_a_1_, int p_a_2_) {
      return new TileEntityLightDetector();
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

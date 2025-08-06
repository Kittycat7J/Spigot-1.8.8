package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;

public class BlockOre extends Block {
   public BlockOre() {
      this(Material.STONE.r());
   }

   public BlockOre(MaterialMapColor p_i285_1_) {
      super(Material.STONE, p_i285_1_);
      this.a(CreativeModeTab.b);
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return this == Blocks.COAL_ORE?Items.COAL:(this == Blocks.DIAMOND_ORE?Items.DIAMOND:(this == Blocks.LAPIS_ORE?Items.DYE:(this == Blocks.EMERALD_ORE?Items.EMERALD:(this == Blocks.QUARTZ_ORE?Items.QUARTZ:Item.getItemOf(this)))));
   }

   public int a(Random p_a_1_) {
      return this == Blocks.LAPIS_ORE?4 + p_a_1_.nextInt(5):1;
   }

   public int getDropCount(int p_getDropCount_1_, Random p_getDropCount_2_) {
      if(p_getDropCount_1_ > 0 && Item.getItemOf(this) != this.getDropType((IBlockData)this.P().a().iterator().next(), p_getDropCount_2_, p_getDropCount_1_)) {
         int i = p_getDropCount_2_.nextInt(p_getDropCount_1_ + 2) - 1;
         if(i < 0) {
            i = 0;
         }

         return this.a(p_getDropCount_2_) * (i + 1);
      } else {
         return this.a(p_getDropCount_2_);
      }
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, p_dropNaturally_5_);
   }

   public int getExpDrop(World p_getExpDrop_1_, IBlockData p_getExpDrop_2_, int p_getExpDrop_3_) {
      if(this.getDropType(p_getExpDrop_2_, p_getExpDrop_1_.random, p_getExpDrop_3_) != Item.getItemOf(this)) {
         int i = 0;
         if(this == Blocks.COAL_ORE) {
            i = MathHelper.nextInt(p_getExpDrop_1_.random, 0, 2);
         } else if(this == Blocks.DIAMOND_ORE) {
            i = MathHelper.nextInt(p_getExpDrop_1_.random, 3, 7);
         } else if(this == Blocks.EMERALD_ORE) {
            i = MathHelper.nextInt(p_getExpDrop_1_.random, 3, 7);
         } else if(this == Blocks.LAPIS_ORE) {
            i = MathHelper.nextInt(p_getExpDrop_1_.random, 2, 5);
         } else if(this == Blocks.QUARTZ_ORE) {
            i = MathHelper.nextInt(p_getExpDrop_1_.random, 2, 5);
         }

         return i;
      } else {
         return 0;
      }
   }

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      return 0;
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return this == Blocks.LAPIS_ORE?EnumColor.BLUE.getInvColorIndex():0;
   }
}

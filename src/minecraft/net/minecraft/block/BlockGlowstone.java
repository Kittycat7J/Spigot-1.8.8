package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.custom.DBLogger;

public class BlockGlowstone extends Block {
   public BlockGlowstone(Material materialIn) {
      super(materialIn);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int quantityDroppedWithBonus(int fortune, Random random) {
      // DBLogger.log("BiomeDecorator.java", this.randomGenerator, "nextInt", 16, "gen sand decorate");
         // DBLogger.log("BlockEnchantmentTable.java", rand, "nextFloat", -1, "Particle spawn coordinates");
      DBLogger.log("BlockGlowstone.java", random, "nextInt", fortune + 1, "glowstone drop count, bound is fortune + 1"); 
      return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
   }

   public int quantityDropped(Random random) {
      DBLogger.log("BlockGlowstone.java", random, "nextInt", 3, "glowstone drop count");
      return 2 + random.nextInt(3);
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.glowstone_dust;
   }

   public MapColor getMapColor(IBlockState state) {
      return MapColor.sandColor;
   }
}

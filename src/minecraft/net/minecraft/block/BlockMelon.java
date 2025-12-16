package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockMelon extends Block {
   protected BlockMelon() {
      super(Material.gourd, MapColor.limeColor);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.melon;
   }

   public int quantityDropped(Random random) {
      // DBLogger.log("BiomeDecorator.java", this.randomGenerator, "nextInt", 16, "gen sand decorate");
         // DBLogger.log("BlockEnchantmentTable.java", rand, "nextFloat", -1, "Particle spawn coordinates");
      DBLogger.log("BlockMelon.java", random, "nextInt", 5, "Melon drop count");
      return 3 + random.nextInt(5);
   }

   public int quantityDroppedWithBonus(int fortune, Random random) {
      DBLogger.log("BlockMelon.java", random, "nextInt", 1 + fortune, "Melon drop count, bound is 1 + fortune");
      return Math.min(9, this.quantityDropped(random) + random.nextInt(1 + fortune));
   }
}

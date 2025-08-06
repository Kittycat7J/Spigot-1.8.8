package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockFlowers;
import net.minecraft.server.v1_8_R3.BlockTallPlant;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;

public class RecipesDyes {
   public void a(CraftingManager p_a_1_) {
      for(int i = 0; i < 16; ++i) {
         p_a_1_.registerShapelessRecipe(new ItemStack(Blocks.WOOL, 1, i), new Object[]{new ItemStack(Items.DYE, 1, 15 - i), new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 0)});
         p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 8, 15 - i), new Object[]{"###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.HARDENED_CLAY), Character.valueOf('X'), new ItemStack(Items.DYE, 1, i)});
         p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STAINED_GLASS, 8, 15 - i), new Object[]{"###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.GLASS), Character.valueOf('X'), new ItemStack(Items.DYE, 1, i)});
         p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 16, i), new Object[]{"###", "###", Character.valueOf('#'), new ItemStack(Blocks.STAINED_GLASS, 1, i)});
      }

      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.YELLOW.getInvColorIndex()), new Object[]{new ItemStack(Blocks.YELLOW_FLOWER, 1, BlockFlowers.EnumFlowerVarient.DANDELION.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.POPPY.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 3, EnumColor.WHITE.getInvColorIndex()), new Object[]{Items.BONE});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.PINK.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.ORANGE.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.YELLOW.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.LIME.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.GREEN.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.GRAY.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.SILVER.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.GRAY.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 3, EnumColor.SILVER.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.LIGHT_BLUE.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.CYAN.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.GREEN.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.PURPLE.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.MAGENTA.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.PURPLE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.PINK.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 3, EnumColor.MAGENTA.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.PINK.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 4, EnumColor.MAGENTA.getInvColorIndex()), new Object[]{new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.LIGHT_BLUE.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.BLUE_ORCHID.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.MAGENTA.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.ALLIUM.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.SILVER.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.HOUSTONIA.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.RED_TULIP.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.ORANGE.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.ORANGE_TULIP.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.SILVER.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.WHITE_TULIP.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.PINK.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.PINK_TULIP.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.SILVER.getInvColorIndex()), new Object[]{new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.OXEYE_DAISY.b())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.YELLOW.getInvColorIndex()), new Object[]{new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.SUNFLOWER.a())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.MAGENTA.getInvColorIndex()), new Object[]{new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.SYRINGA.a())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.RED.getInvColorIndex()), new Object[]{new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.ROSE.a())});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.PINK.getInvColorIndex()), new Object[]{new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.PAEONIA.a())});

      for(int j = 0; j < 16; ++j) {
         p_a_1_.registerShapedRecipe(new ItemStack(Blocks.CARPET, 3, j), new Object[]{"##", Character.valueOf('#'), new ItemStack(Blocks.WOOL, 1, j)});
      }

   }
}

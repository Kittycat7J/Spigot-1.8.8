package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;

public class RecipesFood {
   public void a(CraftingManager p_a_1_) {
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.MUSHROOM_STEW), new Object[]{Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Items.BOWL});
      p_a_1_.registerShapedRecipe(new ItemStack(Items.COOKIE, 8), new Object[]{"#X#", Character.valueOf('X'), new ItemStack(Items.DYE, 1, EnumColor.BROWN.getInvColorIndex()), Character.valueOf('#'), Items.WHEAT});
      p_a_1_.registerShapedRecipe(new ItemStack(Items.RABBIT_STEW), new Object[]{" R ", "CPM", " B ", Character.valueOf('R'), new ItemStack(Items.COOKED_RABBIT), Character.valueOf('C'), Items.CARROT, Character.valueOf('P'), Items.BAKED_POTATO, Character.valueOf('M'), Blocks.BROWN_MUSHROOM, Character.valueOf('B'), Items.BOWL});
      p_a_1_.registerShapedRecipe(new ItemStack(Items.RABBIT_STEW), new Object[]{" R ", "CPD", " B ", Character.valueOf('R'), new ItemStack(Items.COOKED_RABBIT), Character.valueOf('C'), Items.CARROT, Character.valueOf('P'), Items.BAKED_POTATO, Character.valueOf('D'), Blocks.RED_MUSHROOM, Character.valueOf('B'), Items.BOWL});
      p_a_1_.registerShapedRecipe(new ItemStack(Blocks.MELON_BLOCK), new Object[]{"MMM", "MMM", "MMM", Character.valueOf('M'), Items.MELON});
      p_a_1_.registerShapedRecipe(new ItemStack(Items.MELON_SEEDS), new Object[]{"M", Character.valueOf('M'), Items.MELON});
      p_a_1_.registerShapedRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 4), new Object[]{"M", Character.valueOf('M'), Blocks.PUMPKIN});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.PUMPKIN_PIE), new Object[]{Blocks.PUMPKIN, Items.SUGAR, Items.EGG});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE), new Object[]{Items.SPIDER_EYE, Blocks.BROWN_MUSHROOM, Items.SUGAR});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.BLAZE_POWDER, 2), new Object[]{Items.BLAZE_ROD});
      p_a_1_.registerShapelessRecipe(new ItemStack(Items.MAGMA_CREAM), new Object[]{Items.BLAZE_POWDER, Items.SLIME});
   }
}

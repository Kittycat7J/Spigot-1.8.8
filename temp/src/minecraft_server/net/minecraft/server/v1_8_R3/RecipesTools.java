package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;

public class RecipesTools {
   private String[][] a = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
   private Object[][] b = new Object[][]{{Blocks.PLANKS, Blocks.COBBLESTONE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT}, {Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE}, {Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.DIAMOND_SHOVEL, Items.GOLDEN_SHOVEL}, {Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.GOLDEN_AXE}, {Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.DIAMOND_HOE, Items.GOLDEN_HOE}};

   public void a(CraftingManager p_a_1_) {
      for(int i = 0; i < this.b[0].length; ++i) {
         Object object = this.b[0][i];

         for(int j = 0; j < this.b.length - 1; ++j) {
            Item item = (Item)this.b[j + 1][i];
            p_a_1_.registerShapedRecipe(new ItemStack(item), new Object[]{this.a[j], Character.valueOf('#'), Items.STICK, Character.valueOf('X'), object});
         }
      }

      p_a_1_.registerShapedRecipe(new ItemStack(Items.SHEARS), new Object[]{" #", "# ", Character.valueOf('#'), Items.IRON_INGOT});
   }
}

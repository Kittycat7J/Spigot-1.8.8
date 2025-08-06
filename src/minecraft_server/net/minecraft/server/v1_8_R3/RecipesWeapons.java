package net.minecraft.server.v1_8_R3;

public class RecipesWeapons
{
    private String[][] a = new String[][] {{"X", "X", "#"}};
    private Object[][] b = new Object[][] {{Blocks.PLANKS, Blocks.COBBLESTONE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT}, {Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.GOLDEN_SWORD}};

    public void a(CraftingManager p_a_1_)
    {
        for (int i = 0; i < this.b[0].length; ++i)
        {
            Object object = this.b[0][i];

            for (int j = 0; j < this.b.length - 1; ++j)
            {
                Item item = (Item)this.b[j + 1][i];
                p_a_1_.registerShapedRecipe(new ItemStack(item), new Object[] {this.a[j], '#', Items.STICK, 'X', object});
            }
        }

        p_a_1_.registerShapedRecipe(new ItemStack(Items.BOW, 1), new Object[] {" #X", "# X", " #X", 'X', Items.STRING, '#', Items.STICK});
        p_a_1_.registerShapedRecipe(new ItemStack(Items.ARROW, 4), new Object[] {"X", "#", "Y", 'Y', Items.FEATHER, 'X', Items.FLINT, '#', Items.STICK});
    }
}

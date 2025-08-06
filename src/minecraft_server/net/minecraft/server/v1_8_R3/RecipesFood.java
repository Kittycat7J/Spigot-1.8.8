package net.minecraft.server.v1_8_R3;

public class RecipesFood
{
    public void a(CraftingManager p_a_1_)
    {
        p_a_1_.registerShapelessRecipe(new ItemStack(Items.MUSHROOM_STEW), new Object[] {Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Items.BOWL});
        p_a_1_.registerShapedRecipe(new ItemStack(Items.COOKIE, 8), new Object[] {"#X#", 'X', new ItemStack(Items.DYE, 1, EnumColor.BROWN.getInvColorIndex()), '#', Items.WHEAT});
        p_a_1_.registerShapedRecipe(new ItemStack(Items.RABBIT_STEW), new Object[] {" R ", "CPM", " B ", 'R', new ItemStack(Items.COOKED_RABBIT), 'C', Items.CARROT, 'P', Items.BAKED_POTATO, 'M', Blocks.BROWN_MUSHROOM, 'B', Items.BOWL});
        p_a_1_.registerShapedRecipe(new ItemStack(Items.RABBIT_STEW), new Object[] {" R ", "CPD", " B ", 'R', new ItemStack(Items.COOKED_RABBIT), 'C', Items.CARROT, 'P', Items.BAKED_POTATO, 'D', Blocks.RED_MUSHROOM, 'B', Items.BOWL});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.MELON_BLOCK), new Object[] {"MMM", "MMM", "MMM", 'M', Items.MELON});
        p_a_1_.registerShapedRecipe(new ItemStack(Items.MELON_SEEDS), new Object[] {"M", 'M', Items.MELON});
        p_a_1_.registerShapedRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 4), new Object[] {"M", 'M', Blocks.PUMPKIN});
        p_a_1_.registerShapelessRecipe(new ItemStack(Items.PUMPKIN_PIE), new Object[] {Blocks.PUMPKIN, Items.SUGAR, Items.EGG});
        p_a_1_.registerShapelessRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE), new Object[] {Items.SPIDER_EYE, Blocks.BROWN_MUSHROOM, Items.SUGAR});
        p_a_1_.registerShapelessRecipe(new ItemStack(Items.BLAZE_POWDER, 2), new Object[] {Items.BLAZE_ROD});
        p_a_1_.registerShapelessRecipe(new ItemStack(Items.MAGMA_CREAM), new Object[] {Items.BLAZE_POWDER, Items.SLIME});
    }
}

package net.minecraft.server.v1_8_R3;

import java.util.Comparator;

class Recipes$1 implements Comparator<IRecipe>
{
    Recipes$1(CraftingManager p_i521_1_)
    {
        this.a = p_i521_1_;
    }

    public int a(IRecipe p_a_1_, IRecipe p_a_2_)
    {
        return p_a_1_ instanceof ShapelessRecipes && p_a_2_ instanceof ShapedRecipes ? 1 : (p_a_2_ instanceof ShapelessRecipes && p_a_1_ instanceof ShapedRecipes ? -1 : (p_a_2_.a() < p_a_1_.a() ? -1 : (p_a_2_.a() > p_a_1_.a() ? 1 : 0)));
    }
}

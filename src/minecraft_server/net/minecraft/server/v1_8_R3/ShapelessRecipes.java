package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ShapelessRecipe;

public class ShapelessRecipes implements IRecipe
{
    public final ItemStack result;
    private final List<ItemStack> ingredients;

    public ShapelessRecipes(ItemStack p_i457_1_, List<ItemStack> p_i457_2_)
    {
        this.result = p_i457_1_;
        this.ingredients = p_i457_2_;
    }

    public ShapelessRecipe toBukkitRecipe()
    {
        CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(this.result);
        CraftShapelessRecipe craftshapelessrecipe = new CraftShapelessRecipe(craftitemstack, this);

        for (ItemStack itemstack : this.ingredients)
        {
            if (itemstack != null)
            {
                craftshapelessrecipe.addIngredient(CraftMagicNumbers.getMaterial(itemstack.getItem()), itemstack.getData());
            }
        }

        return craftshapelessrecipe;
    }

    public ItemStack b()
    {
        return this.result;
    }

    public ItemStack[] b(InventoryCrafting p_b_1_)
    {
        ItemStack[] aitemstack = new ItemStack[p_b_1_.getSize()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = p_b_1_.getItem(i);

            if (itemstack != null && itemstack.getItem().r())
            {
                aitemstack[i] = new ItemStack(itemstack.getItem().q());
            }
        }

        return aitemstack;
    }

    public boolean a(InventoryCrafting p_a_1_, World p_a_2_)
    {
        ArrayList arraylist = Lists.newArrayList(this.ingredients);

        for (int i = 0; i < p_a_1_.h(); ++i)
        {
            for (int j = 0; j < p_a_1_.i(); ++j)
            {
                ItemStack itemstack = p_a_1_.c(j, i);

                if (itemstack != null)
                {
                    boolean flag = false;

                    for (ItemStack itemstack1 : arraylist)
                    {
                        if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getData() == 32767 || itemstack.getData() == itemstack1.getData()))
                        {
                            flag = true;
                            arraylist.remove(itemstack1);
                            break;
                        }
                    }

                    if (!flag)
                    {
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
    }

    public ItemStack craftItem(InventoryCrafting p_craftItem_1_)
    {
        return this.result.cloneItemStack();
    }

    public int a()
    {
        return this.ingredients.size();
    }

    public List<ItemStack> getIngredients()
    {
        return Collections.unmodifiableList(this.ingredients);
    }
}

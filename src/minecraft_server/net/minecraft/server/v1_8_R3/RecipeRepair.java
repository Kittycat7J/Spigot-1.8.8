package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class RecipeRepair extends ShapelessRecipes implements IRecipe
{
    public RecipeRepair()
    {
        super(new ItemStack(Items.LEATHER_HELMET), Arrays.asList(new ItemStack[] {new ItemStack(Items.LEATHER_HELMET)}));
    }

    public boolean a(InventoryCrafting p_a_1_, World p_a_2_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (int i = 0; i < p_a_1_.getSize(); ++i)
        {
            ItemStack itemstack = p_a_1_.getItem(i);

            if (itemstack != null)
            {
                arraylist.add(itemstack);

                if (arraylist.size() > 1)
                {
                    ItemStack itemstack1 = (ItemStack)arraylist.get(0);

                    if (itemstack.getItem() != itemstack1.getItem() || itemstack1.count != 1 || itemstack.count != 1 || !itemstack1.getItem().usesDurability())
                    {
                        return false;
                    }
                }
            }
        }

        if (arraylist.size() == 2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public ItemStack craftItem(InventoryCrafting p_craftItem_1_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (int i = 0; i < p_craftItem_1_.getSize(); ++i)
        {
            ItemStack itemstack = p_craftItem_1_.getItem(i);

            if (itemstack != null)
            {
                arraylist.add(itemstack);

                if (arraylist.size() > 1)
                {
                    ItemStack itemstack1 = (ItemStack)arraylist.get(0);

                    if (itemstack.getItem() != itemstack1.getItem() || itemstack1.count != 1 || itemstack.count != 1 || !itemstack1.getItem().usesDurability())
                    {
                        return null;
                    }
                }
            }
        }

        if (arraylist.size() == 2)
        {
            ItemStack itemstack3 = (ItemStack)arraylist.get(0);
            ItemStack itemstack4 = (ItemStack)arraylist.get(1);

            if (itemstack3.getItem() == itemstack4.getItem() && itemstack3.count == 1 && itemstack4.count == 1 && itemstack3.getItem().usesDurability())
            {
                Item item = itemstack3.getItem();
                int j = item.getMaxDurability() - itemstack3.h();
                int k = item.getMaxDurability() - itemstack4.h();
                int l = j + k + item.getMaxDurability() * 5 / 100;
                int i1 = item.getMaxDurability() - l;

                if (i1 < 0)
                {
                    i1 = 0;
                }

                ItemStack itemstack2 = new ItemStack(itemstack4.getItem(), 1, i1);
                List<ItemStack> list = new ArrayList();
                list.add(itemstack3.cloneItemStack());
                list.add(itemstack4.cloneItemStack());
                ShapelessRecipes shapelessrecipes = new ShapelessRecipes(itemstack2.cloneItemStack(), list);
                p_craftItem_1_.currentRecipe = shapelessrecipes;
                itemstack2 = CraftEventFactory.callPreCraftEvent(p_craftItem_1_, itemstack2, CraftingManager.getInstance().lastCraftView, true);
                return itemstack2;
            }
        }

        return null;
    }

    public int a()
    {
        return 4;
    }

    public ItemStack b()
    {
        return null;
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
}

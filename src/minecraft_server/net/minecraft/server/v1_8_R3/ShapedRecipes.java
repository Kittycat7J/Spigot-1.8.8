package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ShapedRecipe;

public class ShapedRecipes implements IRecipe
{
    private final int width;
    private final int height;
    private final ItemStack[] items;
    public ItemStack result;
    private boolean e;

    public ShapedRecipes(int p_i396_1_, int p_i396_2_, ItemStack[] p_i396_3_, ItemStack p_i396_4_)
    {
        this.width = p_i396_1_;
        this.height = p_i396_2_;
        this.items = p_i396_3_;
        this.result = p_i396_4_;
    }

    public ShapedRecipe toBukkitRecipe()
    {
        CraftShapedRecipe craftshapedrecipe;
        CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(this.result);
        craftshapedrecipe = new CraftShapedRecipe(craftitemstack, this);
        label0:

        switch (this.height)
        {
            case 1:
                switch (this.width)
                {
                    case 1:
                        craftshapedrecipe.shape(new String[] {"a"});
                        break label0;

                    case 2:
                        craftshapedrecipe.shape(new String[] {"ab"});
                        break label0;

                    case 3:
                        craftshapedrecipe.shape(new String[] {"abc"});

                    default:
                        break label0;
                }

            case 2:
                switch (this.width)
                {
                    case 1:
                        craftshapedrecipe.shape(new String[] {"a", "b"});
                        break label0;

                    case 2:
                        craftshapedrecipe.shape(new String[] {"ab", "cd"});
                        break label0;

                    case 3:
                        craftshapedrecipe.shape(new String[] {"abc", "def"});

                    default:
                        break label0;
                }

            case 3:
                switch (this.width)
                {
                    case 1:
                        craftshapedrecipe.shape(new String[] {"a", "b", "c"});
                        break;

                    case 2:
                        craftshapedrecipe.shape(new String[] {"ab", "cd", "ef"});
                        break;

                    case 3:
                        craftshapedrecipe.shape(new String[] {"abc", "def", "ghi"});
                }
        }

        char c0 = 97;

        for (ItemStack itemstack : this.items)
        {
            if (itemstack != null)
            {
                craftshapedrecipe.setIngredient(c0, CraftMagicNumbers.getMaterial(itemstack.getItem()), itemstack.getData());
            }

            ++c0;
        }

        return craftshapedrecipe;
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
        for (int i = 0; i <= 3 - this.width; ++i)
        {
            for (int j = 0; j <= 3 - this.height; ++j)
            {
                if (this.a(p_a_1_, i, j, true))
                {
                    return true;
                }

                if (this.a(p_a_1_, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean a(InventoryCrafting p_a_1_, int p_a_2_, int p_a_3_, boolean p_a_4_)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                int k = i - p_a_2_;
                int l = j - p_a_3_;
                ItemStack itemstack = null;

                if (k >= 0 && l >= 0 && k < this.width && l < this.height)
                {
                    if (p_a_4_)
                    {
                        itemstack = this.items[this.width - k - 1 + l * this.width];
                    }
                    else
                    {
                        itemstack = this.items[k + l * this.width];
                    }
                }

                ItemStack itemstack1 = p_a_1_.c(i, j);

                if (itemstack1 != null || itemstack != null)
                {
                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null)
                    {
                        return false;
                    }

                    if (itemstack.getItem() != itemstack1.getItem())
                    {
                        return false;
                    }

                    if (itemstack.getData() != 32767 && itemstack.getData() != itemstack1.getData())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ItemStack craftItem(InventoryCrafting p_craftItem_1_)
    {
        ItemStack itemstack = this.b().cloneItemStack();

        if (this.e)
        {
            for (int i = 0; i < p_craftItem_1_.getSize(); ++i)
            {
                ItemStack itemstack1 = p_craftItem_1_.getItem(i);

                if (itemstack1 != null && itemstack1.hasTag())
                {
                    itemstack.setTag((NBTTagCompound)itemstack1.getTag().clone());
                }
            }
        }

        return itemstack;
    }

    public int a()
    {
        return this.width * this.height;
    }

    public List<ItemStack> getIngredients()
    {
        return Arrays.asList(this.items);
    }
}

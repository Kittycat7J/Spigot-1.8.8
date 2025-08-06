package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeArmorDye extends ShapelessRecipes implements IRecipe
{
    public RecipeArmorDye()
    {
        super(new ItemStack(Items.LEATHER_HELMET, 0, 0), Arrays.asList(new ItemStack[] {new ItemStack(Items.DYE, 0, 5)}));
    }

    public boolean a(InventoryCrafting p_a_1_, World p_a_2_)
    {
        ItemStack itemstack = null;
        ArrayList arraylist = Lists.newArrayList();

        for (int i = 0; i < p_a_1_.getSize(); ++i)
        {
            ItemStack itemstack1 = p_a_1_.getItem(i);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() instanceof ItemArmor)
                {
                    ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();

                    if (itemarmor.x_() != ItemArmor.EnumArmorMaterial.LEATHER || itemstack != null)
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.DYE)
                    {
                        return false;
                    }

                    arraylist.add(itemstack1);
                }
            }
        }

        if (itemstack != null && !arraylist.isEmpty())
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
        ItemStack itemstack = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmor itemarmor = null;

        for (int k = 0; k < p_craftItem_1_.getSize(); ++k)
        {
            ItemStack itemstack1 = p_craftItem_1_.getItem(k);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() instanceof ItemArmor)
                {
                    itemarmor = (ItemArmor)itemstack1.getItem();

                    if (itemarmor.x_() != ItemArmor.EnumArmorMaterial.LEATHER || itemstack != null)
                    {
                        return null;
                    }

                    itemstack = itemstack1.cloneItemStack();
                    itemstack.count = 1;

                    if (itemarmor.d_(itemstack1))
                    {
                        int l = itemarmor.b(itemstack);
                        float f = (float)(l >> 16 & 255) / 255.0F;
                        float f1 = (float)(l >> 8 & 255) / 255.0F;
                        float f2 = (float)(l & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                        ++j;
                    }
                }
                else
                {
                    if (itemstack1.getItem() != Items.DYE)
                    {
                        return null;
                    }

                    float[] afloat = EntitySheep.a(EnumColor.fromInvColorIndex(itemstack1.getData()));
                    int i1 = (int)(afloat[0] * 255.0F);
                    int j1 = (int)(afloat[1] * 255.0F);
                    int k1 = (int)(afloat[2] * 255.0F);
                    i += Math.max(i1, Math.max(j1, k1));
                    aint[0] += i1;
                    aint[1] += j1;
                    aint[2] += k1;
                    ++j;
                }
            }
        }

        if (itemarmor == null)
        {
            return null;
        }
        else
        {
            int l1 = aint[0] / j;
            int i2 = aint[1] / j;
            int j2 = aint[2] / j;
            float f3 = (float)i / (float)j;
            float f4 = (float)Math.max(l1, Math.max(i2, j2));
            l1 = (int)((float)l1 * f3 / f4);
            i2 = (int)((float)i2 * f3 / f4);
            j2 = (int)((float)j2 * f3 / f4);
            int k2 = (l1 << 8) + i2;
            k2 = (k2 << 8) + j2;
            itemarmor.b(itemstack, k2);
            return itemstack;
        }
    }

    public int a()
    {
        return 10;
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

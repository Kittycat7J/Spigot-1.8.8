package net.minecraft.server.v1_8_R3;

import java.util.Arrays;

public class RecipesBanner
{
    void a(CraftingManager p_a_1_)
    {
        for (EnumColor enumcolor : EnumColor.values())
        {
            p_a_1_.registerShapedRecipe(new ItemStack(Items.BANNER, 1, enumcolor.getInvColorIndex()), new Object[] {"###", "###", " | ", '#', new ItemStack(Blocks.WOOL, 1, enumcolor.getColorIndex()), '|', Items.STICK});
        }

        p_a_1_.a(new RecipesBanner.DuplicateRecipe((RecipesBanner.SyntheticClass_1)null));
        p_a_1_.a(new RecipesBanner.AddRecipe((RecipesBanner.SyntheticClass_1)null));
    }

    static class AddRecipe extends ShapelessRecipes implements IRecipe
    {
        private AddRecipe()
        {
            super(new ItemStack(Items.BANNER, 0, 0), Arrays.asList(new ItemStack[] {new ItemStack(Items.BANNER)}));
        }

        public boolean a(InventoryCrafting p_a_1_, World p_a_2_)
        {
            boolean flag = false;

            for (int i = 0; i < p_a_1_.getSize(); ++i)
            {
                ItemStack itemstack = p_a_1_.getItem(i);

                if (itemstack != null && itemstack.getItem() == Items.BANNER)
                {
                    if (flag)
                    {
                        return false;
                    }

                    if (TileEntityBanner.c(itemstack) >= 6)
                    {
                        return false;
                    }

                    flag = true;
                }
            }

            if (!flag)
            {
                return false;
            }
            else if (this.c(p_a_1_) != null)
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

            for (int i = 0; i < p_craftItem_1_.getSize(); ++i)
            {
                ItemStack itemstack1 = p_craftItem_1_.getItem(i);

                if (itemstack1 != null && itemstack1.getItem() == Items.BANNER)
                {
                    itemstack = itemstack1.cloneItemStack();
                    itemstack.count = 1;
                    break;
                }
            }

            TileEntityBanner.EnumBannerPatternType tileentitybanner$enumbannerpatterntype = this.c(p_craftItem_1_);

            if (tileentitybanner$enumbannerpatterntype != null)
            {
                int k = 0;

                for (int j = 0; j < p_craftItem_1_.getSize(); ++j)
                {
                    ItemStack itemstack2 = p_craftItem_1_.getItem(j);

                    if (itemstack2 != null && itemstack2.getItem() == Items.DYE)
                    {
                        k = itemstack2.getData();
                        break;
                    }
                }

                NBTTagCompound nbttagcompound1 = itemstack.a("BlockEntityTag", true);
                ItemStack itemstack3 = null;
                NBTTagList nbttaglist;

                if (nbttagcompound1.hasKeyOfType("Patterns", 9))
                {
                    nbttaglist = nbttagcompound1.getList("Patterns", 10);
                }
                else
                {
                    nbttaglist = new NBTTagList();
                    nbttagcompound1.set("Patterns", nbttaglist);
                }

                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setString("Pattern", tileentitybanner$enumbannerpatterntype.b());
                nbttagcompound.setInt("Color", k);
                nbttaglist.add(nbttagcompound);
            }

            return itemstack;
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

        private TileEntityBanner.EnumBannerPatternType c(InventoryCrafting p_c_1_)
        {
            for (TileEntityBanner.EnumBannerPatternType tileentitybanner$enumbannerpatterntype : TileEntityBanner.EnumBannerPatternType.values())
            {
                if (tileentitybanner$enumbannerpatterntype.d())
                {
                    boolean flag = true;

                    if (tileentitybanner$enumbannerpatterntype.e())
                    {
                        boolean flag1 = false;
                        boolean flag2 = false;

                        for (int i = 0; i < p_c_1_.getSize() && flag; ++i)
                        {
                            ItemStack itemstack = p_c_1_.getItem(i);

                            if (itemstack != null && itemstack.getItem() != Items.BANNER)
                            {
                                if (itemstack.getItem() == Items.DYE)
                                {
                                    if (flag2)
                                    {
                                        flag = false;
                                        break;
                                    }

                                    flag2 = true;
                                }
                                else
                                {
                                    if (flag1 || !itemstack.doMaterialsMatch(tileentitybanner$enumbannerpatterntype.f()))
                                    {
                                        flag = false;
                                        break;
                                    }

                                    flag1 = true;
                                }
                            }
                        }

                        if (!flag1)
                        {
                            flag = false;
                        }
                    }
                    else if (p_c_1_.getSize() != tileentitybanner$enumbannerpatterntype.c().length * tileentitybanner$enumbannerpatterntype.c()[0].length())
                    {
                        flag = false;
                    }
                    else
                    {
                        int j = -1;

                        for (int k = 0; k < p_c_1_.getSize() && flag; ++k)
                        {
                            int l = k / 3;
                            int i1 = k % 3;
                            ItemStack itemstack1 = p_c_1_.getItem(k);

                            if (itemstack1 != null && itemstack1.getItem() != Items.BANNER)
                            {
                                if (itemstack1.getItem() != Items.DYE)
                                {
                                    flag = false;
                                    break;
                                }

                                if (j != -1 && j != itemstack1.getData())
                                {
                                    flag = false;
                                    break;
                                }

                                if (tileentitybanner$enumbannerpatterntype.c()[l].charAt(i1) == 32)
                                {
                                    flag = false;
                                    break;
                                }

                                j = itemstack1.getData();
                            }
                            else if (tileentitybanner$enumbannerpatterntype.c()[l].charAt(i1) != 32)
                            {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (flag)
                    {
                        return tileentitybanner$enumbannerpatterntype;
                    }
                }
            }

            return null;
        }

        AddRecipe(RecipesBanner.SyntheticClass_1 p_i276_1_)
        {
            this();
        }
    }

    static class DuplicateRecipe extends ShapelessRecipes implements IRecipe
    {
        private DuplicateRecipe()
        {
            super(new ItemStack(Items.BANNER, 0, 0), Arrays.asList(new ItemStack[] {new ItemStack(Items.DYE, 0, 5)}));
        }

        public boolean a(InventoryCrafting p_a_1_, World p_a_2_)
        {
            ItemStack itemstack = null;
            ItemStack itemstack1 = null;

            for (int i = 0; i < p_a_1_.getSize(); ++i)
            {
                ItemStack itemstack2 = p_a_1_.getItem(i);

                if (itemstack2 != null)
                {
                    if (itemstack2.getItem() != Items.BANNER)
                    {
                        return false;
                    }

                    if (itemstack != null && itemstack1 != null)
                    {
                        return false;
                    }

                    int j = TileEntityBanner.b(itemstack2);
                    boolean flag = TileEntityBanner.c(itemstack2) > 0;

                    if (itemstack != null)
                    {
                        if (flag)
                        {
                            return false;
                        }

                        if (j != TileEntityBanner.b(itemstack))
                        {
                            return false;
                        }

                        itemstack1 = itemstack2;
                    }
                    else if (itemstack1 != null)
                    {
                        if (!flag)
                        {
                            return false;
                        }

                        if (j != TileEntityBanner.b(itemstack1))
                        {
                            return false;
                        }

                        itemstack = itemstack2;
                    }
                    else if (flag)
                    {
                        itemstack = itemstack2;
                    }
                    else
                    {
                        itemstack1 = itemstack2;
                    }
                }
            }

            if (itemstack != null && itemstack1 != null)
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
            for (int i = 0; i < p_craftItem_1_.getSize(); ++i)
            {
                ItemStack itemstack = p_craftItem_1_.getItem(i);

                if (itemstack != null && TileEntityBanner.c(itemstack) > 0)
                {
                    ItemStack itemstack1 = itemstack.cloneItemStack();
                    itemstack1.count = 1;
                    return itemstack1;
                }
            }

            return null;
        }

        public int a()
        {
            return 2;
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

                if (itemstack != null)
                {
                    if (itemstack.getItem().r())
                    {
                        aitemstack[i] = new ItemStack(itemstack.getItem().q());
                    }
                    else if (itemstack.hasTag() && TileEntityBanner.c(itemstack) > 0)
                    {
                        aitemstack[i] = itemstack.cloneItemStack();
                        aitemstack[i].count = 1;
                    }
                }
            }

            return aitemstack;
        }

        DuplicateRecipe(RecipesBanner.SyntheticClass_1 p_i320_1_)
        {
            this();
        }
    }

    static class SyntheticClass_1
    {
    }
}

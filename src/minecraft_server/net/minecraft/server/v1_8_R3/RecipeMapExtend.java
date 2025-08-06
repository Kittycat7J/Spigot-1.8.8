package net.minecraft.server.v1_8_R3;

public class RecipeMapExtend extends ShapedRecipes
{
    public RecipeMapExtend()
    {
        super(3, 3, new ItemStack[] {new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.FILLED_MAP, 0, 32767), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER)}, new ItemStack(Items.MAP, 0, 0));
    }

    public boolean a(InventoryCrafting p_a_1_, World p_a_2_)
    {
        if (!super.a(p_a_1_, p_a_2_))
        {
            return false;
        }
        else
        {
            ItemStack itemstack = null;

            for (int i = 0; i < p_a_1_.getSize() && itemstack == null; ++i)
            {
                ItemStack itemstack1 = p_a_1_.getItem(i);

                if (itemstack1 != null && itemstack1.getItem() == Items.FILLED_MAP)
                {
                    itemstack = itemstack1;
                }
            }

            if (itemstack == null)
            {
                return false;
            }
            else
            {
                WorldMap worldmap = Items.FILLED_MAP.getSavedMap(itemstack, p_a_2_);
                return worldmap == null ? false : worldmap.scale < 4;
            }
        }
    }

    public ItemStack craftItem(InventoryCrafting p_craftItem_1_)
    {
        ItemStack itemstack = null;

        for (int i = 0; i < p_craftItem_1_.getSize() && itemstack == null; ++i)
        {
            ItemStack itemstack1 = p_craftItem_1_.getItem(i);

            if (itemstack1 != null && itemstack1.getItem() == Items.FILLED_MAP)
            {
                itemstack = itemstack1;
            }
        }

        itemstack = itemstack.cloneItemStack();
        itemstack.count = 1;

        if (itemstack.getTag() == null)
        {
            itemstack.setTag(new NBTTagCompound());
        }

        itemstack.getTag().setBoolean("map_is_scaling", true);
        return itemstack;
    }
}

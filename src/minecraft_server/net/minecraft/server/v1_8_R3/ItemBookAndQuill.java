package net.minecraft.server.v1_8_R3;

public class ItemBookAndQuill extends Item
{
    public ItemBookAndQuill()
    {
        this.c(1);
    }

    public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_)
    {
        p_a_3_.openBook(p_a_1_);
        p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
        return p_a_1_;
    }

    public static boolean b(NBTTagCompound p_b_0_)
    {
        if (p_b_0_ == null)
        {
            return false;
        }
        else if (!p_b_0_.hasKeyOfType("pages", 9))
        {
            return false;
        }
        else
        {
            NBTTagList nbttaglist = p_b_0_.getList("pages", 8);

            for (int i = 0; i < nbttaglist.size(); ++i)
            {
                String s = nbttaglist.getString(i);

                if (s == null)
                {
                    return false;
                }

                if (s.length() > 32767)
                {
                    return false;
                }
            }

            return true;
        }
    }
}

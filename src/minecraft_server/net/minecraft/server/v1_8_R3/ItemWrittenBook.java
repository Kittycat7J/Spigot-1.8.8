package net.minecraft.server.v1_8_R3;

public class ItemWrittenBook extends Item
{
    public ItemWrittenBook()
    {
        this.c(1);
    }

    public static boolean b(NBTTagCompound p_b_0_)
    {
        if (!ItemBookAndQuill.b(p_b_0_))
        {
            return false;
        }
        else if (!p_b_0_.hasKeyOfType("title", 8))
        {
            return false;
        }
        else
        {
            String s = p_b_0_.getString("title");
            return s != null && s.length() <= 32 ? p_b_0_.hasKeyOfType("author", 8) : false;
        }
    }

    public static int h(ItemStack p_h_0_)
    {
        return p_h_0_.getTag().getInt("generation");
    }

    public String a(ItemStack p_a_1_)
    {
        if (p_a_1_.hasTag())
        {
            NBTTagCompound nbttagcompound = p_a_1_.getTag();
            String s = nbttagcompound.getString("title");

            if (!UtilColor.b(s))
            {
                return s;
            }
        }

        return super.a(p_a_1_);
    }

    public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_)
    {
        if (!p_a_2_.isClientSide)
        {
            this.a(p_a_1_, p_a_3_);
        }

        p_a_3_.openBook(p_a_1_);
        p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
        return p_a_1_;
    }

    private void a(ItemStack p_a_1_, EntityHuman p_a_2_)
    {
        if (p_a_1_ != null && p_a_1_.getTag() != null)
        {
            NBTTagCompound nbttagcompound = p_a_1_.getTag();

            if (!nbttagcompound.getBoolean("resolved"))
            {
                nbttagcompound.setBoolean("resolved", true);

                if (b(nbttagcompound))
                {
                    NBTTagList nbttaglist = nbttagcompound.getList("pages", 8);

                    for (int i = 0; i < nbttaglist.size(); ++i)
                    {
                        String s = nbttaglist.getString(i);
                        IChatBaseComponent ichatbasecomponent;

                        try
                        {
                            ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(s);
                            ichatbasecomponent = ChatComponentUtils.filterForDisplay(p_a_2_, ichatbasecomponent, p_a_2_);
                        }
                        catch (Exception var9)
                        {
                            ichatbasecomponent = new ChatComponentText(s);
                        }

                        nbttaglist.a(i, new NBTTagString(IChatBaseComponent.ChatSerializer.a(ichatbasecomponent)));
                    }

                    nbttagcompound.set("pages", nbttaglist);

                    if (p_a_2_ instanceof EntityPlayer && p_a_2_.bZ() == p_a_1_)
                    {
                        Slot slot = p_a_2_.activeContainer.getSlot(p_a_2_.inventory, p_a_2_.inventory.itemInHandIndex);
                        ((EntityPlayer)p_a_2_).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, slot.rawSlotIndex, p_a_1_));
                    }
                }
            }
        }
    }
}

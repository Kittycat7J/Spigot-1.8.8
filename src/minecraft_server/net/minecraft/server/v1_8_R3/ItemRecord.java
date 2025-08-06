package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;

public class ItemRecord extends Item
{
    private static final Map<String, ItemRecord> b = Maps.<String, ItemRecord>newHashMap();
    public final String a;

    protected ItemRecord(String p_i225_1_)
    {
        this.a = p_i225_1_;
        this.maxStackSize = 1;
        this.a(CreativeModeTab.f);
        b.put("records." + p_i225_1_, this);
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
        return iblockdata.getBlock() == Blocks.JUKEBOX && !((Boolean)iblockdata.get(BlockJukeBox.HAS_RECORD)).booleanValue() ? (p_interactWith_3_.isClientSide ? true : true) : false;
    }

    public EnumItemRarity g(ItemStack p_g_1_)
    {
        return EnumItemRarity.RARE;
    }
}

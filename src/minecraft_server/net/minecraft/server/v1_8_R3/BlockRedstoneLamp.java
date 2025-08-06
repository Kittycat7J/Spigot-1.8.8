package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockRedstoneLamp extends Block
{
    private final boolean a;

    public BlockRedstoneLamp(boolean p_i443_1_)
    {
        super(Material.BUILDABLE_GLASS);
        this.a = p_i443_1_;

        if (p_i443_1_)
        {
            this.a(1.0F);
        }
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        if (!p_onPlace_1_.isClientSide)
        {
            if (this.a && !p_onPlace_1_.isBlockIndirectlyPowered(p_onPlace_2_))
            {
                p_onPlace_1_.setTypeAndData(p_onPlace_2_, Blocks.REDSTONE_LAMP.getBlockData(), 2);
            }
            else if (!this.a && p_onPlace_1_.isBlockIndirectlyPowered(p_onPlace_2_))
            {
                if (CraftEventFactory.callRedstoneChange(p_onPlace_1_, p_onPlace_2_.getX(), p_onPlace_2_.getY(), p_onPlace_2_.getZ(), 0, 15).getNewCurrent() != 15)
                {
                    return;
                }

                p_onPlace_1_.setTypeAndData(p_onPlace_2_, Blocks.LIT_REDSTONE_LAMP.getBlockData(), 2);
            }
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!p_doPhysics_1_.isClientSide)
        {
            if (this.a && !p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_))
            {
                p_doPhysics_1_.a((BlockPosition)p_doPhysics_2_, (Block)this, 4);
            }
            else if (!this.a && p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_))
            {
                if (CraftEventFactory.callRedstoneChange(p_doPhysics_1_, p_doPhysics_2_.getX(), p_doPhysics_2_.getY(), p_doPhysics_2_.getZ(), 0, 15).getNewCurrent() != 15)
                {
                    return;
                }

                p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, Blocks.LIT_REDSTONE_LAMP.getBlockData(), 2);
            }
        }
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (!p_b_1_.isClientSide && this.a && !p_b_1_.isBlockIndirectlyPowered(p_b_2_))
        {
            if (CraftEventFactory.callRedstoneChange(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), 15, 0).getNewCurrent() != 0)
            {
                return;
            }

            p_b_1_.setTypeAndData(p_b_2_, Blocks.REDSTONE_LAMP.getBlockData(), 2);
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Item.getItemOf(Blocks.REDSTONE_LAMP);
    }

    protected ItemStack i(IBlockData p_i_1_)
    {
        return new ItemStack(Blocks.REDSTONE_LAMP);
    }
}

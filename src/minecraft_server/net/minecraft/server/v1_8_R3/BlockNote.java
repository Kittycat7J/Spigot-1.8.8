package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;

public class BlockNote extends BlockContainer
{
    private static final List<String> a = Lists.newArrayList(new String[] {"harp", "bd", "snare", "hat", "bassattack"});

    public BlockNote()
    {
        super(Material.WOOD);
        this.a(CreativeModeTab.d);
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        boolean flag = p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_);
        TileEntity tileentity = p_doPhysics_1_.getTileEntity(p_doPhysics_2_);

        if (tileentity instanceof TileEntityNote)
        {
            TileEntityNote tileentitynote = (TileEntityNote)tileentity;

            if (tileentitynote.f != flag)
            {
                if (flag)
                {
                    tileentitynote.play(p_doPhysics_1_, p_doPhysics_2_);
                }

                tileentitynote.f = flag;
            }
        }
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (p_interact_1_.isClientSide)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = p_interact_1_.getTileEntity(p_interact_2_);

            if (tileentity instanceof TileEntityNote)
            {
                TileEntityNote tileentitynote = (TileEntityNote)tileentity;
                tileentitynote.b();
                tileentitynote.play(p_interact_1_, p_interact_2_);
                p_interact_4_.b(StatisticList.S);
            }

            return true;
        }
    }

    public void attack(World p_attack_1_, BlockPosition p_attack_2_, EntityHuman p_attack_3_)
    {
        if (!p_attack_1_.isClientSide)
        {
            TileEntity tileentity = p_attack_1_.getTileEntity(p_attack_2_);

            if (tileentity instanceof TileEntityNote)
            {
                ((TileEntityNote)tileentity).play(p_attack_1_, p_attack_2_);
                p_attack_3_.b(StatisticList.R);
            }
        }
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityNote();
    }

    private String b(int p_b_1_)
    {
        if (p_b_1_ < 0 || p_b_1_ >= a.size())
        {
            p_b_1_ = 0;
        }

        return (String)a.get(p_b_1_);
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_, int p_a_5_)
    {
        float f = (float)Math.pow(2.0D, (double)(p_a_5_ - 12) / 12.0D);
        p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.5D, (double)p_a_2_.getZ() + 0.5D, "note." + this.b(p_a_4_), 3.0F, f);
        p_a_1_.addParticle(EnumParticle.NOTE, (double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 1.2D, (double)p_a_2_.getZ() + 0.5D, (double)p_a_5_ / 24.0D, 0.0D, 0.0D, new int[0]);
        return true;
    }

    public int b()
    {
        return 3;
    }
}

package net.minecraft.server.v1_8_R3;

public class BlockSlime extends BlockHalfTransparent
{
    public BlockSlime()
    {
        super(Material.CLAY, false, MaterialMapColor.c);
        this.a(CreativeModeTab.c);
        this.frictionFactor = 0.8F;
    }

    public void fallOn(World p_fallOn_1_, BlockPosition p_fallOn_2_, Entity p_fallOn_3_, float p_fallOn_4_)
    {
        if (p_fallOn_3_.isSneaking())
        {
            super.fallOn(p_fallOn_1_, p_fallOn_2_, p_fallOn_3_, p_fallOn_4_);
        }
        else
        {
            p_fallOn_3_.e(p_fallOn_4_, 0.0F);
        }
    }

    public void a(World p_a_1_, Entity p_a_2_)
    {
        if (p_a_2_.isSneaking())
        {
            super.a(p_a_1_, p_a_2_);
        }
        else if (p_a_2_.motY < 0.0D)
        {
            p_a_2_.motY = -p_a_2_.motY;
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, Entity p_a_3_)
    {
        if (Math.abs(p_a_3_.motY) < 0.1D && !p_a_3_.isSneaking())
        {
            double d0 = 0.4D + Math.abs(p_a_3_.motY) * 0.2D;
            p_a_3_.motX *= d0;
            p_a_3_.motZ *= d0;
        }

        super.a(p_a_1_, p_a_2_, p_a_3_);
    }
}

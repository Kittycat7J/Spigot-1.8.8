package net.minecraft.server.v1_8_R3;

public class WorldGenFlatLayerInfo
{
    private final int a;
    private IBlockData b;
    private int c;
    private int d;

    public WorldGenFlatLayerInfo(int p_i714_1_, Block p_i714_2_)
    {
        this(3, p_i714_1_, p_i714_2_);
    }

    public WorldGenFlatLayerInfo(int p_i715_1_, int p_i715_2_, Block p_i715_3_)
    {
        this.c = 1;
        this.a = p_i715_1_;
        this.c = p_i715_2_;
        this.b = p_i715_3_.getBlockData();
    }

    public WorldGenFlatLayerInfo(int p_i716_1_, int p_i716_2_, Block p_i716_3_, int p_i716_4_)
    {
        this(p_i716_1_, p_i716_2_, p_i716_3_);
        this.b = p_i716_3_.fromLegacyData(p_i716_4_);
    }

    public int b()
    {
        return this.c;
    }

    public IBlockData c()
    {
        return this.b;
    }

    private Block e()
    {
        return this.b.getBlock();
    }

    private int f()
    {
        return this.b.getBlock().toLegacyData(this.b);
    }

    public int d()
    {
        return this.d;
    }

    public void b(int p_b_1_)
    {
        this.d = p_b_1_;
    }

    public String toString()
    {
        String s;

        if (this.a >= 3)
        {
            MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.e());
            s = minecraftkey == null ? "null" : minecraftkey.toString();

            if (this.c > 1)
            {
                s = this.c + "*" + s;
            }
        }
        else
        {
            s = Integer.toString(Block.getId(this.e()));

            if (this.c > 1)
            {
                s = this.c + "x" + s;
            }
        }

        int i = this.f();

        if (i > 0)
        {
            s = s + ":" + i;
        }

        return s;
    }
}

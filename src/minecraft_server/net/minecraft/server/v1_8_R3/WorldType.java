package net.minecraft.server.v1_8_R3;

public class WorldType
{
    public static final WorldType[] types = new WorldType[16];
    public static final WorldType NORMAL = (new WorldType(0, "default", 1)).i();
    public static final WorldType FLAT = new WorldType(1, "flat");
    public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
    public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).j();
    public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
    public static final WorldType DEBUG_ALL_BLOCK_STATES = new WorldType(5, "debug_all_block_states");
    public static final WorldType NORMAL_1_1 = (new WorldType(8, "default_1_1", 0)).a(false);
    private final int i;
    private final String name;
    private final int version;
    private boolean l;
    private boolean m;
    private boolean n;

    private WorldType(int p_i558_1_, String p_i558_2_)
    {
        this(p_i558_1_, p_i558_2_, 0);
    }

    private WorldType(int p_i559_1_, String p_i559_2_, int p_i559_3_)
    {
        this.name = p_i559_2_;
        this.version = p_i559_3_;
        this.l = true;
        this.i = p_i559_1_;
        types[p_i559_1_] = this;
    }

    public String name()
    {
        return this.name;
    }

    public int getVersion()
    {
        return this.version;
    }

    public WorldType a(int p_a_1_)
    {
        return this == NORMAL && p_a_1_ == 0 ? NORMAL_1_1 : this;
    }

    private WorldType a(boolean p_a_1_)
    {
        this.l = p_a_1_;
        return this;
    }

    private WorldType i()
    {
        this.m = true;
        return this;
    }

    public boolean f()
    {
        return this.m;
    }

    public static WorldType getType(String p_getType_0_)
    {
        for (int ix = 0; ix < types.length; ++ix)
        {
            if (types[ix] != null && types[ix].name.equalsIgnoreCase(p_getType_0_))
            {
                return types[ix];
            }
        }

        return null;
    }

    public int g()
    {
        return this.i;
    }

    private WorldType j()
    {
        this.n = true;
        return this;
    }
}

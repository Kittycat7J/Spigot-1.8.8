package net.minecraft.server.v1_8_R3;

public class TileEntityMobSpawnerData extends WeightedRandom.WeightedRandomChoice
{
    private final NBTTagCompound c;
    private final String d;

    public TileEntityMobSpawnerData(MobSpawnerAbstract p_i547_1_, NBTTagCompound p_i547_2_)
    {
        this(p_i547_1_, p_i547_2_.getCompound("Properties"), p_i547_2_.getString("Type"), p_i547_2_.getInt("Weight"));
    }

    public TileEntityMobSpawnerData(MobSpawnerAbstract p_i548_1_, NBTTagCompound p_i548_2_, String p_i548_3_)
    {
        this(p_i548_1_, p_i548_2_, p_i548_3_, 1);
    }

    private TileEntityMobSpawnerData(MobSpawnerAbstract p_i549_1_, NBTTagCompound p_i549_2_, String p_i549_3_, int p_i549_4_)
    {
        super(p_i549_4_);
        this.b = p_i549_1_;

        if (p_i549_3_.equals("Minecart"))
        {
            if (p_i549_2_ != null)
            {
                p_i549_3_ = EntityMinecartAbstract.EnumMinecartType.a(p_i549_2_.getInt("Type")).b();
            }
            else
            {
                p_i549_3_ = "MinecartRideable";
            }
        }

        this.c = p_i549_2_;
        this.d = p_i549_3_;
    }

    public NBTTagCompound a()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.set("Properties", this.c);
        nbttagcompound.setString("Type", this.d);
        nbttagcompound.setInt("Weight", this.a);
        return nbttagcompound;
    }
}

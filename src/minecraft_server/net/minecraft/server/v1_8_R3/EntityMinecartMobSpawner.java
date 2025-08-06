package net.minecraft.server.v1_8_R3;

public class EntityMinecartMobSpawner extends EntityMinecartAbstract
{
    private final MobSpawnerAbstract a = new MobSpawnerAbstract()
    {
        public void a(int p_a_1_)
        {
            EntityMinecartMobSpawner.this.world.broadcastEntityEffect(EntityMinecartMobSpawner.this, (byte)p_a_1_);
        }
        public World a()
        {
            return EntityMinecartMobSpawner.this.world;
        }
        public BlockPosition b()
        {
            return new BlockPosition(EntityMinecartMobSpawner.this);
        }
    };

    public EntityMinecartMobSpawner(World p_i1226_1_)
    {
        super(p_i1226_1_);
    }

    public EntityMinecartMobSpawner(World p_i1227_1_, double p_i1227_2_, double p_i1227_4_, double p_i1227_6_)
    {
        super(p_i1227_1_, p_i1227_2_, p_i1227_4_, p_i1227_6_);
    }

    public EntityMinecartAbstract.EnumMinecartType s()
    {
        return EntityMinecartAbstract.EnumMinecartType.SPAWNER;
    }

    public IBlockData u()
    {
        return Blocks.MOB_SPAWNER.getBlockData();
    }

    protected void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.a.a(p_a_1_);
    }

    protected void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        this.a.b(p_b_1_);
    }

    public void t_()
    {
        super.t_();
        this.a.c();
    }
}

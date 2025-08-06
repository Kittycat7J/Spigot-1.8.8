package net.minecraft.server.v1_8_R3;

public enum EnumCreatureType
{
    MONSTER(IMonster.class, 70, Material.AIR, false, false),
    CREATURE(EntityAnimal.class, 10, Material.AIR, true, true),
    AMBIENT(EntityAmbient.class, 15, Material.AIR, true, false),
    WATER_CREATURE(EntityWaterAnimal.class, 5, Material.WATER, true, false);

    private final Class <? extends IAnimal > e;
    private final int f;
    private final Material g;
    private final boolean h;
    private final boolean i;

    private EnumCreatureType(Class <? extends IAnimal > p_i1147_3_, int p_i1147_4_, Material p_i1147_5_, boolean p_i1147_6_, boolean p_i1147_7_)
    {
        this.e = p_i1147_3_;
        this.f = p_i1147_4_;
        this.g = p_i1147_5_;
        this.h = p_i1147_6_;
        this.i = p_i1147_7_;
    }

    public Class <? extends IAnimal > a()
    {
        return this.e;
    }

    public int b()
    {
        return this.f;
    }

    public boolean d()
    {
        return this.h;
    }

    public boolean e()
    {
        return this.i;
    }
}

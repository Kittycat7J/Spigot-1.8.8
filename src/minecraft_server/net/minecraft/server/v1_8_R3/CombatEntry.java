package net.minecraft.server.v1_8_R3;

public class CombatEntry
{
    private final DamageSource a;
    private final int b;
    private final float c;
    private final float d;
    private final String e;
    private final float f;

    public CombatEntry(DamageSource p_i1131_1_, int p_i1131_2_, float p_i1131_3_, float p_i1131_4_, String p_i1131_5_, float p_i1131_6_)
    {
        this.a = p_i1131_1_;
        this.b = p_i1131_2_;
        this.c = p_i1131_4_;
        this.d = p_i1131_3_;
        this.e = p_i1131_5_;
        this.f = p_i1131_6_;
    }

    public DamageSource a()
    {
        return this.a;
    }

    public float c()
    {
        return this.c;
    }

    public boolean f()
    {
        return this.a.getEntity() instanceof EntityLiving;
    }

    public String g()
    {
        return this.e;
    }

    public IChatBaseComponent h()
    {
        return this.a().getEntity() == null ? null : this.a().getEntity().getScoreboardDisplayName();
    }

    public float i()
    {
        return this.a == DamageSource.OUT_OF_WORLD ? Float.MAX_VALUE : this.f;
    }
}

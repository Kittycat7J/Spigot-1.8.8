package net.minecraft.server.v1_8_R3;

public class EntityComplexPart extends Entity
{
    public final IComplex owner;
    public final String b;

    public EntityComplexPart(IComplex p_i1214_1_, String p_i1214_2_, float p_i1214_3_, float p_i1214_4_)
    {
        super(p_i1214_1_.a());
        this.setSize(p_i1214_3_, p_i1214_4_);
        this.owner = p_i1214_1_;
        this.b = p_i1214_2_;
    }

    protected void h()
    {
    }

    protected void a(NBTTagCompound p_a_1_)
    {
    }

    protected void b(NBTTagCompound p_b_1_)
    {
    }

    public boolean ad()
    {
        return true;
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        return this.isInvulnerable(p_damageEntity_1_) ? false : this.owner.a(this, p_damageEntity_1_, p_damageEntity_2_);
    }

    public boolean k(Entity p_k_1_)
    {
        return this == p_k_1_ || this.owner == p_k_1_;
    }
}

package net.minecraft.server.v1_8_R3;

public class MobEffectAttackDamage extends MobEffectList
{
    protected MobEffectAttackDamage(int p_i1137_1_, MinecraftKey p_i1137_2_, boolean p_i1137_3_, int p_i1137_4_)
    {
        super(p_i1137_1_, p_i1137_2_, p_i1137_3_, p_i1137_4_);
    }

    public double a(int p_a_1_, AttributeModifier p_a_2_)
    {
        return this.id == MobEffectList.WEAKNESS.id ? (double)(-0.5F * (float)(p_a_1_ + 1)) : 1.3D * (double)(p_a_1_ + 1);
    }
}

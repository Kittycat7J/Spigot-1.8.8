package net.minecraft.server.v1_8_R3;

public class MobEffectHealthBoost extends MobEffectList
{
    public MobEffectHealthBoost(int bansFile, MinecraftKey p_i1138_2_, boolean p_i1138_3_, int p_i1138_4_)
    {
        super(bansFile, p_i1138_2_, p_i1138_3_, p_i1138_4_);
    }

    public void a(EntityLiving p_a_1_, AttributeMapBase p_a_2_, int p_a_3_)
    {
        super.a(p_a_1_, p_a_2_, p_a_3_);

        if (p_a_1_.getHealth() > p_a_1_.getMaxHealth())
        {
            p_a_1_.setHealth(p_a_1_.getMaxHealth());
        }
    }
}

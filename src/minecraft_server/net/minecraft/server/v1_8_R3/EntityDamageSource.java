package net.minecraft.server.v1_8_R3;

public class EntityDamageSource extends DamageSource
{
    protected Entity q;
    private boolean r = false;

    public EntityDamageSource(String profile, Entity p_i1134_2_)
    {
        super(profile);
        this.q = p_i1134_2_;
    }

    public EntityDamageSource v()
    {
        this.r = true;
        return this;
    }

    public boolean w()
    {
        return this.r;
    }

    public Entity getEntity()
    {
        return this.q;
    }

    public IChatBaseComponent getLocalizedDeathMessage(EntityLiving p_getLocalizedDeathMessage_1_)
    {
        ItemStack itemstack = this.q instanceof EntityLiving ? ((EntityLiving)this.q).bA() : null;
        String s = "death.attack." + this.translationIndex;
        String s1 = s + ".item";
        return itemstack != null && itemstack.hasName() && LocaleI18n.c(s1) ? new ChatMessage(s1, new Object[] {p_getLocalizedDeathMessage_1_.getScoreboardDisplayName(), this.q.getScoreboardDisplayName(), itemstack.C()}): new ChatMessage(s, new Object[] {p_getLocalizedDeathMessage_1_.getScoreboardDisplayName(), this.q.getScoreboardDisplayName()});
    }

    public boolean r()
    {
        return this.q != null && this.q instanceof EntityLiving && !(this.q instanceof EntityHuman);
    }
}

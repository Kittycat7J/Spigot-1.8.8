package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Multimap;
import java.util.Set;

public class ItemTool extends Item
{
    private Set<Block> c;
    protected float a = 4.0F;
    private float d;
    protected Item.EnumToolMaterial b;

    protected ItemTool(float p_i1272_1_, Item.EnumToolMaterial p_i1272_2_, Set<Block> p_i1272_3_)
    {
        this.b = p_i1272_2_;
        this.c = p_i1272_3_;
        this.maxStackSize = 1;
        this.setMaxDurability(p_i1272_2_.a());
        this.a = p_i1272_2_.b();
        this.d = p_i1272_1_ + p_i1272_2_.c();
        this.a(CreativeModeTab.i);
    }

    public float getDestroySpeed(ItemStack p_getDestroySpeed_1_, Block p_getDestroySpeed_2_)
    {
        return this.c.contains(p_getDestroySpeed_2_) ? this.a : 1.0F;
    }

    public boolean a(ItemStack p_a_1_, EntityLiving p_a_2_, EntityLiving p_a_3_)
    {
        p_a_1_.damage(2, p_a_3_);
        return true;
    }

    public boolean a(ItemStack p_a_1_, World p_a_2_, Block p_a_3_, BlockPosition p_a_4_, EntityLiving p_a_5_)
    {
        if ((double)p_a_3_.g(p_a_2_, p_a_4_) != 0.0D)
        {
            p_a_1_.damage(1, p_a_5_);
        }

        return true;
    }

    public Item.EnumToolMaterial g()
    {
        return this.b;
    }

    public int b()
    {
        return this.b.e();
    }

    public String h()
    {
        return this.b.toString();
    }

    public boolean a(ItemStack p_a_1_, ItemStack p_a_2_)
    {
        return this.b.f() == p_a_2_.getItem() ? true : super.a(p_a_1_, p_a_2_);
    }

    public Multimap<String, AttributeModifier> i()
    {
        Multimap multimap = super.i();
        multimap.put(GenericAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(f, "Tool modifier", (double)this.d, 0));
        return multimap;
    }
}

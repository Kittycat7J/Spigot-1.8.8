package net.minecraft.server.v1_8_R3;

public class EnchantmentProtection extends Enchantment
{
    private static final String[] E = new String[] {"all", "fire", "fall", "explosion", "projectile"};
    private static final int[] F = new int[] {1, 10, 5, 5, 3};
    private static final int[] G = new int[] {11, 8, 6, 8, 6};
    private static final int[] H = new int[] {20, 12, 10, 12, 15};
    public final int a;

    public EnchantmentProtection(int p_i536_1_, MinecraftKey p_i536_2_, int p_i536_3_, int p_i536_4_)
    {
        super(p_i536_1_, p_i536_2_, p_i536_3_, EnchantmentSlotType.ARMOR);
        this.a = p_i536_4_;

        if (p_i536_4_ == 2)
        {
            this.slot = EnchantmentSlotType.ARMOR_FEET;
        }
    }

    public int a(int p_a_1_)
    {
        return F[this.a] + (p_a_1_ - 1) * G[this.a];
    }

    public int b(int p_b_1_)
    {
        return this.a(p_b_1_) + H[this.a];
    }

    public int getMaxLevel()
    {
        return 4;
    }

    public int a(int p_a_1_, DamageSource p_a_2_)
    {
        if (p_a_2_.ignoresInvulnerability())
        {
            return 0;
        }
        else
        {
            float f = (float)(6 + p_a_1_ * p_a_1_) / 3.0F;
            return this.a == 0 ? MathHelper.d(f * 0.75F) : (this.a == 1 && p_a_2_.o() ? MathHelper.d(f * 1.25F) : (this.a == 2 && p_a_2_ == DamageSource.FALL ? MathHelper.d(f * 2.5F) : (this.a == 3 && p_a_2_.isExplosion() ? MathHelper.d(f * 1.5F) : (this.a == 4 && p_a_2_.a() ? MathHelper.d(f * 1.5F) : 0))));
        }
    }

    public String a()
    {
        return "enchantment.protect." + E[this.a];
    }

    public boolean a(Enchantment p_a_1_)
    {
        if (p_a_1_ instanceof EnchantmentProtection)
        {
            EnchantmentProtection enchantmentprotection = (EnchantmentProtection)p_a_1_;
            return enchantmentprotection.a == this.a ? false : this.a == 2 || enchantmentprotection.a == 2;
        }
        else
        {
            return super.a(p_a_1_);
        }
    }

    public static int a(Entity p_a_0_, int p_a_1_)
    {
        int i = EnchantmentManager.a(Enchantment.PROTECTION_FIRE.id, p_a_0_.getEquipment());

        if (i > 0)
        {
            p_a_1_ -= MathHelper.d((float)p_a_1_ * (float)i * 0.15F);
        }

        return p_a_1_;
    }

    public static double a(Entity p_a_0_, double p_a_1_)
    {
        int i = EnchantmentManager.a(Enchantment.PROTECTION_EXPLOSIONS.id, p_a_0_.getEquipment());

        if (i > 0)
        {
            p_a_1_ -= (double)MathHelper.floor(p_a_1_ * (double)((float)i * 0.15F));
        }

        return p_a_1_;
    }
}

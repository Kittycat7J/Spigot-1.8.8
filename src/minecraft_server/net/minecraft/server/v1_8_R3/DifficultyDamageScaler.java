package net.minecraft.server.v1_8_R3;

public class DifficultyDamageScaler
{
    private final EnumDifficulty a;
    private final float b;

    public DifficultyDamageScaler(EnumDifficulty p_i1127_1_, long p_i1127_2_, long p_i1127_4_, float p_i1127_6_)
    {
        this.a = p_i1127_1_;
        this.b = this.a(p_i1127_1_, p_i1127_2_, p_i1127_4_, p_i1127_6_);
    }

    public float c()
    {
        return this.b < 2.0F ? 0.0F : (this.b > 4.0F ? 1.0F : (this.b - 2.0F) / 2.0F);
    }

    private float a(EnumDifficulty p_a_1_, long p_a_2_, long p_a_4_, float p_a_6_)
    {
        if (p_a_1_ == EnumDifficulty.PEACEFUL)
        {
            return 0.0F;
        }
        else
        {
            boolean flag = p_a_1_ == EnumDifficulty.HARD;
            float f = 0.75F;
            float f1 = MathHelper.a(((float)p_a_2_ + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
            f = f + f1;
            float f2 = 0.0F;
            f2 = f2 + MathHelper.a((float)p_a_4_ / 3600000.0F, 0.0F, 1.0F) * (flag ? 1.0F : 0.75F);
            f2 = f2 + MathHelper.a(p_a_6_ * 0.25F, 0.0F, f1);

            if (p_a_1_ == EnumDifficulty.EASY)
            {
                f2 *= 0.5F;
            }

            f = f + f2;
            return (float)p_a_1_.a() * f;
        }
    }
}

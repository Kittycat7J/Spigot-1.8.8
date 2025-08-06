package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class RandomPositionGenerator
{
    private static Vec3D a = new Vec3D(0.0D, 0.0D, 0.0D);

    public static Vec3D a(EntityCreature p_a_0_, int p_a_1_, int p_a_2_)
    {
        return c(p_a_0_, p_a_1_, p_a_2_, (Vec3D)null);
    }

    public static Vec3D a(EntityCreature p_a_0_, int p_a_1_, int p_a_2_, Vec3D p_a_3_)
    {
        a = p_a_3_.a(p_a_0_.locX, p_a_0_.locY, p_a_0_.locZ);
        return c(p_a_0_, p_a_1_, p_a_2_, a);
    }

    public static Vec3D b(EntityCreature p_b_0_, int p_b_1_, int p_b_2_, Vec3D p_b_3_)
    {
        a = (new Vec3D(p_b_0_.locX, p_b_0_.locY, p_b_0_.locZ)).d(p_b_3_);
        return c(p_b_0_, p_b_1_, p_b_2_, a);
    }

    private static Vec3D c(EntityCreature p_c_0_, int p_c_1_, int p_c_2_, Vec3D p_c_3_)
    {
        Random random = p_c_0_.bc();
        boolean flag = false;
        int i = 0;
        int j = 0;
        int k = 0;
        float f = -99999.0F;
        boolean flag1;

        if (p_c_0_.ck())
        {
            double d0 = p_c_0_.ch().c((double)MathHelper.floor(p_c_0_.locX), (double)MathHelper.floor(p_c_0_.locY), (double)MathHelper.floor(p_c_0_.locZ)) + 4.0D;
            double d1 = (double)(p_c_0_.ci() + (float)p_c_1_);
            flag1 = d0 < d1 * d1;
        }
        else
        {
            flag1 = false;
        }

        for (int l = 0; l < 10; ++l)
        {
            int i1 = random.nextInt(2 * p_c_1_ + 1) - p_c_1_;
            int j1 = random.nextInt(2 * p_c_2_ + 1) - p_c_2_;
            int k1 = random.nextInt(2 * p_c_1_ + 1) - p_c_1_;

            if (p_c_3_ == null || (double)i1 * p_c_3_.a + (double)k1 * p_c_3_.c >= 0.0D)
            {
                if (p_c_0_.ck() && p_c_1_ > 1)
                {
                    BlockPosition blockposition = p_c_0_.ch();

                    if (p_c_0_.locX > (double)blockposition.getX())
                    {
                        i1 -= random.nextInt(p_c_1_ / 2);
                    }
                    else
                    {
                        i1 += random.nextInt(p_c_1_ / 2);
                    }

                    if (p_c_0_.locZ > (double)blockposition.getZ())
                    {
                        k1 -= random.nextInt(p_c_1_ / 2);
                    }
                    else
                    {
                        k1 += random.nextInt(p_c_1_ / 2);
                    }
                }

                i1 = i1 + MathHelper.floor(p_c_0_.locX);
                j1 = j1 + MathHelper.floor(p_c_0_.locY);
                k1 = k1 + MathHelper.floor(p_c_0_.locZ);
                BlockPosition blockposition1 = new BlockPosition(i1, j1, k1);

                if (!flag1 || p_c_0_.e(blockposition1))
                {
                    float f1 = p_c_0_.a(blockposition1);

                    if (f1 > f)
                    {
                        f = f1;
                        i = i1;
                        j = j1;
                        k = k1;
                        flag = true;
                    }
                }
            }
        }

        if (flag)
        {
            return new Vec3D((double)i, (double)j, (double)k);
        }
        else
        {
            return null;
        }
    }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.base.Objects;

public class StructureBoundingBox
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;

    public StructureBoundingBox()
    {
    }

    public StructureBoundingBox(int[] p_i717_1_)
    {
        if (p_i717_1_.length == 6)
        {
            this.a = p_i717_1_[0];
            this.b = p_i717_1_[1];
            this.c = p_i717_1_[2];
            this.d = p_i717_1_[3];
            this.e = p_i717_1_[4];
            this.f = p_i717_1_[5];
        }
    }

    public static StructureBoundingBox a()
    {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static StructureBoundingBox a(int p_a_0_, int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, EnumDirection p_a_9_)
    {
        switch (p_a_9_)
        {
            case NORTH:
                return new StructureBoundingBox(p_a_0_ + p_a_3_, p_a_1_ + p_a_4_, p_a_2_ - p_a_8_ + 1 + p_a_5_, p_a_0_ + p_a_6_ - 1 + p_a_3_, p_a_1_ + p_a_7_ - 1 + p_a_4_, p_a_2_ + p_a_5_);

            case SOUTH:
                return new StructureBoundingBox(p_a_0_ + p_a_3_, p_a_1_ + p_a_4_, p_a_2_ + p_a_5_, p_a_0_ + p_a_6_ - 1 + p_a_3_, p_a_1_ + p_a_7_ - 1 + p_a_4_, p_a_2_ + p_a_8_ - 1 + p_a_5_);

            case WEST:
                return new StructureBoundingBox(p_a_0_ - p_a_8_ + 1 + p_a_5_, p_a_1_ + p_a_4_, p_a_2_ + p_a_3_, p_a_0_ + p_a_5_, p_a_1_ + p_a_7_ - 1 + p_a_4_, p_a_2_ + p_a_6_ - 1 + p_a_3_);

            case EAST:
                return new StructureBoundingBox(p_a_0_ + p_a_5_, p_a_1_ + p_a_4_, p_a_2_ + p_a_3_, p_a_0_ + p_a_8_ - 1 + p_a_5_, p_a_1_ + p_a_7_ - 1 + p_a_4_, p_a_2_ + p_a_6_ - 1 + p_a_3_);

            default:
                return new StructureBoundingBox(p_a_0_ + p_a_3_, p_a_1_ + p_a_4_, p_a_2_ + p_a_5_, p_a_0_ + p_a_6_ - 1 + p_a_3_, p_a_1_ + p_a_7_ - 1 + p_a_4_, p_a_2_ + p_a_8_ - 1 + p_a_5_);
        }
    }

    public static StructureBoundingBox a(int p_a_0_, int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_)
    {
        return new StructureBoundingBox(Math.min(p_a_0_, p_a_3_), Math.min(p_a_1_, p_a_4_), Math.min(p_a_2_, p_a_5_), Math.max(p_a_0_, p_a_3_), Math.max(p_a_1_, p_a_4_), Math.max(p_a_2_, p_a_5_));
    }

    public StructureBoundingBox(StructureBoundingBox p_i718_1_)
    {
        this.a = p_i718_1_.a;
        this.b = p_i718_1_.b;
        this.c = p_i718_1_.c;
        this.d = p_i718_1_.d;
        this.e = p_i718_1_.e;
        this.f = p_i718_1_.f;
    }

    public StructureBoundingBox(int p_i719_1_, int p_i719_2_, int p_i719_3_, int p_i719_4_, int p_i719_5_, int p_i719_6_)
    {
        this.a = p_i719_1_;
        this.b = p_i719_2_;
        this.c = p_i719_3_;
        this.d = p_i719_4_;
        this.e = p_i719_5_;
        this.f = p_i719_6_;
    }

    public StructureBoundingBox(BaseBlockPosition p_i720_1_, BaseBlockPosition p_i720_2_)
    {
        this.a = Math.min(p_i720_1_.getX(), p_i720_2_.getX());
        this.b = Math.min(p_i720_1_.getY(), p_i720_2_.getY());
        this.c = Math.min(p_i720_1_.getZ(), p_i720_2_.getZ());
        this.d = Math.max(p_i720_1_.getX(), p_i720_2_.getX());
        this.e = Math.max(p_i720_1_.getY(), p_i720_2_.getY());
        this.f = Math.max(p_i720_1_.getZ(), p_i720_2_.getZ());
    }

    public StructureBoundingBox(int p_i721_1_, int p_i721_2_, int p_i721_3_, int p_i721_4_)
    {
        this.a = p_i721_1_;
        this.c = p_i721_2_;
        this.d = p_i721_3_;
        this.f = p_i721_4_;
        this.b = 1;
        this.e = 512;
    }

    public boolean a(StructureBoundingBox p_a_1_)
    {
        return this.d >= p_a_1_.a && this.a <= p_a_1_.d && this.f >= p_a_1_.c && this.c <= p_a_1_.f && this.e >= p_a_1_.b && this.b <= p_a_1_.e;
    }

    public boolean a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        return this.d >= p_a_1_ && this.a <= p_a_3_ && this.f >= p_a_2_ && this.c <= p_a_4_;
    }

    public void b(StructureBoundingBox p_b_1_)
    {
        this.a = Math.min(this.a, p_b_1_.a);
        this.b = Math.min(this.b, p_b_1_.b);
        this.c = Math.min(this.c, p_b_1_.c);
        this.d = Math.max(this.d, p_b_1_.d);
        this.e = Math.max(this.e, p_b_1_.e);
        this.f = Math.max(this.f, p_b_1_.f);
    }

    public void a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        this.a += p_a_1_;
        this.b += p_a_2_;
        this.c += p_a_3_;
        this.d += p_a_1_;
        this.e += p_a_2_;
        this.f += p_a_3_;
    }

    public boolean b(BaseBlockPosition p_b_1_)
    {
        return p_b_1_.getX() >= this.a && p_b_1_.getX() <= this.d && p_b_1_.getZ() >= this.c && p_b_1_.getZ() <= this.f && p_b_1_.getY() >= this.b && p_b_1_.getY() <= this.e;
    }

    public BaseBlockPosition b()
    {
        return new BaseBlockPosition(this.d - this.a, this.e - this.b, this.f - this.c);
    }

    public int c()
    {
        return this.d - this.a + 1;
    }

    public int d()
    {
        return this.e - this.b + 1;
    }

    public int e()
    {
        return this.f - this.c + 1;
    }

    public BaseBlockPosition f()
    {
        return new BlockPosition(this.a + (this.d - this.a + 1) / 2, this.b + (this.e - this.b + 1) / 2, this.c + (this.f - this.c + 1) / 2);
    }

    public String toString()
    {
        return Objects.toStringHelper(this).add("x0", this.a).add("y0", this.b).add("z0", this.c).add("x1", this.d).add("y1", this.e).add("z1", this.f).toString();
    }

    public NBTTagIntArray g()
    {
        return new NBTTagIntArray(new int[] {this.a, this.b, this.c, this.d, this.e, this.f});
    }
}

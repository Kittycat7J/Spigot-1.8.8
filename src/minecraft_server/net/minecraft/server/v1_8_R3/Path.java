package net.minecraft.server.v1_8_R3;

public class Path
{
    private PathPoint[] a = new PathPoint[128];
    private int b;

    public PathPoint a(PathPoint p_a_1_)
    {
        if (p_a_1_.d >= 0)
        {
            throw new IllegalStateException("OW KNOWS!");
        }
        else
        {
            if (this.b == this.a.length)
            {
                PathPoint[] apathpoint = new PathPoint[this.b << 1];
                System.arraycopy(this.a, 0, apathpoint, 0, this.b);
                this.a = apathpoint;
            }

            this.a[this.b] = p_a_1_;
            p_a_1_.d = this.b;
            this.a(this.b++);
            return p_a_1_;
        }
    }

    public void a()
    {
        this.b = 0;
    }

    public PathPoint c()
    {
        PathPoint pathpoint = this.a[0];
        this.a[0] = this.a[--this.b];
        this.a[this.b] = null;

        if (this.b > 0)
        {
            this.b(0);
        }

        pathpoint.d = -1;
        return pathpoint;
    }

    public void a(PathPoint p_a_1_, float p_a_2_)
    {
        float f = p_a_1_.g;
        p_a_1_.g = p_a_2_;

        if (p_a_2_ < f)
        {
            this.a(p_a_1_.d);
        }
        else
        {
            this.b(p_a_1_.d);
        }
    }

    private void a(int p_a_1_)
    {
        PathPoint pathpoint = this.a[p_a_1_];
        int i;

        for (float f = pathpoint.g; p_a_1_ > 0; p_a_1_ = i)
        {
            i = p_a_1_ - 1 >> 1;
            PathPoint pathpoint1 = this.a[i];

            if (f >= pathpoint1.g)
            {
                break;
            }

            this.a[p_a_1_] = pathpoint1;
            pathpoint1.d = p_a_1_;
        }

        this.a[p_a_1_] = pathpoint;
        pathpoint.d = p_a_1_;
    }

    private void b(int p_b_1_)
    {
        PathPoint pathpoint = this.a[p_b_1_];
        float f = pathpoint.g;

        while (true)
        {
            int i = 1 + (p_b_1_ << 1);
            int j = i + 1;

            if (i >= this.b)
            {
                break;
            }

            PathPoint pathpoint1 = this.a[i];
            float f1 = pathpoint1.g;
            PathPoint pathpoint2;
            float f2;

            if (j >= this.b)
            {
                pathpoint2 = null;
                f2 = Float.POSITIVE_INFINITY;
            }
            else
            {
                pathpoint2 = this.a[j];
                f2 = pathpoint2.g;
            }

            if (f1 < f2)
            {
                if (f1 >= f)
                {
                    break;
                }

                this.a[p_b_1_] = pathpoint1;
                pathpoint1.d = p_b_1_;
                p_b_1_ = i;
            }
            else
            {
                if (f2 >= f)
                {
                    break;
                }

                this.a[p_b_1_] = pathpoint2;
                pathpoint2.d = p_b_1_;
                p_b_1_ = j;
            }
        }

        this.a[p_b_1_] = pathpoint;
        pathpoint.d = p_b_1_;
    }

    public boolean e()
    {
        return this.b == 0;
    }
}

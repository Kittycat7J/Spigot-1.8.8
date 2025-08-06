package net.minecraft.server.v1_8_R3;

public class Pathfinder
{
    private Path a = new Path();
    private PathPoint[] b = new PathPoint[32];
    private PathfinderAbstract c;

    public Pathfinder(PathfinderAbstract p_i834_1_)
    {
        this.c = p_i834_1_;
    }

    public PathEntity a(IBlockAccess p_a_1_, Entity p_a_2_, Entity p_a_3_, float p_a_4_)
    {
        return this.a(p_a_1_, p_a_2_, p_a_3_.locX, p_a_3_.getBoundingBox().b, p_a_3_.locZ, p_a_4_);
    }

    public PathEntity a(IBlockAccess p_a_1_, Entity p_a_2_, BlockPosition p_a_3_, float p_a_4_)
    {
        return this.a(p_a_1_, p_a_2_, (double)((float)p_a_3_.getX() + 0.5F), (double)((float)p_a_3_.getY() + 0.5F), (double)((float)p_a_3_.getZ() + 0.5F), p_a_4_);
    }

    private PathEntity a(IBlockAccess p_a_1_, Entity p_a_2_, double p_a_3_, double p_a_5_, double p_a_7_, float p_a_9_)
    {
        this.a.a();
        this.c.a(p_a_1_, p_a_2_);
        PathPoint pathpoint = this.c.a(p_a_2_);
        PathPoint pathpoint1 = this.c.a(p_a_2_, p_a_3_, p_a_5_, p_a_7_);
        PathEntity pathentity = this.a(p_a_2_, pathpoint, pathpoint1, p_a_9_);
        this.c.a();
        return pathentity;
    }

    private PathEntity a(Entity p_a_1_, PathPoint p_a_2_, PathPoint p_a_3_, float p_a_4_)
    {
        p_a_2_.e = 0.0F;
        p_a_2_.f = p_a_2_.b(p_a_3_);
        p_a_2_.g = p_a_2_.f;
        this.a.a();
        this.a.a(p_a_2_);
        PathPoint pathpoint = p_a_2_;

        while (!this.a.e())
        {
            PathPoint pathpoint1 = this.a.c();

            if (pathpoint1.equals(p_a_3_))
            {
                return this.a(p_a_2_, p_a_3_);
            }

            if (pathpoint1.b(p_a_3_) < pathpoint.b(p_a_3_))
            {
                pathpoint = pathpoint1;
            }

            pathpoint1.i = true;
            int i = this.c.a(this.b, p_a_1_, pathpoint1, p_a_3_, p_a_4_);

            for (int j = 0; j < i; ++j)
            {
                PathPoint pathpoint2 = this.b[j];
                float f = pathpoint1.e + pathpoint1.b(pathpoint2);

                if (f < p_a_4_ * 2.0F && (!pathpoint2.a() || f < pathpoint2.e))
                {
                    pathpoint2.h = pathpoint1;
                    pathpoint2.e = f;
                    pathpoint2.f = pathpoint2.b(p_a_3_);

                    if (pathpoint2.a())
                    {
                        this.a.a(pathpoint2, pathpoint2.e + pathpoint2.f);
                    }
                    else
                    {
                        pathpoint2.g = pathpoint2.e + pathpoint2.f;
                        this.a.a(pathpoint2);
                    }
                }
            }
        }

        if (pathpoint == p_a_2_)
        {
            return null;
        }
        else
        {
            return this.a(p_a_2_, pathpoint);
        }
    }

    private PathEntity a(PathPoint p_a_1_, PathPoint p_a_2_)
    {
        int i = 1;

        for (PathPoint pathpoint = p_a_2_; pathpoint.h != null; pathpoint = pathpoint.h)
        {
            ++i;
        }

        PathPoint[] apathpoint = new PathPoint[i];
        PathPoint pathpoint1 = p_a_2_;
        --i;

        for (apathpoint[i] = p_a_2_; pathpoint1.h != null; apathpoint[i] = pathpoint1)
        {
            pathpoint1 = pathpoint1.h;
            --i;
        }

        return new PathEntity(apathpoint);
    }
}

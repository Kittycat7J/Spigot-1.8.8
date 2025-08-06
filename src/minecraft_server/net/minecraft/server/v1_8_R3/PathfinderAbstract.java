package net.minecraft.server.v1_8_R3;

public abstract class PathfinderAbstract
{
    protected IBlockAccess a;
    protected IntHashMap<PathPoint> b = new IntHashMap();
    protected int c;
    protected int d;
    protected int e;

    public void a(IBlockAccess p_a_1_, Entity p_a_2_)
    {
        this.a = p_a_1_;
        this.b.c();
        this.c = MathHelper.d(p_a_2_.width + 1.0F);
        this.d = MathHelper.d(p_a_2_.length + 1.0F);
        this.e = MathHelper.d(p_a_2_.width + 1.0F);
    }

    public void a()
    {
    }

    protected PathPoint a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        int i = PathPoint.a(p_a_1_, p_a_2_, p_a_3_);
        PathPoint pathpoint = (PathPoint)this.b.get(i);

        if (pathpoint == null)
        {
            pathpoint = new PathPoint(p_a_1_, p_a_2_, p_a_3_);
            this.b.a(i, pathpoint);
        }

        return pathpoint;
    }

    public abstract PathPoint a(Entity var1);

    public abstract PathPoint a(Entity var1, double var2, double var4, double var6);

    public abstract int a(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5);
}

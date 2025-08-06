package net.minecraft.server.v1_8_R3;

public class NavigationGuardian extends NavigationAbstract
{
    public NavigationGuardian(EntityInsentient p_i1203_1_, World p_i1203_2_)
    {
        super(p_i1203_1_, p_i1203_2_);
    }

    protected Pathfinder a()
    {
        return new Pathfinder(new PathfinderWater());
    }

    protected boolean b()
    {
        return this.o();
    }

    protected Vec3D c()
    {
        return new Vec3D(this.b.locX, this.b.locY + (double)this.b.length * 0.5D, this.b.locZ);
    }

    protected void l()
    {
        Vec3D vec3d = this.c();
        float f = this.b.width * this.b.width;
        byte b0 = 6;

        if (vec3d.distanceSquared(this.d.a(this.b, this.d.e())) < (double)f)
        {
            this.d.a();
        }

        for (int i = Math.min(this.d.e() + b0, this.d.d() - 1); i > this.d.e(); --i)
        {
            Vec3D vec3d1 = this.d.a(this.b, i);

            if (vec3d1.distanceSquared(vec3d) <= 36.0D && this.a(vec3d, vec3d1, 0, 0, 0))
            {
                this.d.c(i);
                break;
            }
        }

        this.a(vec3d);
    }

    protected void d()
    {
        super.d();
    }

    protected boolean a(Vec3D p_a_1_, Vec3D p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_)
    {
        MovingObjectPosition movingobjectposition = this.c.rayTrace(p_a_1_, new Vec3D(p_a_2_.a, p_a_2_.b + (double)this.b.length * 0.5D, p_a_2_.c), false, true, false);
        return movingobjectposition == null || movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.MISS;
    }
}

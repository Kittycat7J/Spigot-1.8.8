package net.minecraft.server.v1_8_R3;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget
{
    private boolean a;
    private int b;
    private final Class[] c;

    public PathfinderGoalHurtByTarget(EntityCreature p_i191_1_, boolean p_i191_2_, Class... p_i191_3_)
    {
        super(p_i191_1_, false);
        this.a = p_i191_2_;
        this.c = p_i191_3_;
        this.a(1);
    }

    public boolean a()
    {
        int i = this.e.be();
        return i != this.b && this.a(this.e.getLastDamager(), false);
    }

    public void c()
    {
        this.e.setGoalTarget(this.e.getLastDamager(), TargetReason.TARGET_ATTACKED_ENTITY, true);
        this.b = this.e.be();

        if (this.a)
        {
            double d0 = this.f();

            for (EntityCreature entitycreature : this.e.world.a(this.e.getClass(), (new AxisAlignedBB(this.e.locX, this.e.locY, this.e.locZ, this.e.locX + 1.0D, this.e.locY + 1.0D, this.e.locZ + 1.0D)).grow(d0, 10.0D, d0)))
            {
                if (this.e != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.e.getLastDamager()))
                {
                    boolean flag = false;

                    for (Class oclass : this.c)
                    {
                        if (entitycreature.getClass() == oclass)
                        {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag)
                    {
                        this.a(entitycreature, this.e.getLastDamager());
                    }
                }
            }
        }

        super.c();
    }

    protected void a(EntityCreature p_a_1_, EntityLiving p_a_2_)
    {
        p_a_1_.setGoalTarget(p_a_2_, TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
    }
}

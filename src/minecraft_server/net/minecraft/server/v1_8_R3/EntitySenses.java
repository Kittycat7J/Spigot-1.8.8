package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;

public class EntitySenses
{
    EntityInsentient a;
    List<Entity> b = Lists.<Entity>newArrayList();
    List<Entity> c = Lists.<Entity>newArrayList();

    public EntitySenses(EntityInsentient p_i1204_1_)
    {
        this.a = p_i1204_1_;
    }

    public void a()
    {
        this.b.clear();
        this.c.clear();
    }

    public boolean a(Entity p_a_1_)
    {
        if (this.b.contains(p_a_1_))
        {
            return true;
        }
        else if (this.c.contains(p_a_1_))
        {
            return false;
        }
        else
        {
            this.a.world.methodProfiler.a("canSee");
            boolean flag = this.a.hasLineOfSight(p_a_1_);
            this.a.world.methodProfiler.b();

            if (flag)
            {
                this.b.add(p_a_1_);
            }
            else
            {
                this.c.add(p_a_1_);
            }

            return flag;
        }
    }
}

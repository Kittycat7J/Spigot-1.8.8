package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList.Itr;

public class PathfinderGoalSelector
{
    private static final Logger a = LogManager.getLogger();
    private List<PathfinderGoalSelector.PathfinderGoalSelectorItem> b = new UnsafeList();
    private List<PathfinderGoalSelector.PathfinderGoalSelectorItem> c = new UnsafeList();
    private final MethodProfiler d;
    private int e;
    private int f = 3;

    public PathfinderGoalSelector(MethodProfiler p_i466_1_)
    {
        this.d = p_i466_1_;
    }

    public void a(int p_a_1_, PathfinderGoal p_a_2_)
    {
        this.b.add(new PathfinderGoalSelector.PathfinderGoalSelectorItem(p_a_1_, p_a_2_));
    }

    public void a(PathfinderGoal p_a_1_)
    {
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext())
        {
            PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem = (PathfinderGoalSelector.PathfinderGoalSelectorItem)iterator.next();
            PathfinderGoal pathfindergoal = pathfindergoalselector$pathfindergoalselectoritem.a;

            if (pathfindergoal == p_a_1_)
            {
                if (this.c.contains(pathfindergoalselector$pathfindergoalselectoritem))
                {
                    pathfindergoal.d();
                    this.c.remove(pathfindergoalselector$pathfindergoalselectoritem);
                }

                iterator.remove();
            }
        }
    }

    public void a()
    {
        this.d.a("goalSetup");

        if (this.e++ % this.f == 0)
        {
            Iterator iterator = this.b.iterator();
            label139:

            while (true)
            {
                PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem;

                while (true)
                {
                    if (!iterator.hasNext())
                    {
                        break label139;
                    }

                    pathfindergoalselector$pathfindergoalselectoritem = (PathfinderGoalSelector.PathfinderGoalSelectorItem)iterator.next();
                    boolean flag = this.c.contains(pathfindergoalselector$pathfindergoalselectoritem);

                    if (!flag)
                    {
                        break;
                    }

                    if (!this.b(pathfindergoalselector$pathfindergoalselectoritem) || !this.a(pathfindergoalselector$pathfindergoalselectoritem))
                    {
                        pathfindergoalselector$pathfindergoalselectoritem.a.d();
                        this.c.remove(pathfindergoalselector$pathfindergoalselectoritem);
                        break;
                    }
                }

                if (this.b(pathfindergoalselector$pathfindergoalselectoritem) && pathfindergoalselector$pathfindergoalselectoritem.a.a())
                {
                    pathfindergoalselector$pathfindergoalselectoritem.a.c();
                    this.c.add(pathfindergoalselector$pathfindergoalselectoritem);
                }
            }
        }
        else
        {
            Iterator iterator1 = this.c.iterator();

            while (iterator1.hasNext())
            {
                PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem1 = (PathfinderGoalSelector.PathfinderGoalSelectorItem)iterator1.next();

                if (!this.a(pathfindergoalselector$pathfindergoalselectoritem1))
                {
                    pathfindergoalselector$pathfindergoalselectoritem1.a.d();
                    iterator1.remove();
                }
            }
        }

        this.d.b();
        this.d.a("goalTick");

        for (PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem2 : this.c)
        {
            pathfindergoalselector$pathfindergoalselectoritem2.a.e();
        }

        this.d.b();
    }

    private boolean a(PathfinderGoalSelector.PathfinderGoalSelectorItem p_a_1_)
    {
        boolean flag = p_a_1_.a.b();
        return flag;
    }

    private boolean b(PathfinderGoalSelector.PathfinderGoalSelectorItem p_b_1_)
    {
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext())
        {
            PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem = (PathfinderGoalSelector.PathfinderGoalSelectorItem)iterator.next();

            if (pathfindergoalselector$pathfindergoalselectoritem != p_b_1_)
            {
                if (p_b_1_.b >= pathfindergoalselector$pathfindergoalselectoritem.b)
                {
                    if (!this.a(p_b_1_, pathfindergoalselector$pathfindergoalselectoritem) && this.c.contains(pathfindergoalselector$pathfindergoalselectoritem))
                    {
                        ((Itr)iterator).valid = false;
                        return false;
                    }
                }
                else if (!pathfindergoalselector$pathfindergoalselectoritem.a.i() && this.c.contains(pathfindergoalselector$pathfindergoalselectoritem))
                {
                    ((Itr)iterator).valid = false;
                    return false;
                }
            }
        }

        return true;
    }

    private boolean a(PathfinderGoalSelector.PathfinderGoalSelectorItem p_a_1_, PathfinderGoalSelector.PathfinderGoalSelectorItem p_a_2_)
    {
        return (p_a_1_.a.j() & p_a_2_.a.j()) == 0;
    }

    class PathfinderGoalSelectorItem
    {
        public PathfinderGoal a;
        public int b;

        public PathfinderGoalSelectorItem(int p_i342_2_, PathfinderGoal p_i342_3_)
        {
            this.b = p_i342_2_;
            this.a = p_i342_3_;
        }
    }
}

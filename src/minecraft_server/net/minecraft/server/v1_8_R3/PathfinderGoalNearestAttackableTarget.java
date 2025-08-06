package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalTarget
{
    protected final Class<T> a;
    private final int g;
    protected final PathfinderGoalNearestAttackableTarget.DistanceComparator b;
    protected Predicate <? super T > c;
    protected EntityLiving d;

    public PathfinderGoalNearestAttackableTarget(EntityCreature p_i13_1_, Class<T> p_i13_2_, boolean p_i13_3_)
    {
        this(p_i13_1_, p_i13_2_, p_i13_3_, false);
    }

    public PathfinderGoalNearestAttackableTarget(EntityCreature p_i14_1_, Class<T> p_i14_2_, boolean p_i14_3_, boolean p_i14_4_)
    {
        this(p_i14_1_, p_i14_2_, 10, p_i14_3_, p_i14_4_, (Predicate <? super T >)null);
    }

    public PathfinderGoalNearestAttackableTarget(EntityCreature p_i15_1_, Class<T> p_i15_2_, int p_i15_3_, boolean p_i15_4_, boolean p_i15_5_, final Predicate <? super T > p_i15_6_)
    {
        super(p_i15_1_, p_i15_4_, p_i15_5_);
        this.a = p_i15_2_;
        this.g = p_i15_3_;
        this.b = new PathfinderGoalNearestAttackableTarget.DistanceComparator(p_i15_1_);
        this.a(1);
        this.c = new Predicate()
        {
            public boolean a(T p_a_1_)
            {
                if (p_i15_6_ != null && !p_i15_6_.apply(p_a_1_))
                {
                    return false;
                }
                else
                {
                    if (p_a_1_ instanceof EntityHuman)
                    {
                        double d0 = PathfinderGoalNearestAttackableTarget.this.f();

                        if (p_a_1_.isSneaking())
                        {
                            d0 *= 0.800000011920929D;
                        }

                        if (p_a_1_.isInvisible())
                        {
                            float f = ((EntityHuman)p_a_1_).bY();

                            if (f < 0.1F)
                            {
                                f = 0.1F;
                            }

                            d0 *= (double)(0.7F * f);
                        }

                        if ((double)p_a_1_.g(PathfinderGoalNearestAttackableTarget.this.e) > d0)
                        {
                            return false;
                        }
                    }

                    return PathfinderGoalNearestAttackableTarget.this.a(p_a_1_, false);
                }
            }
            public boolean apply(Object p_apply_1_)
            {
                return this.a((EntityLiving)p_apply_1_);
            }
        };
    }

    public boolean a()
    {
        if (this.g > 0 && this.e.bc().nextInt(this.g) != 0)
        {
            return false;
        }
        else
        {
            double d0 = this.f();
            List list = this.e.world.a(this.a, this.e.getBoundingBox().grow(d0, 4.0D, d0), Predicates.and(this.c, IEntitySelector.d));
            Collections.sort(list, this.b);

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                this.d = (EntityLiving)list.get(0);
                return true;
            }
        }
    }

    public void c()
    {
        this.e.setGoalTarget(this.d, this.d instanceof EntityPlayer ? TargetReason.CLOSEST_PLAYER : TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }

    public static class DistanceComparator implements Comparator<Entity>
    {
        private final Entity a;

        public DistanceComparator(Entity p_i157_1_)
        {
            this.a = p_i157_1_;
        }

        public int a(Entity p_a_1_, Entity p_a_2_)
        {
            double d0 = this.a.h(p_a_1_);
            double d1 = this.a.h(p_a_2_);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_)
        {
            return this.a(p_compare_1_, p_compare_2_);
        }
    }
}

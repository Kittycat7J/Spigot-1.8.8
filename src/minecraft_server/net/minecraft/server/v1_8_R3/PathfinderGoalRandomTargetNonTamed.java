package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;

public class PathfinderGoalRandomTargetNonTamed<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget
{
    private EntityTameableAnimal g;

    public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal p_i1197_1_, Class<T> p_i1197_2_, boolean p_i1197_3_, Predicate <? super T > p_i1197_4_)
    {
        super(p_i1197_1_, p_i1197_2_, 10, p_i1197_3_, false, p_i1197_4_);
        this.g = p_i1197_1_;
    }

    public boolean a()
    {
        return !this.g.isTamed() && super.a();
    }
}

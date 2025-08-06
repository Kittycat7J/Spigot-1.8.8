package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.List;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalTarget;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalNearestAttackableTargetInsentient extends PathfinderGoal {
   private static final Logger a = LogManager.getLogger();
   private EntityInsentient b;
   private final Predicate<EntityLiving> c;
   private final PathfinderGoalNearestAttackableTarget.DistanceComparator d;
   private EntityLiving e;
   private Class<? extends EntityLiving> f;

   public PathfinderGoalNearestAttackableTargetInsentient(EntityInsentient p_i6_1_, Class<? extends EntityLiving> p_i6_2_) {
      this.b = p_i6_1_;
      this.f = p_i6_2_;
      if(p_i6_1_ instanceof EntityCreature) {
         a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.c = new Predicate() {
         public boolean a(EntityLiving p_a_1_) {
            double d0 = PathfinderGoalNearestAttackableTargetInsentient.this.f();
            if(p_a_1_.isSneaking()) {
               d0 *= 0.800000011920929D;
            }

            return p_a_1_.isInvisible()?false:((double)p_a_1_.g(PathfinderGoalNearestAttackableTargetInsentient.this.b) > d0?false:PathfinderGoalTarget.a(PathfinderGoalNearestAttackableTargetInsentient.this.b, p_a_1_, false, true));
         }

         public boolean apply(Object p_apply_1_) {
            return this.a((EntityLiving)p_apply_1_);
         }
      };
      this.d = new PathfinderGoalNearestAttackableTarget.DistanceComparator(p_i6_1_);
   }

   public boolean a() {
      double d0 = this.f();
      List list = this.b.world.a(this.f, this.b.getBoundingBox().grow(d0, 4.0D, d0), this.c);
      Collections.sort(list, this.d);
      if(list.isEmpty()) {
         return false;
      } else {
         this.e = (EntityLiving)list.get(0);
         return true;
      }
   }

   public boolean b() {
      EntityLiving entityliving = this.b.getGoalTarget();
      if(entityliving == null) {
         return false;
      } else if(!entityliving.isAlive()) {
         return false;
      } else {
         double d0 = this.f();
         return this.b.h(entityliving) > d0 * d0?false:!(entityliving instanceof EntityPlayer) || !((EntityPlayer)entityliving).playerInteractManager.isCreative();
      }
   }

   public void c() {
      this.b.setGoalTarget(this.e, TargetReason.CLOSEST_ENTITY, true);
      super.c();
   }

   public void d() {
      this.b.setGoalTarget((EntityLiving)null);
      super.c();
   }

   protected double f() {
      AttributeInstance attributeinstance = this.b.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
      return attributeinstance == null?16.0D:attributeinstance.getValue();
   }
}

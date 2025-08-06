package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.RandomPositionGenerator;
import net.minecraft.server.v1_8_R3.Vec3D;

public class PathfinderGoalAvoidTarget<T extends Entity> extends PathfinderGoal {
   private final Predicate<Entity> c;
   protected EntityCreature a;
   private double d;
   private double e;
   protected T b;
   private float f;
   private PathEntity g;
   private NavigationAbstract h;
   private Class<T> i;
   private Predicate<? super T> j;

   public PathfinderGoalAvoidTarget(EntityCreature p_i1159_1_, Class<T> p_i1159_2_, float p_i1159_3_, double p_i1159_4_, double p_i1159_6_) {
      this(p_i1159_1_, p_i1159_2_, Predicates.<T>alwaysTrue(), p_i1159_3_, p_i1159_4_, p_i1159_6_);
   }

   public PathfinderGoalAvoidTarget(EntityCreature p_i1160_1_, Class<T> p_i1160_2_, Predicate<? super T> p_i1160_3_, float p_i1160_4_, double p_i1160_5_, double p_i1160_7_) {
      this.c = new Predicate<Entity>() {
         public boolean a(Entity p_a_1_) {
            return p_a_1_.isAlive() && PathfinderGoalAvoidTarget.this.a.getEntitySenses().a(p_a_1_);
         }
      };
      this.a = p_i1160_1_;
      this.i = p_i1160_2_;
      this.j = p_i1160_3_;
      this.f = p_i1160_4_;
      this.d = p_i1160_5_;
      this.e = p_i1160_7_;
      this.h = p_i1160_1_.getNavigation();
      this.a(1);
   }

   public boolean a() {
      List list = this.a.world.a(this.i, this.a.getBoundingBox().grow((double)this.f, 3.0D, (double)this.f), Predicates.and(new Predicate[]{IEntitySelector.d, this.c, this.j}));
      if(list.isEmpty()) {
         return false;
      } else {
         this.b = (Entity)list.get(0);
         Vec3D vec3d = RandomPositionGenerator.b(this.a, 16, 7, new Vec3D(this.b.locX, this.b.locY, this.b.locZ));
         if(vec3d == null) {
            return false;
         } else if(this.b.e(vec3d.a, vec3d.b, vec3d.c) < this.b.h(this.a)) {
            return false;
         } else {
            this.g = this.h.a(vec3d.a, vec3d.b, vec3d.c);
            return this.g == null?false:this.g.b(vec3d);
         }
      }
   }

   public boolean b() {
      return !this.h.m();
   }

   public void c() {
      this.h.a(this.g, this.d);
   }

   public void d() {
      this.b = null;
   }

   public void e() {
      if(this.a.h(this.b) < 49.0D) {
         this.a.getNavigation().a(this.e);
      } else {
         this.a.getNavigation().a(this.d);
      }

   }
}

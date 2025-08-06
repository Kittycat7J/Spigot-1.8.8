package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.RandomPositionGenerator;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.Village;
import net.minecraft.server.v1_8_R3.VillageDoor;

public class PathfinderGoalMoveThroughVillage extends PathfinderGoal {
   private EntityCreature a;
   private double b;
   private PathEntity c;
   private VillageDoor d;
   private boolean e;
   private List<VillageDoor> f = Lists.<VillageDoor>newArrayList();

   public PathfinderGoalMoveThroughVillage(EntityCreature p_i1177_1_, double p_i1177_2_, boolean p_i1177_4_) {
      this.a = p_i1177_1_;
      this.b = p_i1177_2_;
      this.e = p_i1177_4_;
      this.a(1);
      if(!(p_i1177_1_.getNavigation() instanceof Navigation)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   public boolean a() {
      this.f();
      if(this.e && this.a.world.w()) {
         return false;
      } else {
         Village village = this.a.world.ae().getClosestVillage(new BlockPosition(this.a), 0);
         if(village == null) {
            return false;
         } else {
            this.d = this.a(village);
            if(this.d == null) {
               return false;
            } else {
               Navigation navigation = (Navigation)this.a.getNavigation();
               boolean flag = navigation.g();
               navigation.b(false);
               this.c = navigation.a(this.d.d());
               navigation.b(flag);
               if(this.c != null) {
                  return true;
               } else {
                  Vec3D vec3d = RandomPositionGenerator.a(this.a, 10, 7, new Vec3D((double)this.d.d().getX(), (double)this.d.d().getY(), (double)this.d.d().getZ()));
                  if(vec3d == null) {
                     return false;
                  } else {
                     navigation.b(false);
                     this.c = this.a.getNavigation().a(vec3d.a, vec3d.b, vec3d.c);
                     navigation.b(flag);
                     return this.c != null;
                  }
               }
            }
         }
      }
   }

   public boolean b() {
      if(this.a.getNavigation().m()) {
         return false;
      } else {
         float fx = this.a.width + 4.0F;
         return this.a.b(this.d.d()) > (double)(fx * fx);
      }
   }

   public void c() {
      this.a.getNavigation().a(this.c, this.b);
   }

   public void d() {
      if(this.a.getNavigation().m() || this.a.b(this.d.d()) < 16.0D) {
         this.f.add(this.d);
      }

   }

   private VillageDoor a(Village p_a_1_) {
      VillageDoor villagedoor = null;
      int i = Integer.MAX_VALUE;

      for(VillageDoor villagedoor1 : p_a_1_.f()) {
         int j = villagedoor1.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
         if(j < i && !this.a(villagedoor1)) {
            villagedoor = villagedoor1;
            i = j;
         }
      }

      return villagedoor;
   }

   private boolean a(VillageDoor p_a_1_) {
      for(VillageDoor villagedoor : this.f) {
         if(p_a_1_.d().equals(villagedoor.d())) {
            return true;
         }
      }

      return false;
   }

   private void f() {
      if(this.f.size() > 15) {
         this.f.remove(0);
      }

   }
}

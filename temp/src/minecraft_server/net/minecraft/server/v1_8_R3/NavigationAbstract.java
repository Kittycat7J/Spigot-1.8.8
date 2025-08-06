package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChunkCache;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.Pathfinder;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public abstract class NavigationAbstract {
   protected EntityInsentient b;
   protected World c;
   protected PathEntity d;
   protected double e;
   private final AttributeInstance a;
   private int f;
   private int g;
   private Vec3D h = new Vec3D(0.0D, 0.0D, 0.0D);
   private float i = 1.0F;
   private final Pathfinder j;

   public NavigationAbstract(EntityInsentient p_i1201_1_, World p_i1201_2_) {
      this.b = p_i1201_1_;
      this.c = p_i1201_2_;
      this.a = p_i1201_1_.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
      this.j = this.a();
   }

   protected abstract Pathfinder a();

   public void a(double p_a_1_) {
      this.e = p_a_1_;
   }

   public float i() {
      return (float)this.a.getValue();
   }

   public final PathEntity a(double p_a_1_, double p_a_3_, double p_a_5_) {
      return this.a(new BlockPosition(MathHelper.floor(p_a_1_), (int)p_a_3_, MathHelper.floor(p_a_5_)));
   }

   public PathEntity a(BlockPosition p_a_1_) {
      if(!this.b()) {
         return null;
      } else {
         float fx = this.i();
         this.c.methodProfiler.a("pathfind");
         BlockPosition blockposition = new BlockPosition(this.b);
         int ix = (int)(fx + 8.0F);
         ChunkCache chunkcache = new ChunkCache(this.c, blockposition.a(-ix, -ix, -ix), blockposition.a(ix, ix, ix), 0);
         PathEntity pathentity = this.j.a((IBlockAccess)chunkcache, (Entity)this.b, (BlockPosition)p_a_1_, fx);
         this.c.methodProfiler.b();
         return pathentity;
      }
   }

   public boolean a(double p_a_1_, double p_a_3_, double p_a_5_, double p_a_7_) {
      PathEntity pathentity = this.a((double)MathHelper.floor(p_a_1_), (double)((int)p_a_3_), (double)MathHelper.floor(p_a_5_));
      return this.a(pathentity, p_a_7_);
   }

   public void a(float p_a_1_) {
      this.i = p_a_1_;
   }

   public PathEntity a(Entity p_a_1_) {
      if(!this.b()) {
         return null;
      } else {
         float fx = this.i();
         this.c.methodProfiler.a("pathfind");
         BlockPosition blockposition = (new BlockPosition(this.b)).up();
         int ix = (int)(fx + 16.0F);
         ChunkCache chunkcache = new ChunkCache(this.c, blockposition.a(-ix, -ix, -ix), blockposition.a(ix, ix, ix), 0);
         PathEntity pathentity = this.j.a((IBlockAccess)chunkcache, (Entity)this.b, (Entity)p_a_1_, fx);
         this.c.methodProfiler.b();
         return pathentity;
      }
   }

   public boolean a(Entity p_a_1_, double p_a_2_) {
      PathEntity pathentity = this.a(p_a_1_);
      return pathentity != null?this.a(pathentity, p_a_2_):false;
   }

   public boolean a(PathEntity p_a_1_, double p_a_2_) {
      if(p_a_1_ == null) {
         this.d = null;
         return false;
      } else {
         if(!p_a_1_.a(this.d)) {
            this.d = p_a_1_;
         }

         this.d();
         if(this.d.d() == 0) {
            return false;
         } else {
            this.e = p_a_2_;
            Vec3D vec3d = this.c();
            this.g = this.f;
            this.h = vec3d;
            return true;
         }
      }
   }

   public PathEntity j() {
      return this.d;
   }

   public void k() {
      ++this.f;
      if(!this.m()) {
         if(this.b()) {
            this.l();
         } else if(this.d != null && this.d.e() < this.d.d()) {
            Vec3D vec3d = this.c();
            Vec3D vec3d1 = this.d.a(this.b, this.d.e());
            if(vec3d.b > vec3d1.b && !this.b.onGround && MathHelper.floor(vec3d.a) == MathHelper.floor(vec3d1.a) && MathHelper.floor(vec3d.c) == MathHelper.floor(vec3d1.c)) {
               this.d.c(this.d.e() + 1);
            }
         }

         if(!this.m()) {
            Vec3D vec3d2 = this.d.a((Entity)this.b);
            if(vec3d2 != null) {
               AxisAlignedBB axisalignedbb1 = (new AxisAlignedBB(vec3d2.a, vec3d2.b, vec3d2.c, vec3d2.a, vec3d2.b, vec3d2.c)).grow(0.5D, 0.5D, 0.5D);
               List list = this.c.getCubes(this.b, axisalignedbb1.a(0.0D, -1.0D, 0.0D));
               double d0 = -1.0D;
               axisalignedbb1 = axisalignedbb1.c(0.0D, 1.0D, 0.0D);

               for(AxisAlignedBB axisalignedbb : list) {
                  d0 = axisalignedbb.b(axisalignedbb1, d0);
               }

               this.b.getControllerMove().a(vec3d2.a, vec3d2.b + d0, vec3d2.c, this.e);
            }
         }
      }
   }

   protected void l() {
      Vec3D vec3d = this.c();
      int ix = this.d.d();

      for(int jx = this.d.e(); jx < this.d.d(); ++jx) {
         if(this.d.a(jx).b != (int)vec3d.b) {
            ix = jx;
            break;
         }
      }

      float fx = this.b.width * this.b.width * this.i;

      for(int k = this.d.e(); k < ix; ++k) {
         Vec3D vec3d1 = this.d.a(this.b, k);
         if(vec3d.distanceSquared(vec3d1) < (double)fx) {
            this.d.c(k + 1);
         }
      }

      int j1 = MathHelper.f(this.b.width);
      int k1 = (int)this.b.length + 1;
      int l = j1;

      for(int i1 = ix - 1; i1 >= this.d.e(); --i1) {
         if(this.a(vec3d, this.d.a(this.b, i1), j1, k1, l)) {
            this.d.c(i1);
            break;
         }
      }

      this.a(vec3d);
   }

   protected void a(Vec3D p_a_1_) {
      if(this.f - this.g > 100) {
         if(p_a_1_.distanceSquared(this.h) < 2.25D) {
            this.n();
         }

         this.g = this.f;
         this.h = p_a_1_;
      }

   }

   public boolean m() {
      return this.d == null || this.d.b();
   }

   public void n() {
      this.d = null;
   }

   protected abstract Vec3D c();

   protected abstract boolean b();

   protected boolean o() {
      return this.b.V() || this.b.ab();
   }

   protected void d() {
   }

   protected abstract boolean a(Vec3D var1, Vec3D var2, int var3, int var4, int var5);
}

package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockLeaves;
import net.minecraft.server.v1_8_R3.BlockLogAbstract;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public class WorldGenBigTree extends WorldGenTreeAbstract {
   private Random k;
   private World l;
   private BlockPosition m = BlockPosition.ZERO;
   int a;
   int b;
   double c = 0.618D;
   double d = 0.381D;
   double e = 1.0D;
   double f = 1.0D;
   int g = 1;
   int h = 12;
   int i = 4;
   List<WorldGenBigTree.Position> j;

   public WorldGenBigTree(boolean p_i690_1_) {
      super(p_i690_1_);
   }

   void a() {
      this.b = (int)((double)this.a * this.c);
      if(this.b >= this.a) {
         this.b = this.a - 1;
      }

      int ix = (int)(1.382D + Math.pow(this.f * (double)this.a / 13.0D, 2.0D));
      if(ix < 1) {
         ix = 1;
      }

      int jx = this.m.getY() + this.b;
      int kx = this.a - this.i;
      this.j = Lists.<WorldGenBigTree.Position>newArrayList();
      this.j.add(new WorldGenBigTree.Position(this.m.up(kx), jx));

      for(; kx >= 0; --kx) {
         float fx = this.a(kx);
         if(fx >= 0.0F) {
            for(int lx = 0; lx < ix; ++lx) {
               double d0 = this.e * (double)fx * ((double)this.k.nextFloat() + 0.328D);
               double d1 = (double)(this.k.nextFloat() * 2.0F) * 3.141592653589793D;
               double d2 = d0 * Math.sin(d1) + 0.5D;
               double d3 = d0 * Math.cos(d1) + 0.5D;
               BlockPosition blockposition = this.m.a(d2, (double)(kx - 1), d3);
               BlockPosition blockposition1 = blockposition.up(this.i);
               if(this.a(blockposition, blockposition1) == -1) {
                  int i1 = this.m.getX() - blockposition.getX();
                  int j1 = this.m.getZ() - blockposition.getZ();
                  double d4 = (double)blockposition.getY() - Math.sqrt((double)(i1 * i1 + j1 * j1)) * this.d;
                  int k1 = d4 > (double)jx?jx:(int)d4;
                  BlockPosition blockposition2 = new BlockPosition(this.m.getX(), k1, this.m.getZ());
                  if(this.a(blockposition2, blockposition) == -1) {
                     this.j.add(new WorldGenBigTree.Position(blockposition, blockposition2.getY()));
                  }
               }
            }
         }
      }

   }

   void a(BlockPosition p_a_1_, float p_a_2_, IBlockData p_a_3_) {
      int ix = (int)((double)p_a_2_ + 0.618D);

      for(int jx = -ix; jx <= ix; ++jx) {
         for(int kx = -ix; kx <= ix; ++kx) {
            if(Math.pow((double)Math.abs(jx) + 0.5D, 2.0D) + Math.pow((double)Math.abs(kx) + 0.5D, 2.0D) <= (double)(p_a_2_ * p_a_2_)) {
               BlockPosition blockposition = p_a_1_.a(jx, 0, kx);
               Material material = this.l.getType(blockposition).getBlock().getMaterial();
               if(material == Material.AIR || material == Material.LEAVES) {
                  this.a(this.l, blockposition, p_a_3_);
               }
            }
         }
      }

   }

   float a(int p_a_1_) {
      if((float)p_a_1_ < (float)this.a * 0.3F) {
         return -1.0F;
      } else {
         float fx = (float)this.a / 2.0F;
         float f1 = fx - (float)p_a_1_;
         float f2 = MathHelper.c(fx * fx - f1 * f1);
         if(f1 == 0.0F) {
            f2 = fx;
         } else if(Math.abs(f1) >= fx) {
            return 0.0F;
         }

         return f2 * 0.5F;
      }
   }

   float b(int p_b_1_) {
      return p_b_1_ >= 0 && p_b_1_ < this.i?(p_b_1_ != 0 && p_b_1_ != this.i - 1?3.0F:2.0F):-1.0F;
   }

   void a(BlockPosition p_a_1_) {
      for(int ix = 0; ix < this.i; ++ix) {
         this.a(p_a_1_.up(ix), this.b(ix), Blocks.LEAVES.getBlockData().set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
      }

   }

   void a(BlockPosition p_a_1_, BlockPosition p_a_2_, Block p_a_3_) {
      BlockPosition blockposition = p_a_2_.a(-p_a_1_.getX(), -p_a_1_.getY(), -p_a_1_.getZ());
      int ix = this.b(blockposition);
      float fx = (float)blockposition.getX() / (float)ix;
      float f1 = (float)blockposition.getY() / (float)ix;
      float f2 = (float)blockposition.getZ() / (float)ix;

      for(int jx = 0; jx <= ix; ++jx) {
         BlockPosition blockposition1 = p_a_1_.a((double)(0.5F + (float)jx * fx), (double)(0.5F + (float)jx * f1), (double)(0.5F + (float)jx * f2));
         BlockLogAbstract.EnumLogRotation blocklogabstract$enumlogrotation = this.b(p_a_1_, blockposition1);
         this.a(this.l, blockposition1, p_a_3_.getBlockData().set(BlockLogAbstract.AXIS, blocklogabstract$enumlogrotation));
      }

   }

   private int b(BlockPosition p_b_1_) {
      int ix = MathHelper.a(p_b_1_.getX());
      int jx = MathHelper.a(p_b_1_.getY());
      int kx = MathHelper.a(p_b_1_.getZ());
      return kx > ix && kx > jx?kx:(jx > ix?jx:ix);
   }

   private BlockLogAbstract.EnumLogRotation b(BlockPosition p_b_1_, BlockPosition p_b_2_) {
      BlockLogAbstract.EnumLogRotation blocklogabstract$enumlogrotation = BlockLogAbstract.EnumLogRotation.Y;
      int ix = Math.abs(p_b_2_.getX() - p_b_1_.getX());
      int jx = Math.abs(p_b_2_.getZ() - p_b_1_.getZ());
      int kx = Math.max(ix, jx);
      if(kx > 0) {
         if(ix == kx) {
            blocklogabstract$enumlogrotation = BlockLogAbstract.EnumLogRotation.X;
         } else if(jx == kx) {
            blocklogabstract$enumlogrotation = BlockLogAbstract.EnumLogRotation.Z;
         }
      }

      return blocklogabstract$enumlogrotation;
   }

   void b() {
      for(WorldGenBigTree.Position worldgenbigtree$position : this.j) {
         this.a(worldgenbigtree$position);
      }

   }

   boolean c(int p_c_1_) {
      return (double)p_c_1_ >= (double)this.a * 0.2D;
   }

   void c() {
      BlockPosition blockposition = this.m;
      BlockPosition blockposition1 = this.m.up(this.b);
      Block block = Blocks.LOG;
      this.a(blockposition, blockposition1, block);
      if(this.g == 2) {
         this.a(blockposition.east(), blockposition1.east(), block);
         this.a(blockposition.east().south(), blockposition1.east().south(), block);
         this.a(blockposition.south(), blockposition1.south(), block);
      }

   }

   void d() {
      for(WorldGenBigTree.Position worldgenbigtree$position : this.j) {
         int ix = worldgenbigtree$position.q();
         BlockPosition blockposition = new BlockPosition(this.m.getX(), ix, this.m.getZ());
         if(!blockposition.equals(worldgenbigtree$position) && this.c(ix - this.m.getY())) {
            this.a(blockposition, worldgenbigtree$position, Blocks.LOG);
         }
      }

   }

   int a(BlockPosition p_a_1_, BlockPosition p_a_2_) {
      BlockPosition blockposition = p_a_2_.a(-p_a_1_.getX(), -p_a_1_.getY(), -p_a_1_.getZ());
      int ix = this.b(blockposition);
      float fx = (float)blockposition.getX() / (float)ix;
      float f1 = (float)blockposition.getY() / (float)ix;
      float f2 = (float)blockposition.getZ() / (float)ix;
      if(ix == 0) {
         return -1;
      } else {
         for(int jx = 0; jx <= ix; ++jx) {
            BlockPosition blockposition1 = p_a_1_.a((double)(0.5F + (float)jx * fx), (double)(0.5F + (float)jx * f1), (double)(0.5F + (float)jx * f2));
            if(!this.a(this.l.getType(blockposition1).getBlock())) {
               return jx;
            }
         }

         return -1;
      }
   }

   public void e() {
      this.i = 5;
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      this.l = p_generate_1_;
      this.m = p_generate_3_;
      this.k = new Random(p_generate_2_.nextLong());
      if(this.a == 0) {
         this.a = 5 + this.k.nextInt(this.h);
      }

      if(!this.f()) {
         return false;
      } else {
         this.a();
         this.b();
         this.c();
         this.d();
         return true;
      }
   }

   private boolean f() {
      Block block = this.l.getType(this.m.down()).getBlock();
      if(block != Blocks.DIRT && block != Blocks.GRASS && block != Blocks.FARMLAND) {
         return false;
      } else {
         int ix = this.a(this.m, this.m.up(this.a - 1));
         if(ix == -1) {
            return true;
         } else if(ix < 6) {
            return false;
         } else {
            this.a = ix;
            return true;
         }
      }
   }

   static class Position extends BlockPosition {
      private final int c;

      public Position(BlockPosition p_i689_1_, int p_i689_2_) {
         super(p_i689_1_.getX(), p_i689_1_.getY(), p_i689_1_.getZ());
         this.c = p_i689_2_;
      }

      public int q() {
         return this.c;
      }
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.minecraft.server.v1_8_R3.BaseBlockPosition;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.ShapeDetectorBlock;
import net.minecraft.server.v1_8_R3.World;

public class ShapeDetector {
   private final Predicate<ShapeDetectorBlock>[][][] a;
   private final int b;
   private final int c;
   private final int d;

   public ShapeDetector(Predicate<ShapeDetectorBlock>[][][] p_i669_1_) {
      this.a = p_i669_1_;
      this.b = p_i669_1_.length;
      if(this.b > 0) {
         this.c = p_i669_1_[0].length;
         if(this.c > 0) {
            this.d = p_i669_1_[0][0].length;
         } else {
            this.d = 0;
         }
      } else {
         this.c = 0;
         this.d = 0;
      }

   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   private ShapeDetector.ShapeDetectorCollection a(BlockPosition p_a_1_, EnumDirection p_a_2_, EnumDirection p_a_3_, LoadingCache<BlockPosition, ShapeDetectorBlock> p_a_4_) {
      for(int i = 0; i < this.d; ++i) {
         for(int j = 0; j < this.c; ++j) {
            for(int k = 0; k < this.b; ++k) {
               if(!this.a[k][j][i].apply(p_a_4_.getUnchecked(a(p_a_1_, p_a_2_, p_a_3_, i, j, k)))) {
                  return null;
               }
            }
         }
      }

      return new ShapeDetector.ShapeDetectorCollection(p_a_1_, p_a_2_, p_a_3_, p_a_4_, this.d, this.c, this.b);
   }

   public ShapeDetector.ShapeDetectorCollection a(World p_a_1_, BlockPosition p_a_2_) {
      LoadingCache loadingcache = a(p_a_1_, false);
      int i = Math.max(Math.max(this.d, this.c), this.b);

      for(BlockPosition blockposition : BlockPosition.a(p_a_2_, p_a_2_.a(i - 1, i - 1, i - 1))) {
         for(EnumDirection enumdirection : EnumDirection.values()) {
            for(EnumDirection enumdirection1 : EnumDirection.values()) {
               if(enumdirection1 != enumdirection && enumdirection1 != enumdirection.opposite()) {
                  ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = this.a(blockposition, enumdirection, enumdirection1, loadingcache);
                  if(shapedetector$shapedetectorcollection != null) {
                     return shapedetector$shapedetectorcollection;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPosition, ShapeDetectorBlock> a(World p_a_0_, boolean p_a_1_) {
      return CacheBuilder.newBuilder().<BlockPosition, ShapeDetectorBlock>build(new ShapeDetector.BlockLoader(p_a_0_, p_a_1_));
   }

   protected static BlockPosition a(BlockPosition p_a_0_, EnumDirection p_a_1_, EnumDirection p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_) {
      if(p_a_1_ != p_a_2_ && p_a_1_ != p_a_2_.opposite()) {
         BaseBlockPosition baseblockposition = new BaseBlockPosition(p_a_1_.getAdjacentX(), p_a_1_.getAdjacentY(), p_a_1_.getAdjacentZ());
         BaseBlockPosition baseblockposition1 = new BaseBlockPosition(p_a_2_.getAdjacentX(), p_a_2_.getAdjacentY(), p_a_2_.getAdjacentZ());
         BaseBlockPosition baseblockposition2 = baseblockposition.d(baseblockposition1);
         return p_a_0_.a(baseblockposition1.getX() * -p_a_4_ + baseblockposition2.getX() * p_a_3_ + baseblockposition.getX() * p_a_5_, baseblockposition1.getY() * -p_a_4_ + baseblockposition2.getY() * p_a_3_ + baseblockposition.getY() * p_a_5_, baseblockposition1.getZ() * -p_a_4_ + baseblockposition2.getZ() * p_a_3_ + baseblockposition.getZ() * p_a_5_);
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class BlockLoader extends CacheLoader<BlockPosition, ShapeDetectorBlock> {
      private final World a;
      private final boolean b;

      public BlockLoader(World p_i667_1_, boolean p_i667_2_) {
         this.a = p_i667_1_;
         this.b = p_i667_2_;
      }

      public ShapeDetectorBlock a(BlockPosition p_a_1_) throws Exception {
         return new ShapeDetectorBlock(this.a, p_a_1_, this.b);
      }
   }

   public static class ShapeDetectorCollection {
      private final BlockPosition a;
      private final EnumDirection b;
      private final EnumDirection c;
      private final LoadingCache<BlockPosition, ShapeDetectorBlock> d;
      private final int e;
      private final int f;
      private final int g;

      public ShapeDetectorCollection(BlockPosition p_i668_1_, EnumDirection p_i668_2_, EnumDirection p_i668_3_, LoadingCache<BlockPosition, ShapeDetectorBlock> p_i668_4_, int p_i668_5_, int p_i668_6_, int p_i668_7_) {
         this.a = p_i668_1_;
         this.b = p_i668_2_;
         this.c = p_i668_3_;
         this.d = p_i668_4_;
         this.e = p_i668_5_;
         this.f = p_i668_6_;
         this.g = p_i668_7_;
      }

      public BlockPosition a() {
         return this.a;
      }

      public EnumDirection b() {
         return this.b;
      }

      public EnumDirection c() {
         return this.c;
      }

      public int d() {
         return this.e;
      }

      public int e() {
         return this.f;
      }

      public ShapeDetectorBlock a(int p_a_1_, int p_a_2_, int p_a_3_) {
         return (ShapeDetectorBlock)this.d.getUnchecked(ShapeDetector.a(this.a, this.b(), this.c(), p_a_1_, p_a_2_, p_a_3_));
      }

      public String toString() {
         return Objects.toStringHelper(this).add("up", this.c).add("forwards", this.b).add("frontTopLeft", this.a).toString();
      }
   }
}

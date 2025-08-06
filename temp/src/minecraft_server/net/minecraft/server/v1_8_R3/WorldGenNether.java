package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.StructureGenerator;
import net.minecraft.server.v1_8_R3.StructurePiece;
import net.minecraft.server.v1_8_R3.StructureStart;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenNetherPieces;

public class WorldGenNether extends StructureGenerator {
   private List<BiomeBase.BiomeMeta> d = Lists.<BiomeBase.BiomeMeta>newArrayList();

   public WorldGenNether() {
      this.d.add(new BiomeBase.BiomeMeta(EntityBlaze.class, 10, 2, 3));
      this.d.add(new BiomeBase.BiomeMeta(EntityPigZombie.class, 5, 4, 4));
      this.d.add(new BiomeBase.BiomeMeta(EntitySkeleton.class, 10, 4, 4));
      this.d.add(new BiomeBase.BiomeMeta(EntityMagmaCube.class, 3, 4, 4));
   }

   public String a() {
      return "Fortress";
   }

   public List<BiomeBase.BiomeMeta> b() {
      return this.d;
   }

   protected boolean a(int p_a_1_, int p_a_2_) {
      int i = p_a_1_ >> 4;
      int j = p_a_2_ >> 4;
      this.b.setSeed((long)(i ^ j << 4) ^ this.c.getSeed());
      this.b.nextInt();
      return this.b.nextInt(3) != 0?false:(p_a_1_ != (i << 4) + 4 + this.b.nextInt(8)?false:p_a_2_ == (j << 4) + 4 + this.b.nextInt(8));
   }

   protected StructureStart b(int p_b_1_, int p_b_2_) {
      return new WorldGenNether.WorldGenNetherStart(this.c, this.b, p_b_1_, p_b_2_);
   }

   public static class WorldGenNetherStart extends StructureStart {
      public WorldGenNetherStart() {
      }

      public WorldGenNetherStart(World p_i728_1_, Random p_i728_2_, int p_i728_3_, int p_i728_4_) {
         super(p_i728_3_, p_i728_4_);
         WorldGenNetherPieces.WorldGenNetherPiece15 worldgennetherpieces$worldgennetherpiece15 = new WorldGenNetherPieces.WorldGenNetherPiece15(p_i728_2_, (p_i728_3_ << 4) + 2, (p_i728_4_ << 4) + 2);
         this.a.add(worldgennetherpieces$worldgennetherpiece15);
         worldgennetherpieces$worldgennetherpiece15.a(worldgennetherpieces$worldgennetherpiece15, this.a, p_i728_2_);
         List list = worldgennetherpieces$worldgennetherpiece15.e;

         while(!list.isEmpty()) {
            int i = p_i728_2_.nextInt(list.size());
            StructurePiece structurepiece = (StructurePiece)list.remove(i);
            structurepiece.a((StructurePiece)worldgennetherpieces$worldgennetherpiece15, this.a, (Random)p_i728_2_);
         }

         this.c();
         this.a(p_i728_1_, p_i728_2_, 48, 70);
      }
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EnumSkyBlock;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class EmptyChunk extends Chunk {
   public EmptyChunk(World p_i678_1_, int p_i678_2_, int p_i678_3_) {
      super(p_i678_1_, p_i678_2_, p_i678_3_);
   }

   public boolean a(int p_a_1_, int p_a_2_) {
      return p_a_1_ == this.locX && p_a_2_ == this.locZ;
   }

   public int b(int p_b_1_, int p_b_2_) {
      return 0;
   }

   public void initLighting() {
   }

   public Block getType(BlockPosition p_getType_1_) {
      return Blocks.AIR;
   }

   public int b(BlockPosition p_b_1_) {
      return 255;
   }

   public int c(BlockPosition p_c_1_) {
      return 0;
   }

   public int getBrightness(EnumSkyBlock p_getBrightness_1_, BlockPosition p_getBrightness_2_) {
      return p_getBrightness_1_.c;
   }

   public void a(EnumSkyBlock p_a_1_, BlockPosition p_a_2_, int p_a_3_) {
   }

   public int a(BlockPosition p_a_1_, int p_a_2_) {
      return 0;
   }

   public void a(Entity p_a_1_) {
   }

   public void b(Entity p_b_1_) {
   }

   public void a(Entity p_a_1_, int p_a_2_) {
   }

   public boolean d(BlockPosition p_d_1_) {
      return false;
   }

   public TileEntity a(BlockPosition p_a_1_, Chunk.EnumTileEntityState p_a_2_) {
      return null;
   }

   public void a(TileEntity p_a_1_) {
   }

   public void a(BlockPosition p_a_1_, TileEntity p_a_2_) {
   }

   public void e(BlockPosition p_e_1_) {
   }

   public void addEntities() {
   }

   public void removeEntities() {
   }

   public void e() {
   }

   public void a(Entity p_a_1_, AxisAlignedBB p_a_2_, List<Entity> p_a_3_, Predicate<? super Entity> p_a_4_) {
   }

   public <T extends Entity> void a(Class<? extends T> p_a_1_, AxisAlignedBB p_a_2_, List<T> p_a_3_, Predicate<? super T> p_a_4_) {
   }

   public boolean a(boolean p_a_1_) {
      return false;
   }

   public Random a(long p_a_1_) {
      return new Random(this.getWorld().getSeed() + (long)(this.locX * this.locX * 4987142) + (long)(this.locX * 5947611) + (long)(this.locZ * this.locZ) * 4392871L + (long)(this.locZ * 389711) ^ p_a_1_);
   }

   public boolean isEmpty() {
      return true;
   }

   public boolean c(int p_c_1_, int p_c_2_) {
      return true;
   }
}

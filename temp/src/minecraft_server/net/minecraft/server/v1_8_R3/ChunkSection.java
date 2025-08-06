package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.NibbleArray;

public class ChunkSection {
   private int yPos;
   private int nonEmptyBlockCount;
   private int tickingBlockCount;
   private char[] blockIds;
   private NibbleArray emittedLight;
   private NibbleArray skyLight;

   public ChunkSection(int p_i353_1_, boolean p_i353_2_) {
      this.yPos = p_i353_1_;
      this.blockIds = new char[4096];
      this.emittedLight = new NibbleArray();
      if(p_i353_2_) {
         this.skyLight = new NibbleArray();
      }

   }

   public ChunkSection(int p_i354_1_, boolean p_i354_2_, char[] p_i354_3_) {
      this.yPos = p_i354_1_;
      this.blockIds = p_i354_3_;
      this.emittedLight = new NibbleArray();
      if(p_i354_2_) {
         this.skyLight = new NibbleArray();
      }

      this.recalcBlockCounts();
   }

   public IBlockData getType(int p_getType_1_, int p_getType_2_, int p_getType_3_) {
      IBlockData iblockdata = (IBlockData)Block.d.a(this.blockIds[p_getType_2_ << 8 | p_getType_3_ << 4 | p_getType_1_]);
      return iblockdata != null?iblockdata:Blocks.AIR.getBlockData();
   }

   public void setType(int p_setType_1_, int p_setType_2_, int p_setType_3_, IBlockData p_setType_4_) {
      IBlockData iblockdata = this.getType(p_setType_1_, p_setType_2_, p_setType_3_);
      Block block = iblockdata.getBlock();
      Block block1 = p_setType_4_.getBlock();
      if(block != Blocks.AIR) {
         --this.nonEmptyBlockCount;
         if(block.isTicking()) {
            --this.tickingBlockCount;
         }
      }

      if(block1 != Blocks.AIR) {
         ++this.nonEmptyBlockCount;
         if(block1.isTicking()) {
            ++this.tickingBlockCount;
         }
      }

      this.blockIds[p_setType_2_ << 8 | p_setType_3_ << 4 | p_setType_1_] = (char)Block.d.b(p_setType_4_);
   }

   public Block b(int p_b_1_, int p_b_2_, int p_b_3_) {
      return this.getType(p_b_1_, p_b_2_, p_b_3_).getBlock();
   }

   public int c(int p_c_1_, int p_c_2_, int p_c_3_) {
      IBlockData iblockdata = this.getType(p_c_1_, p_c_2_, p_c_3_);
      return iblockdata.getBlock().toLegacyData(iblockdata);
   }

   public boolean a() {
      return this.nonEmptyBlockCount == 0;
   }

   public boolean shouldTick() {
      return this.tickingBlockCount > 0;
   }

   public int getYPosition() {
      return this.yPos;
   }

   public void a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      this.skyLight.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
   }

   public int d(int p_d_1_, int p_d_2_, int p_d_3_) {
      return this.skyLight.a(p_d_1_, p_d_2_, p_d_3_);
   }

   public void b(int p_b_1_, int p_b_2_, int p_b_3_, int p_b_4_) {
      this.emittedLight.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
   }

   public int e(int p_e_1_, int p_e_2_, int p_e_3_) {
      return this.emittedLight.a(p_e_1_, p_e_2_, p_e_3_);
   }

   public void recalcBlockCounts() {
      this.nonEmptyBlockCount = 0;
      this.tickingBlockCount = 0;

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            for(int k = 0; k < 16; ++k) {
               Block block = this.b(i, j, k);
               if(block != Blocks.AIR) {
                  ++this.nonEmptyBlockCount;
                  if(block.isTicking()) {
                     ++this.tickingBlockCount;
                  }
               }
            }
         }
      }

   }

   public char[] getIdArray() {
      return this.blockIds;
   }

   public void a(char[] p_a_1_) {
      this.blockIds = p_a_1_;
   }

   public NibbleArray getEmittedLightArray() {
      return this.emittedLight;
   }

   public NibbleArray getSkyLightArray() {
      return this.skyLight;
   }

   public void a(NibbleArray p_a_1_) {
      this.emittedLight = p_a_1_;
   }

   public void b(NibbleArray p_b_1_) {
      this.skyLight = p_b_1_;
   }
}

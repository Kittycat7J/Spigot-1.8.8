package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class ChunkCache implements IBlockAccess {
   protected int a;
   protected int b;
   protected Chunk[][] c;
   protected boolean d;
   protected World e;

   public ChunkCache(World p_i561_1_, BlockPosition p_i561_2_, BlockPosition p_i561_3_, int p_i561_4_) {
      this.e = p_i561_1_;
      this.a = p_i561_2_.getX() - p_i561_4_ >> 4;
      this.b = p_i561_2_.getZ() - p_i561_4_ >> 4;
      int i = p_i561_3_.getX() + p_i561_4_ >> 4;
      int j = p_i561_3_.getZ() + p_i561_4_ >> 4;
      this.c = new Chunk[i - this.a + 1][j - this.b + 1];
      this.d = true;

      for(int k = this.a; k <= i; ++k) {
         for(int l = this.b; l <= j; ++l) {
            this.c[k - this.a][l - this.b] = p_i561_1_.getChunkAt(k, l);
         }
      }

      for(int i1 = p_i561_2_.getX() >> 4; i1 <= p_i561_3_.getX() >> 4; ++i1) {
         for(int j1 = p_i561_2_.getZ() >> 4; j1 <= p_i561_3_.getZ() >> 4; ++j1) {
            Chunk chunk = this.c[i1 - this.a][j1 - this.b];
            if(chunk != null && !chunk.c(p_i561_2_.getY(), p_i561_3_.getY())) {
               this.d = false;
            }
         }
      }

   }

   public TileEntity getTileEntity(BlockPosition p_getTileEntity_1_) {
      int i = (p_getTileEntity_1_.getX() >> 4) - this.a;
      int j = (p_getTileEntity_1_.getZ() >> 4) - this.b;
      return this.c[i][j].a(p_getTileEntity_1_, Chunk.EnumTileEntityState.IMMEDIATE);
   }

   public IBlockData getType(BlockPosition p_getType_1_) {
      if(p_getType_1_.getY() >= 0 && p_getType_1_.getY() < 256) {
         int i = (p_getType_1_.getX() >> 4) - this.a;
         int j = (p_getType_1_.getZ() >> 4) - this.b;
         if(i >= 0 && i < this.c.length && j >= 0 && j < this.c[i].length) {
            Chunk chunk = this.c[i][j];
            if(chunk != null) {
               return chunk.getBlockData(p_getType_1_);
            }
         }
      }

      return Blocks.AIR.getBlockData();
   }

   public boolean isEmpty(BlockPosition p_isEmpty_1_) {
      return this.getType(p_isEmpty_1_).getBlock().getMaterial() == Material.AIR;
   }

   public int getBlockPower(BlockPosition p_getBlockPower_1_, EnumDirection p_getBlockPower_2_) {
      IBlockData iblockdata = this.getType(p_getBlockPower_1_);
      return iblockdata.getBlock().b((IBlockAccess)this, p_getBlockPower_1_, iblockdata, (EnumDirection)p_getBlockPower_2_);
   }
}

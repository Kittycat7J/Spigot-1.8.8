package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutMultiBlockChange implements Packet<PacketListenerPlayOut> {
   private ChunkCoordIntPair a;
   private PacketPlayOutMultiBlockChange.MultiBlockChangeInfo[] b;

   public PacketPlayOutMultiBlockChange() {
   }

   public PacketPlayOutMultiBlockChange(int p_i967_1_, short[] p_i967_2_, Chunk p_i967_3_) {
      this.a = new ChunkCoordIntPair(p_i967_3_.locX, p_i967_3_.locZ);
      this.b = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo[p_i967_1_];

      for(int i = 0; i < this.b.length; ++i) {
         this.b[i] = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo(p_i967_2_[i], p_i967_3_);
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = new ChunkCoordIntPair(p_a_1_.readInt(), p_a_1_.readInt());
      this.b = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo[p_a_1_.e()];

      for(int i = 0; i < this.b.length; ++i) {
         this.b[i] = new PacketPlayOutMultiBlockChange.MultiBlockChangeInfo(p_a_1_.readShort(), (IBlockData)Block.d.a(p_a_1_.e()));
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeInt(this.a.x);
      p_b_1_.writeInt(this.a.z);
      p_b_1_.b(this.b.length);

      for(PacketPlayOutMultiBlockChange.MultiBlockChangeInfo packetplayoutmultiblockchange$multiblockchangeinfo : this.b) {
         p_b_1_.writeShort(packetplayoutmultiblockchange$multiblockchangeinfo.b());
         p_b_1_.b(Block.d.b(packetplayoutmultiblockchange$multiblockchangeinfo.c()));
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public class MultiBlockChangeInfo {
      private final short b;
      private final IBlockData c;

      public MultiBlockChangeInfo(short p_i965_2_, IBlockData p_i965_3_) {
         this.b = p_i965_2_;
         this.c = p_i965_3_;
      }

      public MultiBlockChangeInfo(short p_i966_2_, Chunk p_i966_3_) {
         this.b = p_i966_2_;
         this.c = p_i966_3_.getBlockData(this.a());
      }

      public BlockPosition a() {
         return new BlockPosition(PacketPlayOutMultiBlockChange.this.a.a(this.b >> 12 & 15, this.b & 255, this.b >> 8 & 15));
      }

      public short b() {
         return this.b;
      }

      public IBlockData c() {
         return this.c;
      }
   }
}

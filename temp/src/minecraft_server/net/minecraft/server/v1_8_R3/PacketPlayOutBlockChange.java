package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.World;

public class PacketPlayOutBlockChange implements Packet<PacketListenerPlayOut> {
   private BlockPosition a;
   public IBlockData block;

   public PacketPlayOutBlockChange() {
   }

   public PacketPlayOutBlockChange(World p_i962_1_, BlockPosition p_i962_2_) {
      this.a = p_i962_2_;
      this.block = p_i962_1_.getType(p_i962_2_);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c();
      this.block = (IBlockData)Block.d.a(p_a_1_.e());
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
      p_b_1_.b(Block.d.b(this.block));
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}

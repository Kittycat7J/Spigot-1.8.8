package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;
import org.apache.commons.lang3.StringUtils;

public class PacketPlayInTabComplete implements Packet<PacketListenerPlayIn> {
   private String a;
   private BlockPosition b;

   public PacketPlayInTabComplete() {
   }

   public PacketPlayInTabComplete(String p_i1037_1_) {
      this(p_i1037_1_, (BlockPosition)null);
   }

   public PacketPlayInTabComplete(String p_i1038_1_, BlockPosition p_i1038_2_) {
      this.a = p_i1038_1_;
      this.b = p_i1038_2_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c(32767);
      boolean flag = p_a_1_.readBoolean();
      if(flag) {
         this.b = p_a_1_.c();
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(StringUtils.substring(this.a, 0, 32767));
      boolean flag = this.b != null;
      p_b_1_.writeBoolean(flag);
      if(flag) {
         p_b_1_.a(this.b);
      }

   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public String a() {
      return this.a;
   }

   public BlockPosition b() {
      return this.b;
   }
}

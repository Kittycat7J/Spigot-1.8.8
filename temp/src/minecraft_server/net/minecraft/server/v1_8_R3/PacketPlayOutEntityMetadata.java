package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.List;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutEntityMetadata implements Packet<PacketListenerPlayOut> {
   private int a;
   private List<DataWatcher.WatchableObject> b;

   public PacketPlayOutEntityMetadata() {
   }

   public PacketPlayOutEntityMetadata(int p_i1008_1_, DataWatcher p_i1008_2_, boolean p_i1008_3_) {
      this.a = p_i1008_1_;
      if(p_i1008_3_) {
         this.b = p_i1008_2_.c();
      } else {
         this.b = p_i1008_2_.b();
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = DataWatcher.b(p_a_1_);
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      DataWatcher.a(this.b, p_b_1_);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutUpdateAttributes implements Packet<PacketListenerPlayOut> {
   private int a;
   private final List<PacketPlayOutUpdateAttributes.AttributeSnapshot> b = Lists.<PacketPlayOutUpdateAttributes.AttributeSnapshot>newArrayList();

   public PacketPlayOutUpdateAttributes() {
   }

   public PacketPlayOutUpdateAttributes(int p_i1035_1_, Collection<AttributeInstance> p_i1035_2_) {
      this.a = p_i1035_1_;

      for(AttributeInstance attributeinstance : p_i1035_2_) {
         this.b.add(new PacketPlayOutUpdateAttributes.AttributeSnapshot(attributeinstance.getAttribute().getName(), attributeinstance.b(), attributeinstance.c()));
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      int i = p_a_1_.readInt();

      for(int j = 0; j < i; ++j) {
         String s = p_a_1_.c(64);
         double d0 = p_a_1_.readDouble();
         ArrayList arraylist = Lists.newArrayList();
         int k = p_a_1_.e();

         for(int l = 0; l < k; ++l) {
            UUID uuid = p_a_1_.g();
            arraylist.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", p_a_1_.readDouble(), p_a_1_.readByte()));
         }

         this.b.add(new PacketPlayOutUpdateAttributes.AttributeSnapshot(s, d0, arraylist));
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.writeInt(this.b.size());

      for(PacketPlayOutUpdateAttributes.AttributeSnapshot packetplayoutupdateattributes$attributesnapshot : this.b) {
         p_b_1_.a(packetplayoutupdateattributes$attributesnapshot.a());
         p_b_1_.writeDouble(packetplayoutupdateattributes$attributesnapshot.b());
         p_b_1_.b(packetplayoutupdateattributes$attributesnapshot.c().size());

         for(AttributeModifier attributemodifier : packetplayoutupdateattributes$attributesnapshot.c()) {
            p_b_1_.a(attributemodifier.a());
            p_b_1_.writeDouble(attributemodifier.d());
            p_b_1_.writeByte(attributemodifier.c());
         }
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public class AttributeSnapshot {
      private final String b;
      private final double c;
      private final Collection<AttributeModifier> d;

      public AttributeSnapshot(String p_i1034_2_, double p_i1034_3_, Collection<AttributeModifier> p_i1034_5_) {
         this.b = p_i1034_2_;
         this.c = p_i1034_3_;
         this.d = p_i1034_5_;
      }

      public String a() {
         return this.b;
      }

      public double b() {
         return this.c;
      }

      public Collection<AttributeModifier> c() {
         return this.d;
      }
   }
}

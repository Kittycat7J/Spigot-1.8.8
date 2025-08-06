package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutNamedEntitySpawn implements Packet<PacketListenerPlayOut> {
   private int a;
   private UUID b;
   private int c;
   private int d;
   private int e;
   private byte f;
   private byte g;
   private int h;
   private DataWatcher i;
   private List<DataWatcher.WatchableObject> j;

   public PacketPlayOutNamedEntitySpawn() {
   }

   public PacketPlayOutNamedEntitySpawn(EntityHuman p_i956_1_) {
      this.a = p_i956_1_.getId();
      this.b = p_i956_1_.getProfile().getId();
      this.c = MathHelper.floor(p_i956_1_.locX * 32.0D);
      this.d = MathHelper.floor(p_i956_1_.locY * 32.0D);
      this.e = MathHelper.floor(p_i956_1_.locZ * 32.0D);
      this.f = (byte)((int)(p_i956_1_.yaw * 256.0F / 360.0F));
      this.g = (byte)((int)(p_i956_1_.pitch * 256.0F / 360.0F));
      ItemStack itemstack = p_i956_1_.inventory.getItemInHand();
      this.h = itemstack == null?0:Item.getId(itemstack.getItem());
      this.i = p_i956_1_.getDataWatcher();
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = p_a_1_.g();
      this.c = p_a_1_.readInt();
      this.d = p_a_1_.readInt();
      this.e = p_a_1_.readInt();
      this.f = p_a_1_.readByte();
      this.g = p_a_1_.readByte();
      this.h = p_a_1_.readShort();
      this.j = DataWatcher.b(p_a_1_);
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.a(this.b);
      p_b_1_.writeInt(this.c);
      p_b_1_.writeInt(this.d);
      p_b_1_.writeInt(this.e);
      p_b_1_.writeByte(this.f);
      p_b_1_.writeByte(this.g);
      p_b_1_.writeShort(this.h);
      this.i.a(p_b_1_);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}

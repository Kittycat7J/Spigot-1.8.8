package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInBlockPlace implements Packet<PacketListenerPlayIn> {
   private static final BlockPosition a = new BlockPosition(-1, -1, -1);
   private BlockPosition b;
   private int c;
   private ItemStack d;
   private float e;
   private float f;
   private float g;
   public long timestamp;

   public PacketPlayInBlockPlace() {
   }

   public PacketPlayInBlockPlace(ItemStack p_i209_1_) {
      this(a, 255, p_i209_1_, 0.0F, 0.0F, 0.0F);
   }

   public PacketPlayInBlockPlace(BlockPosition p_i210_1_, int p_i210_2_, ItemStack p_i210_3_, float p_i210_4_, float p_i210_5_, float p_i210_6_) {
      this.b = p_i210_1_;
      this.c = p_i210_2_;
      this.d = p_i210_3_ != null?p_i210_3_.cloneItemStack():null;
      this.e = p_i210_4_;
      this.f = p_i210_5_;
      this.g = p_i210_6_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.timestamp = System.currentTimeMillis();
      this.b = p_a_1_.c();
      this.c = p_a_1_.readUnsignedByte();
      this.d = p_a_1_.i();
      this.e = (float)p_a_1_.readUnsignedByte() / 16.0F;
      this.f = (float)p_a_1_.readUnsignedByte() / 16.0F;
      this.g = (float)p_a_1_.readUnsignedByte() / 16.0F;
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.b);
      p_b_1_.writeByte(this.c);
      p_b_1_.a(this.d);
      p_b_1_.writeByte((int)(this.e * 16.0F));
      p_b_1_.writeByte((int)(this.f * 16.0F));
      p_b_1_.writeByte((int)(this.g * 16.0F));
   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public BlockPosition a() {
      return this.b;
   }

   public int getFace() {
      return this.c;
   }

   public ItemStack getItemStack() {
      return this.d;
   }

   public float d() {
      return this.e;
   }

   public float e() {
      return this.f;
   }

   public float f() {
      return this.g;
   }
}

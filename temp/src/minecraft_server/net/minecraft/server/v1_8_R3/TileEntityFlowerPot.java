package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.TileEntity;

public class TileEntityFlowerPot extends TileEntity {
   private Item a;
   private int f;

   public TileEntityFlowerPot() {
   }

   public TileEntityFlowerPot(Item p_i657_1_, int p_i657_2_) {
      this.a = p_i657_1_;
      this.f = p_i657_2_;
   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      MinecraftKey minecraftkey = (MinecraftKey)Item.REGISTRY.c(this.a);
      p_b_1_.setString("Item", minecraftkey == null?"":minecraftkey.toString());
      p_b_1_.setInt("Data", this.f);
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      if(p_a_1_.hasKeyOfType("Item", 8)) {
         this.a = Item.d(p_a_1_.getString("Item"));
      } else {
         this.a = Item.getById(p_a_1_.getInt("Item"));
      }

      this.f = p_a_1_.getInt("Data");
   }

   public Packet getUpdatePacket() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.b(nbttagcompound);
      nbttagcompound.remove("Item");
      nbttagcompound.setInt("Item", Item.getId(this.a));
      return new PacketPlayOutTileEntityData(this.position, 5, nbttagcompound);
   }

   public void a(Item p_a_1_, int p_a_2_) {
      this.a = p_a_1_;
      this.f = p_a_2_;
   }

   public Item b() {
      return this.a;
   }

   public int c() {
      return this.f;
   }
}

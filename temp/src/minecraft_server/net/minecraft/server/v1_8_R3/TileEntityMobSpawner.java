package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.MobSpawnerAbstract;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityMobSpawnerData;
import net.minecraft.server.v1_8_R3.World;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox {
   private final MobSpawnerAbstract a = new MobSpawnerAbstract() {
      public void a(int p_a_1_) {
         TileEntityMobSpawner.this.world.playBlockAction(TileEntityMobSpawner.this.position, Blocks.MOB_SPAWNER, p_a_1_, 0);
      }

      public World a() {
         return TileEntityMobSpawner.this.world;
      }

      public BlockPosition b() {
         return TileEntityMobSpawner.this.position;
      }

      public void a(TileEntityMobSpawnerData p_a_1_) {
         super.a((TileEntityMobSpawnerData)p_a_1_);
         if(this.a() != null) {
            this.a().notify(TileEntityMobSpawner.this.position);
         }

      }
   };

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.a.a(p_a_1_);
   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      this.a.b(p_b_1_);
   }

   public void c() {
      this.a.c();
   }

   public Packet getUpdatePacket() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.b(nbttagcompound);
      nbttagcompound.remove("SpawnPotentials");
      return new PacketPlayOutTileEntityData(this.position, 1, nbttagcompound);
   }

   public boolean c(int p_c_1_, int p_c_2_) {
      return this.a.b(p_c_1_)?true:super.c(p_c_1_, p_c_2_);
   }

   public boolean F() {
      return true;
   }

   public MobSpawnerAbstract getSpawner() {
      return this.a;
   }
}

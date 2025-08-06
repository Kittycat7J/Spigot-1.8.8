package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.World;

public class DemoPlayerInteractManager extends PlayerInteractManager {
   private boolean c;
   private boolean d;
   private int e;
   private int f;

   public DemoPlayerInteractManager(World p_i1074_1_) {
      super(p_i1074_1_);
   }

   public void a() {
      super.a();
      ++this.f;
      long i = this.world.getTime();
      long j = i / 24000L + 1L;
      if(!this.c && this.f > 20) {
         this.c = true;
         this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 0.0F));
      }

      this.d = i > 120500L;
      if(this.d) {
         ++this.e;
      }

      if(i % 24000L == 500L) {
         if(j <= 6L) {
            this.player.sendMessage((IChatBaseComponent)(new ChatMessage("demo.day." + j, new Object[0])));
         }
      } else if(j == 1L) {
         if(i == 100L) {
            this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 101.0F));
         } else if(i == 175L) {
            this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 102.0F));
         } else if(i == 250L) {
            this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 103.0F));
         }
      } else if(j == 5L && i % 24000L == 22000L) {
         this.player.sendMessage((IChatBaseComponent)(new ChatMessage("demo.day.warning", new Object[0])));
      }

   }

   private void f() {
      if(this.e > 100) {
         this.player.sendMessage((IChatBaseComponent)(new ChatMessage("demo.reminder", new Object[0])));
         this.e = 0;
      }

   }

   public void a(BlockPosition p_a_1_, EnumDirection p_a_2_) {
      if(this.d) {
         this.f();
      } else {
         super.a(p_a_1_, p_a_2_);
      }
   }

   public void a(BlockPosition p_a_1_) {
      if(!this.d) {
         super.a(p_a_1_);
      }
   }

   public boolean breakBlock(BlockPosition p_breakBlock_1_) {
      return this.d?false:super.breakBlock(p_breakBlock_1_);
   }

   public boolean useItem(EntityHuman p_useItem_1_, World p_useItem_2_, ItemStack p_useItem_3_) {
      if(this.d) {
         this.f();
         return false;
      } else {
         return super.useItem(p_useItem_1_, p_useItem_2_, p_useItem_3_);
      }
   }

   public boolean interact(EntityHuman p_interact_1_, World p_interact_2_, ItemStack p_interact_3_, BlockPosition p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(this.d) {
         this.f();
         return false;
      } else {
         return super.interact(p_interact_1_, p_interact_2_, p_interact_3_, p_interact_4_, p_interact_5_, p_interact_6_, p_interact_7_, p_interact_8_);
      }
   }
}

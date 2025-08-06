package net.minecraft.server.v1_8_R3;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.WorldSettings;

public class PacketPlayOutPlayerInfo implements Packet<PacketListenerPlayOut> {
   private PacketPlayOutPlayerInfo.EnumPlayerInfoAction a;
   private final List<PacketPlayOutPlayerInfo.PlayerInfoData> b = Lists.<PacketPlayOutPlayerInfo.PlayerInfoData>newArrayList();

   public PacketPlayOutPlayerInfo() {
   }

   public PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction p_i995_1_, EntityPlayer... p_i995_2_) {
      this.a = p_i995_1_;

      for(EntityPlayer entityplayer : p_i995_2_) {
         this.b.add(new PacketPlayOutPlayerInfo.PlayerInfoData(entityplayer.getProfile(), entityplayer.ping, entityplayer.playerInteractManager.getGameMode(), entityplayer.getPlayerListName()));
      }

   }

   public PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction p_i996_1_, Iterable<EntityPlayer> p_i996_2_) {
      this.a = p_i996_1_;

      for(EntityPlayer entityplayer : p_i996_2_) {
         this.b.add(new PacketPlayOutPlayerInfo.PlayerInfoData(entityplayer.getProfile(), entityplayer.ping, entityplayer.playerInteractManager.getGameMode(), entityplayer.getPlayerListName()));
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = (PacketPlayOutPlayerInfo.EnumPlayerInfoAction)p_a_1_.a(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class);
      int i = p_a_1_.e();

      for(int j = 0; j < i; ++j) {
         GameProfile gameprofile = null;
         int k = 0;
         WorldSettings.EnumGamemode worldsettings$enumgamemode = null;
         IChatBaseComponent ichatbasecomponent = null;
         switch(this.a) {
         case ADD_PLAYER:
            gameprofile = new GameProfile(p_a_1_.g(), p_a_1_.c(16));
            int l = p_a_1_.e();
            int i1 = 0;

            for(; i1 < l; ++i1) {
               String s = p_a_1_.c(32767);
               String s1 = p_a_1_.c(32767);
               if(p_a_1_.readBoolean()) {
                  gameprofile.getProperties().put(s, new Property(s, s1, p_a_1_.c(32767)));
               } else {
                  gameprofile.getProperties().put(s, new Property(s, s1));
               }
            }

            worldsettings$enumgamemode = WorldSettings.EnumGamemode.getById(p_a_1_.e());
            k = p_a_1_.e();
            if(p_a_1_.readBoolean()) {
               ichatbasecomponent = p_a_1_.d();
            }
            break;
         case UPDATE_GAME_MODE:
            gameprofile = new GameProfile(p_a_1_.g(), (String)null);
            worldsettings$enumgamemode = WorldSettings.EnumGamemode.getById(p_a_1_.e());
            break;
         case UPDATE_LATENCY:
            gameprofile = new GameProfile(p_a_1_.g(), (String)null);
            k = p_a_1_.e();
            break;
         case UPDATE_DISPLAY_NAME:
            gameprofile = new GameProfile(p_a_1_.g(), (String)null);
            if(p_a_1_.readBoolean()) {
               ichatbasecomponent = p_a_1_.d();
            }
            break;
         case REMOVE_PLAYER:
            gameprofile = new GameProfile(p_a_1_.g(), (String)null);
         }

         this.b.add(new PacketPlayOutPlayerInfo.PlayerInfoData(gameprofile, k, worldsettings$enumgamemode, ichatbasecomponent));
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a((Enum)this.a);
      p_b_1_.b(this.b.size());

      for(PacketPlayOutPlayerInfo.PlayerInfoData packetplayoutplayerinfo$playerinfodata : this.b) {
         switch(this.a) {
         case ADD_PLAYER:
            p_b_1_.a(packetplayoutplayerinfo$playerinfodata.a().getId());
            p_b_1_.a(packetplayoutplayerinfo$playerinfodata.a().getName());
            p_b_1_.b(packetplayoutplayerinfo$playerinfodata.a().getProperties().size());

            for(Property property : packetplayoutplayerinfo$playerinfodata.a().getProperties().values()) {
               p_b_1_.a(property.getName());
               p_b_1_.a(property.getValue());
               if(property.hasSignature()) {
                  p_b_1_.writeBoolean(true);
                  p_b_1_.a(property.getSignature());
               } else {
                  p_b_1_.writeBoolean(false);
               }
            }

            p_b_1_.b(packetplayoutplayerinfo$playerinfodata.c().getId());
            p_b_1_.b(packetplayoutplayerinfo$playerinfodata.b());
            if(packetplayoutplayerinfo$playerinfodata.d() == null) {
               p_b_1_.writeBoolean(false);
            } else {
               p_b_1_.writeBoolean(true);
               p_b_1_.a(packetplayoutplayerinfo$playerinfodata.d());
            }
            break;
         case UPDATE_GAME_MODE:
            p_b_1_.a(packetplayoutplayerinfo$playerinfodata.a().getId());
            p_b_1_.b(packetplayoutplayerinfo$playerinfodata.c().getId());
            break;
         case UPDATE_LATENCY:
            p_b_1_.a(packetplayoutplayerinfo$playerinfodata.a().getId());
            p_b_1_.b(packetplayoutplayerinfo$playerinfodata.b());
            break;
         case UPDATE_DISPLAY_NAME:
            p_b_1_.a(packetplayoutplayerinfo$playerinfodata.a().getId());
            if(packetplayoutplayerinfo$playerinfodata.d() == null) {
               p_b_1_.writeBoolean(false);
            } else {
               p_b_1_.writeBoolean(true);
               p_b_1_.a(packetplayoutplayerinfo$playerinfodata.d());
            }
            break;
         case REMOVE_PLAYER:
            p_b_1_.a(packetplayoutplayerinfo$playerinfodata.a().getId());
         }
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public String toString() {
      return Objects.toStringHelper(this).add("action", this.a).add("entries", this.b).toString();
   }

   public static enum EnumPlayerInfoAction {
      ADD_PLAYER,
      UPDATE_GAME_MODE,
      UPDATE_LATENCY,
      UPDATE_DISPLAY_NAME,
      REMOVE_PLAYER;
   }

   public class PlayerInfoData {
      private final int b;
      private final WorldSettings.EnumGamemode c;
      private final GameProfile d;
      private final IChatBaseComponent e;

      public PlayerInfoData(GameProfile p_i994_2_, int p_i994_3_, WorldSettings.EnumGamemode p_i994_4_, IChatBaseComponent p_i994_5_) {
         this.d = p_i994_2_;
         this.b = p_i994_3_;
         this.c = p_i994_4_;
         this.e = p_i994_5_;
      }

      public GameProfile a() {
         return this.d;
      }

      public int b() {
         return this.b;
      }

      public WorldSettings.EnumGamemode c() {
         return this.c;
      }

      public IChatBaseComponent d() {
         return this.e;
      }

      public String toString() {
         return Objects.toStringHelper(this).add("latency", this.b).add("gameMode", this.c).add("profile", this.d).add("displayName", this.e == null?null:IChatBaseComponent.ChatSerializer.a(this.e)).toString();
      }
   }
}

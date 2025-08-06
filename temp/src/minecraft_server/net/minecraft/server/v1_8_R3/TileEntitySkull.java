package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.UtilColor;

public class TileEntitySkull extends TileEntity {
   private int a;
   private int rotation;
   private GameProfile g = null;
   public static final Executor executor = Executors.newFixedThreadPool(3, (new ThreadFactoryBuilder()).setNameFormat("Head Conversion Thread - %1$d").build());
   public static final LoadingCache<String, GameProfile> skinCache = CacheBuilder.newBuilder().maximumSize(5000L).expireAfterAccess(60L, TimeUnit.MINUTES).<String, GameProfile>build(new CacheLoader<String, GameProfile>() {
      public GameProfile load(String p_load_1_) throws Exception {
         final GameProfile[] agameprofile = new GameProfile[1];
         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
            public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
               agameprofile[0] = p_onProfileLookupSucceeded_1_;
            }

            public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
               agameprofile[0] = p_onProfileLookupFailed_1_;
            }
         };
         MinecraftServer.getServer().getGameProfileRepository().findProfilesByNames(new String[]{p_load_1_}, Agent.MINECRAFT, profilelookupcallback);
         GameProfile gameprofile = agameprofile[0];
         if(gameprofile == null) {
            UUID uuid = EntityHuman.a(new GameProfile((UUID)null, p_load_1_));
            gameprofile = new GameProfile(uuid, p_load_1_);
            profilelookupcallback.onProfileLookupSucceeded(gameprofile);
         } else {
            Property property = (Property)Iterables.getFirst(gameprofile.getProperties().get("textures"), null);
            if(property == null) {
               gameprofile = MinecraftServer.getServer().aD().fillProfileProperties(gameprofile, true);
            }
         }

         return gameprofile;
      }
   });

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setByte("SkullType", (byte)(this.a & 255));
      p_b_1_.setByte("Rot", (byte)(this.rotation & 255));
      if(this.g != null) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         GameProfileSerializer.serialize(nbttagcompound, this.g);
         p_b_1_.set("Owner", nbttagcompound);
      }

   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.a = p_a_1_.getByte("SkullType");
      this.rotation = p_a_1_.getByte("Rot");
      if(this.a == 3) {
         if(p_a_1_.hasKeyOfType("Owner", 10)) {
            this.g = GameProfileSerializer.deserialize(p_a_1_.getCompound("Owner"));
         } else if(p_a_1_.hasKeyOfType("ExtraType", 8)) {
            String s = p_a_1_.getString("ExtraType");
            if(!UtilColor.b(s)) {
               this.g = new GameProfile((UUID)null, s);
               this.e();
            }
         }
      }

   }

   public GameProfile getGameProfile() {
      return this.g;
   }

   public Packet getUpdatePacket() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.b(nbttagcompound);
      return new PacketPlayOutTileEntityData(this.position, 4, nbttagcompound);
   }

   public void setSkullType(int p_setSkullType_1_) {
      this.a = p_setSkullType_1_;
      this.g = null;
   }

   public void setGameProfile(GameProfile p_setGameProfile_1_) {
      this.a = 3;
      this.g = p_setGameProfile_1_;
      this.e();
   }

   private void e() {
      GameProfile gameprofile = this.g;
      this.setSkullType(0);
      b(gameprofile, new Predicate<GameProfile>() {
         public boolean apply(GameProfile p_apply_1_) {
            TileEntitySkull.this.setSkullType(3);
            TileEntitySkull.this.g = p_apply_1_;
            TileEntitySkull.this.update();
            if(TileEntitySkull.this.world != null) {
               TileEntitySkull.this.world.notify(TileEntitySkull.this.position);
            }

            return false;
         }
      });
   }

   public static void b(final GameProfile p_b_0_, final Predicate<GameProfile> p_b_1_) {
      if(p_b_0_ != null && !UtilColor.b(p_b_0_.getName())) {
         if(p_b_0_.isComplete() && p_b_0_.getProperties().containsKey("textures")) {
            p_b_1_.apply(p_b_0_);
         } else if(MinecraftServer.getServer() == null) {
            p_b_1_.apply(p_b_0_);
         } else {
            GameProfile gameprofile = (GameProfile)skinCache.getIfPresent(p_b_0_.getName());
            if(gameprofile != null && Iterables.getFirst(gameprofile.getProperties().get("textures"), null) != null) {
               p_b_1_.apply(gameprofile);
            } else {
               executor.execute(new Runnable() {
                  public void run() {
                     final GameProfile gameprofile1 = (GameProfile)TileEntitySkull.skinCache.getUnchecked(p_b_0_.getName().toLowerCase());
                     MinecraftServer.getServer().processQueue.add(new Runnable() {
                        public void run() {
                           if(gameprofile1 == null) {
                              p_b_1_.apply(p_b_0_);
                           } else {
                              p_b_1_.apply(gameprofile1);
                           }

                        }
                     });
                  }
               });
            }
         }
      } else {
         p_b_1_.apply(p_b_0_);
      }

   }

   public int getSkullType() {
      return this.a;
   }

   public void setRotation(int p_setRotation_1_) {
      this.rotation = p_setRotation_1_;
   }

   public int getRotation() {
      return this.rotation;
   }
}

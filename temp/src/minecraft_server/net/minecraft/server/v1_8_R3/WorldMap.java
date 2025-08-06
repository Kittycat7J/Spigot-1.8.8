package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItemFrame;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MapIcon;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutMap;
import net.minecraft.server.v1_8_R3.PersistentBase;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.map.CraftMapView;
import org.bukkit.craftbukkit.v1_8_R3.map.RenderData;
import org.bukkit.map.MapCursor;

public class WorldMap extends PersistentBase {
   public int centerX;
   public int centerZ;
   public byte map;
   public byte scale;
   public byte[] colors = new byte[16384];
   public List<WorldMap.WorldMapHumanTracker> g = Lists.<WorldMap.WorldMapHumanTracker>newArrayList();
   public Map<EntityHuman, WorldMap.WorldMapHumanTracker> i = Maps.<EntityHuman, WorldMap.WorldMapHumanTracker>newHashMap();
   public Map<UUID, MapIcon> decorations = Maps.<UUID, MapIcon>newLinkedHashMap();
   public final CraftMapView mapView = new CraftMapView(this);
   private CraftServer server = (CraftServer)Bukkit.getServer();
   private UUID uniqueId = null;

   public WorldMap(String p_i284_1_) {
      super(p_i284_1_);
   }

   public void a(double p_a_1_, double p_a_3_, int p_a_5_) {
      int i = 128 * (1 << p_a_5_);
      int j = MathHelper.floor((p_a_1_ + 64.0D) / (double)i);
      int k = MathHelper.floor((p_a_3_ + 64.0D) / (double)i);
      this.centerX = j * i + i / 2 - 64;
      this.centerZ = k * i + i / 2 - 64;
   }

   public void a(NBTTagCompound p_a_1_) {
      byte b0 = p_a_1_.getByte("dimension");
      if(b0 >= 10) {
         long i = p_a_1_.getLong("UUIDLeast");
         long j = p_a_1_.getLong("UUIDMost");
         if(i != 0L && j != 0L) {
            this.uniqueId = new UUID(j, i);
            CraftWorld craftworld = (CraftWorld)this.server.getWorld(this.uniqueId);
            if(craftworld == null) {
               b0 = 127;
            } else {
               b0 = (byte)craftworld.getHandle().dimension;
            }
         }
      }

      this.map = b0;
      this.centerX = p_a_1_.getInt("xCenter");
      this.centerZ = p_a_1_.getInt("zCenter");
      this.scale = p_a_1_.getByte("scale");
      this.scale = (byte)MathHelper.clamp(this.scale, 0, 4);
      short short1 = p_a_1_.getShort("width");
      short short2 = p_a_1_.getShort("height");
      if(short1 == 128 && short2 == 128) {
         this.colors = p_a_1_.getByteArray("colors");
      } else {
         byte[] abyte = p_a_1_.getByteArray("colors");
         this.colors = new byte[16384];
         int k = (128 - short1) / 2;
         int l1 = (128 - short2) / 2;

         for(int l = 0; l < short2; ++l) {
            int i1 = l + l1;
            if(i1 >= 0 || i1 < 128) {
               for(int j1 = 0; j1 < short1; ++j1) {
                  int k1 = j1 + k;
                  if(k1 >= 0 || k1 < 128) {
                     this.colors[k1 + i1 * 128] = abyte[j1 + l * short1];
                  }
               }
            }
         }
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      if(this.map >= 10) {
         if(this.uniqueId == null) {
            for(org.bukkit.World world : this.server.getWorlds()) {
               CraftWorld craftworld = (CraftWorld)world;
               if(craftworld.getHandle().dimension == this.map) {
                  this.uniqueId = craftworld.getUID();
                  break;
               }
            }
         }

         if(this.uniqueId != null) {
            p_b_1_.setLong("UUIDLeast", this.uniqueId.getLeastSignificantBits());
            p_b_1_.setLong("UUIDMost", this.uniqueId.getMostSignificantBits());
         }
      }

      p_b_1_.setByte("dimension", this.map);
      p_b_1_.setInt("xCenter", this.centerX);
      p_b_1_.setInt("zCenter", this.centerZ);
      p_b_1_.setByte("scale", this.scale);
      p_b_1_.setShort("width", (short)128);
      p_b_1_.setShort("height", (short)128);
      p_b_1_.setByteArray("colors", this.colors);
   }

   public void a(EntityHuman p_a_1_, ItemStack p_a_2_) {
      if(!this.i.containsKey(p_a_1_)) {
         WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker = new WorldMap.WorldMapHumanTracker(p_a_1_);
         this.i.put(p_a_1_, worldmap$worldmaphumantracker);
         this.g.add(worldmap$worldmaphumantracker);
      }

      if(!p_a_1_.inventory.c(p_a_2_)) {
         this.decorations.remove(p_a_1_.getUniqueID());
      }

      for(int i = 0; i < this.g.size(); ++i) {
         WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker1 = (WorldMap.WorldMapHumanTracker)this.g.get(i);
         if(!worldmap$worldmaphumantracker1.trackee.dead && (worldmap$worldmaphumantracker1.trackee.inventory.c(p_a_2_) || p_a_2_.y())) {
            if(!p_a_2_.y() && worldmap$worldmaphumantracker1.trackee.dimension == this.map) {
               this.a(0, worldmap$worldmaphumantracker1.trackee.world, worldmap$worldmaphumantracker1.trackee.getUniqueID(), worldmap$worldmaphumantracker1.trackee.locX, worldmap$worldmaphumantracker1.trackee.locZ, (double)worldmap$worldmaphumantracker1.trackee.yaw);
            }
         } else {
            this.i.remove(worldmap$worldmaphumantracker1.trackee);
            this.g.remove(worldmap$worldmaphumantracker1);
         }
      }

      if(p_a_2_.y()) {
         EntityItemFrame entityitemframe = p_a_2_.z();
         BlockPosition blockposition = entityitemframe.getBlockPosition();
         this.a(1, p_a_1_.world, UUID.nameUUIDFromBytes(("frame-" + entityitemframe.getId()).getBytes(Charsets.US_ASCII)), (double)blockposition.getX(), (double)blockposition.getZ(), (double)(entityitemframe.direction.b() * 90));
      }

      if(p_a_2_.hasTag() && p_a_2_.getTag().hasKeyOfType("Decorations", 9)) {
         NBTTagList nbttaglist = p_a_2_.getTag().getList("Decorations", 10);

         for(int j = 0; j < nbttaglist.size(); ++j) {
            NBTTagCompound nbttagcompound = nbttaglist.get(j);
            UUID uuid = UUID.nameUUIDFromBytes(nbttagcompound.getString("id").getBytes(Charsets.US_ASCII));
            if(!this.decorations.containsKey(uuid)) {
               this.a(nbttagcompound.getByte("type"), p_a_1_.world, uuid, nbttagcompound.getDouble("x"), nbttagcompound.getDouble("z"), nbttagcompound.getDouble("rot"));
            }
         }
      }

   }

   private void a(int p_a_1_, World p_a_2_, UUID p_a_3_, double p_a_4_, double p_a_6_, double p_a_8_) {
      int i = 1 << this.scale;
      float f = (float)(p_a_4_ - (double)this.centerX) / (float)i;
      float f1 = (float)(p_a_6_ - (double)this.centerZ) / (float)i;
      byte b0 = (byte)((int)((double)(f * 2.0F) + 0.5D));
      byte b1 = (byte)((int)((double)(f1 * 2.0F) + 0.5D));
      byte b2 = 63;
      byte b3;
      if(f >= (float)(-b2) && f1 >= (float)(-b2) && f <= (float)b2 && f1 <= (float)b2) {
         p_a_8_ = p_a_8_ + (p_a_8_ < 0.0D?-8.0D:8.0D);
         b3 = (byte)((int)(p_a_8_ * 16.0D / 360.0D));
         if(this.map < 0) {
            int j = (int)(p_a_2_.getWorldData().getDayTime() / 10L);
            b3 = (byte)(j * j * 34187121 + j * 121 >> 15 & 15);
         }
      } else {
         if(Math.abs(f) >= 320.0F || Math.abs(f1) >= 320.0F) {
            this.decorations.remove(p_a_3_);
            return;
         }

         p_a_1_ = 6;
         b3 = 0;
         if(f <= (float)(-b2)) {
            b0 = (byte)((int)((double)(b2 * 2) + 2.5D));
         }

         if(f1 <= (float)(-b2)) {
            b1 = (byte)((int)((double)(b2 * 2) + 2.5D));
         }

         if(f >= (float)b2) {
            b0 = (byte)(b2 * 2 + 1);
         }

         if(f1 >= (float)b2) {
            b1 = (byte)(b2 * 2 + 1);
         }
      }

      this.decorations.put(p_a_3_, new MapIcon((byte)p_a_1_, b0, b1, b3));
   }

   public Packet a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker = (WorldMap.WorldMapHumanTracker)this.i.get(p_a_3_);
      return worldmap$worldmaphumantracker == null?null:worldmap$worldmaphumantracker.a(p_a_1_);
   }

   public void flagDirty(int p_flagDirty_1_, int p_flagDirty_2_) {
      super.c();

      for(WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker : this.g) {
         worldmap$worldmaphumantracker.a(p_flagDirty_1_, p_flagDirty_2_);
      }

   }

   public WorldMap.WorldMapHumanTracker a(EntityHuman p_a_1_) {
      WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker = (WorldMap.WorldMapHumanTracker)this.i.get(p_a_1_);
      if(worldmap$worldmaphumantracker == null) {
         worldmap$worldmaphumantracker = new WorldMap.WorldMapHumanTracker(p_a_1_);
         this.i.put(p_a_1_, worldmap$worldmaphumantracker);
         this.g.add(worldmap$worldmaphumantracker);
      }

      return worldmap$worldmaphumantracker;
   }

   public class WorldMapHumanTracker {
      public final EntityHuman trackee;
      private boolean d = true;
      private int e = 0;
      private int f = 0;
      private int g = 127;
      private int h = 127;
      private int i;
      public int b;

      public WorldMapHumanTracker(EntityHuman p_i268_2_) {
         this.trackee = p_i268_2_;
      }

      public Packet a(ItemStack p_a_1_) {
         RenderData renderdata = WorldMap.this.mapView.render((CraftPlayer)this.trackee.getBukkitEntity());
         Collection<MapIcon> collection = new ArrayList();

         for(MapCursor mapcursor : renderdata.cursors) {
            if(mapcursor.isVisible()) {
               collection.add(new MapIcon(mapcursor.getRawType(), mapcursor.getX(), mapcursor.getY(), mapcursor.getDirection()));
            }
         }

         if(this.d) {
            this.d = false;
            return new PacketPlayOutMap(p_a_1_.getData(), WorldMap.this.scale, collection, renderdata.buffer, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f);
         } else {
            return this.i++ % 5 == 0?new PacketPlayOutMap(p_a_1_.getData(), WorldMap.this.scale, collection, renderdata.buffer, 0, 0, 0, 0):null;
         }
      }

      public void a(int p_a_1_, int p_a_2_) {
         if(this.d) {
            this.e = Math.min(this.e, p_a_1_);
            this.f = Math.min(this.f, p_a_2_);
            this.g = Math.max(this.g, p_a_1_);
            this.h = Math.max(this.h, p_a_2_);
         } else {
            this.d = true;
            this.e = p_a_1_;
            this.f = p_a_2_;
            this.g = p_a_1_;
            this.h = p_a_2_;
         }

      }
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.minecraft.server.v1_8_R3.AttributeMapServer;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityBoat;
import net.minecraft.server.v1_8_R3.EntityEgg;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityEnderPearl;
import net.minecraft.server.v1_8_R3.EntityEnderSignal;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import net.minecraft.server.v1_8_R3.EntityFireball;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.EntityFishingHook;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityItemFrame;
import net.minecraft.server.v1_8_R3.EntityLeash;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityPainting;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityPotion;
import net.minecraft.server.v1_8_R3.EntitySmallFireball;
import net.minecraft.server.v1_8_R3.EntitySnowball;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.EntityThrownExpBottle;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;
import net.minecraft.server.v1_8_R3.IAnimal;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemWorldMap;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateEntityNBT;
import net.minecraft.server.v1_8_R3.WorldMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import org.spigotmc.AsyncCatcher;

public class EntityTrackerEntry {
   private static final Logger p = LogManager.getLogger();
   public Entity tracker;
   public int b;
   public int c;
   public int xLoc;
   public int yLoc;
   public int zLoc;
   public int yRot;
   public int xRot;
   public int i;
   public double j;
   public double k;
   public double l;
   public int m;
   private double q;
   private double r;
   private double s;
   private boolean isMoving;
   private boolean u;
   private int v;
   private Entity w;
   private boolean x;
   private boolean y;
   public boolean n;
   public Set<EntityPlayer> trackedPlayers = Sets.<EntityPlayer>newHashSet();

   public EntityTrackerEntry(Entity p_i139_1_, int p_i139_2_, int p_i139_3_, boolean p_i139_4_) {
      this.tracker = p_i139_1_;
      this.b = p_i139_2_;
      this.c = p_i139_3_;
      this.u = p_i139_4_;
      this.xLoc = MathHelper.floor(p_i139_1_.locX * 32.0D);
      this.yLoc = MathHelper.floor(p_i139_1_.locY * 32.0D);
      this.zLoc = MathHelper.floor(p_i139_1_.locZ * 32.0D);
      this.yRot = MathHelper.d(p_i139_1_.yaw * 256.0F / 360.0F);
      this.xRot = MathHelper.d(p_i139_1_.pitch * 256.0F / 360.0F);
      this.i = MathHelper.d(p_i139_1_.getHeadRotation() * 256.0F / 360.0F);
      this.y = p_i139_1_.onGround;
   }

   public boolean equals(Object p_equals_1_) {
      return p_equals_1_ instanceof EntityTrackerEntry?((EntityTrackerEntry)p_equals_1_).tracker.getId() == this.tracker.getId():false;
   }

   public int hashCode() {
      return this.tracker.getId();
   }

   public void track(List<EntityHuman> p_track_1_) {
      this.n = false;
      if(!this.isMoving || this.tracker.e(this.q, this.r, this.s) > 16.0D) {
         this.q = this.tracker.locX;
         this.r = this.tracker.locY;
         this.s = this.tracker.locZ;
         this.isMoving = true;
         this.n = true;
         this.scanPlayers(p_track_1_);
      }

      if(this.w != this.tracker.vehicle || this.tracker.vehicle != null && this.m % 60 == 0) {
         this.w = this.tracker.vehicle;
         this.broadcast(new PacketPlayOutAttachEntity(0, this.tracker, this.tracker.vehicle));
      }

      if(this.tracker instanceof EntityItemFrame) {
         EntityItemFrame entityitemframe = (EntityItemFrame)this.tracker;
         ItemStack itemstack = entityitemframe.getItem();
         if(this.m % 10 == 0 && itemstack != null && itemstack.getItem() instanceof ItemWorldMap) {
            WorldMap worldmap = Items.FILLED_MAP.getSavedMap(itemstack, this.tracker.world);

            for(EntityHuman entityhuman : this.trackedPlayers) {
               EntityPlayer entityplayer = (EntityPlayer)entityhuman;
               worldmap.a(entityplayer, itemstack);
               Packet packet = Items.FILLED_MAP.c(itemstack, this.tracker.world, entityplayer);
               if(packet != null) {
                  entityplayer.playerConnection.sendPacket(packet);
               }
            }
         }

         this.b();
      }

      if(this.m % this.c == 0 || this.tracker.ai || this.tracker.getDataWatcher().a()) {
         if(this.tracker.vehicle == null) {
            ++this.v;
            int k = MathHelper.floor(this.tracker.locX * 32.0D);
            int j1 = MathHelper.floor(this.tracker.locY * 32.0D);
            int k1 = MathHelper.floor(this.tracker.locZ * 32.0D);
            int l1 = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
            int i2 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
            int j2 = k - this.xLoc;
            int k2 = j1 - this.yLoc;
            int i = k1 - this.zLoc;
            Object object = null;
            boolean flag = Math.abs(j2) >= 4 || Math.abs(k2) >= 4 || Math.abs(i) >= 4 || this.m % 60 == 0;
            boolean flag1 = Math.abs(l1 - this.yRot) >= 4 || Math.abs(i2 - this.xRot) >= 4;
            if(flag) {
               this.xLoc = k;
               this.yLoc = j1;
               this.zLoc = k1;
            }

            if(flag1) {
               this.yRot = l1;
               this.xRot = i2;
            }

            if(this.m > 0 || this.tracker instanceof EntityArrow) {
               if(j2 >= -128 && j2 < 128 && k2 >= -128 && k2 < 128 && i >= -128 && i < 128 && this.v <= 400 && !this.x && this.y == this.tracker.onGround) {
                  if((!flag || !flag1) && !(this.tracker instanceof EntityArrow)) {
                     if(flag) {
                        object = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(this.tracker.getId(), (byte)j2, (byte)k2, (byte)i, this.tracker.onGround);
                     } else if(flag1) {
                        object = new PacketPlayOutEntity.PacketPlayOutEntityLook(this.tracker.getId(), (byte)l1, (byte)i2, this.tracker.onGround);
                     }
                  } else {
                     object = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.tracker.getId(), (byte)j2, (byte)k2, (byte)i, (byte)l1, (byte)i2, this.tracker.onGround);
                  }
               } else {
                  this.y = this.tracker.onGround;
                  this.v = 0;
                  if(this.tracker instanceof EntityPlayer) {
                     this.scanPlayers(new ArrayList(this.trackedPlayers));
                  }

                  object = new PacketPlayOutEntityTeleport(this.tracker.getId(), k, j1, k1, (byte)l1, (byte)i2, this.tracker.onGround);
               }
            }

            if(this.u) {
               double d0 = this.tracker.motX - this.j;
               double d1 = this.tracker.motY - this.k;
               double d2 = this.tracker.motZ - this.l;
               double d3 = 0.02D;
               double d4 = d0 * d0 + d1 * d1 + d2 * d2;
               if(d4 > d3 * d3 || d4 > 0.0D && this.tracker.motX == 0.0D && this.tracker.motY == 0.0D && this.tracker.motZ == 0.0D) {
                  this.j = this.tracker.motX;
                  this.k = this.tracker.motY;
                  this.l = this.tracker.motZ;
                  this.broadcast(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.j, this.k, this.l));
               }
            }

            if(object != null) {
               this.broadcast((Packet)object);
            }

            this.b();
            this.x = false;
         } else {
            int j = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
            int i1 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
            boolean flag3 = Math.abs(j - this.yRot) >= 4 || Math.abs(i1 - this.xRot) >= 4;
            if(flag3) {
               this.broadcast(new PacketPlayOutEntity.PacketPlayOutEntityLook(this.tracker.getId(), (byte)j, (byte)i1, this.tracker.onGround));
               this.yRot = j;
               this.xRot = i1;
            }

            this.xLoc = MathHelper.floor(this.tracker.locX * 32.0D);
            this.yLoc = MathHelper.floor(this.tracker.locY * 32.0D);
            this.zLoc = MathHelper.floor(this.tracker.locZ * 32.0D);
            this.b();
            this.x = true;
         }

         int cancelled = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
         if(Math.abs(cancelled - this.i) >= 4) {
            this.broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)cancelled));
            this.i = cancelled;
         }

         this.tracker.ai = false;
      }

      ++this.m;
      if(this.tracker.velocityChanged) {
         boolean flag2 = false;
         if(this.tracker instanceof EntityPlayer) {
            Player player = (Player)this.tracker.getBukkitEntity();
            Vector vector = player.getVelocity();
            PlayerVelocityEvent playervelocityevent = new PlayerVelocityEvent(player, vector.clone());
            this.tracker.world.getServer().getPluginManager().callEvent(playervelocityevent);
            if(playervelocityevent.isCancelled()) {
               flag2 = true;
            } else if(!vector.equals(playervelocityevent.getVelocity())) {
               player.setVelocity(playervelocityevent.getVelocity());
            }
         }

         if(!flag2) {
            this.broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.tracker));
         }

         this.tracker.velocityChanged = false;
      }

   }

   private void b() {
      DataWatcher datawatcher = this.tracker.getDataWatcher();
      if(datawatcher.a()) {
         this.broadcastIncludingSelf(new PacketPlayOutEntityMetadata(this.tracker.getId(), datawatcher, false));
      }

      if(this.tracker instanceof EntityLiving) {
         AttributeMapServer attributemapserver = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
         Set set = attributemapserver.getAttributes();
         if(!set.isEmpty()) {
            if(this.tracker instanceof EntityPlayer) {
               ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(set, false);
            }

            this.broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(this.tracker.getId(), set));
         }

         set.clear();
      }

   }

   public void broadcast(Packet p_broadcast_1_) {
      for(EntityPlayer entityplayer : this.trackedPlayers) {
         entityplayer.playerConnection.sendPacket(p_broadcast_1_);
      }

   }

   public void broadcastIncludingSelf(Packet p_broadcastIncludingSelf_1_) {
      this.broadcast(p_broadcastIncludingSelf_1_);
      if(this.tracker instanceof EntityPlayer) {
         ((EntityPlayer)this.tracker).playerConnection.sendPacket(p_broadcastIncludingSelf_1_);
      }

   }

   public void a() {
      for(EntityPlayer entityplayer : this.trackedPlayers) {
         entityplayer.d(this.tracker);
      }

   }

   public void a(EntityPlayer p_a_1_) {
      if(this.trackedPlayers.contains(p_a_1_)) {
         p_a_1_.d(this.tracker);
         this.trackedPlayers.remove(p_a_1_);
      }

   }

   public void updatePlayer(EntityPlayer p_updatePlayer_1_) {
      AsyncCatcher.catchOp("player tracker update");
      if(p_updatePlayer_1_ != this.tracker) {
         if(this.c(p_updatePlayer_1_)) {
            if(!this.trackedPlayers.contains(p_updatePlayer_1_) && (this.e(p_updatePlayer_1_) || this.tracker.attachedToPlayer)) {
               if(this.tracker instanceof EntityPlayer) {
                  Player player = ((EntityPlayer)this.tracker).getBukkitEntity();
                  if(!p_updatePlayer_1_.getBukkitEntity().canSee(player)) {
                     return;
                  }
               }

               p_updatePlayer_1_.removeQueue.remove(Integer.valueOf(this.tracker.getId()));
               this.trackedPlayers.add(p_updatePlayer_1_);
               Packet packet = this.c();
               p_updatePlayer_1_.playerConnection.sendPacket(packet);
               if(!this.tracker.getDataWatcher().d()) {
                  p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(this.tracker.getId(), this.tracker.getDataWatcher(), true));
               }

               NBTTagCompound nbttagcompound = this.tracker.getNBTTag();
               if(nbttagcompound != null) {
                  p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutUpdateEntityNBT(this.tracker.getId(), nbttagcompound));
               }

               if(this.tracker instanceof EntityLiving) {
                  AttributeMapServer attributemapserver = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
                  Collection collection = attributemapserver.c();
                  if(this.tracker.getId() == p_updatePlayer_1_.getId()) {
                     ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(collection, false);
                  }

                  if(!collection.isEmpty()) {
                     p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutUpdateAttributes(this.tracker.getId(), collection));
                  }
               }

               this.j = this.tracker.motX;
               this.k = this.tracker.motY;
               this.l = this.tracker.motZ;
               if(this.u && !(packet instanceof PacketPlayOutSpawnEntityLiving)) {
                  p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.tracker.motX, this.tracker.motY, this.tracker.motZ));
               }

               if(this.tracker.vehicle != null) {
                  p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, this.tracker, this.tracker.vehicle));
               }

               if(this.tracker instanceof EntityInsentient && ((EntityInsentient)this.tracker).getLeashHolder() != null) {
                  p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this.tracker, ((EntityInsentient)this.tracker).getLeashHolder()));
               }

               if(this.tracker instanceof EntityLiving) {
                  for(int i = 0; i < 5; ++i) {
                     ItemStack itemstack = ((EntityLiving)this.tracker).getEquipment(i);
                     if(itemstack != null) {
                        p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutEntityEquipment(this.tracker.getId(), i, itemstack));
                     }
                  }
               }

               if(this.tracker instanceof EntityHuman) {
                  EntityHuman entityhuman = (EntityHuman)this.tracker;
                  if(entityhuman.isSleeping()) {
                     p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutBed(entityhuman, new BlockPosition(this.tracker)));
                  }
               }

               this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
               this.broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)this.i));
               if(this.tracker instanceof EntityLiving) {
                  EntityLiving entityliving = (EntityLiving)this.tracker;

                  for(MobEffect mobeffect : entityliving.getEffects()) {
                     p_updatePlayer_1_.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.tracker.getId(), mobeffect));
                  }
               }
            }
         } else if(this.trackedPlayers.contains(p_updatePlayer_1_)) {
            this.trackedPlayers.remove(p_updatePlayer_1_);
            p_updatePlayer_1_.d(this.tracker);
         }
      }

   }

   public boolean c(EntityPlayer p_c_1_) {
      double d0 = p_c_1_.locX - this.tracker.locX;
      double d1 = p_c_1_.locZ - this.tracker.locZ;
      return d0 >= (double)(-this.b) && d0 <= (double)this.b && d1 >= (double)(-this.b) && d1 <= (double)this.b && this.tracker.a(p_c_1_);
   }

   private boolean e(EntityPlayer p_e_1_) {
      return p_e_1_.u().getPlayerChunkMap().a(p_e_1_, this.tracker.ae, this.tracker.ag);
   }

   public void scanPlayers(List<EntityHuman> p_scanPlayers_1_) {
      for(int i = 0; i < p_scanPlayers_1_.size(); ++i) {
         this.updatePlayer((EntityPlayer)p_scanPlayers_1_.get(i));
      }

   }

   private Packet c() {
      if(this.tracker.dead) {
         return null;
      } else if(this.tracker instanceof EntityItem) {
         return new PacketPlayOutSpawnEntity(this.tracker, 2, 1);
      } else if(this.tracker instanceof EntityPlayer) {
         return new PacketPlayOutNamedEntitySpawn((EntityHuman)this.tracker);
      } else if(this.tracker instanceof EntityMinecartAbstract) {
         EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract)this.tracker;
         return new PacketPlayOutSpawnEntity(this.tracker, 10, entityminecartabstract.s().a());
      } else if(this.tracker instanceof EntityBoat) {
         return new PacketPlayOutSpawnEntity(this.tracker, 1);
      } else if(this.tracker instanceof IAnimal) {
         this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
         return new PacketPlayOutSpawnEntityLiving((EntityLiving)this.tracker);
      } else if(this.tracker instanceof EntityFishingHook) {
         EntityHuman entityhuman = ((EntityFishingHook)this.tracker).owner;
         return new PacketPlayOutSpawnEntity(this.tracker, 90, entityhuman != null?entityhuman.getId():this.tracker.getId());
      } else if(this.tracker instanceof EntityArrow) {
         Entity entity = ((EntityArrow)this.tracker).shooter;
         return new PacketPlayOutSpawnEntity(this.tracker, 60, entity != null?entity.getId():this.tracker.getId());
      } else if(this.tracker instanceof EntitySnowball) {
         return new PacketPlayOutSpawnEntity(this.tracker, 61);
      } else if(this.tracker instanceof EntityPotion) {
         return new PacketPlayOutSpawnEntity(this.tracker, 73, ((EntityPotion)this.tracker).getPotionValue());
      } else if(this.tracker instanceof EntityThrownExpBottle) {
         return new PacketPlayOutSpawnEntity(this.tracker, 75);
      } else if(this.tracker instanceof EntityEnderPearl) {
         return new PacketPlayOutSpawnEntity(this.tracker, 65);
      } else if(this.tracker instanceof EntityEnderSignal) {
         return new PacketPlayOutSpawnEntity(this.tracker, 72);
      } else if(this.tracker instanceof EntityFireworks) {
         return new PacketPlayOutSpawnEntity(this.tracker, 76);
      } else if(this.tracker instanceof EntityFireball) {
         EntityFireball entityfireball = (EntityFireball)this.tracker;
         PacketPlayOutSpawnEntity packetplayoutspawnentity2 = null;
         byte b0 = 63;
         if(this.tracker instanceof EntitySmallFireball) {
            b0 = 64;
         } else if(this.tracker instanceof EntityWitherSkull) {
            b0 = 66;
         }

         if(entityfireball.shooter != null) {
            packetplayoutspawnentity2 = new PacketPlayOutSpawnEntity(this.tracker, b0, ((EntityFireball)this.tracker).shooter.getId());
         } else {
            packetplayoutspawnentity2 = new PacketPlayOutSpawnEntity(this.tracker, b0, 0);
         }

         packetplayoutspawnentity2.d((int)(entityfireball.dirX * 8000.0D));
         packetplayoutspawnentity2.e((int)(entityfireball.dirY * 8000.0D));
         packetplayoutspawnentity2.f((int)(entityfireball.dirZ * 8000.0D));
         return packetplayoutspawnentity2;
      } else if(this.tracker instanceof EntityEgg) {
         return new PacketPlayOutSpawnEntity(this.tracker, 62);
      } else if(this.tracker instanceof EntityTNTPrimed) {
         return new PacketPlayOutSpawnEntity(this.tracker, 50);
      } else if(this.tracker instanceof EntityEnderCrystal) {
         return new PacketPlayOutSpawnEntity(this.tracker, 51);
      } else if(this.tracker instanceof EntityFallingBlock) {
         EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.tracker;
         return new PacketPlayOutSpawnEntity(this.tracker, 70, Block.getCombinedId(entityfallingblock.getBlock()));
      } else if(this.tracker instanceof EntityArmorStand) {
         return new PacketPlayOutSpawnEntity(this.tracker, 78);
      } else if(this.tracker instanceof EntityPainting) {
         return new PacketPlayOutSpawnEntityPainting((EntityPainting)this.tracker);
      } else if(this.tracker instanceof EntityItemFrame) {
         EntityItemFrame entityitemframe = (EntityItemFrame)this.tracker;
         PacketPlayOutSpawnEntity packetplayoutspawnentity1 = new PacketPlayOutSpawnEntity(this.tracker, 71, entityitemframe.direction.b());
         BlockPosition blockposition1 = entityitemframe.getBlockPosition();
         packetplayoutspawnentity1.a(MathHelper.d((float)(blockposition1.getX() * 32)));
         packetplayoutspawnentity1.b(MathHelper.d((float)(blockposition1.getY() * 32)));
         packetplayoutspawnentity1.c(MathHelper.d((float)(blockposition1.getZ() * 32)));
         return packetplayoutspawnentity1;
      } else if(this.tracker instanceof EntityLeash) {
         EntityLeash entityleash = (EntityLeash)this.tracker;
         PacketPlayOutSpawnEntity packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, 77);
         BlockPosition blockposition = entityleash.getBlockPosition();
         packetplayoutspawnentity.a(MathHelper.d((float)(blockposition.getX() * 32)));
         packetplayoutspawnentity.b(MathHelper.d((float)(blockposition.getY() * 32)));
         packetplayoutspawnentity.c(MathHelper.d((float)(blockposition.getZ() * 32)));
         return packetplayoutspawnentity;
      } else if(this.tracker instanceof EntityExperienceOrb) {
         return new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb)this.tracker);
      } else {
         throw new IllegalArgumentException("Don\'t know how to add " + this.tracker.getClass() + "!");
      }
   }

   public void clear(EntityPlayer p_clear_1_) {
      AsyncCatcher.catchOp("player tracker clear");
      if(this.trackedPlayers.contains(p_clear_1_)) {
         this.trackedPlayers.remove(p_clear_1_);
         p_clear_1_.d(this.tracker);
      }

   }
}

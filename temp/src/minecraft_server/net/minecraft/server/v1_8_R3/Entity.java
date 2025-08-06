package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockCobbleWall;
import net.minecraft.server.v1_8_R3.BlockFence;
import net.minecraft.server.v1_8_R3.BlockFenceGate;
import net.minecraft.server.v1_8_R3.BlockFluids;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatHoverable;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EnchantmentProtection;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.ShapeDetector;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.TravelAgent;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftTravelAgent;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.projectiles.ProjectileSource;
import org.spigotmc.ActivationRange;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public abstract class Entity implements ICommandListener {
   private static final int CURRENT_LEVEL = 2;
   private static final AxisAlignedBB a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
   private static int entityCount;
   private int id;
   public double j;
   public boolean k;
   public Entity passenger;
   public Entity vehicle;
   public boolean attachedToPlayer;
   public World world;
   public double lastX;
   public double lastY;
   public double lastZ;
   public double locX;
   public double locY;
   public double locZ;
   public double motX;
   public double motY;
   public double motZ;
   public float yaw;
   public float pitch;
   public float lastYaw;
   public float lastPitch;
   private AxisAlignedBB boundingBox;
   public boolean onGround;
   public boolean positionChanged;
   public boolean E;
   public boolean F;
   public boolean velocityChanged;
   protected boolean H;
   private boolean g;
   public boolean dead;
   public float width;
   public float length;
   public float L;
   public float M;
   public float N;
   public float fallDistance;
   private int h;
   public double P;
   public double Q;
   public double R;
   public float S;
   public boolean noclip;
   public float U;
   protected Random random;
   public int ticksLived;
   public int maxFireTicks;
   public int fireTicks;
   public boolean inWater;
   public int noDamageTicks;
   protected boolean justCreated;
   protected boolean fireProof;
   protected DataWatcher datawatcher;
   private double ar;
   private double as;
   public boolean ad;
   public int ae;
   public int af;
   public int ag;
   public boolean ah;
   public boolean ai;
   public int portalCooldown;
   protected boolean ak;
   protected int al;
   public int dimension;
   protected BlockPosition an;
   protected Vec3D ao;
   protected EnumDirection ap;
   private boolean invulnerable;
   protected UUID uniqueID;
   private final CommandObjectiveExecutor au;
   public boolean valid;
   public ProjectileSource projectileSource;
   public boolean forceExplosionKnockback;
   public CustomTimingsHandler tickTimer = SpigotTimings.getEntityTimings(this);
   public final byte activationType = ActivationRange.initializeEntityActivationType(this);
   public final boolean defaultActivationState;
   public long activatedTick = -2147483648L;
   public boolean fromMobSpawner;
   int numCollisions = 0;
   protected CraftEntity bukkitEntity;

   static boolean isLevelAtLeast(NBTTagCompound p_isLevelAtLeast_0_, int p_isLevelAtLeast_1_) {
      return p_isLevelAtLeast_0_.hasKey("Bukkit.updateLevel") && p_isLevelAtLeast_0_.getInt("Bukkit.updateLevel") >= p_isLevelAtLeast_1_;
   }

   public boolean isAddedToChunk() {
      return this.ad;
   }

   public void inactiveTick() {
   }

   public int getId() {
      return this.id;
   }

   public void d(int p_d_1_) {
      this.id = p_d_1_;
   }

   public void G() {
      this.die();
   }

   public Entity(World p_i109_1_) {
      this.id = entityCount++;
      this.j = 1.0D;
      this.boundingBox = a;
      this.width = 0.6F;
      this.length = 1.8F;
      this.h = 1;
      this.random = new Random();
      this.maxFireTicks = 1;
      this.justCreated = true;
      this.uniqueID = MathHelper.a(this.random);
      this.au = new CommandObjectiveExecutor();
      this.world = p_i109_1_;
      this.setPosition(0.0D, 0.0D, 0.0D);
      if(p_i109_1_ != null) {
         this.dimension = p_i109_1_.worldProvider.getDimension();
         this.defaultActivationState = ActivationRange.initializeEntityActivationState(this, p_i109_1_.spigotConfig);
      } else {
         this.defaultActivationState = false;
      }

      this.datawatcher = new DataWatcher(this);
      this.datawatcher.a(0, Byte.valueOf((byte)0));
      this.datawatcher.a(1, Short.valueOf((short)300));
      this.datawatcher.a(3, Byte.valueOf((byte)0));
      this.datawatcher.a(2, "");
      this.datawatcher.a(4, Byte.valueOf((byte)0));
      this.h();
   }

   protected abstract void h();

   public DataWatcher getDataWatcher() {
      return this.datawatcher;
   }

   public boolean equals(Object p_equals_1_) {
      return p_equals_1_ instanceof Entity?((Entity)p_equals_1_).id == this.id:false;
   }

   public int hashCode() {
      return this.id;
   }

   public void die() {
      this.dead = true;
   }

   public void setSize(float p_setSize_1_, float p_setSize_2_) {
      if(p_setSize_1_ != this.width || p_setSize_2_ != this.length) {
         float f = this.width;
         this.width = p_setSize_1_;
         this.length = p_setSize_2_;
         this.a(new AxisAlignedBB(this.getBoundingBox().a, this.getBoundingBox().b, this.getBoundingBox().c, this.getBoundingBox().a + (double)this.width, this.getBoundingBox().b + (double)this.length, this.getBoundingBox().c + (double)this.width));
         if(this.width > f && !this.justCreated && !this.world.isClientSide) {
            this.move((double)(f - this.width), 0.0D, (double)(f - this.width));
         }
      }

   }

   protected void setYawPitch(float p_setYawPitch_1_, float p_setYawPitch_2_) {
      if(Float.isNaN(p_setYawPitch_1_)) {
         p_setYawPitch_1_ = 0.0F;
      }

      if(p_setYawPitch_1_ == Float.POSITIVE_INFINITY || p_setYawPitch_1_ == Float.NEGATIVE_INFINITY) {
         if(this instanceof EntityPlayer) {
            this.world.getServer().getLogger().warning(this.getName() + " was caught trying to crash the server with an invalid yaw");
            ((CraftPlayer)this.getBukkitEntity()).kickPlayer("Infinite yaw (Hacking?)");
         }

         p_setYawPitch_1_ = 0.0F;
      }

      if(Float.isNaN(p_setYawPitch_2_)) {
         p_setYawPitch_2_ = 0.0F;
      }

      if(p_setYawPitch_2_ == Float.POSITIVE_INFINITY || p_setYawPitch_2_ == Float.NEGATIVE_INFINITY) {
         if(this instanceof EntityPlayer) {
            this.world.getServer().getLogger().warning(this.getName() + " was caught trying to crash the server with an invalid pitch");
            ((CraftPlayer)this.getBukkitEntity()).kickPlayer("Infinite pitch (Hacking?)");
         }

         p_setYawPitch_2_ = 0.0F;
      }

      this.yaw = p_setYawPitch_1_ % 360.0F;
      this.pitch = p_setYawPitch_2_ % 360.0F;
   }

   public void setPosition(double p_setPosition_1_, double p_setPosition_3_, double p_setPosition_5_) {
      this.locX = p_setPosition_1_;
      this.locY = p_setPosition_3_;
      this.locZ = p_setPosition_5_;
      float f = this.width / 2.0F;
      float f1 = this.length;
      this.a(new AxisAlignedBB(p_setPosition_1_ - (double)f, p_setPosition_3_, p_setPosition_5_ - (double)f, p_setPosition_1_ + (double)f, p_setPosition_3_ + (double)f1, p_setPosition_5_ + (double)f));
   }

   public void t_() {
      this.K();
   }

   public void K() {
      this.world.methodProfiler.a("entityBaseTick");
      if(this.vehicle != null && this.vehicle.dead) {
         this.vehicle = null;
      }

      this.L = this.M;
      this.lastX = this.locX;
      this.lastY = this.locY;
      this.lastZ = this.locZ;
      this.lastPitch = this.pitch;
      this.lastYaw = this.yaw;
      if(!this.world.isClientSide && this.world instanceof WorldServer) {
         this.world.methodProfiler.a("portal");
         MinecraftServer minecraftserver = ((WorldServer)this.world).getMinecraftServer();
         int i = this.L();
         if(this.ak) {
            if(this.vehicle == null && this.al++ >= i) {
               this.al = i;
               this.portalCooldown = this.aq();
               byte b0;
               if(this.world.worldProvider.getDimension() == -1) {
                  b0 = 0;
               } else {
                  b0 = -1;
               }

               this.c(b0);
            }

            this.ak = false;
         } else {
            if(this.al > 0) {
               this.al -= 4;
            }

            if(this.al < 0) {
               this.al = 0;
            }
         }

         if(this.portalCooldown > 0) {
            --this.portalCooldown;
         }

         this.world.methodProfiler.b();
      }

      this.Y();
      this.W();
      if(this.world.isClientSide) {
         this.fireTicks = 0;
      } else if(this.fireTicks > 0) {
         if(this.fireProof) {
            this.fireTicks -= 4;
            if(this.fireTicks < 0) {
               this.fireTicks = 0;
            }
         } else {
            if(this.fireTicks % 20 == 0) {
               this.damageEntity(DamageSource.BURN, 1.0F);
            }

            --this.fireTicks;
         }
      }

      if(this.ab()) {
         this.burnFromLava();
         this.fallDistance *= 0.5F;
      }

      if(this.locY < -64.0D) {
         this.O();
      }

      if(!this.world.isClientSide) {
         this.b(0, this.fireTicks > 0);
      }

      this.justCreated = false;
      this.world.methodProfiler.b();
   }

   public int L() {
      return 0;
   }

   protected void burnFromLava() {
      if(!this.fireProof) {
         this.damageEntity(DamageSource.LAVA, 4.0F);
         if(this instanceof EntityLiving) {
            if(this.fireTicks <= 0) {
               org.bukkit.block.Block block = null;
               org.bukkit.entity.Entity entity = this.getBukkitEntity();
               EntityCombustEvent entitycombustevent = new EntityCombustByBlockEvent(block, entity, 15);
               this.world.getServer().getPluginManager().callEvent(entitycombustevent);
               if(!entitycombustevent.isCancelled()) {
                  this.setOnFire(entitycombustevent.getDuration());
               }
            } else {
               this.setOnFire(15);
            }

            return;
         }

         this.setOnFire(15);
      }

   }

   public void setOnFire(int p_setOnFire_1_) {
      int i = p_setOnFire_1_ * 20;
      i = EnchantmentProtection.a(this, i);
      if(this.fireTicks < i) {
         this.fireTicks = i;
      }

   }

   public void extinguish() {
      this.fireTicks = 0;
   }

   protected void O() {
      this.die();
   }

   public boolean c(double p_c_1_, double p_c_3_, double p_c_5_) {
      AxisAlignedBB axisalignedbb = this.getBoundingBox().c(p_c_1_, p_c_3_, p_c_5_);
      return this.b(axisalignedbb);
   }

   private boolean b(AxisAlignedBB p_b_1_) {
      return this.world.getCubes(this, p_b_1_).isEmpty() && !this.world.containsLiquid(p_b_1_);
   }

   public void move(double p_move_1_, double p_move_3_, double p_move_5_) {
      SpigotTimings.entityMoveTimer.startTiming();
      if(this.noclip) {
         this.a(this.getBoundingBox().c(p_move_1_, p_move_3_, p_move_5_));
         this.recalcPosition();
      } else {
         try {
            this.checkBlockCollisions();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being checked for collision");
            this.appendEntityCrashDetails(crashreportsystemdetails);
            throw new ReportedException(crashreport);
         }

         if(p_move_1_ == 0.0D && p_move_3_ == 0.0D && p_move_5_ == 0.0D && this.vehicle == null && this.passenger == null) {
            return;
         }

         this.world.methodProfiler.a("move");
         double d0 = this.locX;
         double d1 = this.locY;
         double d2 = this.locZ;
         if(this.H) {
            this.H = false;
            p_move_1_ *= 0.25D;
            p_move_3_ *= 0.05000000074505806D;
            p_move_5_ *= 0.25D;
            this.motX = 0.0D;
            this.motY = 0.0D;
            this.motZ = 0.0D;
         }

         double d3 = p_move_1_;
         double d4 = p_move_3_;
         double d5 = p_move_5_;
         boolean flag = this.onGround && this.isSneaking() && this instanceof EntityHuman;
         if(flag) {
            double d6;
            for(d6 = 0.05D; p_move_1_ != 0.0D && this.world.getCubes(this, this.getBoundingBox().c(p_move_1_, -1.0D, 0.0D)).isEmpty(); d3 = p_move_1_) {
               if(p_move_1_ < d6 && p_move_1_ >= -d6) {
                  p_move_1_ = 0.0D;
               } else if(p_move_1_ > 0.0D) {
                  p_move_1_ -= d6;
               } else {
                  p_move_1_ += d6;
               }
            }

            for(; p_move_5_ != 0.0D && this.world.getCubes(this, this.getBoundingBox().c(0.0D, -1.0D, p_move_5_)).isEmpty(); d5 = p_move_5_) {
               if(p_move_5_ < d6 && p_move_5_ >= -d6) {
                  p_move_5_ = 0.0D;
               } else if(p_move_5_ > 0.0D) {
                  p_move_5_ -= d6;
               } else {
                  p_move_5_ += d6;
               }
            }

            for(; p_move_1_ != 0.0D && p_move_5_ != 0.0D && this.world.getCubes(this, this.getBoundingBox().c(p_move_1_, -1.0D, p_move_5_)).isEmpty(); d5 = p_move_5_) {
               if(p_move_1_ < d6 && p_move_1_ >= -d6) {
                  p_move_1_ = 0.0D;
               } else if(p_move_1_ > 0.0D) {
                  p_move_1_ -= d6;
               } else {
                  p_move_1_ += d6;
               }

               d3 = p_move_1_;
               if(p_move_5_ < d6 && p_move_5_ >= -d6) {
                  p_move_5_ = 0.0D;
               } else if(p_move_5_ > 0.0D) {
                  p_move_5_ -= d6;
               } else {
                  p_move_5_ += d6;
               }
            }
         }

         List list = this.world.getCubes(this, this.getBoundingBox().a(p_move_1_, p_move_3_, p_move_5_));
         AxisAlignedBB axisalignedbb = this.getBoundingBox();

         for(AxisAlignedBB axisalignedbb1 : list) {
            p_move_3_ = axisalignedbb1.b(this.getBoundingBox(), p_move_3_);
         }

         this.a(this.getBoundingBox().c(0.0D, p_move_3_, 0.0D));
         boolean flag1 = this.onGround || d4 != p_move_3_ && d4 < 0.0D;

         for(AxisAlignedBB axisalignedbb2 : list) {
            p_move_1_ = axisalignedbb2.a(this.getBoundingBox(), p_move_1_);
         }

         this.a(this.getBoundingBox().c(p_move_1_, 0.0D, 0.0D));

         for(AxisAlignedBB axisalignedbb13 : list) {
            p_move_5_ = axisalignedbb13.c(this.getBoundingBox(), p_move_5_);
         }

         this.a(this.getBoundingBox().c(0.0D, 0.0D, p_move_5_));
         if(this.S > 0.0F && flag1 && (d3 != p_move_1_ || d5 != p_move_5_)) {
            double d7 = p_move_1_;
            double d8 = p_move_3_;
            double d9 = p_move_5_;
            AxisAlignedBB axisalignedbb3 = this.getBoundingBox();
            this.a(axisalignedbb);
            p_move_3_ = (double)this.S;
            List list1 = this.world.getCubes(this, this.getBoundingBox().a(d3, p_move_3_, d5));
            AxisAlignedBB axisalignedbb4 = this.getBoundingBox();
            AxisAlignedBB axisalignedbb5 = axisalignedbb4.a(d3, 0.0D, d5);
            double d10 = p_move_3_;

            for(AxisAlignedBB axisalignedbb6 : list1) {
               d10 = axisalignedbb6.b(axisalignedbb5, d10);
            }

            axisalignedbb4 = axisalignedbb4.c(0.0D, d10, 0.0D);
            double d11 = d3;

            for(AxisAlignedBB axisalignedbb7 : list1) {
               d11 = axisalignedbb7.a(axisalignedbb4, d11);
            }

            axisalignedbb4 = axisalignedbb4.c(d11, 0.0D, 0.0D);
            double d12 = d5;

            for(AxisAlignedBB axisalignedbb8 : list1) {
               d12 = axisalignedbb8.c(axisalignedbb4, d12);
            }

            axisalignedbb4 = axisalignedbb4.c(0.0D, 0.0D, d12);
            AxisAlignedBB axisalignedbb14 = this.getBoundingBox();
            double d13 = p_move_3_;

            for(AxisAlignedBB axisalignedbb9 : list1) {
               d13 = axisalignedbb9.b(axisalignedbb14, d13);
            }

            axisalignedbb14 = axisalignedbb14.c(0.0D, d13, 0.0D);
            double d14 = d3;

            for(AxisAlignedBB axisalignedbb10 : list1) {
               d14 = axisalignedbb10.a(axisalignedbb14, d14);
            }

            axisalignedbb14 = axisalignedbb14.c(d14, 0.0D, 0.0D);
            double d15 = d5;

            for(AxisAlignedBB axisalignedbb11 : list1) {
               d15 = axisalignedbb11.c(axisalignedbb14, d15);
            }

            axisalignedbb14 = axisalignedbb14.c(0.0D, 0.0D, d15);
            double d16 = d11 * d11 + d12 * d12;
            double d17 = d14 * d14 + d15 * d15;
            if(d16 > d17) {
               p_move_1_ = d11;
               p_move_5_ = d12;
               p_move_3_ = -d10;
               this.a(axisalignedbb4);
            } else {
               p_move_1_ = d14;
               p_move_5_ = d15;
               p_move_3_ = -d13;
               this.a(axisalignedbb14);
            }

            for(AxisAlignedBB axisalignedbb12 : list1) {
               p_move_3_ = axisalignedbb12.b(this.getBoundingBox(), p_move_3_);
            }

            this.a(this.getBoundingBox().c(0.0D, p_move_3_, 0.0D));
            if(d7 * d7 + d9 * d9 >= p_move_1_ * p_move_1_ + p_move_5_ * p_move_5_) {
               p_move_1_ = d7;
               p_move_3_ = d8;
               p_move_5_ = d9;
               this.a(axisalignedbb3);
            }
         }

         this.world.methodProfiler.b();
         this.world.methodProfiler.a("rest");
         this.recalcPosition();
         this.positionChanged = d3 != p_move_1_ || d5 != p_move_5_;
         this.E = d4 != p_move_3_;
         this.onGround = this.E && d4 < 0.0D;
         this.F = this.positionChanged || this.E;
         int i = MathHelper.floor(this.locX);
         int j = MathHelper.floor(this.locY - 0.20000000298023224D);
         int k = MathHelper.floor(this.locZ);
         BlockPosition blockposition = new BlockPosition(i, j, k);
         Block block = this.world.getType(blockposition).getBlock();
         if(block.getMaterial() == Material.AIR) {
            Block block1 = this.world.getType(blockposition.down()).getBlock();
            if(block1 instanceof BlockFence || block1 instanceof BlockCobbleWall || block1 instanceof BlockFenceGate) {
               block = block1;
               blockposition = blockposition.down();
            }
         }

         this.a(p_move_3_, this.onGround, block, blockposition);
         if(d3 != p_move_1_) {
            this.motX = 0.0D;
         }

         if(d5 != p_move_5_) {
            this.motZ = 0.0D;
         }

         if(d4 != p_move_3_) {
            block.a(this.world, this);
         }

         if(this.positionChanged && this.getBukkitEntity() instanceof Vehicle) {
            Vehicle vehicle = (Vehicle)this.getBukkitEntity();
            org.bukkit.block.Block block2 = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
            if(d3 > p_move_1_) {
               block2 = block2.getRelative(BlockFace.EAST);
            } else if(d3 < p_move_1_) {
               block2 = block2.getRelative(BlockFace.WEST);
            } else if(d5 > p_move_5_) {
               block2 = block2.getRelative(BlockFace.SOUTH);
            } else if(d5 < p_move_5_) {
               block2 = block2.getRelative(BlockFace.NORTH);
            }

            VehicleBlockCollisionEvent vehicleblockcollisionevent = new VehicleBlockCollisionEvent(vehicle, block2);
            this.world.getServer().getPluginManager().callEvent(vehicleblockcollisionevent);
         }

         if(this.s_() && !flag && this.vehicle == null) {
            double d18 = this.locX - d0;
            double d19 = this.locY - d1;
            double d20 = this.locZ - d2;
            if(block != Blocks.LADDER) {
               d19 = 0.0D;
            }

            if(block != null) {
               ;
            }

            this.M = (float)((double)this.M + (double)MathHelper.sqrt(d18 * d18 + d20 * d20) * 0.6D);
            this.N = (float)((double)this.N + (double)MathHelper.sqrt(d18 * d18 + d19 * d19 + d20 * d20) * 0.6D);
            if(this.N > (float)this.h && block.getMaterial() != Material.AIR) {
               this.h = (int)this.N + 1;
               if(this.V()) {
                  float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.35F;
                  if(f > 1.0F) {
                     f = 1.0F;
                  }

                  this.makeSound(this.P(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
               }

               this.a(blockposition, block);
               block.a(this.world, blockposition, this);
            }
         }

         boolean flag2 = this.U();
         if(this.world.e(this.getBoundingBox().shrink(0.001D, 0.001D, 0.001D))) {
            this.burn(1.0F);
            if(!flag2) {
               ++this.fireTicks;
               if(this.fireTicks <= 0) {
                  EntityCombustEvent entitycombustevent = new EntityCombustEvent(this.getBukkitEntity(), 8);
                  this.world.getServer().getPluginManager().callEvent(entitycombustevent);
                  if(!entitycombustevent.isCancelled()) {
                     this.setOnFire(entitycombustevent.getDuration());
                  }
               } else {
                  this.setOnFire(8);
               }
            }
         } else if(this.fireTicks <= 0) {
            this.fireTicks = -this.maxFireTicks;
         }

         if(flag2 && this.fireTicks > 0) {
            this.makeSound("random.fizz", 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            this.fireTicks = -this.maxFireTicks;
         }

         this.world.methodProfiler.b();
      }

      SpigotTimings.entityMoveTimer.stopTiming();
   }

   private void recalcPosition() {
      this.locX = (this.getBoundingBox().a + this.getBoundingBox().d) / 2.0D;
      this.locY = this.getBoundingBox().b;
      this.locZ = (this.getBoundingBox().c + this.getBoundingBox().f) / 2.0D;
   }

   protected String P() {
      return "game.neutral.swim";
   }

   protected void checkBlockCollisions() {
      BlockPosition blockposition = new BlockPosition(this.getBoundingBox().a + 0.001D, this.getBoundingBox().b + 0.001D, this.getBoundingBox().c + 0.001D);
      BlockPosition blockposition1 = new BlockPosition(this.getBoundingBox().d - 0.001D, this.getBoundingBox().e - 0.001D, this.getBoundingBox().f - 0.001D);
      if(this.world.areChunksLoadedBetween(blockposition, blockposition1)) {
         for(int i = blockposition.getX(); i <= blockposition1.getX(); ++i) {
            for(int j = blockposition.getY(); j <= blockposition1.getY(); ++j) {
               for(int k = blockposition.getZ(); k <= blockposition1.getZ(); ++k) {
                  BlockPosition blockposition2 = new BlockPosition(i, j, k);
                  IBlockData iblockdata = this.world.getType(blockposition2);

                  try {
                     iblockdata.getBlock().a(this.world, blockposition2, iblockdata, this);
                  } catch (Throwable throwable) {
                     CrashReport crashreport = CrashReport.a(throwable, "Colliding entity with block");
                     CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being collided with");
                     CrashReportSystemDetails.a(crashreportsystemdetails, blockposition2, iblockdata);
                     throw new ReportedException(crashreport);
                  }
               }
            }
         }
      }

   }

   protected void a(BlockPosition p_a_1_, Block p_a_2_) {
      Block.StepSound block$stepsound = p_a_2_.stepSound;
      if(this.world.getType(p_a_1_.up()).getBlock() == Blocks.SNOW_LAYER) {
         block$stepsound = Blocks.SNOW_LAYER.stepSound;
         this.makeSound(block$stepsound.getStepSound(), block$stepsound.getVolume1() * 0.15F, block$stepsound.getVolume2());
      } else if(!p_a_2_.getMaterial().isLiquid()) {
         this.makeSound(block$stepsound.getStepSound(), block$stepsound.getVolume1() * 0.15F, block$stepsound.getVolume2());
      }

   }

   public void makeSound(String p_makeSound_1_, float p_makeSound_2_, float p_makeSound_3_) {
      if(!this.R()) {
         this.world.makeSound(this, p_makeSound_1_, p_makeSound_2_, p_makeSound_3_);
      }

   }

   public boolean R() {
      return this.datawatcher.getByte(4) == 1;
   }

   public void b(boolean p_b_1_) {
      this.datawatcher.watch(4, Byte.valueOf((byte)(p_b_1_?1:0)));
   }

   protected boolean s_() {
      return true;
   }

   protected void a(double p_a_1_, boolean p_a_3_, Block p_a_4_, BlockPosition p_a_5_) {
      if(p_a_3_) {
         if(this.fallDistance > 0.0F) {
            if(p_a_4_ != null) {
               p_a_4_.fallOn(this.world, p_a_5_, this, this.fallDistance);
            } else {
               this.e(this.fallDistance, 1.0F);
            }

            this.fallDistance = 0.0F;
         }
      } else if(p_a_1_ < 0.0D) {
         this.fallDistance = (float)((double)this.fallDistance - p_a_1_);
      }

   }

   public AxisAlignedBB S() {
      return null;
   }

   protected void burn(float p_burn_1_) {
      if(!this.fireProof) {
         this.damageEntity(DamageSource.FIRE, p_burn_1_);
      }

   }

   public final boolean isFireProof() {
      return this.fireProof;
   }

   public void e(float p_e_1_, float p_e_2_) {
      if(this.passenger != null) {
         this.passenger.e(p_e_1_, p_e_2_);
      }

   }

   public boolean U() {
      return this.inWater || this.world.isRainingAt(new BlockPosition(this.locX, this.locY, this.locZ)) || this.world.isRainingAt(new BlockPosition(this.locX, this.locY + (double)this.length, this.locZ));
   }

   public boolean V() {
      return this.inWater;
   }

   public boolean W() {
      if(this.world.a(this.getBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D, 0.001D, 0.001D), Material.WATER, this)) {
         if(!this.inWater && !this.justCreated) {
            this.X();
         }

         this.fallDistance = 0.0F;
         this.inWater = true;
         this.fireTicks = 0;
      } else {
         this.inWater = false;
      }

      return this.inWater;
   }

   protected void X() {
      float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.2F;
      if(f > 1.0F) {
         f = 1.0F;
      }

      this.makeSound(this.aa(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
      float f1 = (float)MathHelper.floor(this.getBoundingBox().b);

      for(int i = 0; (float)i < 1.0F + this.width * 20.0F; ++i) {
         float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
         float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
         this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + (double)f2, (double)(f1 + 1.0F), this.locZ + (double)f3, this.motX, this.motY - (double)(this.random.nextFloat() * 0.2F), this.motZ, new int[0]);
      }

      for(int jx = 0; (float)jx < 1.0F + this.width * 20.0F; ++jx) {
         float f4 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
         float f5 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
         this.world.addParticle(EnumParticle.WATER_SPLASH, this.locX + (double)f4, (double)(f1 + 1.0F), this.locZ + (double)f5, this.motX, this.motY, this.motZ, new int[0]);
      }

   }

   public void Y() {
      if(this.isSprinting() && !this.V()) {
         this.Z();
      }

   }

   protected void Z() {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.locY - 0.20000000298023224D);
      int k = MathHelper.floor(this.locZ);
      BlockPosition blockposition = new BlockPosition(i, j, k);
      IBlockData iblockdata = this.world.getType(blockposition);
      Block block = iblockdata.getBlock();
      if(block.b() != -1) {
         this.world.addParticle(EnumParticle.BLOCK_CRACK, this.locX + ((double)this.random.nextFloat() - 0.5D) * (double)this.width, this.getBoundingBox().b + 0.1D, this.locZ + ((double)this.random.nextFloat() - 0.5D) * (double)this.width, -this.motX * 4.0D, 1.5D, -this.motZ * 4.0D, new int[]{Block.getCombinedId(iblockdata)});
      }

   }

   protected String aa() {
      return "game.neutral.swim.splash";
   }

   public boolean a(Material p_a_1_) {
      double d0 = this.locY + (double)this.getHeadHeight();
      BlockPosition blockposition = new BlockPosition(this.locX, d0, this.locZ);
      IBlockData iblockdata = this.world.getType(blockposition);
      Block block = iblockdata.getBlock();
      if(block.getMaterial() == p_a_1_) {
         float f = BlockFluids.b(iblockdata.getBlock().toLegacyData(iblockdata)) - 0.11111111F;
         float f1 = (float)(blockposition.getY() + 1) - f;
         boolean flag = d0 < (double)f1;
         return !flag && this instanceof EntityHuman?false:flag;
      } else {
         return false;
      }
   }

   public boolean ab() {
      return this.world.a(this.getBoundingBox().grow(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA);
   }

   public void a(float p_a_1_, float p_a_2_, float p_a_3_) {
      float f = p_a_1_ * p_a_1_ + p_a_2_ * p_a_2_;
      if(f >= 1.0E-4F) {
         f = MathHelper.c(f);
         if(f < 1.0F) {
            f = 1.0F;
         }

         f = p_a_3_ / f;
         p_a_1_ = p_a_1_ * f;
         p_a_2_ = p_a_2_ * f;
         float f1 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
         float f2 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
         this.motX += (double)(p_a_1_ * f2 - p_a_2_ * f1);
         this.motZ += (double)(p_a_2_ * f2 + p_a_1_ * f1);
      }

   }

   public float c(float p_c_1_) {
      BlockPosition blockposition = new BlockPosition(this.locX, this.locY + (double)this.getHeadHeight(), this.locZ);
      return this.world.isLoaded(blockposition)?this.world.o(blockposition):0.0F;
   }

   public void spawnIn(World p_spawnIn_1_) {
      if(p_spawnIn_1_ == null) {
         this.die();
         this.world = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
      } else {
         this.world = p_spawnIn_1_;
      }
   }

   public void setLocation(double p_setLocation_1_, double p_setLocation_3_, double p_setLocation_5_, float p_setLocation_7_, float p_setLocation_8_) {
      this.lastX = this.locX = p_setLocation_1_;
      this.lastY = this.locY = p_setLocation_3_;
      this.lastZ = this.locZ = p_setLocation_5_;
      this.lastYaw = this.yaw = p_setLocation_7_;
      this.lastPitch = this.pitch = p_setLocation_8_;
      double d0 = (double)(this.lastYaw - p_setLocation_7_);
      if(d0 < -180.0D) {
         this.lastYaw += 360.0F;
      }

      if(d0 >= 180.0D) {
         this.lastYaw -= 360.0F;
      }

      this.setPosition(this.locX, this.locY, this.locZ);
      this.setYawPitch(p_setLocation_7_, p_setLocation_8_);
   }

   public void setPositionRotation(BlockPosition p_setPositionRotation_1_, float p_setPositionRotation_2_, float p_setPositionRotation_3_) {
      this.setPositionRotation((double)p_setPositionRotation_1_.getX() + 0.5D, (double)p_setPositionRotation_1_.getY(), (double)p_setPositionRotation_1_.getZ() + 0.5D, p_setPositionRotation_2_, p_setPositionRotation_3_);
   }

   public void setPositionRotation(double p_setPositionRotation_1_, double p_setPositionRotation_3_, double p_setPositionRotation_5_, float p_setPositionRotation_7_, float p_setPositionRotation_8_) {
      this.P = this.lastX = this.locX = p_setPositionRotation_1_;
      this.Q = this.lastY = this.locY = p_setPositionRotation_3_;
      this.R = this.lastZ = this.locZ = p_setPositionRotation_5_;
      this.yaw = p_setPositionRotation_7_;
      this.pitch = p_setPositionRotation_8_;
      this.setPosition(this.locX, this.locY, this.locZ);
   }

   public float g(Entity p_g_1_) {
      float f = (float)(this.locX - p_g_1_.locX);
      float f1 = (float)(this.locY - p_g_1_.locY);
      float f2 = (float)(this.locZ - p_g_1_.locZ);
      return MathHelper.c(f * f + f1 * f1 + f2 * f2);
   }

   public double e(double p_e_1_, double p_e_3_, double p_e_5_) {
      double d0 = this.locX - p_e_1_;
      double d1 = this.locY - p_e_3_;
      double d2 = this.locZ - p_e_5_;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double b(BlockPosition p_b_1_) {
      return p_b_1_.c(this.locX, this.locY, this.locZ);
   }

   public double c(BlockPosition p_c_1_) {
      return p_c_1_.d(this.locX, this.locY, this.locZ);
   }

   public double f(double p_f_1_, double p_f_3_, double p_f_5_) {
      double d0 = this.locX - p_f_1_;
      double d1 = this.locY - p_f_3_;
      double d2 = this.locZ - p_f_5_;
      return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public double h(Entity p_h_1_) {
      double d0 = this.locX - p_h_1_.locX;
      double d1 = this.locY - p_h_1_.locY;
      double d2 = this.locZ - p_h_1_.locZ;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public void d(EntityHuman p_d_1_) {
   }

   public void collide(Entity p_collide_1_) {
      if(p_collide_1_.passenger != this && p_collide_1_.vehicle != this && !p_collide_1_.noclip && !this.noclip) {
         double d0 = p_collide_1_.locX - this.locX;
         double d1 = p_collide_1_.locZ - this.locZ;
         double d2 = MathHelper.a(d0, d1);
         if(d2 >= 0.009999999776482582D) {
            d2 = (double)MathHelper.sqrt(d2);
            d0 = d0 / d2;
            d1 = d1 / d2;
            double d3 = 1.0D / d2;
            if(d3 > 1.0D) {
               d3 = 1.0D;
            }

            d0 = d0 * d3;
            d1 = d1 * d3;
            d0 = d0 * 0.05000000074505806D;
            d1 = d1 * 0.05000000074505806D;
            d0 = d0 * (double)(1.0F - this.U);
            d1 = d1 * (double)(1.0F - this.U);
            if(this.passenger == null) {
               this.g(-d0, 0.0D, -d1);
            }

            if(p_collide_1_.passenger == null) {
               p_collide_1_.g(d0, 0.0D, d1);
            }
         }
      }

   }

   public void g(double p_g_1_, double p_g_3_, double p_g_5_) {
      this.motX += p_g_1_;
      this.motY += p_g_3_;
      this.motZ += p_g_5_;
      this.ai = true;
   }

   protected void ac() {
      this.velocityChanged = true;
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      if(this.isInvulnerable(p_damageEntity_1_)) {
         return false;
      } else {
         this.ac();
         return false;
      }
   }

   public Vec3D d(float p_d_1_) {
      if(p_d_1_ == 1.0F) {
         return this.f(this.pitch, this.yaw);
      } else {
         float f = this.lastPitch + (this.pitch - this.lastPitch) * p_d_1_;
         float f1 = this.lastYaw + (this.yaw - this.lastYaw) * p_d_1_;
         return this.f(f, f1);
      }
   }

   protected final Vec3D f(float p_f_1_, float p_f_2_) {
      float f = MathHelper.cos(-p_f_2_ * 0.017453292F - 3.1415927F);
      float f1 = MathHelper.sin(-p_f_2_ * 0.017453292F - 3.1415927F);
      float f2 = -MathHelper.cos(-p_f_1_ * 0.017453292F);
      float f3 = MathHelper.sin(-p_f_1_ * 0.017453292F);
      return new Vec3D((double)(f1 * f2), (double)f3, (double)(f * f2));
   }

   public boolean ad() {
      return false;
   }

   public boolean ae() {
      return false;
   }

   public void b(Entity p_b_1_, int p_b_2_) {
   }

   public boolean c(NBTTagCompound p_c_1_) {
      String s = this.ag();
      if(!this.dead && s != null) {
         p_c_1_.setString("id", s);
         this.e(p_c_1_);
         return true;
      } else {
         return false;
      }
   }

   public boolean d(NBTTagCompound p_d_1_) {
      String s = this.ag();
      if(!this.dead && s != null && this.passenger == null) {
         p_d_1_.setString("id", s);
         this.e(p_d_1_);
         return true;
      } else {
         return false;
      }
   }

   public void e(NBTTagCompound p_e_1_) {
      try {
         p_e_1_.set("Pos", this.a(new double[]{this.locX, this.locY, this.locZ}));
         p_e_1_.set("Motion", this.a(new double[]{this.motX, this.motY, this.motZ}));
         if(Float.isNaN(this.yaw)) {
            this.yaw = 0.0F;
         }

         if(Float.isNaN(this.pitch)) {
            this.pitch = 0.0F;
         }

         p_e_1_.set("Rotation", this.a(new float[]{this.yaw, this.pitch}));
         p_e_1_.setFloat("FallDistance", this.fallDistance);
         p_e_1_.setShort("Fire", (short)this.fireTicks);
         p_e_1_.setShort("Air", (short)this.getAirTicks());
         p_e_1_.setBoolean("OnGround", this.onGround);
         p_e_1_.setInt("Dimension", this.dimension);
         p_e_1_.setBoolean("Invulnerable", this.invulnerable);
         p_e_1_.setInt("PortalCooldown", this.portalCooldown);
         p_e_1_.setLong("UUIDMost", this.getUniqueID().getMostSignificantBits());
         p_e_1_.setLong("UUIDLeast", this.getUniqueID().getLeastSignificantBits());
         p_e_1_.setLong("WorldUUIDLeast", this.world.getDataManager().getUUID().getLeastSignificantBits());
         p_e_1_.setLong("WorldUUIDMost", this.world.getDataManager().getUUID().getMostSignificantBits());
         p_e_1_.setInt("Bukkit.updateLevel", 2);
         p_e_1_.setInt("Spigot.ticksLived", this.ticksLived);
         if(this.getCustomName() != null && this.getCustomName().length() > 0) {
            p_e_1_.setString("CustomName", this.getCustomName());
            p_e_1_.setBoolean("CustomNameVisible", this.getCustomNameVisible());
         }

         this.au.b(p_e_1_);
         if(this.R()) {
            p_e_1_.setBoolean("Silent", this.R());
         }

         this.b(p_e_1_);
         if(this.vehicle != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            if(this.vehicle.c(nbttagcompound)) {
               p_e_1_.set("Riding", nbttagcompound);
            }
         }

      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");
         this.appendEntityCrashDetails(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   public void f(NBTTagCompound p_f_1_) {
      try {
         NBTTagList nbttaglist = p_f_1_.getList("Pos", 6);
         NBTTagList nbttaglist1 = p_f_1_.getList("Motion", 6);
         NBTTagList nbttaglist2 = p_f_1_.getList("Rotation", 5);
         this.motX = nbttaglist1.d(0);
         this.motY = nbttaglist1.d(1);
         this.motZ = nbttaglist1.d(2);
         this.lastX = this.P = this.locX = nbttaglist.d(0);
         this.lastY = this.Q = this.locY = nbttaglist.d(1);
         this.lastZ = this.R = this.locZ = nbttaglist.d(2);
         this.lastYaw = this.yaw = nbttaglist2.e(0);
         this.lastPitch = this.pitch = nbttaglist2.e(1);
         this.f(this.yaw);
         this.g(this.yaw);
         this.fallDistance = p_f_1_.getFloat("FallDistance");
         this.fireTicks = p_f_1_.getShort("Fire");
         this.setAirTicks(p_f_1_.getShort("Air"));
         this.onGround = p_f_1_.getBoolean("OnGround");
         this.dimension = p_f_1_.getInt("Dimension");
         this.invulnerable = p_f_1_.getBoolean("Invulnerable");
         this.portalCooldown = p_f_1_.getInt("PortalCooldown");
         if(p_f_1_.hasKeyOfType("UUIDMost", 4) && p_f_1_.hasKeyOfType("UUIDLeast", 4)) {
            this.uniqueID = new UUID(p_f_1_.getLong("UUIDMost"), p_f_1_.getLong("UUIDLeast"));
         } else if(p_f_1_.hasKeyOfType("UUID", 8)) {
            this.uniqueID = UUID.fromString(p_f_1_.getString("UUID"));
         }

         this.setPosition(this.locX, this.locY, this.locZ);
         this.setYawPitch(this.yaw, this.pitch);
         if(p_f_1_.hasKeyOfType("CustomName", 8) && p_f_1_.getString("CustomName").length() > 0) {
            this.setCustomName(p_f_1_.getString("CustomName"));
         }

         this.setCustomNameVisible(p_f_1_.getBoolean("CustomNameVisible"));
         this.au.a(p_f_1_);
         this.b(p_f_1_.getBoolean("Silent"));
         this.a(p_f_1_);
         if(this.af()) {
            this.setPosition(this.locX, this.locY, this.locZ);
         }

         if(this instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving)this;
            this.ticksLived = p_f_1_.getInt("Spigot.ticksLived");
            if(entityliving instanceof EntityTameableAnimal && !isLevelAtLeast(p_f_1_, 2) && !p_f_1_.getBoolean("PersistenceRequired")) {
               EntityInsentient entityinsentient = (EntityInsentient)entityliving;
               entityinsentient.persistent = !entityinsentient.isTypeNotPersistent();
            }
         }

         if(!(this.getBukkitEntity() instanceof Vehicle)) {
            if(Math.abs(this.motX) > 10.0D) {
               this.motX = 0.0D;
            }

            if(Math.abs(this.motY) > 10.0D) {
               this.motY = 0.0D;
            }

            if(Math.abs(this.motZ) > 10.0D) {
               this.motZ = 0.0D;
            }
         }

         if(this instanceof EntityPlayer) {
            Server server = Bukkit.getServer();
            org.bukkit.World world = null;
            String s = p_f_1_.getString("world");
            if(p_f_1_.hasKey("WorldUUIDMost") && p_f_1_.hasKey("WorldUUIDLeast")) {
               UUID uuid = new UUID(p_f_1_.getLong("WorldUUIDMost"), p_f_1_.getLong("WorldUUIDLeast"));
               world = server.getWorld(uuid);
            } else {
               world = server.getWorld(s);
            }

            if(world == null) {
               EntityPlayer entityplayer = (EntityPlayer)this;
               world = ((CraftServer)server).getServer().getWorldServer(entityplayer.dimension).getWorld();
            }

            this.spawnIn(world == null?null:((CraftWorld)world).getHandle());
         }

      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being loaded");
         this.appendEntityCrashDetails(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   protected boolean af() {
      return true;
   }

   protected final String ag() {
      return EntityTypes.b(this);
   }

   protected abstract void a(NBTTagCompound var1);

   protected abstract void b(NBTTagCompound var1);

   public void ah() {
   }

   protected NBTTagList a(double... p_a_1_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(double d0 : p_a_1_) {
         nbttaglist.add(new NBTTagDouble(d0));
      }

      return nbttaglist;
   }

   protected NBTTagList a(float... p_a_1_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(float f : p_a_1_) {
         nbttaglist.add(new NBTTagFloat(f));
      }

      return nbttaglist;
   }

   public EntityItem a(Item p_a_1_, int p_a_2_) {
      return this.a(p_a_1_, p_a_2_, 0.0F);
   }

   public EntityItem a(Item p_a_1_, int p_a_2_, float p_a_3_) {
      return this.a(new ItemStack(p_a_1_, p_a_2_, 0), p_a_3_);
   }

   public EntityItem a(ItemStack p_a_1_, float p_a_2_) {
      if(p_a_1_.count != 0 && p_a_1_.getItem() != null) {
         if(this instanceof EntityLiving && ((EntityLiving)this).drops != null) {
            ((EntityLiving)this).drops.add(CraftItemStack.asBukkitCopy(p_a_1_));
            return null;
         } else {
            EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + (double)p_a_2_, this.locZ, p_a_1_);
            entityitem.p();
            this.world.addEntity(entityitem);
            return entityitem;
         }
      } else {
         return null;
      }
   }

   public boolean isAlive() {
      return !this.dead;
   }

   public boolean inBlock() {
      if(this.noclip) {
         return false;
      } else {
         BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

         for(int i = 0; i < 8; ++i) {
            int j = MathHelper.floor(this.locY + (double)(((float)((i >> 0) % 2) - 0.5F) * 0.1F) + (double)this.getHeadHeight());
            int k = MathHelper.floor(this.locX + (double)(((float)((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
            int l = MathHelper.floor(this.locZ + (double)(((float)((i >> 2) % 2) - 0.5F) * this.width * 0.8F));
            if(blockposition$mutableblockposition.getX() != k || blockposition$mutableblockposition.getY() != j || blockposition$mutableblockposition.getZ() != l) {
               blockposition$mutableblockposition.c(k, j, l);
               if(this.world.getType(blockposition$mutableblockposition).getBlock().w()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean e(EntityHuman p_e_1_) {
      return false;
   }

   public AxisAlignedBB j(Entity p_j_1_) {
      return null;
   }

   public void ak() {
      if(this.vehicle.dead) {
         this.vehicle = null;
      } else {
         this.motX = 0.0D;
         this.motY = 0.0D;
         this.motZ = 0.0D;
         this.t_();
         if(this.vehicle != null) {
            this.vehicle.al();
            this.as += (double)(this.vehicle.yaw - this.vehicle.lastYaw);

            for(this.ar += (double)(this.vehicle.pitch - this.vehicle.lastPitch); this.as >= 180.0D; this.as -= 360.0D) {
               ;
            }

            while(this.as < -180.0D) {
               this.as += 360.0D;
            }

            while(this.ar >= 180.0D) {
               this.ar -= 360.0D;
            }

            while(this.ar < -180.0D) {
               this.ar += 360.0D;
            }

            double d0 = this.as * 0.5D;
            double d1 = this.ar * 0.5D;
            float f = 10.0F;
            if(d0 > (double)f) {
               d0 = (double)f;
            }

            if(d0 < (double)(-f)) {
               d0 = (double)(-f);
            }

            if(d1 > (double)f) {
               d1 = (double)f;
            }

            if(d1 < (double)(-f)) {
               d1 = (double)(-f);
            }

            this.as -= d0;
            this.ar -= d1;
         }
      }

   }

   public void al() {
      if(this.passenger != null) {
         this.passenger.setPosition(this.locX, this.locY + this.an() + this.passenger.am(), this.locZ);
      }

   }

   public double am() {
      return 0.0D;
   }

   public double an() {
      return (double)this.length * 0.75D;
   }

   public CraftEntity getBukkitEntity() {
      if(this.bukkitEntity == null) {
         this.bukkitEntity = CraftEntity.getEntity(this.world.getServer(), this);
      }

      return this.bukkitEntity;
   }

   public void mount(Entity p_mount_1_) {
      Entity entity = this.vehicle;
      Entity entity1 = this.vehicle == null?null:this.vehicle.passenger;
      PluginManager pluginmanager = Bukkit.getPluginManager();
      this.getBukkitEntity();
      this.ar = 0.0D;
      this.as = 0.0D;
      if(p_mount_1_ == null) {
         if(this.vehicle != null) {
            if(this.bukkitEntity instanceof LivingEntity && this.vehicle.getBukkitEntity() instanceof Vehicle) {
               VehicleExitEvent vehicleexitevent = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
               pluginmanager.callEvent(vehicleexitevent);
               if(vehicleexitevent.isCancelled() || this.vehicle != entity) {
                  return;
               }
            }

            pluginmanager.callEvent(new EntityDismountEvent(this.getBukkitEntity(), this.vehicle.getBukkitEntity()));
            this.setPositionRotation(this.vehicle.locX, this.vehicle.getBoundingBox().b + (double)this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
            this.vehicle.passenger = null;
         }

         this.vehicle = null;
      } else {
         if(this.bukkitEntity instanceof LivingEntity && p_mount_1_.getBukkitEntity() instanceof Vehicle && p_mount_1_.world.isChunkLoaded((int)p_mount_1_.locX >> 4, (int)p_mount_1_.locZ >> 4, true)) {
            VehicleExitEvent vehicleexitevent1 = null;
            if(this.vehicle != null && this.vehicle.getBukkitEntity() instanceof Vehicle) {
               vehicleexitevent1 = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
               pluginmanager.callEvent(vehicleexitevent1);
               if(vehicleexitevent1.isCancelled() || this.vehicle != entity || this.vehicle != null && this.vehicle.passenger != entity1) {
                  return;
               }
            }

            VehicleEnterEvent vehicleenterevent = new VehicleEnterEvent((Vehicle)p_mount_1_.getBukkitEntity(), this.bukkitEntity);
            pluginmanager.callEvent(vehicleenterevent);
            if(vehicleenterevent.isCancelled() || this.vehicle != entity || this.vehicle != null && this.vehicle.passenger != entity1) {
               if(vehicleexitevent1 != null && this.vehicle == entity && this.vehicle != null && this.vehicle.passenger == entity1) {
                  this.setPositionRotation(this.vehicle.locX, this.vehicle.getBoundingBox().b + (double)this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
                  this.vehicle.passenger = null;
                  this.vehicle = null;
               }

               return;
            }
         }

         if(p_mount_1_.world.isChunkLoaded((int)p_mount_1_.locX >> 4, (int)p_mount_1_.locZ >> 4, true)) {
            EntityMountEvent entitymountevent = new EntityMountEvent(this.getBukkitEntity(), p_mount_1_.getBukkitEntity());
            pluginmanager.callEvent(entitymountevent);
            if(entitymountevent.isCancelled()) {
               return;
            }
         }

         if(this.vehicle != null) {
            this.vehicle.passenger = null;
         }

         if(p_mount_1_ != null) {
            for(Entity entity2 = p_mount_1_.vehicle; entity2 != null; entity2 = entity2.vehicle) {
               if(entity2 == this) {
                  return;
               }
            }
         }

         this.vehicle = p_mount_1_;
         p_mount_1_.passenger = this;
      }

   }

   public float ao() {
      return 0.1F;
   }

   public Vec3D ap() {
      return null;
   }

   public void d(BlockPosition p_d_1_) {
      if(this.portalCooldown > 0) {
         this.portalCooldown = this.aq();
      } else {
         if(!this.world.isClientSide && !p_d_1_.equals(this.an)) {
            this.an = p_d_1_;
            ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = Blocks.PORTAL.f(this.world, p_d_1_);
            double d0 = shapedetector$shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X?(double)shapedetector$shapedetectorcollection.a().getZ():(double)shapedetector$shapedetectorcollection.a().getX();
            double d1 = shapedetector$shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X?this.locZ:this.locX;
            d1 = Math.abs(MathHelper.c(d1 - (double)(shapedetector$shapedetectorcollection.b().e().c() == EnumDirection.EnumAxisDirection.NEGATIVE?1:0), d0, d0 - (double)shapedetector$shapedetectorcollection.d()));
            double d2 = MathHelper.c(this.locY - 1.0D, (double)shapedetector$shapedetectorcollection.a().getY(), (double)(shapedetector$shapedetectorcollection.a().getY() - shapedetector$shapedetectorcollection.e()));
            this.ao = new Vec3D(d1, d2, 0.0D);
            this.ap = shapedetector$shapedetectorcollection.b();
         }

         this.ak = true;
      }

   }

   public int aq() {
      return 300;
   }

   public ItemStack[] getEquipment() {
      return null;
   }

   public void setEquipment(int p_setEquipment_1_, ItemStack p_setEquipment_2_) {
   }

   public boolean isBurning() {
      boolean flag = this.world != null && this.world.isClientSide;
      return !this.fireProof && (this.fireTicks > 0 || flag && this.g(0));
   }

   public boolean au() {
      return this.vehicle != null;
   }

   public boolean isSneaking() {
      return this.g(1);
   }

   public void setSneaking(boolean p_setSneaking_1_) {
      this.b(1, p_setSneaking_1_);
   }

   public boolean isSprinting() {
      return this.g(3);
   }

   public void setSprinting(boolean p_setSprinting_1_) {
      this.b(3, p_setSprinting_1_);
   }

   public boolean isInvisible() {
      return this.g(5);
   }

   public void setInvisible(boolean p_setInvisible_1_) {
      this.b(5, p_setInvisible_1_);
   }

   public void f(boolean p_f_1_) {
      this.b(4, p_f_1_);
   }

   protected boolean g(int p_g_1_) {
      return (this.datawatcher.getByte(0) & 1 << p_g_1_) != 0;
   }

   protected void b(int p_b_1_, boolean p_b_2_) {
      byte b0 = this.datawatcher.getByte(0);
      if(p_b_2_) {
         this.datawatcher.watch(0, Byte.valueOf((byte)(b0 | 1 << p_b_1_)));
      } else {
         this.datawatcher.watch(0, Byte.valueOf((byte)(b0 & ~(1 << p_b_1_))));
      }

   }

   public int getAirTicks() {
      return this.datawatcher.getShort(1);
   }

   public void setAirTicks(int p_setAirTicks_1_) {
      this.datawatcher.watch(1, Short.valueOf((short)p_setAirTicks_1_));
   }

   public void onLightningStrike(EntityLightning p_onLightningStrike_1_) {
      org.bukkit.entity.Entity entity = this.getBukkitEntity();
      org.bukkit.entity.Entity entity1 = p_onLightningStrike_1_.getBukkitEntity();
      PluginManager pluginmanager = Bukkit.getPluginManager();
      if(entity instanceof Hanging) {
         HangingBreakByEntityEvent hangingbreakbyentityevent = new HangingBreakByEntityEvent((Hanging)entity, entity1);
         PaintingBreakByEntityEvent paintingbreakbyentityevent = null;
         if(entity instanceof Painting) {
            paintingbreakbyentityevent = new PaintingBreakByEntityEvent((Painting)entity, entity1);
         }

         pluginmanager.callEvent(hangingbreakbyentityevent);
         if(paintingbreakbyentityevent != null) {
            paintingbreakbyentityevent.setCancelled(hangingbreakbyentityevent.isCancelled());
            pluginmanager.callEvent(paintingbreakbyentityevent);
         }

         if(hangingbreakbyentityevent.isCancelled() || paintingbreakbyentityevent != null && paintingbreakbyentityevent.isCancelled()) {
            return;
         }
      }

      if(!this.fireProof) {
         CraftEventFactory.entityDamage = p_onLightningStrike_1_;
         if(!this.damageEntity(DamageSource.LIGHTNING, 5.0F)) {
            CraftEventFactory.entityDamage = null;
         } else {
            ++this.fireTicks;
            if(this.fireTicks == 0) {
               EntityCombustByEntityEvent entitycombustbyentityevent = new EntityCombustByEntityEvent(entity1, entity, 8);
               pluginmanager.callEvent(entitycombustbyentityevent);
               if(!entitycombustbyentityevent.isCancelled()) {
                  this.setOnFire(entitycombustbyentityevent.getDuration());
               }
            }

         }
      }
   }

   public void a(EntityLiving p_a_1_) {
   }

   protected boolean j(double p_j_1_, double p_j_3_, double p_j_5_) {
      BlockPosition blockposition = new BlockPosition(p_j_1_, p_j_3_, p_j_5_);
      double d0 = p_j_1_ - (double)blockposition.getX();
      double d1 = p_j_3_ - (double)blockposition.getY();
      double d2 = p_j_5_ - (double)blockposition.getZ();
      List list = this.world.a(this.getBoundingBox());
      if(list.isEmpty() && !this.world.u(blockposition)) {
         return false;
      } else {
         byte b0 = 3;
         double d3 = 9999.0D;
         if(!this.world.u(blockposition.west()) && d0 < d3) {
            d3 = d0;
            b0 = 0;
         }

         if(!this.world.u(blockposition.east()) && 1.0D - d0 < d3) {
            d3 = 1.0D - d0;
            b0 = 1;
         }

         if(!this.world.u(blockposition.up()) && 1.0D - d1 < d3) {
            d3 = 1.0D - d1;
            b0 = 3;
         }

         if(!this.world.u(blockposition.north()) && d2 < d3) {
            d3 = d2;
            b0 = 4;
         }

         if(!this.world.u(blockposition.south()) && 1.0D - d2 < d3) {
            d3 = 1.0D - d2;
            b0 = 5;
         }

         float f = this.random.nextFloat() * 0.2F + 0.1F;
         if(b0 == 0) {
            this.motX = (double)(-f);
         }

         if(b0 == 1) {
            this.motX = (double)f;
         }

         if(b0 == 3) {
            this.motY = (double)f;
         }

         if(b0 == 4) {
            this.motZ = (double)(-f);
         }

         if(b0 == 5) {
            this.motZ = (double)f;
         }

         return true;
      }
   }

   public void aA() {
      this.H = true;
      this.fallDistance = 0.0F;
   }

   public String getName() {
      if(this.hasCustomName()) {
         return this.getCustomName();
      } else {
         String s = EntityTypes.b(this);
         if(s == null) {
            s = "generic";
         }

         return LocaleI18n.get("entity." + s + ".name");
      }
   }

   public Entity[] aB() {
      return null;
   }

   public boolean k(Entity p_k_1_) {
      return this == p_k_1_;
   }

   public float getHeadRotation() {
      return 0.0F;
   }

   public void f(float p_f_1_) {
   }

   public void g(float p_g_1_) {
   }

   public boolean aD() {
      return true;
   }

   public boolean l(Entity p_l_1_) {
      return false;
   }

   public String toString() {
      return String.format("%s[\'%s\'/%d, l=\'%s\', x=%.2f, y=%.2f, z=%.2f]", new Object[]{this.getClass().getSimpleName(), this.getName(), Integer.valueOf(this.id), this.world == null?"~NULL~":this.world.getWorldData().getName(), Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ)});
   }

   public boolean isInvulnerable(DamageSource p_isInvulnerable_1_) {
      return this.invulnerable && p_isInvulnerable_1_ != DamageSource.OUT_OF_WORLD && !p_isInvulnerable_1_.u();
   }

   public void m(Entity p_m_1_) {
      this.setPositionRotation(p_m_1_.locX, p_m_1_.locY, p_m_1_.locZ, p_m_1_.yaw, p_m_1_.pitch);
   }

   public void n(Entity p_n_1_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      p_n_1_.e(nbttagcompound);
      this.f(nbttagcompound);
      this.portalCooldown = p_n_1_.portalCooldown;
      this.an = p_n_1_.an;
      this.ao = p_n_1_.ao;
      this.ap = p_n_1_.ap;
   }

   public void c(int p_c_1_) {
      if(!this.world.isClientSide && !this.dead) {
         this.world.methodProfiler.a("changeDimension");
         MinecraftServer minecraftserver = MinecraftServer.getServer();
         WorldServer worldserver = null;
         if(this.dimension < 10) {
            for(WorldServer worldserver1 : minecraftserver.worlds) {
               if(worldserver1.dimension == p_c_1_) {
                  worldserver = worldserver1;
               }
            }
         }

         Location location1 = this.getBukkitEntity().getLocation();
         Location location = worldserver != null?minecraftserver.getPlayerList().calculateTarget(location1, minecraftserver.getWorldServer(p_c_1_)):null;
         boolean flag = worldserver != null && (this.dimension != 1 || worldserver.dimension != 1);
         TravelAgent travelagent = location != null?(TravelAgent)((CraftWorld)location.getWorld()).getHandle().getTravelAgent():CraftTravelAgent.DEFAULT;
         EntityPortalEvent entityportalevent = new EntityPortalEvent(this.getBukkitEntity(), location1, location, travelagent);
         entityportalevent.useTravelAgent(flag);
         entityportalevent.getEntity().getServer().getPluginManager().callEvent(entityportalevent);
         if(entityportalevent.isCancelled() || entityportalevent.getTo() == null || entityportalevent.getTo().getWorld() == null || !this.isAlive()) {
            return;
         }

         location = entityportalevent.useTravelAgent()?entityportalevent.getPortalTravelAgent().findOrCreate(entityportalevent.getTo()):entityportalevent.getTo();
         this.teleportTo(location, true);
      }

   }

   public void teleportTo(Location p_teleportTo_1_, boolean p_teleportTo_2_) {
      WorldServer worldserver = ((CraftWorld)this.getBukkitEntity().getLocation().getWorld()).getHandle();
      WorldServer worldserver1 = ((CraftWorld)p_teleportTo_1_.getWorld()).getHandle();
      int i = worldserver1.dimension;
      this.dimension = i;
      this.world.kill(this);
      this.dead = false;
      this.world.methodProfiler.a("reposition");
      boolean flag = worldserver1.chunkProviderServer.forceChunkLoad;
      worldserver1.chunkProviderServer.forceChunkLoad = true;
      worldserver1.getMinecraftServer().getPlayerList().repositionEntity(this, p_teleportTo_1_, p_teleportTo_2_);
      worldserver1.chunkProviderServer.forceChunkLoad = flag;
      this.world.methodProfiler.c("reloading");
      Entity entity = EntityTypes.createEntityByName(EntityTypes.b(this), worldserver1);
      if(entity != null) {
         entity.n(this);
         worldserver1.addEntity(entity);
         this.getBukkitEntity().setHandle(entity);
         entity.bukkitEntity = this.getBukkitEntity();
         if(this instanceof EntityInsentient) {
            ((EntityInsentient)this).unleash(true, false);
         }
      }

      this.dead = true;
      this.world.methodProfiler.b();
      worldserver.j();
      worldserver1.j();
      this.world.methodProfiler.b();
   }

   public float a(Explosion p_a_1_, World p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_) {
      return p_a_4_.getBlock().a(this);
   }

   public boolean a(Explosion p_a_1_, World p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, float p_a_5_) {
      return true;
   }

   public int aE() {
      return 3;
   }

   public Vec3D aG() {
      return this.ao;
   }

   public EnumDirection aH() {
      return this.ap;
   }

   public boolean aI() {
      return false;
   }

   public void appendEntityCrashDetails(CrashReportSystemDetails p_appendEntityCrashDetails_1_) {
      p_appendEntityCrashDetails_1_.a("Entity Type", new Callable() {
         public String a() throws Exception {
            return EntityTypes.b(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_appendEntityCrashDetails_1_.a((String)"Entity ID", Integer.valueOf(this.id));
      p_appendEntityCrashDetails_1_.a("Entity Name", new Callable() {
         public String a() throws Exception {
            return Entity.this.getName();
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_appendEntityCrashDetails_1_.a((String)"Entity\'s Exact location", String.format("%.2f, %.2f, %.2f", new Object[]{Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ)}));
      p_appendEntityCrashDetails_1_.a((String)"Entity\'s Block location", CrashReportSystemDetails.a((double)MathHelper.floor(this.locX), (double)MathHelper.floor(this.locY), (double)MathHelper.floor(this.locZ)));
      p_appendEntityCrashDetails_1_.a((String)"Entity\'s Momentum", String.format("%.2f, %.2f, %.2f", new Object[]{Double.valueOf(this.motX), Double.valueOf(this.motY), Double.valueOf(this.motZ)}));
      p_appendEntityCrashDetails_1_.a("Entity\'s Rider", new Callable() {
         public String a() throws Exception {
            return Entity.this.passenger.toString();
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_appendEntityCrashDetails_1_.a("Entity\'s Vehicle", new Callable() {
         public String a() throws Exception {
            return Entity.this.vehicle.toString();
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
   }

   public UUID getUniqueID() {
      return this.uniqueID;
   }

   public boolean aL() {
      return true;
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      ChatComponentText chatcomponenttext = new ChatComponentText(this.getName());
      chatcomponenttext.getChatModifier().setChatHoverable(this.aQ());
      chatcomponenttext.getChatModifier().setInsertion(this.getUniqueID().toString());
      return chatcomponenttext;
   }

   public void setCustomName(String p_setCustomName_1_) {
      if(p_setCustomName_1_.length() > 256) {
         p_setCustomName_1_ = p_setCustomName_1_.substring(0, 256);
      }

      this.datawatcher.watch(2, p_setCustomName_1_);
   }

   public String getCustomName() {
      return this.datawatcher.getString(2);
   }

   public boolean hasCustomName() {
      return this.datawatcher.getString(2).length() > 0;
   }

   public void setCustomNameVisible(boolean p_setCustomNameVisible_1_) {
      this.datawatcher.watch(3, Byte.valueOf((byte)(p_setCustomNameVisible_1_?1:0)));
   }

   public boolean getCustomNameVisible() {
      return this.datawatcher.getByte(3) == 1;
   }

   public void enderTeleportTo(double p_enderTeleportTo_1_, double p_enderTeleportTo_3_, double p_enderTeleportTo_5_) {
      this.setPositionRotation(p_enderTeleportTo_1_, p_enderTeleportTo_3_, p_enderTeleportTo_5_, this.yaw, this.pitch);
   }

   public void i(int p_i_1_) {
   }

   public EnumDirection getDirection() {
      return EnumDirection.fromType2(MathHelper.floor((double)(this.yaw * 4.0F / 360.0F) + 0.5D) & 3);
   }

   protected ChatHoverable aQ() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      String s = EntityTypes.b(this);
      nbttagcompound.setString("id", this.getUniqueID().toString());
      if(s != null) {
         nbttagcompound.setString("type", s);
      }

      nbttagcompound.setString("name", this.getName());
      return new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ENTITY, new ChatComponentText(nbttagcompound.toString()));
   }

   public boolean a(EntityPlayer p_a_1_) {
      return true;
   }

   public AxisAlignedBB getBoundingBox() {
      return this.boundingBox;
   }

   public void a(AxisAlignedBB p_a_1_) {
      double d0 = p_a_1_.a;
      double d1 = p_a_1_.b;
      double d2 = p_a_1_.c;
      double d3 = p_a_1_.d;
      double d4 = p_a_1_.e;
      double d5 = p_a_1_.f;
      double d6 = p_a_1_.d - p_a_1_.a;
      if(d6 < 0.0D) {
         d3 = d0;
      }

      if(d6 > 64.0D) {
         d3 = d0 + 64.0D;
      }

      d6 = p_a_1_.e - p_a_1_.b;
      if(d6 < 0.0D) {
         d4 = d1;
      }

      if(d6 > 64.0D) {
         d4 = d1 + 64.0D;
      }

      d6 = p_a_1_.f - p_a_1_.c;
      if(d6 < 0.0D) {
         d5 = d2;
      }

      if(d6 > 64.0D) {
         d5 = d2 + 64.0D;
      }

      this.boundingBox = new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
   }

   public float getHeadHeight() {
      return this.length * 0.85F;
   }

   public boolean aT() {
      return this.g;
   }

   public void h(boolean p_h_1_) {
      this.g = p_h_1_;
   }

   public boolean d(int p_d_1_, ItemStack p_d_2_) {
      return false;
   }

   public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
   }

   public boolean a(int p_a_1_, String p_a_2_) {
      return true;
   }

   public BlockPosition getChunkCoordinates() {
      return new BlockPosition(this.locX, this.locY + 0.5D, this.locZ);
   }

   public Vec3D d() {
      return new Vec3D(this.locX, this.locY, this.locZ);
   }

   public World getWorld() {
      return this.world;
   }

   public Entity f() {
      return this;
   }

   public boolean getSendCommandFeedback() {
      return false;
   }

   public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_) {
      this.au.a(this, p_a_1_, p_a_2_);
   }

   public CommandObjectiveExecutor aU() {
      return this.au;
   }

   public void o(Entity p_o_1_) {
      this.au.a(p_o_1_.aU());
   }

   public NBTTagCompound getNBTTag() {
      return null;
   }

   public boolean a(EntityHuman p_a_1_, Vec3D p_a_2_) {
      return false;
   }

   public boolean aW() {
      return false;
   }

   protected void a(EntityLiving p_a_1_, Entity p_a_2_) {
      if(p_a_2_ instanceof EntityLiving) {
         EnchantmentManager.a((EntityLiving)p_a_2_, (Entity)p_a_1_);
      }

      EnchantmentManager.b(p_a_1_, p_a_2_);
   }
}

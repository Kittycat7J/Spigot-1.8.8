package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockMinecartTrackAbstract;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockPoweredRail;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMinecartChest;
import net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock;
import net.minecraft.server.v1_8_R3.EntityMinecartFurnace;
import net.minecraft.server.v1_8_R3.EntityMinecartHopper;
import net.minecraft.server.v1_8_R3.EntityMinecartMobSpawner;
import net.minecraft.server.v1_8_R3.EntityMinecartRideable;
import net.minecraft.server.v1_8_R3.EntityMinecartTNT;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.INamableTileEntity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.util.Vector;

public abstract class EntityMinecartAbstract extends Entity implements INamableTileEntity {
   private boolean a;
   private String b;
   private static final int[][][] matrix = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
   private int d;
   private double e;
   private double f;
   private double g;
   private double h;
   private double i;
   public boolean slowWhenEmpty;
   private double derailedX;
   private double derailedY;
   private double derailedZ;
   private double flyingX;
   private double flyingY;
   private double flyingZ;
   public double maxSpeed;

   public EntityMinecartAbstract(World p_i355_1_) {
      super(p_i355_1_);
      this.slowWhenEmpty = true;
      this.derailedX = 0.5D;
      this.derailedY = 0.5D;
      this.derailedZ = 0.5D;
      this.flyingX = 0.95D;
      this.flyingY = 0.95D;
      this.flyingZ = 0.95D;
      this.maxSpeed = 0.4D;
      this.k = true;
      this.setSize(0.98F, 0.7F);
   }

   public static EntityMinecartAbstract a(World p_a_0_, double p_a_1_, double p_a_3_, double p_a_5_, EntityMinecartAbstract.EnumMinecartType p_a_7_) {
      switch(EntityMinecartAbstract.SyntheticClass_1.a[p_a_7_.ordinal()]) {
      case 1:
         return new EntityMinecartChest(p_a_0_, p_a_1_, p_a_3_, p_a_5_);
      case 2:
         return new EntityMinecartFurnace(p_a_0_, p_a_1_, p_a_3_, p_a_5_);
      case 3:
         return new EntityMinecartTNT(p_a_0_, p_a_1_, p_a_3_, p_a_5_);
      case 4:
         return new EntityMinecartMobSpawner(p_a_0_, p_a_1_, p_a_3_, p_a_5_);
      case 5:
         return new EntityMinecartHopper(p_a_0_, p_a_1_, p_a_3_, p_a_5_);
      case 6:
         return new EntityMinecartCommandBlock(p_a_0_, p_a_1_, p_a_3_, p_a_5_);
      default:
         return new EntityMinecartRideable(p_a_0_, p_a_1_, p_a_3_, p_a_5_);
      }
   }

   protected boolean s_() {
      return false;
   }

   protected void h() {
      this.datawatcher.a(17, new Integer(0));
      this.datawatcher.a(18, new Integer(1));
      this.datawatcher.a(19, new Float(0.0F));
      this.datawatcher.a(20, new Integer(0));
      this.datawatcher.a(21, new Integer(6));
      this.datawatcher.a(22, Byte.valueOf((byte)0));
   }

   public AxisAlignedBB j(Entity p_j_1_) {
      return p_j_1_.ae()?p_j_1_.getBoundingBox():null;
   }

   public AxisAlignedBB S() {
      return null;
   }

   public boolean ae() {
      return true;
   }

   public EntityMinecartAbstract(World p_i356_1_, double p_i356_2_, double p_i356_4_, double p_i356_6_) {
      this(p_i356_1_);
      this.setPosition(p_i356_2_, p_i356_4_, p_i356_6_);
      this.motX = 0.0D;
      this.motY = 0.0D;
      this.motZ = 0.0D;
      this.lastX = p_i356_2_;
      this.lastY = p_i356_4_;
      this.lastZ = p_i356_6_;
      this.world.getServer().getPluginManager().callEvent(new VehicleCreateEvent((Vehicle)this.getBukkitEntity()));
   }

   public double an() {
      return 0.0D;
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      if(!this.world.isClientSide && !this.dead) {
         if(this.isInvulnerable(p_damageEntity_1_)) {
            return false;
         } else {
            Vehicle vehicle = (Vehicle)this.getBukkitEntity();
            org.bukkit.entity.Entity entity = p_damageEntity_1_.getEntity() == null?null:p_damageEntity_1_.getEntity().getBukkitEntity();
            VehicleDamageEvent vehicledamageevent = new VehicleDamageEvent(vehicle, entity, (double)p_damageEntity_2_);
            this.world.getServer().getPluginManager().callEvent(vehicledamageevent);
            if(vehicledamageevent.isCancelled()) {
               return true;
            } else {
               p_damageEntity_2_ = (float)vehicledamageevent.getDamage();
               this.k(-this.r());
               this.j(10);
               this.ac();
               this.setDamage(this.getDamage() + p_damageEntity_2_ * 10.0F);
               boolean flag = p_damageEntity_1_.getEntity() instanceof EntityHuman && ((EntityHuman)p_damageEntity_1_.getEntity()).abilities.canInstantlyBuild;
               if(flag || this.getDamage() > 40.0F) {
                  VehicleDestroyEvent vehicledestroyevent = new VehicleDestroyEvent(vehicle, entity);
                  this.world.getServer().getPluginManager().callEvent(vehicledestroyevent);
                  if(vehicledestroyevent.isCancelled()) {
                     this.setDamage(40.0F);
                     return true;
                  }

                  if(this.passenger != null) {
                     this.passenger.mount((Entity)null);
                  }

                  if(flag && !this.hasCustomName()) {
                     this.die();
                  } else {
                     this.a(p_damageEntity_1_);
                  }
               }

               return true;
            }
         }
      } else {
         return true;
      }
   }

   public void a(DamageSource p_a_1_) {
      this.die();
      if(this.world.getGameRules().getBoolean("doEntityDrops")) {
         ItemStack itemstack = new ItemStack(Items.MINECART, 1);
         if(this.b != null) {
            itemstack.c(this.b);
         }

         this.a(itemstack, 0.0F);
      }

   }

   public boolean ad() {
      return !this.dead;
   }

   public void die() {
      super.die();
   }

   public void t_() {
      double d0 = this.locX;
      double d1 = this.locY;
      double d2 = this.locZ;
      float f = this.yaw;
      float f1 = this.pitch;
      if(this.getType() > 0) {
         this.j(this.getType() - 1);
      }

      if(this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      if(this.locY < -64.0D) {
         this.O();
      }

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

      if(this.world.isClientSide) {
         if(this.d > 0) {
            double d3 = this.locX + (this.e - this.locX) / (double)this.d;
            double d4 = this.locY + (this.f - this.locY) / (double)this.d;
            double d5 = this.locZ + (this.g - this.locZ) / (double)this.d;
            double d6 = MathHelper.g(this.h - (double)this.yaw);
            this.yaw = (float)((double)this.yaw + d6 / (double)this.d);
            this.pitch = (float)((double)this.pitch + (this.i - (double)this.pitch) / (double)this.d);
            --this.d;
            this.setPosition(d3, d4, d5);
            this.setYawPitch(this.yaw, this.pitch);
         } else {
            this.setPosition(this.locX, this.locY, this.locZ);
            this.setYawPitch(this.yaw, this.pitch);
         }
      } else {
         this.lastX = this.locX;
         this.lastY = this.locY;
         this.lastZ = this.locZ;
         this.motY -= 0.03999999910593033D;
         int j = MathHelper.floor(this.locX);
         int k = MathHelper.floor(this.locY);
         int l = MathHelper.floor(this.locZ);
         if(BlockMinecartTrackAbstract.e(this.world, new BlockPosition(j, k - 1, l))) {
            --k;
         }

         BlockPosition blockposition = new BlockPosition(j, k, l);
         IBlockData iblockdata = this.world.getType(blockposition);
         if(BlockMinecartTrackAbstract.d(iblockdata)) {
            this.a(blockposition, iblockdata);
            if(iblockdata.getBlock() == Blocks.ACTIVATOR_RAIL) {
               this.a(j, k, l, ((Boolean)iblockdata.get(BlockPoweredRail.POWERED)).booleanValue());
            }
         } else {
            this.n();
         }

         this.checkBlockCollisions();
         this.pitch = 0.0F;
         double d8 = this.lastX - this.locX;
         double d9 = this.lastZ - this.locZ;
         if(d8 * d8 + d9 * d9 > 0.001D) {
            this.yaw = (float)(MathHelper.b(d9, d8) * 180.0D / 3.141592653589793D);
            if(this.a) {
               this.yaw += 180.0F;
            }
         }

         double d7 = (double)MathHelper.g(this.yaw - this.lastYaw);
         if(d7 < -170.0D || d7 >= 170.0D) {
            this.yaw += 180.0F;
            this.a = !this.a;
         }

         this.setYawPitch(this.yaw, this.pitch);
         org.bukkit.World world = this.world.getWorld();
         Location location = new Location(world, d0, d1, d2, f, f1);
         Location location1 = new Location(world, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
         Vehicle vehicle = (Vehicle)this.getBukkitEntity();
         this.world.getServer().getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));
         if(!location.equals(location1)) {
            this.world.getServer().getPluginManager().callEvent(new VehicleMoveEvent(vehicle, location, location1));
         }

         for(Entity entity : this.world.getEntities(this, this.getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
            if(entity != this.passenger && entity.ae() && entity instanceof EntityMinecartAbstract) {
               entity.collide(this);
            }
         }

         if(this.passenger != null && this.passenger.dead) {
            if(this.passenger.vehicle == this) {
               this.passenger.vehicle = null;
            }

            this.passenger = null;
         }

         this.W();
      }

   }

   protected double m() {
      return this.maxSpeed;
   }

   public void a(int p_a_1_, int p_a_2_, int p_a_3_, boolean p_a_4_) {
   }

   protected void n() {
      double d0 = this.m();
      this.motX = MathHelper.a(this.motX, -d0, d0);
      this.motZ = MathHelper.a(this.motZ, -d0, d0);
      if(this.onGround) {
         this.motX *= this.derailedX;
         this.motY *= this.derailedY;
         this.motZ *= this.derailedZ;
      }

      this.move(this.motX, this.motY, this.motZ);
      if(!this.onGround) {
         this.motX *= this.flyingX;
         this.motY *= this.flyingY;
         this.motZ *= this.flyingZ;
      }

   }

   protected void a(BlockPosition p_a_1_, IBlockData p_a_2_) {
      this.fallDistance = 0.0F;
      Vec3D vec3d = this.k(this.locX, this.locY, this.locZ);
      this.locY = (double)p_a_1_.getY();
      boolean flag = false;
      boolean flag1 = false;
      BlockMinecartTrackAbstract blockminecarttrackabstract = (BlockMinecartTrackAbstract)p_a_2_.getBlock();
      if(blockminecarttrackabstract == Blocks.GOLDEN_RAIL) {
         flag = ((Boolean)p_a_2_.get(BlockPoweredRail.POWERED)).booleanValue();
         flag1 = !flag;
      }

      BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)p_a_2_.get(blockminecarttrackabstract.n());
      switch(EntityMinecartAbstract.SyntheticClass_1.b[blockminecarttrackabstract$enumtrackposition.ordinal()]) {
      case 1:
         this.motX -= 0.0078125D;
         ++this.locY;
         break;
      case 2:
         this.motX += 0.0078125D;
         ++this.locY;
         break;
      case 3:
         this.motZ += 0.0078125D;
         ++this.locY;
         break;
      case 4:
         this.motZ -= 0.0078125D;
         ++this.locY;
      }

      int[][] aint = matrix[blockminecarttrackabstract$enumtrackposition.a()];
      double d0 = (double)(aint[1][0] - aint[0][0]);
      double d1 = (double)(aint[1][2] - aint[0][2]);
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      double d3 = this.motX * d0 + this.motZ * d1;
      if(d3 < 0.0D) {
         d0 = -d0;
         d1 = -d1;
      }

      double d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      if(d4 > 2.0D) {
         d4 = 2.0D;
      }

      this.motX = d4 * d0 / d2;
      this.motZ = d4 * d1 / d2;
      if(this.passenger instanceof EntityLiving) {
         double d5 = (double)((EntityLiving)this.passenger).ba;
         if(d5 > 0.0D) {
            double d6 = -Math.sin((double)(this.passenger.yaw * 3.1415927F / 180.0F));
            double d7 = Math.cos((double)(this.passenger.yaw * 3.1415927F / 180.0F));
            double d8 = this.motX * this.motX + this.motZ * this.motZ;
            if(d8 < 0.01D) {
               this.motX += d6 * 0.1D;
               this.motZ += d7 * 0.1D;
               flag1 = false;
            }
         }
      }

      if(flag1) {
         double d16 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
         if(d16 < 0.03D) {
            this.motX *= 0.0D;
            this.motY *= 0.0D;
            this.motZ *= 0.0D;
         } else {
            this.motX *= 0.5D;
            this.motY *= 0.0D;
            this.motZ *= 0.5D;
         }
      }

      double d17 = 0.0D;
      double d18 = (double)p_a_1_.getX() + 0.5D + (double)aint[0][0] * 0.5D;
      double d19 = (double)p_a_1_.getZ() + 0.5D + (double)aint[0][2] * 0.5D;
      double d20 = (double)p_a_1_.getX() + 0.5D + (double)aint[1][0] * 0.5D;
      double d9 = (double)p_a_1_.getZ() + 0.5D + (double)aint[1][2] * 0.5D;
      d0 = d20 - d18;
      d1 = d9 - d19;
      if(d0 == 0.0D) {
         this.locX = (double)p_a_1_.getX() + 0.5D;
         d17 = this.locZ - (double)p_a_1_.getZ();
      } else if(d1 == 0.0D) {
         this.locZ = (double)p_a_1_.getZ() + 0.5D;
         d17 = this.locX - (double)p_a_1_.getX();
      } else {
         double d10 = this.locX - d18;
         double d11 = this.locZ - d19;
         d17 = (d10 * d0 + d11 * d1) * 2.0D;
      }

      this.locX = d18 + d0 * d17;
      this.locZ = d19 + d1 * d17;
      this.setPosition(this.locX, this.locY, this.locZ);
      double d21 = this.motX;
      double d22 = this.motZ;
      if(this.passenger != null) {
         d21 *= 0.75D;
         d22 *= 0.75D;
      }

      double d12 = this.m();
      d21 = MathHelper.a(d21, -d12, d12);
      d22 = MathHelper.a(d22, -d12, d12);
      this.move(d21, 0.0D, d22);
      if(aint[0][1] != 0 && MathHelper.floor(this.locX) - p_a_1_.getX() == aint[0][0] && MathHelper.floor(this.locZ) - p_a_1_.getZ() == aint[0][2]) {
         this.setPosition(this.locX, this.locY + (double)aint[0][1], this.locZ);
      } else if(aint[1][1] != 0 && MathHelper.floor(this.locX) - p_a_1_.getX() == aint[1][0] && MathHelper.floor(this.locZ) - p_a_1_.getZ() == aint[1][2]) {
         this.setPosition(this.locX, this.locY + (double)aint[1][1], this.locZ);
      }

      this.o();
      Vec3D vec3d1 = this.k(this.locX, this.locY, this.locZ);
      if(vec3d1 != null && vec3d != null) {
         double d13 = (vec3d.b - vec3d1.b) * 0.05D;
         d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
         if(d4 > 0.0D) {
            this.motX = this.motX / d4 * (d4 + d13);
            this.motZ = this.motZ / d4 * (d4 + d13);
         }

         this.setPosition(this.locX, vec3d1.b, this.locZ);
      }

      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.locZ);
      if(i != p_a_1_.getX() || j != p_a_1_.getZ()) {
         d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
         this.motX = d4 * (double)(i - p_a_1_.getX());
         this.motZ = d4 * (double)(j - p_a_1_.getZ());
      }

      if(flag) {
         double d14 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
         if(d14 > 0.01D) {
            double d15 = 0.06D;
            this.motX += this.motX / d14 * d15;
            this.motZ += this.motZ / d14 * d15;
         } else if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST) {
            if(this.world.getType(p_a_1_.west()).getBlock().isOccluding()) {
               this.motX = 0.02D;
            } else if(this.world.getType(p_a_1_.east()).getBlock().isOccluding()) {
               this.motX = -0.02D;
            }
         } else if(blockminecarttrackabstract$enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH) {
            if(this.world.getType(p_a_1_.north()).getBlock().isOccluding()) {
               this.motZ = 0.02D;
            } else if(this.world.getType(p_a_1_.south()).getBlock().isOccluding()) {
               this.motZ = -0.02D;
            }
         }
      }

   }

   protected void o() {
      if(this.passenger == null && this.slowWhenEmpty) {
         this.motX *= 0.9599999785423279D;
         this.motY *= 0.0D;
         this.motZ *= 0.9599999785423279D;
      } else {
         this.motX *= 0.996999979019165D;
         this.motY *= 0.0D;
         this.motZ *= 0.996999979019165D;
      }

   }

   public void setPosition(double p_setPosition_1_, double p_setPosition_3_, double p_setPosition_5_) {
      this.locX = p_setPosition_1_;
      this.locY = p_setPosition_3_;
      this.locZ = p_setPosition_5_;
      float f = this.width / 2.0F;
      float f1 = this.length;
      this.a((AxisAlignedBB)(new AxisAlignedBB(p_setPosition_1_ - (double)f, p_setPosition_3_, p_setPosition_5_ - (double)f, p_setPosition_1_ + (double)f, p_setPosition_3_ + (double)f1, p_setPosition_5_ + (double)f)));
   }

   public Vec3D k(double p_k_1_, double p_k_3_, double p_k_5_) {
      int i = MathHelper.floor(p_k_1_);
      int j = MathHelper.floor(p_k_3_);
      int k = MathHelper.floor(p_k_5_);
      if(BlockMinecartTrackAbstract.e(this.world, new BlockPosition(i, j - 1, k))) {
         --j;
      }

      IBlockData iblockdata = this.world.getType(new BlockPosition(i, j, k));
      if(BlockMinecartTrackAbstract.d(iblockdata)) {
         BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(((BlockMinecartTrackAbstract)iblockdata.getBlock()).n());
         int[][] aint = matrix[blockminecarttrackabstract$enumtrackposition.a()];
         double d0 = 0.0D;
         double d1 = (double)i + 0.5D + (double)aint[0][0] * 0.5D;
         double d2 = (double)j + 0.0625D + (double)aint[0][1] * 0.5D;
         double d3 = (double)k + 0.5D + (double)aint[0][2] * 0.5D;
         double d4 = (double)i + 0.5D + (double)aint[1][0] * 0.5D;
         double d5 = (double)j + 0.0625D + (double)aint[1][1] * 0.5D;
         double d6 = (double)k + 0.5D + (double)aint[1][2] * 0.5D;
         double d7 = d4 - d1;
         double d8 = (d5 - d2) * 2.0D;
         double d9 = d6 - d3;
         if(d7 == 0.0D) {
            p_k_1_ = (double)i + 0.5D;
            d0 = p_k_5_ - (double)k;
         } else if(d9 == 0.0D) {
            p_k_5_ = (double)k + 0.5D;
            d0 = p_k_1_ - (double)i;
         } else {
            double d10 = p_k_1_ - d1;
            double d11 = p_k_5_ - d3;
            d0 = (d10 * d7 + d11 * d9) * 2.0D;
         }

         p_k_1_ = d1 + d7 * d0;
         p_k_3_ = d2 + d8 * d0;
         p_k_5_ = d3 + d9 * d0;
         if(d8 < 0.0D) {
            ++p_k_3_;
         }

         if(d8 > 0.0D) {
            p_k_3_ += 0.5D;
         }

         return new Vec3D(p_k_1_, p_k_3_, p_k_5_);
      } else {
         return null;
      }
   }

   protected void a(NBTTagCompound p_a_1_) {
      if(p_a_1_.getBoolean("CustomDisplayTile")) {
         int i = p_a_1_.getInt("DisplayData");
         if(p_a_1_.hasKeyOfType("DisplayTile", 8)) {
            Block block = Block.getByName(p_a_1_.getString("DisplayTile"));
            if(block == null) {
               this.setDisplayBlock(Blocks.AIR.getBlockData());
            } else {
               this.setDisplayBlock(block.fromLegacyData(i));
            }
         } else {
            Block block1 = Block.getById(p_a_1_.getInt("DisplayTile"));
            if(block1 == null) {
               this.setDisplayBlock(Blocks.AIR.getBlockData());
            } else {
               this.setDisplayBlock(block1.fromLegacyData(i));
            }
         }

         this.SetDisplayBlockOffset(p_a_1_.getInt("DisplayOffset"));
      }

      if(p_a_1_.hasKeyOfType("CustomName", 8) && p_a_1_.getString("CustomName").length() > 0) {
         this.b = p_a_1_.getString("CustomName");
      }

   }

   protected void b(NBTTagCompound p_b_1_) {
      if(this.x()) {
         p_b_1_.setBoolean("CustomDisplayTile", true);
         IBlockData iblockdata = this.getDisplayBlock();
         MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(iblockdata.getBlock());
         p_b_1_.setString("DisplayTile", minecraftkey == null?"":minecraftkey.toString());
         p_b_1_.setInt("DisplayData", iblockdata.getBlock().toLegacyData(iblockdata));
         p_b_1_.setInt("DisplayOffset", this.getDisplayBlockOffset());
      }

      if(this.b != null && this.b.length() > 0) {
         p_b_1_.setString("CustomName", this.b);
      }

   }

   public void collide(Entity p_collide_1_) {
      if(!this.world.isClientSide && !p_collide_1_.noclip && !this.noclip && p_collide_1_ != this.passenger) {
         Vehicle vehicle = (Vehicle)this.getBukkitEntity();
         org.bukkit.entity.Entity entity = p_collide_1_ == null?null:p_collide_1_.getBukkitEntity();
         VehicleEntityCollisionEvent vehicleentitycollisionevent = new VehicleEntityCollisionEvent(vehicle, entity);
         this.world.getServer().getPluginManager().callEvent(vehicleentitycollisionevent);
         if(vehicleentitycollisionevent.isCancelled()) {
            return;
         }

         if(p_collide_1_ instanceof EntityLiving && !(p_collide_1_ instanceof EntityHuman) && !(p_collide_1_ instanceof EntityIronGolem) && this.s() == EntityMinecartAbstract.EnumMinecartType.RIDEABLE && this.motX * this.motX + this.motZ * this.motZ > 0.01D && this.passenger == null && p_collide_1_.vehicle == null) {
            p_collide_1_.mount(this);
         }

         double d0 = p_collide_1_.locX - this.locX;
         double d1 = p_collide_1_.locZ - this.locZ;
         double d2 = d0 * d0 + d1 * d1;
         if(d2 >= 9.999999747378752E-5D && !vehicleentitycollisionevent.isCollisionCancelled()) {
            d2 = (double)MathHelper.sqrt(d2);
            d0 = d0 / d2;
            d1 = d1 / d2;
            double d3 = 1.0D / d2;
            if(d3 > 1.0D) {
               d3 = 1.0D;
            }

            d0 = d0 * d3;
            d1 = d1 * d3;
            d0 = d0 * 0.10000000149011612D;
            d1 = d1 * 0.10000000149011612D;
            d0 = d0 * (double)(1.0F - this.U);
            d1 = d1 * (double)(1.0F - this.U);
            d0 = d0 * 0.5D;
            d1 = d1 * 0.5D;
            if(p_collide_1_ instanceof EntityMinecartAbstract) {
               double d4 = p_collide_1_.locX - this.locX;
               double d5 = p_collide_1_.locZ - this.locZ;
               Vec3D vec3d = (new Vec3D(d4, 0.0D, d5)).a();
               Vec3D vec3d1 = (new Vec3D((double)MathHelper.cos(this.yaw * 3.1415927F / 180.0F), 0.0D, (double)MathHelper.sin(this.yaw * 3.1415927F / 180.0F))).a();
               double d6 = Math.abs(vec3d.b(vec3d1));
               if(d6 < 0.800000011920929D) {
                  return;
               }

               double d7 = p_collide_1_.motX + this.motX;
               double d8 = p_collide_1_.motZ + this.motZ;
               if(((EntityMinecartAbstract)p_collide_1_).s() == EntityMinecartAbstract.EnumMinecartType.FURNACE && this.s() != EntityMinecartAbstract.EnumMinecartType.FURNACE) {
                  this.motX *= 0.20000000298023224D;
                  this.motZ *= 0.20000000298023224D;
                  this.g(p_collide_1_.motX - d0, 0.0D, p_collide_1_.motZ - d1);
                  p_collide_1_.motX *= 0.949999988079071D;
                  p_collide_1_.motZ *= 0.949999988079071D;
               } else if(((EntityMinecartAbstract)p_collide_1_).s() != EntityMinecartAbstract.EnumMinecartType.FURNACE && this.s() == EntityMinecartAbstract.EnumMinecartType.FURNACE) {
                  p_collide_1_.motX *= 0.20000000298023224D;
                  p_collide_1_.motZ *= 0.20000000298023224D;
                  p_collide_1_.g(this.motX + d0, 0.0D, this.motZ + d1);
                  this.motX *= 0.949999988079071D;
                  this.motZ *= 0.949999988079071D;
               } else {
                  d7 = d7 / 2.0D;
                  d8 = d8 / 2.0D;
                  this.motX *= 0.20000000298023224D;
                  this.motZ *= 0.20000000298023224D;
                  this.g(d7 - d0, 0.0D, d8 - d1);
                  p_collide_1_.motX *= 0.20000000298023224D;
                  p_collide_1_.motZ *= 0.20000000298023224D;
                  p_collide_1_.g(d7 + d0, 0.0D, d8 + d1);
               }
            } else {
               this.g(-d0, 0.0D, -d1);
               p_collide_1_.g(d0 / 4.0D, 0.0D, d1 / 4.0D);
            }
         }
      }

   }

   public void setDamage(float p_setDamage_1_) {
      this.datawatcher.watch(19, Float.valueOf(p_setDamage_1_));
   }

   public float getDamage() {
      return this.datawatcher.getFloat(19);
   }

   public void j(int p_j_1_) {
      this.datawatcher.watch(17, Integer.valueOf(p_j_1_));
   }

   public int getType() {
      return this.datawatcher.getInt(17);
   }

   public void k(int p_k_1_) {
      this.datawatcher.watch(18, Integer.valueOf(p_k_1_));
   }

   public int r() {
      return this.datawatcher.getInt(18);
   }

   public abstract EntityMinecartAbstract.EnumMinecartType s();

   public IBlockData getDisplayBlock() {
      return !this.x()?this.u():Block.getByCombinedId(this.getDataWatcher().getInt(20));
   }

   public IBlockData u() {
      return Blocks.AIR.getBlockData();
   }

   public int getDisplayBlockOffset() {
      return !this.x()?this.w():this.getDataWatcher().getInt(21);
   }

   public int w() {
      return 6;
   }

   public void setDisplayBlock(IBlockData p_setDisplayBlock_1_) {
      this.getDataWatcher().watch(20, Integer.valueOf(Block.getCombinedId(p_setDisplayBlock_1_)));
      this.a(true);
   }

   public void SetDisplayBlockOffset(int p_SetDisplayBlockOffset_1_) {
      this.getDataWatcher().watch(21, Integer.valueOf(p_SetDisplayBlockOffset_1_));
      this.a(true);
   }

   public boolean x() {
      return this.getDataWatcher().getByte(22) == 1;
   }

   public void a(boolean p_a_1_) {
      this.getDataWatcher().watch(22, Byte.valueOf((byte)(p_a_1_?1:0)));
   }

   public void setCustomName(String p_setCustomName_1_) {
      this.b = p_setCustomName_1_;
   }

   public String getName() {
      return this.b != null?this.b:super.getName();
   }

   public boolean hasCustomName() {
      return this.b != null;
   }

   public String getCustomName() {
      return this.b;
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      if(this.hasCustomName()) {
         ChatComponentText chatcomponenttext = new ChatComponentText(this.b);
         chatcomponenttext.getChatModifier().setChatHoverable(this.aQ());
         chatcomponenttext.getChatModifier().setInsertion(this.getUniqueID().toString());
         return chatcomponenttext;
      } else {
         ChatMessage chatmessage = new ChatMessage(this.getName(), new Object[0]);
         chatmessage.getChatModifier().setChatHoverable(this.aQ());
         chatmessage.getChatModifier().setInsertion(this.getUniqueID().toString());
         return chatmessage;
      }
   }

   public Vector getFlyingVelocityMod() {
      return new Vector(this.flyingX, this.flyingY, this.flyingZ);
   }

   public void setFlyingVelocityMod(Vector p_setFlyingVelocityMod_1_) {
      this.flyingX = p_setFlyingVelocityMod_1_.getX();
      this.flyingY = p_setFlyingVelocityMod_1_.getY();
      this.flyingZ = p_setFlyingVelocityMod_1_.getZ();
   }

   public Vector getDerailedVelocityMod() {
      return new Vector(this.derailedX, this.derailedY, this.derailedZ);
   }

   public void setDerailedVelocityMod(Vector p_setDerailedVelocityMod_1_) {
      this.derailedX = p_setDerailedVelocityMod_1_.getX();
      this.derailedY = p_setDerailedVelocityMod_1_.getY();
      this.derailedZ = p_setDerailedVelocityMod_1_.getZ();
   }

   public static enum EnumMinecartType {
      RIDEABLE(0, "MinecartRideable"),
      CHEST(1, "MinecartChest"),
      FURNACE(2, "MinecartFurnace"),
      TNT(3, "MinecartTNT"),
      SPAWNER(4, "MinecartSpawner"),
      HOPPER(5, "MinecartHopper"),
      COMMAND_BLOCK(6, "MinecartCommandBlock");

      private static final Map<Integer, EntityMinecartAbstract.EnumMinecartType> h = Maps.<Integer, EntityMinecartAbstract.EnumMinecartType>newHashMap();
      private final int i;
      private final String j;

      static {
         for(EntityMinecartAbstract.EnumMinecartType entityminecartabstract$enumminecarttype : values()) {
            h.put(Integer.valueOf(entityminecartabstract$enumminecarttype.a()), entityminecartabstract$enumminecarttype);
         }

      }

      private EnumMinecartType(int p_i311_3_, String p_i311_4_) {
         this.i = p_i311_3_;
         this.j = p_i311_4_;
      }

      public int a() {
         return this.i;
      }

      public String b() {
         return this.j;
      }

      public static EntityMinecartAbstract.EnumMinecartType a(int p_a_0_) {
         EntityMinecartAbstract.EnumMinecartType entityminecartabstract$enumminecarttype = (EntityMinecartAbstract.EnumMinecartType)h.get(Integer.valueOf(p_a_0_));
         return entityminecartabstract$enumminecarttype == null?RIDEABLE:entityminecartabstract$enumminecarttype;
      }
   }

   static class SyntheticClass_1 {
      static final int[] a;
      static final int[] b = new int[BlockMinecartTrackAbstract.EnumTrackPosition.values().length];

      static {
         try {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
            ;
         }

         a = new int[EntityMinecartAbstract.EnumMinecartType.values().length];

         try {
            a[EntityMinecartAbstract.EnumMinecartType.CHEST.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            a[EntityMinecartAbstract.EnumMinecartType.FURNACE.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            a[EntityMinecartAbstract.EnumMinecartType.TNT.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EntityMinecartAbstract.EnumMinecartType.SPAWNER.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[EntityMinecartAbstract.EnumMinecartType.HOPPER.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}

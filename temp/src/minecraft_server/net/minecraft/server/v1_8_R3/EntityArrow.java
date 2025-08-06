package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IProjectile;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EntityArrow extends Entity implements IProjectile {
   private int d = -1;
   private int e = -1;
   private int f = -1;
   private Block g;
   private int h;
   public boolean inGround = false;
   public int fromPlayer;
   public int shake;
   public Entity shooter;
   private int ar;
   private int as;
   private double damage = 2.0D;
   public int knockbackStrength;

   public void inactiveTick() {
      if(this.inGround) {
         ++this.ar;
      }

      super.inactiveTick();
   }

   public EntityArrow(World p_i287_1_) {
      super(p_i287_1_);
      this.j = 10.0D;
      this.setSize(0.5F, 0.5F);
   }

   public EntityArrow(World p_i288_1_, double p_i288_2_, double p_i288_4_, double p_i288_6_) {
      super(p_i288_1_);
      this.j = 10.0D;
      this.setSize(0.5F, 0.5F);
      this.setPosition(p_i288_2_, p_i288_4_, p_i288_6_);
   }

   public EntityArrow(World p_i289_1_, EntityLiving p_i289_2_, EntityLiving p_i289_3_, float p_i289_4_, float p_i289_5_) {
      super(p_i289_1_);
      this.j = 10.0D;
      this.shooter = p_i289_2_;
      this.projectileSource = (LivingEntity)p_i289_2_.getBukkitEntity();
      if(p_i289_2_ instanceof EntityHuman) {
         this.fromPlayer = 1;
      }

      this.locY = p_i289_2_.locY + (double)p_i289_2_.getHeadHeight() - 0.10000000149011612D;
      double d0 = p_i289_3_.locX - p_i289_2_.locX;
      double d1 = p_i289_3_.getBoundingBox().b + (double)(p_i289_3_.length / 3.0F) - this.locY;
      double d2 = p_i289_3_.locZ - p_i289_2_.locZ;
      double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
      if(d3 >= 1.0E-7D) {
         float f = (float)(MathHelper.b(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
         float f1 = (float)(-(MathHelper.b(d1, d3) * 180.0D / 3.1415927410125732D));
         double d4 = d0 / d3;
         double d5 = d2 / d3;
         this.setPositionRotation(p_i289_2_.locX + d4, this.locY, p_i289_2_.locZ + d5, f, f1);
         float f2 = (float)(d3 * 0.20000000298023224D);
         this.shoot(d0, d1 + (double)f2, d2, p_i289_4_, p_i289_5_);
      }

   }

   public EntityArrow(World p_i290_1_, EntityLiving p_i290_2_, float p_i290_3_) {
      super(p_i290_1_);
      this.j = 10.0D;
      this.shooter = p_i290_2_;
      this.projectileSource = (LivingEntity)p_i290_2_.getBukkitEntity();
      if(p_i290_2_ instanceof EntityHuman) {
         this.fromPlayer = 1;
      }

      this.setSize(0.5F, 0.5F);
      this.setPositionRotation(p_i290_2_.locX, p_i290_2_.locY + (double)p_i290_2_.getHeadHeight(), p_i290_2_.locZ, p_i290_2_.yaw, p_i290_2_.pitch);
      this.locX -= (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
      this.locY -= 0.10000000149011612D;
      this.locZ -= (double)(MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
      this.setPosition(this.locX, this.locY, this.locZ);
      this.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
      this.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
      this.motY = (double)(-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
      this.shoot(this.motX, this.motY, this.motZ, p_i290_3_ * 1.5F, 1.0F);
   }

   protected void h() {
      this.datawatcher.a(16, Byte.valueOf((byte)0));
   }

   public void shoot(double p_shoot_1_, double p_shoot_3_, double p_shoot_5_, float p_shoot_7_, float p_shoot_8_) {
      float f = MathHelper.sqrt(p_shoot_1_ * p_shoot_1_ + p_shoot_3_ * p_shoot_3_ + p_shoot_5_ * p_shoot_5_);
      p_shoot_1_ = p_shoot_1_ / (double)f;
      p_shoot_3_ = p_shoot_3_ / (double)f;
      p_shoot_5_ = p_shoot_5_ / (double)f;
      p_shoot_1_ = p_shoot_1_ + this.random.nextGaussian() * (double)(this.random.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_shoot_8_;
      p_shoot_3_ = p_shoot_3_ + this.random.nextGaussian() * (double)(this.random.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_shoot_8_;
      p_shoot_5_ = p_shoot_5_ + this.random.nextGaussian() * (double)(this.random.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_shoot_8_;
      p_shoot_1_ = p_shoot_1_ * (double)p_shoot_7_;
      p_shoot_3_ = p_shoot_3_ * (double)p_shoot_7_;
      p_shoot_5_ = p_shoot_5_ * (double)p_shoot_7_;
      this.motX = p_shoot_1_;
      this.motY = p_shoot_3_;
      this.motZ = p_shoot_5_;
      float f1 = MathHelper.sqrt(p_shoot_1_ * p_shoot_1_ + p_shoot_5_ * p_shoot_5_);
      this.lastYaw = this.yaw = (float)(MathHelper.b(p_shoot_1_, p_shoot_5_) * 180.0D / 3.1415927410125732D);
      this.lastPitch = this.pitch = (float)(MathHelper.b(p_shoot_3_, (double)f1) * 180.0D / 3.1415927410125732D);
      this.ar = 0;
   }

   public void t_() {
      super.t_();
      if(this.lastPitch == 0.0F && this.lastYaw == 0.0F) {
         float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
         this.lastYaw = this.yaw = (float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
         this.lastPitch = this.pitch = (float)(MathHelper.b(this.motY, (double)f) * 180.0D / 3.1415927410125732D);
      }

      BlockPosition blockposition = new BlockPosition(this.d, this.e, this.f);
      IBlockData iblockdata = this.world.getType(blockposition);
      Block block = iblockdata.getBlock();
      if(block.getMaterial() != Material.AIR) {
         block.updateShape(this.world, blockposition);
         AxisAlignedBB axisalignedbb = block.a(this.world, blockposition, iblockdata);
         if(axisalignedbb != null && axisalignedbb.a(new Vec3D(this.locX, this.locY, this.locZ))) {
            this.inGround = true;
         }
      }

      if(this.shake > 0) {
         --this.shake;
      }

      if(this.inGround) {
         int j = block.toLegacyData(iblockdata);
         if(block == this.g && j == this.h) {
            ++this.ar;
            if(this.ar >= this.world.spigotConfig.arrowDespawnRate) {
               this.die();
            }
         } else {
            this.inGround = false;
            this.motX *= (double)(this.random.nextFloat() * 0.2F);
            this.motY *= (double)(this.random.nextFloat() * 0.2F);
            this.motZ *= (double)(this.random.nextFloat() * 0.2F);
            this.ar = 0;
            this.as = 0;
         }
      } else {
         ++this.as;
         Vec3D vec3d1 = new Vec3D(this.locX, this.locY, this.locZ);
         Vec3D vec3d = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
         MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d1, vec3d, false, true, false);
         vec3d1 = new Vec3D(this.locX, this.locY, this.locZ);
         vec3d = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
         if(movingobjectposition != null) {
            vec3d = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
         }

         Entity entity = null;
         List list = this.world.getEntities(this, this.getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
         double d0 = 0.0D;

         for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            if(entity1.ad() && (entity1 != this.shooter || this.as >= 5)) {
               float f1 = 0.3F;
               AxisAlignedBB axisalignedbb1 = entity1.getBoundingBox().grow((double)f1, (double)f1, (double)f1);
               MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3d1, vec3d);
               if(movingobjectposition1 != null) {
                  double d1 = vec3d1.distanceSquared(movingobjectposition1.pos);
                  if(d1 < d0 || d0 == 0.0D) {
                     entity = entity1;
                     d0 = d1;
                  }
               }
            }
         }

         if(entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
         }

         if(movingobjectposition != null && movingobjectposition.entity != null && movingobjectposition.entity instanceof EntityHuman) {
            EntityHuman entityhuman = (EntityHuman)movingobjectposition.entity;
            if(entityhuman.abilities.isInvulnerable || this.shooter instanceof EntityHuman && !((EntityHuman)this.shooter).a(entityhuman)) {
               movingobjectposition = null;
            }
         }

         if(movingobjectposition != null) {
            CraftEventFactory.callProjectileHitEvent(this);
            if(movingobjectposition.entity == null) {
               BlockPosition blockposition1 = movingobjectposition.a();
               this.d = blockposition1.getX();
               this.e = blockposition1.getY();
               this.f = blockposition1.getZ();
               IBlockData iblockdata1 = this.world.getType(blockposition1);
               this.g = iblockdata1.getBlock();
               this.h = this.g.toLegacyData(iblockdata1);
               this.motX = (double)((float)(movingobjectposition.pos.a - this.locX));
               this.motY = (double)((float)(movingobjectposition.pos.b - this.locY));
               this.motZ = (double)((float)(movingobjectposition.pos.c - this.locZ));
               float f4 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
               this.locX -= this.motX / (double)f4 * 0.05000000074505806D;
               this.locY -= this.motY / (double)f4 * 0.05000000074505806D;
               this.locZ -= this.motZ / (double)f4 * 0.05000000074505806D;
               this.makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
               this.inGround = true;
               this.shake = 7;
               this.setCritical(false);
               if(this.g.getMaterial() != Material.AIR) {
                  this.g.a((World)this.world, blockposition1, (IBlockData)iblockdata1, (Entity)this);
               }
            } else {
               float f2 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
               int l = MathHelper.f((double)f2 * this.damage);
               if(this.isCritical()) {
                  l += this.random.nextInt(l / 2 + 2);
               }

               DamageSource damagesource;
               if(this.shooter == null) {
                  damagesource = DamageSource.arrow(this, this);
               } else {
                  damagesource = DamageSource.arrow(this, this.shooter);
               }

               if(!movingobjectposition.entity.damageEntity(damagesource, (float)l)) {
                  this.motX *= -0.10000000149011612D;
                  this.motY *= -0.10000000149011612D;
                  this.motZ *= -0.10000000149011612D;
                  this.yaw += 180.0F;
                  this.lastYaw += 180.0F;
                  this.as = 0;
               } else {
                  if(this.isBurning() && !(movingobjectposition.entity instanceof EntityEnderman) && (!(movingobjectposition.entity instanceof EntityPlayer) || !(this.shooter instanceof EntityPlayer) || this.world.pvpMode)) {
                     EntityCombustByEntityEvent entitycombustbyentityevent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 5);
                     Bukkit.getPluginManager().callEvent(entitycombustbyentityevent);
                     if(!entitycombustbyentityevent.isCancelled()) {
                        movingobjectposition.entity.setOnFire(entitycombustbyentityevent.getDuration());
                     }
                  }

                  if(movingobjectposition.entity instanceof EntityLiving) {
                     EntityLiving entityliving = (EntityLiving)movingobjectposition.entity;
                     if(!this.world.isClientSide) {
                        entityliving.o(entityliving.bv() + 1);
                     }

                     if(this.knockbackStrength > 0) {
                        float f6 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                        if(f6 > 0.0F) {
                           movingobjectposition.entity.g(this.motX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f6, 0.1D, this.motZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f6);
                        }
                     }

                     if(this.shooter instanceof EntityLiving) {
                        EnchantmentManager.a(entityliving, this.shooter);
                        EnchantmentManager.b((EntityLiving)this.shooter, entityliving);
                     }

                     if(this.shooter != null && movingobjectposition.entity != this.shooter && movingobjectposition.entity instanceof EntityHuman && this.shooter instanceof EntityPlayer) {
                        ((EntityPlayer)this.shooter).playerConnection.sendPacket(new PacketPlayOutGameStateChange(6, 0.0F));
                     }
                  }

                  this.makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                  if(!(movingobjectposition.entity instanceof EntityEnderman)) {
                     this.die();
                  }
               }
            }
         }

         if(this.isCritical()) {
            for(int k = 0; k < 4; ++k) {
               this.world.addParticle(EnumParticle.CRIT, this.locX + this.motX * (double)k / 4.0D, this.locY + this.motY * (double)k / 4.0D, this.locZ + this.motZ * (double)k / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ, new int[0]);
            }
         }

         this.locX += this.motX;
         this.locY += this.motY;
         this.locZ += this.motZ;
         float f3 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
         this.yaw = (float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);

         for(this.pitch = (float)(MathHelper.b(this.motY, (double)f3) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {
            ;
         }

         while(this.pitch - this.lastPitch >= 180.0F) {
            this.lastPitch += 360.0F;
         }

         while(this.yaw - this.lastYaw < -180.0F) {
            this.lastYaw -= 360.0F;
         }

         while(this.yaw - this.lastYaw >= 180.0F) {
            this.lastYaw += 360.0F;
         }

         this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
         this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
         float f8 = 0.99F;
         float f5 = 0.05F;
         if(this.V()) {
            for(int i1 = 0; i1 < 4; ++i1) {
               float f7 = 0.25F;
               this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * (double)f7, this.locY - this.motY * (double)f7, this.locZ - this.motZ * (double)f7, this.motX, this.motY, this.motZ, new int[0]);
            }

            f8 = 0.6F;
         }

         if(this.U()) {
            this.extinguish();
         }

         this.motX *= (double)f8;
         this.motY *= (double)f8;
         this.motZ *= (double)f8;
         this.motY -= (double)f5;
         this.setPosition(this.locX, this.locY, this.locZ);
         this.checkBlockCollisions();
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      p_b_1_.setShort("xTile", (short)this.d);
      p_b_1_.setShort("yTile", (short)this.e);
      p_b_1_.setShort("zTile", (short)this.f);
      p_b_1_.setShort("life", (short)this.ar);
      MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.g);
      p_b_1_.setString("inTile", minecraftkey == null?"":minecraftkey.toString());
      p_b_1_.setByte("inData", (byte)this.h);
      p_b_1_.setByte("shake", (byte)this.shake);
      p_b_1_.setByte("inGround", (byte)(this.inGround?1:0));
      p_b_1_.setByte("pickup", (byte)this.fromPlayer);
      p_b_1_.setDouble("damage", this.damage);
   }

   public void a(NBTTagCompound p_a_1_) {
      this.d = p_a_1_.getShort("xTile");
      this.e = p_a_1_.getShort("yTile");
      this.f = p_a_1_.getShort("zTile");
      this.ar = p_a_1_.getShort("life");
      if(p_a_1_.hasKeyOfType("inTile", 8)) {
         this.g = Block.getByName(p_a_1_.getString("inTile"));
      } else {
         this.g = Block.getById(p_a_1_.getByte("inTile") & 255);
      }

      this.h = p_a_1_.getByte("inData") & 255;
      this.shake = p_a_1_.getByte("shake") & 255;
      this.inGround = p_a_1_.getByte("inGround") == 1;
      if(p_a_1_.hasKeyOfType("damage", 99)) {
         this.damage = p_a_1_.getDouble("damage");
      }

      if(p_a_1_.hasKeyOfType("pickup", 99)) {
         this.fromPlayer = p_a_1_.getByte("pickup");
      } else if(p_a_1_.hasKeyOfType("player", 99)) {
         this.fromPlayer = p_a_1_.getBoolean("player")?1:0;
      }

   }

   public void d(EntityHuman p_d_1_) {
      if(!this.world.isClientSide && this.inGround && this.shake <= 0) {
         ItemStack itemstack = new ItemStack(Items.ARROW);
         if(this.fromPlayer == 1 && p_d_1_.inventory.canHold(itemstack) > 0) {
            EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, itemstack);
            PlayerPickupItemEvent playerpickupitemevent = new PlayerPickupItemEvent((Player)p_d_1_.getBukkitEntity(), new CraftItem(this.world.getServer(), this, entityitem), 0);
            this.world.getServer().getPluginManager().callEvent(playerpickupitemevent);
            if(playerpickupitemevent.isCancelled()) {
               return;
            }
         }

         boolean flag = this.fromPlayer == 1 || this.fromPlayer == 2 && p_d_1_.abilities.canInstantlyBuild;
         if(this.fromPlayer == 1 && !p_d_1_.inventory.pickup(new ItemStack(Items.ARROW, 1))) {
            flag = false;
         }

         if(flag) {
            this.makeSound("random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            p_d_1_.receive(this, 1);
            this.die();
         }
      }

   }

   protected boolean s_() {
      return false;
   }

   public void b(double p_b_1_) {
      this.damage = p_b_1_;
   }

   public double j() {
      return this.damage;
   }

   public void setKnockbackStrength(int p_setKnockbackStrength_1_) {
      this.knockbackStrength = p_setKnockbackStrength_1_;
   }

   public boolean aD() {
      return false;
   }

   public float getHeadHeight() {
      return 0.0F;
   }

   public void setCritical(boolean p_setCritical_1_) {
      byte b0 = this.datawatcher.getByte(16);
      if(p_setCritical_1_) {
         this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 1)));
      } else {
         this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & -2)));
      }

   }

   public boolean isCritical() {
      byte b0 = this.datawatcher.getByte(16);
      return (b0 & 1) != 0;
   }

   public boolean isInGround() {
      return this.inGround;
   }
}

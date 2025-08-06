package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.ItemFish;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PossibleFishingResult;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.WeightedRandom;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

public class EntityFishingHook extends Entity {
   private static final List<PossibleFishingResult> d = Arrays.asList(new PossibleFishingResult[]{(new PossibleFishingResult(new ItemStack(Items.LEATHER_BOOTS), 10)).a(0.9F), new PossibleFishingResult(new ItemStack(Items.LEATHER), 10), new PossibleFishingResult(new ItemStack(Items.BONE), 10), new PossibleFishingResult(new ItemStack(Items.POTION), 10), new PossibleFishingResult(new ItemStack(Items.STRING), 5), (new PossibleFishingResult(new ItemStack(Items.FISHING_ROD), 2)).a(0.9F), new PossibleFishingResult(new ItemStack(Items.BOWL), 10), new PossibleFishingResult(new ItemStack(Items.STICK), 5), new PossibleFishingResult(new ItemStack(Items.DYE, 10, EnumColor.BLACK.getInvColorIndex()), 1), new PossibleFishingResult(new ItemStack(Blocks.TRIPWIRE_HOOK), 10), new PossibleFishingResult(new ItemStack(Items.ROTTEN_FLESH), 10)});
   private static final List<PossibleFishingResult> e = Arrays.asList(new PossibleFishingResult[]{new PossibleFishingResult(new ItemStack(Blocks.WATERLILY), 1), new PossibleFishingResult(new ItemStack(Items.NAME_TAG), 1), new PossibleFishingResult(new ItemStack(Items.SADDLE), 1), (new PossibleFishingResult(new ItemStack(Items.BOW), 1)).a(0.25F).a(), (new PossibleFishingResult(new ItemStack(Items.FISHING_ROD), 1)).a(0.25F).a(), (new PossibleFishingResult(new ItemStack(Items.BOOK), 1)).a()});
   private static final List<PossibleFishingResult> f = Arrays.asList(new PossibleFishingResult[]{new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.COD.a()), 60), new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.SALMON.a()), 25), new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.CLOWNFISH.a()), 2), new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.PUFFERFISH.a()), 13)});
   private int g = -1;
   private int h = -1;
   private int i = -1;
   private Block ar;
   private boolean as;
   public int a;
   public EntityHuman owner;
   private int at;
   private int au;
   private int av;
   private int aw;
   private int ax;
   private float ay;
   public Entity hooked;
   private int az;
   private double aA;
   private double aB;
   private double aC;
   private double aD;
   private double aE;

   public static List<PossibleFishingResult> j() {
      return f;
   }

   public EntityFishingHook(World p_i77_1_) {
      super(p_i77_1_);
      this.setSize(0.25F, 0.25F);
      this.ah = true;
   }

   public EntityFishingHook(World p_i78_1_, EntityHuman p_i78_2_) {
      super(p_i78_1_);
      this.ah = true;
      this.owner = p_i78_2_;
      this.owner.hookedFish = this;
      this.setSize(0.25F, 0.25F);
      this.setPositionRotation(p_i78_2_.locX, p_i78_2_.locY + (double)p_i78_2_.getHeadHeight(), p_i78_2_.locZ, p_i78_2_.yaw, p_i78_2_.pitch);
      this.locX -= (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
      this.locY -= 0.10000000149011612D;
      this.locZ -= (double)(MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
      this.setPosition(this.locX, this.locY, this.locZ);
      float f = 0.4F;
      this.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
      this.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
      this.motY = (double)(-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
      this.c(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
   }

   protected void h() {
   }

   public void c(double p_c_1_, double p_c_3_, double p_c_5_, float p_c_7_, float p_c_8_) {
      float f = MathHelper.sqrt(p_c_1_ * p_c_1_ + p_c_3_ * p_c_3_ + p_c_5_ * p_c_5_);
      p_c_1_ = p_c_1_ / (double)f;
      p_c_3_ = p_c_3_ / (double)f;
      p_c_5_ = p_c_5_ / (double)f;
      p_c_1_ = p_c_1_ + this.random.nextGaussian() * 0.007499999832361937D * (double)p_c_8_;
      p_c_3_ = p_c_3_ + this.random.nextGaussian() * 0.007499999832361937D * (double)p_c_8_;
      p_c_5_ = p_c_5_ + this.random.nextGaussian() * 0.007499999832361937D * (double)p_c_8_;
      p_c_1_ = p_c_1_ * (double)p_c_7_;
      p_c_3_ = p_c_3_ * (double)p_c_7_;
      p_c_5_ = p_c_5_ * (double)p_c_7_;
      this.motX = p_c_1_;
      this.motY = p_c_3_;
      this.motZ = p_c_5_;
      float f1 = MathHelper.sqrt(p_c_1_ * p_c_1_ + p_c_5_ * p_c_5_);
      this.lastYaw = this.yaw = (float)(MathHelper.b(p_c_1_, p_c_5_) * 180.0D / 3.1415927410125732D);
      this.lastPitch = this.pitch = (float)(MathHelper.b(p_c_3_, (double)f1) * 180.0D / 3.1415927410125732D);
      this.at = 0;
   }

   public void t_() {
      super.t_();
      if(this.az > 0) {
         double d0 = this.locX + (this.aA - this.locX) / (double)this.az;
         double d1 = this.locY + (this.aB - this.locY) / (double)this.az;
         double d2 = this.locZ + (this.aC - this.locZ) / (double)this.az;
         double d3 = MathHelper.g(this.aD - (double)this.yaw);
         this.yaw = (float)((double)this.yaw + d3 / (double)this.az);
         this.pitch = (float)((double)this.pitch + (this.aE - (double)this.pitch) / (double)this.az);
         --this.az;
         this.setPosition(d0, d1, d2);
         this.setYawPitch(this.yaw, this.pitch);
      } else {
         if(!this.world.isClientSide) {
            ItemStack itemstack = this.owner.bZ();
            if(this.owner.dead || !this.owner.isAlive() || itemstack == null || itemstack.getItem() != Items.FISHING_ROD || this.h(this.owner) > 1024.0D) {
               this.die();
               this.owner.hookedFish = null;
               return;
            }

            if(this.hooked != null) {
               if(!this.hooked.dead) {
                  this.locX = this.hooked.locX;
                  double d4 = (double)this.hooked.length;
                  this.locY = this.hooked.getBoundingBox().b + d4 * 0.8D;
                  this.locZ = this.hooked.locZ;
                  return;
               }

               this.hooked = null;
            }
         }

         if(this.a > 0) {
            --this.a;
         }

         if(this.as) {
            if(this.world.getType(new BlockPosition(this.g, this.h, this.i)).getBlock() == this.ar) {
               ++this.at;
               if(this.at == 1200) {
                  this.die();
               }

               return;
            }

            this.as = false;
            this.motX *= (double)(this.random.nextFloat() * 0.2F);
            this.motY *= (double)(this.random.nextFloat() * 0.2F);
            this.motZ *= (double)(this.random.nextFloat() * 0.2F);
            this.at = 0;
            this.au = 0;
         } else {
            ++this.au;
         }

         Vec3D vec3d1 = new Vec3D(this.locX, this.locY, this.locZ);
         Vec3D vec3d = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
         MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d1, vec3d);
         vec3d1 = new Vec3D(this.locX, this.locY, this.locZ);
         vec3d = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
         if(movingobjectposition != null) {
            vec3d = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
         }

         Entity entity = null;
         List list = this.world.getEntities(this, this.getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
         double d5 = 0.0D;

         for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            if(entity1.ad() && (entity1 != this.owner || this.au >= 5)) {
               float f = 0.3F;
               AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double)f, (double)f, (double)f);
               MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d1, vec3d);
               if(movingobjectposition1 != null) {
                  double d6 = vec3d1.distanceSquared(movingobjectposition1.pos);
                  if(d6 < d5 || d5 == 0.0D) {
                     entity = entity1;
                     d5 = d6;
                  }
               }
            }
         }

         if(entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
         }

         if(movingobjectposition != null) {
            CraftEventFactory.callProjectileHitEvent(this);
            if(movingobjectposition.entity != null) {
               if(movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.owner), 0.0F)) {
                  this.hooked = movingobjectposition.entity;
               }
            } else {
               this.as = true;
            }
         }

         if(!this.as) {
            this.move(this.motX, this.motY, this.motZ);
            float f6 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.yaw = (float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);

            for(this.pitch = (float)(MathHelper.b(this.motY, (double)f6) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {
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
            float f7 = 0.92F;
            if(this.onGround || this.positionChanged) {
               f7 = 0.5F;
            }

            byte b0 = 5;
            double d7 = 0.0D;

            for(int j = 0; j < b0; ++j) {
               AxisAlignedBB axisalignedbb1 = this.getBoundingBox();
               double d8 = axisalignedbb1.e - axisalignedbb1.b;
               double d9 = axisalignedbb1.b + d8 * (double)j / (double)b0;
               double d10 = axisalignedbb1.b + d8 * (double)(j + 1) / (double)b0;
               AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb1.a, d9, axisalignedbb1.c, axisalignedbb1.d, d10, axisalignedbb1.f);
               if(this.world.b(axisalignedbb2, Material.WATER)) {
                  d7 += 1.0D / (double)b0;
               }
            }

            if(!this.world.isClientSide && d7 > 0.0D) {
               WorldServer worldserver = (WorldServer)this.world;
               int k = 1;
               BlockPosition blockposition = (new BlockPosition(this)).up();
               if(this.random.nextFloat() < 0.25F && this.world.isRainingAt(blockposition)) {
                  k = 2;
               }

               if(this.random.nextFloat() < 0.5F && !this.world.i(blockposition)) {
                  --k;
               }

               if(this.av > 0) {
                  --this.av;
                  if(this.av <= 0) {
                     this.aw = 0;
                     this.ax = 0;
                  }
               } else if(this.ax > 0) {
                  this.ax -= k;
                  if(this.ax <= 0) {
                     this.motY -= 0.20000000298023224D;
                     this.makeSound("random.splash", 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                     float f1 = (float)MathHelper.floor(this.getBoundingBox().b);
                     worldserver.a(EnumParticle.WATER_BUBBLE, this.locX, (double)(f1 + 1.0F), this.locZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0D, (double)this.width, 0.20000000298023224D, new int[0]);
                     worldserver.a(EnumParticle.WATER_WAKE, this.locX, (double)(f1 + 1.0F), this.locZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0D, (double)this.width, 0.20000000298023224D, new int[0]);
                     this.av = MathHelper.nextInt(this.random, 10, 30);
                  } else {
                     this.ay = (float)((double)this.ay + this.random.nextGaussian() * 4.0D);
                     float f8 = this.ay * 0.017453292F;
                     float f2 = MathHelper.sin(f8);
                     float f3 = MathHelper.cos(f8);
                     double d14 = this.locX + (double)(f2 * (float)this.ax * 0.1F);
                     double d11 = (double)((float)MathHelper.floor(this.getBoundingBox().b) + 1.0F);
                     double d12 = this.locZ + (double)(f3 * (float)this.ax * 0.1F);
                     Block block = worldserver.getType(new BlockPosition((int)d14, (int)d11 - 1, (int)d12)).getBlock();
                     if(block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
                        if(this.random.nextFloat() < 0.15F) {
                           worldserver.a(EnumParticle.WATER_BUBBLE, d14, d11 - 0.10000000149011612D, d12, 1, (double)f2, 0.1D, (double)f3, 0.0D, new int[0]);
                        }

                        float f4 = f2 * 0.04F;
                        float f5 = f3 * 0.04F;
                        worldserver.a(EnumParticle.WATER_WAKE, d14, d11, d12, 0, (double)f5, 0.01D, (double)(-f4), 1.0D, new int[0]);
                        worldserver.a(EnumParticle.WATER_WAKE, d14, d11, d12, 0, (double)(-f5), 0.01D, (double)f4, 1.0D, new int[0]);
                     }
                  }
               } else if(this.aw > 0) {
                  this.aw -= k;
                  float f9 = 0.15F;
                  if(this.aw < 20) {
                     f9 = (float)((double)f9 + (double)(20 - this.aw) * 0.05D);
                  } else if(this.aw < 40) {
                     f9 = (float)((double)f9 + (double)(40 - this.aw) * 0.02D);
                  } else if(this.aw < 60) {
                     f9 = (float)((double)f9 + (double)(60 - this.aw) * 0.01D);
                  }

                  if(this.random.nextFloat() < f9) {
                     float f10 = MathHelper.a(this.random, 0.0F, 360.0F) * 0.017453292F;
                     float f11 = MathHelper.a(this.random, 25.0F, 60.0F);
                     double d15 = this.locX + (double)(MathHelper.sin(f10) * f11 * 0.1F);
                     double d16 = (double)((float)MathHelper.floor(this.getBoundingBox().b) + 1.0F);
                     double d17 = this.locZ + (double)(MathHelper.cos(f10) * f11 * 0.1F);
                     Block block1 = worldserver.getType(new BlockPosition((int)d15, (int)d16 - 1, (int)d17)).getBlock();
                     if(block1 == Blocks.WATER || block1 == Blocks.FLOWING_WATER) {
                        worldserver.a(EnumParticle.WATER_SPLASH, d15, d16, d17, 2 + this.random.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
                     }
                  }

                  if(this.aw <= 0) {
                     this.ay = MathHelper.a(this.random, 0.0F, 360.0F);
                     this.ax = MathHelper.nextInt(this.random, 20, 80);
                  }
               } else {
                  this.aw = MathHelper.nextInt(this.random, 100, 900);
                  this.aw -= EnchantmentManager.h(this.owner) * 20 * 5;
               }

               if(this.av > 0) {
                  this.motY -= (double)(this.random.nextFloat() * this.random.nextFloat() * this.random.nextFloat()) * 0.2D;
               }
            }

            double d13 = d7 * 2.0D - 1.0D;
            this.motY += 0.03999999910593033D * d13;
            if(d7 > 0.0D) {
               f7 = (float)((double)f7 * 0.9D);
               this.motY *= 0.8D;
            }

            this.motX *= (double)f7;
            this.motY *= (double)f7;
            this.motZ *= (double)f7;
            this.setPosition(this.locX, this.locY, this.locZ);
         }
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      p_b_1_.setShort("xTile", (short)this.g);
      p_b_1_.setShort("yTile", (short)this.h);
      p_b_1_.setShort("zTile", (short)this.i);
      MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.ar);
      p_b_1_.setString("inTile", minecraftkey == null?"":minecraftkey.toString());
      p_b_1_.setByte("shake", (byte)this.a);
      p_b_1_.setByte("inGround", (byte)(this.as?1:0));
   }

   public void a(NBTTagCompound p_a_1_) {
      this.g = p_a_1_.getShort("xTile");
      this.h = p_a_1_.getShort("yTile");
      this.i = p_a_1_.getShort("zTile");
      if(p_a_1_.hasKeyOfType("inTile", 8)) {
         this.ar = Block.getByName(p_a_1_.getString("inTile"));
      } else {
         this.ar = Block.getById(p_a_1_.getByte("inTile") & 255);
      }

      this.a = p_a_1_.getByte("shake") & 255;
      this.as = p_a_1_.getByte("inGround") == 1;
   }

   public int l() {
      if(this.world.isClientSide) {
         return 0;
      } else {
         byte b0 = 0;
         if(this.hooked != null) {
            PlayerFishEvent playerfishevent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), this.hooked.getBukkitEntity(), (Fish)this.getBukkitEntity(), State.CAUGHT_ENTITY);
            this.world.getServer().getPluginManager().callEvent(playerfishevent);
            if(playerfishevent.isCancelled()) {
               return 0;
            }

            double d0 = this.owner.locX - this.locX;
            double d1 = this.owner.locY - this.locY;
            double d2 = this.owner.locZ - this.locZ;
            double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            double d4 = 0.1D;
            this.hooked.motX += d0 * d4;
            this.hooked.motY += d1 * d4 + (double)MathHelper.sqrt(d3) * 0.08D;
            this.hooked.motZ += d2 * d4;
            b0 = 3;
         } else if(this.av > 0) {
            EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, this.m());
            PlayerFishEvent playerfishevent1 = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), entityitem.getBukkitEntity(), (Fish)this.getBukkitEntity(), State.CAUGHT_FISH);
            playerfishevent1.setExpToDrop(this.random.nextInt(6) + 1);
            this.world.getServer().getPluginManager().callEvent(playerfishevent1);
            if(playerfishevent1.isCancelled()) {
               return 0;
            }

            double d5 = this.owner.locX - this.locX;
            double d6 = this.owner.locY - this.locY;
            double d7 = this.owner.locZ - this.locZ;
            double d8 = (double)MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
            double d9 = 0.1D;
            entityitem.motX = d5 * d9;
            entityitem.motY = d6 * d9 + (double)MathHelper.sqrt(d8) * 0.08D;
            entityitem.motZ = d7 * d9;
            this.world.addEntity(entityitem);
            if(playerfishevent1.getExpToDrop() > 0) {
               this.owner.world.addEntity(new EntityExperienceOrb(this.owner.world, this.owner.locX, this.owner.locY + 0.5D, this.owner.locZ + 0.5D, playerfishevent1.getExpToDrop()));
            }

            b0 = 1;
         }

         if(this.as) {
            PlayerFishEvent playerfishevent2 = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), (org.bukkit.entity.Entity)null, (Fish)this.getBukkitEntity(), State.IN_GROUND);
            this.world.getServer().getPluginManager().callEvent(playerfishevent2);
            if(playerfishevent2.isCancelled()) {
               return 0;
            }

            b0 = 2;
         }

         if(b0 == 0) {
            PlayerFishEvent playerfishevent3 = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), (org.bukkit.entity.Entity)null, (Fish)this.getBukkitEntity(), State.FAILED_ATTEMPT);
            this.world.getServer().getPluginManager().callEvent(playerfishevent3);
            if(playerfishevent3.isCancelled()) {
               return 0;
            }
         }

         this.die();
         this.owner.hookedFish = null;
         return b0;
      }
   }

   private ItemStack m() {
      float f = this.world.random.nextFloat();
      int i = EnchantmentManager.g(this.owner);
      int j = EnchantmentManager.h(this.owner);
      float f1 = 0.1F - (float)i * 0.025F - (float)j * 0.01F;
      float f2 = 0.05F + (float)i * 0.01F - (float)j * 0.01F;
      f1 = MathHelper.a(f1, 0.0F, 1.0F);
      f2 = MathHelper.a(f2, 0.0F, 1.0F);
      if(f < f1) {
         this.owner.b(StatisticList.D);
         return ((PossibleFishingResult)WeightedRandom.a(this.random, d)).a(this.random);
      } else {
         f = f - f1;
         if(f < f2) {
            this.owner.b(StatisticList.E);
            return ((PossibleFishingResult)WeightedRandom.a(this.random, e)).a(this.random);
         } else {
            this.owner.b(StatisticList.C);
            return ((PossibleFishingResult)WeightedRandom.a(this.random, f)).a(this.random);
         }
      }
   }

   public void die() {
      super.die();
      if(this.owner != null) {
         this.owner.hookedFish = null;
      }

   }
}

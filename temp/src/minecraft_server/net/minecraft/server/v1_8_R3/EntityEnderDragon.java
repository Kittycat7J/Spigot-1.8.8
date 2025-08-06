package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockTorch;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityDamageSource;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IComplex;
import net.minecraft.server.v1_8_R3.IMonster;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.PortalType;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityEnderDragon extends EntityInsentient implements IComplex, IMonster {
   public double a;
   public double b;
   public double c;
   public double[][] bk = new double[64][3];
   public int bl = -1;
   public EntityComplexPart[] children;
   public EntityComplexPart bn;
   public EntityComplexPart bo;
   public EntityComplexPart bp;
   public EntityComplexPart bq;
   public EntityComplexPart br;
   public EntityComplexPart bs;
   public EntityComplexPart bt;
   public float bu;
   public float bv;
   public boolean bw;
   public boolean bx;
   public Entity target;
   public int by;
   public EntityEnderCrystal bz;
   private Explosion explosionSource = new Explosion((World)null, this, Double.NaN, Double.NaN, Double.NaN, Float.NaN, true, true);

   public EntityEnderDragon(World p_i335_1_) {
      super(p_i335_1_);
      this.children = new EntityComplexPart[]{this.bn = new EntityComplexPart(this, "head", 6.0F, 6.0F), this.bo = new EntityComplexPart(this, "body", 8.0F, 8.0F), this.bp = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bq = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.br = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bs = new EntityComplexPart(this, "wing", 4.0F, 4.0F), this.bt = new EntityComplexPart(this, "wing", 4.0F, 4.0F)};
      this.setHealth(this.getMaxHealth());
      this.setSize(16.0F, 8.0F);
      this.noclip = true;
      this.fireProof = true;
      this.b = 100.0D;
      this.ah = true;
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.maxHealth).setValue(200.0D);
   }

   protected void h() {
      super.h();
   }

   public double[] b(int p_b_1_, float p_b_2_) {
      if(this.getHealth() <= 0.0F) {
         p_b_2_ = 0.0F;
      }

      p_b_2_ = 1.0F - p_b_2_;
      int i = this.bl - p_b_1_ * 1 & 63;
      int j = this.bl - p_b_1_ * 1 - 1 & 63;
      double[] adouble = new double[3];
      double d0 = this.bk[i][0];
      double d1 = MathHelper.g(this.bk[j][0] - d0);
      adouble[0] = d0 + d1 * (double)p_b_2_;
      d0 = this.bk[i][1];
      d1 = this.bk[j][1] - d0;
      adouble[1] = d0 + d1 * (double)p_b_2_;
      adouble[2] = this.bk[i][2] + (this.bk[j][2] - this.bk[i][2]) * (double)p_b_2_;
      return adouble;
   }

   public void m() {
      if(this.world.isClientSide) {
         float f = MathHelper.cos(this.bv * 3.1415927F * 2.0F);
         float f1 = MathHelper.cos(this.bu * 3.1415927F * 2.0F);
         if(f1 <= -0.3F && f >= -0.3F && !this.R()) {
            this.world.a(this.locX, this.locY, this.locZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
         }
      }

      this.bu = this.bv;
      if(this.getHealth() <= 0.0F) {
         float f15 = (this.random.nextFloat() - 0.5F) * 8.0F;
         float f17 = (this.random.nextFloat() - 0.5F) * 4.0F;
         float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
         this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX + (double)f15, this.locY + 2.0D + (double)f17, this.locZ + (double)f2, 0.0D, 0.0D, 0.0D, new int[0]);
      } else {
         this.n();
         float f16 = 0.2F / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0F + 1.0F);
         f16 = f16 * (float)Math.pow(2.0D, this.motY);
         if(this.bx) {
            this.bv += f16 * 0.5F;
         } else {
            this.bv += f16;
         }

         this.yaw = MathHelper.g(this.yaw);
         if(this.ce()) {
            this.bv = 0.5F;
         } else {
            if(this.bl < 0) {
               for(int i = 0; i < this.bk.length; ++i) {
                  this.bk[i][0] = (double)this.yaw;
                  this.bk[i][1] = this.locY;
               }
            }

            if(++this.bl == this.bk.length) {
               this.bl = 0;
            }

            this.bk[this.bl][0] = (double)this.yaw;
            this.bk[this.bl][1] = this.locY;
            if(this.world.isClientSide) {
               if(this.bc > 0) {
                  double d9 = this.locX + (this.bd - this.locX) / (double)this.bc;
                  double d10 = this.locY + (this.be - this.locY) / (double)this.bc;
                  double d11 = this.locZ + (this.bf - this.locZ) / (double)this.bc;
                  double d12 = MathHelper.g(this.bg - (double)this.yaw);
                  this.yaw = (float)((double)this.yaw + d12 / (double)this.bc);
                  this.pitch = (float)((double)this.pitch + (this.bh - (double)this.pitch) / (double)this.bc);
                  --this.bc;
                  this.setPosition(d9, d10, d11);
                  this.setYawPitch(this.yaw, this.pitch);
               }
            } else {
               double d0 = this.a - this.locX;
               double d1 = this.b - this.locY;
               double d2 = this.c - this.locZ;
               double d3 = d0 * d0 + d1 * d1 + d2 * d2;
               if(this.target != null) {
                  this.a = this.target.locX;
                  this.c = this.target.locZ;
                  double d4 = this.a - this.locX;
                  double d5 = this.c - this.locZ;
                  double d6 = Math.sqrt(d4 * d4 + d5 * d5);
                  double d7 = 0.4000000059604645D + d6 / 80.0D - 1.0D;
                  if(d7 > 10.0D) {
                     d7 = 10.0D;
                  }

                  this.b = this.target.getBoundingBox().b + d7;
               } else {
                  this.a += this.random.nextGaussian() * 2.0D;
                  this.c += this.random.nextGaussian() * 2.0D;
               }

               if(this.bw || d3 < 100.0D || d3 > 22500.0D || this.positionChanged || this.E) {
                  this.cf();
               }

               d1 = d1 / (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
               float f3 = 0.6F;
               d1 = MathHelper.a(d1, (double)(-f3), (double)f3);
               this.motY += d1 * 0.10000000149011612D;
               this.yaw = MathHelper.g(this.yaw);
               double d13 = 180.0D - MathHelper.b(d0, d2) * 180.0D / 3.1415927410125732D;
               double d14 = MathHelper.g(d13 - (double)this.yaw);
               if(d14 > 50.0D) {
                  d14 = 50.0D;
               }

               if(d14 < -50.0D) {
                  d14 = -50.0D;
               }

               Vec3D vec3d = (new Vec3D(this.a - this.locX, this.b - this.locY, this.c - this.locZ)).a();
               double d15 = (double)(-MathHelper.cos(this.yaw * 3.1415927F / 180.0F));
               Vec3D vec3d1 = (new Vec3D((double)MathHelper.sin(this.yaw * 3.1415927F / 180.0F), this.motY, d15)).a();
               float f4 = ((float)vec3d1.b(vec3d) + 0.5F) / 1.5F;
               if(f4 < 0.0F) {
                  f4 = 0.0F;
               }

               this.bb *= 0.8F;
               float f5 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0F + 1.0F;
               double d8 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0D + 1.0D;
               if(d8 > 40.0D) {
                  d8 = 40.0D;
               }

               this.bb = (float)((double)this.bb + d14 * (0.699999988079071D / d8 / (double)f5));
               this.yaw += this.bb * 0.1F;
               float f6 = (float)(2.0D / (d8 + 1.0D));
               float f7 = 0.06F;
               this.a(0.0F, -1.0F, f7 * (f4 * f6 + (1.0F - f6)));
               if(this.bx) {
                  this.move(this.motX * 0.800000011920929D, this.motY * 0.800000011920929D, this.motZ * 0.800000011920929D);
               } else {
                  this.move(this.motX, this.motY, this.motZ);
               }

               Vec3D vec3d2 = (new Vec3D(this.motX, this.motY, this.motZ)).a();
               float f8 = ((float)vec3d2.b(vec3d1) + 1.0F) / 2.0F;
               f8 = 0.8F + 0.15F * f8;
               this.motX *= (double)f8;
               this.motZ *= (double)f8;
               this.motY *= 0.9100000262260437D;
            }

            this.aI = this.yaw;
            this.bn.width = this.bn.length = 3.0F;
            this.bp.width = this.bp.length = 2.0F;
            this.bq.width = this.bq.length = 2.0F;
            this.br.width = this.br.length = 2.0F;
            this.bo.length = 3.0F;
            this.bo.width = 5.0F;
            this.bs.length = 2.0F;
            this.bs.width = 4.0F;
            this.bt.length = 3.0F;
            this.bt.width = 4.0F;
            float f18 = (float)(this.b(5, 1.0F)[1] - this.b(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
            float f19 = MathHelper.cos(f18);
            float f9 = -MathHelper.sin(f18);
            float f10 = this.yaw * 3.1415927F / 180.0F;
            float f11 = MathHelper.sin(f10);
            float f12 = MathHelper.cos(f10);
            this.bo.t_();
            this.bo.setPositionRotation(this.locX + (double)(f11 * 0.5F), this.locY, this.locZ - (double)(f12 * 0.5F), 0.0F, 0.0F);
            this.bs.t_();
            this.bs.setPositionRotation(this.locX + (double)(f12 * 4.5F), this.locY + 2.0D, this.locZ + (double)(f11 * 4.5F), 0.0F, 0.0F);
            this.bt.t_();
            this.bt.setPositionRotation(this.locX - (double)(f12 * 4.5F), this.locY + 2.0D, this.locZ - (double)(f11 * 4.5F), 0.0F, 0.0F);
            if(!this.world.isClientSide && this.hurtTicks == 0) {
               this.a(this.world.getEntities(this, this.bs.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
               this.a(this.world.getEntities(this, this.bt.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
               this.b(this.world.getEntities(this, this.bn.getBoundingBox().grow(1.0D, 1.0D, 1.0D)));
            }

            double[] adouble = this.b(5, 1.0F);
            double[] adouble1 = this.b(0, 1.0F);
            float f20 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F - this.bb * 0.01F);
            float f21 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F - this.bb * 0.01F);
            this.bn.t_();
            this.bn.setPositionRotation(this.locX + (double)(f20 * 5.5F * f19), this.locY + (adouble1[1] - adouble[1]) * 1.0D + (double)(f9 * 5.5F), this.locZ - (double)(f21 * 5.5F * f19), 0.0F, 0.0F);

            for(int j = 0; j < 3; ++j) {
               EntityComplexPart entitycomplexpart = null;
               if(j == 0) {
                  entitycomplexpart = this.bp;
               }

               if(j == 1) {
                  entitycomplexpart = this.bq;
               }

               if(j == 2) {
                  entitycomplexpart = this.br;
               }

               double[] adouble2 = this.b(12 + j * 2, 1.0F);
               float f13 = this.yaw * 3.1415927F / 180.0F + this.b(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
               float f14 = MathHelper.sin(f13);
               float f22 = MathHelper.cos(f13);
               float f23 = 1.5F;
               float f24 = (float)(j + 1) * 2.0F;
               entitycomplexpart.t_();
               entitycomplexpart.setPositionRotation(this.locX - (double)((f11 * f23 + f14 * f24) * f19), this.locY + (adouble2[1] - adouble[1]) * 1.0D - (double)((f24 + f23) * f9) + 1.5D, this.locZ + (double)((f12 * f23 + f22 * f24) * f19), 0.0F, 0.0F);
            }

            if(!this.world.isClientSide) {
               this.bx = this.b(this.bn.getBoundingBox()) | this.b(this.bo.getBoundingBox());
            }
         }
      }

   }

   private void n() {
      if(this.bz != null) {
         if(this.bz.dead) {
            if(!this.world.isClientSide) {
               CraftEventFactory.entityDamage = this.bz;
               this.a(this.bn, DamageSource.explosion((Explosion)null), 10.0F);
               CraftEventFactory.entityDamage = null;
            }

            this.bz = null;
         } else if(this.ticksLived % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
            EntityRegainHealthEvent entityregainhealthevent = new EntityRegainHealthEvent(this.getBukkitEntity(), 1.0D, RegainReason.ENDER_CRYSTAL);
            this.world.getServer().getPluginManager().callEvent(entityregainhealthevent);
            if(!entityregainhealthevent.isCancelled()) {
               this.setHealth((float)((double)this.getHealth() + entityregainhealthevent.getAmount()));
            }
         }
      }

      if(this.random.nextInt(10) == 0) {
         float f = 32.0F;
         List list = this.world.a(EntityEnderCrystal.class, this.getBoundingBox().grow((double)f, (double)f, (double)f));
         EntityEnderCrystal entityendercrystal = null;
         double d0 = Double.MAX_VALUE;

         for(EntityEnderCrystal entityendercrystal1 : list) {
            double d1 = entityendercrystal1.h(this);
            if(d1 < d0) {
               d0 = d1;
               entityendercrystal = entityendercrystal1;
            }
         }

         this.bz = entityendercrystal;
      }

   }

   private void a(List<Entity> p_a_1_) {
      double d0 = (this.bo.getBoundingBox().a + this.bo.getBoundingBox().d) / 2.0D;
      double d1 = (this.bo.getBoundingBox().c + this.bo.getBoundingBox().f) / 2.0D;

      for(Entity entity : p_a_1_) {
         if(entity instanceof EntityLiving) {
            double d2 = entity.locX - d0;
            double d3 = entity.locZ - d1;
            double d4 = d2 * d2 + d3 * d3;
            entity.g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
         }
      }

   }

   private void b(List<Entity> p_b_1_) {
      for(int i = 0; i < p_b_1_.size(); ++i) {
         Entity entity = (Entity)p_b_1_.get(i);
         if(entity instanceof EntityLiving) {
            entity.damageEntity(DamageSource.mobAttack(this), 10.0F);
            this.a(this, entity);
         }
      }

   }

   private void cf() {
      this.bw = false;
      ArrayList arraylist = Lists.newArrayList(this.world.players);
      Iterator iterator = arraylist.iterator();

      while(iterator.hasNext()) {
         if(((EntityHuman)iterator.next()).isSpectator()) {
            iterator.remove();
         }
      }

      if(this.random.nextInt(2) == 0 && !arraylist.isEmpty()) {
         Entity entity = (Entity)this.world.players.get(this.random.nextInt(this.world.players.size()));
         EntityTargetEvent entitytargetevent = new EntityTargetEvent(this.getBukkitEntity(), entity.getBukkitEntity(), TargetReason.RANDOM_TARGET);
         this.world.getServer().getPluginManager().callEvent(entitytargetevent);
         if(!entitytargetevent.isCancelled()) {
            if(entitytargetevent.getTarget() == null) {
               this.target = null;
            } else {
               this.target = ((CraftEntity)entitytargetevent.getTarget()).getHandle();
            }
         }
      } else {
         while(true) {
            this.a = 0.0D;
            this.b = (double)(70.0F + this.random.nextFloat() * 50.0F);
            this.c = 0.0D;
            this.a += (double)(this.random.nextFloat() * 120.0F - 60.0F);
            this.c += (double)(this.random.nextFloat() * 120.0F - 60.0F);
            double d0 = this.locX - this.a;
            double d1 = this.locY - this.b;
            double d2 = this.locZ - this.c;
            boolean flag = d0 * d0 + d1 * d1 + d2 * d2 > 100.0D;
            if(flag) {
               break;
            }
         }

         this.target = null;
      }

   }

   private float b(double p_b_1_) {
      return (float)MathHelper.g(p_b_1_);
   }

   private boolean b(AxisAlignedBB p_b_1_) {
      int i = MathHelper.floor(p_b_1_.a);
      int j = MathHelper.floor(p_b_1_.b);
      int k = MathHelper.floor(p_b_1_.c);
      int l = MathHelper.floor(p_b_1_.d);
      int i1 = MathHelper.floor(p_b_1_.e);
      int j1 = MathHelper.floor(p_b_1_.f);
      boolean flag = false;
      boolean flag1 = false;
      List<org.bukkit.block.Block> list = new ArrayList();
      CraftWorld craftworld = this.world.getWorld();

      for(int k1 = i; k1 <= l; ++k1) {
         for(int l1 = j; l1 <= i1; ++l1) {
            for(int i2 = k; i2 <= j1; ++i2) {
               BlockPosition blockposition = new BlockPosition(k1, l1, i2);
               Block block = this.world.getType(blockposition).getBlock();
               if(block.getMaterial() != Material.AIR) {
                  if(block != Blocks.BARRIER && block != Blocks.OBSIDIAN && block != Blocks.END_STONE && block != Blocks.BEDROCK && block != Blocks.COMMAND_BLOCK && this.world.getGameRules().getBoolean("mobGriefing")) {
                     flag1 = true;
                     list.add(craftworld.getBlockAt(k1, l1, i2));
                  } else {
                     flag = true;
                  }
               }
            }
         }
      }

      if(flag1) {
         org.bukkit.entity.Entity entity = this.getBukkitEntity();
         EntityExplodeEvent entityexplodeevent = new EntityExplodeEvent(entity, entity.getLocation(), list, 0.0F);
         Bukkit.getPluginManager().callEvent(entityexplodeevent);
         if(entityexplodeevent.isCancelled()) {
            return flag;
         }

         if(entityexplodeevent.getYield() == 0.0F) {
            for(org.bukkit.block.Block block3 : entityexplodeevent.blockList()) {
               this.world.setAir(new BlockPosition(block3.getX(), block3.getY(), block3.getZ()));
            }
         } else {
            for(org.bukkit.block.Block block2 : entityexplodeevent.blockList()) {
               org.bukkit.Material material = block2.getType();
               if(material != org.bukkit.Material.AIR) {
                  int j2 = block2.getX();
                  int k2 = block2.getY();
                  int l2 = block2.getZ();
                  Block block1 = CraftMagicNumbers.getBlock(material);
                  if(block1.a(this.explosionSource)) {
                     block1.dropNaturally(this.world, new BlockPosition(j2, k2, l2), block1.fromLegacyData(block2.getData()), entityexplodeevent.getYield(), 0);
                  }

                  block1.wasExploded(this.world, new BlockPosition(j2, k2, l2), this.explosionSource);
                  this.world.setAir(new BlockPosition(j2, k2, l2));
               }
            }
         }

         double d0 = p_b_1_.a + (p_b_1_.d - p_b_1_.a) * (double)this.random.nextFloat();
         double d1 = p_b_1_.b + (p_b_1_.e - p_b_1_.b) * (double)this.random.nextFloat();
         double d2 = p_b_1_.c + (p_b_1_.f - p_b_1_.c) * (double)this.random.nextFloat();
         this.world.addParticle(EnumParticle.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
      }

      return flag;
   }

   public boolean a(EntityComplexPart p_a_1_, DamageSource p_a_2_, float p_a_3_) {
      if(p_a_1_ != this.bn) {
         p_a_3_ = p_a_3_ / 4.0F + 1.0F;
      }

      float f = this.yaw * 3.1415927F / 180.0F;
      float f1 = MathHelper.sin(f);
      float f2 = MathHelper.cos(f);
      this.a = this.locX + (double)(f1 * 5.0F) + (double)((this.random.nextFloat() - 0.5F) * 2.0F);
      this.b = this.locY + (double)(this.random.nextFloat() * 3.0F) + 1.0D;
      this.c = this.locZ - (double)(f2 * 5.0F) + (double)((this.random.nextFloat() - 0.5F) * 2.0F);
      this.target = null;
      if(p_a_2_.getEntity() instanceof EntityHuman || p_a_2_.isExplosion()) {
         this.dealDamage(p_a_2_, p_a_3_);
      }

      return true;
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      if(p_damageEntity_1_ instanceof EntityDamageSource && ((EntityDamageSource)p_damageEntity_1_).w()) {
         this.dealDamage(p_damageEntity_1_, p_damageEntity_2_);
      }

      return false;
   }

   protected boolean dealDamage(DamageSource p_dealDamage_1_, float p_dealDamage_2_) {
      return super.damageEntity(p_dealDamage_1_, p_dealDamage_2_);
   }

   public void G() {
      this.die();
   }

   protected void aZ() {
      if(!this.dead) {
         ++this.by;
         if(this.by >= 180 && this.by <= 200) {
            float f = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.locX + (double)f, this.locY + 2.0D + (double)f1, this.locZ + (double)f2, 0.0D, 0.0D, 0.0D, new int[0]);
         }

         boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
         if(!this.world.isClientSide) {
            if(this.by > 150 && this.by % 5 == 0 && flag) {
               int j = this.expToDrop / 12;

               while(j > 0) {
                  int l = EntityExperienceOrb.getOrbValue(j);
                  j -= l;
                  this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, l));
               }
            }

            if(this.by == 1) {
               int i = ((WorldServer)this.world).getServer().getViewDistance() * 16;

               for(EntityPlayer entityplayer : MinecraftServer.getServer().getPlayerList().players) {
                  double d0 = this.locX - entityplayer.locX;
                  double d1 = this.locZ - entityplayer.locZ;
                  double d2 = d0 * d0 + d1 * d1;
                  if(this.world.spigotConfig.dragonDeathSoundRadius <= 0 || d2 <= (double)(this.world.spigotConfig.dragonDeathSoundRadius * this.world.spigotConfig.dragonDeathSoundRadius)) {
                     if(d2 > (double)(i * i)) {
                        double d3 = Math.sqrt(d2);
                        double d4 = entityplayer.locX + d0 / d3 * (double)i;
                        double d5 = entityplayer.locZ + d1 / d3 * (double)i;
                        entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, new BlockPosition((int)d4, (int)this.locY, (int)d5), 0, true));
                     } else {
                        entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, new BlockPosition((int)this.locX, (int)this.locY, (int)this.locZ), 0, true));
                     }
                  }
               }
            }
         }

         this.move(0.0D, 0.10000000149011612D, 0.0D);
         this.aI = this.yaw += 20.0F;
         if(this.by == 200 && !this.world.isClientSide) {
            if(flag) {
               int k = this.expToDrop - 10 * this.expToDrop / 12;

               while(k > 0) {
                  int i1 = EntityExperienceOrb.getOrbValue(k);
                  k -= i1;
                  this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, i1));
               }
            }

            this.a(new BlockPosition(this.locX, 64.0D, this.locZ));
            this.die();
         }

      }
   }

   private void a(BlockPosition p_a_1_) {
      BlockStateListPopulator blockstatelistpopulator = new BlockStateListPopulator(this.world.getWorld());

      for(int i = -1; i <= 32; ++i) {
         for(int j = -4; j <= 4; ++j) {
            for(int k = -4; k <= 4; ++k) {
               double d0 = (double)(j * j + k * k);
               if(d0 <= 12.25D) {
                  BlockPosition blockposition = p_a_1_.a(j, i, k);
                  if(i < 0) {
                     if(d0 <= 6.25D) {
                        blockstatelistpopulator.setTypeUpdate(blockposition, Blocks.BEDROCK.getBlockData());
                     }
                  } else if(i > 0) {
                     blockstatelistpopulator.setTypeUpdate(blockposition, Blocks.AIR.getBlockData());
                  } else if(d0 > 6.25D) {
                     blockstatelistpopulator.setTypeUpdate(blockposition, Blocks.BEDROCK.getBlockData());
                  } else {
                     blockstatelistpopulator.setTypeUpdate(blockposition, Blocks.END_PORTAL.getBlockData());
                  }
               }
            }
         }
      }

      blockstatelistpopulator.setTypeUpdate(p_a_1_, Blocks.BEDROCK.getBlockData());
      blockstatelistpopulator.setTypeUpdate(p_a_1_.up(), Blocks.BEDROCK.getBlockData());
      BlockPosition blockposition1 = p_a_1_.up(2);
      blockstatelistpopulator.setTypeUpdate(blockposition1, Blocks.BEDROCK.getBlockData());
      blockstatelistpopulator.setTypeUpdate(blockposition1.west(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.EAST));
      blockstatelistpopulator.setTypeUpdate(blockposition1.east(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.WEST));
      blockstatelistpopulator.setTypeUpdate(blockposition1.north(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.SOUTH));
      blockstatelistpopulator.setTypeUpdate(blockposition1.south(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.NORTH));
      blockstatelistpopulator.setTypeUpdate(p_a_1_.up(3), Blocks.BEDROCK.getBlockData());
      blockstatelistpopulator.setTypeUpdate(p_a_1_.up(4), Blocks.DRAGON_EGG.getBlockData());
      EntityCreatePortalEvent entitycreateportalevent = new EntityCreatePortalEvent((LivingEntity)this.getBukkitEntity(), Collections.unmodifiableList(blockstatelistpopulator.getList()), PortalType.ENDER);
      this.world.getServer().getPluginManager().callEvent(entitycreateportalevent);
      if(!entitycreateportalevent.isCancelled()) {
         for(org.bukkit.block.BlockState blockstate : entitycreateportalevent.getBlocks()) {
            blockstate.update(true);
         }
      } else {
         for(org.bukkit.block.BlockState blockstate1 : entitycreateportalevent.getBlocks()) {
            PacketPlayOutBlockChange packetplayoutblockchange = new PacketPlayOutBlockChange(this.world, new BlockPosition(blockstate1.getX(), blockstate1.getY(), blockstate1.getZ()));

            for(EntityHuman entityhuman : this.world.players) {
               if(entityhuman instanceof EntityPlayer) {
                  ((EntityPlayer)entityhuman).playerConnection.sendPacket(packetplayoutblockchange);
               }
            }
         }
      }

   }

   protected void D() {
   }

   public Entity[] aB() {
      return this.children;
   }

   public boolean ad() {
      return false;
   }

   public World a() {
      return this.world;
   }

   protected String z() {
      return "mob.enderdragon.growl";
   }

   protected String bo() {
      return "mob.enderdragon.hit";
   }

   protected float bB() {
      return 5.0F;
   }

   public int getExpReward() {
      return 12000;
   }
}

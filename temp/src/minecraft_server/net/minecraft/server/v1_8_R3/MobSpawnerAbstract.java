package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.UtilColor;
import net.minecraft.server.v1_8_R3.WeightedRandom;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public abstract class MobSpawnerAbstract {
   public int spawnDelay = 20;
   private String mobName = "Pig";
   private final List<MobSpawnerAbstract.a> mobs = Lists.<MobSpawnerAbstract.a>newArrayList();
   private MobSpawnerAbstract.a spawnData;
   private double e;
   private double f;
   private int minSpawnDelay = 200;
   private int maxSpawnDelay = 800;
   private int spawnCount = 4;
   private Entity j;
   private int maxNearbyEntities = 6;
   private int requiredPlayerRange = 16;
   private int spawnRange = 4;

   public String getMobName() {
      if(this.i() == null) {
         if(this.mobName == null) {
            this.mobName = "Pig";
         }

         if(this.mobName != null && this.mobName.equals("Minecart")) {
            this.mobName = "MinecartRideable";
         }

         return this.mobName;
      } else {
         return this.i().d;
      }
   }

   public void setMobName(String p_setMobName_1_) {
      this.mobName = p_setMobName_1_;
   }

   private boolean g() {
      BlockPosition blockposition = this.b();
      return this.a().isPlayerNearby((double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D, (double)this.requiredPlayerRange);
   }

   public void c() {
      if(this.g()) {
         BlockPosition blockposition = this.b();
         if(this.a().isClientSide) {
            double d0 = (double)((float)blockposition.getX() + this.a().random.nextFloat());
            double d1 = (double)((float)blockposition.getY() + this.a().random.nextFloat());
            double d5 = (double)((float)blockposition.getZ() + this.a().random.nextFloat());
            this.a().addParticle(EnumParticle.SMOKE_NORMAL, d0, d1, d5, 0.0D, 0.0D, 0.0D, new int[0]);
            this.a().addParticle(EnumParticle.FLAME, d0, d1, d5, 0.0D, 0.0D, 0.0D, new int[0]);
            if(this.spawnDelay > 0) {
               --this.spawnDelay;
            }

            this.f = this.e;
            this.e = (this.e + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
         } else {
            if(this.spawnDelay == -1) {
               this.h();
            }

            if(this.spawnDelay > 0) {
               --this.spawnDelay;
               return;
            }

            boolean flag = false;

            for(int i = 0; i < this.spawnCount; ++i) {
               Entity entity = EntityTypes.createEntityByName(this.getMobName(), this.a());
               if(entity == null) {
                  return;
               }

               int j = this.a().a(entity.getClass(), (new AxisAlignedBB((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ(), (double)(blockposition.getX() + 1), (double)(blockposition.getY() + 1), (double)(blockposition.getZ() + 1))).grow((double)this.spawnRange, (double)this.spawnRange, (double)this.spawnRange)).size();
               if(j >= this.maxNearbyEntities) {
                  this.h();
                  return;
               }

               double d2 = (double)blockposition.getX() + (this.a().random.nextDouble() - this.a().random.nextDouble()) * (double)this.spawnRange + 0.5D;
               double d3 = (double)(blockposition.getY() + this.a().random.nextInt(3) - 1);
               double d4 = (double)blockposition.getZ() + (this.a().random.nextDouble() - this.a().random.nextDouble()) * (double)this.spawnRange + 0.5D;
               EntityInsentient entityinsentient = entity instanceof EntityInsentient?(EntityInsentient)entity:null;
               entity.setPositionRotation(d2, d3, d4, this.a().random.nextFloat() * 360.0F, 0.0F);
               if(entityinsentient == null || entityinsentient.bR() && entityinsentient.canSpawn()) {
                  this.a(entity, true);
                  this.a().triggerEffect(2004, blockposition, 0);
                  if(entityinsentient != null) {
                     entityinsentient.y();
                  }

                  flag = true;
               }
            }

            if(flag) {
               this.h();
            }
         }
      }

   }

   private Entity a(Entity p_a_1_, boolean p_a_2_) {
      if(this.i() != null) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         p_a_1_.d(nbttagcompound);

         for(String s : this.i().c.c()) {
            NBTBase nbtbase = this.i().c.get(s);
            nbttagcompound.set(s, nbtbase.clone());
         }

         p_a_1_.f(nbttagcompound);
         if(p_a_1_.world != null && p_a_2_) {
            SpawnerSpawnEvent spawnerspawnevent1 = CraftEventFactory.callSpawnerSpawnEvent(p_a_1_, this.b().getX(), this.b().getY(), this.b().getZ());
            if(!spawnerspawnevent1.isCancelled()) {
               p_a_1_.world.addEntity(p_a_1_, SpawnReason.SPAWNER);
               if(p_a_1_.world.spigotConfig.nerfSpawnerMobs) {
                  p_a_1_.fromMobSpawner = true;
               }
            }
         }

         NBTTagCompound nbttagcompound2;
         for(Entity entity1 = p_a_1_; nbttagcompound.hasKeyOfType("Riding", 10); nbttagcompound = nbttagcompound2) {
            nbttagcompound2 = nbttagcompound.getCompound("Riding");
            Entity entity = EntityTypes.createEntityByName(nbttagcompound2.getString("id"), p_a_1_.world);
            if(entity != null) {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               entity.d(nbttagcompound1);

               for(String s1 : nbttagcompound2.c()) {
                  NBTBase nbtbase1 = nbttagcompound2.get(s1);
                  nbttagcompound1.set(s1, nbtbase1.clone());
               }

               entity.f(nbttagcompound1);
               entity.setPositionRotation(entity1.locX, entity1.locY, entity1.locZ, entity1.yaw, entity1.pitch);
               SpawnerSpawnEvent spawnerspawnevent2 = CraftEventFactory.callSpawnerSpawnEvent(entity, this.b().getX(), this.b().getY(), this.b().getZ());
               if(spawnerspawnevent2.isCancelled()) {
                  continue;
               }

               if(p_a_1_.world != null && p_a_2_) {
                  p_a_1_.world.addEntity(entity, SpawnReason.SPAWNER);
               }

               entity1.mount(entity);
            }

            entity1 = entity;
         }
      } else if(p_a_1_ instanceof EntityLiving && p_a_1_.world != null && p_a_2_) {
         if(p_a_1_ instanceof EntityInsentient) {
            ((EntityInsentient)p_a_1_).prepare(p_a_1_.world.E(new BlockPosition(p_a_1_)), (GroupDataEntity)null);
         }

         SpawnerSpawnEvent spawnerspawnevent = CraftEventFactory.callSpawnerSpawnEvent(p_a_1_, this.b().getX(), this.b().getY(), this.b().getZ());
         if(!spawnerspawnevent.isCancelled()) {
            p_a_1_.world.addEntity(p_a_1_, SpawnReason.SPAWNER);
            if(p_a_1_.world.spigotConfig.nerfSpawnerMobs) {
               p_a_1_.fromMobSpawner = true;
            }
         }
      }

      return p_a_1_;
   }

   private void h() {
      if(this.maxSpawnDelay <= this.minSpawnDelay) {
         this.spawnDelay = this.minSpawnDelay;
      } else {
         int i = this.maxSpawnDelay - this.minSpawnDelay;
         this.spawnDelay = this.minSpawnDelay + this.a().random.nextInt(i);
      }

      if(this.mobs.size() > 0) {
         this.a((MobSpawnerAbstract.a)WeightedRandom.a(this.a().random, this.mobs));
      }

      this.a(1);
   }

   public void a(NBTTagCompound p_a_1_) {
      this.mobName = p_a_1_.getString("EntityId");
      this.spawnDelay = p_a_1_.getShort("Delay");
      this.mobs.clear();
      if(p_a_1_.hasKeyOfType("SpawnPotentials", 9)) {
         NBTTagList nbttaglist = p_a_1_.getList("SpawnPotentials", 10);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            this.mobs.add(new MobSpawnerAbstract.a(nbttaglist.get(i)));
         }
      }

      if(p_a_1_.hasKeyOfType("SpawnData", 10)) {
         this.a(new MobSpawnerAbstract.a(p_a_1_.getCompound("SpawnData"), this.mobName));
      } else {
         this.a((MobSpawnerAbstract.a)null);
      }

      if(p_a_1_.hasKeyOfType("MinSpawnDelay", 99)) {
         this.minSpawnDelay = p_a_1_.getShort("MinSpawnDelay");
         this.maxSpawnDelay = p_a_1_.getShort("MaxSpawnDelay");
         this.spawnCount = p_a_1_.getShort("SpawnCount");
      }

      if(p_a_1_.hasKeyOfType("MaxNearbyEntities", 99)) {
         this.maxNearbyEntities = p_a_1_.getShort("MaxNearbyEntities");
         this.requiredPlayerRange = p_a_1_.getShort("RequiredPlayerRange");
      }

      if(p_a_1_.hasKeyOfType("SpawnRange", 99)) {
         this.spawnRange = p_a_1_.getShort("SpawnRange");
      }

      if(this.a() != null) {
         this.j = null;
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      String s = this.getMobName();
      if(!UtilColor.b(s)) {
         p_b_1_.setString("EntityId", s);
         p_b_1_.setShort("Delay", (short)this.spawnDelay);
         p_b_1_.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
         p_b_1_.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
         p_b_1_.setShort("SpawnCount", (short)this.spawnCount);
         p_b_1_.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
         p_b_1_.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
         p_b_1_.setShort("SpawnRange", (short)this.spawnRange);
         if(this.i() != null) {
            p_b_1_.set("SpawnData", this.i().c.clone());
         }

         if(this.i() != null || this.mobs.size() > 0) {
            NBTTagList nbttaglist = new NBTTagList();
            if(this.mobs.size() > 0) {
               for(MobSpawnerAbstract.a mobspawnerabstract$a : this.mobs) {
                  nbttaglist.add(mobspawnerabstract$a.a());
               }
            } else {
               nbttaglist.add(this.i().a());
            }

            p_b_1_.set("SpawnPotentials", nbttaglist);
         }
      }

   }

   public boolean b(int p_b_1_) {
      if(p_b_1_ == 1 && this.a().isClientSide) {
         this.spawnDelay = this.minSpawnDelay;
         return true;
      } else {
         return false;
      }
   }

   private MobSpawnerAbstract.a i() {
      return this.spawnData;
   }

   public void a(MobSpawnerAbstract.a p_a_1_) {
      this.spawnData = p_a_1_;
   }

   public abstract void a(int var1);

   public abstract World a();

   public abstract BlockPosition b();

   public class a extends WeightedRandom.WeightedRandomChoice {
      private final NBTTagCompound c;
      private final String d;

      public a(NBTTagCompound p_i55_2_) {
         this(p_i55_2_.getCompound("Properties"), p_i55_2_.getString("Type"), p_i55_2_.getInt("Weight"));
      }

      public a(NBTTagCompound p_i56_2_, String p_i56_3_) {
         this(p_i56_2_, p_i56_3_, 1);
      }

      private a(NBTTagCompound p_i57_2_, String p_i57_3_, int p_i57_4_) {
         super(p_i57_4_);
         if(p_i57_3_.equals("Minecart")) {
            if(p_i57_2_ != null) {
               p_i57_3_ = EntityMinecartAbstract.EnumMinecartType.a(p_i57_2_.getInt("Type")).b();
            } else {
               p_i57_3_ = "MinecartRideable";
            }
         }

         this.c = p_i57_2_;
         this.d = p_i57_3_;
      }

      public NBTTagCompound a() {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.set("Properties", this.c);
         nbttagcompound.setString("Type", this.d);
         nbttagcompound.setInt("Weight", this.a);
         return nbttagcompound;
      }
   }
}

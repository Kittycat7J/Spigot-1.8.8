package net.minecraft.server.v1_8_R3;

import java.util.UUID;
import java.util.logging.Level;
import net.minecraft.server.v1_8_R3.AchievementList;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ControllerJump;
import net.minecraft.server.v1_8_R3.ControllerLook;
import net.minecraft.server.v1_8_R3.ControllerMove;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAIBodyControl;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityHanging;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityLeash;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntitySenses;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.IMonster;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemBow;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemSword;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.Statistic;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;

public abstract class EntityInsentient extends EntityLiving {
   public int a_;
   protected int b_;
   private ControllerLook lookController;
   protected ControllerMove moveController;
   protected ControllerJump g;
   private EntityAIBodyControl b;
   protected NavigationAbstract navigation;
   public PathfinderGoalSelector goalSelector;
   public PathfinderGoalSelector targetSelector;
   private EntityLiving goalTarget;
   private EntitySenses bk;
   private ItemStack[] equipment = new ItemStack[5];
   public float[] dropChances = new float[5];
   public boolean canPickUpLoot;
   public boolean persistent;
   private boolean bo;
   private Entity bp;
   private NBTTagCompound bq;
   protected ItemStack headDrop = null;

   public EntityInsentient(World p_i366_1_) {
      super(p_i366_1_);
      this.goalSelector = new PathfinderGoalSelector(p_i366_1_ != null && p_i366_1_.methodProfiler != null?p_i366_1_.methodProfiler:null);
      this.targetSelector = new PathfinderGoalSelector(p_i366_1_ != null && p_i366_1_.methodProfiler != null?p_i366_1_.methodProfiler:null);
      this.lookController = new ControllerLook(this);
      this.moveController = new ControllerMove(this);
      this.g = new ControllerJump(this);
      this.b = new EntityAIBodyControl(this);
      this.navigation = this.b(p_i366_1_);
      this.bk = new EntitySenses(this);

      for(int i = 0; i < this.dropChances.length; ++i) {
         this.dropChances[i] = 0.085F;
      }

      this.persistent = !this.isTypeNotPersistent();
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeMap().b(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
   }

   protected NavigationAbstract b(World p_b_1_) {
      return new Navigation(this, p_b_1_);
   }

   public ControllerLook getControllerLook() {
      return this.lookController;
   }

   public ControllerMove getControllerMove() {
      return this.moveController;
   }

   public ControllerJump getControllerJump() {
      return this.g;
   }

   public NavigationAbstract getNavigation() {
      return this.navigation;
   }

   public EntitySenses getEntitySenses() {
      return this.bk;
   }

   public EntityLiving getGoalTarget() {
      return this.goalTarget;
   }

   public void setGoalTarget(EntityLiving p_setGoalTarget_1_) {
      this.setGoalTarget(p_setGoalTarget_1_, TargetReason.UNKNOWN, true);
   }

   public void setGoalTarget(EntityLiving p_setGoalTarget_1_, TargetReason p_setGoalTarget_2_, boolean p_setGoalTarget_3_) {
      if(this.getGoalTarget() != p_setGoalTarget_1_) {
         if(p_setGoalTarget_3_) {
            if(p_setGoalTarget_2_ == TargetReason.UNKNOWN && this.getGoalTarget() != null && p_setGoalTarget_1_ == null) {
               p_setGoalTarget_2_ = this.getGoalTarget().isAlive()?TargetReason.FORGOT_TARGET:TargetReason.TARGET_DIED;
            }

            if(p_setGoalTarget_2_ == TargetReason.UNKNOWN) {
               this.world.getServer().getLogger().log(Level.WARNING, "Unknown target reason, please report on the issue tracker", new Exception());
            }

            CraftLivingEntity craftlivingentity = null;
            if(p_setGoalTarget_1_ != null) {
               craftlivingentity = (CraftLivingEntity)p_setGoalTarget_1_.getBukkitEntity();
            }

            EntityTargetLivingEntityEvent entitytargetlivingentityevent = new EntityTargetLivingEntityEvent(this.getBukkitEntity(), craftlivingentity, p_setGoalTarget_2_);
            this.world.getServer().getPluginManager().callEvent(entitytargetlivingentityevent);
            if(entitytargetlivingentityevent.isCancelled()) {
               return;
            }

            if(entitytargetlivingentityevent.getTarget() != null) {
               p_setGoalTarget_1_ = ((CraftLivingEntity)entitytargetlivingentityevent.getTarget()).getHandle();
            } else {
               p_setGoalTarget_1_ = null;
            }
         }

         this.goalTarget = p_setGoalTarget_1_;
      }
   }

   public boolean a(Class<? extends EntityLiving> p_a_1_) {
      return p_a_1_ != EntityGhast.class;
   }

   public void v() {
   }

   protected void h() {
      super.h();
      this.datawatcher.a(15, Byte.valueOf((byte)0));
   }

   public int w() {
      return 80;
   }

   public void x() {
      String s = this.z();
      if(s != null) {
         this.makeSound(s, this.bB(), this.bC());
      }

   }

   public void K() {
      super.K();
      this.world.methodProfiler.a("mobBaseTick");
      if(this.isAlive() && this.random.nextInt(1000) < this.a_++) {
         this.a_ = -this.w();
         this.x();
      }

      this.world.methodProfiler.b();
   }

   protected int getExpValue(EntityHuman p_getExpValue_1_) {
      if(this.b_ > 0) {
         int i = this.b_;
         ItemStack[] aitemstack = this.getEquipment();

         for(int j = 0; j < aitemstack.length; ++j) {
            if(aitemstack[j] != null && this.dropChances[j] <= 1.0F) {
               i += 1 + this.random.nextInt(3);
            }
         }

         return i;
      } else {
         return this.b_;
      }
   }

   public void y() {
      if(this.world.isClientSide) {
         for(int i = 0; i < 20; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, this.locX + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width - d0 * d3, this.locY + (double)(this.random.nextFloat() * this.length) - d1 * d3, this.locZ + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width - d2 * d3, d0, d1, d2, new int[0]);
         }
      } else {
         this.world.broadcastEntityEffect(this, (byte)20);
      }

   }

   public void t_() {
      super.t_();
      if(!this.world.isClientSide) {
         this.ca();
      }

   }

   protected float h(float p_h_1_, float p_h_2_) {
      this.b.a();
      return p_h_2_;
   }

   protected String z() {
      return null;
   }

   protected Item getLoot() {
      return null;
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      Item item = this.getLoot();
      if(item != null) {
         int i = this.random.nextInt(3);
         if(p_dropDeathLoot_2_ > 0) {
            i += this.random.nextInt(p_dropDeathLoot_2_ + 1);
         }

         for(int j = 0; j < i; ++j) {
            this.a(item, 1);
         }
      }

      if(this.headDrop != null) {
         this.a(this.headDrop, 0.0F);
         this.headDrop = null;
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setBoolean("CanPickUpLoot", this.bY());
      p_b_1_.setBoolean("PersistenceRequired", this.persistent);
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.equipment.length; ++i) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         if(this.equipment[i] != null) {
            this.equipment[i].save(nbttagcompound);
         }

         nbttaglist.add(nbttagcompound);
      }

      p_b_1_.set("Equipment", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(int j = 0; j < this.dropChances.length; ++j) {
         nbttaglist1.add(new NBTTagFloat(this.dropChances[j]));
      }

      p_b_1_.set("DropChances", nbttaglist1);
      p_b_1_.setBoolean("Leashed", this.bo);
      if(this.bp != null) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         if(this.bp instanceof EntityLiving) {
            nbttagcompound1.setLong("UUIDMost", this.bp.getUniqueID().getMostSignificantBits());
            nbttagcompound1.setLong("UUIDLeast", this.bp.getUniqueID().getLeastSignificantBits());
         } else if(this.bp instanceof EntityHanging) {
            BlockPosition blockposition = ((EntityHanging)this.bp).getBlockPosition();
            nbttagcompound1.setInt("X", blockposition.getX());
            nbttagcompound1.setInt("Y", blockposition.getY());
            nbttagcompound1.setInt("Z", blockposition.getZ());
         }

         p_b_1_.set("Leash", nbttagcompound1);
      }

      if(this.ce()) {
         p_b_1_.setBoolean("NoAI", this.ce());
      }

   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      if(p_a_1_.hasKeyOfType("CanPickUpLoot", 1)) {
         boolean flag = p_a_1_.getBoolean("CanPickUpLoot");
         if(isLevelAtLeast(p_a_1_, 1) || flag) {
            this.j(flag);
         }
      }

      boolean flag1 = p_a_1_.getBoolean("PersistenceRequired");
      if(isLevelAtLeast(p_a_1_, 1) || flag1) {
         this.persistent = flag1;
      }

      if(p_a_1_.hasKeyOfType("Equipment", 9)) {
         NBTTagList nbttaglist = p_a_1_.getList("Equipment", 10);

         for(int i = 0; i < this.equipment.length; ++i) {
            this.equipment[i] = ItemStack.createStack(nbttaglist.get(i));
         }
      }

      if(p_a_1_.hasKeyOfType("DropChances", 9)) {
         NBTTagList nbttaglist1 = p_a_1_.getList("DropChances", 5);

         for(int j = 0; j < nbttaglist1.size(); ++j) {
            this.dropChances[j] = nbttaglist1.e(j);
         }
      }

      this.bo = p_a_1_.getBoolean("Leashed");
      if(this.bo && p_a_1_.hasKeyOfType("Leash", 10)) {
         this.bq = p_a_1_.getCompound("Leash");
      }

      this.k(p_a_1_.getBoolean("NoAI"));
   }

   public void n(float p_n_1_) {
      this.ba = p_n_1_;
   }

   public void k(float p_k_1_) {
      super.k(p_k_1_);
      this.n(p_k_1_);
   }

   public void m() {
      super.m();
      this.world.methodProfiler.a("looting");
      if(!this.world.isClientSide && this.bY() && !this.aP && this.world.getGameRules().getBoolean("mobGriefing")) {
         for(EntityItem entityitem : this.world.a(EntityItem.class, this.getBoundingBox().grow(1.0D, 0.0D, 1.0D))) {
            if(!entityitem.dead && entityitem.getItemStack() != null && !entityitem.s()) {
               this.a(entityitem);
            }
         }
      }

      this.world.methodProfiler.b();
   }

   protected void a(EntityItem p_a_1_) {
      ItemStack itemstack = p_a_1_.getItemStack();
      int i = c(itemstack);
      if(i > -1) {
         boolean flag = true;
         ItemStack itemstack1 = this.getEquipment(i);
         if(itemstack1 != null) {
            if(i == 0) {
               if(itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
                  flag = true;
               } else if(itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
                  ItemSword itemsword = (ItemSword)itemstack.getItem();
                  ItemSword itemsword1 = (ItemSword)itemstack1.getItem();
                  if(itemsword.g() != itemsword1.g()) {
                     flag = itemsword.g() > itemsword1.g();
                  } else {
                     flag = itemstack.getData() > itemstack1.getData() || itemstack.hasTag() && !itemstack1.hasTag();
                  }
               } else if(itemstack.getItem() instanceof ItemBow && itemstack1.getItem() instanceof ItemBow) {
                  flag = itemstack.hasTag() && !itemstack1.hasTag();
               } else {
                  flag = false;
               }
            } else if(itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
               flag = true;
            } else if(itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor) {
               ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
               ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();
               if(itemarmor.c != itemarmor1.c) {
                  flag = itemarmor.c > itemarmor1.c;
               } else {
                  flag = itemstack.getData() > itemstack1.getData() || itemstack.hasTag() && !itemstack1.hasTag();
               }
            } else {
               flag = false;
            }
         }

         if(flag && this.a(itemstack)) {
            if(itemstack1 != null && this.random.nextFloat() - 0.1F < this.dropChances[i]) {
               this.a(itemstack1, 0.0F);
            }

            if(itemstack.getItem() == Items.DIAMOND && p_a_1_.n() != null) {
               EntityHuman entityhuman = this.world.a(p_a_1_.n());
               if(entityhuman != null) {
                  entityhuman.b((Statistic)AchievementList.x);
               }
            }

            this.setEquipment(i, itemstack);
            this.dropChances[i] = 2.0F;
            this.persistent = true;
            this.receive(p_a_1_, 1);
            p_a_1_.die();
         }
      }

   }

   protected boolean a(ItemStack p_a_1_) {
      return true;
   }

   protected boolean isTypeNotPersistent() {
      return true;
   }

   protected void D() {
      if(this.persistent) {
         this.ticksFarFromPlayer = 0;
      } else {
         EntityHuman entityhuman = this.world.findNearbyPlayer(this, -1.0D);
         if(entityhuman != null) {
            double d0 = entityhuman.locX - this.locX;
            double d1 = entityhuman.locY - this.locY;
            double d2 = entityhuman.locZ - this.locZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if(d3 > 16384.0D) {
               this.die();
            }

            if(this.ticksFarFromPlayer > 600 && this.random.nextInt(800) == 0 && d3 > 1024.0D) {
               this.die();
            } else if(d3 < 1024.0D) {
               this.ticksFarFromPlayer = 0;
            }
         }
      }

   }

   protected final void doTick() {
      ++this.ticksFarFromPlayer;
      this.world.methodProfiler.a("checkDespawn");
      this.D();
      this.world.methodProfiler.b();
      if(!this.fromMobSpawner) {
         this.world.methodProfiler.a("sensing");
         this.bk.a();
         this.world.methodProfiler.b();
         this.world.methodProfiler.a("targetSelector");
         this.targetSelector.a();
         this.world.methodProfiler.b();
         this.world.methodProfiler.a("goalSelector");
         this.goalSelector.a();
         this.world.methodProfiler.b();
         this.world.methodProfiler.a("navigation");
         this.navigation.k();
         this.world.methodProfiler.b();
         this.world.methodProfiler.a("mob tick");
         this.E();
         this.world.methodProfiler.b();
         this.world.methodProfiler.a("controls");
         this.world.methodProfiler.a("move");
         this.moveController.c();
         this.world.methodProfiler.c("look");
         this.lookController.a();
         this.world.methodProfiler.c("jump");
         this.g.b();
         this.world.methodProfiler.b();
         this.world.methodProfiler.b();
      }
   }

   protected void E() {
   }

   public int bQ() {
      return 40;
   }

   public void a(Entity p_a_1_, float p_a_2_, float p_a_3_) {
      double d0 = p_a_1_.locX - this.locX;
      double d1 = p_a_1_.locZ - this.locZ;
      double d2;
      if(p_a_1_ instanceof EntityLiving) {
         EntityLiving entityliving = (EntityLiving)p_a_1_;
         d2 = entityliving.locY + (double)entityliving.getHeadHeight() - (this.locY + (double)this.getHeadHeight());
      } else {
         d2 = (p_a_1_.getBoundingBox().b + p_a_1_.getBoundingBox().e) / 2.0D - (this.locY + (double)this.getHeadHeight());
      }

      double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
      float f = (float)(MathHelper.b(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
      float f1 = (float)(-(MathHelper.b(d2, d3) * 180.0D / 3.1415927410125732D));
      this.pitch = this.b(this.pitch, f1, p_a_3_);
      this.yaw = this.b(this.yaw, f, p_a_2_);
   }

   private float b(float p_b_1_, float p_b_2_, float p_b_3_) {
      float f = MathHelper.g(p_b_2_ - p_b_1_);
      if(f > p_b_3_) {
         f = p_b_3_;
      }

      if(f < -p_b_3_) {
         f = -p_b_3_;
      }

      return p_b_1_ + f;
   }

   public boolean bR() {
      return true;
   }

   public boolean canSpawn() {
      return this.world.a((AxisAlignedBB)this.getBoundingBox(), (Entity)this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
   }

   public int bV() {
      return 4;
   }

   public int aE() {
      if(this.getGoalTarget() == null) {
         return 3;
      } else {
         int i = (int)(this.getHealth() - this.getMaxHealth() * 0.33F);
         i = i - (3 - this.world.getDifficulty().a()) * 4;
         if(i < 0) {
            i = 0;
         }

         return i + 3;
      }
   }

   public ItemStack bA() {
      return this.equipment[0];
   }

   public ItemStack getEquipment(int p_getEquipment_1_) {
      return this.equipment[p_getEquipment_1_];
   }

   public ItemStack q(int p_q_1_) {
      return this.equipment[p_q_1_ + 1];
   }

   public void setEquipment(int p_setEquipment_1_, ItemStack p_setEquipment_2_) {
      this.equipment[p_setEquipment_1_] = p_setEquipment_2_;
   }

   public ItemStack[] getEquipment() {
      return this.equipment;
   }

   protected void dropEquipment(boolean p_dropEquipment_1_, int p_dropEquipment_2_) {
      for(int i = 0; i < this.getEquipment().length; ++i) {
         ItemStack itemstack = this.getEquipment(i);
         boolean flag = this.dropChances[i] > 1.0F;
         if(itemstack != null && (p_dropEquipment_1_ || flag) && this.random.nextFloat() - (float)p_dropEquipment_2_ * 0.01F < this.dropChances[i]) {
            if(!flag && itemstack.e()) {
               int j = Math.max(itemstack.j() - 25, 1);
               int k = itemstack.j() - this.random.nextInt(this.random.nextInt(j) + 1);
               if(k > j) {
                  k = j;
               }

               if(k < 1) {
                  k = 1;
               }

               itemstack.setData(k);
            }

            this.a(itemstack, 0.0F);
         }
      }

   }

   protected void a(DifficultyDamageScaler p_a_1_) {
      if(this.random.nextFloat() < 0.15F * p_a_1_.c()) {
         int i = this.random.nextInt(2);
         float f = this.world.getDifficulty() == EnumDifficulty.HARD?0.1F:0.25F;
         if(this.random.nextFloat() < 0.095F) {
            ++i;
         }

         if(this.random.nextFloat() < 0.095F) {
            ++i;
         }

         if(this.random.nextFloat() < 0.095F) {
            ++i;
         }

         for(int j = 3; j >= 0; --j) {
            ItemStack itemstack = this.q(j);
            if(j < 3 && this.random.nextFloat() < f) {
               break;
            }

            if(itemstack == null) {
               Item item = a(j + 1, i);
               if(item != null) {
                  this.setEquipment(j + 1, new ItemStack(item));
               }
            }
         }
      }

   }

   public static int c(ItemStack p_c_0_) {
      if(p_c_0_.getItem() != Item.getItemOf(Blocks.PUMPKIN) && p_c_0_.getItem() != Items.SKULL) {
         if(p_c_0_.getItem() instanceof ItemArmor) {
            switch(((ItemArmor)p_c_0_.getItem()).b) {
            case 0:
               return 4;
            case 1:
               return 3;
            case 2:
               return 2;
            case 3:
               return 1;
            }
         }

         return 0;
      } else {
         return 4;
      }
   }

   public static Item a(int p_a_0_, int p_a_1_) {
      switch(p_a_0_) {
      case 4:
         if(p_a_1_ == 0) {
            return Items.LEATHER_HELMET;
         } else if(p_a_1_ == 1) {
            return Items.GOLDEN_HELMET;
         } else if(p_a_1_ == 2) {
            return Items.CHAINMAIL_HELMET;
         } else if(p_a_1_ == 3) {
            return Items.IRON_HELMET;
         } else if(p_a_1_ == 4) {
            return Items.DIAMOND_HELMET;
         }
      case 3:
         if(p_a_1_ == 0) {
            return Items.LEATHER_CHESTPLATE;
         } else if(p_a_1_ == 1) {
            return Items.GOLDEN_CHESTPLATE;
         } else if(p_a_1_ == 2) {
            return Items.CHAINMAIL_CHESTPLATE;
         } else if(p_a_1_ == 3) {
            return Items.IRON_CHESTPLATE;
         } else if(p_a_1_ == 4) {
            return Items.DIAMOND_CHESTPLATE;
         }
      case 2:
         if(p_a_1_ == 0) {
            return Items.LEATHER_LEGGINGS;
         } else if(p_a_1_ == 1) {
            return Items.GOLDEN_LEGGINGS;
         } else if(p_a_1_ == 2) {
            return Items.CHAINMAIL_LEGGINGS;
         } else if(p_a_1_ == 3) {
            return Items.IRON_LEGGINGS;
         } else if(p_a_1_ == 4) {
            return Items.DIAMOND_LEGGINGS;
         }
      case 1:
         if(p_a_1_ == 0) {
            return Items.LEATHER_BOOTS;
         } else if(p_a_1_ == 1) {
            return Items.GOLDEN_BOOTS;
         } else if(p_a_1_ == 2) {
            return Items.CHAINMAIL_BOOTS;
         } else if(p_a_1_ == 3) {
            return Items.IRON_BOOTS;
         } else if(p_a_1_ == 4) {
            return Items.DIAMOND_BOOTS;
         }
      default:
         return null;
      }
   }

   protected void b(DifficultyDamageScaler p_b_1_) {
      float f = p_b_1_.c();
      if(this.bA() != null && this.random.nextFloat() < 0.25F * f) {
         EnchantmentManager.a(this.random, this.bA(), (int)(5.0F + f * (float)this.random.nextInt(18)));
      }

      for(int i = 0; i < 4; ++i) {
         ItemStack itemstack = this.q(i);
         if(itemstack != null && this.random.nextFloat() < 0.5F * f) {
            EnchantmentManager.a(this.random, itemstack, (int)(5.0F + f * (float)this.random.nextInt(18)));
         }
      }

   }

   public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_) {
      this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).b(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05D, 1));
      return p_prepare_2_;
   }

   public boolean bW() {
      return false;
   }

   public void bX() {
      this.persistent = true;
   }

   public void a(int p_a_1_, float p_a_2_) {
      this.dropChances[p_a_1_] = p_a_2_;
   }

   public boolean bY() {
      return this.canPickUpLoot;
   }

   public void j(boolean p_j_1_) {
      this.canPickUpLoot = p_j_1_;
   }

   public boolean isPersistent() {
      return this.persistent;
   }

   public final boolean e(EntityHuman p_e_1_) {
      if(this.cc() && this.getLeashHolder() == p_e_1_) {
         if(CraftEventFactory.callPlayerUnleashEntityEvent(this, p_e_1_).isCancelled()) {
            ((EntityPlayer)p_e_1_).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, this.getLeashHolder()));
            return false;
         } else {
            this.unleash(true, !p_e_1_.abilities.canInstantlyBuild);
            return true;
         }
      } else {
         ItemStack itemstack = p_e_1_.inventory.getItemInHand();
         if(itemstack != null && itemstack.getItem() == Items.LEAD && this.cb()) {
            if(!(this instanceof EntityTameableAnimal) || !((EntityTameableAnimal)this).isTamed()) {
               if(CraftEventFactory.callPlayerLeashEntityEvent(this, p_e_1_, p_e_1_).isCancelled()) {
                  ((EntityPlayer)p_e_1_).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, this.getLeashHolder()));
                  return false;
               } else {
                  this.setLeashHolder(p_e_1_, true);
                  --itemstack.count;
                  return true;
               }
            }

            if(((EntityTameableAnimal)this).e(p_e_1_)) {
               if(CraftEventFactory.callPlayerLeashEntityEvent(this, p_e_1_, p_e_1_).isCancelled()) {
                  ((EntityPlayer)p_e_1_).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, this.getLeashHolder()));
                  return false;
               }

               this.setLeashHolder(p_e_1_, true);
               --itemstack.count;
               return true;
            }
         }

         return this.a(p_e_1_)?true:super.e(p_e_1_);
      }
   }

   protected boolean a(EntityHuman p_a_1_) {
      return false;
   }

   protected void ca() {
      if(this.bq != null) {
         this.n();
      }

      if(this.bo) {
         if(!this.isAlive()) {
            this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), UnleashReason.PLAYER_UNLEASH));
            this.unleash(true, true);
         }

         if(this.bp == null || this.bp.dead) {
            this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), UnleashReason.HOLDER_GONE));
            this.unleash(true, true);
         }
      }

   }

   public void unleash(boolean p_unleash_1_, boolean p_unleash_2_) {
      if(this.bo) {
         this.bo = false;
         this.bp = null;
         if(!this.world.isClientSide && p_unleash_2_) {
            this.a(Items.LEAD, 1);
         }

         if(!this.world.isClientSide && p_unleash_1_ && this.world instanceof WorldServer) {
            ((WorldServer)this.world).getTracker().a((Entity)this, (Packet)(new PacketPlayOutAttachEntity(1, this, (Entity)null)));
         }
      }

   }

   public boolean cb() {
      return !this.cc() && !(this instanceof IMonster);
   }

   public boolean cc() {
      return this.bo;
   }

   public Entity getLeashHolder() {
      return this.bp;
   }

   public void setLeashHolder(Entity p_setLeashHolder_1_, boolean p_setLeashHolder_2_) {
      this.bo = true;
      this.bp = p_setLeashHolder_1_;
      if(!this.world.isClientSide && p_setLeashHolder_2_ && this.world instanceof WorldServer) {
         ((WorldServer)this.world).getTracker().a((Entity)this, (Packet)(new PacketPlayOutAttachEntity(1, this, this.bp)));
      }

   }

   private void n() {
      if(this.bo && this.bq != null) {
         if(this.bq.hasKeyOfType("UUIDMost", 4) && this.bq.hasKeyOfType("UUIDLeast", 4)) {
            UUID uuid = new UUID(this.bq.getLong("UUIDMost"), this.bq.getLong("UUIDLeast"));

            for(EntityLiving entityliving : this.world.a(EntityLiving.class, this.getBoundingBox().grow(10.0D, 10.0D, 10.0D))) {
               if(entityliving.getUniqueID().equals(uuid)) {
                  this.bp = entityliving;
                  break;
               }
            }
         } else if(this.bq.hasKeyOfType("X", 99) && this.bq.hasKeyOfType("Y", 99) && this.bq.hasKeyOfType("Z", 99)) {
            BlockPosition blockposition = new BlockPosition(this.bq.getInt("X"), this.bq.getInt("Y"), this.bq.getInt("Z"));
            EntityLeash entityleash = EntityLeash.b(this.world, blockposition);
            if(entityleash == null) {
               entityleash = EntityLeash.a(this.world, blockposition);
            }

            this.bp = entityleash;
         } else {
            this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), UnleashReason.UNKNOWN));
            this.unleash(false, true);
         }
      }

      this.bq = null;
   }

   public boolean d(int p_d_1_, ItemStack p_d_2_) {
      int i;
      if(p_d_1_ == 99) {
         i = 0;
      } else {
         i = p_d_1_ - 100 + 1;
         if(i < 0 || i >= this.equipment.length) {
            return false;
         }
      }

      if(p_d_2_ == null || c(p_d_2_) == i || i == 4 && p_d_2_.getItem() instanceof ItemBlock) {
         this.setEquipment(i, p_d_2_);
         return true;
      } else {
         return false;
      }
   }

   public boolean bM() {
      return super.bM() && !this.ce();
   }

   public void k(boolean p_k_1_) {
      this.datawatcher.watch(15, Byte.valueOf((byte)(p_k_1_?1:0)));
   }

   public boolean ce() {
      return this.datawatcher.getByte(15) != 0;
   }

   public static enum EnumEntityPositionType {
      ON_GROUND,
      IN_AIR,
      IN_WATER;
   }
}

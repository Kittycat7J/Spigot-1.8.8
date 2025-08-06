package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeRanged;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAgeable;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.IInventoryListener;
import net.minecraft.server.v1_8_R3.InventoryHorseChest;
import net.minecraft.server.v1_8_R3.InventorySubcontainer;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NameReferencingFileConverter;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalFollowParent;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalTame;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class EntityHorse extends EntityAnimal implements IInventoryListener {
   private static final Predicate<Entity> bs = new Predicate() {
      public boolean a(Entity p_a_1_) {
         return p_a_1_ instanceof EntityHorse && ((EntityHorse)p_a_1_).cA();
      }

      public boolean apply(Object p_apply_1_) {
         return this.a((Entity)p_apply_1_);
      }
   };
   public static final IAttribute attributeJumpStrength = (new AttributeRanged((IAttribute)null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).a("Jump Strength").a(true);
   private static final String[] bu = new String[]{null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png"};
   private static final String[] bv = new String[]{"", "meo", "goo", "dio"};
   private static final int[] bw = new int[]{0, 5, 7, 11};
   private static final String[] bx = new String[]{"textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
   private static final String[] by = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
   private static final String[] bz = new String[]{null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
   private static final String[] bA = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
   private int bB;
   private int bC;
   private int bD;
   public int bm;
   public int bo;
   protected boolean bp;
   public InventoryHorseChest inventoryChest;
   private boolean bF;
   protected int bq;
   protected float br;
   private boolean bG;
   private float bH;
   private float bI;
   private float bJ;
   private float bK;
   private float bL;
   private float bM;
   private int bN;
   private String bO;
   private String[] bP = new String[3];
   private boolean bQ = false;
   public int maxDomestication = 100;

   public EntityHorse(World p_i43_1_) {
      super(p_i43_1_);
      this.setSize(1.4F, 1.6F);
      this.fireProof = false;
      this.setHasChest(false);
      ((Navigation)this.getNavigation()).a(true);
      this.goalSelector.a(0, new PathfinderGoalFloat(this));
      this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.2D));
      this.goalSelector.a(1, new PathfinderGoalTame(this, 1.2D));
      this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
      this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.0D));
      this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.7D));
      this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
      this.loadChest();
   }

   protected void h() {
      super.h();
      this.datawatcher.a(16, Integer.valueOf(0));
      this.datawatcher.a(19, Byte.valueOf((byte)0));
      this.datawatcher.a(20, Integer.valueOf(0));
      this.datawatcher.a(21, String.valueOf(""));
      this.datawatcher.a(22, Integer.valueOf(0));
   }

   public void setType(int p_setType_1_) {
      this.datawatcher.watch(19, Byte.valueOf((byte)p_setType_1_));
      this.dc();
   }

   public int getType() {
      return this.datawatcher.getByte(19);
   }

   public void setVariant(int p_setVariant_1_) {
      this.datawatcher.watch(20, Integer.valueOf(p_setVariant_1_));
      this.dc();
   }

   public int getVariant() {
      return this.datawatcher.getInt(20);
   }

   public String getName() {
      if(this.hasCustomName()) {
         return this.getCustomName();
      } else {
         int i = this.getType();
         switch(i) {
         case 0:
         default:
            return LocaleI18n.get("entity.horse.name");
         case 1:
            return LocaleI18n.get("entity.donkey.name");
         case 2:
            return LocaleI18n.get("entity.mule.name");
         case 3:
            return LocaleI18n.get("entity.zombiehorse.name");
         case 4:
            return LocaleI18n.get("entity.skeletonhorse.name");
         }
      }
   }

   private boolean w(int p_w_1_) {
      return (this.datawatcher.getInt(16) & p_w_1_) != 0;
   }

   private void c(int p_c_1_, boolean p_c_2_) {
      int i = this.datawatcher.getInt(16);
      if(p_c_2_) {
         this.datawatcher.watch(16, Integer.valueOf(i | p_c_1_));
      } else {
         this.datawatcher.watch(16, Integer.valueOf(i & ~p_c_1_));
      }

   }

   public boolean cn() {
      return !this.isBaby();
   }

   public boolean isTame() {
      return this.w(2);
   }

   public boolean cp() {
      return this.cn();
   }

   public String getOwnerUUID() {
      return this.datawatcher.getString(21);
   }

   public void setOwnerUUID(String p_setOwnerUUID_1_) {
      this.datawatcher.watch(21, p_setOwnerUUID_1_);
   }

   public float cu() {
      return 0.5F;
   }

   public void a(boolean p_a_1_) {
      if(p_a_1_) {
         this.a(this.cu());
      } else {
         this.a(1.0F);
      }

   }

   public boolean cv() {
      return this.bp;
   }

   public void setTame(boolean p_setTame_1_) {
      this.c(2, p_setTame_1_);
   }

   public void m(boolean p_m_1_) {
      this.bp = p_m_1_;
   }

   public boolean cb() {
      return !this.cR() && super.cb();
   }

   protected void o(float p_o_1_) {
      if(p_o_1_ > 6.0F && this.cy()) {
         this.r(false);
      }

   }

   public boolean hasChest() {
      return this.w(8);
   }

   public int cx() {
      return this.datawatcher.getInt(22);
   }

   private int f(ItemStack p_f_1_) {
      if(p_f_1_ == null) {
         return 0;
      } else {
         Item item = p_f_1_.getItem();
         return item == Items.IRON_HORSE_ARMOR?1:(item == Items.GOLDEN_HORSE_ARMOR?2:(item == Items.DIAMOND_HORSE_ARMOR?3:0));
      }
   }

   public boolean cy() {
      return this.w(32);
   }

   public boolean cz() {
      return this.w(64);
   }

   public boolean cA() {
      return this.w(16);
   }

   public boolean cB() {
      return this.bF;
   }

   public void e(ItemStack p_e_1_) {
      this.datawatcher.watch(22, Integer.valueOf(this.f(p_e_1_)));
      this.dc();
   }

   public void n(boolean p_n_1_) {
      this.c(16, p_n_1_);
   }

   public void setHasChest(boolean p_setHasChest_1_) {
      this.c(8, p_setHasChest_1_);
   }

   public void p(boolean p_p_1_) {
      this.bF = p_p_1_;
   }

   public void q(boolean p_q_1_) {
      this.c(4, p_q_1_);
   }

   public int getTemper() {
      return this.bq;
   }

   public void setTemper(int p_setTemper_1_) {
      this.bq = p_setTemper_1_;
   }

   public int u(int p_u_1_) {
      int i = MathHelper.clamp(this.getTemper() + p_u_1_, 0, this.getMaxDomestication());
      this.setTemper(i);
      return i;
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      Entity entity = p_damageEntity_1_.getEntity();
      return this.passenger != null && this.passenger.equals(entity)?false:super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
   }

   public int br() {
      return bw[this.cx()];
   }

   public boolean ae() {
      return this.passenger == null;
   }

   public boolean cD() {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.locZ);
      this.world.getBiome(new BlockPosition(i, 0, j));
      return true;
   }

   public void cE() {
      if(!this.world.isClientSide && this.hasChest()) {
         this.a(Item.getItemOf(Blocks.CHEST), 1);
         this.setHasChest(false);
      }

   }

   private void cY() {
      this.df();
      if(!this.R()) {
         this.world.makeSound(this, "eating", 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
      }

   }

   public void e(float p_e_1_, float p_e_2_) {
      if(p_e_1_ > 1.0F) {
         this.makeSound("mob.horse.land", 0.4F, 1.0F);
      }

      int i = MathHelper.f((p_e_1_ * 0.5F - 3.0F) * p_e_2_);
      if(i > 0) {
         this.damageEntity(DamageSource.FALL, (float)i);
         if(this.passenger != null) {
            this.passenger.damageEntity(DamageSource.FALL, (float)i);
         }

         Block block = this.world.getType(new BlockPosition(this.locX, this.locY - 0.2D - (double)this.lastYaw, this.locZ)).getBlock();
         if(block.getMaterial() != Material.AIR && !this.R()) {
            Block.StepSound block$stepsound = block.stepSound;
            this.world.makeSound(this, block$stepsound.getStepSound(), block$stepsound.getVolume1() * 0.5F, block$stepsound.getVolume2() * 0.75F);
         }
      }

   }

   private int cZ() {
      this.getType();
      return this.hasChest()?17:2;
   }

   public void loadChest() {
      InventoryHorseChest inventoryhorsechest = this.inventoryChest;
      this.inventoryChest = new InventoryHorseChest("HorseChest", this.cZ(), this);
      this.inventoryChest.a(this.getName());
      if(inventoryhorsechest != null) {
         inventoryhorsechest.b(this);
         int i = Math.min(inventoryhorsechest.getSize(), this.inventoryChest.getSize());

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = inventoryhorsechest.getItem(j);
            if(itemstack != null) {
               this.inventoryChest.setItem(j, itemstack.cloneItemStack());
            }
         }
      }

      this.inventoryChest.a(this);
      this.db();
   }

   private void db() {
      if(!this.world.isClientSide) {
         this.q(this.inventoryChest.getItem(0) != null);
         if(this.cO()) {
            this.e(this.inventoryChest.getItem(1));
         }
      }

   }

   public void a(InventorySubcontainer p_a_1_) {
      int i = this.cx();
      boolean flag = this.cG();
      this.db();
      if(this.ticksLived > 20) {
         if(i == 0 && i != this.cx()) {
            this.makeSound("mob.horse.armor", 0.5F, 1.0F);
         } else if(i != this.cx()) {
            this.makeSound("mob.horse.armor", 0.5F, 1.0F);
         }

         if(!flag && this.cG()) {
            this.makeSound("mob.horse.leather", 0.5F, 1.0F);
         }
      }

   }

   public boolean bR() {
      this.cD();
      return super.bR();
   }

   protected EntityHorse a(Entity p_a_1_, double p_a_2_) {
      double d0 = Double.MAX_VALUE;
      Entity entity = null;

      for(Entity entity1 : this.world.a(p_a_1_, p_a_1_.getBoundingBox().a(p_a_2_, p_a_2_, p_a_2_), bs)) {
         double d1 = entity1.e(p_a_1_.locX, p_a_1_.locY, p_a_1_.locZ);
         if(d1 < d0) {
            entity = entity1;
            d0 = d1;
         }
      }

      return (EntityHorse)entity;
   }

   public double getJumpStrength() {
      return this.getAttributeInstance(attributeJumpStrength).getValue();
   }

   protected String bp() {
      this.df();
      int i = this.getType();
      return i == 3?"mob.horse.zombie.death":(i == 4?"mob.horse.skeleton.death":(i != 1 && i != 2?"mob.horse.death":"mob.horse.donkey.death"));
   }

   protected Item getLoot() {
      boolean flag = this.random.nextInt(4) == 0;
      int i = this.getType();
      return i == 4?Items.BONE:(i == 3?(flag?null:Items.ROTTEN_FLESH):Items.LEATHER);
   }

   protected String bo() {
      this.df();
      if(this.random.nextInt(3) == 0) {
         this.dh();
      }

      int i = this.getType();
      return i == 3?"mob.horse.zombie.hit":(i == 4?"mob.horse.skeleton.hit":(i != 1 && i != 2?"mob.horse.hit":"mob.horse.donkey.hit"));
   }

   public boolean cG() {
      return this.w(4);
   }

   protected String z() {
      this.df();
      if(this.random.nextInt(10) == 0 && !this.bD()) {
         this.dh();
      }

      int i = this.getType();
      return i == 3?"mob.horse.zombie.idle":(i == 4?"mob.horse.skeleton.idle":(i != 1 && i != 2?"mob.horse.idle":"mob.horse.donkey.idle"));
   }

   protected String cH() {
      this.df();
      this.dh();
      int i = this.getType();
      return i != 3 && i != 4?(i != 1 && i != 2?"mob.horse.angry":"mob.horse.donkey.angry"):null;
   }

   protected void a(BlockPosition p_a_1_, Block p_a_2_) {
      Block.StepSound block$stepsound = p_a_2_.stepSound;
      if(this.world.getType(p_a_1_.up()).getBlock() == Blocks.SNOW_LAYER) {
         block$stepsound = Blocks.SNOW_LAYER.stepSound;
      }

      if(!p_a_2_.getMaterial().isLiquid()) {
         int i = this.getType();
         if(this.passenger != null && i != 1 && i != 2) {
            ++this.bN;
            if(this.bN > 5 && this.bN % 3 == 0) {
               this.makeSound("mob.horse.gallop", block$stepsound.getVolume1() * 0.15F, block$stepsound.getVolume2());
               if(i == 0 && this.random.nextInt(10) == 0) {
                  this.makeSound("mob.horse.breathe", block$stepsound.getVolume1() * 0.6F, block$stepsound.getVolume2());
               }
            } else if(this.bN <= 5) {
               this.makeSound("mob.horse.wood", block$stepsound.getVolume1() * 0.15F, block$stepsound.getVolume2());
            }
         } else if(block$stepsound == Block.f) {
            this.makeSound("mob.horse.wood", block$stepsound.getVolume1() * 0.15F, block$stepsound.getVolume2());
         } else {
            this.makeSound("mob.horse.soft", block$stepsound.getVolume1() * 0.15F, block$stepsound.getVolume2());
         }
      }

   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeMap().b(attributeJumpStrength);
      this.getAttributeInstance(GenericAttributes.maxHealth).setValue(53.0D);
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.22499999403953552D);
   }

   public int bV() {
      return 6;
   }

   public int getMaxDomestication() {
      return this.maxDomestication;
   }

   protected float bB() {
      return 0.8F;
   }

   public int w() {
      return 400;
   }

   private void dc() {
      this.bO = null;
   }

   public void g(EntityHuman p_g_1_) {
      if(!this.world.isClientSide && (this.passenger == null || this.passenger == p_g_1_) && this.isTame()) {
         this.inventoryChest.a(this.getName());
         p_g_1_.openHorseInventory(this, this.inventoryChest);
      }

   }

   public boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      if(itemstack != null && itemstack.getItem() == Items.SPAWN_EGG) {
         return super.a(p_a_1_);
      } else if(!this.isTame() && this.cR()) {
         return false;
      } else if(this.isTame() && this.cn() && p_a_1_.isSneaking()) {
         this.g(p_a_1_);
         return true;
      } else if(this.cp() && this.passenger != null) {
         return super.a(p_a_1_);
      } else {
         if(itemstack != null) {
            boolean flag = false;
            if(this.cO()) {
               byte b0 = -1;
               if(itemstack.getItem() == Items.IRON_HORSE_ARMOR) {
                  b0 = 1;
               } else if(itemstack.getItem() == Items.GOLDEN_HORSE_ARMOR) {
                  b0 = 2;
               } else if(itemstack.getItem() == Items.DIAMOND_HORSE_ARMOR) {
                  b0 = 3;
               }

               if(b0 >= 0) {
                  if(!this.isTame()) {
                     this.cW();
                     return true;
                  }

                  this.g(p_a_1_);
                  return true;
               }
            }

            if(!flag && !this.cR()) {
               float f = 0.0F;
               short short1 = 0;
               byte b1 = 0;
               if(itemstack.getItem() == Items.WHEAT) {
                  f = 2.0F;
                  short1 = 20;
                  b1 = 3;
               } else if(itemstack.getItem() == Items.SUGAR) {
                  f = 1.0F;
                  short1 = 30;
                  b1 = 3;
               } else if(Block.asBlock(itemstack.getItem()) == Blocks.HAY_BLOCK) {
                  f = 20.0F;
                  short1 = 180;
               } else if(itemstack.getItem() == Items.APPLE) {
                  f = 3.0F;
                  short1 = 60;
                  b1 = 3;
               } else if(itemstack.getItem() == Items.GOLDEN_CARROT) {
                  f = 4.0F;
                  short1 = 60;
                  b1 = 5;
                  if(this.isTame() && this.getAge() == 0) {
                     flag = true;
                     this.c(p_a_1_);
                  }
               } else if(itemstack.getItem() == Items.GOLDEN_APPLE) {
                  f = 10.0F;
                  short1 = 240;
                  b1 = 10;
                  if(this.isTame() && this.getAge() == 0) {
                     flag = true;
                     this.c(p_a_1_);
                  }
               }

               if(this.getHealth() < this.getMaxHealth() && f > 0.0F) {
                  this.heal(f, RegainReason.EATING);
                  flag = true;
               }

               if(!this.cn() && short1 > 0) {
                  this.setAge(short1);
                  flag = true;
               }

               if(b1 > 0 && (flag || !this.isTame()) && b1 < this.getMaxDomestication()) {
                  flag = true;
                  this.u(b1);
               }

               if(flag) {
                  this.cY();
               }
            }

            if(!this.isTame() && !flag) {
               if(itemstack != null && itemstack.a((EntityHuman)p_a_1_, (EntityLiving)this)) {
                  return true;
               }

               this.cW();
               return true;
            }

            if(!flag && this.cP() && !this.hasChest() && itemstack.getItem() == Item.getItemOf(Blocks.CHEST)) {
               this.setHasChest(true);
               this.makeSound("mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
               flag = true;
               this.loadChest();
            }

            if(!flag && this.cp() && !this.cG() && itemstack.getItem() == Items.SADDLE) {
               this.g(p_a_1_);
               return true;
            }

            if(flag) {
               if(!p_a_1_.abilities.canInstantlyBuild && --itemstack.count == 0) {
                  p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, (ItemStack)null);
               }

               return true;
            }
         }

         if(this.cp() && this.passenger == null) {
            if(itemstack != null && itemstack.a((EntityHuman)p_a_1_, (EntityLiving)this)) {
               return true;
            } else {
               this.i(p_a_1_);
               return true;
            }
         } else {
            return super.a(p_a_1_);
         }
      }
   }

   private void i(EntityHuman p_i_1_) {
      p_i_1_.yaw = this.yaw;
      p_i_1_.pitch = this.pitch;
      this.r(false);
      this.s(false);
      if(!this.world.isClientSide) {
         p_i_1_.mount(this);
      }

   }

   public boolean cO() {
      return this.getType() == 0;
   }

   public boolean cP() {
      int i = this.getType();
      return i == 2 || i == 1;
   }

   protected boolean bD() {
      return this.passenger != null && this.cG()?true:this.cy() || this.cz();
   }

   public boolean cR() {
      int i = this.getType();
      return i == 3 || i == 4;
   }

   public boolean cS() {
      return this.cR() || this.getType() == 2;
   }

   public boolean d(ItemStack p_d_1_) {
      return false;
   }

   private void de() {
      this.bm = 1;
   }

   public void die(DamageSource p_die_1_) {
      super.die(p_die_1_);
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      super.dropDeathLoot(p_dropDeathLoot_1_, p_dropDeathLoot_2_);
      if(!this.world.isClientSide) {
         this.dropChest();
      }

   }

   public void m() {
      if(this.random.nextInt(200) == 0) {
         this.de();
      }

      super.m();
      if(!this.world.isClientSide) {
         if(this.random.nextInt(900) == 0 && this.deathTicks == 0) {
            this.heal(1.0F, RegainReason.REGEN);
         }

         if(!this.cy() && this.passenger == null && this.random.nextInt(300) == 0 && this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.locY) - 1, MathHelper.floor(this.locZ))).getBlock() == Blocks.GRASS) {
            this.r(true);
         }

         if(this.cy() && ++this.bB > 50) {
            this.bB = 0;
            this.r(false);
         }

         if(this.cA() && !this.cn() && !this.cy()) {
            EntityHorse entityhorse = this.a(this, 16.0D);
            if(entityhorse != null && this.h(entityhorse) > 4.0D) {
               this.navigation.a((Entity)entityhorse);
            }
         }
      }

   }

   public void t_() {
      super.t_();
      if(this.world.isClientSide && this.datawatcher.a()) {
         this.datawatcher.e();
         this.dc();
      }

      if(this.bC > 0 && ++this.bC > 30) {
         this.bC = 0;
         this.c(128, false);
      }

      if(!this.world.isClientSide && this.bD > 0 && ++this.bD > 20) {
         this.bD = 0;
         this.s(false);
      }

      if(this.bm > 0 && ++this.bm > 8) {
         this.bm = 0;
      }

      if(this.bo > 0) {
         ++this.bo;
         if(this.bo > 300) {
            this.bo = 0;
         }
      }

      this.bI = this.bH;
      if(this.cy()) {
         this.bH += (1.0F - this.bH) * 0.4F + 0.05F;
         if(this.bH > 1.0F) {
            this.bH = 1.0F;
         }
      } else {
         this.bH += (0.0F - this.bH) * 0.4F - 0.05F;
         if(this.bH < 0.0F) {
            this.bH = 0.0F;
         }
      }

      this.bK = this.bJ;
      if(this.cz()) {
         this.bI = this.bH = 0.0F;
         this.bJ += (1.0F - this.bJ) * 0.4F + 0.05F;
         if(this.bJ > 1.0F) {
            this.bJ = 1.0F;
         }
      } else {
         this.bG = false;
         this.bJ += (0.8F * this.bJ * this.bJ * this.bJ - this.bJ) * 0.6F - 0.05F;
         if(this.bJ < 0.0F) {
            this.bJ = 0.0F;
         }
      }

      this.bM = this.bL;
      if(this.w(128)) {
         this.bL += (1.0F - this.bL) * 0.7F + 0.05F;
         if(this.bL > 1.0F) {
            this.bL = 1.0F;
         }
      } else {
         this.bL += (0.0F - this.bL) * 0.7F - 0.05F;
         if(this.bL < 0.0F) {
            this.bL = 0.0F;
         }
      }

   }

   private void df() {
      if(!this.world.isClientSide) {
         this.bC = 1;
         this.c(128, true);
      }

   }

   private boolean dg() {
      return this.passenger == null && this.vehicle == null && this.isTame() && this.cn() && !this.cS() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
   }

   public void f(boolean p_f_1_) {
      this.c(32, p_f_1_);
   }

   public void r(boolean p_r_1_) {
      this.f(p_r_1_);
   }

   public void s(boolean p_s_1_) {
      if(p_s_1_) {
         this.r(false);
      }

      this.c(64, p_s_1_);
   }

   private void dh() {
      if(!this.world.isClientSide) {
         this.bD = 1;
         this.s(true);
      }

   }

   public void cW() {
      this.dh();
      String s = this.cH();
      if(s != null) {
         this.makeSound(s, this.bB(), this.bC());
      }

   }

   public void dropChest() {
      this.a((Entity)this, (InventoryHorseChest)this.inventoryChest);
      this.cE();
   }

   private void a(Entity p_a_1_, InventoryHorseChest p_a_2_) {
      if(p_a_2_ != null && !this.world.isClientSide) {
         for(int i = 0; i < p_a_2_.getSize(); ++i) {
            ItemStack itemstack = p_a_2_.getItem(i);
            if(itemstack != null) {
               this.a(itemstack, 0.0F);
            }
         }
      }

   }

   public boolean h(EntityHuman p_h_1_) {
      this.setOwnerUUID(p_h_1_.getUniqueID().toString());
      this.setTame(true);
      return true;
   }

   public void g(float p_g_1_, float p_g_2_) {
      if(this.passenger != null && this.passenger instanceof EntityLiving && this.cG()) {
         this.lastYaw = this.yaw = this.passenger.yaw;
         this.pitch = this.passenger.pitch * 0.5F;
         this.setYawPitch(this.yaw, this.pitch);
         this.aK = this.aI = this.yaw;
         p_g_1_ = ((EntityLiving)this.passenger).aZ * 0.5F;
         p_g_2_ = ((EntityLiving)this.passenger).ba;
         if(p_g_2_ <= 0.0F) {
            p_g_2_ *= 0.25F;
            this.bN = 0;
         }

         if(this.onGround && this.br == 0.0F && this.cz() && !this.bG) {
            p_g_1_ = 0.0F;
            p_g_2_ = 0.0F;
         }

         if(this.br > 0.0F && !this.cv() && this.onGround) {
            this.motY = this.getJumpStrength() * (double)this.br;
            if(this.hasEffect(MobEffectList.JUMP)) {
               this.motY += (double)((float)(this.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F);
            }

            this.m(true);
            this.ai = true;
            if(p_g_2_ > 0.0F) {
               float f = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
               float f1 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
               this.motX += (double)(-0.4F * f * this.br);
               this.motZ += (double)(0.4F * f1 * this.br);
               this.makeSound("mob.horse.jump", 0.4F, 1.0F);
            }

            this.br = 0.0F;
         }

         this.S = 1.0F;
         this.aM = this.bI() * 0.1F;
         if(!this.world.isClientSide) {
            this.k((float)this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue());
            super.g(p_g_1_, p_g_2_);
         }

         if(this.onGround) {
            this.br = 0.0F;
            this.m(false);
         }

         this.aA = this.aB;
         double d0 = this.locX - this.lastX;
         double d1 = this.locZ - this.lastZ;
         float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
         if(f2 > 1.0F) {
            f2 = 1.0F;
         }

         this.aB += (f2 - this.aB) * 0.4F;
         this.aC += this.aB;
      } else {
         this.S = 0.5F;
         this.aM = 0.02F;
         super.g(p_g_1_, p_g_2_);
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setBoolean("EatingHaystack", this.cy());
      p_b_1_.setBoolean("ChestedHorse", this.hasChest());
      p_b_1_.setBoolean("HasReproduced", this.cB());
      p_b_1_.setBoolean("Bred", this.cA());
      p_b_1_.setInt("Type", this.getType());
      p_b_1_.setInt("Variant", this.getVariant());
      p_b_1_.setInt("Temper", this.getTemper());
      p_b_1_.setBoolean("Tame", this.isTame());
      p_b_1_.setString("OwnerUUID", this.getOwnerUUID());
      p_b_1_.setInt("Bukkit.MaxDomestication", this.maxDomestication);
      if(this.hasChest()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(int i = 2; i < this.inventoryChest.getSize(); ++i) {
            ItemStack itemstack = this.inventoryChest.getItem(i);
            if(itemstack != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.setByte("Slot", (byte)i);
               itemstack.save(nbttagcompound);
               nbttaglist.add(nbttagcompound);
            }
         }

         p_b_1_.set("Items", nbttaglist);
      }

      if(this.inventoryChest.getItem(1) != null) {
         p_b_1_.set("ArmorItem", this.inventoryChest.getItem(1).save(new NBTTagCompound()));
      }

      if(this.inventoryChest.getItem(0) != null) {
         p_b_1_.set("SaddleItem", this.inventoryChest.getItem(0).save(new NBTTagCompound()));
      }

   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.r(p_a_1_.getBoolean("EatingHaystack"));
      this.n(p_a_1_.getBoolean("Bred"));
      this.setHasChest(p_a_1_.getBoolean("ChestedHorse"));
      this.p(p_a_1_.getBoolean("HasReproduced"));
      this.setType(p_a_1_.getInt("Type"));
      this.setVariant(p_a_1_.getInt("Variant"));
      this.setTemper(p_a_1_.getInt("Temper"));
      this.setTame(p_a_1_.getBoolean("Tame"));
      String s = "";
      if(p_a_1_.hasKeyOfType("OwnerUUID", 8)) {
         s = p_a_1_.getString("OwnerUUID");
      } else {
         String s1 = p_a_1_.getString("Owner");
         if((s1 == null || s1.isEmpty()) && p_a_1_.hasKey("OwnerName")) {
            String s2 = p_a_1_.getString("OwnerName");
            if(s2 != null && !s2.isEmpty()) {
               s1 = s2;
            }
         }

         s = NameReferencingFileConverter.a(s1);
      }

      if(s.length() > 0) {
         this.setOwnerUUID(s);
      }

      if(p_a_1_.hasKey("Bukkit.MaxDomestication")) {
         this.maxDomestication = p_a_1_.getInt("Bukkit.MaxDomestication");
      }

      AttributeInstance attributeinstance = this.getAttributeMap().a("Speed");
      if(attributeinstance != null) {
         this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(attributeinstance.b() * 0.25D);
      }

      if(this.hasChest()) {
         NBTTagList nbttaglist = p_a_1_.getList("Items", 10);
         this.loadChest();

         for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            if(j >= 2 && j < this.inventoryChest.getSize()) {
               this.inventoryChest.setItem(j, ItemStack.createStack(nbttagcompound));
            }
         }
      }

      if(p_a_1_.hasKeyOfType("ArmorItem", 10)) {
         ItemStack itemstack = ItemStack.createStack(p_a_1_.getCompound("ArmorItem"));
         if(itemstack != null && a(itemstack.getItem())) {
            this.inventoryChest.setItem(1, itemstack);
         }
      }

      if(p_a_1_.hasKeyOfType("SaddleItem", 10)) {
         ItemStack itemstack1 = ItemStack.createStack(p_a_1_.getCompound("SaddleItem"));
         if(itemstack1 != null && itemstack1.getItem() == Items.SADDLE) {
            this.inventoryChest.setItem(0, itemstack1);
         }
      } else if(p_a_1_.getBoolean("Saddle")) {
         this.inventoryChest.setItem(0, new ItemStack(Items.SADDLE));
      }

      this.db();
   }

   public boolean mate(EntityAnimal p_mate_1_) {
      if(p_mate_1_ == this) {
         return false;
      } else if(p_mate_1_.getClass() != this.getClass()) {
         return false;
      } else {
         EntityHorse entityhorse = (EntityHorse)p_mate_1_;
         if(this.dg() && entityhorse.dg()) {
            int i = this.getType();
            int j = entityhorse.getType();
            return i == j || i == 0 && j == 1 || i == 1 && j == 0;
         } else {
            return false;
         }
      }
   }

   public EntityAgeable createChild(EntityAgeable p_createChild_1_) {
      EntityHorse entityhorse = (EntityHorse)p_createChild_1_;
      EntityHorse entityhorse1 = new EntityHorse(this.world);
      int i = this.getType();
      int j = entityhorse.getType();
      int k = 0;
      if(i == j) {
         k = i;
      } else if(i == 0 && j == 1 || i == 1 && j == 0) {
         k = 2;
      }

      if(k == 0) {
         int l = this.random.nextInt(9);
         int i1;
         if(l < 4) {
            i1 = this.getVariant() & 255;
         } else if(l < 8) {
            i1 = entityhorse.getVariant() & 255;
         } else {
            i1 = this.random.nextInt(7);
         }

         int j1 = this.random.nextInt(5);
         if(j1 < 2) {
            i1 = i1 | this.getVariant() & '\uff00';
         } else if(j1 < 4) {
            i1 = i1 | entityhorse.getVariant() & '\uff00';
         } else {
            i1 = i1 | this.random.nextInt(5) << 8 & '\uff00';
         }

         entityhorse1.setVariant(i1);
      }

      entityhorse1.setType(k);
      double d0 = this.getAttributeInstance(GenericAttributes.maxHealth).b() + p_createChild_1_.getAttributeInstance(GenericAttributes.maxHealth).b() + (double)this.di();
      entityhorse1.getAttributeInstance(GenericAttributes.maxHealth).setValue(d0 / 3.0D);
      double d1 = this.getAttributeInstance(attributeJumpStrength).b() + p_createChild_1_.getAttributeInstance(attributeJumpStrength).b() + this.dj();
      entityhorse1.getAttributeInstance(attributeJumpStrength).setValue(d1 / 3.0D);
      double d2 = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).b() + p_createChild_1_.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).b() + this.dk();
      entityhorse1.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(d2 / 3.0D);
      return entityhorse1;
   }

   public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_) {
      Object object = super.prepare(p_prepare_1_, p_prepare_2_);
      int i = 0;
      int j;
      if(object instanceof EntityHorse.GroupDataHorse) {
         j = ((EntityHorse.GroupDataHorse)object).a;
         i = ((EntityHorse.GroupDataHorse)object).b & 255 | this.random.nextInt(5) << 8;
      } else {
         if(this.random.nextInt(10) == 0) {
            j = 1;
         } else {
            int k = this.random.nextInt(7);
            int l = this.random.nextInt(5);
            j = 0;
            i = k | l << 8;
         }

         object = new EntityHorse.GroupDataHorse(j, i);
      }

      this.setType(j);
      this.setVariant(i);
      if(this.random.nextInt(5) == 0) {
         this.setAgeRaw(-24000);
      }

      if(j != 4 && j != 3) {
         this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double)this.di());
         if(j == 0) {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.dk());
         } else {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.17499999701976776D);
         }
      } else {
         this.getAttributeInstance(GenericAttributes.maxHealth).setValue(15.0D);
         this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
      }

      if(j != 2 && j != 1) {
         this.getAttributeInstance(attributeJumpStrength).setValue(this.dj());
      } else {
         this.getAttributeInstance(attributeJumpStrength).setValue(0.5D);
      }

      this.setHealth(this.getMaxHealth());
      return (GroupDataEntity)object;
   }

   public void v(int p_v_1_) {
      if(this.cG()) {
         if(p_v_1_ < 0) {
            p_v_1_ = 0;
         }

         float f;
         if(p_v_1_ >= 90) {
            f = 1.0F;
         } else {
            f = 0.4F + 0.4F * (float)p_v_1_ / 90.0F;
         }

         HorseJumpEvent horsejumpevent = CraftEventFactory.callHorseJumpEvent(this, f);
         if(!horsejumpevent.isCancelled()) {
            this.bG = true;
            this.dh();
            this.br = f;
         }
      }

   }

   public void al() {
      super.al();
      if(this.bK > 0.0F) {
         float f = MathHelper.sin(this.aI * 3.1415927F / 180.0F);
         float f1 = MathHelper.cos(this.aI * 3.1415927F / 180.0F);
         float f2 = 0.7F * this.bK;
         float f3 = 0.15F * this.bK;
         this.passenger.setPosition(this.locX + (double)(f2 * f), this.locY + this.an() + this.passenger.am() + (double)f3, this.locZ - (double)(f2 * f1));
         if(this.passenger instanceof EntityLiving) {
            ((EntityLiving)this.passenger).aI = this.aI;
         }
      }

   }

   private float di() {
      return 15.0F + (float)this.random.nextInt(8) + (float)this.random.nextInt(9);
   }

   private double dj() {
      return 0.4000000059604645D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
   }

   private double dk() {
      return (0.44999998807907104D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
   }

   public static boolean a(Item p_a_0_) {
      return p_a_0_ == Items.IRON_HORSE_ARMOR || p_a_0_ == Items.GOLDEN_HORSE_ARMOR || p_a_0_ == Items.DIAMOND_HORSE_ARMOR;
   }

   public boolean k_() {
      return false;
   }

   public float getHeadHeight() {
      return this.length;
   }

   public boolean d(int p_d_1_, ItemStack p_d_2_) {
      if(p_d_1_ == 499 && this.cP()) {
         if(p_d_2_ == null && this.hasChest()) {
            this.setHasChest(false);
            this.loadChest();
            return true;
         }

         if(p_d_2_ != null && p_d_2_.getItem() == Item.getItemOf(Blocks.CHEST) && !this.hasChest()) {
            this.setHasChest(true);
            this.loadChest();
            return true;
         }
      }

      int i = p_d_1_ - 400;
      if(i >= 0 && i < 2 && i < this.inventoryChest.getSize()) {
         if(i == 0 && p_d_2_ != null && p_d_2_.getItem() != Items.SADDLE) {
            return false;
         } else if(i != 1 || (p_d_2_ == null || a(p_d_2_.getItem())) && this.cO()) {
            this.inventoryChest.setItem(i, p_d_2_);
            this.db();
            return true;
         } else {
            return false;
         }
      } else {
         int j = p_d_1_ - 500 + 2;
         if(j >= 2 && j < this.inventoryChest.getSize()) {
            this.inventoryChest.setItem(j, p_d_2_);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class GroupDataHorse implements GroupDataEntity {
      public int a;
      public int b;

      public GroupDataHorse(int p_i163_1_, int p_i163_2_) {
         this.a = p_i163_1_;
         this.b = p_i163_2_;
      }
   }
}

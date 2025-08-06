package net.minecraft.server.v1_8_R3;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeMapBase;
import net.minecraft.server.v1_8_R3.AttributeMapServer;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CombatTracker;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import net.minecraft.server.v1_8_R3.EntityTracker;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutCollect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PotionBrewer;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_8_R3.TrigMath;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.event.entity.EntityDismountEvent;

public abstract class EntityLiving extends Entity {
   private static final UUID a = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final AttributeModifier b = (new AttributeModifier(a, "Sprinting speed boost", 0.30000001192092896D, 2)).a(false);
   private AttributeMapBase c;
   public CombatTracker combatTracker = new CombatTracker(this);
   public final Map<Integer, MobEffect> effects = Maps.<Integer, MobEffect>newHashMap();
   private final ItemStack[] h = new ItemStack[5];
   public boolean ar;
   public int as;
   public int at;
   public int hurtTicks;
   public int av;
   public float aw;
   public int deathTicks;
   public float ay;
   public float az;
   public float aA;
   public float aB;
   public float aC;
   public int maxNoDamageTicks = 20;
   public float aE;
   public float aF;
   public float aG;
   public float aH;
   public float aI;
   public float aJ;
   public float aK;
   public float aL;
   public float aM = 0.02F;
   public EntityHuman killer;
   protected int lastDamageByPlayerTime;
   protected boolean aP;
   protected int ticksFarFromPlayer;
   protected float aR;
   protected float aS;
   protected float aT;
   protected float aU;
   protected float aV;
   protected int aW;
   public float lastDamage;
   protected boolean aY;
   public float aZ;
   public float ba;
   protected float bb;
   protected int bc;
   protected double bd;
   protected double be;
   protected double bf;
   protected double bg;
   protected double bh;
   public boolean updateEffects = true;
   public EntityLiving lastDamager;
   public int hurtTimestamp;
   private EntityLiving bk;
   private int bl;
   private float bm;
   private int bn;
   private float bo;
   public int expToDrop;
   public int maxAirTicks = 300;
   ArrayList<org.bukkit.inventory.ItemStack> drops = null;
   private boolean isTickingEffects = false;
   private List<Object> effectsToProcess = Lists.<Object>newArrayList();

   public void inactiveTick() {
      super.inactiveTick();
      ++this.ticksFarFromPlayer;
   }

   public void G() {
      this.damageEntity(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
   }

   public EntityLiving(World p_i72_1_) {
      super(p_i72_1_);
      this.initAttributes();
      this.datawatcher.watch(6, Float.valueOf((float)this.getAttributeInstance(GenericAttributes.maxHealth).getValue()));
      this.k = true;
      this.aH = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
      this.setPosition(this.locX, this.locY, this.locZ);
      this.aG = (float)Math.random() * 12398.0F;
      this.yaw = (float)(Math.random() * 3.1415927410125732D * 2.0D);
      this.aK = this.yaw;
      this.S = 0.6F;
   }

   protected void h() {
      this.datawatcher.a(7, Integer.valueOf(0));
      this.datawatcher.a(8, Byte.valueOf((byte)0));
      this.datawatcher.a(9, Byte.valueOf((byte)0));
      this.datawatcher.a(6, Float.valueOf(1.0F));
   }

   protected void initAttributes() {
      this.getAttributeMap().b(GenericAttributes.maxHealth);
      this.getAttributeMap().b(GenericAttributes.c);
      this.getAttributeMap().b(GenericAttributes.MOVEMENT_SPEED);
   }

   protected void a(double p_a_1_, boolean p_a_3_, Block p_a_4_, BlockPosition p_a_5_) {
      if(!this.V()) {
         this.W();
      }

      if(!this.world.isClientSide && this.fallDistance > 3.0F && p_a_3_) {
         IBlockData iblockdata = this.world.getType(p_a_5_);
         Block block = iblockdata.getBlock();
         float f = (float)MathHelper.f(this.fallDistance - 3.0F);
         if(block.getMaterial() != Material.AIR) {
            double d0 = (double)Math.min(0.2F + f / 15.0F, 10.0F);
            if(d0 > 2.5D) {
               d0 = 2.5D;
            }

            int i = (int)(150.0D * d0);
            if(this instanceof EntityPlayer) {
               ((WorldServer)this.world).sendParticles((EntityPlayer)this, EnumParticle.BLOCK_DUST, false, this.locX, this.locY, this.locZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[]{Block.getCombinedId(iblockdata)});
            } else {
               ((WorldServer)this.world).a(EnumParticle.BLOCK_DUST, this.locX, this.locY, this.locZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[]{Block.getCombinedId(iblockdata)});
            }
         }
      }

      super.a(p_a_1_, p_a_3_, p_a_4_, p_a_5_);
   }

   public boolean aY() {
      return false;
   }

   public void K() {
      this.ay = this.az;
      super.K();
      this.world.methodProfiler.a("livingEntityBaseTick");
      boolean flag = this instanceof EntityHuman;
      if(this.isAlive()) {
         if(this.inBlock()) {
            this.damageEntity(DamageSource.STUCK, 1.0F);
         } else if(flag && !this.world.getWorldBorder().a(this.getBoundingBox())) {
            double d0 = this.world.getWorldBorder().a((Entity)this) + this.world.getWorldBorder().getDamageBuffer();
            if(d0 < 0.0D) {
               this.damageEntity(DamageSource.STUCK, (float)Math.max(1, MathHelper.floor(-d0 * this.world.getWorldBorder().getDamageAmount())));
            }
         }
      }

      if(this.isFireProof() || this.world.isClientSide) {
         this.extinguish();
      }

      boolean flag1 = flag && ((EntityHuman)this).abilities.isInvulnerable;
      if(this.isAlive()) {
         if(this.a((Material)Material.WATER)) {
            if(!this.aY() && !this.hasEffect(MobEffectList.WATER_BREATHING.id) && !flag1) {
               this.setAirTicks(this.j(this.getAirTicks()));
               if(this.getAirTicks() == -20) {
                  this.setAirTicks(0);

                  for(int i = 0; i < 8; ++i) {
                     float f = this.random.nextFloat() - this.random.nextFloat();
                     float f1 = this.random.nextFloat() - this.random.nextFloat();
                     float f2 = this.random.nextFloat() - this.random.nextFloat();
                     this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + (double)f, this.locY + (double)f1, this.locZ + (double)f2, this.motX, this.motY, this.motZ, new int[0]);
                  }

                  this.damageEntity(DamageSource.DROWN, 2.0F);
               }
            }

            if(!this.world.isClientSide && this.au() && this.vehicle instanceof EntityLiving) {
               this.mount((Entity)null);
            }
         } else if(this.getAirTicks() != 300) {
            this.setAirTicks(this.maxAirTicks);
         }
      }

      if(this.isAlive() && this.U()) {
         this.extinguish();
      }

      this.aE = this.aF;
      if(this.hurtTicks > 0) {
         --this.hurtTicks;
      }

      if(this.noDamageTicks > 0 && !(this instanceof EntityPlayer)) {
         --this.noDamageTicks;
      }

      if(this.getHealth() <= 0.0F) {
         this.aZ();
      }

      if(this.lastDamageByPlayerTime > 0) {
         --this.lastDamageByPlayerTime;
      } else {
         this.killer = null;
      }

      if(this.bk != null && !this.bk.isAlive()) {
         this.bk = null;
      }

      if(this.lastDamager != null) {
         if(!this.lastDamager.isAlive()) {
            this.b((EntityLiving)null);
         } else if(this.ticksLived - this.hurtTimestamp > 100) {
            this.b((EntityLiving)null);
         }
      }

      this.bi();
      this.aU = this.aT;
      this.aJ = this.aI;
      this.aL = this.aK;
      this.lastYaw = this.yaw;
      this.lastPitch = this.pitch;
      this.world.methodProfiler.b();
   }

   public int getExpReward() {
      int i = this.getExpValue(this.killer);
      return !this.world.isClientSide && (this.lastDamageByPlayerTime > 0 || this.alwaysGivesExp()) && this.ba() && this.world.getGameRules().getBoolean("doMobLoot")?i:0;
   }

   public boolean isBaby() {
      return false;
   }

   protected void aZ() {
      ++this.deathTicks;
      if(this.deathTicks >= 20 && !this.dead) {
         int i = this.expToDrop;

         while(i > 0) {
            int j = EntityExperienceOrb.getOrbValue(i);
            i -= j;
            this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
         }

         this.expToDrop = 0;
         this.die();

         for(i = 0; i < 20; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, this.locX + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, this.locY + (double)(this.random.nextFloat() * this.length), this.locZ + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
         }
      }

   }

   protected boolean ba() {
      return !this.isBaby();
   }

   protected int j(int p_j_1_) {
      int i = EnchantmentManager.getOxygenEnchantmentLevel(this);
      return i > 0 && this.random.nextInt(i + 1) > 0?p_j_1_:p_j_1_ - 1;
   }

   protected int getExpValue(EntityHuman p_getExpValue_1_) {
      return 0;
   }

   protected boolean alwaysGivesExp() {
      return false;
   }

   public Random bc() {
      return this.random;
   }

   public EntityLiving getLastDamager() {
      return this.lastDamager;
   }

   public int be() {
      return this.hurtTimestamp;
   }

   public void b(EntityLiving p_b_1_) {
      this.lastDamager = p_b_1_;
      this.hurtTimestamp = this.ticksLived;
   }

   public EntityLiving bf() {
      return this.bk;
   }

   public int bg() {
      return this.bl;
   }

   public void p(Entity p_p_1_) {
      if(p_p_1_ instanceof EntityLiving) {
         this.bk = (EntityLiving)p_p_1_;
      } else {
         this.bk = null;
      }

      this.bl = this.ticksLived;
   }

   public int bh() {
      return this.ticksFarFromPlayer;
   }

   public void b(NBTTagCompound p_b_1_) {
      p_b_1_.setFloat("HealF", this.getHealth());
      p_b_1_.setShort("Health", (short)((int)Math.ceil((double)this.getHealth())));
      p_b_1_.setShort("HurtTime", (short)this.hurtTicks);
      p_b_1_.setInt("HurtByTimestamp", this.hurtTimestamp);
      p_b_1_.setShort("DeathTime", (short)this.deathTicks);
      p_b_1_.setFloat("AbsorptionAmount", this.getAbsorptionHearts());

      for(ItemStack itemstack : this.getEquipment()) {
         if(itemstack != null) {
            this.c.a(itemstack.B());
         }
      }

      p_b_1_.set("Attributes", GenericAttributes.a(this.getAttributeMap()));

      for(ItemStack itemstack1 : this.getEquipment()) {
         if(itemstack1 != null) {
            this.c.b(itemstack1.B());
         }
      }

      if(!this.effects.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(MobEffect mobeffect : this.effects.values()) {
            nbttaglist.add(mobeffect.a(new NBTTagCompound()));
         }

         p_b_1_.set("ActiveEffects", nbttaglist);
      }

   }

   public void a(NBTTagCompound p_a_1_) {
      this.setAbsorptionHearts(p_a_1_.getFloat("AbsorptionAmount"));
      if(p_a_1_.hasKeyOfType("Attributes", 9) && this.world != null && !this.world.isClientSide) {
         GenericAttributes.a(this.getAttributeMap(), p_a_1_.getList("Attributes", 10));
      }

      if(p_a_1_.hasKeyOfType("ActiveEffects", 9)) {
         NBTTagList nbttaglist = p_a_1_.getList("ActiveEffects", 10);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            MobEffect mobeffect = MobEffect.b(nbttagcompound);
            if(mobeffect != null) {
               this.effects.put(Integer.valueOf(mobeffect.getEffectId()), mobeffect);
            }
         }
      }

      if(p_a_1_.hasKey("Bukkit.MaxHealth")) {
         NBTBase nbtbase = p_a_1_.get("Bukkit.MaxHealth");
         if(nbtbase.getTypeId() == 5) {
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double)((NBTTagFloat)nbtbase).c());
         } else if(nbtbase.getTypeId() == 3) {
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double)((NBTTagInt)nbtbase).d());
         }
      }

      if(p_a_1_.hasKeyOfType("HealF", 99)) {
         this.setHealth(p_a_1_.getFloat("HealF"));
      } else {
         NBTBase nbtbase1 = p_a_1_.get("Health");
         if(nbtbase1 == null) {
            this.setHealth(this.getMaxHealth());
         } else if(nbtbase1.getTypeId() == 5) {
            this.setHealth(((NBTTagFloat)nbtbase1).h());
         } else if(nbtbase1.getTypeId() == 2) {
            this.setHealth((float)((NBTTagShort)nbtbase1).e());
         }
      }

      this.hurtTicks = p_a_1_.getShort("HurtTime");
      this.deathTicks = p_a_1_.getShort("DeathTime");
      this.hurtTimestamp = p_a_1_.getInt("HurtByTimestamp");
   }

   protected void bi() {
      Iterator iterator = this.effects.keySet().iterator();
      this.isTickingEffects = true;

      while(iterator.hasNext()) {
         Integer integer = (Integer)iterator.next();
         MobEffect mobeffect = (MobEffect)this.effects.get(integer);
         if(!mobeffect.tick(this)) {
            if(!this.world.isClientSide) {
               iterator.remove();
               this.b(mobeffect);
            }
         } else if(mobeffect.getDuration() % 600 == 0) {
            this.a(mobeffect, false);
         }
      }

      this.isTickingEffects = false;

      for(Object object : this.effectsToProcess) {
         if(object instanceof MobEffect) {
            this.addEffect((MobEffect)object);
         } else {
            this.removeEffect(((Integer)object).intValue());
         }
      }

      if(this.updateEffects) {
         if(!this.world.isClientSide) {
            this.B();
         }

         this.updateEffects = false;
      }

      int i = this.datawatcher.getInt(7);
      boolean flag1 = this.datawatcher.getByte(8) > 0;
      if(i > 0) {
         boolean flag = false;
         if(!this.isInvisible()) {
            flag = this.random.nextBoolean();
         } else {
            flag = this.random.nextInt(15) == 0;
         }

         if(flag1) {
            flag &= this.random.nextInt(5) == 0;
         }

         if(flag && i > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;
            this.world.addParticle(flag1?EnumParticle.SPELL_MOB_AMBIENT:EnumParticle.SPELL_MOB, this.locX + (this.random.nextDouble() - 0.5D) * (double)this.width, this.locY + this.random.nextDouble() * (double)this.length, this.locZ + (this.random.nextDouble() - 0.5D) * (double)this.width, d0, d1, d2, new int[0]);
         }
      }

   }

   protected void B() {
      if(this.effects.isEmpty()) {
         this.bj();
         this.setInvisible(false);
      } else {
         int i = PotionBrewer.a(this.effects.values());
         this.datawatcher.watch(8, Byte.valueOf((byte)(PotionBrewer.b(this.effects.values())?1:0)));
         this.datawatcher.watch(7, Integer.valueOf(i));
         this.setInvisible(this.hasEffect(MobEffectList.INVISIBILITY.id));
      }

   }

   protected void bj() {
      this.datawatcher.watch(8, Byte.valueOf((byte)0));
      this.datawatcher.watch(7, Integer.valueOf(0));
   }

   public void removeAllEffects() {
      Iterator iterator = this.effects.keySet().iterator();

      while(iterator.hasNext()) {
         Integer integer = (Integer)iterator.next();
         MobEffect mobeffect = (MobEffect)this.effects.get(integer);
         if(!this.world.isClientSide) {
            iterator.remove();
            this.b(mobeffect);
         }
      }

   }

   public Collection<MobEffect> getEffects() {
      return this.effects.values();
   }

   public boolean hasEffect(int p_hasEffect_1_) {
      return this.effects.size() != 0 && this.effects.containsKey(Integer.valueOf(p_hasEffect_1_));
   }

   public boolean hasEffect(MobEffectList p_hasEffect_1_) {
      return this.effects.containsKey(Integer.valueOf(p_hasEffect_1_.id));
   }

   public MobEffect getEffect(MobEffectList p_getEffect_1_) {
      return (MobEffect)this.effects.get(Integer.valueOf(p_getEffect_1_.id));
   }

   public void addEffect(MobEffect p_addEffect_1_) {
      AsyncCatcher.catchOp("effect add");
      if(this.isTickingEffects) {
         this.effectsToProcess.add(p_addEffect_1_);
      } else {
         if(this.d(p_addEffect_1_)) {
            if(this.effects.containsKey(Integer.valueOf(p_addEffect_1_.getEffectId()))) {
               ((MobEffect)this.effects.get(Integer.valueOf(p_addEffect_1_.getEffectId()))).a(p_addEffect_1_);
               this.a((MobEffect)this.effects.get(Integer.valueOf(p_addEffect_1_.getEffectId())), true);
            } else {
               this.effects.put(Integer.valueOf(p_addEffect_1_.getEffectId()), p_addEffect_1_);
               this.a(p_addEffect_1_);
            }
         }

      }
   }

   public boolean d(MobEffect p_d_1_) {
      if(this.getMonsterType() == EnumMonsterType.UNDEAD) {
         int i = p_d_1_.getEffectId();
         if(i == MobEffectList.REGENERATION.id || i == MobEffectList.POISON.id) {
            return false;
         }
      }

      return true;
   }

   public boolean bm() {
      return this.getMonsterType() == EnumMonsterType.UNDEAD;
   }

   public void removeEffect(int p_removeEffect_1_) {
      if(this.isTickingEffects) {
         this.effectsToProcess.add(Integer.valueOf(p_removeEffect_1_));
      } else {
         MobEffect mobeffect = (MobEffect)this.effects.remove(Integer.valueOf(p_removeEffect_1_));
         if(mobeffect != null) {
            this.b(mobeffect);
         }

      }
   }

   protected void a(MobEffect p_a_1_) {
      this.updateEffects = true;
      if(!this.world.isClientSide) {
         MobEffectList.byId[p_a_1_.getEffectId()].b(this, this.getAttributeMap(), p_a_1_.getAmplifier());
      }

   }

   protected void a(MobEffect p_a_1_, boolean p_a_2_) {
      this.updateEffects = true;
      if(p_a_2_ && !this.world.isClientSide) {
         MobEffectList.byId[p_a_1_.getEffectId()].a(this, this.getAttributeMap(), p_a_1_.getAmplifier());
         MobEffectList.byId[p_a_1_.getEffectId()].b(this, this.getAttributeMap(), p_a_1_.getAmplifier());
      }

   }

   protected void b(MobEffect p_b_1_) {
      this.updateEffects = true;
      if(!this.world.isClientSide) {
         MobEffectList.byId[p_b_1_.getEffectId()].a(this, this.getAttributeMap(), p_b_1_.getAmplifier());
      }

   }

   public void heal(float p_heal_1_) {
      this.heal(p_heal_1_, RegainReason.CUSTOM);
   }

   public void heal(float p_heal_1_, RegainReason p_heal_2_) {
      float f = this.getHealth();
      if(f > 0.0F) {
         EntityRegainHealthEvent entityregainhealthevent = new EntityRegainHealthEvent(this.getBukkitEntity(), (double)p_heal_1_, p_heal_2_);
         this.world.getServer().getPluginManager().callEvent(entityregainhealthevent);
         if(!entityregainhealthevent.isCancelled()) {
            this.setHealth((float)((double)this.getHealth() + entityregainhealthevent.getAmount()));
         }
      }

   }

   public final float getHealth() {
      return this instanceof EntityPlayer?(float)((EntityPlayer)this).getBukkitEntity().getHealth():this.datawatcher.getFloat(6);
   }

   public void setHealth(float p_setHealth_1_) {
      if(this instanceof EntityPlayer) {
         CraftPlayer craftplayer = ((EntityPlayer)this).getBukkitEntity();
         if(p_setHealth_1_ < 0.0F) {
            craftplayer.setRealHealth(0.0D);
         } else if((double)p_setHealth_1_ > craftplayer.getMaxHealth()) {
            craftplayer.setRealHealth(craftplayer.getMaxHealth());
         } else {
            craftplayer.setRealHealth((double)p_setHealth_1_);
         }

         this.datawatcher.watch(6, Float.valueOf(craftplayer.getScaledHealth()));
      } else {
         this.datawatcher.watch(6, Float.valueOf(MathHelper.a(p_setHealth_1_, 0.0F, this.getMaxHealth())));
      }
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      if(this.isInvulnerable(p_damageEntity_1_)) {
         return false;
      } else if(this.world.isClientSide) {
         return false;
      } else {
         this.ticksFarFromPlayer = 0;
         if(this.getHealth() <= 0.0F) {
            return false;
         } else if(p_damageEntity_1_.o() && this.hasEffect(MobEffectList.FIRE_RESISTANCE)) {
            return false;
         } else {
            this.aB = 1.5F;
            boolean flag = true;
            if((float)this.noDamageTicks > (float)this.maxNoDamageTicks / 2.0F) {
               if(p_damageEntity_2_ <= this.lastDamage) {
                  this.forceExplosionKnockback = true;
                  return false;
               }

               if(!this.d(p_damageEntity_1_, p_damageEntity_2_ - this.lastDamage)) {
                  return false;
               }

               this.lastDamage = p_damageEntity_2_;
               flag = false;
            } else {
               this.getHealth();
               if(!this.d(p_damageEntity_1_, p_damageEntity_2_)) {
                  return false;
               }

               this.lastDamage = p_damageEntity_2_;
               this.noDamageTicks = this.maxNoDamageTicks;
               this.hurtTicks = this.av = 10;
            }

            if(this instanceof EntityAnimal) {
               ((EntityAnimal)this).cq();
               if(this instanceof EntityTameableAnimal) {
                  ((EntityTameableAnimal)this).getGoalSit().setSitting(false);
               }
            }

            this.aw = 0.0F;
            Entity entity = p_damageEntity_1_.getEntity();
            if(entity != null) {
               if(entity instanceof EntityLiving) {
                  this.b((EntityLiving)entity);
               }

               if(entity instanceof EntityHuman) {
                  this.lastDamageByPlayerTime = 100;
                  this.killer = (EntityHuman)entity;
               } else if(entity instanceof EntityWolf) {
                  EntityWolf entitywolf = (EntityWolf)entity;
                  if(entitywolf.isTamed()) {
                     this.lastDamageByPlayerTime = 100;
                     this.killer = null;
                  }
               }
            }

            if(flag) {
               this.world.broadcastEntityEffect(this, (byte)2);
               if(p_damageEntity_1_ != DamageSource.DROWN) {
                  this.ac();
               }

               if(entity != null) {
                  double d0 = entity.locX - this.locX;

                  double d1;
                  for(d1 = entity.locZ - this.locZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                     d0 = (Math.random() - Math.random()) * 0.01D;
                  }

                  this.aw = (float)(MathHelper.b(d1, d0) * 180.0D / 3.1415927410125732D - (double)this.yaw);
                  this.a(entity, p_damageEntity_2_, d0, d1);
               } else {
                  this.aw = (float)((int)(Math.random() * 2.0D) * 180);
               }
            }

            if(this.getHealth() <= 0.0F) {
               String s = this.bp();
               if(flag && s != null) {
                  this.makeSound(s, this.bB(), this.bC());
               }

               this.die(p_damageEntity_1_);
            } else {
               String s1 = this.bo();
               if(flag && s1 != null) {
                  this.makeSound(s1, this.bB(), this.bC());
               }
            }

            return true;
         }
      }
   }

   public void b(ItemStack p_b_1_) {
      this.makeSound("random.break", 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);

      for(int i = 0; i < 5; ++i) {
         Vec3D vec3d = new Vec3D(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
         vec3d = vec3d.a(-this.pitch * 3.1415927F / 180.0F);
         vec3d = vec3d.b(-this.yaw * 3.1415927F / 180.0F);
         double d0 = (double)(-this.random.nextFloat()) * 0.6D - 0.3D;
         Vec3D vec3d1 = new Vec3D(((double)this.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
         vec3d1 = vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
         vec3d1 = vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
         vec3d1 = vec3d1.add(this.locX, this.locY + (double)this.getHeadHeight(), this.locZ);
         this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[]{Item.getId(p_b_1_.getItem())});
      }

   }

   public void die(DamageSource p_die_1_) {
      Entity entity = p_die_1_.getEntity();
      EntityLiving entityliving = this.bt();
      if(this.aW >= 0 && entityliving != null) {
         entityliving.b(this, this.aW);
      }

      if(entity != null) {
         entity.a(this);
      }

      this.aP = true;
      this.bs().g();
      if(!this.world.isClientSide) {
         int i = 0;
         if(entity instanceof EntityHuman) {
            i = EnchantmentManager.getBonusMonsterLootEnchantmentLevel((EntityLiving)entity);
         }

         if(this.ba() && this.world.getGameRules().getBoolean("doMobLoot")) {
            this.drops = new ArrayList();
            this.dropDeathLoot(this.lastDamageByPlayerTime > 0, i);
            this.dropEquipment(this.lastDamageByPlayerTime > 0, i);
            if(this.lastDamageByPlayerTime > 0 && this.random.nextFloat() < 0.025F + (float)i * 0.01F) {
               this.getRareDrop();
            }

            CraftEventFactory.callEntityDeathEvent(this, this.drops);
            this.drops = null;
         } else {
            CraftEventFactory.callEntityDeathEvent(this);
         }
      }

      this.world.broadcastEntityEffect(this, (byte)3);
   }

   protected void dropEquipment(boolean p_dropEquipment_1_, int p_dropEquipment_2_) {
   }

   public void a(Entity p_a_1_, float p_a_2_, double p_a_3_, double p_a_5_) {
      if(this.random.nextDouble() >= this.getAttributeInstance(GenericAttributes.c).getValue()) {
         this.ai = true;
         float f = MathHelper.sqrt(p_a_3_ * p_a_3_ + p_a_5_ * p_a_5_);
         float f1 = 0.4F;
         this.motX /= 2.0D;
         this.motY /= 2.0D;
         this.motZ /= 2.0D;
         this.motX -= p_a_3_ / (double)f * (double)f1;
         this.motY += (double)f1;
         this.motZ -= p_a_5_ / (double)f * (double)f1;
         if(this.motY > 0.4000000059604645D) {
            this.motY = 0.4000000059604645D;
         }
      }

   }

   protected String bo() {
      return "game.neutral.hurt";
   }

   protected String bp() {
      return "game.neutral.die";
   }

   protected void getRareDrop() {
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
   }

   public boolean k_() {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.getBoundingBox().b);
      int k = MathHelper.floor(this.locZ);
      Block block = this.world.getType(new BlockPosition(i, j, k)).getBlock();
      return (block == Blocks.LADDER || block == Blocks.VINE) && (!(this instanceof EntityHuman) || !((EntityHuman)this).isSpectator());
   }

   public boolean isAlive() {
      return !this.dead && this.getHealth() > 0.0F;
   }

   public void e(float p_e_1_, float p_e_2_) {
      super.e(p_e_1_, p_e_2_);
      MobEffect mobeffect = this.getEffect(MobEffectList.JUMP);
      float f = mobeffect != null?(float)(mobeffect.getAmplifier() + 1):0.0F;
      int i = MathHelper.f((p_e_1_ - 3.0F - f) * p_e_2_);
      if(i > 0) {
         if(!this.damageEntity(DamageSource.FALL, (float)i)) {
            return;
         }

         this.makeSound(this.n(i), 1.0F, 1.0F);
         int j = MathHelper.floor(this.locX);
         int k = MathHelper.floor(this.locY - 0.20000000298023224D);
         int l = MathHelper.floor(this.locZ);
         Block block = this.world.getType(new BlockPosition(j, k, l)).getBlock();
         if(block.getMaterial() != Material.AIR) {
            Block.StepSound block$stepsound = block.stepSound;
            this.makeSound(block$stepsound.getStepSound(), block$stepsound.getVolume1() * 0.5F, block$stepsound.getVolume2() * 0.75F);
         }
      }

   }

   protected String n(int p_n_1_) {
      return p_n_1_ > 4?"game.neutral.hurt.fall.big":"game.neutral.hurt.fall.small";
   }

   public int br() {
      int i = 0;

      for(ItemStack itemstack : this.getEquipment()) {
         if(itemstack != null && itemstack.getItem() instanceof ItemArmor) {
            int j = ((ItemArmor)itemstack.getItem()).c;
            i += j;
         }
      }

      return i;
   }

   protected void damageArmor(float p_damageArmor_1_) {
   }

   protected float applyArmorModifier(DamageSource p_applyArmorModifier_1_, float p_applyArmorModifier_2_) {
      if(!p_applyArmorModifier_1_.ignoresArmor()) {
         int i = 25 - this.br();
         float f = p_applyArmorModifier_2_ * (float)i;
         p_applyArmorModifier_2_ = f / 25.0F;
      }

      return p_applyArmorModifier_2_;
   }

   protected float applyMagicModifier(DamageSource p_applyMagicModifier_1_, float p_applyMagicModifier_2_) {
      if(p_applyMagicModifier_1_.isStarvation()) {
         return p_applyMagicModifier_2_;
      } else if(p_applyMagicModifier_2_ <= 0.0F) {
         return 0.0F;
      } else {
         int i = EnchantmentManager.a(this.getEquipment(), p_applyMagicModifier_1_);
         if(i > 20) {
            i = 20;
         }

         if(i > 0 && i <= 20) {
            int j = 25 - i;
            float f = p_applyMagicModifier_2_ * (float)j;
            p_applyMagicModifier_2_ = f / 25.0F;
         }

         return p_applyMagicModifier_2_;
      }
   }

   protected boolean d(final DamageSource p_d_1_, float p_d_2_) {
      if(!this.isInvulnerable(p_d_1_)) {
         final boolean flag = this instanceof EntityHuman;
         float f = p_d_2_;
         Function<Double, Double> function = new Function<Double, Double>() {
            public Double apply(Double p_apply_1_) {
               return (p_d_1_ == DamageSource.ANVIL || p_d_1_ == DamageSource.FALLING_BLOCK) && EntityLiving.this.getEquipment(4) != null?Double.valueOf(-(p_apply_1_.doubleValue() - p_apply_1_.doubleValue() * 0.75D)):Double.valueOf(-0.0D);
            }
         };
         float f1 = ((Double)function.apply(Double.valueOf((double)p_d_2_))).floatValue();
         p_d_2_ = p_d_2_ + f1;
         Function<Double, Double> function1 = new Function<Double, Double>() {
            public Double apply(Double p_apply_1_) {
               return flag && !p_d_1_.ignoresArmor() && ((EntityHuman)EntityLiving.this).isBlocking() && p_apply_1_.doubleValue() > 0.0D?Double.valueOf(-(p_apply_1_.doubleValue() - (1.0D + p_apply_1_.doubleValue()) * 0.5D)):Double.valueOf(-0.0D);
            }
         };
         float f2 = ((Double)function1.apply(Double.valueOf((double)p_d_2_))).floatValue();
         p_d_2_ = p_d_2_ + f2;
         Function<Double, Double> function2 = new Function<Double, Double>() {
            public Double apply(Double p_apply_1_) {
               return Double.valueOf(-(p_apply_1_.doubleValue() - (double)EntityLiving.this.applyArmorModifier(p_d_1_, p_apply_1_.floatValue())));
            }
         };
         float f3 = ((Double)function2.apply(Double.valueOf((double)p_d_2_))).floatValue();
         p_d_2_ = p_d_2_ + f3;
         Function<Double, Double> function3 = new Function<Double, Double>() {
            public Double apply(Double p_apply_1_) {
               if(!p_d_1_.isStarvation() && EntityLiving.this.hasEffect(MobEffectList.RESISTANCE) && p_d_1_ != DamageSource.OUT_OF_WORLD) {
                  int i = (EntityLiving.this.getEffect(MobEffectList.RESISTANCE).getAmplifier() + 1) * 5;
                  int j = 25 - i;
                  float f9 = p_apply_1_.floatValue() * (float)j;
                  return Double.valueOf(-(p_apply_1_.doubleValue() - (double)(f9 / 25.0F)));
               } else {
                  return Double.valueOf(-0.0D);
               }
            }
         };
         float f4 = ((Double)function3.apply(Double.valueOf((double)p_d_2_))).floatValue();
         p_d_2_ = p_d_2_ + f4;
         Function<Double, Double> function4 = new Function<Double, Double>() {
            public Double apply(Double p_apply_1_) {
               return Double.valueOf(-(p_apply_1_.doubleValue() - (double)EntityLiving.this.applyMagicModifier(p_d_1_, p_apply_1_.floatValue())));
            }
         };
         float f5 = ((Double)function4.apply(Double.valueOf((double)p_d_2_))).floatValue();
         p_d_2_ = p_d_2_ + f5;
         Function<Double, Double> function5 = new Function<Double, Double>() {
            public Double apply(Double p_apply_1_) {
               return Double.valueOf(-Math.max(p_apply_1_.doubleValue() - Math.max(p_apply_1_.doubleValue() - (double)EntityLiving.this.getAbsorptionHearts(), 0.0D), 0.0D));
            }
         };
         float f6 = ((Double)function5.apply(Double.valueOf((double)p_d_2_))).floatValue();
         EntityDamageEvent entitydamageevent = CraftEventFactory.handleLivingEntityDamageEvent(this, p_d_1_, (double)f, (double)f1, (double)f2, (double)f3, (double)f4, (double)f5, (double)f6, function, function1, function2, function3, function4, function5);
         if(entitydamageevent.isCancelled()) {
            return false;
         } else {
            p_d_2_ = (float)entitydamageevent.getFinalDamage();
            if((p_d_1_ == DamageSource.ANVIL || p_d_1_ == DamageSource.FALLING_BLOCK) && this.getEquipment(4) != null) {
               this.getEquipment(4).damage((int)(entitydamageevent.getDamage() * 4.0D + (double)this.random.nextFloat() * entitydamageevent.getDamage() * 2.0D), this);
            }

            if(!p_d_1_.ignoresArmor()) {
               float f7 = (float)(entitydamageevent.getDamage() + entitydamageevent.getDamage(DamageModifier.BLOCKING) + entitydamageevent.getDamage(DamageModifier.HARD_HAT));
               this.damageArmor(f7);
            }

            f6 = (float)(-entitydamageevent.getDamage(DamageModifier.ABSORPTION));
            this.setAbsorptionHearts(Math.max(this.getAbsorptionHearts() - f6, 0.0F));
            if(p_d_2_ != 0.0F) {
               if(flag) {
                  ((EntityHuman)this).applyExhaustion(p_d_1_.getExhaustionCost());
                  if(p_d_2_ < 3.4028235E37F) {
                     ((EntityHuman)this).a(StatisticList.x, Math.round(p_d_2_ * 10.0F));
                  }
               }

               float f8 = this.getHealth();
               this.setHealth(f8 - p_d_2_);
               this.bs().a(p_d_1_, f8, p_d_2_);
               if(flag) {
                  return true;
               }

               this.setAbsorptionHearts(this.getAbsorptionHearts() - p_d_2_);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public CombatTracker bs() {
      return this.combatTracker;
   }

   public EntityLiving bt() {
      return (EntityLiving)(this.combatTracker.c() != null?this.combatTracker.c():(this.killer != null?this.killer:(this.lastDamager != null?this.lastDamager:null)));
   }

   public final float getMaxHealth() {
      return (float)this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
   }

   public final int bv() {
      return this.datawatcher.getByte(9);
   }

   public final void o(int p_o_1_) {
      this.datawatcher.watch(9, Byte.valueOf((byte)p_o_1_));
   }

   private int n() {
      return this.hasEffect(MobEffectList.FASTER_DIG)?6 - (1 + this.getEffect(MobEffectList.FASTER_DIG).getAmplifier()) * 1:(this.hasEffect(MobEffectList.SLOWER_DIG)?6 + (1 + this.getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) * 2:6);
   }

   public void bw() {
      if(!this.ar || this.as >= this.n() / 2 || this.as < 0) {
         this.as = -1;
         this.ar = true;
         if(this.world instanceof WorldServer) {
            ((WorldServer)this.world).getTracker().a((Entity)this, (Packet)(new PacketPlayOutAnimation(this, 0)));
         }
      }

   }

   protected void O() {
      this.damageEntity(DamageSource.OUT_OF_WORLD, 4.0F);
   }

   protected void bx() {
      int i = this.n();
      if(this.ar) {
         ++this.as;
         if(this.as >= i) {
            this.as = 0;
            this.ar = false;
         }
      } else {
         this.as = 0;
      }

      this.az = (float)this.as / (float)i;
   }

   public AttributeInstance getAttributeInstance(IAttribute p_getAttributeInstance_1_) {
      return this.getAttributeMap().a(p_getAttributeInstance_1_);
   }

   public AttributeMapBase getAttributeMap() {
      if(this.c == null) {
         this.c = new AttributeMapServer();
      }

      return this.c;
   }

   public EnumMonsterType getMonsterType() {
      return EnumMonsterType.UNDEFINED;
   }

   public abstract ItemStack bA();

   public abstract ItemStack getEquipment(int var1);

   public abstract void setEquipment(int var1, ItemStack var2);

   public void setSprinting(boolean p_setSprinting_1_) {
      super.setSprinting(p_setSprinting_1_);
      AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
      if(attributeinstance.a(a) != null) {
         attributeinstance.c(b);
      }

      if(p_setSprinting_1_) {
         attributeinstance.b(b);
      }

   }

   public abstract ItemStack[] getEquipment();

   protected float bB() {
      return 1.0F;
   }

   protected float bC() {
      return this.isBaby()?(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F:(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean bD() {
      return this.getHealth() <= 0.0F;
   }

   public void q(Entity p_q_1_) {
      double d0 = p_q_1_.locX;
      double d1 = p_q_1_.getBoundingBox().b + (double)p_q_1_.length;
      double d2 = p_q_1_.locZ;
      byte b0 = 1;

      for(int i = -b0; i <= b0; ++i) {
         for(int j = -b0; j < b0; ++j) {
            if(i != 0 || j != 0) {
               int k = (int)(this.locX + (double)i);
               int l = (int)(this.locZ + (double)j);
               AxisAlignedBB axisalignedbb = this.getBoundingBox().c((double)i, 1.0D, (double)j);
               if(this.world.a(axisalignedbb).isEmpty()) {
                  if(World.a((IBlockAccess)this.world, (BlockPosition)(new BlockPosition(k, (int)this.locY, l)))) {
                     this.enderTeleportTo(this.locX + (double)i, this.locY + 1.0D, this.locZ + (double)j);
                     return;
                  }

                  if(World.a((IBlockAccess)this.world, (BlockPosition)(new BlockPosition(k, (int)this.locY - 1, l))) || this.world.getType(new BlockPosition(k, (int)this.locY - 1, l)).getBlock().getMaterial() == Material.WATER) {
                     d0 = this.locX + (double)i;
                     d1 = this.locY + 1.0D;
                     d2 = this.locZ + (double)j;
                  }
               }
            }
         }
      }

      this.enderTeleportTo(d0, d1, d2);
   }

   protected float bE() {
      return 0.42F;
   }

   protected void bF() {
      this.motY = (double)this.bE();
      if(this.hasEffect(MobEffectList.JUMP)) {
         this.motY += (double)((float)(this.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F);
      }

      if(this.isSprinting()) {
         float f = this.yaw * 0.017453292F;
         this.motX -= (double)(MathHelper.sin(f) * 0.2F);
         this.motZ += (double)(MathHelper.cos(f) * 0.2F);
      }

      this.ai = true;
   }

   protected void bG() {
      this.motY += 0.03999999910593033D;
   }

   protected void bH() {
      this.motY += 0.03999999910593033D;
   }

   public void g(float p_g_1_, float p_g_2_) {
      if(this.bM()) {
         if(!this.V() || this instanceof EntityHuman && ((EntityHuman)this).abilities.isFlying) {
            if(!this.ab() || this instanceof EntityHuman && ((EntityHuman)this).abilities.isFlying) {
               float f3 = 0.91F;
               if(this.onGround) {
                  f3 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
               }

               float f4 = 0.16277136F / (f3 * f3 * f3);
               float f5;
               if(this.onGround) {
                  f5 = this.bI() * f4;
               } else {
                  f5 = this.aM;
               }

               this.a(p_g_1_, p_g_2_, f5);
               f3 = 0.91F;
               if(this.onGround) {
                  f3 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
               }

               if(this.k_()) {
                  float f6 = 0.15F;
                  this.motX = MathHelper.a(this.motX, (double)(-f6), (double)f6);
                  this.motZ = MathHelper.a(this.motZ, (double)(-f6), (double)f6);
                  this.fallDistance = 0.0F;
                  if(this.motY < -0.15D) {
                     this.motY = -0.15D;
                  }

                  boolean flag = this.isSneaking() && this instanceof EntityHuman;
                  if(flag && this.motY < 0.0D) {
                     this.motY = 0.0D;
                  }
               }

               this.move(this.motX, this.motY, this.motZ);
               if(this.positionChanged && this.k_()) {
                  this.motY = 0.2D;
               }

               if(this.world.isClientSide && (!this.world.isLoaded(new BlockPosition((int)this.locX, 0, (int)this.locZ)) || !this.world.getChunkAtWorldCoords(new BlockPosition((int)this.locX, 0, (int)this.locZ)).o())) {
                  if(this.locY > 0.0D) {
                     this.motY = -0.1D;
                  } else {
                     this.motY = 0.0D;
                  }
               } else {
                  this.motY -= 0.08D;
               }

               this.motY *= 0.9800000190734863D;
               this.motX *= (double)f3;
               this.motZ *= (double)f3;
            } else {
               double d2 = this.locY;
               this.a(p_g_1_, p_g_2_, 0.02F);
               this.move(this.motX, this.motY, this.motZ);
               this.motX *= 0.5D;
               this.motY *= 0.5D;
               this.motZ *= 0.5D;
               this.motY -= 0.02D;
               if(this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + d2, this.motZ)) {
                  this.motY = 0.30000001192092896D;
               }
            }
         } else {
            double d0 = this.locY;
            float f = 0.8F;
            float f1 = 0.02F;
            float f2 = (float)EnchantmentManager.b(this);
            if(f2 > 3.0F) {
               f2 = 3.0F;
            }

            if(!this.onGround) {
               f2 *= 0.5F;
            }

            if(f2 > 0.0F) {
               f += (0.54600006F - f) * f2 / 3.0F;
               f1 += (this.bI() * 1.0F - f1) * f2 / 3.0F;
            }

            this.a(p_g_1_, p_g_2_, f1);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= (double)f;
            this.motY *= 0.800000011920929D;
            this.motZ *= (double)f;
            this.motY -= 0.02D;
            if(this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ)) {
               this.motY = 0.30000001192092896D;
            }
         }
      }

      this.aA = this.aB;
      double d3 = this.locX - this.lastX;
      double d1 = this.locZ - this.lastZ;
      float f7 = MathHelper.sqrt(d3 * d3 + d1 * d1) * 4.0F;
      if(f7 > 1.0F) {
         f7 = 1.0F;
      }

      this.aB += (f7 - this.aB) * 0.4F;
      this.aC += this.aB;
   }

   public float bI() {
      return this.bm;
   }

   public void k(float p_k_1_) {
      this.bm = p_k_1_;
   }

   public boolean r(Entity p_r_1_) {
      this.p(p_r_1_);
      return false;
   }

   public boolean isSleeping() {
      return false;
   }

   public void t_() {
      SpigotTimings.timerEntityBaseTick.startTiming();
      super.t_();
      if(!this.world.isClientSide) {
         int i = this.bv();
         if(i > 0) {
            if(this.at <= 0) {
               this.at = 20 * (30 - i);
            }

            --this.at;
            if(this.at <= 0) {
               this.o(i - 1);
            }
         }

         for(int j = 0; j < 5; ++j) {
            ItemStack itemstack = this.h[j];
            ItemStack itemstack1 = this.getEquipment(j);
            if(!ItemStack.matches(itemstack1, itemstack)) {
               ((WorldServer)this.world).getTracker().a((Entity)this, (Packet)(new PacketPlayOutEntityEquipment(this.getId(), j, itemstack1)));
               if(itemstack != null) {
                  this.c.a(itemstack.B());
               }

               if(itemstack1 != null) {
                  this.c.b(itemstack1.B());
               }

               this.h[j] = itemstack1 == null?null:itemstack1.cloneItemStack();
            }
         }

         if(this.ticksLived % 20 == 0) {
            this.bs().g();
         }
      }

      SpigotTimings.timerEntityBaseTick.stopTiming();
      this.m();
      SpigotTimings.timerEntityTickRest.startTiming();
      double d0 = this.locX - this.lastX;
      double d1 = this.locZ - this.lastZ;
      float f = (float)(d0 * d0 + d1 * d1);
      float f1 = this.aI;
      float f2 = 0.0F;
      this.aR = this.aS;
      float f3 = 0.0F;
      if(f > 0.0025000002F) {
         f3 = 1.0F;
         f2 = (float)Math.sqrt((double)f) * 3.0F;
         f1 = (float)TrigMath.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
      }

      if(this.az > 0.0F) {
         f1 = this.yaw;
      }

      if(!this.onGround) {
         f3 = 0.0F;
      }

      this.aS += (f3 - this.aS) * 0.3F;
      this.world.methodProfiler.a("headTurn");
      f2 = this.h(f1, f2);
      this.world.methodProfiler.b();
      this.world.methodProfiler.a("rangeChecks");

      while(this.yaw - this.lastYaw < -180.0F) {
         this.lastYaw -= 360.0F;
      }

      while(this.yaw - this.lastYaw >= 180.0F) {
         this.lastYaw += 360.0F;
      }

      while(this.aI - this.aJ < -180.0F) {
         this.aJ -= 360.0F;
      }

      while(this.aI - this.aJ >= 180.0F) {
         this.aJ += 360.0F;
      }

      while(this.pitch - this.lastPitch < -180.0F) {
         this.lastPitch -= 360.0F;
      }

      while(this.pitch - this.lastPitch >= 180.0F) {
         this.lastPitch += 360.0F;
      }

      while(this.aK - this.aL < -180.0F) {
         this.aL -= 360.0F;
      }

      while(this.aK - this.aL >= 180.0F) {
         this.aL += 360.0F;
      }

      this.world.methodProfiler.b();
      this.aT += f2;
      SpigotTimings.timerEntityTickRest.stopTiming();
   }

   protected float h(float p_h_1_, float p_h_2_) {
      float f = MathHelper.g(p_h_1_ - this.aI);
      this.aI += f * 0.3F;
      float f1 = MathHelper.g(this.yaw - this.aI);
      boolean flag = f1 < -90.0F || f1 >= 90.0F;
      if(f1 < -75.0F) {
         f1 = -75.0F;
      }

      if(f1 >= 75.0F) {
         f1 = 75.0F;
      }

      this.aI = this.yaw - f1;
      if(f1 * f1 > 2500.0F) {
         this.aI += f1 * 0.2F;
      }

      if(flag) {
         p_h_2_ *= -1.0F;
      }

      return p_h_2_;
   }

   public void m() {
      if(this.bn > 0) {
         --this.bn;
      }

      if(this.bc > 0) {
         double d0 = this.locX + (this.bd - this.locX) / (double)this.bc;
         double d1 = this.locY + (this.be - this.locY) / (double)this.bc;
         double d2 = this.locZ + (this.bf - this.locZ) / (double)this.bc;
         double d3 = MathHelper.g(this.bg - (double)this.yaw);
         this.yaw = (float)((double)this.yaw + d3 / (double)this.bc);
         this.pitch = (float)((double)this.pitch + (this.bh - (double)this.pitch) / (double)this.bc);
         --this.bc;
         this.setPosition(d0, d1, d2);
         this.setYawPitch(this.yaw, this.pitch);
      } else if(!this.bM()) {
         this.motX *= 0.98D;
         this.motY *= 0.98D;
         this.motZ *= 0.98D;
      }

      if(Math.abs(this.motX) < 0.005D) {
         this.motX = 0.0D;
      }

      if(Math.abs(this.motY) < 0.005D) {
         this.motY = 0.0D;
      }

      if(Math.abs(this.motZ) < 0.005D) {
         this.motZ = 0.0D;
      }

      this.world.methodProfiler.a("ai");
      SpigotTimings.timerEntityAI.startTiming();
      if(this.bD()) {
         this.aY = false;
         this.aZ = 0.0F;
         this.ba = 0.0F;
         this.bb = 0.0F;
      } else if(this.bM()) {
         this.world.methodProfiler.a("newAi");
         this.doTick();
         this.world.methodProfiler.b();
      }

      SpigotTimings.timerEntityAI.stopTiming();
      this.world.methodProfiler.b();
      this.world.methodProfiler.a("jump");
      if(this.aY) {
         if(this.V()) {
            this.bG();
         } else if(this.ab()) {
            this.bH();
         } else if(this.onGround && this.bn == 0) {
            this.bF();
            this.bn = 10;
         }
      } else {
         this.bn = 0;
      }

      this.world.methodProfiler.b();
      this.world.methodProfiler.a("travel");
      this.aZ *= 0.98F;
      this.ba *= 0.98F;
      this.bb *= 0.9F;
      SpigotTimings.timerEntityAIMove.startTiming();
      this.g(this.aZ, this.ba);
      SpigotTimings.timerEntityAIMove.stopTiming();
      this.world.methodProfiler.b();
      this.world.methodProfiler.a("push");
      if(!this.world.isClientSide) {
         SpigotTimings.timerEntityAICollision.startTiming();
         this.bL();
         SpigotTimings.timerEntityAICollision.stopTiming();
      }

      this.world.methodProfiler.b();
   }

   protected void doTick() {
   }

   protected void bL() {
      List list = this.world.a((Entity)this, (AxisAlignedBB)this.getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D), (Predicate)Predicates.and(IEntitySelector.d, new Predicate() {
         public boolean a(Entity p_a_1_) {
            return p_a_1_.ae();
         }

         public boolean apply(Object p_apply_1_) {
            return this.a((Entity)p_apply_1_);
         }
      }));
      if(this.ad() && !list.isEmpty()) {
         this.numCollisions -= this.world.spigotConfig.maxCollisionsPerEntity;

         for(int i = 0; i < list.size() && this.numCollisions <= this.world.spigotConfig.maxCollisionsPerEntity; ++i) {
            Entity entity = (Entity)list.get(i);
            if(!(entity instanceof EntityLiving) || this instanceof EntityPlayer || this.ticksLived % 2 != 0) {
               ++entity.numCollisions;
               ++this.numCollisions;
               this.s(entity);
            }
         }

         this.numCollisions = 0;
      }

   }

   protected void s(Entity p_s_1_) {
      p_s_1_.collide(this);
   }

   public void mount(Entity p_mount_1_) {
      if(this.vehicle != null && p_mount_1_ == null) {
         Entity entity = this.vehicle;
         if(this.bukkitEntity instanceof LivingEntity && this.vehicle.getBukkitEntity() instanceof Vehicle) {
            VehicleExitEvent vehicleexitevent = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
            this.getBukkitEntity().getServer().getPluginManager().callEvent(vehicleexitevent);
            if(vehicleexitevent.isCancelled() || this.vehicle != entity) {
               return;
            }
         }

         Bukkit.getPluginManager().callEvent(new EntityDismountEvent(this.getBukkitEntity(), this.vehicle.getBukkitEntity()));
         if(!this.world.isClientSide) {
            this.q(this.vehicle);
         }

         if(this.vehicle != null) {
            this.vehicle.passenger = null;
         }

         this.vehicle = null;
      } else {
         super.mount(p_mount_1_);
      }

   }

   public void ak() {
      super.ak();
      this.aR = this.aS;
      this.aS = 0.0F;
      this.fallDistance = 0.0F;
   }

   public void i(boolean p_i_1_) {
      this.aY = p_i_1_;
   }

   public void receive(Entity p_receive_1_, int p_receive_2_) {
      if(!p_receive_1_.dead && !this.world.isClientSide) {
         EntityTracker entitytracker = ((WorldServer)this.world).getTracker();
         if(p_receive_1_ instanceof EntityItem) {
            entitytracker.a((Entity)p_receive_1_, (Packet)(new PacketPlayOutCollect(p_receive_1_.getId(), this.getId())));
         }

         if(p_receive_1_ instanceof EntityArrow) {
            entitytracker.a((Entity)p_receive_1_, (Packet)(new PacketPlayOutCollect(p_receive_1_.getId(), this.getId())));
         }

         if(p_receive_1_ instanceof EntityExperienceOrb) {
            entitytracker.a((Entity)p_receive_1_, (Packet)(new PacketPlayOutCollect(p_receive_1_.getId(), this.getId())));
         }
      }

   }

   public boolean hasLineOfSight(Entity p_hasLineOfSight_1_) {
      return this.world.rayTrace(new Vec3D(this.locX, this.locY + (double)this.getHeadHeight(), this.locZ), new Vec3D(p_hasLineOfSight_1_.locX, p_hasLineOfSight_1_.locY + (double)p_hasLineOfSight_1_.getHeadHeight(), p_hasLineOfSight_1_.locZ)) == null;
   }

   public Vec3D ap() {
      return this.d(1.0F);
   }

   public Vec3D d(float p_d_1_) {
      if(p_d_1_ == 1.0F) {
         return this.f(this.pitch, this.aK);
      } else {
         float f = this.lastPitch + (this.pitch - this.lastPitch) * p_d_1_;
         float f1 = this.aL + (this.aK - this.aL) * p_d_1_;
         return this.f(f, f1);
      }
   }

   public boolean bM() {
      return !this.world.isClientSide;
   }

   public boolean ad() {
      return !this.dead;
   }

   public boolean ae() {
      return !this.dead;
   }

   protected void ac() {
      this.velocityChanged = this.random.nextDouble() >= this.getAttributeInstance(GenericAttributes.c).getValue();
   }

   public float getHeadRotation() {
      return this.aK;
   }

   public void f(float p_f_1_) {
      this.aK = p_f_1_;
   }

   public void g(float p_g_1_) {
      this.aI = p_g_1_;
   }

   public float getAbsorptionHearts() {
      return this.bo;
   }

   public void setAbsorptionHearts(float p_setAbsorptionHearts_1_) {
      if(p_setAbsorptionHearts_1_ < 0.0F) {
         p_setAbsorptionHearts_1_ = 0.0F;
      }

      this.bo = p_setAbsorptionHearts_1_;
   }

   public ScoreboardTeamBase getScoreboardTeam() {
      return this.world.getScoreboard().getPlayerTeam(this.getUniqueID().toString());
   }

   public boolean c(EntityLiving p_c_1_) {
      return this.a(p_c_1_.getScoreboardTeam());
   }

   public boolean a(ScoreboardTeamBase p_a_1_) {
      return this.getScoreboardTeam() != null?this.getScoreboardTeam().isAlly(p_a_1_):false;
   }

   public void enterCombat() {
   }

   public void exitCombat() {
   }

   protected void bP() {
      this.updateEffects = true;
   }
}

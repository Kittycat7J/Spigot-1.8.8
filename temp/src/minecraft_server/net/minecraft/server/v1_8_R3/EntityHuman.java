package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.AchievementList;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockBed;
import net.minecraft.server.v1_8_R3.BlockDirectional;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatClickable;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChestLock;
import net.minecraft.server.v1_8_R3.ChunkProviderServer;
import net.minecraft.server.v1_8_R3.CommandBlockListenerAbstract;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerPlayer;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityBoat;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityFishingHook;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.FoodMetaData;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IComplex;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.IMerchant;
import net.minecraft.server.v1_8_R3.IMonster;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.ITileEntityContainer;
import net.minecraft.server.v1_8_R3.InventoryEnderChest;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PlayerAbilities;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import net.minecraft.server.v1_8_R3.Statistic;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntitySign;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.TrigMath;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public abstract class EntityHuman extends EntityLiving {
   public PlayerInventory inventory = new PlayerInventory(this);
   private InventoryEnderChest enderChest = new InventoryEnderChest();
   public Container defaultContainer;
   public Container activeContainer;
   protected FoodMetaData foodData = new FoodMetaData(this);
   protected int bm;
   public float bn;
   public float bo;
   public int bp;
   public double bq;
   public double br;
   public double bs;
   public double bt;
   public double bu;
   public double bv;
   public boolean sleeping;
   public BlockPosition bx;
   public int sleepTicks;
   public float by;
   public float bz;
   private BlockPosition c;
   private boolean d;
   private BlockPosition e;
   public PlayerAbilities abilities = new PlayerAbilities();
   public int expLevel;
   public int expTotal;
   public float exp;
   private int f;
   private ItemStack g;
   private int h;
   protected float bE = 0.1F;
   protected float bF = 0.02F;
   private int i;
   private final GameProfile bH;
   private boolean bI = false;
   public EntityFishingHook hookedFish;
   public boolean fauxSleeping;
   public String spawnWorld = "";
   public int oldLevel = -1;

   public CraftHumanEntity getBukkitEntity() {
      return (CraftHumanEntity)super.getBukkitEntity();
   }

   public EntityHuman(World p_i261_1_, GameProfile p_i261_2_) {
      super(p_i261_1_);
      this.uniqueID = a(p_i261_2_);
      this.bH = p_i261_2_;
      this.defaultContainer = new ContainerPlayer(this.inventory, !p_i261_1_.isClientSide, this);
      this.activeContainer = this.defaultContainer;
      BlockPosition blockposition = p_i261_1_.getSpawn();
      this.setPositionRotation((double)blockposition.getX() + 0.5D, (double)(blockposition.getY() + 1), (double)blockposition.getZ() + 0.5D, 0.0F, 0.0F);
      this.aV = 180.0F;
      this.maxFireTicks = 20;
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE).setValue(1.0D);
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.10000000149011612D);
   }

   protected void h() {
      super.h();
      this.datawatcher.a(16, Byte.valueOf((byte)0));
      this.datawatcher.a(17, Float.valueOf(0.0F));
      this.datawatcher.a(18, Integer.valueOf(0));
      this.datawatcher.a(10, Byte.valueOf((byte)0));
   }

   public boolean bS() {
      return this.g != null;
   }

   public void bU() {
      if(this.g != null) {
         this.g.b(this.world, this, this.h);
      }

      this.bV();
   }

   public void bV() {
      this.g = null;
      this.h = 0;
      if(!this.world.isClientSide) {
         this.f(false);
      }

   }

   public boolean isBlocking() {
      return this.bS() && this.g.getItem().e(this.g) == EnumAnimation.BLOCK;
   }

   public void t_() {
      this.noclip = this.isSpectator();
      if(this.isSpectator()) {
         this.onGround = false;
      }

      if(this.g != null) {
         ItemStack itemstack = this.inventory.getItemInHand();
         if(itemstack == this.g) {
            if(this.h <= 25 && this.h % 4 == 0) {
               this.b((ItemStack)itemstack, 5);
            }

            if(--this.h == 0 && !this.world.isClientSide) {
               this.s();
            }
         } else {
            this.bV();
         }
      }

      if(this.bp > 0) {
         --this.bp;
      }

      if(this.isSleeping()) {
         ++this.sleepTicks;
         if(this.sleepTicks > 100) {
            this.sleepTicks = 100;
         }

         if(!this.world.isClientSide) {
            if(!this.p()) {
               this.a(true, true, false);
            } else if(this.world.w()) {
               this.a(false, true, true);
            }
         }
      } else if(this.sleepTicks > 0) {
         ++this.sleepTicks;
         if(this.sleepTicks >= 110) {
            this.sleepTicks = 0;
         }
      }

      super.t_();
      if(!this.world.isClientSide && this.activeContainer != null && !this.activeContainer.a(this)) {
         this.closeInventory();
         this.activeContainer = this.defaultContainer;
      }

      if(this.isBurning() && this.abilities.isInvulnerable) {
         this.extinguish();
      }

      this.bq = this.bt;
      this.br = this.bu;
      this.bs = this.bv;
      double d0 = this.locX - this.bt;
      double d1 = this.locY - this.bu;
      double d2 = this.locZ - this.bv;
      double d3 = 10.0D;
      if(d0 > d3) {
         this.bq = this.bt = this.locX;
      }

      if(d2 > d3) {
         this.bs = this.bv = this.locZ;
      }

      if(d1 > d3) {
         this.br = this.bu = this.locY;
      }

      if(d0 < -d3) {
         this.bq = this.bt = this.locX;
      }

      if(d2 < -d3) {
         this.bs = this.bv = this.locZ;
      }

      if(d1 < -d3) {
         this.br = this.bu = this.locY;
      }

      this.bt += d0 * 0.25D;
      this.bv += d2 * 0.25D;
      this.bu += d1 * 0.25D;
      if(this.vehicle == null) {
         this.e = null;
      }

      if(!this.world.isClientSide) {
         this.foodData.a(this);
         this.b(StatisticList.g);
         if(this.isAlive()) {
            this.b(StatisticList.h);
         }
      }

      double d4 = MathHelper.a(this.locX, -2.9999999E7D, 2.9999999E7D);
      double d5 = MathHelper.a(this.locZ, -2.9999999E7D, 2.9999999E7D);
      if(d4 != this.locX || d5 != this.locZ) {
         this.setPosition(d4, this.locY, d5);
      }

   }

   public int L() {
      return this.abilities.isInvulnerable?0:80;
   }

   protected String P() {
      return "game.player.swim";
   }

   protected String aa() {
      return "game.player.swim.splash";
   }

   public int aq() {
      return 10;
   }

   public void makeSound(String p_makeSound_1_, float p_makeSound_2_, float p_makeSound_3_) {
      this.world.a(this, p_makeSound_1_, p_makeSound_2_, p_makeSound_3_);
   }

   protected void b(ItemStack p_b_1_, int p_b_2_) {
      if(p_b_1_.m() == EnumAnimation.DRINK) {
         this.makeSound("random.drink", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
      }

      if(p_b_1_.m() == EnumAnimation.EAT) {
         for(int i = 0; i < p_b_2_; ++i) {
            Vec3D vec3d = new Vec3D(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3d = vec3d.a(-this.pitch * 3.1415927F / 180.0F);
            vec3d = vec3d.b(-this.yaw * 3.1415927F / 180.0F);
            double d0 = (double)(-this.random.nextFloat()) * 0.6D - 0.3D;
            Vec3D vec3d1 = new Vec3D(((double)this.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec3d1 = vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
            vec3d1 = vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
            vec3d1 = vec3d1.add(this.locX, this.locY + (double)this.getHeadHeight(), this.locZ);
            if(p_b_1_.usesData()) {
               this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[]{Item.getId(p_b_1_.getItem()), p_b_1_.getData()});
            } else {
               this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[]{Item.getId(p_b_1_.getItem())});
            }
         }

         this.makeSound("random.eat", 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
      }

   }

   protected void s() {
      if(this.g != null) {
         this.b((ItemStack)this.g, 16);
         int i = this.g.count;
         org.bukkit.inventory.ItemStack itemstack = CraftItemStack.asBukkitCopy(this.g);
         PlayerItemConsumeEvent playeritemconsumeevent = new PlayerItemConsumeEvent((Player)this.getBukkitEntity(), itemstack);
         this.world.getServer().getPluginManager().callEvent(playeritemconsumeevent);
         if(playeritemconsumeevent.isCancelled()) {
            if(this instanceof EntityPlayer) {
               ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex).index, this.g));
               ((EntityPlayer)this).getBukkitEntity().updateInventory();
               ((EntityPlayer)this).getBukkitEntity().updateScaledHealth();
            }

            return;
         }

         if(!itemstack.equals(playeritemconsumeevent.getItem())) {
            CraftItemStack.asNMSCopy(playeritemconsumeevent.getItem()).b(this.world, this);
            if(this instanceof EntityPlayer) {
               ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex).index, this.g));
            }

            return;
         }

         ItemStack itemstack1 = this.g.b(this.world, this);
         if(itemstack1 != this.g || itemstack1 != null && itemstack1.count != i) {
            this.inventory.items[this.inventory.itemInHandIndex] = itemstack1;
            if(itemstack1.count == 0) {
               this.inventory.items[this.inventory.itemInHandIndex] = null;
            }
         }

         this.bV();
      }

   }

   protected boolean bD() {
      return this.getHealth() <= 0.0F || this.isSleeping();
   }

   public void closeInventory() {
      this.activeContainer = this.defaultContainer;
   }

   public void ak() {
      if(!this.world.isClientSide && this.isSneaking()) {
         this.mount((Entity)null);
         this.setSneaking(false);
      } else {
         double d0 = this.locX;
         double d1 = this.locY;
         double d2 = this.locZ;
         float f = this.yaw;
         float f1 = this.pitch;
         super.ak();
         this.bn = this.bo;
         this.bo = 0.0F;
         this.l(this.locX - d0, this.locY - d1, this.locZ - d2);
         if(this.vehicle instanceof EntityPig) {
            this.pitch = f1;
            this.yaw = f;
            this.aI = ((EntityPig)this.vehicle).aI;
         }
      }

   }

   protected void doTick() {
      super.doTick();
      this.bx();
      this.aK = this.yaw;
   }

   public void m() {
      if(this.bm > 0) {
         --this.bm;
      }

      if(this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.world.getGameRules().getBoolean("naturalRegeneration")) {
         if(this.getHealth() < this.getMaxHealth() && this.ticksLived % 20 == 0) {
            this.heal(1.0F, RegainReason.REGEN);
         }

         if(this.foodData.c() && this.ticksLived % 10 == 0) {
            this.foodData.a(this.foodData.getFoodLevel() + 1);
         }
      }

      this.inventory.k();
      this.bn = this.bo;
      super.m();
      AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
      if(!this.world.isClientSide) {
         attributeinstance.setValue((double)this.abilities.b());
      }

      this.aM = this.bF;
      if(this.isSprinting()) {
         this.aM = (float)((double)this.aM + (double)this.bF * 0.3D);
      }

      this.k((float)attributeinstance.getValue());
      float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      float f1 = (float)(TrigMath.atan(-this.motY * 0.20000000298023224D) * 15.0D);
      if(f > 0.1F) {
         f = 0.1F;
      }

      if(!this.onGround || this.getHealth() <= 0.0F) {
         f = 0.0F;
      }

      if(this.onGround || this.getHealth() <= 0.0F) {
         f1 = 0.0F;
      }

      this.bo += (f - this.bo) * 0.4F;
      this.aF += (f1 - this.aF) * 0.8F;
      if(this.getHealth() > 0.0F && !this.isSpectator()) {
         AxisAlignedBB axisalignedbb = null;
         if(this.vehicle != null && !this.vehicle.dead) {
            axisalignedbb = this.getBoundingBox().a(this.vehicle.getBoundingBox()).grow(1.0D, 0.0D, 1.0D);
         } else {
            axisalignedbb = this.getBoundingBox().grow(1.0D, 0.5D, 1.0D);
         }

         List list = this.world.getEntities(this, axisalignedbb);
         if(this.ae()) {
            for(int i = 0; i < list.size(); ++i) {
               Entity entity = (Entity)list.get(i);
               if(!entity.dead) {
                  this.d(entity);
               }
            }
         }
      }

   }

   private void d(Entity p_d_1_) {
      p_d_1_.d(this);
   }

   public int getScore() {
      return this.datawatcher.getInt(18);
   }

   public void setScore(int p_setScore_1_) {
      this.datawatcher.watch(18, Integer.valueOf(p_setScore_1_));
   }

   public void addScore(int p_addScore_1_) {
      int i = this.getScore();
      this.datawatcher.watch(18, Integer.valueOf(i + p_addScore_1_));
   }

   public void die(DamageSource p_die_1_) {
      super.die(p_die_1_);
      this.setSize(0.2F, 0.2F);
      this.setPosition(this.locX, this.locY, this.locZ);
      this.motY = 0.10000000149011612D;
      if(this.getName().equals("Notch")) {
         this.a(new ItemStack(Items.APPLE, 1), true, false);
      }

      if(!this.world.getGameRules().getBoolean("keepInventory")) {
         this.inventory.n();
      }

      if(p_die_1_ != null) {
         this.motX = (double)(-MathHelper.cos((this.aw + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
         this.motZ = (double)(-MathHelper.sin((this.aw + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
      } else {
         this.motX = this.motZ = 0.0D;
      }

      this.b(StatisticList.y);
      this.a(StatisticList.h);
   }

   protected String bo() {
      return "game.player.hurt";
   }

   protected String bp() {
      return "game.player.die";
   }

   public void b(Entity p_b_1_, int p_b_2_) {
      this.addScore(p_b_2_);
      Collection<ScoreboardScore> collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.f, this.getName(), new ArrayList());
      if(p_b_1_ instanceof EntityHuman) {
         this.b(StatisticList.B);
         this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.e, this.getName(), collection);
         collection.addAll(this.e(p_b_1_));
      } else {
         this.b(StatisticList.z);
      }

      for(ScoreboardScore scoreboardscore : collection) {
         scoreboardscore.incrementScore();
      }

   }

   private Collection e(Entity p_e_1_) {
      ScoreboardTeam scoreboardteam = this.getScoreboard().getPlayerTeam(this.getName());
      if(scoreboardteam != null) {
         int i = scoreboardteam.l().b();
         if(i >= 0 && i < IScoreboardCriteria.i.length) {
            for(ScoreboardObjective scoreboardobjective : this.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.i[i])) {
               ScoreboardScore scoreboardscore = this.getScoreboard().getPlayerScoreForObjective(p_e_1_.getName(), scoreboardobjective);
               scoreboardscore.incrementScore();
            }
         }
      }

      ScoreboardTeam scoreboardteam1 = this.getScoreboard().getPlayerTeam(p_e_1_.getName());
      if(scoreboardteam1 != null) {
         int j = scoreboardteam1.l().b();
         if(j >= 0 && j < IScoreboardCriteria.h.length) {
            return this.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.h[j]);
         }
      }

      return Lists.newArrayList();
   }

   public EntityItem a(boolean p_a_1_) {
      return this.a(this.inventory.splitStack(this.inventory.itemInHandIndex, p_a_1_ && this.inventory.getItemInHand() != null?this.inventory.getItemInHand().count:1), false, true);
   }

   public EntityItem drop(ItemStack p_drop_1_, boolean p_drop_2_) {
      return this.a(p_drop_1_, false, false);
   }

   public EntityItem a(ItemStack p_a_1_, boolean p_a_2_, boolean p_a_3_) {
      if(p_a_1_ == null) {
         return null;
      } else if(p_a_1_.count == 0) {
         return null;
      } else {
         double d0 = this.locY - 0.30000001192092896D + (double)this.getHeadHeight();
         EntityItem entityitem = new EntityItem(this.world, this.locX, d0, this.locZ, p_a_1_);
         entityitem.a(40);
         if(p_a_3_) {
            entityitem.c(this.getName());
         }

         if(p_a_2_) {
            float f = this.random.nextFloat() * 0.5F;
            float f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
            entityitem.motX = (double)(-MathHelper.sin(f1) * f);
            entityitem.motZ = (double)(MathHelper.cos(f1) * f);
            entityitem.motY = 0.20000000298023224D;
         } else {
            float f2 = 0.3F;
            entityitem.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f2);
            entityitem.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f2);
            entityitem.motY = (double)(-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f2 + 0.1F);
            float f3 = this.random.nextFloat() * 3.1415927F * 2.0F;
            f2 = 0.02F * this.random.nextFloat();
            entityitem.motX += Math.cos((double)f3) * (double)f2;
            entityitem.motY += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
            entityitem.motZ += Math.sin((double)f3) * (double)f2;
         }

         Player player = (Player)this.getBukkitEntity();
         CraftItem craftitem = new CraftItem(this.world.getServer(), entityitem);
         PlayerDropItemEvent playerdropitemevent = new PlayerDropItemEvent(player, craftitem);
         this.world.getServer().getPluginManager().callEvent(playerdropitemevent);
         if(!playerdropitemevent.isCancelled()) {
            this.a(entityitem);
            if(p_a_3_) {
               this.b(StatisticList.v);
            }

            return entityitem;
         } else {
            org.bukkit.inventory.ItemStack itemstack = player.getInventory().getItemInHand();
            if(!p_a_3_ || itemstack != null && itemstack.getAmount() != 0) {
               if(p_a_3_ && itemstack.isSimilar(craftitem.getItemStack()) && craftitem.getItemStack().getAmount() == 1) {
                  itemstack.setAmount(itemstack.getAmount() + 1);
                  player.getInventory().setItemInHand(itemstack);
               } else {
                  player.getInventory().addItem(new org.bukkit.inventory.ItemStack[]{craftitem.getItemStack()});
               }
            } else {
               player.getInventory().setItemInHand(craftitem.getItemStack());
            }

            return null;
         }
      }
   }

   protected void a(EntityItem p_a_1_) {
      this.world.addEntity(p_a_1_);
   }

   public float a(Block p_a_1_) {
      float f = this.inventory.a(p_a_1_);
      if(f > 1.0F) {
         int i = EnchantmentManager.getDigSpeedEnchantmentLevel(this);
         ItemStack itemstack = this.inventory.getItemInHand();
         if(i > 0 && itemstack != null) {
            f += (float)(i * i + 1);
         }
      }

      if(this.hasEffect(MobEffectList.FASTER_DIG)) {
         f *= 1.0F + (float)(this.getEffect(MobEffectList.FASTER_DIG).getAmplifier() + 1) * 0.2F;
      }

      if(this.hasEffect(MobEffectList.SLOWER_DIG)) {
         float f1 = 1.0F;
         switch(this.getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) {
         case 0:
            f1 = 0.3F;
            break;
         case 1:
            f1 = 0.09F;
            break;
         case 2:
            f1 = 0.0027F;
            break;
         case 3:
         default:
            f1 = 8.1E-4F;
         }

         f *= f1;
      }

      if(this.a((Material)Material.WATER) && !EnchantmentManager.j(this)) {
         f /= 5.0F;
      }

      if(!this.onGround) {
         f /= 5.0F;
      }

      return f;
   }

   public boolean b(Block p_b_1_) {
      return this.inventory.b(p_b_1_);
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.uniqueID = a(this.bH);
      NBTTagList nbttaglist = p_a_1_.getList("Inventory", 10);
      this.inventory.b(nbttaglist);
      this.inventory.itemInHandIndex = p_a_1_.getInt("SelectedItemSlot");
      this.sleeping = p_a_1_.getBoolean("Sleeping");
      this.sleepTicks = p_a_1_.getShort("SleepTimer");
      this.exp = p_a_1_.getFloat("XpP");
      this.expLevel = p_a_1_.getInt("XpLevel");
      this.expTotal = p_a_1_.getInt("XpTotal");
      this.f = p_a_1_.getInt("XpSeed");
      if(this.f == 0) {
         this.f = this.random.nextInt();
      }

      this.setScore(p_a_1_.getInt("Score"));
      if(this.sleeping) {
         this.bx = new BlockPosition(this);
         this.a(true, true, false);
      }

      this.spawnWorld = p_a_1_.getString("SpawnWorld");
      if("".equals(this.spawnWorld)) {
         this.spawnWorld = ((org.bukkit.World)this.world.getServer().getWorlds().get(0)).getName();
      }

      if(p_a_1_.hasKeyOfType("SpawnX", 99) && p_a_1_.hasKeyOfType("SpawnY", 99) && p_a_1_.hasKeyOfType("SpawnZ", 99)) {
         this.c = new BlockPosition(p_a_1_.getInt("SpawnX"), p_a_1_.getInt("SpawnY"), p_a_1_.getInt("SpawnZ"));
         this.d = p_a_1_.getBoolean("SpawnForced");
      }

      this.foodData.a(p_a_1_);
      this.abilities.b(p_a_1_);
      if(p_a_1_.hasKeyOfType("EnderItems", 9)) {
         NBTTagList nbttaglist1 = p_a_1_.getList("EnderItems", 10);
         this.enderChest.a(nbttaglist1);
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.set("Inventory", this.inventory.a(new NBTTagList()));
      p_b_1_.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
      p_b_1_.setBoolean("Sleeping", this.sleeping);
      p_b_1_.setShort("SleepTimer", (short)this.sleepTicks);
      p_b_1_.setFloat("XpP", this.exp);
      p_b_1_.setInt("XpLevel", this.expLevel);
      p_b_1_.setInt("XpTotal", this.expTotal);
      p_b_1_.setInt("XpSeed", this.f);
      p_b_1_.setInt("Score", this.getScore());
      if(this.c != null) {
         p_b_1_.setInt("SpawnX", this.c.getX());
         p_b_1_.setInt("SpawnY", this.c.getY());
         p_b_1_.setInt("SpawnZ", this.c.getZ());
         p_b_1_.setBoolean("SpawnForced", this.d);
      }

      this.foodData.b(p_b_1_);
      this.abilities.a(p_b_1_);
      p_b_1_.set("EnderItems", this.enderChest.h());
      ItemStack itemstack = this.inventory.getItemInHand();
      if(itemstack != null && itemstack.getItem() != null) {
         p_b_1_.set("SelectedItem", itemstack.save(new NBTTagCompound()));
      }

      p_b_1_.setString("SpawnWorld", this.spawnWorld);
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      if(this.isInvulnerable(p_damageEntity_1_)) {
         return false;
      } else if(this.abilities.isInvulnerable && !p_damageEntity_1_.ignoresInvulnerability()) {
         return false;
      } else {
         this.ticksFarFromPlayer = 0;
         if(this.getHealth() <= 0.0F) {
            return false;
         } else {
            if(this.isSleeping() && !this.world.isClientSide) {
               this.a(true, true, false);
            }

            if(p_damageEntity_1_.r()) {
               if(this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
                  return false;
               }

               if(this.world.getDifficulty() == EnumDifficulty.EASY) {
                  p_damageEntity_2_ = p_damageEntity_2_ / 2.0F + 1.0F;
               }

               if(this.world.getDifficulty() == EnumDifficulty.HARD) {
                  p_damageEntity_2_ = p_damageEntity_2_ * 3.0F / 2.0F;
               }
            }

            Entity entity = p_damageEntity_1_.getEntity();
            if(entity instanceof EntityArrow && ((EntityArrow)entity).shooter != null) {
               entity = ((EntityArrow)entity).shooter;
            }

            return super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
         }
      }
   }

   public boolean a(EntityHuman p_a_1_) {
      Team team;
      if(p_a_1_ instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)p_a_1_;
         team = entityplayer.getBukkitEntity().getScoreboard().getPlayerTeam(entityplayer.getBukkitEntity());
         if(team == null || team.allowFriendlyFire()) {
            return true;
         }
      } else {
         OfflinePlayer offlineplayer = p_a_1_.world.getServer().getOfflinePlayer(p_a_1_.getName());
         team = p_a_1_.world.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(offlineplayer);
         if(team == null || team.allowFriendlyFire()) {
            return true;
         }
      }

      if(this instanceof EntityPlayer) {
         return !team.hasPlayer(((EntityPlayer)this).getBukkitEntity());
      } else {
         return !team.hasPlayer(this.world.getServer().getOfflinePlayer(this.getName()));
      }
   }

   protected void damageArmor(float p_damageArmor_1_) {
      this.inventory.a(p_damageArmor_1_);
   }

   public int br() {
      return this.inventory.m();
   }

   public float bY() {
      int i = 0;

      for(ItemStack itemstack : this.inventory.armor) {
         if(itemstack != null) {
            ++i;
         }
      }

      return (float)i / (float)this.inventory.armor.length;
   }

   protected boolean d(DamageSource p_d_1_, float p_d_2_) {
      return super.d(p_d_1_, p_d_2_);
   }

   public void openSign(TileEntitySign p_openSign_1_) {
   }

   public void a(CommandBlockListenerAbstract p_a_1_) {
   }

   public void openTrade(IMerchant p_openTrade_1_) {
   }

   public void openContainer(IInventory p_openContainer_1_) {
   }

   public void openHorseInventory(EntityHorse p_openHorseInventory_1_, IInventory p_openHorseInventory_2_) {
   }

   public void openTileEntity(ITileEntityContainer p_openTileEntity_1_) {
   }

   public void openBook(ItemStack p_openBook_1_) {
   }

   public boolean u(Entity p_u_1_) {
      if(this.isSpectator()) {
         if(p_u_1_ instanceof IInventory) {
            this.openContainer((IInventory)p_u_1_);
         }

         return false;
      } else {
         ItemStack itemstack = this.bZ();
         ItemStack itemstack1 = itemstack != null?itemstack.cloneItemStack():null;
         if(!p_u_1_.e(this)) {
            if(itemstack != null && p_u_1_ instanceof EntityLiving) {
               if(this.abilities.canInstantlyBuild) {
                  itemstack = itemstack1;
               }

               if(itemstack.a(this, (EntityLiving)p_u_1_)) {
                  if(itemstack.count == 0 && !this.abilities.canInstantlyBuild) {
                     this.ca();
                  }

                  return true;
               }
            }

            return false;
         } else {
            if(itemstack != null && itemstack == this.bZ()) {
               if(itemstack.count <= 0 && !this.abilities.canInstantlyBuild) {
                  this.ca();
               } else if(itemstack.count < itemstack1.count && this.abilities.canInstantlyBuild) {
                  itemstack.count = itemstack1.count;
               }
            }

            return true;
         }
      }
   }

   public ItemStack bZ() {
      return this.inventory.getItemInHand();
   }

   public void ca() {
      this.inventory.setItem(this.inventory.itemInHandIndex, (ItemStack)null);
   }

   public double am() {
      return -0.35D;
   }

   public void attack(Entity p_attack_1_) {
      if(p_attack_1_.aD() && !p_attack_1_.l(this)) {
         float f = (float)this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
         byte b0 = 0;
         float f1 = 0.0F;
         if(p_attack_1_ instanceof EntityLiving) {
            f1 = EnchantmentManager.a(this.bA(), ((EntityLiving)p_attack_1_).getMonsterType());
         } else {
            f1 = EnchantmentManager.a(this.bA(), EnumMonsterType.UNDEFINED);
         }

         int i = b0 + EnchantmentManager.a((EntityLiving)this);
         if(this.isSprinting()) {
            ++i;
         }

         if(f > 0.0F || f1 > 0.0F) {
            boolean flag = this.fallDistance > 0.0F && !this.onGround && !this.k_() && !this.V() && !this.hasEffect(MobEffectList.BLINDNESS) && this.vehicle == null && p_attack_1_ instanceof EntityLiving;
            if(flag && f > 0.0F) {
               f *= 1.5F;
            }

            f = f + f1;
            boolean flag1 = false;
            int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);
            if(p_attack_1_ instanceof EntityLiving && j > 0 && !p_attack_1_.isBurning()) {
               EntityCombustByEntityEvent entitycombustbyentityevent = new EntityCombustByEntityEvent(this.getBukkitEntity(), p_attack_1_.getBukkitEntity(), 1);
               Bukkit.getPluginManager().callEvent(entitycombustbyentityevent);
               if(!entitycombustbyentityevent.isCancelled()) {
                  flag1 = true;
                  p_attack_1_.setOnFire(entitycombustbyentityevent.getDuration());
               }
            }

            double d0 = p_attack_1_.motX;
            double d1 = p_attack_1_.motY;
            double d2 = p_attack_1_.motZ;
            boolean flag2 = p_attack_1_.damageEntity(DamageSource.playerAttack(this), f);
            if(flag2) {
               if(i > 0) {
                  p_attack_1_.g((double)(-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * (float)i * 0.5F));
                  this.motX *= 0.6D;
                  this.motZ *= 0.6D;
                  this.setSprinting(false);
               }

               if(p_attack_1_ instanceof EntityPlayer && p_attack_1_.velocityChanged) {
                  boolean flag3 = false;
                  Player player = (Player)p_attack_1_.getBukkitEntity();
                  Vector vector = new Vector(d0, d1, d2);
                  PlayerVelocityEvent playervelocityevent = new PlayerVelocityEvent(player, vector.clone());
                  this.world.getServer().getPluginManager().callEvent(playervelocityevent);
                  if(playervelocityevent.isCancelled()) {
                     flag3 = true;
                  } else if(!vector.equals(playervelocityevent.getVelocity())) {
                     player.setVelocity(playervelocityevent.getVelocity());
                  }

                  if(!flag3) {
                     ((EntityPlayer)p_attack_1_).playerConnection.sendPacket(new PacketPlayOutEntityVelocity(p_attack_1_));
                     p_attack_1_.velocityChanged = false;
                     p_attack_1_.motX = d0;
                     p_attack_1_.motY = d1;
                     p_attack_1_.motZ = d2;
                  }
               }

               if(flag) {
                  this.b(p_attack_1_);
               }

               if(f1 > 0.0F) {
                  this.c(p_attack_1_);
               }

               if(f >= 18.0F) {
                  this.b((Statistic)AchievementList.F);
               }

               this.p(p_attack_1_);
               if(p_attack_1_ instanceof EntityLiving) {
                  EnchantmentManager.a((EntityLiving)p_attack_1_, (Entity)this);
               }

               EnchantmentManager.b(this, p_attack_1_);
               ItemStack itemstack = this.bZ();
               Object object = p_attack_1_;
               if(p_attack_1_ instanceof EntityComplexPart) {
                  IComplex icomplex = ((EntityComplexPart)p_attack_1_).owner;
                  if(icomplex instanceof EntityLiving) {
                     object = (EntityLiving)icomplex;
                  }
               }

               if(itemstack != null && object instanceof EntityLiving) {
                  itemstack.a((EntityLiving)object, this);
                  if(itemstack.count == 0) {
                     this.ca();
                  }
               }

               if(p_attack_1_ instanceof EntityLiving) {
                  this.a(StatisticList.w, Math.round(f * 10.0F));
                  if(j > 0) {
                     EntityCombustByEntityEvent entitycombustbyentityevent1 = new EntityCombustByEntityEvent(this.getBukkitEntity(), p_attack_1_.getBukkitEntity(), j * 4);
                     Bukkit.getPluginManager().callEvent(entitycombustbyentityevent1);
                     if(!entitycombustbyentityevent1.isCancelled()) {
                        p_attack_1_.setOnFire(entitycombustbyentityevent1.getDuration());
                     }
                  }
               }

               this.applyExhaustion(this.world.spigotConfig.combatExhaustion);
            } else if(flag1) {
               p_attack_1_.extinguish();
            }
         }
      }

   }

   public void b(Entity p_b_1_) {
   }

   public void c(Entity p_c_1_) {
   }

   public void die() {
      super.die();
      this.defaultContainer.b(this);
      if(this.activeContainer != null) {
         this.activeContainer.b(this);
      }

   }

   public boolean inBlock() {
      return !this.sleeping && super.inBlock();
   }

   public GameProfile getProfile() {
      return this.bH;
   }

   public EntityHuman.EnumBedResult a(BlockPosition p_a_1_) {
      if(!this.world.isClientSide) {
         if(this.isSleeping() || !this.isAlive()) {
            return EntityHuman.EnumBedResult.OTHER_PROBLEM;
         }

         if(!this.world.worldProvider.d()) {
            return EntityHuman.EnumBedResult.NOT_POSSIBLE_HERE;
         }

         if(this.world.w()) {
            return EntityHuman.EnumBedResult.NOT_POSSIBLE_NOW;
         }

         if(Math.abs(this.locX - (double)p_a_1_.getX()) > 3.0D || Math.abs(this.locY - (double)p_a_1_.getY()) > 2.0D || Math.abs(this.locZ - (double)p_a_1_.getZ()) > 3.0D) {
            return EntityHuman.EnumBedResult.TOO_FAR_AWAY;
         }

         double d0 = 8.0D;
         double d1 = 5.0D;
         List list = this.world.a(EntityMonster.class, new AxisAlignedBB((double)p_a_1_.getX() - d0, (double)p_a_1_.getY() - d1, (double)p_a_1_.getZ() - d0, (double)p_a_1_.getX() + d0, (double)p_a_1_.getY() + d1, (double)p_a_1_.getZ() + d0));
         if(!list.isEmpty()) {
            return EntityHuman.EnumBedResult.NOT_SAFE;
         }
      }

      if(this.au()) {
         this.mount((Entity)null);
      }

      if(this.getBukkitEntity() instanceof Player) {
         Player player = (Player)this.getBukkitEntity();
         org.bukkit.block.Block block = this.world.getWorld().getBlockAt(p_a_1_.getX(), p_a_1_.getY(), p_a_1_.getZ());
         PlayerBedEnterEvent playerbedenterevent = new PlayerBedEnterEvent(player, block);
         this.world.getServer().getPluginManager().callEvent(playerbedenterevent);
         if(playerbedenterevent.isCancelled()) {
            return EntityHuman.EnumBedResult.OTHER_PROBLEM;
         }
      }

      this.setSize(0.2F, 0.2F);
      if(this.world.isLoaded(p_a_1_)) {
         EnumDirection enumdirection = (EnumDirection)this.world.getType(p_a_1_).get(BlockDirectional.FACING);
         float f = 0.5F;
         float f1 = 0.5F;
         switch(EntityHuman.SyntheticClass_1.a[enumdirection.ordinal()]) {
         case 1:
            f1 = 0.9F;
            break;
         case 2:
            f1 = 0.1F;
            break;
         case 3:
            f = 0.1F;
            break;
         case 4:
            f = 0.9F;
         }

         this.a(enumdirection);
         this.setPosition((double)((float)p_a_1_.getX() + f), (double)((float)p_a_1_.getY() + 0.6875F), (double)((float)p_a_1_.getZ() + f1));
      } else {
         this.setPosition((double)((float)p_a_1_.getX() + 0.5F), (double)((float)p_a_1_.getY() + 0.6875F), (double)((float)p_a_1_.getZ() + 0.5F));
      }

      this.sleeping = true;
      this.sleepTicks = 0;
      this.bx = p_a_1_;
      this.motX = this.motZ = this.motY = 0.0D;
      if(!this.world.isClientSide) {
         this.world.everyoneSleeping();
      }

      return EntityHuman.EnumBedResult.OK;
   }

   private void a(EnumDirection p_a_1_) {
      this.by = 0.0F;
      this.bz = 0.0F;
      switch(EntityHuman.SyntheticClass_1.a[p_a_1_.ordinal()]) {
      case 1:
         this.bz = -1.8F;
         break;
      case 2:
         this.bz = 1.8F;
         break;
      case 3:
         this.by = 1.8F;
         break;
      case 4:
         this.by = -1.8F;
      }

   }

   public void a(boolean p_a_1_, boolean p_a_2_, boolean p_a_3_) {
      this.setSize(0.6F, 1.8F);
      IBlockData iblockdata = this.world.getType(this.bx);
      if(this.bx != null && iblockdata.getBlock() == Blocks.BED) {
         this.world.setTypeAndData(this.bx, iblockdata.set(BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
         BlockPosition blockposition = BlockBed.a(this.world, this.bx, 0);
         if(blockposition == null) {
            blockposition = this.bx.up();
         }

         this.setPosition((double)((float)blockposition.getX() + 0.5F), (double)((float)blockposition.getY() + 0.1F), (double)((float)blockposition.getZ() + 0.5F));
      }

      this.sleeping = false;
      if(!this.world.isClientSide && p_a_2_) {
         this.world.everyoneSleeping();
      }

      if(this.getBukkitEntity() instanceof Player) {
         Player player = (Player)this.getBukkitEntity();
         BlockPosition blockposition1 = this.bx;
         org.bukkit.block.Block block;
         if(blockposition1 != null) {
            block = this.world.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
         } else {
            block = this.world.getWorld().getBlockAt(player.getLocation());
         }

         PlayerBedLeaveEvent playerbedleaveevent = new PlayerBedLeaveEvent(player, block);
         this.world.getServer().getPluginManager().callEvent(playerbedleaveevent);
      }

      this.sleepTicks = p_a_1_?0:100;
      if(p_a_3_) {
         this.setRespawnPosition(this.bx, false);
      }

   }

   private boolean p() {
      return this.world.getType(this.bx).getBlock() == Blocks.BED;
   }

   public static BlockPosition getBed(World p_getBed_0_, BlockPosition p_getBed_1_, boolean p_getBed_2_) {
      ((ChunkProviderServer)p_getBed_0_.chunkProvider).getChunkAt(p_getBed_1_.getX() >> 4, p_getBed_1_.getZ() >> 4);
      Block block = p_getBed_0_.getType(p_getBed_1_).getBlock();
      if(block != Blocks.BED) {
         if(!p_getBed_2_) {
            return null;
         } else {
            boolean flag = block.g();
            boolean flag1 = p_getBed_0_.getType(p_getBed_1_.up()).getBlock().g();
            return flag && flag1?p_getBed_1_:null;
         }
      } else {
         return BlockBed.a(p_getBed_0_, p_getBed_1_, 0);
      }
   }

   public boolean isSleeping() {
      return this.sleeping;
   }

   public boolean isDeeplySleeping() {
      return this.sleeping && this.sleepTicks >= 100;
   }

   public void b(IChatBaseComponent p_b_1_) {
   }

   public BlockPosition getBed() {
      return this.c;
   }

   public boolean isRespawnForced() {
      return this.d;
   }

   public void setRespawnPosition(BlockPosition p_setRespawnPosition_1_, boolean p_setRespawnPosition_2_) {
      if(p_setRespawnPosition_1_ != null) {
         this.c = p_setRespawnPosition_1_;
         this.d = p_setRespawnPosition_2_;
         this.spawnWorld = this.world.worldData.getName();
      } else {
         this.c = null;
         this.d = false;
         this.spawnWorld = "";
      }

   }

   public void b(Statistic p_b_1_) {
      this.a((Statistic)p_b_1_, 1);
   }

   public void a(Statistic p_a_1_, int p_a_2_) {
   }

   public void a(Statistic p_a_1_) {
   }

   public void bF() {
      super.bF();
      this.b(StatisticList.u);
      if(this.isSprinting()) {
         this.applyExhaustion(this.world.spigotConfig.sprintExhaustion);
      } else {
         this.applyExhaustion(this.world.spigotConfig.walkExhaustion);
      }

   }

   public void g(float p_g_1_, float p_g_2_) {
      double d0 = this.locX;
      double d1 = this.locY;
      double d2 = this.locZ;
      if(this.abilities.isFlying && this.vehicle == null) {
         double d3 = this.motY;
         float f = this.aM;
         this.aM = this.abilities.a() * (float)(this.isSprinting()?2:1);
         super.g(p_g_1_, p_g_2_);
         this.motY = d3 * 0.6D;
         this.aM = f;
      } else {
         super.g(p_g_1_, p_g_2_);
      }

      this.checkMovement(this.locX - d0, this.locY - d1, this.locZ - d2);
   }

   public float bI() {
      return (float)this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
   }

   public void checkMovement(double p_checkMovement_1_, double p_checkMovement_3_, double p_checkMovement_5_) {
      if(this.vehicle == null) {
         if(this.a((Material)Material.WATER)) {
            int i = Math.round(MathHelper.sqrt(p_checkMovement_1_ * p_checkMovement_1_ + p_checkMovement_3_ * p_checkMovement_3_ + p_checkMovement_5_ * p_checkMovement_5_) * 100.0F);
            if(i > 0) {
               this.a(StatisticList.p, i);
               this.applyExhaustion(0.015F * (float)i * 0.01F);
            }
         } else if(this.V()) {
            int j = Math.round(MathHelper.sqrt(p_checkMovement_1_ * p_checkMovement_1_ + p_checkMovement_5_ * p_checkMovement_5_) * 100.0F);
            if(j > 0) {
               this.a(StatisticList.l, j);
               this.applyExhaustion(0.015F * (float)j * 0.01F);
            }
         } else if(this.k_()) {
            if(p_checkMovement_3_ > 0.0D) {
               this.a(StatisticList.n, (int)Math.round(p_checkMovement_3_ * 100.0D));
            }
         } else if(this.onGround) {
            int k = Math.round(MathHelper.sqrt(p_checkMovement_1_ * p_checkMovement_1_ + p_checkMovement_5_ * p_checkMovement_5_) * 100.0F);
            if(k > 0) {
               this.a(StatisticList.i, k);
               if(this.isSprinting()) {
                  this.a(StatisticList.k, k);
                  this.applyExhaustion(0.099999994F * (float)k * 0.01F);
               } else {
                  if(this.isSneaking()) {
                     this.a(StatisticList.j, k);
                  }

                  this.applyExhaustion(0.01F * (float)k * 0.01F);
               }
            }
         } else {
            int l = Math.round(MathHelper.sqrt(p_checkMovement_1_ * p_checkMovement_1_ + p_checkMovement_5_ * p_checkMovement_5_) * 100.0F);
            if(l > 25) {
               this.a(StatisticList.o, l);
            }
         }
      }

   }

   private void l(double p_l_1_, double p_l_3_, double p_l_5_) {
      if(this.vehicle != null) {
         int i = Math.round(MathHelper.sqrt(p_l_1_ * p_l_1_ + p_l_3_ * p_l_3_ + p_l_5_ * p_l_5_) * 100.0F);
         if(i > 0) {
            if(this.vehicle instanceof EntityMinecartAbstract) {
               this.a(StatisticList.q, i);
               if(this.e == null) {
                  this.e = new BlockPosition(this);
               } else if(this.e.c((double)MathHelper.floor(this.locX), (double)MathHelper.floor(this.locY), (double)MathHelper.floor(this.locZ)) >= 1000000.0D) {
                  this.b((Statistic)AchievementList.q);
               }
            } else if(this.vehicle instanceof EntityBoat) {
               this.a(StatisticList.r, i);
            } else if(this.vehicle instanceof EntityPig) {
               this.a(StatisticList.s, i);
            } else if(this.vehicle instanceof EntityHorse) {
               this.a(StatisticList.t, i);
            }
         }
      }

   }

   public void e(float p_e_1_, float p_e_2_) {
      if(!this.abilities.canFly) {
         if(p_e_1_ >= 2.0F) {
            this.a(StatisticList.m, (int)Math.round((double)p_e_1_ * 100.0D));
         }

         super.e(p_e_1_, p_e_2_);
      }

   }

   protected void X() {
      if(!this.isSpectator()) {
         super.X();
      }

   }

   protected String n(int p_n_1_) {
      return p_n_1_ > 4?"game.player.hurt.fall.big":"game.player.hurt.fall.small";
   }

   public void a(EntityLiving p_a_1_) {
      if(p_a_1_ instanceof IMonster) {
         this.b((Statistic)AchievementList.s);
      }

      EntityTypes.MonsterEggInfo entitytypes$monsteregginfo = (EntityTypes.MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(EntityTypes.a(p_a_1_)));
      if(entitytypes$monsteregginfo != null) {
         this.b(entitytypes$monsteregginfo.killEntityStatistic);
      }

   }

   public void aA() {
      if(!this.abilities.isFlying) {
         super.aA();
      }

   }

   public ItemStack q(int p_q_1_) {
      return this.inventory.e(p_q_1_);
   }

   public void giveExp(int p_giveExp_1_) {
      this.addScore(p_giveExp_1_);
      int i = Integer.MAX_VALUE - this.expTotal;
      if(p_giveExp_1_ > i) {
         p_giveExp_1_ = i;
      }

      this.exp += (float)p_giveExp_1_ / (float)this.getExpToLevel();

      for(this.expTotal += p_giveExp_1_; this.exp >= 1.0F; this.exp /= (float)this.getExpToLevel()) {
         this.exp = (this.exp - 1.0F) * (float)this.getExpToLevel();
         this.levelDown(1);
      }

   }

   public int cj() {
      return this.f;
   }

   public void enchantDone(int p_enchantDone_1_) {
      this.expLevel -= p_enchantDone_1_;
      if(this.expLevel < 0) {
         this.expLevel = 0;
         this.exp = 0.0F;
         this.expTotal = 0;
      }

      this.f = this.random.nextInt();
   }

   public void levelDown(int p_levelDown_1_) {
      this.expLevel += p_levelDown_1_;
      if(this.expLevel < 0) {
         this.expLevel = 0;
         this.exp = 0.0F;
         this.expTotal = 0;
      }

      if(p_levelDown_1_ > 0 && this.expLevel % 5 == 0 && (float)this.i < (float)this.ticksLived - 100.0F) {
         float f = this.expLevel > 30?1.0F:(float)this.expLevel / 30.0F;
         this.world.makeSound(this, "random.levelup", f * 0.75F, 1.0F);
         this.i = this.ticksLived;
      }

   }

   public int getExpToLevel() {
      return this.expLevel >= 30?112 + (this.expLevel - 30) * 9:(this.expLevel >= 15?37 + (this.expLevel - 15) * 5:7 + this.expLevel * 2);
   }

   public void applyExhaustion(float p_applyExhaustion_1_) {
      if(!this.abilities.isInvulnerable && !this.world.isClientSide) {
         this.foodData.a(p_applyExhaustion_1_);
      }

   }

   public FoodMetaData getFoodData() {
      return this.foodData;
   }

   public boolean j(boolean p_j_1_) {
      return (p_j_1_ || this.foodData.c()) && !this.abilities.isInvulnerable;
   }

   public boolean cm() {
      return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
   }

   public void a(ItemStack p_a_1_, int p_a_2_) {
      if(p_a_1_ != this.g) {
         this.g = p_a_1_;
         this.h = p_a_2_;
         if(!this.world.isClientSide) {
            this.f(true);
         }
      }

   }

   public boolean cn() {
      return this.abilities.mayBuild;
   }

   public boolean a(BlockPosition p_a_1_, EnumDirection p_a_2_, ItemStack p_a_3_) {
      if(this.abilities.mayBuild) {
         return true;
      } else if(p_a_3_ == null) {
         return false;
      } else {
         BlockPosition blockposition = p_a_1_.shift(p_a_2_.opposite());
         Block block = this.world.getType(blockposition).getBlock();
         return p_a_3_.d(block) || p_a_3_.x();
      }
   }

   protected int getExpValue(EntityHuman p_getExpValue_1_) {
      if(this.world.getGameRules().getBoolean("keepInventory")) {
         return 0;
      } else {
         int i = this.expLevel * 7;
         return i > 100?100:i;
      }
   }

   protected boolean alwaysGivesExp() {
      return true;
   }

   public void copyTo(EntityHuman p_copyTo_1_, boolean p_copyTo_2_) {
      if(p_copyTo_2_) {
         this.inventory.b(p_copyTo_1_.inventory);
         this.setHealth(p_copyTo_1_.getHealth());
         this.foodData = p_copyTo_1_.foodData;
         this.expLevel = p_copyTo_1_.expLevel;
         this.expTotal = p_copyTo_1_.expTotal;
         this.exp = p_copyTo_1_.exp;
         this.setScore(p_copyTo_1_.getScore());
         this.an = p_copyTo_1_.an;
         this.ao = p_copyTo_1_.ao;
         this.ap = p_copyTo_1_.ap;
      } else if(this.world.getGameRules().getBoolean("keepInventory")) {
         this.inventory.b(p_copyTo_1_.inventory);
         this.expLevel = p_copyTo_1_.expLevel;
         this.expTotal = p_copyTo_1_.expTotal;
         this.exp = p_copyTo_1_.exp;
         this.setScore(p_copyTo_1_.getScore());
      }

      this.f = p_copyTo_1_.f;
      this.enderChest = p_copyTo_1_.enderChest;
      this.getDataWatcher().watch(10, Byte.valueOf(p_copyTo_1_.getDataWatcher().getByte(10)));
   }

   protected boolean s_() {
      return !this.abilities.isFlying;
   }

   public void updateAbilities() {
   }

   public void a(WorldSettings.EnumGamemode p_a_1_) {
   }

   public String getName() {
      return this.bH.getName();
   }

   public InventoryEnderChest getEnderChest() {
      return this.enderChest;
   }

   public ItemStack getEquipment(int p_getEquipment_1_) {
      return p_getEquipment_1_ == 0?this.inventory.getItemInHand():this.inventory.armor[p_getEquipment_1_ - 1];
   }

   public ItemStack bA() {
      return this.inventory.getItemInHand();
   }

   public void setEquipment(int p_setEquipment_1_, ItemStack p_setEquipment_2_) {
      this.inventory.armor[p_setEquipment_1_] = p_setEquipment_2_;
   }

   public abstract boolean isSpectator();

   public ItemStack[] getEquipment() {
      return this.inventory.armor;
   }

   public boolean aL() {
      return !this.abilities.isFlying;
   }

   public Scoreboard getScoreboard() {
      return this.world.getScoreboard();
   }

   public ScoreboardTeamBase getScoreboardTeam() {
      return this.getScoreboard().getPlayerTeam(this.getName());
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      ChatComponentText chatcomponenttext = new ChatComponentText(ScoreboardTeam.getPlayerDisplayName(this.getScoreboardTeam(), this.getName()));
      chatcomponenttext.getChatModifier().setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
      chatcomponenttext.getChatModifier().setChatHoverable(this.aQ());
      chatcomponenttext.getChatModifier().setInsertion(this.getName());
      return chatcomponenttext;
   }

   public float getHeadHeight() {
      float f = 1.62F;
      if(this.isSleeping()) {
         f = 0.2F;
      }

      if(this.isSneaking()) {
         f -= 0.08F;
      }

      return f;
   }

   public void setAbsorptionHearts(float p_setAbsorptionHearts_1_) {
      if(p_setAbsorptionHearts_1_ < 0.0F) {
         p_setAbsorptionHearts_1_ = 0.0F;
      }

      this.getDataWatcher().watch(17, Float.valueOf(p_setAbsorptionHearts_1_));
   }

   public float getAbsorptionHearts() {
      return this.getDataWatcher().getFloat(17);
   }

   public static UUID a(GameProfile p_a_0_) {
      UUID uuid = p_a_0_.getId();
      if(uuid == null) {
         uuid = b(p_a_0_.getName());
      }

      return uuid;
   }

   public static UUID b(String p_b_0_) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_b_0_).getBytes(Charsets.UTF_8));
   }

   public boolean a(ChestLock p_a_1_) {
      if(p_a_1_.a()) {
         return true;
      } else {
         ItemStack itemstack = this.bZ();
         return itemstack != null && itemstack.hasName()?itemstack.getName().equals(p_a_1_.b()):false;
      }
   }

   public boolean getSendCommandFeedback() {
      return MinecraftServer.getServer().worldServer[0].getGameRules().getBoolean("sendCommandFeedback");
   }

   public boolean d(int p_d_1_, ItemStack p_d_2_) {
      if(p_d_1_ >= 0 && p_d_1_ < this.inventory.items.length) {
         this.inventory.setItem(p_d_1_, p_d_2_);
         return true;
      } else {
         int i = p_d_1_ - 100;
         if(i >= 0 && i < this.inventory.armor.length) {
            int k = i + 1;
            if(p_d_2_ != null && p_d_2_.getItem() != null) {
               if(p_d_2_.getItem() instanceof ItemArmor) {
                  if(EntityInsentient.c(p_d_2_) != k) {
                     return false;
                  }
               } else if(k != 4 || p_d_2_.getItem() != Items.SKULL && !(p_d_2_.getItem() instanceof ItemBlock)) {
                  return false;
               }
            }

            this.inventory.setItem(i + this.inventory.items.length, p_d_2_);
            return true;
         } else {
            int j = p_d_1_ - 200;
            if(j >= 0 && j < this.enderChest.getSize()) {
               this.enderChest.setItem(j, p_d_2_);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public static enum EnumBedResult {
      OK,
      NOT_POSSIBLE_HERE,
      NOT_POSSIBLE_NOW,
      TOO_FAR_AWAY,
      OTHER_PROBLEM,
      NOT_SAFE;
   }

   public static enum EnumChatVisibility {
      FULL(0, "options.chat.visibility.full"),
      SYSTEM(1, "options.chat.visibility.system"),
      HIDDEN(2, "options.chat.visibility.hidden");

      private static final EntityHuman.EnumChatVisibility[] d = new EntityHuman.EnumChatVisibility[values().length];
      private final int e;
      private final String f;

      static {
         for(EntityHuman.EnumChatVisibility entityhuman$enumchatvisibility : values()) {
            d[entityhuman$enumchatvisibility.e] = entityhuman$enumchatvisibility;
         }

      }

      private EnumChatVisibility(int p_i176_3_, String p_i176_4_) {
         this.e = p_i176_3_;
         this.f = p_i176_4_;
      }

      public int a() {
         return this.e;
      }

      public static EntityHuman.EnumChatVisibility a(int p_a_0_) {
         return d[p_a_0_ % d.length];
      }
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[EnumDirection.values().length];

      static {
         try {
            a[EnumDirection.SOUTH.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EnumDirection.NORTH.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[EnumDirection.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EnumDirection.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}

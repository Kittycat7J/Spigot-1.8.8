package net.minecraft.server.v1_8_R3;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_8_R3.AchievementList;
import net.minecraft.server.v1_8_R3.AchievementSet;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockCobbleWall;
import net.minecraft.server.v1_8_R3.BlockFence;
import net.minecraft.server.v1_8_R3.BlockFenceGate;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.ChestLock;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.CombatTracker;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerChest;
import net.minecraft.server.v1_8_R3.ContainerHorse;
import net.minecraft.server.v1_8_R3.ContainerMerchant;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityDamageSource;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import net.minecraft.server.v1_8_R3.FoodMetaData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICrafting;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.IMerchant;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.ITileEntityContainer;
import net.minecraft.server.v1_8_R3.ITileInventory;
import net.minecraft.server.v1_8_R3.InventoryMerchant;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemWorldMapBase;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MerchantRecipeList;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayOutAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowData;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowItems;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import net.minecraft.server.v1_8_R3.ServerStatisticManager;
import net.minecraft.server.v1_8_R3.SlotResult;
import net.minecraft.server.v1_8_R3.Statistic;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntitySign;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class EntityPlayer extends EntityHuman implements ICrafting {
   private static final Logger bH = LogManager.getLogger();
   public String locale = "en_US";
   public PlayerConnection playerConnection;
   public final MinecraftServer server;
   public final PlayerInteractManager playerInteractManager;
   public double d;
   public double e;
   public final List<ChunkCoordIntPair> chunkCoordIntPairQueue = Lists.<ChunkCoordIntPair>newLinkedList();
   public final List<Integer> removeQueue = Lists.<Integer>newLinkedList();
   private final ServerStatisticManager bK;
   private float bL = Float.MIN_VALUE;
   private float bM = -1.0E8F;
   private int bN = -99999999;
   private boolean bO = true;
   public int lastSentExp = -99999999;
   public int invulnerableTicks = 60;
   private EntityHuman.EnumChatVisibility bR;
   private boolean bS = true;
   private long bT = System.currentTimeMillis();
   private Entity bU = null;
   private int containerCounter;
   public boolean g;
   public int ping;
   public boolean viewingCredits;
   public String displayName;
   public IChatBaseComponent listName;
   public Location compassTarget;
   public int newExp = 0;
   public int newLevel = 0;
   public int newTotalExp = 0;
   public boolean keepLevel = false;
   public double maxHealthCache;
   public boolean joining = true;
   public boolean collidesWithEntities = true;
   public long timeOffset = 0L;
   public boolean relativeTime = true;
   public WeatherType weather = null;
   private float pluginRainPosition;
   private float pluginRainPositionPrevious;

   public boolean ad() {
      return this.collidesWithEntities && super.ad();
   }

   public boolean ae() {
      return this.collidesWithEntities && super.ae();
   }

   public EntityPlayer(MinecraftServer p_i122_1_, WorldServer p_i122_2_, GameProfile p_i122_3_, PlayerInteractManager p_i122_4_) {
      super(p_i122_2_, p_i122_3_);
      p_i122_4_.player = this;
      this.playerInteractManager = p_i122_4_;
      BlockPosition blockposition = p_i122_2_.getSpawn();
      if(!p_i122_2_.worldProvider.o() && p_i122_2_.getWorldData().getGameType() != WorldSettings.EnumGamemode.ADVENTURE) {
         int i = Math.max(5, p_i122_1_.getSpawnProtection() - 6);
         int j = MathHelper.floor(p_i122_2_.getWorldBorder().b((double)blockposition.getX(), (double)blockposition.getZ()));
         if(j < i) {
            i = j;
         }

         if(j <= 1) {
            i = 1;
         }

         blockposition = p_i122_2_.r(blockposition.a(this.random.nextInt(i * 2) - i, 0, this.random.nextInt(i * 2) - i));
      }

      this.server = p_i122_1_;
      this.bK = p_i122_1_.getPlayerList().a((EntityHuman)this);
      this.S = 0.0F;
      this.setPositionRotation(blockposition, 0.0F, 0.0F);

      while(!p_i122_2_.getCubes(this, this.getBoundingBox()).isEmpty() && this.locY < 255.0D) {
         this.setPosition(this.locX, this.locY + 1.0D, this.locZ);
      }

      this.displayName = this.getName();
      this.maxHealthCache = (double)this.getMaxHealth();
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      if(p_a_1_.hasKeyOfType("playerGameType", 99)) {
         if(MinecraftServer.getServer().getForceGamemode()) {
            this.playerInteractManager.setGameMode(MinecraftServer.getServer().getGamemode());
         } else {
            this.playerInteractManager.setGameMode(WorldSettings.EnumGamemode.getById(p_a_1_.getInt("playerGameType")));
         }
      }

      this.getBukkitEntity().readExtraData(p_a_1_);
   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setInt("playerGameType", this.playerInteractManager.getGameMode().getId());
      this.getBukkitEntity().setExtraData(p_b_1_);
   }

   public void spawnIn(World p_spawnIn_1_) {
      super.spawnIn((World)p_spawnIn_1_);
      if(p_spawnIn_1_ == null) {
         this.dead = false;
         BlockPosition blockposition = null;
         if(this.spawnWorld != null && !this.spawnWorld.equals("")) {
            CraftWorld craftworld = (CraftWorld)Bukkit.getServer().getWorld(this.spawnWorld);
            if(craftworld != null && this.getBed() != null) {
               p_spawnIn_1_ = craftworld.getHandle();
               blockposition = EntityHuman.getBed(craftworld.getHandle(), this.getBed(), false);
            }
         }

         if(p_spawnIn_1_ == null || blockposition == null) {
            p_spawnIn_1_ = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
            blockposition = ((World)p_spawnIn_1_).getSpawn();
         }

         this.world = (World)p_spawnIn_1_;
         this.setPosition((double)blockposition.getX() + 0.5D, (double)blockposition.getY(), (double)blockposition.getZ() + 0.5D);
      }

      this.dimension = ((WorldServer)this.world).dimension;
      this.playerInteractManager.a((WorldServer)p_spawnIn_1_);
   }

   public void levelDown(int p_levelDown_1_) {
      super.levelDown(p_levelDown_1_);
      this.lastSentExp = -1;
   }

   public void enchantDone(int p_enchantDone_1_) {
      super.enchantDone(p_enchantDone_1_);
      this.lastSentExp = -1;
   }

   public void syncInventory() {
      this.activeContainer.addSlotListener(this);
   }

   public void enterCombat() {
      super.enterCombat();
      this.playerConnection.sendPacket(new PacketPlayOutCombatEvent(this.bs(), PacketPlayOutCombatEvent.EnumCombatEventType.ENTER_COMBAT));
   }

   public void exitCombat() {
      super.exitCombat();
      this.playerConnection.sendPacket(new PacketPlayOutCombatEvent(this.bs(), PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT));
   }

   public void t_() {
      if(this.joining) {
         this.joining = false;
      }

      this.playerInteractManager.a();
      --this.invulnerableTicks;
      if(this.noDamageTicks > 0) {
         --this.noDamageTicks;
      }

      this.activeContainer.b();
      if(!this.world.isClientSide && !this.activeContainer.a((EntityHuman)this)) {
         this.closeInventory();
         this.activeContainer = this.defaultContainer;
      }

      while(!this.removeQueue.isEmpty()) {
         int i = Math.min(this.removeQueue.size(), Integer.MAX_VALUE);
         int[] aint = new int[i];
         Iterator iterator = this.removeQueue.iterator();
         int j = 0;

         while(iterator.hasNext() && j < i) {
            aint[j++] = ((Integer)iterator.next()).intValue();
            iterator.remove();
         }

         this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(aint));
      }

      if(!this.chunkCoordIntPairQueue.isEmpty()) {
         ArrayList arraylist = Lists.newArrayList();
         Iterator iterator1 = this.chunkCoordIntPairQueue.iterator();
         ArrayList arraylist1 = Lists.newArrayList();

         while(iterator1.hasNext() && arraylist.size() < this.world.spigotConfig.maxBulkChunk) {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator1.next();
            if(chunkcoordintpair != null) {
               if(this.world.isLoaded(new BlockPosition(chunkcoordintpair.x << 4, 0, chunkcoordintpair.z << 4))) {
                  Chunk chunk = this.world.getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z);
                  if(chunk.isReady()) {
                     arraylist.add(chunk);
                     arraylist1.addAll(chunk.tileEntities.values());
                     iterator1.remove();
                  }
               }
            } else {
               iterator1.remove();
            }
         }

         if(!arraylist.isEmpty()) {
            if(arraylist.size() == 1) {
               this.playerConnection.sendPacket(new PacketPlayOutMapChunk((Chunk)arraylist.get(0), true, '\uffff'));
            } else {
               this.playerConnection.sendPacket(new PacketPlayOutMapChunkBulk(arraylist));
            }

            for(TileEntity tileentity : arraylist1) {
               this.a(tileentity);
            }

            for(Chunk chunk1 : arraylist) {
               this.u().getTracker().a(this, chunk1);
            }
         }
      }

      Entity entity = this.C();
      if(entity != this) {
         if(!entity.isAlive()) {
            this.setSpectatorTarget(this);
         } else {
            this.setLocation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
            this.server.getPlayerList().d(this);
            if(this.isSneaking()) {
               this.setSpectatorTarget(this);
            }
         }
      }

   }

   public void l() {
      try {
         super.t_();

         for(int i = 0; i < this.inventory.getSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if(itemstack != null && itemstack.getItem().f()) {
               Packet packet = ((ItemWorldMapBase)itemstack.getItem()).c(itemstack, this.world, this);
               if(packet != null) {
                  this.playerConnection.sendPacket(packet);
               }
            }
         }

         if(this.getHealth() != this.bM || this.bN != this.foodData.getFoodLevel() || this.foodData.getSaturationLevel() == 0.0F != this.bO) {
            this.playerConnection.sendPacket(new PacketPlayOutUpdateHealth(this.getBukkitEntity().getScaledHealth(), this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
            this.bM = this.getHealth();
            this.bN = this.foodData.getFoodLevel();
            this.bO = this.foodData.getSaturationLevel() == 0.0F;
         }

         if(this.getHealth() + this.getAbsorptionHearts() != this.bL) {
            this.bL = this.getHealth() + this.getAbsorptionHearts();

            for(ScoreboardObjective scoreboardobjective : this.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.g)) {
               this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective).updateForList(Arrays.asList(new EntityHuman[]{this}));
            }

            this.world.getServer().getScoreboardManager().updateAllScoresForList(IScoreboardCriteria.g, this.getName(), ImmutableList.<EntityPlayer>of(this));
         }

         if(this.maxHealthCache != (double)this.getMaxHealth()) {
            this.getBukkitEntity().updateScaledHealth();
         }

         if(this.expTotal != this.lastSentExp) {
            this.lastSentExp = this.expTotal;
            this.playerConnection.sendPacket(new PacketPlayOutExperience(this.exp, this.expTotal, this.expLevel));
         }

         if(this.ticksLived % 20 * 5 == 0 && !this.getStatisticManager().hasAchievement(AchievementList.L)) {
            this.i_();
         }

         if(this.oldLevel == -1) {
            this.oldLevel = this.expLevel;
         }

         if(this.oldLevel != this.expLevel) {
            CraftEventFactory.callPlayerLevelChangeEvent(this.world.getServer().getPlayer(this), this.oldLevel, this.expLevel);
            this.oldLevel = this.expLevel;
         }

      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.a(throwable, "Ticking player");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Player being ticked");
         this.appendEntityCrashDetails(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   protected void i_() {
      BiomeBase biomebase = this.world.getBiome(new BlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ)));
      String s = biomebase.ah;
      AchievementSet achievementset = (AchievementSet)this.getStatisticManager().b(AchievementList.L);
      if(achievementset == null) {
         achievementset = (AchievementSet)this.getStatisticManager().a(AchievementList.L, new AchievementSet());
      }

      achievementset.add(s);
      if(this.getStatisticManager().b(AchievementList.L) && achievementset.size() >= BiomeBase.n.size()) {
         HashSet hashset = Sets.newHashSet(BiomeBase.n);

         for(String s1 : achievementset) {
            Iterator iterator = hashset.iterator();

            while(iterator.hasNext()) {
               BiomeBase biomebase1 = (BiomeBase)iterator.next();
               if(biomebase1.ah.equals(s1)) {
                  iterator.remove();
               }
            }

            if(hashset.isEmpty()) {
               break;
            }
         }

         if(hashset.isEmpty()) {
            this.b((Statistic)AchievementList.L);
         }
      }

   }

   public void die(DamageSource p_die_1_) {
      if(!this.dead) {
         List<org.bukkit.inventory.ItemStack> list = new ArrayList();
         boolean flag = this.world.getGameRules().getBoolean("keepInventory");
         if(!flag) {
            for(int i = 0; i < this.inventory.items.length; ++i) {
               if(this.inventory.items[i] != null) {
                  list.add(CraftItemStack.asCraftMirror(this.inventory.items[i]));
               }
            }

            for(int k = 0; k < this.inventory.armor.length; ++k) {
               if(this.inventory.armor[k] != null) {
                  list.add(CraftItemStack.asCraftMirror(this.inventory.armor[k]));
               }
            }
         }

         IChatBaseComponent ichatbasecomponent = this.bs().b();
         String s = ichatbasecomponent.c();
         PlayerDeathEvent playerdeathevent = CraftEventFactory.callPlayerDeathEvent(this, list, s, flag);
         String s1 = playerdeathevent.getDeathMessage();
         if(s1 != null && s1.length() > 0 && this.world.getGameRules().getBoolean("showDeathMessages")) {
            if(s1.equals(s)) {
               this.server.getPlayerList().sendMessage(ichatbasecomponent);
            } else {
               this.server.getPlayerList().sendMessage(CraftChatMessage.fromString(s1));
            }
         }

         if(!playerdeathevent.getKeepInventory()) {
            for(int j = 0; j < this.inventory.items.length; ++j) {
               this.inventory.items[j] = null;
            }

            for(int l = 0; l < this.inventory.armor.length; ++l) {
               this.inventory.armor[l] = null;
            }
         }

         this.closeInventory();
         this.setSpectatorTarget(this);

         for(ScoreboardScore scoreboardscore : this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.d, this.getName(), new ArrayList())) {
            scoreboardscore.incrementScore();
         }

         EntityLiving entityliving = this.bt();
         if(entityliving != null) {
            EntityTypes.MonsterEggInfo entitytypes$monsteregginfo = (EntityTypes.MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(EntityTypes.a(entityliving)));
            if(entitytypes$monsteregginfo != null) {
               this.b((Statistic)entitytypes$monsteregginfo.e);
            }

            entityliving.b(this, this.aW);
         }

         this.b((Statistic)StatisticList.y);
         this.a(StatisticList.h);
         this.bs().g();
      }
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      if(this.isInvulnerable(p_damageEntity_1_)) {
         return false;
      } else {
         boolean flag = this.server.ae() && this.cr() && "fall".equals(p_damageEntity_1_.translationIndex);
         if(!flag && this.invulnerableTicks > 0 && p_damageEntity_1_ != DamageSource.OUT_OF_WORLD) {
            return false;
         } else {
            if(p_damageEntity_1_ instanceof EntityDamageSource) {
               Entity entity = p_damageEntity_1_.getEntity();
               if(entity instanceof EntityHuman && !this.a((EntityHuman)entity)) {
                  return false;
               }

               if(entity instanceof EntityArrow) {
                  EntityArrow entityarrow = (EntityArrow)entity;
                  if(entityarrow.shooter instanceof EntityHuman && !this.a((EntityHuman)entityarrow.shooter)) {
                     return false;
                  }
               }
            }

            return super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
         }
      }
   }

   public boolean a(EntityHuman p_a_1_) {
      return !this.cr()?false:super.a(p_a_1_);
   }

   private boolean cr() {
      return this.world.pvpMode;
   }

   public void c(int p_c_1_) {
      if(this.dimension == 1 && p_c_1_ == 1) {
         this.b((Statistic)AchievementList.D);
         this.world.kill(this);
         this.viewingCredits = true;
         this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(4, 0.0F));
      } else {
         if(this.dimension == 0 && p_c_1_ == 1) {
            this.b((Statistic)AchievementList.C);
         } else {
            this.b((Statistic)AchievementList.y);
         }

         TeleportCause teleportcause = this.dimension != 1 && p_c_1_ != 1?TeleportCause.NETHER_PORTAL:TeleportCause.END_PORTAL;
         this.server.getPlayerList().changeDimension(this, p_c_1_, teleportcause);
         this.lastSentExp = -1;
         this.bM = -1.0F;
         this.bN = -1;
      }

   }

   public boolean a(EntityPlayer p_a_1_) {
      return p_a_1_.isSpectator()?this.C() == this:(this.isSpectator()?false:super.a((EntityPlayer)p_a_1_));
   }

   private void a(TileEntity p_a_1_) {
      if(p_a_1_ != null) {
         Packet packet = p_a_1_.getUpdatePacket();
         if(packet != null) {
            this.playerConnection.sendPacket(packet);
         }
      }

   }

   public void receive(Entity p_receive_1_, int p_receive_2_) {
      super.receive(p_receive_1_, p_receive_2_);
      this.activeContainer.b();
   }

   public EntityHuman.EnumBedResult a(BlockPosition p_a_1_) {
      EntityHuman.EnumBedResult entityhuman$enumbedresult = super.a(p_a_1_);
      if(entityhuman$enumbedresult == EntityHuman.EnumBedResult.OK) {
         PacketPlayOutBed packetplayoutbed = new PacketPlayOutBed(this, p_a_1_);
         this.u().getTracker().a((Entity)this, (Packet)packetplayoutbed);
         this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
         this.playerConnection.sendPacket(packetplayoutbed);
      }

      return entityhuman$enumbedresult;
   }

   public void a(boolean p_a_1_, boolean p_a_2_, boolean p_a_3_) {
      if(this.sleeping) {
         if(this.isSleeping()) {
            this.u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(this, 2));
         }

         super.a(p_a_1_, p_a_2_, p_a_3_);
         if(this.playerConnection != null) {
            this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
         }

      }
   }

   public void mount(Entity p_mount_1_) {
      Entity entity = this.vehicle;
      super.mount(p_mount_1_);
      if(this.vehicle != entity) {
         this.playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, this, this.vehicle));
         this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      }

   }

   protected void a(double p_a_1_, boolean p_a_3_, Block p_a_4_, BlockPosition p_a_5_) {
   }

   public void a(double p_a_1_, boolean p_a_3_) {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.locY - 0.20000000298023224D);
      int k = MathHelper.floor(this.locZ);
      BlockPosition blockposition = new BlockPosition(i, j, k);
      Block block = this.world.getType(blockposition).getBlock();
      if(block.getMaterial() == Material.AIR) {
         Block block1 = this.world.getType(blockposition.down()).getBlock();
         if(block1 instanceof BlockFence || block1 instanceof BlockCobbleWall || block1 instanceof BlockFenceGate) {
            blockposition = blockposition.down();
            block = this.world.getType(blockposition).getBlock();
         }
      }

      super.a(p_a_1_, p_a_3_, block, blockposition);
   }

   public void openSign(TileEntitySign p_openSign_1_) {
      p_openSign_1_.a((EntityHuman)this);
      this.playerConnection.sendPacket(new PacketPlayOutOpenSignEditor(p_openSign_1_.getPosition()));
   }

   public int nextContainerCounter() {
      this.containerCounter = this.containerCounter % 100 + 1;
      return this.containerCounter;
   }

   public void openTileEntity(ITileEntityContainer p_openTileEntity_1_) {
      Container container = CraftEventFactory.callInventoryOpenEvent(this, p_openTileEntity_1_.createContainer(this.inventory, this));
      if(container != null) {
         this.nextContainerCounter();
         this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, p_openTileEntity_1_.getContainerName(), p_openTileEntity_1_.getScoreboardDisplayName()));
         this.activeContainer = container;
         this.activeContainer.windowId = this.containerCounter;
         this.activeContainer.addSlotListener(this);
      }
   }

   public void openContainer(IInventory p_openContainer_1_) {
      boolean flag = false;
      if(p_openContainer_1_ instanceof ITileInventory) {
         ITileInventory itileinventory = (ITileInventory)p_openContainer_1_;
         flag = itileinventory.r_() && !this.a((ChestLock)itileinventory.i()) && !this.isSpectator();
      }

      Container container;
      if(p_openContainer_1_ instanceof ITileEntityContainer) {
         container = ((ITileEntityContainer)p_openContainer_1_).createContainer(this.inventory, this);
      } else {
         container = new ContainerChest(this.inventory, p_openContainer_1_, this);
      }

      container = CraftEventFactory.callInventoryOpenEvent(this, container, flag);
      if(container == null && !flag) {
         p_openContainer_1_.closeContainer(this);
      } else {
         if(this.activeContainer != this.defaultContainer) {
            this.closeInventory();
         }

         if(p_openContainer_1_ instanceof ITileInventory) {
            ITileInventory itileinventory1 = (ITileInventory)p_openContainer_1_;
            if(itileinventory1.r_() && !this.a((ChestLock)itileinventory1.i()) && !this.isSpectator() && container == null) {
               this.playerConnection.sendPacket(new PacketPlayOutChat(new ChatMessage("container.isLocked", new Object[]{p_openContainer_1_.getScoreboardDisplayName()}), (byte)2));
               this.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("random.door_close", this.locX, this.locY, this.locZ, 1.0F, 1.0F));
               p_openContainer_1_.closeContainer(this);
               return;
            }
         }

         this.nextContainerCounter();
         if(p_openContainer_1_ instanceof ITileEntityContainer) {
            this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, ((ITileEntityContainer)p_openContainer_1_).getContainerName(), p_openContainer_1_.getScoreboardDisplayName(), p_openContainer_1_.getSize()));
            this.activeContainer = container;
         } else {
            this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "minecraft:container", p_openContainer_1_.getScoreboardDisplayName(), p_openContainer_1_.getSize()));
            this.activeContainer = container;
         }

         this.activeContainer.windowId = this.containerCounter;
         this.activeContainer.addSlotListener(this);
      }
   }

   public void openTrade(IMerchant p_openTrade_1_) {
      Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerMerchant(this.inventory, p_openTrade_1_, this.world));
      if(container != null) {
         this.nextContainerCounter();
         this.activeContainer = container;
         this.activeContainer.windowId = this.containerCounter;
         this.activeContainer.addSlotListener(this);
         InventoryMerchant inventorymerchant = ((ContainerMerchant)this.activeContainer).e();
         IChatBaseComponent ichatbasecomponent = p_openTrade_1_.getScoreboardDisplayName();
         this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "minecraft:villager", ichatbasecomponent, inventorymerchant.getSize()));
         MerchantRecipeList merchantrecipelist = p_openTrade_1_.getOffers(this);
         if(merchantrecipelist != null) {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.buffer());
            packetdataserializer.writeInt(this.containerCounter);
            merchantrecipelist.a(packetdataserializer);
            this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|TrList", packetdataserializer));
         }

      }
   }

   public void openHorseInventory(EntityHorse p_openHorseInventory_1_, IInventory p_openHorseInventory_2_) {
      Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerHorse(this.inventory, p_openHorseInventory_2_, p_openHorseInventory_1_, this));
      if(container == null) {
         p_openHorseInventory_2_.closeContainer(this);
      } else {
         if(this.activeContainer != this.defaultContainer) {
            this.closeInventory();
         }

         this.nextContainerCounter();
         this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "EntityHorse", p_openHorseInventory_2_.getScoreboardDisplayName(), p_openHorseInventory_2_.getSize(), p_openHorseInventory_1_.getId()));
         this.activeContainer = container;
         this.activeContainer.windowId = this.containerCounter;
         this.activeContainer.addSlotListener(this);
      }
   }

   public void openBook(ItemStack p_openBook_1_) {
      Item item = p_openBook_1_.getItem();
      if(item == Items.WRITTEN_BOOK) {
         this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(Unpooled.buffer())));
      }

   }

   public void a(Container p_a_1_, int p_a_2_, ItemStack p_a_3_) {
      if(!(p_a_1_.getSlot(p_a_2_) instanceof SlotResult) && !this.g) {
         this.playerConnection.sendPacket(new PacketPlayOutSetSlot(p_a_1_.windowId, p_a_2_, p_a_3_));
      }

   }

   public void updateInventory(Container p_updateInventory_1_) {
      this.a(p_updateInventory_1_, p_updateInventory_1_.a());
   }

   public void a(Container p_a_1_, List<ItemStack> p_a_2_) {
      this.playerConnection.sendPacket(new PacketPlayOutWindowItems(p_a_1_.windowId, p_a_2_));
      this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
      if(EnumSet.of(InventoryType.CRAFTING, InventoryType.WORKBENCH).contains(p_a_1_.getBukkitView().getType())) {
         this.playerConnection.sendPacket(new PacketPlayOutSetSlot(p_a_1_.windowId, 0, p_a_1_.getSlot(0).getItem()));
      }

   }

   public void setContainerData(Container p_setContainerData_1_, int p_setContainerData_2_, int p_setContainerData_3_) {
      this.playerConnection.sendPacket(new PacketPlayOutWindowData(p_setContainerData_1_.windowId, p_setContainerData_2_, p_setContainerData_3_));
   }

   public void setContainerData(Container p_setContainerData_1_, IInventory p_setContainerData_2_) {
      for(int i = 0; i < p_setContainerData_2_.g(); ++i) {
         this.playerConnection.sendPacket(new PacketPlayOutWindowData(p_setContainerData_1_.windowId, i, p_setContainerData_2_.getProperty(i)));
      }

   }

   public void closeInventory() {
      CraftEventFactory.handleInventoryCloseEvent(this);
      this.playerConnection.sendPacket(new PacketPlayOutCloseWindow(this.activeContainer.windowId));
      this.p();
   }

   public void broadcastCarriedItem() {
      if(!this.g) {
         this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
      }

   }

   public void p() {
      this.activeContainer.b((EntityHuman)this);
      this.activeContainer = this.defaultContainer;
   }

   public void a(float p_a_1_, float p_a_2_, boolean p_a_3_, boolean p_a_4_) {
      if(this.vehicle != null) {
         if(p_a_1_ >= -1.0F && p_a_1_ <= 1.0F) {
            this.aZ = p_a_1_;
         }

         if(p_a_2_ >= -1.0F && p_a_2_ <= 1.0F) {
            this.ba = p_a_2_;
         }

         this.aY = p_a_3_;
         this.setSneaking(p_a_4_);
      }

   }

   public void a(Statistic p_a_1_, int p_a_2_) {
      if(p_a_1_ != null) {
         this.bK.b(this, p_a_1_, p_a_2_);

         for(ScoreboardObjective scoreboardobjective : this.getScoreboard().getObjectivesForCriteria(p_a_1_.k())) {
            this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective).addScore(p_a_2_);
         }

         if(this.bK.e()) {
            this.bK.a(this);
         }
      }

   }

   public void a(Statistic p_a_1_) {
      if(p_a_1_ != null) {
         this.bK.setStatistic(this, p_a_1_, 0);

         for(ScoreboardObjective scoreboardobjective : this.getScoreboard().getObjectivesForCriteria(p_a_1_.k())) {
            this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective).setScore(0);
         }

         if(this.bK.e()) {
            this.bK.a(this);
         }
      }

   }

   public void q() {
      if(this.passenger != null) {
         this.passenger.mount(this);
      }

      if(this.sleeping) {
         this.a(true, false, false);
      }

   }

   public void triggerHealthUpdate() {
      this.bM = -1.0E8F;
      this.lastSentExp = -1;
   }

   public void sendMessage(IChatBaseComponent[] p_sendMessage_1_) {
      for(IChatBaseComponent ichatbasecomponent : p_sendMessage_1_) {
         this.sendMessage(ichatbasecomponent);
      }

   }

   public void b(IChatBaseComponent p_b_1_) {
      this.playerConnection.sendPacket(new PacketPlayOutChat(p_b_1_));
   }

   protected void s() {
      this.playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte)9));
      super.s();
   }

   public void a(ItemStack p_a_1_, int p_a_2_) {
      super.a(p_a_1_, p_a_2_);
      if(p_a_1_ != null && p_a_1_.getItem() != null && p_a_1_.getItem().e(p_a_1_) == EnumAnimation.EAT) {
         this.u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(this, 3));
      }

   }

   public void copyTo(EntityHuman p_copyTo_1_, boolean p_copyTo_2_) {
      super.copyTo(p_copyTo_1_, p_copyTo_2_);
      this.lastSentExp = -1;
      this.bM = -1.0F;
      this.bN = -1;
      this.removeQueue.addAll(((EntityPlayer)p_copyTo_1_).removeQueue);
   }

   protected void a(MobEffect p_a_1_) {
      super.a((MobEffect)p_a_1_);
      this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.getId(), p_a_1_));
   }

   protected void a(MobEffect p_a_1_, boolean p_a_2_) {
      super.a(p_a_1_, p_a_2_);
      this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.getId(), p_a_1_));
   }

   protected void b(MobEffect p_b_1_) {
      super.b((MobEffect)p_b_1_);
      this.playerConnection.sendPacket(new PacketPlayOutRemoveEntityEffect(this.getId(), p_b_1_));
   }

   public void enderTeleportTo(double p_enderTeleportTo_1_, double p_enderTeleportTo_3_, double p_enderTeleportTo_5_) {
      this.playerConnection.a(p_enderTeleportTo_1_, p_enderTeleportTo_3_, p_enderTeleportTo_5_, this.yaw, this.pitch);
   }

   public void b(Entity p_b_1_) {
      this.u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(p_b_1_, 4));
   }

   public void c(Entity p_c_1_) {
      this.u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(p_c_1_, 5));
   }

   public void updateAbilities() {
      if(this.playerConnection != null) {
         this.playerConnection.sendPacket(new PacketPlayOutAbilities(this.abilities));
         this.B();
      }

   }

   public WorldServer u() {
      return (WorldServer)this.world;
   }

   public void a(WorldSettings.EnumGamemode p_a_1_) {
      this.getBukkitEntity().setGameMode(GameMode.getByValue(p_a_1_.getId()));
   }

   public boolean isSpectator() {
      return this.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.SPECTATOR;
   }

   public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
      this.playerConnection.sendPacket(new PacketPlayOutChat(p_sendMessage_1_));
   }

   public boolean a(int p_a_1_, String p_a_2_) {
      return "@".equals(p_a_2_)?this.getBukkitEntity().hasPermission("minecraft.command.selector"):true;
   }

   public String w() {
      String s = this.playerConnection.networkManager.getSocketAddress().toString();
      s = s.substring(s.indexOf("/") + 1);
      s = s.substring(0, s.indexOf(":"));
      return s;
   }

   public void a(PacketPlayInSettings p_a_1_) {
      this.locale = p_a_1_.a();
      this.bR = p_a_1_.c();
      this.bS = p_a_1_.d();
      this.getDataWatcher().watch(10, Byte.valueOf((byte)p_a_1_.e()));
   }

   public EntityHuman.EnumChatVisibility getChatFlags() {
      return this.bR;
   }

   public void setResourcePack(String p_setResourcePack_1_, String p_setResourcePack_2_) {
      this.playerConnection.sendPacket(new PacketPlayOutResourcePackSend(p_setResourcePack_1_, p_setResourcePack_2_));
   }

   public BlockPosition getChunkCoordinates() {
      return new BlockPosition(this.locX, this.locY + 0.5D, this.locZ);
   }

   public void resetIdleTimer() {
      this.bT = MinecraftServer.az();
   }

   public ServerStatisticManager getStatisticManager() {
      return this.bK;
   }

   public void d(Entity p_d_1_) {
      if(p_d_1_ instanceof EntityHuman) {
         this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[]{p_d_1_.getId()}));
      } else {
         this.removeQueue.add(Integer.valueOf(p_d_1_.getId()));
      }

   }

   protected void B() {
      if(this.isSpectator()) {
         this.bj();
         this.setInvisible(true);
      } else {
         super.B();
      }

      this.u().getTracker().a(this);
   }

   public Entity C() {
      return (Entity)(this.bU == null?this:this.bU);
   }

   public void setSpectatorTarget(Entity p_setSpectatorTarget_1_) {
      Entity entity = this.C();
      this.bU = (Entity)(p_setSpectatorTarget_1_ == null?this:p_setSpectatorTarget_1_);
      if(entity != this.bU) {
         this.playerConnection.sendPacket(new PacketPlayOutCamera(this.bU));
         this.enderTeleportTo(this.bU.locX, this.bU.locY, this.bU.locZ);
      }

   }

   public void attack(Entity p_attack_1_) {
      if(this.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.SPECTATOR) {
         this.setSpectatorTarget(p_attack_1_);
      } else {
         super.attack(p_attack_1_);
      }

   }

   public long D() {
      return this.bT;
   }

   public IChatBaseComponent getPlayerListName() {
      return this.listName;
   }

   public long getPlayerTime() {
      return this.relativeTime?this.world.getDayTime() + this.timeOffset:this.world.getDayTime() - this.world.getDayTime() % 24000L + this.timeOffset;
   }

   public WeatherType getPlayerWeather() {
      return this.weather;
   }

   public void setPlayerWeather(WeatherType p_setPlayerWeather_1_, boolean p_setPlayerWeather_2_) {
      if(p_setPlayerWeather_2_ || this.weather == null) {
         if(p_setPlayerWeather_2_) {
            this.weather = p_setPlayerWeather_1_;
         }

         if(p_setPlayerWeather_1_ == WeatherType.DOWNFALL) {
            this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(2, 0.0F));
         } else {
            this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(1, 0.0F));
         }

      }
   }

   public void updateWeather(float p_updateWeather_1_, float p_updateWeather_2_, float p_updateWeather_3_, float p_updateWeather_4_) {
      if(this.weather == null) {
         if(p_updateWeather_1_ != p_updateWeather_2_) {
            this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, p_updateWeather_2_));
         }
      } else if(this.pluginRainPositionPrevious != this.pluginRainPosition) {
         this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, this.pluginRainPosition));
      }

      if(p_updateWeather_3_ != p_updateWeather_4_) {
         if(this.weather != WeatherType.DOWNFALL && this.weather != null) {
            this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, 0.0F));
         } else {
            this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, p_updateWeather_4_));
         }
      }

   }

   public void tickWeather() {
      if(this.weather != null) {
         this.pluginRainPositionPrevious = this.pluginRainPosition;
         if(this.weather == WeatherType.DOWNFALL) {
            this.pluginRainPosition = (float)((double)this.pluginRainPosition + 0.01D);
         } else {
            this.pluginRainPosition = (float)((double)this.pluginRainPosition - 0.01D);
         }

         this.pluginRainPosition = MathHelper.a(this.pluginRainPosition, 0.0F, 1.0F);
      }
   }

   public void resetPlayerWeather() {
      this.weather = null;
      this.setPlayerWeather(this.world.getWorldData().hasStorm()?WeatherType.DOWNFALL:WeatherType.CLEAR, false);
   }

   public String toString() {
      return super.toString() + "(" + this.getName() + " at " + this.locX + "," + this.locY + "," + this.locZ + ")";
   }

   public void reset() {
      float f = 0.0F;
      boolean flag = this.world.getGameRules().getBoolean("keepInventory");
      if(this.keepLevel || flag) {
         f = this.exp;
         this.newTotalExp = this.expTotal;
         this.newLevel = this.expLevel;
      }

      this.setHealth(this.getMaxHealth());
      this.fireTicks = 0;
      this.fallDistance = 0.0F;
      this.foodData = new FoodMetaData(this);
      this.expLevel = this.newLevel;
      this.expTotal = this.newTotalExp;
      this.exp = 0.0F;
      this.deathTicks = 0;
      this.removeAllEffects();
      this.updateEffects = true;
      this.activeContainer = this.defaultContainer;
      this.killer = null;
      this.lastDamager = null;
      this.combatTracker = new CombatTracker(this);
      this.lastSentExp = -1;
      if(!this.keepLevel && !flag) {
         this.giveExp(this.newExp);
      } else {
         this.exp = f;
      }

      this.keepLevel = false;
   }

   public CraftPlayer getBukkitEntity() {
      return (CraftPlayer)super.getBukkitEntity();
   }
}

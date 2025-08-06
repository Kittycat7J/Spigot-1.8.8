package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import net.minecraft.server.v1_8_R3.AchievementList;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandBlockListenerAbstract;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.ContainerBeacon;
import net.minecraft.server.v1_8_R3.ContainerMerchant;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.GameProfileBanEntry;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.IntHashMap;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBookAndQuill;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemWrittenBook;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayOutTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_8_R3.PlayerConnectionUtils;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.SharedConstants;
import net.minecraft.server.v1_8_R3.Slot;
import net.minecraft.server.v1_8_R3.Statistic;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityCommand;
import net.minecraft.server.v1_8_R3.TileEntitySign;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftSign;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_8_R3.util.LazyPlayerSet;
import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Recipe;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.spigotmc.SpigotConfig;

public class PlayerConnection implements PacketListenerPlayIn, IUpdatePlayerListBox {
   private static final Logger c = LogManager.getLogger();
   public final NetworkManager networkManager;
   private final MinecraftServer minecraftServer;
   public EntityPlayer player;
   private int e;
   private int f;
   private int g;
   private boolean h;
   private int i;
   private long j;
   private long k;
   private volatile int chatThrottle;
   private static final AtomicIntegerFieldUpdater chatSpamField = AtomicIntegerFieldUpdater.newUpdater(PlayerConnection.class, "chatThrottle");
   private int m;
   private IntHashMap<Short> n = new IntHashMap();
   private double o;
   private double p;
   private double q;
   private boolean checkMovement = true;
   private boolean processedDisconnect;
   private final CraftServer server;
   private int lastTick = MinecraftServer.currentTick;
   private int lastDropTick = MinecraftServer.currentTick;
   private int dropCount = 0;
   private static final int SURVIVAL_PLACE_DISTANCE_SQUARED = 36;
   private static final int CREATIVE_PLACE_DISTANCE_SQUARED = 49;
   private double lastPosX = Double.MAX_VALUE;
   private double lastPosY = Double.MAX_VALUE;
   private double lastPosZ = Double.MAX_VALUE;
   private float lastPitch = Float.MAX_VALUE;
   private float lastYaw = Float.MAX_VALUE;
   private boolean justTeleported = false;
   private boolean hasMoved;
   private static final HashSet<Integer> invalidItems = new HashSet(Arrays.asList(new Integer[]{Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(26), Integer.valueOf(34), Integer.valueOf(36), Integer.valueOf(43), Integer.valueOf(51), Integer.valueOf(52), Integer.valueOf(55), Integer.valueOf(59), Integer.valueOf(60), Integer.valueOf(62), Integer.valueOf(63), Integer.valueOf(64), Integer.valueOf(68), Integer.valueOf(71), Integer.valueOf(74), Integer.valueOf(75), Integer.valueOf(83), Integer.valueOf(90), Integer.valueOf(92), Integer.valueOf(93), Integer.valueOf(94), Integer.valueOf(104), Integer.valueOf(105), Integer.valueOf(115), Integer.valueOf(117), Integer.valueOf(118), Integer.valueOf(119), Integer.valueOf(125), Integer.valueOf(127), Integer.valueOf(132), Integer.valueOf(140), Integer.valueOf(141), Integer.valueOf(142), Integer.valueOf(144)}));
   private long lastPlace = -1L;
   private int packets = 0;

   public PlayerConnection(MinecraftServer p_i473_1_, NetworkManager p_i473_2_, EntityPlayer p_i473_3_) {
      this.minecraftServer = p_i473_1_;
      this.networkManager = p_i473_2_;
      p_i473_2_.a((PacketListener)this);
      this.player = p_i473_3_;
      p_i473_3_.playerConnection = this;
      this.server = p_i473_1_.server;
   }

   public CraftPlayer getPlayer() {
      return this.player == null?null:this.player.getBukkitEntity();
   }

   public void c() {
      this.h = false;
      ++this.e;
      this.minecraftServer.methodProfiler.a("keepAlive");
      if((long)this.e - this.k > 40L) {
         this.k = (long)this.e;
         this.j = this.d();
         this.i = (int)this.j;
         this.sendPacket(new PacketPlayOutKeepAlive(this.i));
      }

      this.minecraftServer.methodProfiler.b();

      while(true) {
         int i = this.chatThrottle;
         if(this.chatThrottle <= 0 || chatSpamField.compareAndSet(this, i, i - 1)) {
            break;
         }
      }

      if(this.m > 0) {
         --this.m;
      }

      if(this.player.D() > 0L && this.minecraftServer.getIdleTimeout() > 0 && MinecraftServer.az() - this.player.D() > (long)(this.minecraftServer.getIdleTimeout() * 1000 * 60)) {
         this.player.resetIdleTimer();
         this.disconnect("You have been idle for too long!");
      }

   }

   public NetworkManager a() {
      return this.networkManager;
   }

   public void disconnect(String p_disconnect_1_) {
      String s = EnumChatFormat.YELLOW + this.player.getName() + " left the game.";
      PlayerKickEvent playerkickevent = new PlayerKickEvent(this.server.getPlayer(this.player), p_disconnect_1_, s);
      if(this.server.getServer().isRunning()) {
         this.server.getPluginManager().callEvent(playerkickevent);
      }

      if(!playerkickevent.isCancelled()) {
         p_disconnect_1_ = playerkickevent.getReason();
         final ChatComponentText chatcomponenttext = new ChatComponentText(p_disconnect_1_);
         this.networkManager.a(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener() {
            public void operationComplete(Future p_operationComplete_1_) throws Exception {
               PlayerConnection.this.networkManager.close(chatcomponenttext);
            }
         }, new GenericFutureListener[0]);
         this.a((IChatBaseComponent)chatcomponenttext);
         this.networkManager.k();
         this.minecraftServer.postToMainThread(new Runnable() {
            public void run() {
               PlayerConnection.this.networkManager.l();
            }
         });
      }
   }

   public void a(PacketPlayInSteerVehicle p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      this.player.a(p_a_1_.a(), p_a_1_.b(), p_a_1_.c(), p_a_1_.d());
   }

   private boolean b(PacketPlayInFlying p_b_1_) {
      return !Doubles.isFinite(p_b_1_.a()) || !Doubles.isFinite(p_b_1_.b()) || !Doubles.isFinite(p_b_1_.c()) || !Floats.isFinite(p_b_1_.e()) || !Floats.isFinite(p_b_1_.d());
   }

   public void a(PacketPlayInFlying p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      if(this.b(p_a_1_)) {
         this.disconnect("Invalid move packet received");
      } else {
         WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
         this.h = true;
         if(!this.player.viewingCredits) {
            double d0 = this.player.locX;
            double d1 = this.player.locY;
            double d2 = this.player.locZ;
            double d3 = 0.0D;
            double d4 = p_a_1_.a() - this.o;
            double d5 = p_a_1_.b() - this.p;
            double d6 = p_a_1_.c() - this.q;
            if(p_a_1_.g()) {
               d3 = d4 * d4 + d5 * d5 + d6 * d6;
               if(!this.checkMovement && d3 < 0.25D) {
                  this.checkMovement = true;
               }
            }

            Player player = this.getPlayer();
            if(!this.hasMoved) {
               Location location = player.getLocation();
               this.lastPosX = location.getX();
               this.lastPosY = location.getY();
               this.lastPosZ = location.getZ();
               this.lastYaw = location.getYaw();
               this.lastPitch = location.getPitch();
               this.hasMoved = true;
            }

            Location location3 = new Location(player.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
            Location location1 = player.getLocation().clone();
            if(p_a_1_.hasPos && (!p_a_1_.hasPos || p_a_1_.y != -999.0D)) {
               location1.setX(p_a_1_.x);
               location1.setY(p_a_1_.y);
               location1.setZ(p_a_1_.z);
            }

            if(p_a_1_.hasLook) {
               location1.setYaw(p_a_1_.yaw);
               location1.setPitch(p_a_1_.pitch);
            }

            double d7 = Math.pow(this.lastPosX - location1.getX(), 2.0D) + Math.pow(this.lastPosY - location1.getY(), 2.0D) + Math.pow(this.lastPosZ - location1.getZ(), 2.0D);
            float f = Math.abs(this.lastYaw - location1.getYaw()) + Math.abs(this.lastPitch - location1.getPitch());
            if((d7 > 0.00390625D || f > 10.0F) && this.checkMovement && !this.player.dead) {
               this.lastPosX = location1.getX();
               this.lastPosY = location1.getY();
               this.lastPosZ = location1.getZ();
               this.lastYaw = location1.getYaw();
               this.lastPitch = location1.getPitch();
               Location location2 = location1.clone();
               PlayerMoveEvent playermoveevent = new PlayerMoveEvent(player, location3, location1);
               this.server.getPluginManager().callEvent(playermoveevent);
               if(playermoveevent.isCancelled()) {
                  this.player.playerConnection.sendPacket(new PacketPlayOutPosition(location3.getX(), location3.getY(), location3.getZ(), location3.getYaw(), location3.getPitch(), Collections.emptySet()));
                  return;
               }

               if(!location2.equals(playermoveevent.getTo()) && !playermoveevent.isCancelled()) {
                  this.player.getBukkitEntity().teleport(playermoveevent.getTo(), TeleportCause.UNKNOWN);
                  return;
               }

               if(!location3.equals(this.getPlayer().getLocation()) && this.justTeleported) {
                  this.justTeleported = false;
                  return;
               }
            }

            if(this.checkMovement && !this.player.dead) {
               this.f = this.e;
               if(this.player.vehicle != null) {
                  float f1 = this.player.yaw;
                  float f2 = this.player.pitch;
                  this.player.vehicle.al();
                  double d17 = this.player.locX;
                  double d18 = this.player.locY;
                  double d19 = this.player.locZ;
                  if(p_a_1_.h()) {
                     f1 = p_a_1_.d();
                     f2 = p_a_1_.e();
                  }

                  this.player.onGround = p_a_1_.f();
                  this.player.l();
                  this.player.setLocation(d17, d18, d19, f1, f2);
                  if(this.player.vehicle != null) {
                     this.player.vehicle.al();
                  }

                  this.minecraftServer.getPlayerList().d(this.player);
                  if(this.player.vehicle != null) {
                     this.player.vehicle.ai = true;
                     if(d3 > 4.0D) {
                        Entity entity = this.player.vehicle;
                        this.player.playerConnection.sendPacket(new PacketPlayOutEntityTeleport(entity));
                        this.a(this.player.locX, this.player.locY, this.player.locZ, this.player.yaw, this.player.pitch);
                     }
                  }

                  if(this.checkMovement) {
                     this.o = this.player.locX;
                     this.p = this.player.locY;
                     this.q = this.player.locZ;
                  }

                  worldserver.g(this.player);
                  return;
               }

               if(this.player.isSleeping()) {
                  this.player.l();
                  this.player.setLocation(this.o, this.p, this.q, this.player.yaw, this.player.pitch);
                  worldserver.g(this.player);
                  return;
               }

               double d11 = this.player.locY;
               this.o = this.player.locX;
               this.p = this.player.locY;
               this.q = this.player.locZ;
               double d8 = this.player.locX;
               double d9 = this.player.locY;
               double d10 = this.player.locZ;
               float f3 = this.player.yaw;
               float f4 = this.player.pitch;
               if(p_a_1_.g() && p_a_1_.b() == -999.0D) {
                  p_a_1_.a(false);
               }

               if(p_a_1_.g()) {
                  d8 = p_a_1_.a();
                  d9 = p_a_1_.b();
                  d10 = p_a_1_.c();
                  if(Math.abs(p_a_1_.a()) > 3.0E7D || Math.abs(p_a_1_.c()) > 3.0E7D) {
                     this.disconnect("Illegal position");
                     return;
                  }
               }

               if(p_a_1_.h()) {
                  f3 = p_a_1_.d();
                  f4 = p_a_1_.e();
               }

               this.player.l();
               this.player.setLocation(this.o, this.p, this.q, f3, f4);
               if(!this.checkMovement) {
                  return;
               }

               double d12 = d8 - this.player.locX;
               double d13 = d9 - this.player.locY;
               double d14 = d10 - this.player.locZ;
               double d15 = this.player.motX * this.player.motX + this.player.motY * this.player.motY + this.player.motZ * this.player.motZ;
               double d16 = d12 * d12 + d13 * d13 + d14 * d14;
               if(d16 - d15 > SpigotConfig.movedTooQuicklyThreshold && this.checkMovement && (!this.minecraftServer.T() || !this.minecraftServer.S().equals(this.player.getName()))) {
                  c.warn(this.player.getName() + " moved too quickly! " + d12 + "," + d13 + "," + d14 + " (" + d12 + ", " + d13 + ", " + d14 + ")");
                  this.a(this.o, this.p, this.q, this.player.yaw, this.player.pitch);
                  return;
               }

               float f5 = 0.0625F;
               boolean flag = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink((double)f5, (double)f5, (double)f5)).isEmpty();
               if(this.player.onGround && !p_a_1_.f() && d13 > 0.0D) {
                  this.player.bF();
               }

               this.player.move(d12, d13, d14);
               this.player.onGround = p_a_1_.f();
               d12 = d8 - this.player.locX;
               d13 = d9 - this.player.locY;
               if(d13 > -0.5D || d13 < 0.5D) {
                  d13 = 0.0D;
               }

               d14 = d10 - this.player.locZ;
               d16 = d12 * d12 + d13 * d13 + d14 * d14;
               boolean flag1 = false;
               if(d16 > SpigotConfig.movedWronglyThreshold && !this.player.isSleeping() && !this.player.playerInteractManager.isCreative()) {
                  flag1 = true;
                  c.warn(this.player.getName() + " moved wrongly!");
               }

               this.player.setLocation(d8, d9, d10, f3, f4);
               this.player.checkMovement(this.player.locX - d0, this.player.locY - d1, this.player.locZ - d2);
               if(!this.player.noclip) {
                  boolean flag2 = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink((double)f5, (double)f5, (double)f5)).isEmpty();
                  if(flag && (flag1 || !flag2) && !this.player.isSleeping()) {
                     this.a(this.o, this.p, this.q, f3, f4);
                     return;
                  }
               }

               AxisAlignedBB axisalignedbb = this.player.getBoundingBox().grow((double)f5, (double)f5, (double)f5).a(0.0D, -0.55D, 0.0D);
               if(!this.minecraftServer.getAllowFlight() && !this.player.abilities.canFly && !worldserver.c(axisalignedbb)) {
                  if(d13 >= -0.03125D) {
                     ++this.g;
                     if(this.g > 80) {
                        c.warn(this.player.getName() + " was kicked for floating too long!");
                        this.disconnect("Flying is not enabled on this server");
                        return;
                     }
                  }
               } else {
                  this.g = 0;
               }

               this.player.onGround = p_a_1_.f();
               this.minecraftServer.getPlayerList().d(this.player);
               this.player.a(this.player.locY - d11, p_a_1_.f());
            } else if(this.e - this.f > 20) {
               this.a(this.o, this.p, this.q, this.player.yaw, this.player.pitch);
            }
         }
      }

   }

   public void a(double p_a_1_, double p_a_3_, double p_a_5_, float p_a_7_, float p_a_8_) {
      this.a(p_a_1_, p_a_3_, p_a_5_, p_a_7_, p_a_8_, Collections.emptySet());
   }

   public void a(double p_a_1_, double p_a_3_, double p_a_5_, float p_a_7_, float p_a_8_, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> p_a_9_) {
      Player player = this.getPlayer();
      Location location = player.getLocation();
      double d0 = p_a_1_;
      double d1 = p_a_3_;
      double d2 = p_a_5_;
      float f = p_a_7_;
      float f1 = p_a_8_;
      if(p_a_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X)) {
         d0 = p_a_1_ + location.getX();
      }

      if(p_a_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y)) {
         d1 = p_a_3_ + location.getY();
      }

      if(p_a_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z)) {
         d2 = p_a_5_ + location.getZ();
      }

      if(p_a_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT)) {
         f = p_a_7_ + location.getYaw();
      }

      if(p_a_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT)) {
         f1 = p_a_8_ + location.getPitch();
      }

      Location location1 = new Location(this.getPlayer().getWorld(), d0, d1, d2, f, f1);
      PlayerTeleportEvent playerteleportevent = new PlayerTeleportEvent(player, location.clone(), location1.clone(), TeleportCause.UNKNOWN);
      this.server.getPluginManager().callEvent(playerteleportevent);
      if(playerteleportevent.isCancelled() || location1.equals(playerteleportevent.getTo())) {
         p_a_9_.clear();
         location1 = playerteleportevent.isCancelled()?playerteleportevent.getFrom():playerteleportevent.getTo();
         p_a_1_ = location1.getX();
         p_a_3_ = location1.getY();
         p_a_5_ = location1.getZ();
         p_a_7_ = location1.getYaw();
         p_a_8_ = location1.getPitch();
      }

      this.internalTeleport(p_a_1_, p_a_3_, p_a_5_, p_a_7_, p_a_8_, p_a_9_);
   }

   public void teleport(Location p_teleport_1_) {
      this.internalTeleport(p_teleport_1_.getX(), p_teleport_1_.getY(), p_teleport_1_.getZ(), p_teleport_1_.getYaw(), p_teleport_1_.getPitch(), Collections.emptySet());
   }

   private void internalTeleport(double p_internalTeleport_1_, double p_internalTeleport_3_, double p_internalTeleport_5_, float p_internalTeleport_7_, float p_internalTeleport_8_, Set p_internalTeleport_9_) {
      if(Float.isNaN(p_internalTeleport_7_)) {
         p_internalTeleport_7_ = 0.0F;
      }

      if(Float.isNaN(p_internalTeleport_8_)) {
         p_internalTeleport_8_ = 0.0F;
      }

      this.justTeleported = true;
      this.checkMovement = false;
      this.o = p_internalTeleport_1_;
      this.p = p_internalTeleport_3_;
      this.q = p_internalTeleport_5_;
      if(p_internalTeleport_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X)) {
         this.o += this.player.locX;
      }

      if(p_internalTeleport_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y)) {
         this.p += this.player.locY;
      }

      if(p_internalTeleport_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z)) {
         this.q += this.player.locZ;
      }

      float f = p_internalTeleport_7_;
      float f1 = p_internalTeleport_8_;
      if(p_internalTeleport_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT)) {
         f = p_internalTeleport_7_ + this.player.yaw;
      }

      if(p_internalTeleport_9_.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT)) {
         f1 = p_internalTeleport_8_ + this.player.pitch;
      }

      this.lastPosX = this.o;
      this.lastPosY = this.p;
      this.lastPosZ = this.q;
      this.lastYaw = f;
      this.lastPitch = f1;
      this.player.setLocation(this.o, this.p, this.q, f, f1);
      this.player.playerConnection.sendPacket(new PacketPlayOutPosition(p_internalTeleport_1_, p_internalTeleport_3_, p_internalTeleport_5_, p_internalTeleport_7_, p_internalTeleport_8_, p_internalTeleport_9_));
   }

   public void a(PacketPlayInBlockDig p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      if(!this.player.dead) {
         WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
         BlockPosition blockposition = p_a_1_.a();
         this.player.resetIdleTimer();
         switch(PlayerConnection.SyntheticClass_1.a[p_a_1_.c().ordinal()]) {
         case 1:
            if(!this.player.isSpectator()) {
               if(this.lastDropTick != MinecraftServer.currentTick) {
                  this.dropCount = 0;
                  this.lastDropTick = MinecraftServer.currentTick;
               } else {
                  ++this.dropCount;
                  if(this.dropCount >= 20) {
                     c.warn(this.player.getName() + " dropped their items too quickly!");
                     this.disconnect("You dropped your items too quickly (Hacking?)");
                     return;
                  }
               }

               this.player.a(false);
            }

            return;
         case 2:
            if(!this.player.isSpectator()) {
               this.player.a(true);
            }

            return;
         case 3:
            this.player.bU();
            return;
         case 4:
         case 5:
         case 6:
            double d0 = this.player.locX - ((double)blockposition.getX() + 0.5D);
            double d1 = this.player.locY - ((double)blockposition.getY() + 0.5D) + 1.5D;
            double d2 = this.player.locZ - ((double)blockposition.getZ() + 0.5D);
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if(d3 > 36.0D) {
               return;
            } else if(blockposition.getY() >= this.minecraftServer.getMaxBuildHeight()) {
               return;
            } else {
               if(p_a_1_.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                  if(!this.minecraftServer.a(worldserver, blockposition, this.player) && worldserver.getWorldBorder().a(blockposition)) {
                     this.player.playerInteractManager.a(blockposition, p_a_1_.b());
                  } else {
                     CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, blockposition, p_a_1_.b(), this.player.inventory.getItemInHand());
                     this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
                     TileEntity tileentity = worldserver.getTileEntity(blockposition);
                     if(tileentity != null) {
                        this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
                     }
                  }
               } else {
                  if(p_a_1_.c() == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
                     this.player.playerInteractManager.a(blockposition);
                  } else if(p_a_1_.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                     this.player.playerInteractManager.e();
                  }

                  if(worldserver.getType(blockposition).getBlock().getMaterial() != Material.AIR) {
                     this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
                  }
               }

               return;
            }
         default:
            throw new IllegalArgumentException("Invalid player action");
         }
      }
   }

   public void a(PacketPlayInBlockPlace p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
      boolean flag = false;
      if(this.lastPlace != -1L && p_a_1_.timestamp - this.lastPlace < 30L && this.packets++ >= 4) {
         flag = true;
      } else if(p_a_1_.timestamp - this.lastPlace >= 30L || this.lastPlace == -1L) {
         this.lastPlace = p_a_1_.timestamp;
         this.packets = 0;
      }

      if(!this.player.dead) {
         boolean flag1 = false;
         ItemStack itemstack = this.player.inventory.getItemInHand();
         boolean flag2 = false;
         BlockPosition blockposition = p_a_1_.a();
         EnumDirection enumdirection = EnumDirection.fromType1(p_a_1_.getFace());
         this.player.resetIdleTimer();
         if(p_a_1_.getFace() == 255) {
            if(itemstack == null) {
               return;
            }

            int i = itemstack.count;
            if(!flag) {
               float f = this.player.pitch;
               float f1 = this.player.yaw;
               double d0 = this.player.locX;
               double d1 = this.player.locY + (double)this.player.getHeadHeight();
               double d2 = this.player.locZ;
               Vec3D vec3d = new Vec3D(d0, d1, d2);
               float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
               float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
               float f4 = -MathHelper.cos(-f * 0.017453292F);
               float f5 = MathHelper.sin(-f * 0.017453292F);
               float f6 = f3 * f4;
               float f7 = f2 * f4;
               double d3 = this.player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE?5.0D:4.5D;
               Vec3D vec3d1 = vec3d.add((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
               MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d1, false);
               boolean flag3 = false;
               if(movingobjectposition != null && movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
                  if(this.player.playerInteractManager.firedInteract) {
                     this.player.playerInteractManager.firedInteract = false;
                     flag3 = this.player.playerInteractManager.interactResult;
                  } else {
                     PlayerInteractEvent playerinteractevent1 = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_BLOCK, movingobjectposition.a(), movingobjectposition.direction, itemstack, true);
                     flag3 = playerinteractevent1.useItemInHand() == Result.DENY;
                  }
               } else {
                  PlayerInteractEvent playerinteractevent = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_AIR, itemstack);
                  flag3 = playerinteractevent.useItemInHand() == Result.DENY;
               }

               if(!flag3) {
                  this.player.playerInteractManager.useItem(this.player, this.player.world, itemstack);
               }
            }

            flag1 = itemstack.count != i || itemstack.getItem() == Item.getItemOf(Blocks.WATERLILY);
         } else if(blockposition.getY() < this.minecraftServer.getMaxBuildHeight() - 1 || enumdirection != EnumDirection.UP && blockposition.getY() < this.minecraftServer.getMaxBuildHeight()) {
            Location location = this.getPlayer().getEyeLocation();
            double d4 = NumberConversions.square(location.getX() - (double)blockposition.getX()) + NumberConversions.square(location.getY() - (double)blockposition.getY()) + NumberConversions.square(location.getZ() - (double)blockposition.getZ());
            if(d4 > (double)(this.getPlayer().getGameMode() == GameMode.CREATIVE?49:36)) {
               return;
            }

            if(!worldserver.getWorldBorder().a(blockposition)) {
               return;
            }

            if(this.checkMovement && this.player.e((double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D) < 64.0D && !this.minecraftServer.a(worldserver, blockposition, this.player) && worldserver.getWorldBorder().a(blockposition)) {
               flag1 = flag || !this.player.playerInteractManager.interact(this.player, worldserver, itemstack, blockposition, enumdirection, p_a_1_.d(), p_a_1_.e(), p_a_1_.f());
            }

            flag2 = true;
         } else {
            ChatMessage chatmessage = new ChatMessage("build.tooHigh", new Object[]{Integer.valueOf(this.minecraftServer.getMaxBuildHeight())});
            chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
            this.player.playerConnection.sendPacket(new PacketPlayOutChat(chatmessage));
            flag2 = true;
         }

         if(flag2) {
            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition.shift(enumdirection)));
         }

         itemstack = this.player.inventory.getItemInHand();
         if(itemstack != null && itemstack.count == 0) {
            this.player.inventory.items[this.player.inventory.itemInHandIndex] = null;
            itemstack = null;
         }

         if(itemstack == null || itemstack.l() == 0) {
            this.player.g = true;
            this.player.inventory.items[this.player.inventory.itemInHandIndex] = ItemStack.b(this.player.inventory.items[this.player.inventory.itemInHandIndex]);
            Slot slot = this.player.activeContainer.getSlot(this.player.inventory, this.player.inventory.itemInHandIndex);
            this.player.activeContainer.b();
            this.player.g = false;
            if(!ItemStack.matches(this.player.inventory.getItemInHand(), p_a_1_.getItemStack()) || flag1) {
               this.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, slot.rawSlotIndex, this.player.inventory.getItemInHand()));
            }
         }

      }
   }

   public void a(PacketPlayInSpectate p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      if(this.player.isSpectator()) {
         Entity entity = null;
         WorldServer[] aworldserver = this.minecraftServer.worldServer;
         int ix = aworldserver.length;

         for(WorldServer worldserver : this.minecraftServer.worlds) {
            if(worldserver != null) {
               entity = p_a_1_.a(worldserver);
               if(entity != null) {
                  break;
               }
            }
         }

         if(entity != null) {
            this.player.setSpectatorTarget(this.player);
            this.player.mount((Entity)null);
            this.player.getBukkitEntity().teleport(entity.getBukkitEntity(), TeleportCause.SPECTATE);
         }
      }

   }

   public void a(PacketPlayInResourcePackStatus p_a_1_) {
      this.server.getPluginManager().callEvent(new PlayerResourcePackStatusEvent(this.getPlayer(), Status.values()[p_a_1_.b.ordinal()]));
   }

   public void a(IChatBaseComponent p_a_1_) {
      if(!this.processedDisconnect) {
         this.processedDisconnect = true;
         c.info(this.player.getName() + " lost connection: " + p_a_1_.c());
         this.player.q();
         String s = this.minecraftServer.getPlayerList().disconnect(this.player);
         if(s != null && s.length() > 0) {
            this.minecraftServer.getPlayerList().sendMessage(CraftChatMessage.fromString(s));
         }

         if(this.minecraftServer.T() && this.player.getName().equals(this.minecraftServer.S())) {
            c.info("Stopping singleplayer server as player logged out");
            this.minecraftServer.safeShutdown();
         }

      }
   }

   public void sendPacket(final Packet p_sendPacket_1_) {
      if(p_sendPacket_1_ instanceof PacketPlayOutChat) {
         PacketPlayOutChat packetplayoutchat = (PacketPlayOutChat)p_sendPacket_1_;
         EntityHuman.EnumChatVisibility entityhuman$enumchatvisibility = this.player.getChatFlags();
         if(entityhuman$enumchatvisibility == EntityHuman.EnumChatVisibility.HIDDEN) {
            return;
         }

         if(entityhuman$enumchatvisibility == EntityHuman.EnumChatVisibility.SYSTEM && !packetplayoutchat.b()) {
            return;
         }
      }

      if(p_sendPacket_1_ != null && !this.processedDisconnect) {
         if(p_sendPacket_1_ instanceof PacketPlayOutSpawnPosition) {
            PacketPlayOutSpawnPosition packetplayoutspawnposition = (PacketPlayOutSpawnPosition)p_sendPacket_1_;
            this.player.compassTarget = new Location(this.getPlayer().getWorld(), (double)packetplayoutspawnposition.position.getX(), (double)packetplayoutspawnposition.position.getY(), (double)packetplayoutspawnposition.position.getZ());
         }

         try {
            this.networkManager.handle(p_sendPacket_1_);
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Sending packet");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Packet being sent");
            crashreportsystemdetails.a("Packet class", new Callable() {
               public String a() throws Exception {
                  return p_sendPacket_1_.getClass().getCanonicalName();
               }

               public Object call() throws Exception {
                  return this.a();
               }
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   public void a(PacketPlayInHeldItemSlot p_a_1_) {
      if(!this.player.dead) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
         if(p_a_1_.a() >= 0 && p_a_1_.a() < PlayerInventory.getHotbarSize()) {
            PlayerItemHeldEvent playeritemheldevent = new PlayerItemHeldEvent(this.getPlayer(), this.player.inventory.itemInHandIndex, p_a_1_.a());
            this.server.getPluginManager().callEvent(playeritemheldevent);
            if(playeritemheldevent.isCancelled()) {
               this.sendPacket(new PacketPlayOutHeldItemSlot(this.player.inventory.itemInHandIndex));
               this.player.resetIdleTimer();
               return;
            }

            this.player.inventory.itemInHandIndex = p_a_1_.a();
            this.player.resetIdleTimer();
         } else {
            c.warn(this.player.getName() + " tried to set an invalid carried item");
            this.disconnect("Invalid hotbar selection (Hacking?)");
         }

      }
   }

   public void a(PacketPlayInChat p_a_1_) {
      boolean flag = p_a_1_.a().startsWith("/");
      if(p_a_1_.a().startsWith("/")) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      }

      if(!this.player.dead && this.player.getChatFlags() != EntityHuman.EnumChatVisibility.HIDDEN) {
         this.player.resetIdleTimer();
         final String s = p_a_1_.a();
         s = StringUtils.normalizeSpace(s);

         for(int i = 0; i < s.length(); ++i) {
            if(!SharedConstants.isAllowedChatCharacter(s.charAt(i))) {
               if(!flag) {
                  Waitable waitable = new Waitable() {
                     protected Object evaluate() {
                        PlayerConnection.this.disconnect("Illegal characters in chat");
                        return null;
                     }
                  };
                  this.minecraftServer.processQueue.add(waitable);

                  try {
                     waitable.get();
                  } catch (InterruptedException var12) {
                     Thread.currentThread().interrupt();
                  } catch (ExecutionException executionexception) {
                     throw new RuntimeException(executionexception);
                  }
               } else {
                  this.disconnect("Illegal characters in chat");
               }

               return;
            }
         }

         if(flag) {
            try {
               this.minecraftServer.server.playerCommandState = true;
               this.handleCommand(s);
            } finally {
               this.minecraftServer.server.playerCommandState = false;
            }
         } else if(s.isEmpty()) {
            c.warn(this.player.getName() + " tried to send an empty message");
         } else if(this.getPlayer().isConversing()) {
            this.minecraftServer.processQueue.add(new Waitable() {
               protected Object evaluate() {
                  PlayerConnection.this.getPlayer().acceptConversationInput(s);
                  return null;
               }
            });
         } else if(this.player.getChatFlags() == EntityHuman.EnumChatVisibility.SYSTEM) {
            ChatMessage chatmessage1 = new ChatMessage("chat.cannotSend", new Object[0]);
            chatmessage1.getChatModifier().setColor(EnumChatFormat.RED);
            this.sendPacket(new PacketPlayOutChat(chatmessage1));
         } else {
            this.chat(s, true);
         }

         boolean flag1 = true;

         for(String s1 : SpigotConfig.spamExclusions) {
            if(s1 != null && s.startsWith(s1)) {
               flag1 = false;
               break;
            }
         }

         if(flag1 && chatSpamField.addAndGet(this, 20) > 200 && !this.minecraftServer.getPlayerList().isOp(this.player.getProfile())) {
            if(!flag) {
               Waitable waitable1 = new Waitable() {
                  protected Object evaluate() {
                     PlayerConnection.this.disconnect("disconnect.spam");
                     return null;
                  }
               };
               this.minecraftServer.processQueue.add(waitable1);

               try {
                  waitable1.get();
               } catch (InterruptedException var14) {
                  Thread.currentThread().interrupt();
               } catch (ExecutionException executionexception1) {
                  throw new RuntimeException(executionexception1);
               }
            } else {
               this.disconnect("disconnect.spam");
            }
         }
      } else {
         ChatMessage chatmessage = new ChatMessage("chat.cannotSend", new Object[0]);
         chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
         this.sendPacket(new PacketPlayOutChat(chatmessage));
      }

   }

   public void chat(String p_chat_1_, boolean p_chat_2_) {
      if(!p_chat_1_.isEmpty() && this.player.getChatFlags() != EntityHuman.EnumChatVisibility.HIDDEN) {
         if(!p_chat_2_ && p_chat_1_.startsWith("/")) {
            this.handleCommand(p_chat_1_);
         } else if(this.player.getChatFlags() != EntityHuman.EnumChatVisibility.SYSTEM) {
            Player player = this.getPlayer();
            AsyncPlayerChatEvent asyncplayerchatevent = new AsyncPlayerChatEvent(p_chat_2_, player, p_chat_1_, new LazyPlayerSet());
            this.server.getPluginManager().callEvent(asyncplayerchatevent);
            if(PlayerChatEvent.getHandlerList().getRegisteredListeners().length != 0) {
               final PlayerChatEvent playerchatevent = new PlayerChatEvent(player, asyncplayerchatevent.getMessage(), asyncplayerchatevent.getFormat(), asyncplayerchatevent.getRecipients());
               playerchatevent.setCancelled(asyncplayerchatevent.isCancelled());
               Waitable waitable = new Waitable() {
                  protected Object evaluate() {
                     Bukkit.getPluginManager().callEvent(playerchatevent);
                     if(playerchatevent.isCancelled()) {
                        return null;
                     } else {
                        String s = String.format(playerchatevent.getFormat(), new Object[]{playerchatevent.getPlayer().getDisplayName(), playerchatevent.getMessage()});
                        PlayerConnection.this.minecraftServer.console.sendMessage(s);
                        if(((LazyPlayerSet)playerchatevent.getRecipients()).isLazy()) {
                           for(Object object1 : PlayerConnection.this.minecraftServer.getPlayerList().players) {
                              ((EntityPlayer)object1).sendMessage(CraftChatMessage.fromString(s));
                           }
                        } else {
                           for(Player player2 : playerchatevent.getRecipients()) {
                              player2.sendMessage(s);
                           }
                        }

                        return null;
                     }
                  }
               };
               if(p_chat_2_) {
                  this.minecraftServer.processQueue.add(waitable);
               } else {
                  waitable.run();
               }

               try {
                  waitable.get();
               } catch (InterruptedException var8) {
                  Thread.currentThread().interrupt();
               } catch (ExecutionException executionexception) {
                  throw new RuntimeException("Exception processing chat event", executionexception.getCause());
               }
            } else {
               if(asyncplayerchatevent.isCancelled()) {
                  return;
               }

               p_chat_1_ = String.format(asyncplayerchatevent.getFormat(), new Object[]{asyncplayerchatevent.getPlayer().getDisplayName(), asyncplayerchatevent.getMessage()});
               this.minecraftServer.console.sendMessage(p_chat_1_);
               if(((LazyPlayerSet)asyncplayerchatevent.getRecipients()).isLazy()) {
                  for(Object object : this.minecraftServer.getPlayerList().players) {
                     ((EntityPlayer)object).sendMessage(CraftChatMessage.fromString(p_chat_1_));
                  }
               } else {
                  for(Player player1 : asyncplayerchatevent.getRecipients()) {
                     player1.sendMessage(p_chat_1_);
                  }
               }
            }
         }

      }
   }

   private void handleCommand(String p_handleCommand_1_) {
      SpigotTimings.playerCommandTimer.startTiming();
      if(SpigotConfig.logCommands) {
         c.info(this.player.getName() + " issued server command: " + p_handleCommand_1_);
      }

      CraftPlayer craftplayer = this.getPlayer();
      PlayerCommandPreprocessEvent playercommandpreprocessevent = new PlayerCommandPreprocessEvent(craftplayer, p_handleCommand_1_, new LazyPlayerSet());
      this.server.getPluginManager().callEvent(playercommandpreprocessevent);
      if(playercommandpreprocessevent.isCancelled()) {
         SpigotTimings.playerCommandTimer.stopTiming();
      } else {
         try {
            if(this.server.dispatchCommand(playercommandpreprocessevent.getPlayer(), playercommandpreprocessevent.getMessage().substring(1))) {
               SpigotTimings.playerCommandTimer.stopTiming();
               return;
            }
         } catch (org.bukkit.command.CommandException commandexception) {
            craftplayer.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
            java.util.logging.Logger.getLogger(PlayerConnection.class.getName()).log(Level.SEVERE, (String)null, commandexception);
            SpigotTimings.playerCommandTimer.stopTiming();
            return;
         }

         SpigotTimings.playerCommandTimer.stopTiming();
      }
   }

   public void a(PacketPlayInArmAnimation p_a_1_) {
      if(!this.player.dead) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
         this.player.resetIdleTimer();
         float f = this.player.pitch;
         float f1 = this.player.yaw;
         double d0 = this.player.locX;
         double d1 = this.player.locY + (double)this.player.getHeadHeight();
         double d2 = this.player.locZ;
         Vec3D vec3d = new Vec3D(d0, d1, d2);
         float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
         float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
         float f4 = -MathHelper.cos(-f * 0.017453292F);
         float f5 = MathHelper.sin(-f * 0.017453292F);
         float f6 = f3 * f4;
         float f7 = f2 * f4;
         double d3 = this.player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE?5.0D:4.5D;
         Vec3D vec3d1 = vec3d.add((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
         MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d1, false);
         if(movingobjectposition == null || movingobjectposition.type != MovingObjectPosition.EnumMovingObjectType.BLOCK) {
            CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_AIR, this.player.inventory.getItemInHand());
         }

         PlayerAnimationEvent playeranimationevent = new PlayerAnimationEvent(this.getPlayer());
         this.server.getPluginManager().callEvent(playeranimationevent);
         if(!playeranimationevent.isCancelled()) {
            this.player.bw();
         }
      }
   }

   public void a(PacketPlayInEntityAction p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      if(!this.player.dead) {
         switch($SWITCH_TABLE$net$minecraft$server$PacketPlayInEntityAction$EnumPlayerAction()[p_a_1_.b().ordinal()]) {
         case 1:
         case 2:
            PlayerToggleSneakEvent playertogglesneakevent = new PlayerToggleSneakEvent(this.getPlayer(), p_a_1_.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING);
            this.server.getPluginManager().callEvent(playertogglesneakevent);
            if(playertogglesneakevent.isCancelled()) {
               return;
            }
         case 3:
         default:
            break;
         case 4:
         case 5:
            PlayerToggleSprintEvent playertogglesprintevent = new PlayerToggleSprintEvent(this.getPlayer(), p_a_1_.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING);
            this.server.getPluginManager().callEvent(playertogglesprintevent);
            if(playertogglesprintevent.isCancelled()) {
               return;
            }
         }

         this.player.resetIdleTimer();
         switch(PlayerConnection.SyntheticClass_1.b[p_a_1_.b().ordinal()]) {
         case 1:
            this.player.setSneaking(true);
            break;
         case 2:
            this.player.setSneaking(false);
            break;
         case 3:
            this.player.setSprinting(true);
            break;
         case 4:
            this.player.setSprinting(false);
            break;
         case 5:
            this.player.a(false, true, true);
            break;
         case 6:
            if(this.player.vehicle instanceof EntityHorse) {
               ((EntityHorse)this.player.vehicle).v(p_a_1_.c());
            }
            break;
         case 7:
            if(this.player.vehicle instanceof EntityHorse) {
               ((EntityHorse)this.player.vehicle).g(this.player);
            }
            break;
         default:
            throw new IllegalArgumentException("Invalid client command!");
         }

      }
   }

   public void a(PacketPlayInUseEntity p_a_1_) {
      if(!this.player.dead) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
         WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
         Entity entity = p_a_1_.a((World)worldserver);
         if(entity == this.player && !this.player.isSpectator()) {
            this.disconnect("Cannot interact with self!");
         } else {
            this.player.resetIdleTimer();
            if(entity != null) {
               boolean flag = this.player.hasLineOfSight(entity);
               double d0 = 36.0D;
               if(!flag) {
                  d0 = 9.0D;
               }

               if(this.player.h(entity) < d0) {
                  ItemStack itemstack = this.player.inventory.getItemInHand();
                  if(p_a_1_.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT || p_a_1_.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
                     boolean flag1 = itemstack != null && itemstack.getItem() == Items.LEAD && entity instanceof EntityInsentient;
                     Item item = this.player.inventory.getItemInHand() == null?null:this.player.inventory.getItemInHand().getItem();
                     PlayerInteractEntityEvent playerinteractentityevent;
                     if(p_a_1_.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
                        playerinteractentityevent = new PlayerInteractEntityEvent(this.getPlayer(), entity.getBukkitEntity());
                     } else {
                        Vec3D vec3d = p_a_1_.b();
                        playerinteractentityevent = new PlayerInteractAtEntityEvent(this.getPlayer(), entity.getBukkitEntity(), new Vector(vec3d.a, vec3d.b, vec3d.c));
                     }

                     this.server.getPluginManager().callEvent(playerinteractentityevent);
                     if(flag1 && (playerinteractentityevent.isCancelled() || this.player.inventory.getItemInHand() == null || this.player.inventory.getItemInHand().getItem() != Items.LEAD)) {
                        this.sendPacket(new PacketPlayOutAttachEntity(1, entity, ((EntityInsentient)entity).getLeashHolder()));
                     }

                     if(playerinteractentityevent.isCancelled() || this.player.inventory.getItemInHand() == null || this.player.inventory.getItemInHand().getItem() != item) {
                        this.sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.datawatcher, true));
                     }

                     if(playerinteractentityevent.isCancelled()) {
                        return;
                     }
                  }

                  if(p_a_1_.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
                     this.player.u(entity);
                     if(itemstack != null && itemstack.count <= -1) {
                        this.player.updateInventory(this.player.activeContainer);
                     }
                  } else if(p_a_1_.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
                     entity.a((EntityHuman)this.player, (Vec3D)p_a_1_.b());
                     if(itemstack != null && itemstack.count <= -1) {
                        this.player.updateInventory(this.player.activeContainer);
                     }
                  } else if(p_a_1_.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                     if(entity instanceof EntityItem || entity instanceof EntityExperienceOrb || entity instanceof EntityArrow || entity == this.player && !this.player.isSpectator()) {
                        this.disconnect("Attempting to attack an invalid entity");
                        this.minecraftServer.warning("Player " + this.player.getName() + " tried to attack an invalid entity");
                        return;
                     }

                     this.player.attack(entity);
                     if(itemstack != null && itemstack.count <= -1) {
                        this.player.updateInventory(this.player.activeContainer);
                     }
                  }
               }
            }

         }
      }
   }

   public void a(PacketPlayInClientCommand p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      this.player.resetIdleTimer();
      PacketPlayInClientCommand.EnumClientCommand packetplayinclientcommand$enumclientcommand = p_a_1_.a();
      switch(PlayerConnection.SyntheticClass_1.c[packetplayinclientcommand$enumclientcommand.ordinal()]) {
      case 1:
         if(this.player.viewingCredits) {
            this.minecraftServer.getPlayerList().changeDimension(this.player, 0, TeleportCause.END_PORTAL);
         } else if(this.player.u().getWorldData().isHardcore()) {
            if(this.minecraftServer.T() && this.player.getName().equals(this.minecraftServer.S())) {
               this.player.playerConnection.disconnect("You have died. Game over, man, it\'s game over!");
               this.minecraftServer.aa();
            } else {
               GameProfileBanEntry gameprofilebanentry = new GameProfileBanEntry(this.player.getProfile(), (Date)null, "(You just lost the game)", (Date)null, "Death in Hardcore");
               this.minecraftServer.getPlayerList().getProfileBans().add(gameprofilebanentry);
               this.player.playerConnection.disconnect("You have died. Game over, man, it\'s game over!");
            }
         } else {
            if(this.player.getHealth() > 0.0F) {
               return;
            }

            this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, false);
         }
         break;
      case 2:
         this.player.getStatisticManager().a(this.player);
         break;
      case 3:
         this.player.b((Statistic)AchievementList.f);
      }

   }

   public void a(PacketPlayInCloseWindow p_a_1_) {
      if(!this.player.dead) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
         CraftEventFactory.handleInventoryCloseEvent(this.player);
         this.player.p();
      }
   }

   public void a(PacketPlayInWindowClick p_a_1_) {
      if(!this.player.dead) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
         this.player.resetIdleTimer();
         if(this.player.activeContainer.windowId == p_a_1_.a() && this.player.activeContainer.c(this.player)) {
            boolean flag = this.player.isSpectator();
            if(p_a_1_.b() < -1 && p_a_1_.b() != -999) {
               return;
            }

            InventoryView inventoryview = this.player.activeContainer.getBukkitView();
            SlotType slottype = CraftInventoryView.getSlotType(inventoryview, p_a_1_.b());
            InventoryClickEvent inventoryclickevent = null;
            ClickType clicktype = ClickType.UNKNOWN;
            InventoryAction inventoryaction = InventoryAction.UNKNOWN;
            ItemStack itemstack = null;
            if(p_a_1_.b() == -1) {
               slottype = SlotType.OUTSIDE;
               clicktype = p_a_1_.c() == 0?ClickType.WINDOW_BORDER_LEFT:ClickType.WINDOW_BORDER_RIGHT;
               inventoryaction = InventoryAction.NOTHING;
            } else if(p_a_1_.f() == 0) {
               if(p_a_1_.c() == 0) {
                  clicktype = ClickType.LEFT;
               } else if(p_a_1_.c() == 1) {
                  clicktype = ClickType.RIGHT;
               }

               if(p_a_1_.c() == 0 || p_a_1_.c() == 1) {
                  inventoryaction = InventoryAction.NOTHING;
                  if(p_a_1_.b() == -999) {
                     if(this.player.inventory.getCarried() != null) {
                        inventoryaction = p_a_1_.c() == 0?InventoryAction.DROP_ALL_CURSOR:InventoryAction.DROP_ONE_CURSOR;
                     }
                  } else {
                     Slot slot5 = this.player.activeContainer.getSlot(p_a_1_.b());
                     if(slot5 != null) {
                        ItemStack itemstack3 = slot5.getItem();
                        ItemStack itemstack4 = this.player.inventory.getCarried();
                        if(itemstack3 == null) {
                           if(itemstack4 != null) {
                              inventoryaction = p_a_1_.c() == 0?InventoryAction.PLACE_ALL:InventoryAction.PLACE_ONE;
                           }
                        } else if(slot5.isAllowed((EntityHuman)this.player)) {
                           if(itemstack4 == null) {
                              inventoryaction = p_a_1_.c() == 0?InventoryAction.PICKUP_ALL:InventoryAction.PICKUP_HALF;
                           } else if(slot5.isAllowed(itemstack4)) {
                              if(itemstack3.doMaterialsMatch(itemstack4) && ItemStack.equals(itemstack3, itemstack4)) {
                                 int k = p_a_1_.c() == 0?itemstack4.count:1;
                                 k = Math.min(k, itemstack3.getMaxStackSize() - itemstack3.count);
                                 k = Math.min(k, slot5.inventory.getMaxStackSize() - itemstack3.count);
                                 if(k == 1) {
                                    inventoryaction = InventoryAction.PLACE_ONE;
                                 } else if(k == itemstack4.count) {
                                    inventoryaction = InventoryAction.PLACE_ALL;
                                 } else if(k < 0) {
                                    inventoryaction = k != -1?InventoryAction.PICKUP_SOME:InventoryAction.PICKUP_ONE;
                                 } else if(k != 0) {
                                    inventoryaction = InventoryAction.PLACE_SOME;
                                 }
                              } else if(itemstack4.count <= slot5.getMaxStackSize()) {
                                 inventoryaction = InventoryAction.SWAP_WITH_CURSOR;
                              }
                           } else if(itemstack4.getItem() == itemstack3.getItem() && (!itemstack4.usesData() || itemstack4.getData() == itemstack3.getData()) && ItemStack.equals(itemstack4, itemstack3) && itemstack3.count >= 0 && itemstack3.count + itemstack4.count <= itemstack4.getMaxStackSize()) {
                              inventoryaction = InventoryAction.PICKUP_ALL;
                           }
                        }
                     }
                  }
               }
            } else if(p_a_1_.f() == 1) {
               if(p_a_1_.c() == 0) {
                  clicktype = ClickType.SHIFT_LEFT;
               } else if(p_a_1_.c() == 1) {
                  clicktype = ClickType.SHIFT_RIGHT;
               }

               if(p_a_1_.c() == 0 || p_a_1_.c() == 1) {
                  if(p_a_1_.b() < 0) {
                     inventoryaction = InventoryAction.NOTHING;
                  } else {
                     Slot slot4 = this.player.activeContainer.getSlot(p_a_1_.b());
                     if(slot4 != null && slot4.isAllowed((EntityHuman)this.player) && slot4.hasItem()) {
                        inventoryaction = InventoryAction.MOVE_TO_OTHER_INVENTORY;
                     } else {
                        inventoryaction = InventoryAction.NOTHING;
                     }
                  }
               }
            } else if(p_a_1_.f() == 2) {
               if(p_a_1_.c() >= 0 && p_a_1_.c() < 9) {
                  clicktype = ClickType.NUMBER_KEY;
                  Slot slot3 = this.player.activeContainer.getSlot(p_a_1_.b());
                  if(slot3.isAllowed((EntityHuman)this.player)) {
                     ItemStack itemstack2 = this.player.inventory.getItem(p_a_1_.c());
                     boolean flag1 = itemstack2 == null || slot3.inventory == this.player.inventory && slot3.isAllowed(itemstack2);
                     if(slot3.hasItem()) {
                        if(flag1) {
                           inventoryaction = InventoryAction.HOTBAR_SWAP;
                        } else {
                           int i = this.player.inventory.getFirstEmptySlotIndex();
                           if(i > -1) {
                              inventoryaction = InventoryAction.HOTBAR_MOVE_AND_READD;
                           } else {
                              inventoryaction = InventoryAction.NOTHING;
                           }
                        }
                     } else if(!slot3.hasItem() && itemstack2 != null && slot3.isAllowed(itemstack2)) {
                        inventoryaction = InventoryAction.HOTBAR_SWAP;
                     } else {
                        inventoryaction = InventoryAction.NOTHING;
                     }
                  } else {
                     inventoryaction = InventoryAction.NOTHING;
                  }

                  new InventoryClickEvent(inventoryview, slottype, p_a_1_.b(), clicktype, inventoryaction, p_a_1_.c());
               }
            } else if(p_a_1_.f() == 3) {
               if(p_a_1_.c() == 2) {
                  clicktype = ClickType.MIDDLE;
                  if(p_a_1_.b() == -999) {
                     inventoryaction = InventoryAction.NOTHING;
                  } else {
                     Slot slot2 = this.player.activeContainer.getSlot(p_a_1_.b());
                     if(slot2 != null && slot2.hasItem() && this.player.abilities.canInstantlyBuild && this.player.inventory.getCarried() == null) {
                        inventoryaction = InventoryAction.CLONE_STACK;
                     } else {
                        inventoryaction = InventoryAction.NOTHING;
                     }
                  }
               } else {
                  clicktype = ClickType.UNKNOWN;
                  inventoryaction = InventoryAction.UNKNOWN;
               }
            } else if(p_a_1_.f() == 4) {
               if(p_a_1_.b() >= 0) {
                  if(p_a_1_.c() == 0) {
                     clicktype = ClickType.DROP;
                     Slot slot1 = this.player.activeContainer.getSlot(p_a_1_.b());
                     if(slot1 != null && slot1.hasItem() && slot1.isAllowed((EntityHuman)this.player) && slot1.getItem() != null && slot1.getItem().getItem() != Item.getItemOf(Blocks.AIR)) {
                        inventoryaction = InventoryAction.DROP_ONE_SLOT;
                     } else {
                        inventoryaction = InventoryAction.NOTHING;
                     }
                  } else if(p_a_1_.c() == 1) {
                     clicktype = ClickType.CONTROL_DROP;
                     Slot slot = this.player.activeContainer.getSlot(p_a_1_.b());
                     if(slot != null && slot.hasItem() && slot.isAllowed((EntityHuman)this.player) && slot.getItem() != null && slot.getItem().getItem() != Item.getItemOf(Blocks.AIR)) {
                        inventoryaction = InventoryAction.DROP_ALL_SLOT;
                     } else {
                        inventoryaction = InventoryAction.NOTHING;
                     }
                  }
               } else {
                  clicktype = ClickType.LEFT;
                  if(p_a_1_.c() == 1) {
                     clicktype = ClickType.RIGHT;
                  }

                  inventoryaction = InventoryAction.NOTHING;
               }
            } else if(p_a_1_.f() == 5) {
               itemstack = this.player.activeContainer.clickItem(p_a_1_.b(), p_a_1_.c(), 5, this.player);
            } else if(p_a_1_.f() == 6) {
               clicktype = ClickType.DOUBLE_CLICK;
               inventoryaction = InventoryAction.NOTHING;
               if(p_a_1_.b() >= 0 && this.player.inventory.getCarried() != null) {
                  ItemStack itemstack1 = this.player.inventory.getCarried();
                  inventoryaction = InventoryAction.NOTHING;
                  if(inventoryview.getTopInventory().contains(org.bukkit.Material.getMaterial(Item.getId(itemstack1.getItem()))) || inventoryview.getBottomInventory().contains(org.bukkit.Material.getMaterial(Item.getId(itemstack1.getItem())))) {
                     inventoryaction = InventoryAction.COLLECT_TO_CURSOR;
                  }
               }
            }

            if(p_a_1_.f() != 5) {
               if(clicktype == ClickType.NUMBER_KEY) {
                  inventoryclickevent = new InventoryClickEvent(inventoryview, slottype, p_a_1_.b(), clicktype, inventoryaction, p_a_1_.c());
               } else {
                  inventoryclickevent = new InventoryClickEvent(inventoryview, slottype, p_a_1_.b(), clicktype, inventoryaction);
               }

               Inventory inventory = inventoryview.getTopInventory();
               if(p_a_1_.b() == 0 && inventory instanceof CraftingInventory) {
                  Recipe recipe = ((CraftingInventory)inventory).getRecipe();
                  if(recipe != null) {
                     if(clicktype == ClickType.NUMBER_KEY) {
                        inventoryclickevent = new CraftItemEvent(recipe, inventoryview, slottype, p_a_1_.b(), clicktype, inventoryaction, p_a_1_.c());
                     } else {
                        inventoryclickevent = new CraftItemEvent(recipe, inventoryview, slottype, p_a_1_.b(), clicktype, inventoryaction);
                     }
                  }
               }

               inventoryclickevent.setCancelled(flag);
               this.server.getPluginManager().callEvent(inventoryclickevent);
               switch($SWITCH_TABLE$org$bukkit$event$Event$Result()[inventoryclickevent.getResult().ordinal()]) {
               case 1:
                  switch($SWITCH_TABLE$org$bukkit$event$inventory$InventoryAction()[inventoryaction.ordinal()]) {
                  case 1:
                  default:
                     break;
                  case 2:
                  case 14:
                  case 15:
                  case 16:
                  case 18:
                  case 19:
                     this.player.updateInventory(this.player.activeContainer);
                     break;
                  case 3:
                  case 4:
                  case 5:
                  case 6:
                  case 7:
                  case 8:
                  case 9:
                     this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.player.inventory.getCarried()));
                     this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, p_a_1_.b(), this.player.activeContainer.getSlot(p_a_1_.b()).getItem()));
                     break;
                  case 10:
                  case 11:
                  case 17:
                     this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.player.inventory.getCarried()));
                     break;
                  case 12:
                  case 13:
                     this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, p_a_1_.b(), this.player.activeContainer.getSlot(p_a_1_.b()).getItem()));
                  }

                  return;
               case 2:
               case 3:
                  itemstack = this.player.activeContainer.clickItem(p_a_1_.b(), p_a_1_.c(), p_a_1_.f(), this.player);
               default:
                  if(inventoryclickevent instanceof CraftItemEvent) {
                     this.player.updateInventory(this.player.activeContainer);
                  }
               }
            }

            if(ItemStack.matches(p_a_1_.e(), itemstack)) {
               this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(p_a_1_.a(), p_a_1_.d(), true));
               this.player.g = true;
               this.player.activeContainer.b();
               this.player.broadcastCarriedItem();
               this.player.g = false;
            } else {
               this.n.a(this.player.activeContainer.windowId, Short.valueOf(p_a_1_.d()));
               this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(p_a_1_.a(), p_a_1_.d(), false));
               this.player.activeContainer.a(this.player, false);
               ArrayList arraylist = Lists.newArrayList();

               for(int j = 0; j < this.player.activeContainer.c.size(); ++j) {
                  arraylist.add(((Slot)this.player.activeContainer.c.get(j)).getItem());
               }

               this.player.a(this.player.activeContainer, arraylist);
            }
         }

      }
   }

   public void a(PacketPlayInEnchantItem p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      this.player.resetIdleTimer();
      if(this.player.activeContainer.windowId == p_a_1_.a() && this.player.activeContainer.c(this.player) && !this.player.isSpectator()) {
         this.player.activeContainer.a(this.player, p_a_1_.b());
         this.player.activeContainer.b();
      }

   }

   public void a(PacketPlayInSetCreativeSlot p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      if(this.player.playerInteractManager.isCreative()) {
         boolean flag = p_a_1_.a() < 0;
         ItemStack itemstack = p_a_1_.getItemStack();
         if(itemstack != null && itemstack.hasTag() && itemstack.getTag().hasKeyOfType("BlockEntityTag", 10)) {
            NBTTagCompound nbttagcompound = itemstack.getTag().getCompound("BlockEntityTag");
            if(nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z")) {
               BlockPosition blockposition = new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z"));
               TileEntity tileentity = this.player.world.getTileEntity(blockposition);
               if(tileentity != null) {
                  NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                  tileentity.b(nbttagcompound1);
                  nbttagcompound1.remove("x");
                  nbttagcompound1.remove("y");
                  nbttagcompound1.remove("z");
                  itemstack.a((String)"BlockEntityTag", (NBTBase)nbttagcompound1);
               }
            }
         }

         boolean flag1 = p_a_1_.a() >= 1 && p_a_1_.a() < 36 + PlayerInventory.getHotbarSize();
         boolean flag2 = itemstack == null || itemstack.getItem() != null && (!invalidItems.contains(Integer.valueOf(Item.getId(itemstack.getItem()))) || !SpigotConfig.filterCreativeItems);
         boolean flag3 = itemstack == null || itemstack.getData() >= 0 && itemstack.count <= 64 && itemstack.count > 0;
         if(flag || flag1 && !ItemStack.matches(this.player.defaultContainer.getSlot(p_a_1_.a()).getItem(), p_a_1_.getItemStack())) {
            HumanEntity humanentity = this.player.getBukkitEntity();
            InventoryView inventoryview = new CraftInventoryView(humanentity, humanentity.getInventory(), this.player.defaultContainer);
            org.bukkit.inventory.ItemStack itemstack1 = CraftItemStack.asBukkitCopy(p_a_1_.getItemStack());
            SlotType slottype = SlotType.QUICKBAR;
            if(flag) {
               slottype = SlotType.OUTSIDE;
            } else if(p_a_1_.a() < 36) {
               if(p_a_1_.a() >= 5 && p_a_1_.a() < 9) {
                  slottype = SlotType.ARMOR;
               } else {
                  slottype = SlotType.CONTAINER;
               }
            }

            InventoryCreativeEvent inventorycreativeevent = new InventoryCreativeEvent(inventoryview, slottype, flag?-999:p_a_1_.a(), itemstack1);
            this.server.getPluginManager().callEvent(inventorycreativeevent);
            itemstack = CraftItemStack.asNMSCopy(inventorycreativeevent.getCursor());
            switch($SWITCH_TABLE$org$bukkit$event$Event$Result()[inventorycreativeevent.getResult().ordinal()]) {
            case 1:
               if(p_a_1_.a() >= 0) {
                  this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.defaultContainer.windowId, p_a_1_.a(), this.player.defaultContainer.getSlot(p_a_1_.a()).getItem()));
                  this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, (ItemStack)null));
               }

               return;
            case 2:
            default:
               break;
            case 3:
               flag3 = true;
               flag2 = true;
            }
         }

         if(flag1 && flag2 && flag3) {
            if(itemstack == null) {
               this.player.defaultContainer.setItem(p_a_1_.a(), (ItemStack)null);
            } else {
               this.player.defaultContainer.setItem(p_a_1_.a(), itemstack);
            }

            this.player.defaultContainer.a(this.player, true);
         } else if(flag && flag2 && flag3 && this.m < 200) {
            this.m += 20;
            EntityItem entityitem = this.player.drop(itemstack, true);
            if(entityitem != null) {
               entityitem.j();
            }
         }
      }

   }

   public void a(PacketPlayInTransaction p_a_1_) {
      if(!this.player.dead) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
         Short oshort = (Short)this.n.get(this.player.activeContainer.windowId);
         if(oshort != null && p_a_1_.b() == oshort.shortValue() && this.player.activeContainer.windowId == p_a_1_.a() && !this.player.activeContainer.c(this.player) && !this.player.isSpectator()) {
            this.player.activeContainer.a(this.player, true);
         }

      }
   }

   public void a(PacketPlayInUpdateSign p_a_1_) {
      if(!this.player.dead) {
         PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
         this.player.resetIdleTimer();
         WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
         BlockPosition blockposition = p_a_1_.a();
         if(worldserver.isLoaded(blockposition)) {
            TileEntity tileentity = worldserver.getTileEntity(blockposition);
            if(!(tileentity instanceof TileEntitySign)) {
               return;
            }

            TileEntitySign tileentitysign = (TileEntitySign)tileentity;
            if(!tileentitysign.b() || tileentitysign.c() != this.player) {
               this.minecraftServer.warning("Player " + this.player.getName() + " just tried to change non-editable sign");
               this.sendPacket(new PacketPlayOutUpdateSign(tileentity.world, p_a_1_.a(), tileentitysign.lines));
               return;
            }

            IChatBaseComponent[] aichatbasecomponent = p_a_1_.b();
            Player player = this.server.getPlayer(this.player);
            int i = p_a_1_.a().getX();
            int j = p_a_1_.a().getY();
            int k = p_a_1_.a().getZ();
            String[] astring = new String[4];

            for(int l = 0; l < aichatbasecomponent.length; ++l) {
               astring[l] = EnumChatFormat.a(aichatbasecomponent[l].c());
            }

            SignChangeEvent signchangeevent = new SignChangeEvent((CraftBlock)player.getWorld().getBlockAt(i, j, k), this.server.getPlayer(this.player), astring);
            this.server.getPluginManager().callEvent(signchangeevent);
            if(!signchangeevent.isCancelled()) {
               System.arraycopy(CraftSign.sanitizeLines(signchangeevent.getLines()), 0, tileentitysign.lines, 0, 4);
               tileentitysign.isEditable = false;
            }

            tileentitysign.update();
            worldserver.notify(blockposition);
         }

      }
   }

   public void a(PacketPlayInKeepAlive p_a_1_) {
      if(p_a_1_.a() == this.i) {
         int i = (int)(this.d() - this.j);
         this.player.ping = (this.player.ping * 3 + i) / 4;
      }

   }

   private long d() {
      return System.nanoTime() / 1000000L;
   }

   public void a(PacketPlayInAbilities p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      if(this.player.abilities.canFly && this.player.abilities.isFlying != p_a_1_.isFlying()) {
         PlayerToggleFlightEvent playertoggleflightevent = new PlayerToggleFlightEvent(this.server.getPlayer(this.player), p_a_1_.isFlying());
         this.server.getPluginManager().callEvent(playertoggleflightevent);
         if(!playertoggleflightevent.isCancelled()) {
            this.player.abilities.isFlying = p_a_1_.isFlying();
         } else {
            this.player.updateAbilities();
         }
      }

   }

   public void a(PacketPlayInTabComplete p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      if(chatSpamField.addAndGet(this, 10) > 500 && !this.minecraftServer.getPlayerList().isOp(this.player.getProfile())) {
         this.disconnect("disconnect.spam");
      } else {
         ArrayList arraylist = Lists.newArrayList();

         for(String s : this.minecraftServer.tabCompleteCommand(this.player, p_a_1_.a(), p_a_1_.b())) {
            arraylist.add(s);
         }

         this.player.playerConnection.sendPacket(new PacketPlayOutTabComplete((String[])arraylist.toArray(new String[arraylist.size()])));
      }
   }

   public void a(PacketPlayInSettings p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());
      this.player.a(p_a_1_);
   }

   public void a(PacketPlayInCustomPayload p_a_1_) {
      PlayerConnectionUtils.ensureMainThread(p_a_1_, this, this.player.u());

      try {
         if(!"MC|BEdit".equals(p_a_1_.a())) {
            if("MC|BSign".equals(p_a_1_.a())) {
               PacketDataSerializer packetdataserializer3 = new PacketDataSerializer(Unpooled.wrappedBuffer((ByteBuf)p_a_1_.b()));

               try {
                  ItemStack itemstack2 = packetdataserializer3.i();
                  if(itemstack2 != null) {
                     if(!ItemWrittenBook.b(itemstack2.getTag())) {
                        throw new IOException("Invalid book tag!");
                     }

                     ItemStack itemstack3 = this.player.inventory.getItemInHand();
                     if(itemstack3 == null) {
                        return;
                     }

                     if(itemstack2.getItem() == Items.WRITTEN_BOOK && itemstack3.getItem() == Items.WRITABLE_BOOK) {
                        itemstack3 = new ItemStack(Items.WRITTEN_BOOK);
                        itemstack3.a((String)"author", (NBTBase)(new NBTTagString(this.player.getName())));
                        itemstack3.a((String)"title", (NBTBase)(new NBTTagString(itemstack2.getTag().getString("title"))));
                        itemstack3.a((String)"pages", (NBTBase)itemstack2.getTag().getList("pages", 8));
                        itemstack3.setItem(Items.WRITTEN_BOOK);
                        CraftEventFactory.handleEditBookEvent(this.player, itemstack3);
                     }

                     return;
                  }

                  return;
               } catch (Exception exception3) {
                  c.error((String)"Couldn\'t sign book", (Throwable)exception3);
                  this.disconnect("Invalid book data!");
                  return;
               } finally {
                  packetdataserializer3.release();
               }
            }

            if("MC|TrSel".equals(p_a_1_.a())) {
               try {
                  int i = p_a_1_.b().readInt();
                  Container container = this.player.activeContainer;
                  if(container instanceof ContainerMerchant) {
                     ((ContainerMerchant)container).d(i);
                     return;
                  }
               } catch (Exception exception2) {
                  c.error((String)"Couldn\'t select trade", (Throwable)exception2);
                  this.disconnect("Invalid trade data!");
               }

               return;
            } else if("MC|AdvCdm".equals(p_a_1_.a())) {
               if(!this.minecraftServer.getEnableCommandBlock()) {
                  this.player.sendMessage((IChatBaseComponent)(new ChatMessage("advMode.notEnabled", new Object[0])));
                  return;
               } else if(this.player.getBukkitEntity().isOp() && this.player.abilities.canInstantlyBuild) {
                  PacketDataSerializer packetdataserializer1 = p_a_1_.b();

                  try {
                     try {
                        byte b0 = packetdataserializer1.readByte();
                        CommandBlockListenerAbstract commandblocklistenerabstract = null;
                        if(b0 == 0) {
                           TileEntity tileentity = this.player.world.getTileEntity(new BlockPosition(packetdataserializer1.readInt(), packetdataserializer1.readInt(), packetdataserializer1.readInt()));
                           if(tileentity instanceof TileEntityCommand) {
                              commandblocklistenerabstract = ((TileEntityCommand)tileentity).getCommandBlock();
                           }
                        } else if(b0 == 1) {
                           Entity entity = this.player.world.a(packetdataserializer1.readInt());
                           if(entity instanceof EntityMinecartCommandBlock) {
                              commandblocklistenerabstract = ((EntityMinecartCommandBlock)entity).getCommandBlock();
                           }
                        }

                        String s5 = packetdataserializer1.c(packetdataserializer1.readableBytes());
                        boolean flag = packetdataserializer1.readBoolean();
                        if(commandblocklistenerabstract != null) {
                           commandblocklistenerabstract.setCommand(s5);
                           commandblocklistenerabstract.a(flag);
                           if(!flag) {
                              commandblocklistenerabstract.b((IChatBaseComponent)null);
                           }

                           commandblocklistenerabstract.h();
                           this.player.sendMessage((IChatBaseComponent)(new ChatMessage("advMode.setCommand.success", new Object[]{s5})));
                           return;
                        }
                     } catch (Exception exception1) {
                        c.error((String)"Couldn\'t set command block", (Throwable)exception1);
                        this.disconnect("Invalid CommandBlock data!");
                     }

                     return;
                  } finally {
                     packetdataserializer1.release();
                  }
               } else {
                  this.player.sendMessage((IChatBaseComponent)(new ChatMessage("advMode.notAllowed", new Object[0])));
                  return;
               }
            } else if("MC|Beacon".equals(p_a_1_.a())) {
               if(this.player.activeContainer instanceof ContainerBeacon) {
                  try {
                     PacketDataSerializer packetdataserializer2 = p_a_1_.b();
                     int j = packetdataserializer2.readInt();
                     int k = packetdataserializer2.readInt();
                     ContainerBeacon containerbeacon = (ContainerBeacon)this.player.activeContainer;
                     Slot slot = containerbeacon.getSlot(0);
                     if(slot.hasItem()) {
                        slot.a(1);
                        IInventory iinventory = containerbeacon.e();
                        iinventory.b(1, j);
                        iinventory.b(2, k);
                        iinventory.update();
                        return;
                     }
                  } catch (Exception exception1) {
                     c.error((String)"Couldn\'t set beacon", (Throwable)exception1);
                     this.disconnect("Invalid beacon data!");
                  }

                  return;
               }

               return;
            } else if("MC|ItemName".equals(p_a_1_.a()) && this.player.activeContainer instanceof ContainerAnvil) {
               ContainerAnvil containeranvil = (ContainerAnvil)this.player.activeContainer;
               if(p_a_1_.b() != null && p_a_1_.b().readableBytes() >= 1) {
                  String s4 = SharedConstants.a(p_a_1_.b().c(32767));
                  if(s4.length() <= 30) {
                     containeranvil.a(s4);
                     return;
                  }
               } else {
                  containeranvil.a("");
               }

               return;
            } else if(p_a_1_.a().equals("REGISTER")) {
               String s = p_a_1_.b().toString(Charsets.UTF_8);

               String[] astring;
               for(String s2 : astring = s.split("\u0000")) {
                  this.getPlayer().addChannel(s2);
               }

               return;
            } else if(p_a_1_.a().equals("UNREGISTER")) {
               String s1 = p_a_1_.b().toString(Charsets.UTF_8);

               String[] astring1;
               for(String s3 : astring1 = s1.split("\u0000")) {
                  this.getPlayer().removeChannel(s3);
               }

               return;
            } else {
               byte[] abyte = new byte[p_a_1_.b().readableBytes()];
               p_a_1_.b().readBytes(abyte);
               this.server.getMessenger().dispatchIncomingMessage(this.player.getBukkitEntity(), p_a_1_.a(), abyte);
               return;
            }
         }

         PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.wrappedBuffer((ByteBuf)p_a_1_.b()));

         try {
            ItemStack itemstack = packetdataserializer.i();
            if(itemstack != null) {
               if(!ItemBookAndQuill.b(itemstack.getTag())) {
                  throw new IOException("Invalid book tag!");
               }

               ItemStack itemstack1 = this.player.inventory.getItemInHand();
               if(itemstack1 == null) {
                  return;
               }

               if(itemstack.getItem() == Items.WRITABLE_BOOK && itemstack.getItem() == itemstack1.getItem()) {
                  itemstack1 = new ItemStack(Items.WRITABLE_BOOK);
                  itemstack1.a((String)"pages", (NBTBase)itemstack.getTag().getList("pages", 8));
                  CraftEventFactory.handleEditBookEvent(this.player, itemstack1);
               }

               return;
            }
         } catch (Exception exception4) {
            c.error((String)"Couldn\'t handle book info", (Throwable)exception4);
            this.disconnect("Invalid book data!");
            return;
         } finally {
            packetdataserializer.release();
         }
      } finally {
         if(p_a_1_.b().refCnt() > 0) {
            p_a_1_.b().release();
         }

      }

   }

   public boolean isDisconnected() {
      return !this.player.joining && !this.networkManager.channel.config().isAutoRead();
   }

   static class SyntheticClass_1 {
      static final int[] a;
      static final int[] b;
      static final int[] c = new int[PacketPlayInClientCommand.EnumClientCommand.values().length];

      static {
         try {
            c[PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN.ordinal()] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            c[PacketPlayInClientCommand.EnumClientCommand.REQUEST_STATS.ordinal()] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            c[PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         b = new int[PacketPlayInEntityAction.EnumPlayerAction.values().length];

         try {
            b[PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING.ordinal()] = 1;
         } catch (NoSuchFieldError var12) {
            ;
         }

         try {
            b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING.ordinal()] = 2;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            b[PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING.ordinal()] = 3;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING.ordinal()] = 4;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SLEEPING.ordinal()] = 5;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            b[PacketPlayInEntityAction.EnumPlayerAction.RIDING_JUMP.ordinal()] = 6;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            b[PacketPlayInEntityAction.EnumPlayerAction.OPEN_INVENTORY.ordinal()] = 7;
         } catch (NoSuchFieldError var6) {
            ;
         }

         a = new int[PacketPlayInBlockDig.EnumPlayerDigType.values().length];

         try {
            a[PacketPlayInBlockDig.EnumPlayerDigType.DROP_ITEM.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            a[PacketPlayInBlockDig.EnumPlayerDigType.DROP_ALL_ITEMS.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            a[PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK.ordinal()] = 6;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.security.KeyPair;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.DedicatedPlayerList;
import net.minecraft.server.v1_8_R3.EULA;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.IMinecraftServer;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftEncryption;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MojangStatisticsGenerator;
import net.minecraft.server.v1_8_R3.NameReferencingFileConverter;
import net.minecraft.server.v1_8_R3.PlayerList;
import net.minecraft.server.v1_8_R3.PropertyManager;
import net.minecraft.server.v1_8_R3.RemoteControlCommandListener;
import net.minecraft.server.v1_8_R3.RemoteControlListener;
import net.minecraft.server.v1_8_R3.RemoteStatusListener;
import net.minecraft.server.v1_8_R3.ServerCommand;
import net.minecraft.server.v1_8_R3.ServerGUI;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldLoaderServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import net.minecraft.server.v1_8_R3.WorldType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.craftbukkit.v1_8_R3.LoggerOutputStream;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_8_R3.command.CraftRemoteConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.util.ForwardLogHandler;
import org.bukkit.craftbukkit.v1_8_R3.util.TerminalConsoleWriterThread;
import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.spigotmc.SpigotConfig;

public class DedicatedServer extends MinecraftServer implements IMinecraftServer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<ServerCommand> l = Collections.synchronizedList(Lists.newArrayList());
   private RemoteStatusListener m;
   private RemoteControlListener n;
   public PropertyManager propertyManager;
   private EULA p;
   private boolean generateStructures;
   private WorldSettings.EnumGamemode r;
   private boolean s;

   public DedicatedServer(OptionSet p_i151_1_) {
      super(p_i151_1_, Proxy.NO_PROXY, a);
      Thread thread = new Thread("Server Infinisleeper") {
         {
            this.setDaemon(true);
            this.start();
         }

         public void run() {
            while(true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var1) {
                  ;
               }
            }
         }
      };
   }

   protected boolean init() throws IOException {
      Thread thread = new Thread("Server console handler") {
         public void run() {
            if(Main.useConsole) {
               ConsoleReader consolereader = DedicatedServer.this.reader;

               try {
                  while(!DedicatedServer.this.isStopped() && DedicatedServer.this.isRunning()) {
                     String s4;
                     if(Main.useJline) {
                        s4 = consolereader.readLine(">", (Character)null);
                     } else {
                        s4 = consolereader.readLine();
                     }

                     if(s4 != null && s4.trim().length() > 0) {
                        DedicatedServer.this.issueCommand(s4, DedicatedServer.this);
                     }
                  }
               } catch (IOException ioexception2) {
                  DedicatedServer.LOGGER.error((String)"Exception handling console input", (Throwable)ioexception2);
               }

            }
         }
      };
      java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");
      logger.setUseParentHandlers(false);

      Handler[] inetaddress;
      for(Handler handler : inetaddress = logger.getHandlers()) {
         logger.removeHandler(handler);
      }

      logger.addHandler(new ForwardLogHandler());
      org.apache.logging.log4j.core.Logger logger1 = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();

      for(Appender appender : logger1.getAppenders().values()) {
         if(appender instanceof ConsoleAppender) {
            logger1.removeAppender(appender);
         }
      }

      (new Thread(new TerminalConsoleWriterThread(System.out, this.reader))).start();
      System.setOut(new PrintStream(new LoggerOutputStream(logger1, Level.INFO), true));
      System.setErr(new PrintStream(new LoggerOutputStream(logger1, Level.WARN), true));
      thread.setDaemon(true);
      thread.start();
      LOGGER.info("Starting minecraft server version 1.8.8");
      if(Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
         LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
      }

      LOGGER.info("Loading properties");
      this.propertyManager = new PropertyManager(this.options);
      this.p = new EULA(new File("eula.txt"));
      boolean flag = Boolean.getBoolean("com.mojang.eula.agree");
      if(flag) {
         System.err.println("You have used the Spigot command line EULA agreement flag.");
         System.err.println("By using this setting you are indicating your agreement to Mojang\'s EULA (https://account.mojang.com/documents/minecraft_eula).");
         System.err.println("If you do not agree to the above EULA please stop your server and remove this flag immediately.");
      }

      if(!this.p.a() && !flag) {
         LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
         this.p.b();
         return false;
      } else {
         if(this.T()) {
            this.c("127.0.0.1");
         } else {
            this.setOnlineMode(this.propertyManager.getBoolean("online-mode", true));
            this.c(this.propertyManager.getString("server-ip", ""));
         }

         this.setSpawnAnimals(this.propertyManager.getBoolean("spawn-animals", true));
         this.setSpawnNPCs(this.propertyManager.getBoolean("spawn-npcs", true));
         this.setPVP(this.propertyManager.getBoolean("pvp", true));
         this.setAllowFlight(this.propertyManager.getBoolean("allow-flight", false));
         this.setResourcePack(this.propertyManager.getString("resource-pack", ""), this.propertyManager.getString("resource-pack-hash", ""));
         this.setMotd(this.propertyManager.getString("motd", "A Minecraft Server"));
         this.setForceGamemode(this.propertyManager.getBoolean("force-gamemode", false));
         this.setIdleTimeout(this.propertyManager.getInt("player-idle-timeout", 0));
         if(this.propertyManager.getInt("difficulty", 1) < 0) {
            this.propertyManager.setProperty("difficulty", Integer.valueOf(0));
         } else if(this.propertyManager.getInt("difficulty", 1) > 3) {
            this.propertyManager.setProperty("difficulty", Integer.valueOf(3));
         }

         this.generateStructures = this.propertyManager.getBoolean("generate-structures", true);
         int i1 = this.propertyManager.getInt("gamemode", WorldSettings.EnumGamemode.SURVIVAL.getId());
         this.r = WorldSettings.a(i1);
         LOGGER.info("Default game type: " + this.r);
         InetAddress inetaddress = null;
         if(this.getServerIp().length() > 0) {
            inetaddress = InetAddress.getByName(this.getServerIp());
         }

         if(this.R() < 0) {
            this.setPort(this.propertyManager.getInt("server-port", 25565));
         }

         this.a((PlayerList)(new DedicatedPlayerList(this)));
         SpigotConfig.init((File)this.options.valueOf("spigot-settings"));
         SpigotConfig.registerCommands();
         LOGGER.info("Generating keypair");
         this.a((KeyPair)MinecraftEncryption.b());
         LOGGER.info("Starting Minecraft server on " + (this.getServerIp().length() == 0?"*":this.getServerIp()) + ":" + this.R());
         if(!SpigotConfig.lateBind) {
            try {
               this.aq().a(inetaddress, this.R());
            } catch (IOException ioexception1) {
               LOGGER.warn("**** FAILED TO BIND TO PORT!");
               LOGGER.warn("The exception was: {}", new Object[]{ioexception1.toString()});
               LOGGER.warn("Perhaps a server is already running on that port?");
               return false;
            }
         }

         this.server.loadPlugins();
         this.server.enablePlugins(PluginLoadOrder.STARTUP);
         if(!this.getOnlineMode()) {
            LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
            if(SpigotConfig.bungee) {
               LOGGER.warn("Whilst this makes it possible to use BungeeCord, unless access to your server is properly restricted, it also opens up the ability for hackers to connect with any username they choose.");
               LOGGER.warn("Please see http://www.spigotmc.org/wiki/firewall-guide/ for further information.");
            } else {
               LOGGER.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            }

            LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
         }

         if(this.aR()) {
            this.getUserCache().c();
         }

         if(!NameReferencingFileConverter.a(this.propertyManager)) {
            return false;
         } else {
            this.convertable = new WorldLoaderServer(this.server.getWorldContainer());
            long i = System.nanoTime();
            if(this.U() == null) {
               this.setWorld(this.propertyManager.getString("level-name", "world"));
            }

            String s = this.propertyManager.getString("level-seed", "");
            String s1 = this.propertyManager.getString("level-type", "DEFAULT");
            String s2 = this.propertyManager.getString("generator-settings", "");
            long j = (new Random()).nextLong();
            if(s.length() > 0) {
               try {
                  long k = Long.parseLong(s);
                  if(k != 0L) {
                     j = k;
                  }
               } catch (NumberFormatException var23) {
                  j = (long)s.hashCode();
               }
            }

            WorldType worldtype = WorldType.getType(s1);
            if(worldtype == null) {
               worldtype = WorldType.NORMAL;
            }

            this.aB();
            this.getEnableCommandBlock();
            this.p();
            this.getSnooperEnabled();
            this.aK();
            this.c(this.propertyManager.getInt("max-build-height", 256));
            this.c((this.getMaxBuildHeight() + 8) / 16 * 16);
            this.c(MathHelper.clamp(this.getMaxBuildHeight(), 64, 256));
            this.propertyManager.setProperty("max-build-height", Integer.valueOf(this.getMaxBuildHeight()));
            LOGGER.info("Preparing level \"" + this.U() + "\"");
            this.a(this.U(), this.U(), j, worldtype, s2);
            long l = System.nanoTime() - i;
            String s3 = String.format("%.3fs", new Object[]{Double.valueOf((double)l / 1.0E9D)});
            LOGGER.info("Done (" + s3 + ")! For help, type \"help\" or \"?\"");
            if(this.propertyManager.getBoolean("enable-query", false)) {
               LOGGER.info("Starting GS4 status listener");
               this.m = new RemoteStatusListener(this);
               this.m.a();
            }

            if(this.propertyManager.getBoolean("enable-rcon", false)) {
               LOGGER.info("Starting remote control listener");
               this.n = new RemoteControlListener(this);
               this.n.a();
               this.remoteConsole = new CraftRemoteConsoleCommandSender();
            }

            if(this.server.getBukkitSpawnRadius() > -1) {
               LOGGER.info("\'settings.spawn-radius\' in bukkit.yml has been moved to \'spawn-protection\' in server.properties. I will move your config for you.");
               this.propertyManager.properties.remove("spawn-protection");
               this.propertyManager.getInt("spawn-protection", this.server.getBukkitSpawnRadius());
               this.server.removeBukkitSpawnRadius();
               this.propertyManager.savePropertiesFile();
            }

            if(SpigotConfig.lateBind) {
               try {
                  this.aq().a(inetaddress, this.R());
               } catch (IOException ioexception) {
                  LOGGER.warn("**** FAILED TO BIND TO PORT!");
                  LOGGER.warn("The exception was: {}", new Object[]{ioexception.toString()});
                  LOGGER.warn("Perhaps a server is already running on that port?");
                  return false;
               }
            }

            return true;
         }
      }
   }

   public PropertyManager getPropertyManager() {
      return this.propertyManager;
   }

   public void setGamemode(WorldSettings.EnumGamemode p_setGamemode_1_) {
      super.setGamemode(p_setGamemode_1_);
      this.r = p_setGamemode_1_;
   }

   public boolean getGenerateStructures() {
      return this.generateStructures;
   }

   public WorldSettings.EnumGamemode getGamemode() {
      return this.r;
   }

   public EnumDifficulty getDifficulty() {
      return EnumDifficulty.getById(this.propertyManager.getInt("difficulty", EnumDifficulty.NORMAL.a()));
   }

   public boolean isHardcore() {
      return this.propertyManager.getBoolean("hardcore", false);
   }

   protected void a(CrashReport p_a_1_) {
   }

   public CrashReport b(CrashReport p_b_1_) {
      p_b_1_ = super.b(p_b_1_);
      p_b_1_.g().a("Is Modded", new Callable() {
         public String a() throws Exception {
            String s = DedicatedServer.this.getServerModName();
            return !s.equals("vanilla")?"Definitely; Server brand changed to \'" + s + "\'":"Unknown (can\'t tell)";
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_b_1_.g().a("Type", new Callable() {
         public String a() throws Exception {
            return "Dedicated Server (map_server.txt)";
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      return p_b_1_;
   }

   protected void z() {
      System.exit(0);
   }

   public void B() {
      super.B();
      this.aO();
   }

   public boolean getAllowNether() {
      return this.propertyManager.getBoolean("allow-nether", true);
   }

   public boolean getSpawnMonsters() {
      return this.propertyManager.getBoolean("spawn-monsters", true);
   }

   public void a(MojangStatisticsGenerator p_a_1_) {
      p_a_1_.a("whitelist_enabled", Boolean.valueOf(this.aP().getHasWhitelist()));
      p_a_1_.a("whitelist_count", Integer.valueOf(this.aP().getWhitelisted().length));
      super.a(p_a_1_);
   }

   public boolean getSnooperEnabled() {
      return this.propertyManager.getBoolean("snooper-enabled", true);
   }

   public void issueCommand(String p_issueCommand_1_, ICommandListener p_issueCommand_2_) {
      this.l.add(new ServerCommand(p_issueCommand_1_, p_issueCommand_2_));
   }

   public void aO() {
      SpigotTimings.serverCommandTimer.startTiming();

      while(!this.l.isEmpty()) {
         ServerCommand servercommand = (ServerCommand)this.l.remove(0);
         ServerCommandEvent servercommandevent = new ServerCommandEvent(this.console, servercommand.command);
         this.server.getPluginManager().callEvent(servercommandevent);
         if(!servercommandevent.isCancelled()) {
            servercommand = new ServerCommand(servercommandevent.getCommand(), servercommand.source);
            this.server.dispatchServerCommand(this.console, servercommand);
         }
      }

      SpigotTimings.serverCommandTimer.stopTiming();
   }

   public boolean ae() {
      return true;
   }

   public boolean ai() {
      return this.propertyManager.getBoolean("use-native-transport", true);
   }

   public DedicatedPlayerList aP() {
      return (DedicatedPlayerList)super.getPlayerList();
   }

   public int a(String p_a_1_, int p_a_2_) {
      return this.propertyManager.getInt(p_a_1_, p_a_2_);
   }

   public String a(String p_a_1_, String p_a_2_) {
      return this.propertyManager.getString(p_a_1_, p_a_2_);
   }

   public boolean a(String p_a_1_, boolean p_a_2_) {
      return this.propertyManager.getBoolean(p_a_1_, p_a_2_);
   }

   public void a(String p_a_1_, Object p_a_2_) {
      this.propertyManager.setProperty(p_a_1_, p_a_2_);
   }

   public void a() {
      this.propertyManager.savePropertiesFile();
   }

   public String b() {
      File file1 = this.propertyManager.c();
      return file1 != null?file1.getAbsolutePath():"No settings file";
   }

   public void aQ() {
      ServerGUI.a(this);
      this.s = true;
   }

   public boolean as() {
      return this.s;
   }

   public String a(WorldSettings.EnumGamemode p_a_1_, boolean p_a_2_) {
      return "";
   }

   public boolean getEnableCommandBlock() {
      return this.propertyManager.getBoolean("enable-command-block", false);
   }

   public int getSpawnProtection() {
      return this.propertyManager.getInt("spawn-protection", super.getSpawnProtection());
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, EntityHuman p_a_3_) {
      if(p_a_1_.worldProvider.getDimension() != 0) {
         return false;
      } else if(this.aP().getOPs().isEmpty()) {
         return false;
      } else if(this.aP().isOp(p_a_3_.getProfile())) {
         return false;
      } else if(this.getSpawnProtection() <= 0) {
         return false;
      } else {
         BlockPosition blockposition = p_a_1_.getSpawn();
         int i = MathHelper.a(p_a_2_.getX() - blockposition.getX());
         int j = MathHelper.a(p_a_2_.getZ() - blockposition.getZ());
         int k = Math.max(i, j);
         return k <= this.getSpawnProtection();
      }
   }

   public int p() {
      return this.propertyManager.getInt("op-permission-level", 4);
   }

   public void setIdleTimeout(int p_setIdleTimeout_1_) {
      super.setIdleTimeout(p_setIdleTimeout_1_);
      this.propertyManager.setProperty("player-idle-timeout", Integer.valueOf(p_setIdleTimeout_1_));
      this.a();
   }

   public boolean q() {
      return this.propertyManager.getBoolean("broadcast-rcon-to-ops", true);
   }

   public boolean r() {
      return this.propertyManager.getBoolean("broadcast-console-to-ops", true);
   }

   public boolean aB() {
      return this.propertyManager.getBoolean("announce-player-achievements", true);
   }

   public int aI() {
      int i = this.propertyManager.getInt("max-world-size", super.aI());
      if(i < 1) {
         i = 1;
      } else if(i > super.aI()) {
         i = super.aI();
      }

      return i;
   }

   public int aK() {
      return this.propertyManager.getInt("network-compression-threshold", super.aK());
   }

   protected boolean aR() {
      this.server.getLogger().info("**** Beginning UUID conversion, this may take A LONG time ****");
      boolean flag = false;

      for(int i = 0; !flag && i <= 2; ++i) {
         if(i > 0) {
            LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
            this.aU();
         }

         flag = NameReferencingFileConverter.a((MinecraftServer)this);
      }

      boolean flag1 = false;

      for(int j = 0; !flag1 && j <= 2; ++j) {
         if(j > 0) {
            LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
            this.aU();
         }

         flag1 = NameReferencingFileConverter.b((MinecraftServer)this);
      }

      boolean flag2 = false;

      for(int k = 0; !flag2 && k <= 2; ++k) {
         if(k > 0) {
            LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
            this.aU();
         }

         flag2 = NameReferencingFileConverter.c((MinecraftServer)this);
      }

      boolean flag3 = false;

      for(int lx = 0; !flag3 && lx <= 2; ++lx) {
         if(lx > 0) {
            LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
            this.aU();
         }

         flag3 = NameReferencingFileConverter.d((MinecraftServer)this);
      }

      boolean flag4 = false;

      for(int i1 = 0; !flag4 && i1 <= 2; ++i1) {
         if(i1 > 0) {
            LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
            this.aU();
         }

         flag4 = NameReferencingFileConverter.a(this, this.propertyManager);
      }

      return flag || flag1 || flag2 || flag3 || flag4;
   }

   private void aU() {
      try {
         Thread.sleep(5000L);
      } catch (InterruptedException var1) {
         ;
      }

   }

   public long aS() {
      return this.propertyManager.getLong("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
   }

   public String getPlugins() {
      StringBuilder stringbuilder = new StringBuilder();
      Plugin[] aplugin = this.server.getPluginManager().getPlugins();
      stringbuilder.append(this.server.getName());
      stringbuilder.append(" on Bukkit ");
      stringbuilder.append(this.server.getBukkitVersion());
      if(aplugin.length > 0 && this.server.getQueryPlugins()) {
         stringbuilder.append(": ");

         for(int i = 0; i < aplugin.length; ++i) {
            if(i > 0) {
               stringbuilder.append("; ");
            }

            stringbuilder.append(aplugin[i].getDescription().getName());
            stringbuilder.append(" ");
            stringbuilder.append(aplugin[i].getDescription().getVersion().replaceAll(";", ","));
         }
      }

      return stringbuilder.toString();
   }

   public String executeRemoteCommand(final String p_executeRemoteCommand_1_) {
      Waitable<String> waitable = new Waitable<String>() {
         protected String evaluate() {
            RemoteControlCommandListener.getInstance().i();
            RemoteServerCommandEvent remoteservercommandevent = new RemoteServerCommandEvent(DedicatedServer.this.remoteConsole, p_executeRemoteCommand_1_);
            DedicatedServer.this.server.getPluginManager().callEvent(remoteservercommandevent);
            if(remoteservercommandevent.isCancelled()) {
               return "";
            } else {
               ServerCommand servercommand = new ServerCommand(remoteservercommandevent.getCommand(), RemoteControlCommandListener.getInstance());
               DedicatedServer.this.server.dispatchServerCommand(DedicatedServer.this.remoteConsole, servercommand);
               return RemoteControlCommandListener.getInstance().j();
            }
         }
      };
      this.processQueue.add(waitable);

      try {
         return (String)waitable.get();
      } catch (ExecutionException executionexception) {
         throw new RuntimeException("Exception processing rcon command " + p_executeRemoteCommand_1_, executionexception.getCause());
      } catch (InterruptedException interruptedexception) {
         Thread.currentThread().interrupt();
         throw new RuntimeException("Interrupted processing rcon command " + p_executeRemoteCommand_1_, interruptedexception);
      }
   }

   public PlayerList getPlayerList() {
      return this.aP();
   }
}

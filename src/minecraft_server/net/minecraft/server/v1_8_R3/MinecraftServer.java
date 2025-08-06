package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.ResourceLeakDetector;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.World.Environment;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager;
import org.bukkit.craftbukkit.v1_8_R3.util.ServerShutdownThread;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginLoadOrder;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.SpigotConfig;
import org.spigotmc.WatchdogThread;

public abstract class MinecraftServer implements Runnable, ICommandListener, IAsyncTaskHandler, IMojangStatistics
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final File a = new File("usercache.json");
    private static MinecraftServer l;
    public Convertable convertable;
    private final MojangStatisticsGenerator n = new MojangStatisticsGenerator("server", this, az());
    public File universe;
    private final List<IUpdatePlayerListBox> p = Lists.<IUpdatePlayerListBox>newArrayList();
    protected final ICommandHandler b;
    public final MethodProfiler methodProfiler = new MethodProfiler();
    private ServerConnection q;
    private final ServerPing r = new ServerPing();
    private final Random s = new Random();
    private String serverIp;
    private int u = -1;
    public WorldServer[] worldServer;
    private PlayerList v;
    private boolean isRunning = true;
    private boolean isStopped;
    private int ticks;
    protected final Proxy e;
    public String f;
    public int g;
    private boolean onlineMode;
    private boolean spawnAnimals;
    private boolean spawnNPCs;
    private boolean pvpMode;
    private boolean allowFlight;
    private String motd;
    private int F;
    private int G = 0;
    public final long[] h = new long[100];
    public long[][] i;
    private KeyPair H;
    private String I;
    private String J;
    private boolean demoMode;
    private boolean M;
    private boolean N;
    private String O = "";
    private String P = "";
    private boolean Q;
    private long R;
    private String S;
    private boolean T;
    private boolean U;
    private final YggdrasilAuthenticationService V;
    private final MinecraftSessionService W;
    private long X = 0L;
    private final GameProfileRepository Y;
    private final UserCache Z;
    protected final Queue < FutureTask<? >> j = new ConcurrentLinkedQueue();
    private Thread serverThread;
    private long ab = az();
    public List<WorldServer> worlds = new ArrayList();
    public CraftServer server;
    public OptionSet options;
    public ConsoleCommandSender console;
    public RemoteConsoleCommandSender remoteConsole;
    public ConsoleReader reader;
    public static int currentTick = (int)(System.currentTimeMillis() / 50L);
    public final Thread primaryThread;
    public Queue<Runnable> processQueue = new ConcurrentLinkedQueue();
    public int autosavePeriod;
    private static final int TPS = 20;
    private static final int TICK_TIME = 50000000;
    private static final int SAMPLE_INTERVAL = 100;
    public final double[] recentTps = new double[3];
    private boolean hasStopped = false;
    private final Object stopLock = new Object();

    public MinecraftServer(OptionSet p_i162_1_, Proxy p_i162_2_, File p_i162_3_)
    {
        ResourceLeakDetector.setEnabled(false);
        this.e = p_i162_2_;
        l = this;
        this.Z = new UserCache(this, p_i162_3_);
        this.b = this.h();
        this.V = new YggdrasilAuthenticationService(p_i162_2_, UUID.randomUUID().toString());
        this.W = this.V.createMinecraftSessionService();
        this.Y = this.V.createProfileRepository();
        this.options = p_i162_1_;

        if (System.console() == null && System.getProperty("org.bukkit.craftbukkit.libs.jline.terminal") == null)
        {
            System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal");
            Main.useJline = false;
        }

        try
        {
            this.reader = new ConsoleReader(System.in, System.out);
            this.reader.setExpandEvents(false);
        }
        catch (Throwable var6)
        {
            try
            {
                System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal");
                System.setProperty("user.language", "en");
                Main.useJline = false;
                this.reader = new ConsoleReader(System.in, System.out);
                this.reader.setExpandEvents(false);
            }
            catch (IOException ioexception)
            {
                LOGGER.warn((String)null, (Throwable)ioexception);
            }
        }

        Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(this));
        this.serverThread = this.primaryThread = new Thread(this, "Server thread");
    }

    public abstract PropertyManager getPropertyManager();

    protected CommandDispatcher h()
    {
        return new CommandDispatcher();
    }

    protected abstract boolean init() throws IOException;

    protected void a(String p_a_1_)
    {
        if (this.getConvertable().isConvertable(p_a_1_))
        {
            LOGGER.info("Converting map!");
            this.b("menu.convertingLevel");
            this.getConvertable().convert(p_a_1_, new IProgressUpdate()
            {
                private long b = System.currentTimeMillis();
                public void a(String p_a_1_)
                {
                }
                public void a(int p_a_1_)
                {
                    if (System.currentTimeMillis() - this.b >= 1000L)
                    {
                        this.b = System.currentTimeMillis();
                        MinecraftServer.LOGGER.info("Converting... " + p_a_1_ + "%");
                    }
                }
                public void c(String p_c_1_)
                {
                }
            });
        }
    }

    protected synchronized void b(String p_b_1_)
    {
        this.S = p_b_1_;
    }

    protected void a(String p_a_1_, String p_a_2_, long p_a_3_, WorldType p_a_5_, String p_a_6_)
    {
        this.a(p_a_1_);
        this.b("menu.loadingLevel");
        this.worldServer = new WorldServer[3];
        int i = 3;

        for (int j = 0; j < i; ++j)
        {
            byte b0 = 0;

            if (j == 1)
            {
                if (!this.getAllowNether())
                {
                    continue;
                }

                b0 = -1;
            }

            if (j == 2)
            {
                if (!this.server.getAllowEnd())
                {
                    continue;
                }

                b0 = 1;
            }

            String s = Environment.getEnvironment(b0).toString().toLowerCase();
            String s1 = b0 == 0 ? p_a_1_ : p_a_1_ + "_" + s;
            ChunkGenerator chunkgenerator = this.server.getGenerator(s1);
            WorldSettings worldsettings = new WorldSettings(p_a_3_, this.getGamemode(), this.getGenerateStructures(), this.isHardcore(), p_a_5_);
            worldsettings.setGeneratorSettings(p_a_6_);
            WorldServer worldserver;

            if (j == 0)
            {
                IDataManager idatamanager = new ServerNBTManager(this.server.getWorldContainer(), p_a_2_, true);
                WorldData worlddata = idatamanager.getWorldData();

                if (worlddata == null)
                {
                    worlddata = new WorldData(worldsettings, p_a_2_);
                }

                worlddata.checkName(p_a_2_);

                if (this.X())
                {
                    worldserver = (WorldServer)(new DemoWorldServer(this, idatamanager, worlddata, b0, this.methodProfiler)).b();
                }
                else
                {
                    worldserver = (WorldServer)(new WorldServer(this, idatamanager, worlddata, b0, this.methodProfiler, Environment.getEnvironment(b0), chunkgenerator)).b();
                }

                worldserver.a(worldsettings);
                this.server.scoreboardManager = new CraftScoreboardManager(this, worldserver.getScoreboard());
            }
            else
            {
                String s2 = "DIM" + b0;
                File file2 = new File(new File(s1), s2);
                File file1 = new File(new File(p_a_1_), s2);

                if (!file2.isDirectory() && file1.isDirectory())
                {
                    LOGGER.info("---- Migration of old " + s + " folder required ----");
                    LOGGER.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + s + " folder to a new location in order to operate correctly.");
                    LOGGER.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
                    LOGGER.info("Attempting to move " + file1 + " to " + file2 + "...");

                    if (file2.exists())
                    {
                        LOGGER.warn("A file or folder already exists at " + file2 + "!");
                        LOGGER.info("---- Migration of old " + s + " folder failed ----");
                    }
                    else if (file2.getParentFile().mkdirs())
                    {
                        if (file1.renameTo(file2))
                        {
                            LOGGER.info("Success! To restore " + s + " in the future, simply move " + file2 + " to " + file1);

                            try
                            {
                                Files.copy(new File(new File(p_a_1_), "level.dat"), new File(new File(s1), "level.dat"));
                            }
                            catch (IOException var20)
                            {
                                LOGGER.warn("Unable to migrate world data.");
                            }

                            LOGGER.info("---- Migration of old " + s + " folder complete ----");
                        }
                        else
                        {
                            LOGGER.warn("Could not move folder " + file1 + " to " + file2 + "!");
                            LOGGER.info("---- Migration of old " + s + " folder failed ----");
                        }
                    }
                    else
                    {
                        LOGGER.warn("Could not create path for " + file2 + "!");
                        LOGGER.info("---- Migration of old " + s + " folder failed ----");
                    }
                }

                IDataManager idatamanager1 = new ServerNBTManager(this.server.getWorldContainer(), s1, true);
                WorldData worlddata1 = idatamanager1.getWorldData();

                if (worlddata1 == null)
                {
                    worlddata1 = new WorldData(worldsettings, s1);
                }

                worlddata1.checkName(s1);
                worldserver = (WorldServer)(new SecondaryWorldServer(this, idatamanager1, b0, (WorldServer)this.worlds.get(0), this.methodProfiler, worlddata1, Environment.getEnvironment(b0), chunkgenerator)).b();
            }

            this.server.getPluginManager().callEvent(new WorldInitEvent(worldserver.getWorld()));
            worldserver.addIWorldAccess(new WorldManager(this, worldserver));

            if (!this.T())
            {
                worldserver.getWorldData().setGameType(this.getGamemode());
            }

            this.worlds.add(worldserver);
            this.getPlayerList().setPlayerFileData((WorldServer[])this.worlds.toArray(new WorldServer[this.worlds.size()]));
        }

        this.a(this.getDifficulty());
        this.k();
    }

    protected void k()
    {
        int i = 0;
        this.b("menu.generatingTerrain");

        for (int j = 0; j < this.worlds.size(); ++j)
        {
            WorldServer worldserver = (WorldServer)this.worlds.get(j);
            LOGGER.info("Preparing start region for level " + j + " (Seed: " + worldserver.getSeed() + ")");

            if (worldserver.getWorld().getKeepSpawnInMemory())
            {
                BlockPosition blockposition = worldserver.getSpawn();
                long k = az();
                i = 0;

                for (int l = -192; l <= 192 && this.isRunning(); l += 16)
                {
                    for (int i1 = -192; i1 <= 192 && this.isRunning(); i1 += 16)
                    {
                        long j1 = az();

                        if (j1 - k > 1000L)
                        {
                            this.a_("Preparing spawn area", i * 100 / 625);
                            k = j1;
                        }

                        ++i;
                        worldserver.chunkProviderServer.getChunkAt(blockposition.getX() + l >> 4, blockposition.getZ() + i1 >> 4);
                    }
                }
            }
        }

        for (WorldServer worldserver1 : this.worlds)
        {
            this.server.getPluginManager().callEvent(new WorldLoadEvent(worldserver1.getWorld()));
        }

        this.s();
    }

    protected void a(String p_a_1_, IDataManager p_a_2_)
    {
        File file1 = new File(p_a_2_.getDirectory(), "resources.zip");

        if (file1.isFile())
        {
            this.setResourcePack("level://" + p_a_1_ + "/" + file1.getName(), "");
        }
    }

    public abstract boolean getGenerateStructures();

    public abstract WorldSettings.EnumGamemode getGamemode();

    public abstract EnumDifficulty getDifficulty();

    public abstract boolean isHardcore();

    public abstract int p();

    public abstract boolean q();

    public abstract boolean r();

    protected void a_(String p_a__1_, int p_a__2_)
    {
        this.f = p_a__1_;
        this.g = p_a__2_;
        LOGGER.info(p_a__1_ + ": " + p_a__2_ + "%");
    }

    protected void s()
    {
        this.f = null;
        this.g = 0;
        this.server.enablePlugins(PluginLoadOrder.POSTWORLD);
    }

    protected void saveChunks(boolean p_saveChunks_1_) throws ExceptionWorldConflict
    {
        if (!this.N)
        {
            WorldServer[] aworldserver = this.worldServer;
            int jx = aworldserver.length;

            for (int i = 0; i < this.worlds.size(); ++i)
            {
                WorldServer worldserver = (WorldServer)this.worlds.get(i);

                if (worldserver != null)
                {
                    if (!p_saveChunks_1_)
                    {
                        LOGGER.info("Saving chunks for level \'" + worldserver.getWorldData().getName() + "\'/" + worldserver.worldProvider.getName());
                    }

                    try
                    {
                        worldserver.save(true, (IProgressUpdate)null);
                        worldserver.saveLevel();
                    }
                    catch (ExceptionWorldConflict exceptionworldconflict)
                    {
                        LOGGER.warn(exceptionworldconflict.getMessage());
                    }
                }
            }
        }
    }

    public void stop() throws ExceptionWorldConflict
    {
        synchronized (this.stopLock)
        {
            if (this.hasStopped)
            {
                return;
            }

            this.hasStopped = true;
        }

        if (!this.N)
        {
            LOGGER.info("Stopping server");

            if (this.server != null)
            {
                this.server.disablePlugins();
            }

            if (this.aq() != null)
            {
                this.aq().b();
            }

            if (this.v != null)
            {
                LOGGER.info("Saving players");
                this.v.savePlayers();
                this.v.u();

                try
                {
                    Thread.sleep(100L);
                }
                catch (InterruptedException var2)
                {
                    ;
                }
            }

            if (this.worldServer != null)
            {
                LOGGER.info("Saving worlds");
                this.saveChunks(false);
            }

            if (this.n.d())
            {
                this.n.e();
            }

            if (SpigotConfig.saveUserCacheOnStopOnly)
            {
                LOGGER.info("Saving usercache.json");
                this.Z.c();
            }
        }
    }

    public String getServerIp()
    {
        return this.serverIp;
    }

    public void c(String p_c_1_)
    {
        this.serverIp = p_c_1_;
    }

    public boolean isRunning()
    {
        return this.isRunning;
    }

    public void safeShutdown()
    {
        this.isRunning = false;
    }

    private static double calcTps(double p_calcTps_0_, double p_calcTps_2_, double p_calcTps_4_)
    {
        return p_calcTps_0_ * p_calcTps_2_ + p_calcTps_4_ * (1.0D - p_calcTps_2_);
    }

    public void run()
    {
        try
        {
            if (this.init())
            {
                this.ab = az();
                this.r.setMOTD(new ChatComponentText(this.motd));
                this.r.setServerInfo(new ServerPing.ServerData("1.8.8", 47));
                this.a(this.r);
                Arrays.fill(this.recentTps, 20.0D);
                long i = System.nanoTime();
                long j = 0L;
                long k = i;

                while (this.isRunning)
                {
                    long l = System.nanoTime();
                    long i1 = 50000000L - (l - i) - j;

                    if (i1 > 0L)
                    {
                        Thread.sleep(i1 / 1000000L);
                        j = 0L;
                    }
                    else
                    {
                        j = Math.min(1000000000L, Math.abs(i1));

                        if (currentTick++ % 100 == 0)
                        {
                            double d0 = 1.0E9D / (double)(l - k) * 100.0D;
                            this.recentTps[0] = calcTps(this.recentTps[0], 0.92D, d0);
                            this.recentTps[1] = calcTps(this.recentTps[1], 0.9835D, d0);
                            this.recentTps[2] = calcTps(this.recentTps[2], 0.9945D, d0);
                            k = l;
                        }

                        i = l;
                        this.A();
                        this.Q = true;
                    }
                }
            }
            else
            {
                this.a((CrashReport)null);
            }
        }
        catch (Throwable throwable1)
        {
            LOGGER.error("Encountered an unexpected exception", throwable1);

            if (throwable1.getCause() != null)
            {
                LOGGER.error("\tCause of unexpected exception was", throwable1.getCause());
            }

            CrashReport crashreport = null;

            if (throwable1 instanceof ReportedException)
            {
                crashreport = this.b(((ReportedException)throwable1).a());
            }
            else
            {
                crashreport = this.b(new CrashReport("Exception in server tick loop", throwable1));
            }

            File file1 = new File(new File(this.y(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");

            if (crashreport.a(file1))
            {
                LOGGER.error("This crash report has been saved to: " + file1.getAbsolutePath());
            }
            else
            {
                LOGGER.error("We were unable to save this crash report to disk.");
            }

            this.a(crashreport);
        }
        finally
        {
            try
            {
                WatchdogThread.doStop();
                this.isStopped = true;
                this.stop();
            }
            catch (Throwable throwable)
            {
                LOGGER.error("Exception stopping the server", throwable);
            }
            finally
            {
                try
                {
                    this.reader.getTerminal().restore();
                }
                catch (Exception var75)
                {
                    ;
                }

                this.z();
            }
        }
    }

    private void a(ServerPing p_a_1_)
    {
        File file1 = this.d("server-icon.png");

        if (file1.isFile())
        {
            ByteBuf bytebuf = Unpooled.buffer();

            try
            {
                BufferedImage bufferedimage = ImageIO.read(file1);
                Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write(bufferedimage, "PNG", new ByteBufOutputStream(bytebuf));
                ByteBuf bytebuf1 = Base64.encode(bytebuf);
                p_a_1_.setFavicon("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
            }
            catch (Exception exception)
            {
                LOGGER.error((String)"Couldn\'t load server icon", (Throwable)exception);
            }
            finally
            {
                bytebuf.release();
            }
        }
    }

    public File y()
    {
        return new File(".");
    }

    protected void a(CrashReport p_a_1_)
    {
    }

    protected void z()
    {
    }

    protected void A() throws ExceptionWorldConflict
    {
        SpigotTimings.serverTickTimer.startTiming();
        long i = System.nanoTime();
        ++this.ticks;

        if (this.T)
        {
            this.T = false;
            this.methodProfiler.a = true;
            this.methodProfiler.a();
        }

        this.methodProfiler.a("root");
        this.B();

        if (i - this.X >= 5000000000L)
        {
            this.X = i;
            this.r.setPlayerSample(new ServerPing.ServerPingPlayerSample(this.J(), this.I()));
            GameProfile[] agameprofile = new GameProfile[Math.min(this.I(), 12)];
            int j = MathHelper.nextInt(this.s, 0, this.I() - agameprofile.length);

            for (int k = 0; k < agameprofile.length; ++k)
            {
                agameprofile[k] = ((EntityPlayer)this.v.v().get(j + k)).getProfile();
            }

            Collections.shuffle(Arrays.asList(agameprofile));
            this.r.b().a(agameprofile);
        }

        if (this.autosavePeriod > 0 && this.ticks % this.autosavePeriod == 0)
        {
            SpigotTimings.worldSaveTimer.startTiming();
            this.methodProfiler.a("save");
            this.v.savePlayers();
            this.server.playerCommandState = true;

            for (World world : this.worlds)
            {
                world.getWorld().save(false);
            }

            this.server.playerCommandState = false;
            this.methodProfiler.b();
            SpigotTimings.worldSaveTimer.stopTiming();
        }

        this.methodProfiler.a("tallying");
        this.h[this.ticks % 100] = System.nanoTime() - i;
        this.methodProfiler.b();
        this.methodProfiler.a("snooper");

        if (this.getSnooperEnabled() && !this.n.d() && this.ticks > 100)
        {
            this.n.a();
        }

        if (this.getSnooperEnabled() && this.ticks % 6000 == 0)
        {
            this.n.b();
        }

        this.methodProfiler.b();
        this.methodProfiler.b();
        WatchdogThread.tick();
        SpigotTimings.serverTickTimer.stopTiming();
        CustomTimingsHandler.tick();
    }

    public void B()
    {
        this.methodProfiler.a("jobs");
        int i = this.j.size();
        FutureTask<?> futuretask;

        while (i-- > 0 && (futuretask = (FutureTask)this.j.poll()) != null)
        {
            SystemUtils.a(futuretask, LOGGER);
        }

        this.methodProfiler.c("levels");
        SpigotTimings.schedulerTimer.startTiming();
        this.server.getScheduler().mainThreadHeartbeat(this.ticks);
        SpigotTimings.schedulerTimer.stopTiming();
        SpigotTimings.processQueueTimer.startTiming();

        while (!this.processQueue.isEmpty())
        {
            ((Runnable)this.processQueue.remove()).run();
        }

        SpigotTimings.processQueueTimer.stopTiming();
        SpigotTimings.chunkIOTickTimer.startTiming();
        ChunkIOExecutor.tick();
        SpigotTimings.chunkIOTickTimer.stopTiming();
        SpigotTimings.timeUpdateTimer.startTiming();

        if (this.ticks % 20 == 0)
        {
            for (int j = 0; j < this.getPlayerList().players.size(); ++j)
            {
                EntityPlayer entityplayer = (EntityPlayer)this.getPlayerList().players.get(j);
                entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(entityplayer.world.getTime(), entityplayer.getPlayerTime(), entityplayer.world.getGameRules().getBoolean("doDaylightCycle")));
            }
        }

        SpigotTimings.timeUpdateTimer.stopTiming();

        for (int k = 0; k < this.worlds.size(); ++k)
        {
            System.nanoTime();
            WorldServer worldserver = (WorldServer)this.worlds.get(k);
            this.methodProfiler.a(worldserver.getWorldData().getName());
            this.methodProfiler.a("tick");

            try
            {
                worldserver.timings.doTick.startTiming();
                worldserver.doTick();
                worldserver.timings.doTick.stopTiming();
            }
            catch (Throwable throwable4)
            {
                Throwable throwable = throwable4;
                CrashReport crashreport;

                try
                {
                    crashreport = CrashReport.a(throwable, "Exception ticking world");
                }
                catch (Throwable throwable2)
                {
                    throw new RuntimeException("Error generating crash report", throwable2);
                }

                worldserver.a((CrashReport)crashreport);
                throw new ReportedException(crashreport);
            }

            try
            {
                worldserver.timings.tickEntities.startTiming();
                worldserver.tickEntities();
                worldserver.timings.tickEntities.stopTiming();
            }
            catch (Throwable throwable3)
            {
                Throwable throwable5 = throwable3;
                CrashReport crashreport1;

                try
                {
                    crashreport1 = CrashReport.a(throwable5, "Exception ticking world entities");
                }
                catch (Throwable throwable1)
                {
                    throw new RuntimeException("Error generating crash report", throwable1);
                }

                worldserver.a((CrashReport)crashreport1);
                throw new ReportedException(crashreport1);
            }

            this.methodProfiler.b();
            this.methodProfiler.a("tracker");
            worldserver.timings.tracker.startTiming();
            worldserver.getTracker().updatePlayers();
            worldserver.timings.tracker.stopTiming();
            this.methodProfiler.b();
            this.methodProfiler.b();
        }

        this.methodProfiler.c("connection");
        SpigotTimings.connectionTimer.startTiming();
        this.aq().c();
        SpigotTimings.connectionTimer.stopTiming();
        this.methodProfiler.c("players");
        SpigotTimings.playerListTimer.startTiming();
        this.v.tick();
        SpigotTimings.playerListTimer.stopTiming();
        this.methodProfiler.c("tickables");
        SpigotTimings.tickablesTimer.startTiming();

        for (int lx = 0; lx < this.p.size(); ++lx)
        {
            ((IUpdatePlayerListBox)this.p.get(lx)).c();
        }

        SpigotTimings.tickablesTimer.stopTiming();
        this.methodProfiler.b();
    }

    public boolean getAllowNether()
    {
        return true;
    }

    public void a(IUpdatePlayerListBox p_a_1_)
    {
        this.p.add(p_a_1_);
    }

    public static void main(OptionSet p_main_0_)
    {
        DispenserRegistry.c();

        try
        {
            DedicatedServer dedicatedserver = new DedicatedServer(p_main_0_);

            if (p_main_0_.has("port"))
            {
                int i = ((Integer)p_main_0_.valueOf("port")).intValue();

                if (i > 0)
                {
                    dedicatedserver.setPort(i);
                }
            }

            if (p_main_0_.has("universe"))
            {
                dedicatedserver.universe = (File)p_main_0_.valueOf("universe");
            }

            if (p_main_0_.has("world"))
            {
                dedicatedserver.setWorld((String)p_main_0_.valueOf("world"));
            }

            dedicatedserver.primaryThread.start();
        }
        catch (Exception exception)
        {
            LOGGER.fatal((String)"Failed to start the minecraft server", (Throwable)exception);
        }
    }

    public void C()
    {
    }

    public File d(String p_d_1_)
    {
        return new File(this.y(), p_d_1_);
    }

    public void info(String p_info_1_)
    {
        LOGGER.info(p_info_1_);
    }

    public void warning(String p_warning_1_)
    {
        LOGGER.warn(p_warning_1_);
    }

    public WorldServer getWorldServer(int p_getWorldServer_1_)
    {
        for (WorldServer worldserver : this.worlds)
        {
            if (worldserver.dimension == p_getWorldServer_1_)
            {
                return worldserver;
            }
        }

        return (WorldServer)this.worlds.get(0);
    }

    public String E()
    {
        return this.serverIp;
    }

    public int F()
    {
        return this.u;
    }

    public String G()
    {
        return this.motd;
    }

    public String getVersion()
    {
        return "1.8.8";
    }

    public int I()
    {
        return this.v.getPlayerCount();
    }

    public int J()
    {
        return this.v.getMaxPlayers();
    }

    public String[] getPlayers()
    {
        return this.v.f();
    }

    public GameProfile[] L()
    {
        return this.v.g();
    }

    public boolean isDebugging()
    {
        return this.getPropertyManager().getBoolean("debug", false);
    }

    public void g(String p_g_1_)
    {
        LOGGER.error(p_g_1_);
    }

    public void h(String p_h_1_)
    {
        if (this.isDebugging())
        {
            LOGGER.info(p_h_1_);
        }
    }

    public String getServerModName()
    {
        return "Spigot";
    }

    public CrashReport b(CrashReport p_b_1_)
    {
        p_b_1_.g().a("Profiler Position", new Callable()
        {
            public String a() throws Exception
            {
                return MinecraftServer.this.methodProfiler.a ? MinecraftServer.this.methodProfiler.c() : "N/A (disabled)";
            }
            public Object call() throws Exception
            {
                return this.a();
            }
        });

        if (this.v != null)
        {
            p_b_1_.g().a("Player Count", new Callable()
            {
                public String a()
                {
                    return MinecraftServer.this.v.getPlayerCount() + " / " + MinecraftServer.this.v.getMaxPlayers() + "; " + MinecraftServer.this.v.v();
                }
                public Object call() throws Exception
                {
                    return this.a();
                }
            });
        }

        return p_b_1_;
    }

    public List<String> tabCompleteCommand(ICommandListener p_tabCompleteCommand_1_, String p_tabCompleteCommand_2_, BlockPosition p_tabCompleteCommand_3_)
    {
        return this.server.tabComplete(p_tabCompleteCommand_1_, p_tabCompleteCommand_2_);
    }

    public static MinecraftServer getServer()
    {
        return l;
    }

    public boolean O()
    {
        return true;
    }

    public String getName()
    {
        return "Server";
    }

    public void sendMessage(IChatBaseComponent p_sendMessage_1_)
    {
        LOGGER.info(p_sendMessage_1_.c());
    }

    public boolean a(int p_a_1_, String p_a_2_)
    {
        return true;
    }

    public ICommandHandler getCommandHandler()
    {
        return this.b;
    }

    public KeyPair Q()
    {
        return this.H;
    }

    public int R()
    {
        return this.u;
    }

    public void setPort(int p_setPort_1_)
    {
        this.u = p_setPort_1_;
    }

    public String S()
    {
        return this.I;
    }

    public void i(String p_i_1_)
    {
        this.I = p_i_1_;
    }

    public boolean T()
    {
        return this.I != null;
    }

    public String U()
    {
        return this.J;
    }

    public void setWorld(String p_setWorld_1_)
    {
        this.J = p_setWorld_1_;
    }

    public void a(KeyPair p_a_1_)
    {
        this.H = p_a_1_;
    }

    public void a(EnumDifficulty p_a_1_)
    {
        for (int i = 0; i < this.worlds.size(); ++i)
        {
            WorldServer worldserver = (WorldServer)this.worlds.get(i);

            if (worldserver != null)
            {
                if (worldserver.getWorldData().isHardcore())
                {
                    worldserver.getWorldData().setDifficulty(EnumDifficulty.HARD);
                    worldserver.setSpawnFlags(true, true);
                }
                else if (this.T())
                {
                    worldserver.getWorldData().setDifficulty(p_a_1_);
                    worldserver.setSpawnFlags(worldserver.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                }
                else
                {
                    worldserver.getWorldData().setDifficulty(p_a_1_);
                    worldserver.setSpawnFlags(this.getSpawnMonsters(), this.spawnAnimals);
                }
            }
        }
    }

    protected boolean getSpawnMonsters()
    {
        return true;
    }

    public boolean X()
    {
        return this.demoMode;
    }

    public void b(boolean p_b_1_)
    {
        this.demoMode = p_b_1_;
    }

    public void c(boolean p_c_1_)
    {
        this.M = p_c_1_;
    }

    public Convertable getConvertable()
    {
        return this.convertable;
    }

    public void aa()
    {
        this.N = true;
        this.getConvertable().d();

        for (int i = 0; i < this.worlds.size(); ++i)
        {
            WorldServer worldserver = (WorldServer)this.worlds.get(i);

            if (worldserver != null)
            {
                worldserver.saveLevel();
            }
        }

        this.getConvertable().e(((WorldServer)this.worlds.get(0)).getDataManager().g());
        this.safeShutdown();
    }

    public String getResourcePack()
    {
        return this.O;
    }

    public String getResourcePackHash()
    {
        return this.P;
    }

    public void setResourcePack(String p_setResourcePack_1_, String p_setResourcePack_2_)
    {
        this.O = p_setResourcePack_1_;
        this.P = p_setResourcePack_2_;
    }

    public void a(MojangStatisticsGenerator p_a_1_)
    {
        p_a_1_.a("whitelist_enabled", Boolean.valueOf(false));
        p_a_1_.a("whitelist_count", Integer.valueOf(0));

        if (this.v != null)
        {
            p_a_1_.a("players_current", Integer.valueOf(this.I()));
            p_a_1_.a("players_max", Integer.valueOf(this.J()));
            p_a_1_.a("players_seen", Integer.valueOf(this.v.getSeenPlayers().length));
        }

        p_a_1_.a("uses_auth", Boolean.valueOf(this.onlineMode));
        p_a_1_.a("gui_state", this.as() ? "enabled" : "disabled");
        p_a_1_.a("run_time", Long.valueOf((az() - p_a_1_.g()) / 60L * 1000L));
        p_a_1_.a("avg_tick_ms", Integer.valueOf((int)(MathHelper.a(this.h) * 1.0E-6D)));
        int i = 0;

        if (this.worldServer != null)
        {
            for (int j = 0; j < this.worlds.size(); ++j)
            {
                WorldServer worldserver = (WorldServer)this.worlds.get(j);

                if (worldserver != null)
                {
                    WorldData worlddata = worldserver.getWorldData();
                    p_a_1_.a("world[" + i + "][dimension]", Integer.valueOf(worldserver.worldProvider.getDimension()));
                    p_a_1_.a("world[" + i + "][mode]", worlddata.getGameType());
                    p_a_1_.a("world[" + i + "][difficulty]", worldserver.getDifficulty());
                    p_a_1_.a("world[" + i + "][hardcore]", Boolean.valueOf(worlddata.isHardcore()));
                    p_a_1_.a("world[" + i + "][generator_name]", worlddata.getType().name());
                    p_a_1_.a("world[" + i + "][generator_version]", Integer.valueOf(worlddata.getType().getVersion()));
                    p_a_1_.a("world[" + i + "][height]", Integer.valueOf(this.F));
                    p_a_1_.a("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.N().getLoadedChunks()));
                    ++i;
                }
            }
        }

        p_a_1_.a("worlds", Integer.valueOf(i));
    }

    public void b(MojangStatisticsGenerator p_b_1_)
    {
        p_b_1_.b("singleplayer", Boolean.valueOf(this.T()));
        p_b_1_.b("server_brand", this.getServerModName());
        p_b_1_.b("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        p_b_1_.b("dedicated", Boolean.valueOf(this.ae()));
    }

    public boolean getSnooperEnabled()
    {
        return true;
    }

    public abstract boolean ae();

    public boolean getOnlineMode()
    {
        return this.server.getOnlineMode();
    }

    public void setOnlineMode(boolean p_setOnlineMode_1_)
    {
        this.onlineMode = p_setOnlineMode_1_;
    }

    public boolean getSpawnAnimals()
    {
        return this.spawnAnimals;
    }

    public void setSpawnAnimals(boolean p_setSpawnAnimals_1_)
    {
        this.spawnAnimals = p_setSpawnAnimals_1_;
    }

    public boolean getSpawnNPCs()
    {
        return this.spawnNPCs;
    }

    public abstract boolean ai();

    public void setSpawnNPCs(boolean p_setSpawnNPCs_1_)
    {
        this.spawnNPCs = p_setSpawnNPCs_1_;
    }

    public boolean getPVP()
    {
        return this.pvpMode;
    }

    public void setPVP(boolean p_setPVP_1_)
    {
        this.pvpMode = p_setPVP_1_;
    }

    public boolean getAllowFlight()
    {
        return this.allowFlight;
    }

    public void setAllowFlight(boolean p_setAllowFlight_1_)
    {
        this.allowFlight = p_setAllowFlight_1_;
    }

    public abstract boolean getEnableCommandBlock();

    public String getMotd()
    {
        return this.motd;
    }

    public void setMotd(String p_setMotd_1_)
    {
        this.motd = p_setMotd_1_;
    }

    public int getMaxBuildHeight()
    {
        return this.F;
    }

    public void c(int p_c_1_)
    {
        this.F = p_c_1_;
    }

    public boolean isStopped()
    {
        return this.isStopped;
    }

    public PlayerList getPlayerList()
    {
        return this.v;
    }

    public void a(PlayerList p_a_1_)
    {
        this.v = p_a_1_;
    }

    public void setGamemode(WorldSettings.EnumGamemode p_setGamemode_1_)
    {
        for (int i = 0; i < this.worlds.size(); ++i)
        {
            ((WorldServer)getServer().worlds.get(i)).getWorldData().setGameType(p_setGamemode_1_);
        }
    }

    public ServerConnection getServerConnection()
    {
        return this.q;
    }

    public ServerConnection aq()
    {
        return this.q == null ? (this.q = new ServerConnection(this)) : this.q;
    }

    public boolean as()
    {
        return false;
    }

    public abstract String a(WorldSettings.EnumGamemode var1, boolean var2);

    public int at()
    {
        return this.ticks;
    }

    public void au()
    {
        this.T = true;
    }

    public BlockPosition getChunkCoordinates()
    {
        return BlockPosition.ZERO;
    }

    public Vec3D d()
    {
        return new Vec3D(0.0D, 0.0D, 0.0D);
    }

    public World getWorld()
    {
        return (World)this.worlds.get(0);
    }

    public Entity f()
    {
        return null;
    }

    public int getSpawnProtection()
    {
        return 16;
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, EntityHuman p_a_3_)
    {
        return false;
    }

    public void setForceGamemode(boolean p_setForceGamemode_1_)
    {
        this.U = p_setForceGamemode_1_;
    }

    public boolean getForceGamemode()
    {
        return this.U;
    }

    public Proxy ay()
    {
        return this.e;
    }

    public static long az()
    {
        return System.currentTimeMillis();
    }

    public int getIdleTimeout()
    {
        return this.G;
    }

    public void setIdleTimeout(int p_setIdleTimeout_1_)
    {
        this.G = p_setIdleTimeout_1_;
    }

    public IChatBaseComponent getScoreboardDisplayName()
    {
        return new ChatComponentText(this.getName());
    }

    public boolean aB()
    {
        return true;
    }

    public MinecraftSessionService aD()
    {
        return this.W;
    }

    public GameProfileRepository getGameProfileRepository()
    {
        return this.Y;
    }

    public UserCache getUserCache()
    {
        return this.Z;
    }

    public ServerPing aG()
    {
        return this.r;
    }

    public void aH()
    {
        this.X = 0L;
    }

    public Entity a(UUID p_a_1_)
    {
        WorldServer[] aworldserver = this.worldServer;
        int jx = aworldserver.length;

        for (int i = 0; i < this.worlds.size(); ++i)
        {
            WorldServer worldserver = (WorldServer)this.worlds.get(i);

            if (worldserver != null)
            {
                Entity entity = worldserver.getEntity(p_a_1_);

                if (entity != null)
                {
                    return entity;
                }
            }
        }

        return null;
    }

    public boolean getSendCommandFeedback()
    {
        return ((WorldServer)getServer().worlds.get(0)).getGameRules().getBoolean("sendCommandFeedback");
    }

    public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_)
    {
    }

    public int aI()
    {
        return 29999984;
    }

    public <V> ListenableFuture<V> a(Callable<V> p_a_1_)
    {
        Validate.notNull(p_a_1_);

        if (!this.isMainThread())
        {
            ListenableFutureTask listenablefuturetask = ListenableFutureTask.create(p_a_1_);
            this.j.add(listenablefuturetask);
            return listenablefuturetask;
        }
        else
        {
            try
            {
                return Futures.<V>immediateFuture(p_a_1_.call());
            }
            catch (Exception exception)
            {
                return Futures.immediateFailedCheckedFuture(exception);
            }
        }
    }

    public ListenableFuture<Object> postToMainThread(Runnable p_postToMainThread_1_)
    {
        Validate.notNull(p_postToMainThread_1_);
        return this.a(Executors.callable(p_postToMainThread_1_));
    }

    public boolean isMainThread()
    {
        return Thread.currentThread() == this.serverThread;
    }

    public int aK()
    {
        return 256;
    }

    public long aL()
    {
        return this.ab;
    }

    public Thread aM()
    {
        return this.serverThread;
    }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftTravelAgent;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.v1_8_R3.command.ColouredConsoleSender;
import org.bukkit.craftbukkit.v1_8_R3.command.ConsoleCommandCompleter;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;
import org.spigotmc.CaseInsensitiveMap;
import org.spigotmc.SpigotConfig;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public abstract class PlayerList
{
    public static final File a = new File("banned-players.json");
    public static final File b = new File("banned-ips.json");
    public static final File c = new File("ops.json");
    public static final File d = new File("whitelist.json");
    private static final Logger f = LogManager.getLogger();
    private static final SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");
    private final MinecraftServer server;
    public final List<EntityPlayer> players = new CopyOnWriteArrayList();
    private final Map<UUID, EntityPlayer> j = Maps.<UUID, EntityPlayer>newHashMap();
    private final GameProfileBanList k;
    private final IpBanList l;
    private final OpList operators;
    private final WhiteList whitelist;
    private final Map<UUID, ServerStatisticManager> o;
    public IPlayerFileData playerFileData;
    private boolean hasWhitelist;
    protected int maxPlayers;
    private int r;
    private WorldSettings.EnumGamemode s;
    private boolean t;
    private int u;
    private CraftServer cserver;
    private final Map<String, EntityPlayer> playersByName = new CaseInsensitiveMap();

    public PlayerList(MinecraftServer p_i441_1_)
    {
        this.cserver = p_i441_1_.server = new CraftServer(p_i441_1_, this);
        p_i441_1_.console = ColouredConsoleSender.getInstance();
        p_i441_1_.reader.addCompleter(new ConsoleCommandCompleter(p_i441_1_.server));
        this.k = new GameProfileBanList(a);
        this.l = new IpBanList(b);
        this.operators = new OpList(c);
        this.whitelist = new WhiteList(d);
        this.o = Maps.<UUID, ServerStatisticManager>newHashMap();
        this.server = p_i441_1_;
        this.k.a(false);
        this.l.a(false);
        this.maxPlayers = 8;
    }

    public void a(NetworkManager p_a_1_, EntityPlayer p_a_2_)
    {
        GameProfile gameprofile = p_a_2_.getProfile();
        UserCache usercache = this.server.getUserCache();
        GameProfile gameprofile1 = usercache.a(gameprofile.getId());
        String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
        usercache.a(gameprofile);
        NBTTagCompound nbttagcompound = this.a(p_a_2_);

        if (nbttagcompound != null && nbttagcompound.hasKey("bukkit"))
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("bukkit");
            s = nbttagcompound1.hasKeyOfType("lastKnownName", 8) ? nbttagcompound1.getString("lastKnownName") : s;
        }

        p_a_2_.spawnIn(this.server.getWorldServer(p_a_2_.dimension));
        p_a_2_.playerInteractManager.a((WorldServer)p_a_2_.world);
        String s2 = "local";

        if (p_a_1_.getSocketAddress() != null)
        {
            s2 = p_a_1_.getSocketAddress().toString();
        }

        Player player = p_a_2_.getBukkitEntity();
        PlayerSpawnLocationEvent playerspawnlocationevent = new PlayerSpawnLocationEvent(player, player.getLocation());
        Bukkit.getPluginManager().callEvent(playerspawnlocationevent);
        Location location = playerspawnlocationevent.getSpawnLocation();
        WorldServer worldserver = ((CraftWorld)location.getWorld()).getHandle();
        p_a_2_.spawnIn(worldserver);
        p_a_2_.setPosition(location.getX(), location.getY(), location.getZ());
        p_a_2_.setYawPitch(location.getYaw(), location.getPitch());
        WorldServer worldserver1 = this.server.getWorldServer(p_a_2_.dimension);
        WorldData worlddata = worldserver1.getWorldData();
        BlockPosition blockposition = worldserver1.getSpawn();
        this.a(p_a_2_, (EntityPlayer)null, worldserver1);
        PlayerConnection playerconnection = new PlayerConnection(this.server, p_a_1_, p_a_2_);
        playerconnection.sendPacket(new PacketPlayOutLogin(p_a_2_.getId(), p_a_2_.playerInteractManager.getGameMode(), worlddata.isHardcore(), worldserver1.worldProvider.getDimension(), worldserver1.getDifficulty(), Math.min(this.getMaxPlayers(), 60), worlddata.getType(), worldserver1.getGameRules().getBoolean("reducedDebugInfo")));
        p_a_2_.getBukkitEntity().sendSupportedChannels();
        playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", (new PacketDataSerializer(Unpooled.buffer())).a(this.getServer().getServerModName())));
        playerconnection.sendPacket(new PacketPlayOutServerDifficulty(worlddata.getDifficulty(), worlddata.isDifficultyLocked()));
        playerconnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition));
        playerconnection.sendPacket(new PacketPlayOutAbilities(p_a_2_.abilities));
        playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(p_a_2_.inventory.itemInHandIndex));
        p_a_2_.getStatisticManager().d();
        p_a_2_.getStatisticManager().updateStatistics(p_a_2_);
        this.sendScoreboard((ScoreboardServer)worldserver1.getScoreboard(), p_a_2_);
        this.server.aH();
        String s1;

        if (!p_a_2_.getName().equalsIgnoreCase(s))
        {
            s1 = "\u00a7e" + LocaleI18n.a("multiplayer.player.joined.renamed", new Object[] {p_a_2_.getName(), s});
        }
        else
        {
            s1 = "\u00a7e" + LocaleI18n.a("multiplayer.player.joined", new Object[] {p_a_2_.getName()});
        }

        this.onPlayerJoin(p_a_2_, s1);
        worldserver1 = this.server.getWorldServer(p_a_2_.dimension);
        playerconnection.a(p_a_2_.locX, p_a_2_.locY, p_a_2_.locZ, p_a_2_.yaw, p_a_2_.pitch);
        this.b(p_a_2_, worldserver1);

        if (this.server.getResourcePack().length() > 0)
        {
            p_a_2_.setResourcePack(this.server.getResourcePack(), this.server.getResourcePackHash());
        }

        for (MobEffect mobeffect : p_a_2_.getEffects())
        {
            playerconnection.sendPacket(new PacketPlayOutEntityEffect(p_a_2_.getId(), mobeffect));
        }

        p_a_2_.syncInventory();

        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("Riding", 10))
        {
            Entity entity = EntityTypes.a((NBTTagCompound)nbttagcompound.getCompound("Riding"), (World)worldserver1);

            if (entity != null)
            {
                entity.attachedToPlayer = true;
                worldserver1.addEntity(entity);
                p_a_2_.mount(entity);
                entity.attachedToPlayer = false;
            }
        }

        f.info(p_a_2_.getName() + "[" + s2 + "] logged in with entity id " + p_a_2_.getId() + " at ([" + p_a_2_.world.worldData.getName() + "]" + p_a_2_.locX + ", " + p_a_2_.locY + ", " + p_a_2_.locZ + ")");
    }

    public void sendScoreboard(ScoreboardServer p_sendScoreboard_1_, EntityPlayer p_sendScoreboard_2_)
    {
        HashSet hashset = Sets.newHashSet();

        for (ScoreboardTeam scoreboardteam : p_sendScoreboard_1_.getTeams())
        {
            p_sendScoreboard_2_.playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
        }

        for (int i = 0; i < 19; ++i)
        {
            ScoreboardObjective scoreboardobjective = p_sendScoreboard_1_.getObjectiveForSlot(i);

            if (scoreboardobjective != null && !hashset.contains(scoreboardobjective))
            {
                for (Packet packet : p_sendScoreboard_1_.getScoreboardScorePacketsForObjective(scoreboardobjective))
                {
                    p_sendScoreboard_2_.playerConnection.sendPacket(packet);
                }

                hashset.add(scoreboardobjective);
            }
        }
    }

    public void setPlayerFileData(WorldServer[] p_setPlayerFileData_1_)
    {
        if (this.playerFileData == null)
        {
            this.playerFileData = p_setPlayerFileData_1_[0].getDataManager().getPlayerFileData();
            p_setPlayerFileData_1_[0].getWorldBorder().a(new IWorldBorderListener()
            {
                public void a(WorldBorder p_a_1_, double p_a_2_)
                {
                    PlayerList.this.sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE));
                }
                public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_, long p_a_6_)
                {
                    PlayerList.this.sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE));
                }
                public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_)
                {
                    PlayerList.this.sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER));
                }
                public void a(WorldBorder p_a_1_, int p_a_2_)
                {
                    PlayerList.this.sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME));
                }
                public void b(WorldBorder p_b_1_, int p_b_2_)
                {
                    PlayerList.this.sendAll(new PacketPlayOutWorldBorder(p_b_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS));
                }
                public void b(WorldBorder p_b_1_, double p_b_2_)
                {
                }
                public void c(WorldBorder p_c_1_, double p_c_2_)
                {
                }
            });
        }
    }

    public void a(EntityPlayer p_a_1_, WorldServer p_a_2_)
    {
        WorldServer worldserver = p_a_1_.u();

        if (p_a_2_ != null)
        {
            p_a_2_.getPlayerChunkMap().removePlayer(p_a_1_);
        }

        worldserver.getPlayerChunkMap().addPlayer(p_a_1_);
        worldserver.chunkProviderServer.getChunkAt((int)p_a_1_.locX >> 4, (int)p_a_1_.locZ >> 4);
    }

    public int d()
    {
        return PlayerChunkMap.getFurthestViewableBlock(this.s());
    }

    public NBTTagCompound a(EntityPlayer p_a_1_)
    {
        NBTTagCompound nbttagcompound = ((WorldServer)this.server.worlds.get(0)).getWorldData().i();
        NBTTagCompound nbttagcompound1;

        if (p_a_1_.getName().equals(this.server.S()) && nbttagcompound != null)
        {
            p_a_1_.f(nbttagcompound);
            nbttagcompound1 = nbttagcompound;
            f.debug("loading single player");
        }
        else
        {
            nbttagcompound1 = this.playerFileData.load(p_a_1_);
        }

        return nbttagcompound1;
    }

    protected void savePlayerFile(EntityPlayer p_savePlayerFile_1_)
    {
        this.playerFileData.save(p_savePlayerFile_1_);
        ServerStatisticManager serverstatisticmanager = (ServerStatisticManager)this.o.get(p_savePlayerFile_1_.getUniqueID());

        if (serverstatisticmanager != null)
        {
            serverstatisticmanager.b();
        }
    }

    public void onPlayerJoin(EntityPlayer p_onPlayerJoin_1_, String p_onPlayerJoin_2_)
    {
        this.players.add(p_onPlayerJoin_1_);
        this.playersByName.put(p_onPlayerJoin_1_.getName(), p_onPlayerJoin_1_);
        this.j.put(p_onPlayerJoin_1_.getUniqueID(), p_onPlayerJoin_1_);
        WorldServer worldserver = this.server.getWorldServer(p_onPlayerJoin_1_.dimension);
        PlayerJoinEvent playerjoinevent = new PlayerJoinEvent(this.cserver.getPlayer(p_onPlayerJoin_1_), p_onPlayerJoin_2_);
        this.cserver.getPluginManager().callEvent(playerjoinevent);
        p_onPlayerJoin_2_ = playerjoinevent.getJoinMessage();

        if (p_onPlayerJoin_2_ != null && p_onPlayerJoin_2_.length() > 0)
        {
            IChatBaseComponent[] aichatbasecomponent;

            for (IChatBaseComponent ichatbasecomponent : aichatbasecomponent = CraftChatMessage.fromString(p_onPlayerJoin_2_))
            {
                this.server.getPlayerList().sendAll(new PacketPlayOutChat(ichatbasecomponent));
            }
        }

        ChunkIOExecutor.adjustPoolSize(this.getPlayerCount());
        PacketPlayOutPlayerInfo packetplayoutplayerinfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] {p_onPlayerJoin_1_});

        for (int i = 0; i < this.players.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);

            if (entityplayer.getBukkitEntity().canSee(p_onPlayerJoin_1_.getBukkitEntity()))
            {
                entityplayer.playerConnection.sendPacket(packetplayoutplayerinfo);
            }

            if (p_onPlayerJoin_1_.getBukkitEntity().canSee(entityplayer.getBukkitEntity()))
            {
                p_onPlayerJoin_1_.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] {entityplayer}));
            }
        }

        if (p_onPlayerJoin_1_.world == worldserver && !worldserver.players.contains(p_onPlayerJoin_1_))
        {
            worldserver.addEntity(p_onPlayerJoin_1_);
            this.a((EntityPlayer)p_onPlayerJoin_1_, (WorldServer)null);
        }
    }

    public void d(EntityPlayer p_d_1_)
    {
        p_d_1_.u().getPlayerChunkMap().movePlayer(p_d_1_);
    }

    public String disconnect(EntityPlayer p_disconnect_1_)
    {
        p_disconnect_1_.b((Statistic)StatisticList.f);
        CraftEventFactory.handleInventoryCloseEvent(p_disconnect_1_);
        PlayerQuitEvent playerquitevent = new PlayerQuitEvent(this.cserver.getPlayer(p_disconnect_1_), "\u00a7e" + p_disconnect_1_.getName() + " left the game.");
        this.cserver.getPluginManager().callEvent(playerquitevent);
        p_disconnect_1_.getBukkitEntity().disconnect(playerquitevent.getQuitMessage());
        this.savePlayerFile(p_disconnect_1_);
        WorldServer worldserver = p_disconnect_1_.u();

        if (p_disconnect_1_.vehicle != null && !(p_disconnect_1_.vehicle instanceof EntityPlayer))
        {
            worldserver.removeEntity(p_disconnect_1_.vehicle);
            f.debug("removing player mount");
        }

        worldserver.kill(p_disconnect_1_);
        worldserver.getPlayerChunkMap().removePlayer(p_disconnect_1_);
        this.players.remove(p_disconnect_1_);
        this.playersByName.remove(p_disconnect_1_.getName());
        UUID uuid = p_disconnect_1_.getUniqueID();
        EntityPlayer entityplayer = (EntityPlayer)this.j.get(uuid);

        if (entityplayer == p_disconnect_1_)
        {
            this.j.remove(uuid);
            this.o.remove(uuid);
        }

        PacketPlayOutPlayerInfo packetplayoutplayerinfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] {p_disconnect_1_});

        for (int i = 0; i < this.players.size(); ++i)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);

            if (entityplayer1.getBukkitEntity().canSee(p_disconnect_1_.getBukkitEntity()))
            {
                entityplayer1.playerConnection.sendPacket(packetplayoutplayerinfo);
            }
            else
            {
                entityplayer1.getBukkitEntity().removeDisconnectingPlayer(p_disconnect_1_.getBukkitEntity());
            }
        }

        this.cserver.getScoreboardManager().removePlayer(p_disconnect_1_.getBukkitEntity());
        ChunkIOExecutor.adjustPoolSize(this.getPlayerCount());
        return playerquitevent.getQuitMessage();
    }

    public EntityPlayer attemptLogin(LoginListener p_attemptLogin_1_, GameProfile p_attemptLogin_2_, String p_attemptLogin_3_)
    {
        UUID uuid = EntityHuman.a(p_attemptLogin_2_);
        ArrayList arraylist = Lists.newArrayList();

        for (int i = 0; i < this.players.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);

            if (entityplayer.getUniqueID().equals(uuid))
            {
                arraylist.add(entityplayer);
            }
        }

        for (EntityPlayer entityplayer2 : arraylist)
        {
            this.savePlayerFile(entityplayer2);
            entityplayer2.playerConnection.disconnect("You logged in from another location");
        }

        SocketAddress socketaddress = p_attemptLogin_1_.networkManager.getSocketAddress();
        EntityPlayer entityplayer1 = new EntityPlayer(this.server, this.server.getWorldServer(0), p_attemptLogin_2_, new PlayerInteractManager(this.server.getWorldServer(0)));
        Player player = entityplayer1.getBukkitEntity();
        PlayerLoginEvent playerloginevent = new PlayerLoginEvent(player, p_attemptLogin_3_, ((InetSocketAddress)socketaddress).getAddress(), ((InetSocketAddress)p_attemptLogin_1_.networkManager.getRawAddress()).getAddress());

        if (this.getProfileBans().isBanned(p_attemptLogin_2_) && !((GameProfileBanEntry)this.getProfileBans().get(p_attemptLogin_2_)).hasExpired())
        {
            GameProfileBanEntry gameprofilebanentry = (GameProfileBanEntry)this.k.get(p_attemptLogin_2_);
            String s1 = "You are banned from this server!\nReason: " + gameprofilebanentry.getReason();

            if (gameprofilebanentry.getExpires() != null)
            {
                s1 = s1 + "\nYour ban will be removed on " + g.format(gameprofilebanentry.getExpires());
            }

            if (!gameprofilebanentry.hasExpired())
            {
                playerloginevent.disallow(Result.KICK_BANNED, s1);
            }
        }
        else if (!this.isWhitelisted(p_attemptLogin_2_))
        {
            playerloginevent.disallow(Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
        }
        else if (this.getIPBans().isBanned(socketaddress) && !this.getIPBans().get(socketaddress).hasExpired())
        {
            IpBanEntry ipbanentry = this.l.get(socketaddress);
            String s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getReason();

            if (ipbanentry.getExpires() != null)
            {
                s = s + "\nYour ban will be removed on " + g.format(ipbanentry.getExpires());
            }

            playerloginevent.disallow(Result.KICK_BANNED, s);
        }
        else if (this.players.size() >= this.maxPlayers && !this.f(p_attemptLogin_2_))
        {
            playerloginevent.disallow(Result.KICK_FULL, SpigotConfig.serverFullMessage);
        }

        this.cserver.getPluginManager().callEvent(playerloginevent);

        if (playerloginevent.getResult() != Result.ALLOWED)
        {
            p_attemptLogin_1_.disconnect(playerloginevent.getKickMessage());
            return null;
        }
        else
        {
            return entityplayer1;
        }
    }

    public EntityPlayer processLogin(GameProfile p_processLogin_1_, EntityPlayer p_processLogin_2_)
    {
        return p_processLogin_2_;
    }

    public EntityPlayer moveToWorld(EntityPlayer p_moveToWorld_1_, int p_moveToWorld_2_, boolean p_moveToWorld_3_)
    {
        return this.moveToWorld(p_moveToWorld_1_, p_moveToWorld_2_, p_moveToWorld_3_, (Location)null, true);
    }

    public EntityPlayer moveToWorld(EntityPlayer p_moveToWorld_1_, int p_moveToWorld_2_, boolean p_moveToWorld_3_, Location p_moveToWorld_4_, boolean p_moveToWorld_5_)
    {
        p_moveToWorld_1_.u().getTracker().untrackPlayer(p_moveToWorld_1_);
        p_moveToWorld_1_.u().getPlayerChunkMap().removePlayer(p_moveToWorld_1_);
        this.players.remove(p_moveToWorld_1_);
        this.playersByName.remove(p_moveToWorld_1_.getName());
        this.server.getWorldServer(p_moveToWorld_1_.dimension).removeEntity(p_moveToWorld_1_);
        BlockPosition blockposition = p_moveToWorld_1_.getBed();
        boolean flag = p_moveToWorld_1_.isRespawnForced();
        EntityPlayer entityplayer = p_moveToWorld_1_;
        org.bukkit.World world = p_moveToWorld_1_.getBukkitEntity().getWorld();
        p_moveToWorld_1_.viewingCredits = false;
        p_moveToWorld_1_.playerConnection = p_moveToWorld_1_.playerConnection;
        p_moveToWorld_1_.copyTo(p_moveToWorld_1_, p_moveToWorld_3_);
        p_moveToWorld_1_.d(p_moveToWorld_1_.getId());
        p_moveToWorld_1_.o(p_moveToWorld_1_);

        if (p_moveToWorld_4_ == null)
        {
            boolean flag1 = false;
            CraftWorld craftworld = (CraftWorld)this.server.server.getWorld(p_moveToWorld_1_.spawnWorld);

            if (craftworld != null && blockposition != null)
            {
                BlockPosition blockposition1 = EntityHuman.getBed(craftworld.getHandle(), blockposition, flag);

                if (blockposition1 != null)
                {
                    flag1 = true;
                    p_moveToWorld_4_ = new Location(craftworld, (double)blockposition1.getX() + 0.5D, (double)blockposition1.getY(), (double)blockposition1.getZ() + 0.5D);
                }
                else
                {
                    p_moveToWorld_1_.setRespawnPosition((BlockPosition)null, true);
                    p_moveToWorld_1_.playerConnection.sendPacket(new PacketPlayOutGameStateChange(0, 0.0F));
                }
            }

            if (p_moveToWorld_4_ == null)
            {
                craftworld = (CraftWorld)this.server.server.getWorlds().get(0);
                blockposition = craftworld.getHandle().getSpawn();
                p_moveToWorld_4_ = new Location(craftworld, (double)blockposition.getX() + 0.5D, (double)blockposition.getY(), (double)blockposition.getZ() + 0.5D);
            }

            Player player = this.cserver.getPlayer(p_moveToWorld_1_);
            PlayerRespawnEvent playerrespawnevent = new PlayerRespawnEvent(player, p_moveToWorld_4_, flag1);
            this.cserver.getPluginManager().callEvent(playerrespawnevent);

            if (p_moveToWorld_1_.playerConnection.isDisconnected())
            {
                return p_moveToWorld_1_;
            }

            p_moveToWorld_4_ = playerrespawnevent.getRespawnLocation();
            p_moveToWorld_1_.reset();
        }
        else
        {
            p_moveToWorld_4_.setWorld(this.server.getWorldServer(p_moveToWorld_2_).getWorld());
        }

        WorldServer worldserver = ((CraftWorld)p_moveToWorld_4_.getWorld()).getHandle();
        p_moveToWorld_1_.setLocation(p_moveToWorld_4_.getX(), p_moveToWorld_4_.getY(), p_moveToWorld_4_.getZ(), p_moveToWorld_4_.getYaw(), p_moveToWorld_4_.getPitch());
        worldserver.chunkProviderServer.getChunkAt((int)p_moveToWorld_1_.locX >> 4, (int)p_moveToWorld_1_.locZ >> 4);

        while (p_moveToWorld_5_ && !worldserver.getCubes(entityplayer, entityplayer.getBoundingBox()).isEmpty() && entityplayer.locY < 256.0D)
        {
            entityplayer.setPosition(entityplayer.locX, entityplayer.locY + 1.0D, entityplayer.locZ);
        }

        byte b0 = (byte)worldserver.getWorld().getEnvironment().getId();

        if (world.getEnvironment() == worldserver.getWorld().getEnvironment())
        {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutRespawn((byte)(b0 >= 0 ? -1 : 0), worldserver.getDifficulty(), worldserver.getWorldData().getType(), p_moveToWorld_1_.playerInteractManager.getGameMode()));
        }

        entityplayer.playerConnection.sendPacket(new PacketPlayOutRespawn(b0, worldserver.getDifficulty(), worldserver.getWorldData().getType(), entityplayer.playerInteractManager.getGameMode()));
        entityplayer.spawnIn(worldserver);
        entityplayer.dead = false;
        entityplayer.playerConnection.teleport(new Location(worldserver.getWorld(), entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch));
        entityplayer.setSneaking(false);
        BlockPosition blockposition2 = worldserver.getSpawn();
        entityplayer.playerConnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition2));
        entityplayer.playerConnection.sendPacket(new PacketPlayOutExperience(entityplayer.exp, entityplayer.expTotal, entityplayer.expLevel));
        this.b(entityplayer, worldserver);

        if (!p_moveToWorld_1_.playerConnection.isDisconnected())
        {
            worldserver.getPlayerChunkMap().addPlayer(entityplayer);
            worldserver.addEntity(entityplayer);
            this.players.add(entityplayer);
            this.playersByName.put(entityplayer.getName(), entityplayer);
            this.j.put(entityplayer.getUniqueID(), entityplayer);
        }

        this.updateClient(p_moveToWorld_1_);
        p_moveToWorld_1_.updateAbilities();

        for (Object object : p_moveToWorld_1_.getEffects())
        {
            MobEffect mobeffect = (MobEffect)object;
            p_moveToWorld_1_.playerConnection.sendPacket(new PacketPlayOutEntityEffect(p_moveToWorld_1_.getId(), mobeffect));
        }

        entityplayer.setHealth(entityplayer.getHealth());

        if (world != p_moveToWorld_4_.getWorld())
        {
            PlayerChangedWorldEvent playerchangedworldevent = new PlayerChangedWorldEvent(p_moveToWorld_1_.getBukkitEntity(), world);
            this.server.server.getPluginManager().callEvent(playerchangedworldevent);
        }

        if (p_moveToWorld_1_.playerConnection.isDisconnected())
        {
            this.savePlayerFile(p_moveToWorld_1_);
        }

        return entityplayer;
    }

    public void changeDimension(EntityPlayer p_changeDimension_1_, int p_changeDimension_2_, TeleportCause p_changeDimension_3_)
    {
        WorldServer worldserver = null;

        if (p_changeDimension_1_.dimension < 10)
        {
            for (WorldServer worldserver1 : this.server.worlds)
            {
                if (worldserver1.dimension == p_changeDimension_2_)
                {
                    worldserver = worldserver1;
                }
            }
        }

        Location location1 = p_changeDimension_1_.getBukkitEntity().getLocation();
        Location location = null;
        boolean flag = false;

        if (worldserver != null)
        {
            if (p_changeDimension_3_ == TeleportCause.END_PORTAL && p_changeDimension_2_ == 0)
            {
                location = p_changeDimension_1_.getBukkitEntity().getBedSpawnLocation();

                if (location == null || ((CraftWorld)location.getWorld()).getHandle().dimension != 0)
                {
                    location = worldserver.getWorld().getSpawnLocation();
                }
            }
            else
            {
                location = this.calculateTarget(location1, worldserver);
                flag = true;
            }
        }

        TravelAgent travelagent = location != null ? (TravelAgent)((CraftWorld)location.getWorld()).getHandle().getTravelAgent() : CraftTravelAgent.DEFAULT;
        PlayerPortalEvent playerportalevent = new PlayerPortalEvent(p_changeDimension_1_.getBukkitEntity(), location1, location, travelagent, p_changeDimension_3_);
        playerportalevent.useTravelAgent(flag);
        Bukkit.getServer().getPluginManager().callEvent(playerportalevent);

        if (!playerportalevent.isCancelled() && playerportalevent.getTo() != null)
        {
            location = playerportalevent.useTravelAgent() ? playerportalevent.getPortalTravelAgent().findOrCreate(playerportalevent.getTo()) : playerportalevent.getTo();

            if (location != null)
            {
                worldserver = ((CraftWorld)location.getWorld()).getHandle();
                PlayerTeleportEvent playerteleportevent = new PlayerTeleportEvent(p_changeDimension_1_.getBukkitEntity(), location1, location, p_changeDimension_3_);
                Bukkit.getServer().getPluginManager().callEvent(playerteleportevent);

                if (!playerteleportevent.isCancelled() && playerteleportevent.getTo() != null)
                {
                    Vector vector = p_changeDimension_1_.getBukkitEntity().getVelocity();
                    boolean flag1 = worldserver.chunkProviderServer.forceChunkLoad;
                    worldserver.chunkProviderServer.forceChunkLoad = true;
                    worldserver.getTravelAgent().adjustExit(p_changeDimension_1_, location, vector);
                    worldserver.chunkProviderServer.forceChunkLoad = flag1;
                    this.moveToWorld(p_changeDimension_1_, worldserver.dimension, true, location, false);

                    if (p_changeDimension_1_.motX != vector.getX() || p_changeDimension_1_.motY != vector.getY() || p_changeDimension_1_.motZ != vector.getZ())
                    {
                        p_changeDimension_1_.getBukkitEntity().setVelocity(vector);
                    }
                }
            }
        }
    }

    public void changeWorld(Entity p_changeWorld_1_, int p_changeWorld_2_, WorldServer p_changeWorld_3_, WorldServer p_changeWorld_4_)
    {
        Location location = this.calculateTarget(p_changeWorld_1_.getBukkitEntity().getLocation(), p_changeWorld_4_);
        this.repositionEntity(p_changeWorld_1_, location, true);
    }

    public Location calculateTarget(Location p_calculateTarget_1_, World p_calculateTarget_2_)
    {
        WorldServer worldserver = ((CraftWorld)p_calculateTarget_1_.getWorld()).getHandle();
        WorldServer worldserver1 = p_calculateTarget_2_.getWorld().getHandle();
        int i = worldserver.dimension;
        double d0 = p_calculateTarget_1_.getY();
        float f = p_calculateTarget_1_.getYaw();
        float f1 = p_calculateTarget_1_.getPitch();
        double d1 = p_calculateTarget_1_.getX();
        double d2 = p_calculateTarget_1_.getZ();
        double d3 = 8.0D;

        if (worldserver1.dimension == -1)
        {
            d1 = MathHelper.a(d1 / d3, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
            d2 = MathHelper.a(d2 / d3, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
        }
        else if (worldserver1.dimension == 0)
        {
            d1 = MathHelper.a(d1 * d3, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
            d2 = MathHelper.a(d2 * d3, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
        }
        else
        {
            BlockPosition blockposition;

            if (i == 1)
            {
                worldserver1 = (WorldServer)this.server.worlds.get(0);
                blockposition = worldserver1.getSpawn();
            }
            else
            {
                blockposition = worldserver1.getDimensionSpawn();
            }

            d1 = (double)blockposition.getX();
            d0 = (double)blockposition.getY();
            d2 = (double)blockposition.getZ();
        }

        if (i != 1)
        {
            worldserver.methodProfiler.a("placing");
            d1 = (double)MathHelper.clamp((int)d1, -29999872, 29999872);
            d2 = (double)MathHelper.clamp((int)d2, -29999872, 29999872);
        }

        return new Location(worldserver1.getWorld(), d1, d0, d2, f, f1);
    }

    public void repositionEntity(Entity p_repositionEntity_1_, Location p_repositionEntity_2_, boolean p_repositionEntity_3_)
    {
        WorldServer worldserver = (WorldServer)p_repositionEntity_1_.world;
        WorldServer worldserver1 = ((CraftWorld)p_repositionEntity_2_.getWorld()).getHandle();
        int i = worldserver.dimension;
        p_repositionEntity_1_.setPositionRotation(p_repositionEntity_2_.getX(), p_repositionEntity_2_.getY(), p_repositionEntity_2_.getZ(), p_repositionEntity_2_.getYaw(), p_repositionEntity_2_.getPitch());

        if (p_repositionEntity_1_.isAlive())
        {
            worldserver.entityJoinedWorld(p_repositionEntity_1_, false);
        }

        worldserver.methodProfiler.b();

        if (i != 1)
        {
            worldserver.methodProfiler.a("placing");

            if (p_repositionEntity_1_.isAlive())
            {
                if (p_repositionEntity_3_)
                {
                    Vector vector = p_repositionEntity_1_.getBukkitEntity().getVelocity();
                    worldserver1.getTravelAgent().adjustExit(p_repositionEntity_1_, p_repositionEntity_2_, vector);
                    p_repositionEntity_1_.setPositionRotation(p_repositionEntity_2_.getX(), p_repositionEntity_2_.getY(), p_repositionEntity_2_.getZ(), p_repositionEntity_2_.getYaw(), p_repositionEntity_2_.getPitch());

                    if (p_repositionEntity_1_.motX != vector.getX() || p_repositionEntity_1_.motY != vector.getY() || p_repositionEntity_1_.motZ != vector.getZ())
                    {
                        p_repositionEntity_1_.getBukkitEntity().setVelocity(vector);
                    }
                }

                worldserver1.addEntity(p_repositionEntity_1_);
                worldserver1.entityJoinedWorld(p_repositionEntity_1_, false);
            }

            worldserver.methodProfiler.b();
        }

        p_repositionEntity_1_.spawnIn(worldserver1);
    }

    public void tick()
    {
        if (++this.u > 600)
        {
            this.sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY, this.players));
            this.u = 0;
        }
    }

    public void sendAll(Packet p_sendAll_1_)
    {
        for (int i = 0; i < this.players.size(); ++i)
        {
            ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(p_sendAll_1_);
        }
    }

    public void sendAll(Packet p_sendAll_1_, EntityHuman p_sendAll_2_)
    {
        for (int i = 0; i < this.players.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);

            if (p_sendAll_2_ == null || !(p_sendAll_2_ instanceof EntityPlayer) || entityplayer.getBukkitEntity().canSee(((EntityPlayer)p_sendAll_2_).getBukkitEntity()))
            {
                ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(p_sendAll_1_);
            }
        }
    }

    public void sendAll(Packet p_sendAll_1_, World p_sendAll_2_)
    {
        for (int i = 0; i < p_sendAll_2_.players.size(); ++i)
        {
            ((EntityPlayer)p_sendAll_2_.players.get(i)).playerConnection.sendPacket(p_sendAll_1_);
        }
    }

    public void a(Packet p_a_1_, int p_a_2_)
    {
        for (int i = 0; i < this.players.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);

            if (entityplayer.dimension == p_a_2_)
            {
                entityplayer.playerConnection.sendPacket(p_a_1_);
            }
        }
    }

    public void a(EntityHuman p_a_1_, IChatBaseComponent p_a_2_)
    {
        ScoreboardTeamBase scoreboardteambase = p_a_1_.getScoreboardTeam();

        if (scoreboardteambase != null)
        {
            for (String s : scoreboardteambase.getPlayerNameSet())
            {
                EntityPlayer entityplayer = this.getPlayer(s);

                if (entityplayer != null && entityplayer != p_a_1_)
                {
                    entityplayer.sendMessage(p_a_2_);
                }
            }
        }
    }

    public void b(EntityHuman p_b_1_, IChatBaseComponent p_b_2_)
    {
        ScoreboardTeamBase scoreboardteambase = p_b_1_.getScoreboardTeam();

        if (scoreboardteambase == null)
        {
            this.sendMessage(p_b_2_);
        }
        else
        {
            for (int i = 0; i < this.players.size(); ++i)
            {
                EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);

                if (entityplayer.getScoreboardTeam() != scoreboardteambase)
                {
                    entityplayer.sendMessage(p_b_2_);
                }
            }
        }
    }

    public String b(boolean p_b_1_)
    {
        String s = "";
        ArrayList arraylist = Lists.newArrayList(this.players);

        for (int i = 0; i < arraylist.size(); ++i)
        {
            if (i > 0)
            {
                s = s + ", ";
            }

            s = s + ((EntityPlayer)arraylist.get(i)).getName();

            if (p_b_1_)
            {
                s = s + " (" + ((EntityPlayer)arraylist.get(i)).getUniqueID().toString() + ")";
            }
        }

        return s;
    }

    public String[] f()
    {
        String[] astring = new String[this.players.size()];

        for (int i = 0; i < this.players.size(); ++i)
        {
            astring[i] = ((EntityPlayer)this.players.get(i)).getName();
        }

        return astring;
    }

    public GameProfile[] g()
    {
        GameProfile[] agameprofile = new GameProfile[this.players.size()];

        for (int i = 0; i < this.players.size(); ++i)
        {
            agameprofile[i] = ((EntityPlayer)this.players.get(i)).getProfile();
        }

        return agameprofile;
    }

    public GameProfileBanList getProfileBans()
    {
        return this.k;
    }

    public IpBanList getIPBans()
    {
        return this.l;
    }

    public void addOp(GameProfile p_addOp_1_)
    {
        this.operators.add(new OpListEntry(p_addOp_1_, this.server.p(), this.operators.b(p_addOp_1_)));
        Player player = this.server.server.getPlayer(p_addOp_1_.getId());

        if (player != null)
        {
            player.recalculatePermissions();
        }
    }

    public void removeOp(GameProfile p_removeOp_1_)
    {
        this.operators.remove(p_removeOp_1_);
        Player player = this.server.server.getPlayer(p_removeOp_1_.getId());

        if (player != null)
        {
            player.recalculatePermissions();
        }
    }

    public boolean isWhitelisted(GameProfile p_isWhitelisted_1_)
    {
        return !this.hasWhitelist || this.operators.d(p_isWhitelisted_1_) || this.whitelist.d(p_isWhitelisted_1_);
    }

    public boolean isOp(GameProfile p_isOp_1_)
    {
        return this.operators.d(p_isOp_1_) || this.server.T() && ((WorldServer)this.server.worlds.get(0)).getWorldData().v() && this.server.S().equalsIgnoreCase(p_isOp_1_.getName()) || this.t;
    }

    public EntityPlayer getPlayer(String p_getPlayer_1_)
    {
        return (EntityPlayer)this.playersByName.get(p_getPlayer_1_);
    }

    public void sendPacketNearby(double p_sendPacketNearby_1_, double p_sendPacketNearby_3_, double p_sendPacketNearby_5_, double p_sendPacketNearby_7_, int p_sendPacketNearby_9_, Packet p_sendPacketNearby_10_)
    {
        this.sendPacketNearby((EntityHuman)null, p_sendPacketNearby_1_, p_sendPacketNearby_3_, p_sendPacketNearby_5_, p_sendPacketNearby_7_, p_sendPacketNearby_9_, p_sendPacketNearby_10_);
    }

    public void sendPacketNearby(EntityHuman p_sendPacketNearby_1_, double p_sendPacketNearby_2_, double p_sendPacketNearby_4_, double p_sendPacketNearby_6_, double p_sendPacketNearby_8_, int p_sendPacketNearby_10_, Packet p_sendPacketNearby_11_)
    {
        for (int i = 0; i < this.players.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);

            if ((p_sendPacketNearby_1_ == null || !(p_sendPacketNearby_1_ instanceof EntityPlayer) || entityplayer.getBukkitEntity().canSee(((EntityPlayer)p_sendPacketNearby_1_).getBukkitEntity())) && entityplayer != p_sendPacketNearby_1_ && entityplayer.dimension == p_sendPacketNearby_10_)
            {
                double d0 = p_sendPacketNearby_2_ - entityplayer.locX;
                double d1 = p_sendPacketNearby_4_ - entityplayer.locY;
                double d2 = p_sendPacketNearby_6_ - entityplayer.locZ;

                if (d0 * d0 + d1 * d1 + d2 * d2 < p_sendPacketNearby_8_ * p_sendPacketNearby_8_)
                {
                    entityplayer.playerConnection.sendPacket(p_sendPacketNearby_11_);
                }
            }
        }
    }

    public void savePlayers()
    {
        for (int i = 0; i < this.players.size(); ++i)
        {
            this.savePlayerFile((EntityPlayer)this.players.get(i));
        }
    }

    public void addWhitelist(GameProfile p_addWhitelist_1_)
    {
        this.whitelist.add(new WhiteListEntry(p_addWhitelist_1_));
    }

    public void removeWhitelist(GameProfile p_removeWhitelist_1_)
    {
        this.whitelist.remove(p_removeWhitelist_1_);
    }

    public WhiteList getWhitelist()
    {
        return this.whitelist;
    }

    public String[] getWhitelisted()
    {
        return this.whitelist.getEntries();
    }

    public OpList getOPs()
    {
        return this.operators;
    }

    public String[] n()
    {
        return this.operators.getEntries();
    }

    public void reloadWhitelist()
    {
    }

    public void b(EntityPlayer p_b_1_, WorldServer p_b_2_)
    {
        WorldBorder worldborder = p_b_1_.world.getWorldBorder();
        p_b_1_.playerConnection.sendPacket(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE));
        p_b_1_.playerConnection.sendPacket(new PacketPlayOutUpdateTime(p_b_2_.getTime(), p_b_2_.getDayTime(), p_b_2_.getGameRules().getBoolean("doDaylightCycle")));

        if (p_b_2_.S())
        {
            p_b_1_.setPlayerWeather(WeatherType.DOWNFALL, false);
            p_b_1_.updateWeather(-p_b_2_.p, p_b_2_.p, -p_b_2_.r, p_b_2_.r);
        }
    }

    public void updateClient(EntityPlayer p_updateClient_1_)
    {
        p_updateClient_1_.updateInventory(p_updateClient_1_.defaultContainer);
        p_updateClient_1_.getBukkitEntity().updateScaledHealth();
        p_updateClient_1_.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(p_updateClient_1_.inventory.itemInHandIndex));
    }

    public int getPlayerCount()
    {
        return this.players.size();
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public String[] getSeenPlayers()
    {
        return ((WorldServer)this.server.worlds.get(0)).getDataManager().getPlayerFileData().getSeenPlayers();
    }

    public boolean getHasWhitelist()
    {
        return this.hasWhitelist;
    }

    public void setHasWhitelist(boolean p_setHasWhitelist_1_)
    {
        this.hasWhitelist = p_setHasWhitelist_1_;
    }

    public List<EntityPlayer> b(String p_b_1_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (EntityPlayer entityplayer : this.players)
        {
            if (entityplayer.w().equals(p_b_1_))
            {
                arraylist.add(entityplayer);
            }
        }

        return arraylist;
    }

    public int s()
    {
        return this.r;
    }

    public MinecraftServer getServer()
    {
        return this.server;
    }

    public NBTTagCompound t()
    {
        return null;
    }

    private void a(EntityPlayer p_a_1_, EntityPlayer p_a_2_, World p_a_3_)
    {
        if (p_a_2_ != null)
        {
            p_a_1_.playerInteractManager.setGameMode(p_a_2_.playerInteractManager.getGameMode());
        }
        else if (this.s != null)
        {
            p_a_1_.playerInteractManager.setGameMode(this.s);
        }

        p_a_1_.playerInteractManager.b(p_a_3_.getWorldData().getGameType());
    }

    public void u()
    {
        for (int i = 0; i < this.players.size(); ++i)
        {
            ((EntityPlayer)this.players.get(i)).playerConnection.disconnect(this.server.server.getShutdownMessage());
        }
    }

    public void sendMessage(IChatBaseComponent[] p_sendMessage_1_)
    {
        for (IChatBaseComponent ichatbasecomponent : p_sendMessage_1_)
        {
            this.sendMessage(ichatbasecomponent, true);
        }
    }

    public void sendMessage(IChatBaseComponent p_sendMessage_1_, boolean p_sendMessage_2_)
    {
        this.server.sendMessage(p_sendMessage_1_);
        int i = p_sendMessage_2_ ? 1 : 0;
        this.sendAll(new PacketPlayOutChat(CraftChatMessage.fixComponent(p_sendMessage_1_), (byte)i));
    }

    public void sendMessage(IChatBaseComponent p_sendMessage_1_)
    {
        this.sendMessage(p_sendMessage_1_, true);
    }

    public ServerStatisticManager a(EntityHuman p_a_1_)
    {
        UUID uuid = p_a_1_.getUniqueID();
        ServerStatisticManager serverstatisticmanager = uuid == null ? null : (ServerStatisticManager)this.o.get(uuid);

        if (serverstatisticmanager == null)
        {
            File file1 = new File(this.server.getWorldServer(0).getDataManager().getDirectory(), "stats");
            File file2 = new File(file1, uuid.toString() + ".json");

            if (!file2.exists())
            {
                File file3 = new File(file1, p_a_1_.getName() + ".json");

                if (file3.exists() && file3.isFile())
                {
                    file3.renameTo(file2);
                }
            }

            serverstatisticmanager = new ServerStatisticManager(this.server, file2);
            serverstatisticmanager.a();
            this.o.put(uuid, serverstatisticmanager);
        }

        return serverstatisticmanager;
    }

    public void a(int p_a_1_)
    {
        this.r = p_a_1_;

        if (this.server.worldServer != null)
        {
            WorldServer[] aworldserver = this.server.worldServer;
            int jx = aworldserver.length;

            for (int i = 0; i < this.server.worlds.size(); ++i)
            {
                WorldServer worldserver = (WorldServer)this.server.worlds.get(0);

                if (worldserver != null)
                {
                    worldserver.getPlayerChunkMap().a(p_a_1_);
                }
            }
        }
    }

    public List<EntityPlayer> v()
    {
        return this.players;
    }

    public EntityPlayer a(UUID p_a_1_)
    {
        return (EntityPlayer)this.j.get(p_a_1_);
    }

    public boolean f(GameProfile p_f_1_)
    {
        return false;
    }
}

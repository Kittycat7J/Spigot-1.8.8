package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketStatusInListener;
import net.minecraft.server.v1_8_R3.PacketStatusInPing;
import net.minecraft.server.v1_8_R3.PacketStatusInStart;
import net.minecraft.server.v1_8_R3.PacketStatusOutPong;
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo;
import net.minecraft.server.v1_8_R3.ServerPing;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftIconCache;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

public class PacketStatusListener implements PacketStatusInListener {
   private static final IChatBaseComponent a = new ChatComponentText("Status request has been handled.");
   private final MinecraftServer minecraftServer;
   private final NetworkManager networkManager;
   private boolean d;

   public PacketStatusListener(MinecraftServer p_i83_1_, NetworkManager p_i83_2_) {
      this.minecraftServer = p_i83_1_;
      this.networkManager = p_i83_2_;
   }

   public void a(IChatBaseComponent p_a_1_) {
   }

   public void a(PacketStatusInStart p_a_1_) {
      if(this.d) {
         this.networkManager.close(a);
      } else {
         this.d = true;
         final Object[] aobject = this.minecraftServer.getPlayerList().players.toArray();
         class 1ServerListPingEvent extends ServerListPingEvent {
            CraftIconCache icon;

            _ServerListPingEvent/* $FF was: 1ServerListPingEvent*/() {
               super(((InetSocketAddress)PacketStatusListener.this.networkManager.getSocketAddress()).getAddress(), PacketStatusListener.this.minecraftServer.getMotd(), PacketStatusListener.this.minecraftServer.getPlayerList().getMaxPlayers());
               this.icon = PacketStatusListener.this.minecraftServer.server.getServerIcon();
            }

            public void setServerIcon(CachedServerIcon p_setServerIcon_1_) {
               if(!(p_setServerIcon_1_ instanceof CraftIconCache)) {
                  throw new IllegalArgumentException(p_setServerIcon_1_ + " was not created by " + CraftServer.class);
               } else {
                  this.icon = (CraftIconCache)p_setServerIcon_1_;
               }
            }

            public Iterator<Player> iterator() throws UnsupportedOperationException {
               return new Iterator<Player>() {
                  int i;
                  int ret = Integer.MIN_VALUE;
                  EntityPlayer player;

                  public boolean hasNext() {
                     if(this.player != null) {
                        return true;
                     } else {
                        Object[] aobject = aobject;
                        int i = aobject.length;

                        for(int j = this.i; j < i; ++j) {
                           EntityPlayer entityplayer = (EntityPlayer)aobject[j];
                           if(entityplayer != null) {
                              this.i = j + 1;
                              this.player = entityplayer;
                              return true;
                           }
                        }

                        return false;
                     }
                  }

                  public Player next() {
                     if(!this.hasNext()) {
                        throw new NoSuchElementException();
                     } else {
                        EntityPlayer entityplayer = this.player;
                        this.player = null;
                        this.ret = this.i - 1;
                        return entityplayer.getBukkitEntity();
                     }
                  }

                  public void remove() {
                     Object[] aobject = aobject;
                     int i = this.ret;
                     if(i >= 0 && aobject[i] != null) {
                        aobject[i] = null;
                     } else {
                        throw new IllegalStateException();
                     }
                  }
               };
            }
         }

         1ServerListPingEvent packetstatuslistener$1serverlistpingevent = new 1ServerListPingEvent();
         this.minecraftServer.server.getPluginManager().callEvent(packetstatuslistener$1serverlistpingevent);
         List<GameProfile> list = new ArrayList(aobject.length);

         for(Object object : aobject) {
            if(object != null) {
               list.add(((EntityPlayer)object).getProfile());
            }
         }

         ServerPing.ServerPingPlayerSample serverping$serverpingplayersample = new ServerPing.ServerPingPlayerSample(packetstatuslistener$1serverlistpingevent.getMaxPlayers(), list.size());
         if(!list.isEmpty()) {
            Collections.shuffle(list);
            list = list.subList(0, Math.min(list.size(), SpigotConfig.playerSample));
         }

         serverping$serverpingplayersample.a((GameProfile[])list.toArray(new GameProfile[list.size()]));
         ServerPing serverping = new ServerPing();
         serverping.setFavicon(packetstatuslistener$1serverlistpingevent.icon.value);
         serverping.setMOTD(new ChatComponentText(packetstatuslistener$1serverlistpingevent.getMotd()));
         serverping.setPlayerSample(serverping$serverpingplayersample);
         serverping.setServerInfo(new ServerPing.ServerData(this.minecraftServer.getServerModName() + " " + this.minecraftServer.getVersion(), 47));
         this.networkManager.handle(new PacketStatusOutServerInfo(serverping));
      }
   }

   public void a(PacketStatusInPing p_a_1_) {
      this.networkManager.handle(new PacketStatusOutPong(p_a_1_.a()));
      this.networkManager.close(a);
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.properties.Property;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import javax.crypto.SecretKey;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.MinecraftEncryption;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginInListener;
import net.minecraft.server.v1_8_R3.PacketLoginInStart;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;

public class LoginListener implements PacketLoginInListener, IUpdatePlayerListBox {
   private static final AtomicInteger b = new AtomicInteger(0);
   private static final Logger c = LogManager.getLogger();
   private static final Random random = new Random();
   private final byte[] e = new byte[4];
   private final MinecraftServer server;
   public final NetworkManager networkManager;
   private LoginListener.EnumProtocolState g = LoginListener.EnumProtocolState.HELLO;
   private int h;
   private GameProfile i;
   private String j = "";
   private SecretKey loginKey;
   private EntityPlayer l;
   public String hostname = "";

   public LoginListener(MinecraftServer p_i462_1_, NetworkManager p_i462_2_) {
      this.server = p_i462_1_;
      this.networkManager = p_i462_2_;
      random.nextBytes(this.e);
   }

   public void c() {
      if(this.g == LoginListener.EnumProtocolState.READY_TO_ACCEPT) {
         this.b();
      } else if(this.g == LoginListener.EnumProtocolState.e) {
         EntityPlayer entityplayer = this.server.getPlayerList().a(this.i.getId());
         if(entityplayer == null) {
            this.g = LoginListener.EnumProtocolState.READY_TO_ACCEPT;
            this.server.getPlayerList().a(this.networkManager, this.l);
            this.l = null;
         }
      }

      if(this.h++ == 600) {
         this.disconnect("Took too long to log in");
      }

   }

   public void disconnect(String p_disconnect_1_) {
      try {
         c.info("Disconnecting " + this.d() + ": " + p_disconnect_1_);
         ChatComponentText chatcomponenttext = new ChatComponentText(p_disconnect_1_);
         this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
         this.networkManager.close(chatcomponenttext);
      } catch (Exception exception) {
         c.error((String)"Error whilst disconnecting player", (Throwable)exception);
      }

   }

   public void initUUID() {
      UUID uuid;
      if(this.networkManager.spoofedUUID != null) {
         uuid = this.networkManager.spoofedUUID;
      } else {
         uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.i.getName()).getBytes(Charsets.UTF_8));
      }

      this.i = new GameProfile(uuid, this.i.getName());
      if(this.networkManager.spoofedProfile != null) {
         for(Property property : this.networkManager.spoofedProfile) {
            this.i.getProperties().put(property.getName(), property);
         }
      }

   }

   public void b() {
      EntityPlayer entityplayer = this.server.getPlayerList().attemptLogin(this, this.i, this.hostname);
      if(entityplayer != null) {
         this.g = LoginListener.EnumProtocolState.ACCEPTED;
         if(this.server.aK() >= 0 && !this.networkManager.c()) {
            this.networkManager.a(new PacketLoginOutSetCompression(this.server.aK()), new ChannelFutureListener() {
               public void a(ChannelFuture p_a_1_) throws Exception {
                  LoginListener.this.networkManager.a(LoginListener.this.server.aK());
               }

               public void operationComplete(ChannelFuture p_operationComplete_1_) throws Exception {
                  this.a(p_operationComplete_1_);
               }
            }, new GenericFutureListener[0]);
         }

         this.networkManager.handle(new PacketLoginOutSuccess(this.i));
         EntityPlayer entityplayer1 = this.server.getPlayerList().a(this.i.getId());
         if(entityplayer1 != null) {
            this.g = LoginListener.EnumProtocolState.e;
            this.l = this.server.getPlayerList().processLogin(this.i, entityplayer);
         } else {
            this.server.getPlayerList().a(this.networkManager, this.server.getPlayerList().processLogin(this.i, entityplayer));
         }
      }

   }

   public void a(IChatBaseComponent p_a_1_) {
      c.info(this.d() + " lost connection: " + p_a_1_.c());
   }

   public String d() {
      return this.i != null?this.i.toString() + " (" + this.networkManager.getSocketAddress().toString() + ")":String.valueOf(this.networkManager.getSocketAddress());
   }

   public void a(PacketLoginInStart p_a_1_) {
      Validate.validState(this.g == LoginListener.EnumProtocolState.HELLO, "Unexpected hello packet", new Object[0]);
      this.i = p_a_1_.a();
      if(this.server.getOnlineMode() && !this.networkManager.c()) {
         this.g = LoginListener.EnumProtocolState.KEY;
         this.networkManager.handle(new PacketLoginOutEncryptionBegin(this.j, this.server.Q().getPublic(), this.e));
      } else {
         this.initUUID();
         (new Thread(new Runnable() {
            public void run() {
               try {
                  (LoginListener.this.new LoginHandler()).fireEvents();
               } catch (Exception exception) {
                  LoginListener.this.disconnect("Failed to verify username!");
                  LoginListener.this.server.server.getLogger().log(Level.WARNING, "Exception verifying " + LoginListener.this.i.getName(), exception);
               }

            }
         })).start();
      }

   }

   public void a(PacketLoginInEncryptionBegin p_a_1_) {
      Validate.validState(this.g == LoginListener.EnumProtocolState.KEY, "Unexpected key packet", new Object[0]);
      PrivateKey privatekey = this.server.Q().getPrivate();
      if(!Arrays.equals(this.e, p_a_1_.b(privatekey))) {
         throw new IllegalStateException("Invalid nonce!");
      } else {
         this.loginKey = p_a_1_.a(privatekey);
         this.g = LoginListener.EnumProtocolState.AUTHENTICATING;
         this.networkManager.a(this.loginKey);
         (new Thread("User Authenticator #" + b.incrementAndGet()) {
            public void run() {
               GameProfile gameprofile = LoginListener.this.i;

               try {
                  String s = (new BigInteger(MinecraftEncryption.a(LoginListener.this.j, LoginListener.this.server.Q().getPublic(), LoginListener.this.loginKey))).toString(16);
                  LoginListener.this.i = LoginListener.this.server.aD().hasJoinedServer(new GameProfile((UUID)null, gameprofile.getName()), s);
                  if(LoginListener.this.i != null) {
                     if(!LoginListener.this.networkManager.g()) {
                        return;
                     }

                     (LoginListener.this.new LoginHandler()).fireEvents();
                  } else if(LoginListener.this.server.T()) {
                     LoginListener.c.warn("Failed to verify username but will let them in anyway!");
                     LoginListener.this.i = LoginListener.this.a(gameprofile);
                     LoginListener.this.g = LoginListener.EnumProtocolState.READY_TO_ACCEPT;
                  } else {
                     LoginListener.this.disconnect("Failed to verify username!");
                     LoginListener.c.error("Username \'" + gameprofile.getName() + "\' tried to join with an invalid session");
                  }
               } catch (AuthenticationUnavailableException var3) {
                  if(LoginListener.this.server.T()) {
                     LoginListener.c.warn("Authentication servers are down but will let them in anyway!");
                     LoginListener.this.i = LoginListener.this.a(gameprofile);
                     LoginListener.this.g = LoginListener.EnumProtocolState.READY_TO_ACCEPT;
                  } else {
                     LoginListener.this.disconnect("Authentication servers are down. Please try again later, sorry!");
                     LoginListener.c.error("Couldn\'t verify username because servers are unavailable");
                  }
               } catch (Exception exception) {
                  LoginListener.this.disconnect("Failed to verify username!");
                  LoginListener.this.server.server.getLogger().log(Level.WARNING, "Exception verifying " + gameprofile.getName(), exception);
               }

            }
         }).start();
      }
   }

   protected GameProfile a(GameProfile p_a_1_) {
      UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_a_1_.getName()).getBytes(Charsets.UTF_8));
      return new GameProfile(uuid, p_a_1_.getName());
   }

   static enum EnumProtocolState {
      HELLO,
      KEY,
      AUTHENTICATING,
      READY_TO_ACCEPT,
      e,
      ACCEPTED;
   }

   public class LoginHandler {
      public void fireEvents() throws Exception {
         String s = LoginListener.this.i.getName();
         InetAddress inetaddress = ((InetSocketAddress)LoginListener.this.networkManager.getSocketAddress()).getAddress();
         UUID uuid = LoginListener.this.i.getId();
         final CraftServer craftserver = LoginListener.this.server.server;
         AsyncPlayerPreLoginEvent asyncplayerpreloginevent = new AsyncPlayerPreLoginEvent(s, inetaddress, uuid);
         craftserver.getPluginManager().callEvent(asyncplayerpreloginevent);
         if(PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
            final PlayerPreLoginEvent playerpreloginevent = new PlayerPreLoginEvent(s, inetaddress, uuid);
            if(asyncplayerpreloginevent.getResult() != Result.ALLOWED) {
               playerpreloginevent.disallow(asyncplayerpreloginevent.getResult(), asyncplayerpreloginevent.getKickMessage());
            }

            Waitable<Result> waitable = new Waitable<Result>() {
               protected Result evaluate() {
                  craftserver.getPluginManager().callEvent(playerpreloginevent);
                  return playerpreloginevent.getResult();
               }
            };
            LoginListener.this.server.processQueue.add(waitable);
            if(waitable.get() != Result.ALLOWED) {
               LoginListener.this.disconnect(playerpreloginevent.getKickMessage());
               return;
            }
         } else if(asyncplayerpreloginevent.getLoginResult() != org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            LoginListener.this.disconnect(asyncplayerpreloginevent.getKickMessage());
            return;
         }

         LoginListener.c.info("UUID of player " + LoginListener.this.i.getName() + " is " + LoginListener.this.i.getId());
         LoginListener.this.g = LoginListener.EnumProtocolState.READY_TO_ACCEPT;
      }
   }
}

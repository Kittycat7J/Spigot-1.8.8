package net.minecraft.server.v1_8_R3;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.LoginListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketHandshakingInListener;
import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R3.PacketStatusListener;
import org.apache.logging.log4j.LogManager;
import org.spigotmc.SpigotConfig;

public class HandshakeListener implements PacketHandshakingInListener {
   private static final Gson gson = new Gson();
   private static final HashMap<InetAddress, Long> throttleTracker = new HashMap();
   private static int throttleCounter = 0;
   private final MinecraftServer a;
   private final NetworkManager b;

   public HandshakeListener(MinecraftServer p_i435_1_, NetworkManager p_i435_2_) {
      this.a = p_i435_1_;
      this.b = p_i435_2_;
   }

   public void a(PacketHandshakingInSetProtocol p_a_1_) {
      switch(HandshakeListener.SyntheticClass_1.a[p_a_1_.a().ordinal()]) {
      case 1:
         this.b.a(EnumProtocol.LOGIN);

         try {
            long i = System.currentTimeMillis();
            long j = MinecraftServer.getServer().server.getConnectionThrottle();
            InetAddress inetaddress = ((InetSocketAddress)this.b.getSocketAddress()).getAddress();
            synchronized(throttleTracker) {
               if(throttleTracker.containsKey(inetaddress) && !"127.0.0.1".equals(inetaddress.getHostAddress()) && i - ((Long)throttleTracker.get(inetaddress)).longValue() < j) {
                  throttleTracker.put(inetaddress, Long.valueOf(i));
                  ChatComponentText chatcomponenttext3 = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
                  this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext3));
                  this.b.close(chatcomponenttext3);
                  return;
               }

               throttleTracker.put(inetaddress, Long.valueOf(i));
               ++throttleCounter;
               if(throttleCounter > 200) {
                  throttleCounter = 0;
                  Iterator iterator = throttleTracker.entrySet().iterator();

                  while(iterator.hasNext()) {
                     Entry<InetAddress, Long> entry = (Entry)iterator.next();
                     if(((Long)entry.getValue()).longValue() > j) {
                        iterator.remove();
                     }
                  }
               }
            }
         } catch (Throwable throwable) {
            LogManager.getLogger().debug("Failed to check connection throttle", throwable);
         }

         if(p_a_1_.b() > 47) {
            ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedServerMessage.replaceAll("\'", "\'\'"), new Object[]{"1.8.8"}));
            this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
            this.b.close(chatcomponenttext);
         } else if(p_a_1_.b() < 47) {
            ChatComponentText chatcomponenttext1 = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedClientMessage.replaceAll("\'", "\'\'"), new Object[]{"1.8.8"}));
            this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext1));
            this.b.close(chatcomponenttext1);
         } else {
            this.b.a((PacketListener)(new LoginListener(this.a, this.b)));
            if(SpigotConfig.bungee) {
               String[] astring = p_a_1_.hostname.split("\u0000");
               if(astring.length != 3 && astring.length != 4) {
                  ChatComponentText chatcomponenttext2 = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
                  this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext2));
                  this.b.close(chatcomponenttext2);
                  return;
               }

               p_a_1_.hostname = astring[0];
               this.b.l = new InetSocketAddress(astring[1], ((InetSocketAddress)this.b.getSocketAddress()).getPort());
               this.b.spoofedUUID = UUIDTypeAdapter.fromString(astring[2]);
               if(astring.length == 4) {
                  this.b.spoofedProfile = (Property[])gson.fromJson(astring[3], Property[].class);
               }
            }

            ((LoginListener)this.b.getPacketListener()).hostname = p_a_1_.hostname + ":" + p_a_1_.port;
         }
         break;
      case 2:
         this.b.a(EnumProtocol.STATUS);
         this.b.a((PacketListener)(new PacketStatusListener(this.a, this.b)));
         break;
      default:
         throw new UnsupportedOperationException("Invalid intention " + p_a_1_.a());
      }

   }

   public void a(IChatBaseComponent p_a_1_) {
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[EnumProtocol.values().length];

      static {
         try {
            a[EnumProtocol.LOGIN.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EnumProtocol.STATUS.ordinal()] = 2;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}

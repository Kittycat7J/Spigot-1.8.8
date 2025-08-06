package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.HandshakeListener;
import net.minecraft.server.v1_8_R3.LazyInitVar;
import net.minecraft.server.v1_8_R3.LegacyPingHandler;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketDecoder;
import net.minecraft.server.v1_8_R3.PacketEncoder;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_8_R3.PacketPrepender;
import net.minecraft.server.v1_8_R3.PacketSplitter;
import net.minecraft.server.v1_8_R3.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.SpigotConfig;

public class ServerConnection {
   private static final Logger e = LogManager.getLogger();
   public static final LazyInitVar<NioEventLoopGroup> a = new LazyInitVar() {
      protected NioEventLoopGroup a() {
         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
      }

      protected Object init() {
         return this.a();
      }
   };
   public static final LazyInitVar<EpollEventLoopGroup> b = new LazyInitVar() {
      protected EpollEventLoopGroup a() {
         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
      }

      protected Object init() {
         return this.a();
      }
   };
   public static final LazyInitVar<LocalEventLoopGroup> c = new LazyInitVar() {
      protected LocalEventLoopGroup a() {
         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
      }

      protected Object init() {
         return this.a();
      }
   };
   private final MinecraftServer f;
   public volatile boolean d;
   private final List<ChannelFuture> g = Collections.synchronizedList(Lists.newArrayList());
   private final List<NetworkManager> h = Collections.synchronizedList(Lists.newArrayList());

   public ServerConnection(MinecraftServer p_i278_1_) {
      this.f = p_i278_1_;
      this.d = true;
   }

   public void a(InetAddress p_a_1_, int p_a_2_) throws IOException {
      synchronized(this.g) {
         Class oclass;
         LazyInitVar lazyinitvar;
         if(Epoll.isAvailable() && this.f.ai()) {
            oclass = EpollServerSocketChannel.class;
            lazyinitvar = b;
            e.info("Using epoll channel type");
         } else {
            oclass = NioServerSocketChannel.class;
            lazyinitvar = a;
            e.info("Using default channel type");
         }

         this.g.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(oclass)).childHandler(new ChannelInitializer() {
            protected void initChannel(Channel p_initChannel_1_) throws Exception {
               try {
                  p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
               } catch (ChannelException var3) {
                  ;
               }

               p_initChannel_1_.pipeline().addLast((String)"timeout", (ChannelHandler)(new ReadTimeoutHandler(30))).addLast((String)"legacy_query", (ChannelHandler)(new LegacyPingHandler(ServerConnection.this))).addLast((String)"splitter", (ChannelHandler)(new PacketSplitter())).addLast((String)"decoder", (ChannelHandler)(new PacketDecoder(EnumProtocolDirection.SERVERBOUND))).addLast((String)"prepender", (ChannelHandler)(new PacketPrepender())).addLast((String)"encoder", (ChannelHandler)(new PacketEncoder(EnumProtocolDirection.CLIENTBOUND)));
               NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
               ServerConnection.this.h.add(networkmanager);
               p_initChannel_1_.pipeline().addLast((String)"packet_handler", (ChannelHandler)networkmanager);
               networkmanager.a((PacketListener)(new HandshakeListener(ServerConnection.this.f, networkmanager)));
            }
         }).group((EventLoopGroup)lazyinitvar.c()).localAddress(p_a_1_, p_a_2_)).bind().syncUninterruptibly());
      }
   }

   public void b() {
      this.d = false;

      for(ChannelFuture channelfuture : this.g) {
         try {
            channelfuture.channel().close().sync();
         } catch (InterruptedException var3) {
            e.error("Interrupted whilst closing channel");
         }
      }

   }

   public void c() {
      synchronized(this.h) {
         if(SpigotConfig.playerShuffle > 0 && MinecraftServer.currentTick % SpigotConfig.playerShuffle == 0) {
            Collections.shuffle(this.h);
         }

         Iterator iterator = this.h.iterator();

         while(iterator.hasNext()) {
            final NetworkManager networkmanager = (NetworkManager)iterator.next();
            if(!networkmanager.h()) {
               if(!networkmanager.g()) {
                  if(!networkmanager.preparing) {
                     iterator.remove();
                     networkmanager.l();
                  }
               } else {
                  try {
                     networkmanager.a();
                  } catch (Exception exception) {
                     if(networkmanager.c()) {
                        CrashReport crashreport = CrashReport.a(exception, "Ticking memory connection");
                        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Ticking connection");
                        crashreportsystemdetails.a("Connection", new Callable() {
                           public String a() throws Exception {
                              return networkmanager.toString();
                           }

                           public Object call() throws Exception {
                              return this.a();
                           }
                        });
                        throw new ReportedException(crashreport);
                     }

                     e.warn((String)("Failed to handle packet for " + networkmanager.getSocketAddress()), (Throwable)exception);
                     final ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
                     networkmanager.a(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener() {
                        public void operationComplete(Future p_operationComplete_1_) throws Exception {
                           networkmanager.close(chatcomponenttext);
                        }
                     }, new GenericFutureListener[0]);
                     networkmanager.k();
                  }
               }
            }
         }

      }
   }

   public MinecraftServer d() {
      return this.f;
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.authlib.properties.Property;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.crypto.SecretKey;
import net.minecraft.server.v1_8_R3.CancelledPacketHandleException;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.LazyInitVar;
import net.minecraft.server.v1_8_R3.MinecraftEncryption;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketCompressor;
import net.minecraft.server.v1_8_R3.PacketDecompressor;
import net.minecraft.server.v1_8_R3.PacketDecrypter;
import net.minecraft.server.v1_8_R3.PacketEncrypter;
import net.minecraft.server.v1_8_R3.PacketListener;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NetworkManager extends SimpleChannelInboundHandler<Packet> {
   private static final Logger g = LogManager.getLogger();
   public static final Marker a = MarkerManager.getMarker("NETWORK");
   public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
   public static final AttributeKey<EnumProtocol> c = AttributeKey.<EnumProtocol>valueOf("protocol");
   public static final LazyInitVar<NioEventLoopGroup> d = new LazyInitVar() {
      protected NioEventLoopGroup a() {
         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
      }

      protected Object init() {
         return this.a();
      }
   };
   public static final LazyInitVar<EpollEventLoopGroup> e = new LazyInitVar() {
      protected EpollEventLoopGroup a() {
         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
      }

      protected Object init() {
         return this.a();
      }
   };
   public static final LazyInitVar<LocalEventLoopGroup> f = new LazyInitVar() {
      protected LocalEventLoopGroup a() {
         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
      }

      protected Object init() {
         return this.a();
      }
   };
   private final EnumProtocolDirection h;
   private final Queue<NetworkManager.QueuedPacket> i = Queues.<NetworkManager.QueuedPacket>newConcurrentLinkedQueue();
   private final ReentrantReadWriteLock j = new ReentrantReadWriteLock();
   public Channel channel;
   public SocketAddress l;
   public UUID spoofedUUID;
   public Property[] spoofedProfile;
   public boolean preparing = true;
   private PacketListener m;
   private IChatBaseComponent n;
   private boolean o;
   private boolean p;

   public NetworkManager(EnumProtocolDirection p_i233_1_) {
      this.h = p_i233_1_;
   }

   public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
      super.channelActive(p_channelActive_1_);
      this.channel = p_channelActive_1_.channel();
      this.l = this.channel.remoteAddress();
      this.preparing = false;

      try {
         this.a(EnumProtocol.HANDSHAKING);
      } catch (Throwable throwable) {
         g.fatal((Object)throwable);
      }

   }

   public void a(EnumProtocol p_a_1_) {
      this.channel.attr(c).set(p_a_1_);
      this.channel.config().setAutoRead(true);
      g.debug("Enabled auto read");
   }

   public void channelInactive(ChannelHandlerContext p_channelInactive_1_) throws Exception {
      this.close(new ChatMessage("disconnect.endOfStream", new Object[0]));
   }

   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
      ChatMessage chatmessage;
      if(p_exceptionCaught_2_ instanceof TimeoutException) {
         chatmessage = new ChatMessage("disconnect.timeout", new Object[0]);
      } else {
         chatmessage = new ChatMessage("disconnect.genericReason", new Object[]{"Internal Exception: " + p_exceptionCaught_2_});
      }

      this.close(chatmessage);
      if(MinecraftServer.getServer().isDebugging()) {
         p_exceptionCaught_2_.printStackTrace();
      }

   }

   protected void a(ChannelHandlerContext p_a_1_, Packet p_a_2_) throws Exception {
      if(this.channel.isOpen()) {
         try {
            p_a_2_.a(this.m);
         } catch (CancelledPacketHandleException var3) {
            ;
         }
      }

   }

   public void a(PacketListener p_a_1_) {
      Validate.notNull(p_a_1_, "packetListener", new Object[0]);
      g.debug("Set listener of {} to {}", new Object[]{this, p_a_1_});
      this.m = p_a_1_;
   }

   public void handle(Packet p_handle_1_) {
      if(this.g()) {
         this.m();
         this.a((Packet)p_handle_1_, (GenericFutureListener[])null);
      } else {
         this.j.writeLock().lock();

         try {
            this.i.add(new NetworkManager.QueuedPacket(p_handle_1_, (GenericFutureListener[])null));
         } finally {
            this.j.writeLock().unlock();
         }
      }

   }

   public void a(Packet p_a_1_, GenericFutureListener<? extends Future<? super Void>> p_a_2_, GenericFutureListener<? extends Future<? super Void>>... p_a_3_) {
      if(this.g()) {
         this.m();
         this.a(p_a_1_, (GenericFutureListener[])ArrayUtils.add(p_a_3_, 0, p_a_2_));
      } else {
         this.j.writeLock().lock();

         try {
            this.i.add(new NetworkManager.QueuedPacket(p_a_1_, (GenericFutureListener[])ArrayUtils.add(p_a_3_, 0, p_a_2_)));
         } finally {
            this.j.writeLock().unlock();
         }
      }

   }

   private void a(final Packet p_a_1_, final GenericFutureListener<? extends Future<? super Void>>[] p_a_2_) {
      final EnumProtocol enumprotocol = EnumProtocol.a(p_a_1_);
      final EnumProtocol enumprotocol1 = (EnumProtocol)this.channel.attr(c).get();
      if(enumprotocol1 != enumprotocol) {
         g.debug("Disabled auto read");
         this.channel.config().setAutoRead(false);
      }

      if(this.channel.eventLoop().inEventLoop()) {
         if(enumprotocol != enumprotocol1) {
            this.a(enumprotocol);
         }

         ChannelFuture channelfuture = this.channel.writeAndFlush(p_a_1_);
         if(p_a_2_ != null) {
            channelfuture.addListeners(p_a_2_);
         }

         channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
      } else {
         this.channel.eventLoop().execute(new Runnable() {
            public void run() {
               if(enumprotocol != enumprotocol1) {
                  NetworkManager.this.a(enumprotocol);
               }

               ChannelFuture channelfuture1 = NetworkManager.this.channel.writeAndFlush(p_a_1_);
               if(p_a_2_ != null) {
                  channelfuture1.addListeners(p_a_2_);
               }

               channelfuture1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
         });
      }

   }

   private void m() {
      if(this.channel != null && this.channel.isOpen()) {
         this.j.readLock().lock();

         try {
            while(!this.i.isEmpty()) {
               NetworkManager.QueuedPacket networkmanager$queuedpacket = (NetworkManager.QueuedPacket)this.i.poll();
               this.a(networkmanager$queuedpacket.a, networkmanager$queuedpacket.b);
            }
         } finally {
            this.j.readLock().unlock();
         }
      }

   }

   public void a() {
      this.m();
      if(this.m instanceof IUpdatePlayerListBox) {
         ((IUpdatePlayerListBox)this.m).c();
      }

      this.channel.flush();
   }

   public SocketAddress getSocketAddress() {
      return this.l;
   }

   public void close(IChatBaseComponent p_close_1_) {
      this.preparing = false;
      if(this.channel.isOpen()) {
         this.channel.close();
         this.n = p_close_1_;
      }

   }

   public boolean c() {
      return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
   }

   public void a(SecretKey p_a_1_) {
      this.o = true;
      this.channel.pipeline().addBefore("splitter", "decrypt", new PacketDecrypter(MinecraftEncryption.a(2, p_a_1_)));
      this.channel.pipeline().addBefore("prepender", "encrypt", new PacketEncrypter(MinecraftEncryption.a(1, p_a_1_)));
   }

   public boolean g() {
      return this.channel != null && this.channel.isOpen();
   }

   public boolean h() {
      return this.channel == null;
   }

   public PacketListener getPacketListener() {
      return this.m;
   }

   public IChatBaseComponent j() {
      return this.n;
   }

   public void k() {
      this.channel.config().setAutoRead(false);
   }

   public void a(int p_a_1_) {
      if(p_a_1_ >= 0) {
         if(this.channel.pipeline().get("decompress") instanceof PacketDecompressor) {
            ((PacketDecompressor)this.channel.pipeline().get("decompress")).a(p_a_1_);
         } else {
            this.channel.pipeline().addBefore("decoder", "decompress", new PacketDecompressor(p_a_1_));
         }

         if(this.channel.pipeline().get("compress") instanceof PacketCompressor) {
            ((PacketCompressor)this.channel.pipeline().get("decompress")).a(p_a_1_);
         } else {
            this.channel.pipeline().addBefore("encoder", "compress", new PacketCompressor(p_a_1_));
         }
      } else {
         if(this.channel.pipeline().get("decompress") instanceof PacketDecompressor) {
            this.channel.pipeline().remove("decompress");
         }

         if(this.channel.pipeline().get("compress") instanceof PacketCompressor) {
            this.channel.pipeline().remove("compress");
         }
      }

   }

   public void l() {
      if(this.channel != null && !this.channel.isOpen()) {
         if(!this.p) {
            this.p = true;
            if(this.j() != null) {
               this.getPacketListener().a(this.j());
            } else if(this.getPacketListener() != null) {
               this.getPacketListener().a(new ChatComponentText("Disconnected"));
            }

            this.i.clear();
         } else {
            g.warn("handleDisconnection() called twice");
         }
      }

   }

   protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_) throws Exception {
      this.a(p_channelRead0_1_, p_channelRead0_2_);
   }

   public SocketAddress getRawAddress() {
      return this.channel.remoteAddress();
   }

   static class QueuedPacket {
      private final Packet a;
      private final GenericFutureListener<? extends Future<? super Void>>[] b;

      public QueuedPacket(Packet p_i31_1_, GenericFutureListener<? extends Future<? super Void>>... p_i31_2_) {
         this.a = p_i31_1_;
         this.b = p_i31_2_;
      }
   }
}

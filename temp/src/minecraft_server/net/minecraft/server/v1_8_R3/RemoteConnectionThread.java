package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.v1_8_R3.IMinecraftServer;

public abstract class RemoteConnectionThread implements Runnable {
   private static final AtomicInteger h = new AtomicInteger(0);
   protected boolean a;
   protected IMinecraftServer b;
   protected final String c;
   protected Thread d;
   protected int e = 5;
   protected List<DatagramSocket> f = Lists.<DatagramSocket>newArrayList();
   protected List<ServerSocket> g = Lists.<ServerSocket>newArrayList();

   protected RemoteConnectionThread(IMinecraftServer p_i1092_1_, String p_i1092_2_) {
      this.b = p_i1092_1_;
      this.c = p_i1092_2_;
      if(this.b.isDebugging()) {
         this.c("Debugging is enabled, performance maybe reduced!");
      }

   }

   public synchronized void a() {
      this.d = new Thread(this, this.c + " #" + h.incrementAndGet());
      this.d.start();
      this.a = true;
   }

   public boolean c() {
      return this.a;
   }

   protected void a(String p_a_1_) {
      this.b.h(p_a_1_);
   }

   protected void b(String p_b_1_) {
      this.b.info(p_b_1_);
   }

   protected void c(String p_c_1_) {
      this.b.warning(p_c_1_);
   }

   protected void d(String p_d_1_) {
      this.b.g(p_d_1_);
   }

   protected int d() {
      return this.b.I();
   }

   protected void a(DatagramSocket p_a_1_) {
      this.a("registerSocket: " + p_a_1_);
      this.f.add(p_a_1_);
   }

   protected boolean a(DatagramSocket p_a_1_, boolean p_a_2_) {
      this.a("closeSocket: " + p_a_1_);
      if(null == p_a_1_) {
         return false;
      } else {
         boolean flag = false;
         if(!p_a_1_.isClosed()) {
            p_a_1_.close();
            flag = true;
         }

         if(p_a_2_) {
            this.f.remove(p_a_1_);
         }

         return flag;
      }
   }

   protected boolean b(ServerSocket p_b_1_) {
      return this.a(p_b_1_, true);
   }

   protected boolean a(ServerSocket p_a_1_, boolean p_a_2_) {
      this.a("closeSocket: " + p_a_1_);
      if(null == p_a_1_) {
         return false;
      } else {
         boolean flag = false;

         try {
            if(!p_a_1_.isClosed()) {
               p_a_1_.close();
               flag = true;
            }
         } catch (IOException ioexception) {
            this.c("IO: " + ioexception.getMessage());
         }

         if(p_a_2_) {
            this.g.remove(p_a_1_);
         }

         return flag;
      }
   }

   protected void e() {
      this.a(false);
   }

   protected void a(boolean p_a_1_) {
      int i = 0;

      for(DatagramSocket datagramsocket : this.f) {
         if(this.a(datagramsocket, false)) {
            ++i;
         }
      }

      this.f.clear();

      for(ServerSocket serversocket : this.g) {
         if(this.a(serversocket, false)) {
            ++i;
         }
      }

      this.g.clear();
      if(p_a_1_ && 0 < i) {
         this.c("Force closed " + i + " sockets");
      }

   }
}

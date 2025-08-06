package net.minecraft.server.v1_8_R3;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import net.minecraft.server.v1_8_R3.IMinecraftServer;
import net.minecraft.server.v1_8_R3.RemoteConnectionThread;
import net.minecraft.server.v1_8_R3.StatusChallengeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoteControlSession extends RemoteConnectionThread {
   private static final Logger h = LogManager.getLogger();
   private boolean i;
   private Socket j;
   private byte[] k = new byte[1460];
   private String l;

   RemoteControlSession(IMinecraftServer p_i1095_1_, Socket p_i1095_2_) {
      super(p_i1095_1_, "RCON Client");
      this.j = p_i1095_2_;

      try {
         this.j.setSoTimeout(0);
      } catch (Exception var4) {
         this.a = false;
      }

      this.l = p_i1095_1_.a("rcon.password", "");
      this.b("Rcon connection from: " + p_i1095_2_.getInetAddress());
   }

   public void run() {
      while(true) {
         try {
            if(!this.a) {
               return;
            }

            BufferedInputStream bufferedinputstream = new BufferedInputStream(this.j.getInputStream());
            int ix = bufferedinputstream.read(this.k, 0, 1460);
            if(10 <= ix) {
               int jx = 0;
               int kx = StatusChallengeUtils.b(this.k, 0, ix);
               if(kx != ix - 4) {
                  return;
               }

               jx = jx + 4;
               int lx = StatusChallengeUtils.b(this.k, jx, ix);
               jx = jx + 4;
               int i1 = StatusChallengeUtils.b(this.k, jx);
               jx = jx + 4;
               switch(i1) {
               case 2:
                  if(this.i) {
                     String s1 = StatusChallengeUtils.a(this.k, jx, ix);

                     try {
                        this.a(lx, this.b.executeRemoteCommand(s1));
                     } catch (Exception exception) {
                        this.a(lx, "Error executing: " + s1 + " (" + exception.getMessage() + ")");
                     }
                     continue;
                  }

                  this.f();
                  continue;
               case 3:
                  String s = StatusChallengeUtils.a(this.k, jx, ix);
                  int j1 = jx + s.length();
                  if(0 != s.length() && s.equals(this.l)) {
                     this.i = true;
                     this.a(lx, 2, "");
                     continue;
                  }

                  this.i = false;
                  this.f();
                  continue;
               default:
                  this.a(lx, String.format("Unknown request %s", new Object[]{Integer.toHexString(i1)}));
                  continue;
               }
            }
         } catch (SocketTimeoutException var17) {
            return;
         } catch (IOException var18) {
            return;
         } catch (Exception exception1) {
            h.error((String)"Exception whilst parsing RCON input", (Throwable)exception1);
            return;
         } finally {
            this.g();
         }

         return;
      }
   }

   private void a(int p_a_1_, int p_a_2_, String p_a_3_) throws IOException {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1248);
      DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
      byte[] abyte = p_a_3_.getBytes("UTF-8");
      dataoutputstream.writeInt(Integer.reverseBytes(abyte.length + 10));
      dataoutputstream.writeInt(Integer.reverseBytes(p_a_1_));
      dataoutputstream.writeInt(Integer.reverseBytes(p_a_2_));
      dataoutputstream.write(abyte);
      dataoutputstream.write(0);
      dataoutputstream.write(0);
      this.j.getOutputStream().write(bytearrayoutputstream.toByteArray());
   }

   private void f() throws IOException {
      this.a(-1, 2, "");
   }

   private void a(int p_a_1_, String p_a_2_) throws IOException {
      int ix = p_a_2_.length();

      while(true) {
         int jx = 4096 <= ix?4096:ix;
         this.a(p_a_1_, 0, p_a_2_.substring(0, jx));
         p_a_2_ = p_a_2_.substring(jx);
         ix = p_a_2_.length();
         if(0 == ix) {
            break;
         }
      }

   }

   private void g() {
      if(null != this.j) {
         try {
            this.j.close();
         } catch (IOException ioexception) {
            this.c("IO: " + ioexception.getMessage());
         }

         this.j = null;
      }
   }
}

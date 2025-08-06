package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class RegionFile {
   private static final byte[] a = new byte[4096];
   private final File b;
   private RandomAccessFile c;
   private final int[] d = new int[1024];
   private final int[] e = new int[1024];
   private List<Boolean> f;
   private int g;
   private long h;

   public RegionFile(File p_i410_1_) {
      this.b = p_i410_1_;
      this.g = 0;

      try {
         if(p_i410_1_.exists()) {
            this.h = p_i410_1_.lastModified();
         }

         this.c = new RandomAccessFile(p_i410_1_, "rw");
         if(this.c.length() < 4096L) {
            this.c.write(a);
            this.c.write(a);
            this.g += 8192;
         }

         if((this.c.length() & 4095L) != 0L) {
            for(int i = 0; (long)i < (this.c.length() & 4095L); ++i) {
               this.c.write(0);
            }
         }

         int i1 = (int)this.c.length() / 4096;
         this.f = Lists.<Boolean>newArrayListWithCapacity(i1);

         for(int j = 0; j < i1; ++j) {
            this.f.add(Boolean.valueOf(true));
         }

         this.f.set(0, Boolean.valueOf(false));
         this.f.set(1, Boolean.valueOf(false));
         this.c.seek(0L);

         for(int j1 = 0; j1 < 1024; ++j1) {
            int k = this.c.readInt();
            this.d[j1] = k;
            if(k != 0 && (k >> 8) + (k & 255) <= this.f.size()) {
               for(int l = 0; l < (k & 255); ++l) {
                  this.f.set((k >> 8) + l, Boolean.valueOf(false));
               }
            }
         }

         for(int k1 = 0; k1 < 1024; ++k1) {
            int l1 = this.c.readInt();
            this.e[k1] = l1;
         }
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
      }

   }

   public synchronized boolean chunkExists(int p_chunkExists_1_, int p_chunkExists_2_) {
      if(this.d(p_chunkExists_1_, p_chunkExists_2_)) {
         return false;
      } else {
         try {
            int i = this.e(p_chunkExists_1_, p_chunkExists_2_);
            if(i == 0) {
               return false;
            } else {
               int j = i >> 8;
               int k = i & 255;
               if(j + k > this.f.size()) {
                  return false;
               } else {
                  this.c.seek((long)(j * 4096));
                  int l = this.c.readInt();
                  if(l <= 4096 * k && l > 0) {
                     byte b0 = this.c.readByte();
                     return b0 == 1 || b0 == 2;
                  } else {
                     return false;
                  }
               }
            }
         } catch (IOException var8) {
            return false;
         }
      }
   }

   public synchronized DataInputStream a(int p_a_1_, int p_a_2_) {
      if(this.d(p_a_1_, p_a_2_)) {
         return null;
      } else {
         try {
            int i = this.e(p_a_1_, p_a_2_);
            if(i == 0) {
               return null;
            } else {
               int j = i >> 8;
               int k = i & 255;
               if(j + k > this.f.size()) {
                  return null;
               } else {
                  this.c.seek((long)(j * 4096));
                  int l = this.c.readInt();
                  if(l > 4096 * k) {
                     return null;
                  } else if(l <= 0) {
                     return null;
                  } else {
                     byte b0 = this.c.readByte();
                     if(b0 == 1) {
                        byte[] abyte1 = new byte[l - 1];
                        this.c.read(abyte1);
                        return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
                     } else if(b0 == 2) {
                        byte[] abyte = new byte[l - 1];
                        this.c.read(abyte);
                        return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
                     } else {
                        return null;
                     }
                  }
               }
            }
         } catch (IOException var9) {
            return null;
         }
      }
   }

   public DataOutputStream b(int p_b_1_, int p_b_2_) {
      return this.d(p_b_1_, p_b_2_)?null:new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(new RegionFile.ChunkBuffer(p_b_1_, p_b_2_))));
   }

   protected synchronized void a(int p_a_1_, int p_a_2_, byte[] p_a_3_, int p_a_4_) {
      try {
         int i = this.e(p_a_1_, p_a_2_);
         int j = i >> 8;
         int k = i & 255;
         int l = (p_a_4_ + 5) / 4096 + 1;
         if(l >= 256) {
            return;
         }

         if(j != 0 && k == l) {
            this.a(j, p_a_3_, p_a_4_);
         } else {
            for(int i1 = 0; i1 < k; ++i1) {
               this.f.set(j + i1, Boolean.valueOf(true));
            }

            int l11 = this.f.indexOf(Boolean.valueOf(true));
            int j1 = 0;
            if(l11 != -1) {
               for(int k1 = l11; k1 < this.f.size(); ++k1) {
                  if(j1 != 0) {
                     if(((Boolean)this.f.get(k1)).booleanValue()) {
                        ++j1;
                     } else {
                        j1 = 0;
                     }
                  } else if(((Boolean)this.f.get(k1)).booleanValue()) {
                     l11 = k1;
                     j1 = 1;
                  }

                  if(j1 >= l) {
                     break;
                  }
               }
            }

            if(j1 >= l) {
               j = l11;
               this.a(p_a_1_, p_a_2_, l11 << 8 | l);

               for(int j2 = 0; j2 < l; ++j2) {
                  this.f.set(j + j2, Boolean.valueOf(false));
               }

               this.a(j, p_a_3_, p_a_4_);
            } else {
               this.c.seek(this.c.length());
               j = this.f.size();

               for(int i2 = 0; i2 < l; ++i2) {
                  this.c.write(a);
                  this.f.add(Boolean.valueOf(false));
               }

               this.g += 4096 * l;
               this.a(j, p_a_3_, p_a_4_);
               this.a(p_a_1_, p_a_2_, j << 8 | l);
            }
         }

         this.b(p_a_1_, p_a_2_, (int)(MinecraftServer.az() / 1000L));
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
      }

   }

   private void a(int p_a_1_, byte[] p_a_2_, int p_a_3_) throws IOException {
      this.c.seek((long)(p_a_1_ * 4096));
      this.c.writeInt(p_a_3_ + 1);
      this.c.writeByte(2);
      this.c.write(p_a_2_, 0, p_a_3_);
   }

   private boolean d(int p_d_1_, int p_d_2_) {
      return p_d_1_ < 0 || p_d_1_ >= 32 || p_d_2_ < 0 || p_d_2_ >= 32;
   }

   private int e(int p_e_1_, int p_e_2_) {
      return this.d[p_e_1_ + p_e_2_ * 32];
   }

   public boolean c(int p_c_1_, int p_c_2_) {
      return this.e(p_c_1_, p_c_2_) != 0;
   }

   private void a(int p_a_1_, int p_a_2_, int p_a_3_) throws IOException {
      this.d[p_a_1_ + p_a_2_ * 32] = p_a_3_;
      this.c.seek((long)((p_a_1_ + p_a_2_ * 32) * 4));
      this.c.writeInt(p_a_3_);
   }

   private void b(int p_b_1_, int p_b_2_, int p_b_3_) throws IOException {
      this.e[p_b_1_ + p_b_2_ * 32] = p_b_3_;
      this.c.seek((long)(4096 + (p_b_1_ + p_b_2_ * 32) * 4));
      this.c.writeInt(p_b_3_);
   }

   public void c() throws IOException {
      if(this.c != null) {
         this.c.close();
      }

   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private int b;
      private int c;

      public ChunkBuffer(int p_i362_2_, int p_i362_3_) {
         super(8096);
         this.b = p_i362_2_;
         this.c = p_i362_3_;
      }

      public void close() {
         RegionFile.this.a(this.b, this.c, this.buf, this.count);
      }
   }
}

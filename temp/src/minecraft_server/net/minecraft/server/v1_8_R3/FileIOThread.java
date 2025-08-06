package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.server.v1_8_R3.IAsyncChunkSaver;

public class FileIOThread implements Runnable {
   private static final FileIOThread a = new FileIOThread();
   private List<IAsyncChunkSaver> b = Collections.synchronizedList(Lists.newArrayList());
   private volatile long c;
   private volatile long d;
   private volatile boolean e;

   private FileIOThread() {
      Thread thread = new Thread(this, "File IO Thread");
      thread.setPriority(1);
      thread.start();
   }

   public static FileIOThread a() {
      return a;
   }

   public void run() {
      while(true) {
         this.c();
      }
   }

   private void c() {
      for(int i = 0; i < this.b.size(); ++i) {
         IAsyncChunkSaver iasyncchunksaver = (IAsyncChunkSaver)this.b.get(i);
         boolean flag = iasyncchunksaver.c();
         if(!flag) {
            this.b.remove(i--);
            ++this.d;
         }
      }

      if(this.b.isEmpty()) {
         try {
            Thread.sleep(25L);
         } catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
         }
      }

   }

   public void a(IAsyncChunkSaver p_a_1_) {
      if(!this.b.contains(p_a_1_)) {
         ++this.c;
         this.b.add(p_a_1_);
      }

   }

   public void b() throws InterruptedException {
      this.e = true;

      while(this.c != this.d) {
         Thread.sleep(10L);
      }

      this.e = false;
   }
}

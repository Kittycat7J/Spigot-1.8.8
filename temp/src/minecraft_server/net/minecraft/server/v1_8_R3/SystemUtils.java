package net.minecraft.server.v1_8_R3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.Logger;

public class SystemUtils {
   public static <V> V a(FutureTask<V> p_a_0_, Logger p_a_1_) {
      try {
         p_a_0_.run();
         return (V)p_a_0_.get();
      } catch (ExecutionException executionexception) {
         p_a_1_.fatal((String)"Error executing task", (Throwable)executionexception);
      } catch (InterruptedException interruptedexception) {
         p_a_1_.fatal((String)"Error executing task", (Throwable)interruptedexception);
      }

      return (V)null;
   }
}

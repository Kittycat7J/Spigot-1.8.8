package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.Statistic;
import net.minecraft.server.v1_8_R3.StatisticList;

public class PacketPlayOutStatistic implements Packet<PacketListenerPlayOut> {
   private Map<Statistic, Integer> a;

   public PacketPlayOutStatistic() {
   }

   public PacketPlayOutStatistic(Map<Statistic, Integer> p_i958_1_) {
      this.a = p_i958_1_;
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      int i = p_a_1_.e();
      this.a = Maps.<Statistic, Integer>newHashMap();

      for(int j = 0; j < i; ++j) {
         Statistic statistic = StatisticList.getStatistic(p_a_1_.c(32767));
         int k = p_a_1_.e();
         if(statistic != null) {
            this.a.put(statistic, Integer.valueOf(k));
         }
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a.size());

      for(Entry entry : this.a.entrySet()) {
         p_b_1_.a(((Statistic)entry.getKey()).name);
         p_b_1_.b(((Integer)entry.getValue()).intValue());
      }

   }
}

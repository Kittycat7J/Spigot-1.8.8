package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.server.v1_8_R3.Achievement;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IJsonStatistic;
import net.minecraft.server.v1_8_R3.Statistic;
import net.minecraft.server.v1_8_R3.StatisticWrapper;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;

public class StatisticManager {
   protected final Map<Statistic, StatisticWrapper> a = Maps.<Statistic, StatisticWrapper>newConcurrentMap();

   public boolean hasAchievement(Achievement p_hasAchievement_1_) {
      return this.getStatisticValue(p_hasAchievement_1_) > 0;
   }

   public boolean b(Achievement p_b_1_) {
      return p_b_1_.c == null || this.hasAchievement(p_b_1_.c);
   }

   public void b(EntityHuman p_b_1_, Statistic p_b_2_, int p_b_3_) {
      if(!p_b_2_.d() || this.b((Achievement)p_b_2_)) {
         Cancellable cancellable = CraftEventFactory.handleStatisticsIncrease(p_b_1_, p_b_2_, this.getStatisticValue(p_b_2_), p_b_3_);
         if(cancellable != null && cancellable.isCancelled()) {
            return;
         }

         this.setStatistic(p_b_1_, p_b_2_, this.getStatisticValue(p_b_2_) + p_b_3_);
      }

   }

   public void setStatistic(EntityHuman p_setStatistic_1_, Statistic p_setStatistic_2_, int p_setStatistic_3_) {
      StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(p_setStatistic_2_);
      if(statisticwrapper == null) {
         statisticwrapper = new StatisticWrapper();
         this.a.put(p_setStatistic_2_, statisticwrapper);
      }

      statisticwrapper.a(p_setStatistic_3_);
   }

   public int getStatisticValue(Statistic p_getStatisticValue_1_) {
      StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(p_getStatisticValue_1_);
      return statisticwrapper == null?0:statisticwrapper.a();
   }

   public <T extends IJsonStatistic> T b(Statistic p_b_1_) {
      StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(p_b_1_);
      return (T)(statisticwrapper != null?statisticwrapper.b():null);
   }

   public <T extends IJsonStatistic> T a(Statistic p_a_1_, T p_a_2_) {
      StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(p_a_1_);
      if(statisticwrapper == null) {
         statisticwrapper = new StatisticWrapper();
         this.a.put(p_a_1_, statisticwrapper);
      }

      statisticwrapper.a(p_a_2_);
      return (T)p_a_2_;
   }
}

package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.MojangStatisticsGenerator;

public interface IMojangStatistics {
   void a(MojangStatisticsGenerator var1);

   void b(MojangStatisticsGenerator var1);

   boolean getSnooperEnabled();
}

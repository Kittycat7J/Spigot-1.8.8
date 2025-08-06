package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CrashReport;

public class ReportedException extends RuntimeException {
   private final CrashReport a;

   public ReportedException(CrashReport p_i916_1_) {
      this.a = p_i916_1_;
   }

   public CrashReport a() {
      return this.a;
   }

   public Throwable getCause() {
      return this.a.b();
   }

   public String getMessage() {
      return this.a.a();
   }
}

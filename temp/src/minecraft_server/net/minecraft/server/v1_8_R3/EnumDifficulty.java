package net.minecraft.server.v1_8_R3;

public enum EnumDifficulty {
   PEACEFUL(0, "options.difficulty.peaceful"),
   EASY(1, "options.difficulty.easy"),
   NORMAL(2, "options.difficulty.normal"),
   HARD(3, "options.difficulty.hard");

   private static final EnumDifficulty[] e = new EnumDifficulty[values().length];
   private final int f;
   private final String g;

   private EnumDifficulty(int p_i1126_3_, String p_i1126_4_) {
      this.f = p_i1126_3_;
      this.g = p_i1126_4_;
   }

   public int a() {
      return this.f;
   }

   public static EnumDifficulty getById(int p_getById_0_) {
      return e[p_getById_0_ % e.length];
   }

   public String b() {
      return this.g;
   }

   static {
      for(EnumDifficulty enumdifficulty : values()) {
         e[enumdifficulty.f] = enumdifficulty;
      }

   }
}

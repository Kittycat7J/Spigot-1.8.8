package net.minecraft.server.v1_8_R3;

import java.util.concurrent.Callable;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.GameRules;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import net.minecraft.server.v1_8_R3.WorldType;
import org.bukkit.Bukkit;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldData {
   public static final EnumDifficulty a = EnumDifficulty.NORMAL;
   private long b;
   private WorldType c = WorldType.NORMAL;
   private String d = "";
   private int e;
   private int f;
   private int g;
   private long h;
   private long i;
   private long j;
   private long k;
   private NBTTagCompound l;
   private int m;
   private String n;
   private int o;
   private int p;
   private boolean q;
   private int r;
   private boolean s;
   private int t;
   private WorldSettings.EnumGamemode u;
   private boolean v;
   private boolean w;
   private boolean x;
   private boolean y;
   private EnumDifficulty z;
   private boolean A;
   private double B = 0.0D;
   private double C = 0.0D;
   private double D = 6.0E7D;
   private long E = 0L;
   private double F = 0.0D;
   private double G = 5.0D;
   private double H = 0.2D;
   private int I = 5;
   private int J = 15;
   private GameRules K = new GameRules();
   public WorldServer world;

   protected WorldData() {
   }

   public WorldData(NBTTagCompound p_i244_1_) {
      this.b = p_i244_1_.getLong("RandomSeed");
      if(p_i244_1_.hasKeyOfType("generatorName", 8)) {
         String s = p_i244_1_.getString("generatorName");
         this.c = WorldType.getType(s);
         if(this.c == null) {
            this.c = WorldType.NORMAL;
         } else if(this.c.f()) {
            int i = 0;
            if(p_i244_1_.hasKeyOfType("generatorVersion", 99)) {
               i = p_i244_1_.getInt("generatorVersion");
            }

            this.c = this.c.a(i);
         }

         if(p_i244_1_.hasKeyOfType("generatorOptions", 8)) {
            this.d = p_i244_1_.getString("generatorOptions");
         }
      }

      this.u = WorldSettings.EnumGamemode.getById(p_i244_1_.getInt("GameType"));
      if(p_i244_1_.hasKeyOfType("MapFeatures", 99)) {
         this.v = p_i244_1_.getBoolean("MapFeatures");
      } else {
         this.v = true;
      }

      this.e = p_i244_1_.getInt("SpawnX");
      this.f = p_i244_1_.getInt("SpawnY");
      this.g = p_i244_1_.getInt("SpawnZ");
      this.h = p_i244_1_.getLong("Time");
      if(p_i244_1_.hasKeyOfType("DayTime", 99)) {
         this.i = p_i244_1_.getLong("DayTime");
      } else {
         this.i = this.h;
      }

      this.j = p_i244_1_.getLong("LastPlayed");
      this.k = p_i244_1_.getLong("SizeOnDisk");
      this.n = p_i244_1_.getString("LevelName");
      this.o = p_i244_1_.getInt("version");
      this.p = p_i244_1_.getInt("clearWeatherTime");
      this.r = p_i244_1_.getInt("rainTime");
      this.q = p_i244_1_.getBoolean("raining");
      this.t = p_i244_1_.getInt("thunderTime");
      this.s = p_i244_1_.getBoolean("thundering");
      this.w = p_i244_1_.getBoolean("hardcore");
      if(p_i244_1_.hasKeyOfType("initialized", 99)) {
         this.y = p_i244_1_.getBoolean("initialized");
      } else {
         this.y = true;
      }

      if(p_i244_1_.hasKeyOfType("allowCommands", 99)) {
         this.x = p_i244_1_.getBoolean("allowCommands");
      } else {
         this.x = this.u == WorldSettings.EnumGamemode.CREATIVE;
      }

      if(p_i244_1_.hasKeyOfType("Player", 10)) {
         this.l = p_i244_1_.getCompound("Player");
         this.m = this.l.getInt("Dimension");
      }

      if(p_i244_1_.hasKeyOfType("GameRules", 10)) {
         this.K.a(p_i244_1_.getCompound("GameRules"));
      }

      if(p_i244_1_.hasKeyOfType("Difficulty", 99)) {
         this.z = EnumDifficulty.getById(p_i244_1_.getByte("Difficulty"));
      }

      if(p_i244_1_.hasKeyOfType("DifficultyLocked", 1)) {
         this.A = p_i244_1_.getBoolean("DifficultyLocked");
      }

      if(p_i244_1_.hasKeyOfType("BorderCenterX", 99)) {
         this.B = p_i244_1_.getDouble("BorderCenterX");
      }

      if(p_i244_1_.hasKeyOfType("BorderCenterZ", 99)) {
         this.C = p_i244_1_.getDouble("BorderCenterZ");
      }

      if(p_i244_1_.hasKeyOfType("BorderSize", 99)) {
         this.D = p_i244_1_.getDouble("BorderSize");
      }

      if(p_i244_1_.hasKeyOfType("BorderSizeLerpTime", 99)) {
         this.E = p_i244_1_.getLong("BorderSizeLerpTime");
      }

      if(p_i244_1_.hasKeyOfType("BorderSizeLerpTarget", 99)) {
         this.F = p_i244_1_.getDouble("BorderSizeLerpTarget");
      }

      if(p_i244_1_.hasKeyOfType("BorderSafeZone", 99)) {
         this.G = p_i244_1_.getDouble("BorderSafeZone");
      }

      if(p_i244_1_.hasKeyOfType("BorderDamagePerBlock", 99)) {
         this.H = p_i244_1_.getDouble("BorderDamagePerBlock");
      }

      if(p_i244_1_.hasKeyOfType("BorderWarningBlocks", 99)) {
         this.I = p_i244_1_.getInt("BorderWarningBlocks");
      }

      if(p_i244_1_.hasKeyOfType("BorderWarningTime", 99)) {
         this.J = p_i244_1_.getInt("BorderWarningTime");
      }

   }

   public WorldData(WorldSettings p_i245_1_, String p_i245_2_) {
      this.a(p_i245_1_);
      this.n = p_i245_2_;
      this.z = a;
      this.y = false;
   }

   public void a(WorldSettings p_a_1_) {
      this.b = p_a_1_.d();
      this.u = p_a_1_.e();
      this.v = p_a_1_.g();
      this.w = p_a_1_.f();
      this.c = p_a_1_.h();
      this.d = p_a_1_.j();
      this.x = p_a_1_.i();
   }

   public WorldData(WorldData p_i246_1_) {
      this.b = p_i246_1_.b;
      this.c = p_i246_1_.c;
      this.d = p_i246_1_.d;
      this.u = p_i246_1_.u;
      this.v = p_i246_1_.v;
      this.e = p_i246_1_.e;
      this.f = p_i246_1_.f;
      this.g = p_i246_1_.g;
      this.h = p_i246_1_.h;
      this.i = p_i246_1_.i;
      this.j = p_i246_1_.j;
      this.k = p_i246_1_.k;
      this.l = p_i246_1_.l;
      this.m = p_i246_1_.m;
      this.n = p_i246_1_.n;
      this.o = p_i246_1_.o;
      this.r = p_i246_1_.r;
      this.q = p_i246_1_.q;
      this.t = p_i246_1_.t;
      this.s = p_i246_1_.s;
      this.w = p_i246_1_.w;
      this.x = p_i246_1_.x;
      this.y = p_i246_1_.y;
      this.K = p_i246_1_.K;
      this.z = p_i246_1_.z;
      this.A = p_i246_1_.A;
      this.B = p_i246_1_.B;
      this.C = p_i246_1_.C;
      this.D = p_i246_1_.D;
      this.E = p_i246_1_.E;
      this.F = p_i246_1_.F;
      this.G = p_i246_1_.G;
      this.H = p_i246_1_.H;
      this.J = p_i246_1_.J;
      this.I = p_i246_1_.I;
   }

   public NBTTagCompound a() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.a(nbttagcompound, this.l);
      return nbttagcompound;
   }

   public NBTTagCompound a(NBTTagCompound p_a_1_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.a(nbttagcompound, p_a_1_);
      return nbttagcompound;
   }

   private void a(NBTTagCompound p_a_1_, NBTTagCompound p_a_2_) {
      p_a_1_.setLong("RandomSeed", this.b);
      p_a_1_.setString("generatorName", this.c.name());
      p_a_1_.setInt("generatorVersion", this.c.getVersion());
      p_a_1_.setString("generatorOptions", this.d);
      p_a_1_.setInt("GameType", this.u.getId());
      p_a_1_.setBoolean("MapFeatures", this.v);
      p_a_1_.setInt("SpawnX", this.e);
      p_a_1_.setInt("SpawnY", this.f);
      p_a_1_.setInt("SpawnZ", this.g);
      p_a_1_.setLong("Time", this.h);
      p_a_1_.setLong("DayTime", this.i);
      p_a_1_.setLong("SizeOnDisk", this.k);
      p_a_1_.setLong("LastPlayed", MinecraftServer.az());
      p_a_1_.setString("LevelName", this.n);
      p_a_1_.setInt("version", this.o);
      p_a_1_.setInt("clearWeatherTime", this.p);
      p_a_1_.setInt("rainTime", this.r);
      p_a_1_.setBoolean("raining", this.q);
      p_a_1_.setInt("thunderTime", this.t);
      p_a_1_.setBoolean("thundering", this.s);
      p_a_1_.setBoolean("hardcore", this.w);
      p_a_1_.setBoolean("allowCommands", this.x);
      p_a_1_.setBoolean("initialized", this.y);
      p_a_1_.setDouble("BorderCenterX", this.B);
      p_a_1_.setDouble("BorderCenterZ", this.C);
      p_a_1_.setDouble("BorderSize", this.D);
      p_a_1_.setLong("BorderSizeLerpTime", this.E);
      p_a_1_.setDouble("BorderSafeZone", this.G);
      p_a_1_.setDouble("BorderDamagePerBlock", this.H);
      p_a_1_.setDouble("BorderSizeLerpTarget", this.F);
      p_a_1_.setDouble("BorderWarningBlocks", (double)this.I);
      p_a_1_.setDouble("BorderWarningTime", (double)this.J);
      if(this.z != null) {
         p_a_1_.setByte("Difficulty", (byte)this.z.a());
      }

      p_a_1_.setBoolean("DifficultyLocked", this.A);
      p_a_1_.set("GameRules", this.K.a());
      if(p_a_2_ != null) {
         p_a_1_.set("Player", p_a_2_);
      }

   }

   public long getSeed() {
      return this.b;
   }

   public int c() {
      return this.e;
   }

   public int d() {
      return this.f;
   }

   public int e() {
      return this.g;
   }

   public long getTime() {
      return this.h;
   }

   public long getDayTime() {
      return this.i;
   }

   public NBTTagCompound i() {
      return this.l;
   }

   public void setTime(long p_setTime_1_) {
      this.h = p_setTime_1_;
   }

   public void setDayTime(long p_setDayTime_1_) {
      this.i = p_setDayTime_1_;
   }

   public void setSpawn(BlockPosition p_setSpawn_1_) {
      this.e = p_setSpawn_1_.getX();
      this.f = p_setSpawn_1_.getY();
      this.g = p_setSpawn_1_.getZ();
   }

   public String getName() {
      return this.n;
   }

   public void a(String p_a_1_) {
      this.n = p_a_1_;
   }

   public int l() {
      return this.o;
   }

   public void e(int p_e_1_) {
      this.o = p_e_1_;
   }

   public int A() {
      return this.p;
   }

   public void i(int p_i_1_) {
      this.p = p_i_1_;
   }

   public boolean isThundering() {
      return this.s;
   }

   public void setThundering(boolean p_setThundering_1_) {
      org.bukkit.World world = Bukkit.getWorld(this.getName());
      if(world != null) {
         ThunderChangeEvent thunderchangeevent = new ThunderChangeEvent(world, p_setThundering_1_);
         Bukkit.getServer().getPluginManager().callEvent(thunderchangeevent);
         if(thunderchangeevent.isCancelled()) {
            return;
         }

         this.setThunderDuration(0);
      }

      this.s = p_setThundering_1_;
   }

   public int getThunderDuration() {
      return this.t;
   }

   public void setThunderDuration(int p_setThunderDuration_1_) {
      this.t = p_setThunderDuration_1_;
   }

   public boolean hasStorm() {
      return this.q;
   }

   public void setStorm(boolean p_setStorm_1_) {
      org.bukkit.World world = Bukkit.getWorld(this.getName());
      if(world != null) {
         WeatherChangeEvent weatherchangeevent = new WeatherChangeEvent(world, p_setStorm_1_);
         Bukkit.getServer().getPluginManager().callEvent(weatherchangeevent);
         if(weatherchangeevent.isCancelled()) {
            return;
         }

         this.setWeatherDuration(0);
      }

      this.q = p_setStorm_1_;
   }

   public int getWeatherDuration() {
      return this.r;
   }

   public void setWeatherDuration(int p_setWeatherDuration_1_) {
      this.r = p_setWeatherDuration_1_;
   }

   public WorldSettings.EnumGamemode getGameType() {
      return this.u;
   }

   public boolean shouldGenerateMapFeatures() {
      return this.v;
   }

   public void f(boolean p_f_1_) {
      this.v = p_f_1_;
   }

   public void setGameType(WorldSettings.EnumGamemode p_setGameType_1_) {
      this.u = p_setGameType_1_;
   }

   public boolean isHardcore() {
      return this.w;
   }

   public void g(boolean p_g_1_) {
      this.w = p_g_1_;
   }

   public WorldType getType() {
      return this.c;
   }

   public void a(WorldType p_a_1_) {
      this.c = p_a_1_;
   }

   public String getGeneratorOptions() {
      return this.d;
   }

   public boolean v() {
      return this.x;
   }

   public void c(boolean p_c_1_) {
      this.x = p_c_1_;
   }

   public boolean w() {
      return this.y;
   }

   public void d(boolean p_d_1_) {
      this.y = p_d_1_;
   }

   public GameRules x() {
      return this.K;
   }

   public double C() {
      return this.B;
   }

   public double D() {
      return this.C;
   }

   public double E() {
      return this.D;
   }

   public void a(double p_a_1_) {
      this.D = p_a_1_;
   }

   public long F() {
      return this.E;
   }

   public void e(long p_e_1_) {
      this.E = p_e_1_;
   }

   public double G() {
      return this.F;
   }

   public void b(double p_b_1_) {
      this.F = p_b_1_;
   }

   public void c(double p_c_1_) {
      this.C = p_c_1_;
   }

   public void d(double p_d_1_) {
      this.B = p_d_1_;
   }

   public double H() {
      return this.G;
   }

   public void e(double p_e_1_) {
      this.G = p_e_1_;
   }

   public double I() {
      return this.H;
   }

   public void f(double p_f_1_) {
      this.H = p_f_1_;
   }

   public int J() {
      return this.I;
   }

   public int K() {
      return this.J;
   }

   public void j(int p_j_1_) {
      this.I = p_j_1_;
   }

   public void k(int p_k_1_) {
      this.J = p_k_1_;
   }

   public EnumDifficulty getDifficulty() {
      return this.z;
   }

   public void setDifficulty(EnumDifficulty p_setDifficulty_1_) {
      this.z = p_setDifficulty_1_;
      PacketPlayOutServerDifficulty packetplayoutserverdifficulty = new PacketPlayOutServerDifficulty(this.getDifficulty(), this.isDifficultyLocked());

      for(EntityPlayer entityplayer : this.world.players) {
         entityplayer.playerConnection.sendPacket(packetplayoutserverdifficulty);
      }

   }

   public boolean isDifficultyLocked() {
      return this.A;
   }

   public void e(boolean p_e_1_) {
      this.A = p_e_1_;
   }

   public void a(CrashReportSystemDetails p_a_1_) {
      p_a_1_.a("Level seed", new Callable() {
         public String a() throws Exception {
            return String.valueOf(WorldData.this.getSeed());
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level generator", new Callable() {
         public String a() throws Exception {
            return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[]{Integer.valueOf(WorldData.this.c.g()), WorldData.this.c.name(), Integer.valueOf(WorldData.this.c.getVersion()), Boolean.valueOf(WorldData.this.v)});
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level generator options", new Callable() {
         public String a() throws Exception {
            return WorldData.this.d;
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level spawn location", new Callable() {
         public String a() throws Exception {
            return CrashReportSystemDetails.a((double)WorldData.this.e, (double)WorldData.this.f, (double)WorldData.this.g);
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level time", new Callable() {
         public String a() throws Exception {
            return String.format("%d game time, %d day time", new Object[]{Long.valueOf(WorldData.this.h), Long.valueOf(WorldData.this.i)});
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level dimension", new Callable() {
         public String a() throws Exception {
            return String.valueOf(WorldData.this.m);
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level storage version", new Callable() {
         public String a() throws Exception {
            String s = "Unknown?";

            try {
               switch(WorldData.this.o) {
               case 19132:
                  s = "McRegion";
                  break;
               case 19133:
                  s = "Anvil";
               }
            } catch (Throwable var2) {
               ;
            }

            return String.format("0x%05X - %s", new Object[]{Integer.valueOf(WorldData.this.o), s});
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level weather", new Callable() {
         public String a() throws Exception {
            return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[]{Integer.valueOf(WorldData.this.r), Boolean.valueOf(WorldData.this.q), Integer.valueOf(WorldData.this.t), Boolean.valueOf(WorldData.this.s)});
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      p_a_1_.a("Level game mode", new Callable() {
         public String a() throws Exception {
            return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[]{WorldData.this.u.b(), Integer.valueOf(WorldData.this.u.getId()), Boolean.valueOf(WorldData.this.w), Boolean.valueOf(WorldData.this.x)});
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
   }

   public void checkName(String p_checkName_1_) {
      if(!this.n.equals(p_checkName_1_)) {
         this.n = p_checkName_1_;
      }

   }
}

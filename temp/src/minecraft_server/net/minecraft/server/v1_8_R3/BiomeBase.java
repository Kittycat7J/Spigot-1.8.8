package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.server.v1_8_R3.BiomeBaseSub;
import net.minecraft.server.v1_8_R3.BiomeBeach;
import net.minecraft.server.v1_8_R3.BiomeBigHills;
import net.minecraft.server.v1_8_R3.BiomeDecorator;
import net.minecraft.server.v1_8_R3.BiomeDesert;
import net.minecraft.server.v1_8_R3.BiomeForest;
import net.minecraft.server.v1_8_R3.BiomeHell;
import net.minecraft.server.v1_8_R3.BiomeIcePlains;
import net.minecraft.server.v1_8_R3.BiomeJungle;
import net.minecraft.server.v1_8_R3.BiomeMesa;
import net.minecraft.server.v1_8_R3.BiomeMushrooms;
import net.minecraft.server.v1_8_R3.BiomeOcean;
import net.minecraft.server.v1_8_R3.BiomePlains;
import net.minecraft.server.v1_8_R3.BiomeRiver;
import net.minecraft.server.v1_8_R3.BiomeSavanna;
import net.minecraft.server.v1_8_R3.BiomeStoneBeach;
import net.minecraft.server.v1_8_R3.BiomeSwamp;
import net.minecraft.server.v1_8_R3.BiomeTaiga;
import net.minecraft.server.v1_8_R3.BiomeTheEnd;
import net.minecraft.server.v1_8_R3.BlockFlowers;
import net.minecraft.server.v1_8_R3.BlockLongGrass;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSand;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntitySquid;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.EnumCreatureType;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.NoiseGenerator3;
import net.minecraft.server.v1_8_R3.WeightedRandom;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenBigTree;
import net.minecraft.server.v1_8_R3.WorldGenGrass;
import net.minecraft.server.v1_8_R3.WorldGenSwampTree;
import net.minecraft.server.v1_8_R3.WorldGenTallPlant;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;
import net.minecraft.server.v1_8_R3.WorldGenTrees;
import net.minecraft.server.v1_8_R3.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BiomeBase {
   private static final Logger aD = LogManager.getLogger();
   protected static final BiomeBase.BiomeTemperature a = new BiomeBase.BiomeTemperature(0.1F, 0.2F);
   protected static final BiomeBase.BiomeTemperature b = new BiomeBase.BiomeTemperature(-0.5F, 0.0F);
   protected static final BiomeBase.BiomeTemperature c = new BiomeBase.BiomeTemperature(-1.0F, 0.1F);
   protected static final BiomeBase.BiomeTemperature d = new BiomeBase.BiomeTemperature(-1.8F, 0.1F);
   protected static final BiomeBase.BiomeTemperature e = new BiomeBase.BiomeTemperature(0.125F, 0.05F);
   protected static final BiomeBase.BiomeTemperature f = new BiomeBase.BiomeTemperature(0.2F, 0.2F);
   protected static final BiomeBase.BiomeTemperature g = new BiomeBase.BiomeTemperature(0.45F, 0.3F);
   protected static final BiomeBase.BiomeTemperature h = new BiomeBase.BiomeTemperature(1.5F, 0.025F);
   protected static final BiomeBase.BiomeTemperature i = new BiomeBase.BiomeTemperature(1.0F, 0.5F);
   protected static final BiomeBase.BiomeTemperature j = new BiomeBase.BiomeTemperature(0.0F, 0.025F);
   protected static final BiomeBase.BiomeTemperature k = new BiomeBase.BiomeTemperature(0.1F, 0.8F);
   protected static final BiomeBase.BiomeTemperature l = new BiomeBase.BiomeTemperature(0.2F, 0.3F);
   protected static final BiomeBase.BiomeTemperature m = new BiomeBase.BiomeTemperature(-0.2F, 0.1F);
   private static final BiomeBase[] biomes = new BiomeBase[256];
   public static final Set<BiomeBase> n = Sets.<BiomeBase>newHashSet();
   public static final Map<String, BiomeBase> o = Maps.<String, BiomeBase>newHashMap();
   public static final BiomeBase OCEAN = (new BiomeOcean(0)).b(112).a("Ocean").a(c);
   public static final BiomeBase PLAINS = (new BiomePlains(1)).b(9286496).a("Plains");
   public static final BiomeBase DESERT = (new BiomeDesert(2)).b(16421912).a("Desert").b().a(2.0F, 0.0F).a(e);
   public static final BiomeBase EXTREME_HILLS = (new BiomeBigHills(3, false)).b(6316128).a("Extreme Hills").a(i).a(0.2F, 0.3F);
   public static final BiomeBase FOREST = (new BiomeForest(4, 0)).b(353825).a("Forest");
   public static final BiomeBase TAIGA = (new BiomeTaiga(5, 0)).b(747097).a("Taiga").a(5159473).a(0.25F, 0.8F).a(f);
   public static final BiomeBase SWAMPLAND = (new BiomeSwamp(6)).b(522674).a("Swampland").a(9154376).a(m).a(0.8F, 0.9F);
   public static final BiomeBase RIVER = (new BiomeRiver(7)).b(255).a("River").a(b);
   public static final BiomeBase HELL = (new BiomeHell(8)).b(16711680).a("Hell").b().a(2.0F, 0.0F);
   public static final BiomeBase SKY = (new BiomeTheEnd(9)).b(8421631).a("The End").b();
   public static final BiomeBase FROZEN_OCEAN = (new BiomeOcean(10)).b(9474208).a("FrozenOcean").c().a(c).a(0.0F, 0.5F);
   public static final BiomeBase FROZEN_RIVER = (new BiomeRiver(11)).b(10526975).a("FrozenRiver").c().a(b).a(0.0F, 0.5F);
   public static final BiomeBase ICE_PLAINS = (new BiomeIcePlains(12, false)).b(16777215).a("Ice Plains").c().a(0.0F, 0.5F).a(e);
   public static final BiomeBase ICE_MOUNTAINS = (new BiomeIcePlains(13, false)).b(10526880).a("Ice Mountains").c().a(g).a(0.0F, 0.5F);
   public static final BiomeBase MUSHROOM_ISLAND = (new BiomeMushrooms(14)).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).a(l);
   public static final BiomeBase MUSHROOM_SHORE = (new BiomeMushrooms(15)).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).a(j);
   public static final BiomeBase BEACH = (new BiomeBeach(16)).b(16440917).a("Beach").a(0.8F, 0.4F).a(j);
   public static final BiomeBase DESERT_HILLS = (new BiomeDesert(17)).b(13786898).a("DesertHills").b().a(2.0F, 0.0F).a(g);
   public static final BiomeBase FOREST_HILLS = (new BiomeForest(18, 0)).b(2250012).a("ForestHills").a(g);
   public static final BiomeBase TAIGA_HILLS = (new BiomeTaiga(19, 0)).b(1456435).a("TaigaHills").a(5159473).a(0.25F, 0.8F).a(g);
   public static final BiomeBase SMALL_MOUNTAINS = (new BiomeBigHills(20, true)).b(7501978).a("Extreme Hills Edge").a(i.a()).a(0.2F, 0.3F);
   public static final BiomeBase JUNGLE = (new BiomeJungle(21, false)).b(5470985).a("Jungle").a(5470985).a(0.95F, 0.9F);
   public static final BiomeBase JUNGLE_HILLS = (new BiomeJungle(22, false)).b(2900485).a("JungleHills").a(5470985).a(0.95F, 0.9F).a(g);
   public static final BiomeBase JUNGLE_EDGE = (new BiomeJungle(23, true)).b(6458135).a("JungleEdge").a(5470985).a(0.95F, 0.8F);
   public static final BiomeBase DEEP_OCEAN = (new BiomeOcean(24)).b(48).a("Deep Ocean").a(d);
   public static final BiomeBase STONE_BEACH = (new BiomeStoneBeach(25)).b(10658436).a("Stone Beach").a(0.2F, 0.3F).a(k);
   public static final BiomeBase COLD_BEACH = (new BiomeBeach(26)).b(16445632).a("Cold Beach").a(0.05F, 0.3F).a(j).c();
   public static final BiomeBase BIRCH_FOREST = (new BiomeForest(27, 2)).a("Birch Forest").b(3175492);
   public static final BiomeBase BIRCH_FOREST_HILLS = (new BiomeForest(28, 2)).a("Birch Forest Hills").b(2055986).a(g);
   public static final BiomeBase ROOFED_FOREST = (new BiomeForest(29, 3)).b(4215066).a("Roofed Forest");
   public static final BiomeBase COLD_TAIGA = (new BiomeTaiga(30, 0)).b(3233098).a("Cold Taiga").a(5159473).c().a(-0.5F, 0.4F).a(f).c(16777215);
   public static final BiomeBase COLD_TAIGA_HILLS = (new BiomeTaiga(31, 0)).b(2375478).a("Cold Taiga Hills").a(5159473).c().a(-0.5F, 0.4F).a(g).c(16777215);
   public static final BiomeBase MEGA_TAIGA = (new BiomeTaiga(32, 1)).b(5858897).a("Mega Taiga").a(5159473).a(0.3F, 0.8F).a(f);
   public static final BiomeBase MEGA_TAIGA_HILLS = (new BiomeTaiga(33, 1)).b(4542270).a("Mega Taiga Hills").a(5159473).a(0.3F, 0.8F).a(g);
   public static final BiomeBase EXTREME_HILLS_PLUS = (new BiomeBigHills(34, true)).b(5271632).a("Extreme Hills+").a(i).a(0.2F, 0.3F);
   public static final BiomeBase SAVANNA = (new BiomeSavanna(35)).b(12431967).a("Savanna").a(1.2F, 0.0F).b().a(e);
   public static final BiomeBase SAVANNA_PLATEAU = (new BiomeSavanna(36)).b(10984804).a("Savanna Plateau").a(1.0F, 0.0F).b().a(h);
   public static final BiomeBase MESA = (new BiomeMesa(37, false, false)).b(14238997).a("Mesa");
   public static final BiomeBase MESA_PLATEAU_F = (new BiomeMesa(38, false, true)).b(11573093).a("Mesa Plateau F").a(h);
   public static final BiomeBase MESA_PLATEAU = (new BiomeMesa(39, false, false)).b(13274213).a("Mesa Plateau").a(h);
   public static final BiomeBase ad = OCEAN;
   protected static final NoiseGenerator3 ae;
   protected static final NoiseGenerator3 af;
   protected static final WorldGenTallPlant ag;
   public String ah;
   public int ai;
   public int aj;
   public IBlockData ak = Blocks.GRASS.getBlockData();
   public IBlockData al = Blocks.DIRT.getBlockData();
   public int am = 5169201;
   public float an;
   public float ao;
   public float temperature;
   public float humidity;
   public int ar;
   public BiomeDecorator as;
   protected List<BiomeBase.BiomeMeta> at;
   protected List<BiomeBase.BiomeMeta> au;
   protected List<BiomeBase.BiomeMeta> av;
   protected List<BiomeBase.BiomeMeta> aw;
   protected boolean ax;
   protected boolean ay;
   public final int id;
   protected WorldGenTrees aA;
   protected WorldGenBigTree aB;
   protected WorldGenSwampTree aC;

   protected BiomeBase(int p_i566_1_) {
      this.an = a.a;
      this.ao = a.b;
      this.temperature = 0.5F;
      this.humidity = 0.5F;
      this.ar = 16777215;
      this.at = Lists.<BiomeBase.BiomeMeta>newArrayList();
      this.au = Lists.<BiomeBase.BiomeMeta>newArrayList();
      this.av = Lists.<BiomeBase.BiomeMeta>newArrayList();
      this.aw = Lists.<BiomeBase.BiomeMeta>newArrayList();
      this.ay = true;
      this.aA = new WorldGenTrees(false);
      this.aB = new WorldGenBigTree(false);
      this.aC = new WorldGenSwampTree();
      this.id = p_i566_1_;
      biomes[p_i566_1_] = this;
      this.as = this.a();
      this.au.add(new BiomeBase.BiomeMeta(EntitySheep.class, 12, 4, 4));
      this.au.add(new BiomeBase.BiomeMeta(EntityRabbit.class, 10, 3, 3));
      this.au.add(new BiomeBase.BiomeMeta(EntityPig.class, 10, 4, 4));
      this.au.add(new BiomeBase.BiomeMeta(EntityChicken.class, 10, 4, 4));
      this.au.add(new BiomeBase.BiomeMeta(EntityCow.class, 8, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntitySpider.class, 100, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntityZombie.class, 100, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntitySkeleton.class, 100, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntityCreeper.class, 100, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntitySlime.class, 100, 4, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntityEnderman.class, 10, 1, 4));
      this.at.add(new BiomeBase.BiomeMeta(EntityWitch.class, 5, 1, 1));
      this.av.add(new BiomeBase.BiomeMeta(EntitySquid.class, 10, 4, 4));
      this.aw.add(new BiomeBase.BiomeMeta(EntityBat.class, 10, 8, 8));
   }

   protected BiomeDecorator a() {
      return new BiomeDecorator();
   }

   protected BiomeBase a(float p_a_1_, float p_a_2_) {
      if(p_a_1_ > 0.1F && p_a_1_ < 0.2F) {
         throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
      } else {
         this.temperature = p_a_1_;
         this.humidity = p_a_2_;
         return this;
      }
   }

   protected final BiomeBase a(BiomeBase.BiomeTemperature p_a_1_) {
      this.an = p_a_1_.a;
      this.ao = p_a_1_.b;
      return this;
   }

   protected BiomeBase b() {
      this.ay = false;
      return this;
   }

   public WorldGenTreeAbstract a(Random p_a_1_) {
      return (WorldGenTreeAbstract)(p_a_1_.nextInt(10) == 0?this.aB:this.aA);
   }

   public WorldGenerator b(Random p_b_1_) {
      return new WorldGenGrass(BlockLongGrass.EnumTallGrassType.GRASS);
   }

   public BlockFlowers.EnumFlowerVarient a(Random p_a_1_, BlockPosition p_a_2_) {
      return p_a_1_.nextInt(3) > 0?BlockFlowers.EnumFlowerVarient.DANDELION:BlockFlowers.EnumFlowerVarient.POPPY;
   }

   protected BiomeBase c() {
      this.ax = true;
      return this;
   }

   protected BiomeBase a(String p_a_1_) {
      this.ah = p_a_1_;
      return this;
   }

   protected BiomeBase a(int p_a_1_) {
      this.am = p_a_1_;
      return this;
   }

   protected BiomeBase b(int p_b_1_) {
      this.a(p_b_1_, false);
      return this;
   }

   protected BiomeBase c(int p_c_1_) {
      this.aj = p_c_1_;
      return this;
   }

   protected BiomeBase a(int p_a_1_, boolean p_a_2_) {
      this.ai = p_a_1_;
      if(p_a_2_) {
         this.aj = (p_a_1_ & 16711422) >> 1;
      } else {
         this.aj = p_a_1_;
      }

      return this;
   }

   public List<BiomeBase.BiomeMeta> getMobs(EnumCreatureType p_getMobs_1_) {
      switch(p_getMobs_1_) {
      case MONSTER:
         return this.at;
      case CREATURE:
         return this.au;
      case WATER_CREATURE:
         return this.av;
      case AMBIENT:
         return this.aw;
      default:
         return Collections.emptyList();
      }
   }

   public boolean d() {
      return this.j();
   }

   public boolean e() {
      return this.j()?false:this.ay;
   }

   public boolean f() {
      return this.humidity > 0.85F;
   }

   public float g() {
      return 0.1F;
   }

   public final int h() {
      return (int)(this.humidity * 65536.0F);
   }

   public final float a(BlockPosition p_a_1_) {
      if(p_a_1_.getY() > 64) {
         float fx = (float)(ae.a((double)p_a_1_.getX() * 1.0D / 8.0D, (double)p_a_1_.getZ() * 1.0D / 8.0D) * 4.0D);
         return this.temperature - (fx + (float)p_a_1_.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return this.temperature;
      }
   }

   public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_) {
      this.as.a(p_a_1_, p_a_2_, this, p_a_3_);
   }

   public boolean j() {
      return this.ax;
   }

   public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_) {
      this.b(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
   }

   public final void b(World p_b_1_, Random p_b_2_, ChunkSnapshot p_b_3_, int p_b_4_, int p_b_5_, double p_b_6_) {
      int ix = p_b_1_.F();
      IBlockData iblockdata = this.ak;
      IBlockData iblockdata1 = this.al;
      int jx = -1;
      int kx = (int)(p_b_6_ / 3.0D + 3.0D + p_b_2_.nextDouble() * 0.25D);
      int lx = p_b_4_ & 15;
      int i1 = p_b_5_ & 15;
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int j1 = 255; j1 >= 0; --j1) {
         if(j1 <= p_b_2_.nextInt(5)) {
            p_b_3_.a(i1, j1, lx, Blocks.BEDROCK.getBlockData());
         } else {
            IBlockData iblockdata2 = p_b_3_.a(i1, j1, lx);
            if(iblockdata2.getBlock().getMaterial() == Material.AIR) {
               jx = -1;
            } else if(iblockdata2.getBlock() == Blocks.STONE) {
               if(jx == -1) {
                  if(kx <= 0) {
                     iblockdata = null;
                     iblockdata1 = Blocks.STONE.getBlockData();
                  } else if(j1 >= ix - 4 && j1 <= ix + 1) {
                     iblockdata = this.ak;
                     iblockdata1 = this.al;
                  }

                  if(j1 < ix && (iblockdata == null || iblockdata.getBlock().getMaterial() == Material.AIR)) {
                     if(this.a((BlockPosition)blockposition$mutableblockposition.c(p_b_4_, j1, p_b_5_)) < 0.15F) {
                        iblockdata = Blocks.ICE.getBlockData();
                     } else {
                        iblockdata = Blocks.WATER.getBlockData();
                     }
                  }

                  jx = kx;
                  if(j1 >= ix - 1) {
                     p_b_3_.a(i1, j1, lx, iblockdata);
                  } else if(j1 < ix - 7 - kx) {
                     iblockdata = null;
                     iblockdata1 = Blocks.STONE.getBlockData();
                     p_b_3_.a(i1, j1, lx, Blocks.GRAVEL.getBlockData());
                  } else {
                     p_b_3_.a(i1, j1, lx, iblockdata1);
                  }
               } else if(jx > 0) {
                  --jx;
                  p_b_3_.a(i1, j1, lx, iblockdata1);
                  if(jx == 0 && iblockdata1.getBlock() == Blocks.SAND) {
                     jx = p_b_2_.nextInt(4) + Math.max(0, j1 - 63);
                     iblockdata1 = iblockdata1.get(BlockSand.VARIANT) == BlockSand.EnumSandVariant.RED_SAND?Blocks.RED_SANDSTONE.getBlockData():Blocks.SANDSTONE.getBlockData();
                  }
               }
            }
         }
      }

   }

   protected BiomeBase k() {
      return this.d(this.id + 128);
   }

   protected BiomeBase d(int p_d_1_) {
      return new BiomeBaseSub(p_d_1_, this);
   }

   public Class<? extends BiomeBase> l() {
      return this.getClass();
   }

   public boolean a(BiomeBase p_a_1_) {
      return p_a_1_ == this?true:(p_a_1_ == null?false:this.l() == p_a_1_.l());
   }

   public BiomeBase.EnumTemperature m() {
      return (double)this.temperature < 0.2D?BiomeBase.EnumTemperature.COLD:((double)this.temperature < 1.0D?BiomeBase.EnumTemperature.MEDIUM:BiomeBase.EnumTemperature.WARM);
   }

   public static BiomeBase[] getBiomes() {
      return biomes;
   }

   public static BiomeBase getBiome(int p_getBiome_0_) {
      return getBiome(p_getBiome_0_, (BiomeBase)null);
   }

   public static BiomeBase getBiome(int p_getBiome_0_, BiomeBase p_getBiome_1_) {
      if(p_getBiome_0_ >= 0 && p_getBiome_0_ <= biomes.length) {
         BiomeBase biomebase = biomes[p_getBiome_0_];
         return biomebase == null?p_getBiome_1_:biomebase;
      } else {
         aD.warn("Biome ID is out of bounds: " + p_getBiome_0_ + ", defaulting to 0 (Ocean)");
         return OCEAN;
      }
   }

   static {
      PLAINS.k();
      DESERT.k();
      FOREST.k();
      TAIGA.k();
      SWAMPLAND.k();
      ICE_PLAINS.k();
      JUNGLE.k();
      JUNGLE_EDGE.k();
      COLD_TAIGA.k();
      SAVANNA.k();
      SAVANNA_PLATEAU.k();
      MESA.k();
      MESA_PLATEAU_F.k();
      MESA_PLATEAU.k();
      BIRCH_FOREST.k();
      BIRCH_FOREST_HILLS.k();
      ROOFED_FOREST.k();
      MEGA_TAIGA.k();
      EXTREME_HILLS.k();
      EXTREME_HILLS_PLUS.k();
      MEGA_TAIGA.d(MEGA_TAIGA_HILLS.id + 128).a("Redwood Taiga Hills M");

      for(BiomeBase biomebase : biomes) {
         if(biomebase != null) {
            if(o.containsKey(biomebase.ah)) {
               throw new Error("Biome \"" + biomebase.ah + "\" is defined as both ID " + ((BiomeBase)o.get(biomebase.ah)).id + " and " + biomebase.id);
            }

            o.put(biomebase.ah, biomebase);
            if(biomebase.id < 128) {
               n.add(biomebase);
            }
         }
      }

      n.remove(HELL);
      n.remove(SKY);
      n.remove(FROZEN_OCEAN);
      n.remove(SMALL_MOUNTAINS);
      ae = new NoiseGenerator3(new Random(1234L), 1);
      af = new NoiseGenerator3(new Random(2345L), 1);
      ag = new WorldGenTallPlant();
   }

   public static class BiomeMeta extends WeightedRandom.WeightedRandomChoice {
      public Class<? extends EntityInsentient> b;
      public int c;
      public int d;

      public BiomeMeta(Class<? extends EntityInsentient> p_i565_1_, int p_i565_2_, int p_i565_3_, int p_i565_4_) {
         super(p_i565_2_);
         this.b = p_i565_1_;
         this.c = p_i565_3_;
         this.d = p_i565_4_;
      }

      public String toString() {
         return this.b.getSimpleName() + "*(" + this.c + "-" + this.d + "):" + this.a;
      }
   }

   public static class BiomeTemperature {
      public float a;
      public float b;

      public BiomeTemperature(float p_i563_1_, float p_i563_2_) {
         this.a = p_i563_1_;
         this.b = p_i563_2_;
      }

      public BiomeBase.BiomeTemperature a() {
         return new BiomeBase.BiomeTemperature(this.a * 0.8F, this.b * 0.6F);
      }
   }

   public static enum EnumTemperature {
      OCEAN,
      COLD,
      MEDIUM,
      WARM;
   }
}

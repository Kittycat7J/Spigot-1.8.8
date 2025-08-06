package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockAir;
import net.minecraft.server.v1_8_R3.BlockAnvil;
import net.minecraft.server.v1_8_R3.BlockBanner;
import net.minecraft.server.v1_8_R3.BlockBarrier;
import net.minecraft.server.v1_8_R3.BlockBeacon;
import net.minecraft.server.v1_8_R3.BlockBed;
import net.minecraft.server.v1_8_R3.BlockBloodStone;
import net.minecraft.server.v1_8_R3.BlockBookshelf;
import net.minecraft.server.v1_8_R3.BlockBrewingStand;
import net.minecraft.server.v1_8_R3.BlockCactus;
import net.minecraft.server.v1_8_R3.BlockCake;
import net.minecraft.server.v1_8_R3.BlockCarpet;
import net.minecraft.server.v1_8_R3.BlockCarrots;
import net.minecraft.server.v1_8_R3.BlockCauldron;
import net.minecraft.server.v1_8_R3.BlockChest;
import net.minecraft.server.v1_8_R3.BlockClay;
import net.minecraft.server.v1_8_R3.BlockCloth;
import net.minecraft.server.v1_8_R3.BlockCobbleWall;
import net.minecraft.server.v1_8_R3.BlockCocoa;
import net.minecraft.server.v1_8_R3.BlockCommand;
import net.minecraft.server.v1_8_R3.BlockCrops;
import net.minecraft.server.v1_8_R3.BlockDaylightDetector;
import net.minecraft.server.v1_8_R3.BlockDeadBush;
import net.minecraft.server.v1_8_R3.BlockDirt;
import net.minecraft.server.v1_8_R3.BlockDispenser;
import net.minecraft.server.v1_8_R3.BlockDoor;
import net.minecraft.server.v1_8_R3.BlockDoubleStep;
import net.minecraft.server.v1_8_R3.BlockDoubleStoneStep2;
import net.minecraft.server.v1_8_R3.BlockDoubleWoodStep;
import net.minecraft.server.v1_8_R3.BlockDragonEgg;
import net.minecraft.server.v1_8_R3.BlockDropper;
import net.minecraft.server.v1_8_R3.BlockEnchantmentTable;
import net.minecraft.server.v1_8_R3.BlockEnderChest;
import net.minecraft.server.v1_8_R3.BlockEnderPortal;
import net.minecraft.server.v1_8_R3.BlockEnderPortalFrame;
import net.minecraft.server.v1_8_R3.BlockFence;
import net.minecraft.server.v1_8_R3.BlockFenceGate;
import net.minecraft.server.v1_8_R3.BlockFire;
import net.minecraft.server.v1_8_R3.BlockFloorSign;
import net.minecraft.server.v1_8_R3.BlockFlowerPot;
import net.minecraft.server.v1_8_R3.BlockFlowing;
import net.minecraft.server.v1_8_R3.BlockFurnace;
import net.minecraft.server.v1_8_R3.BlockGlass;
import net.minecraft.server.v1_8_R3.BlockGrass;
import net.minecraft.server.v1_8_R3.BlockGravel;
import net.minecraft.server.v1_8_R3.BlockHardenedClay;
import net.minecraft.server.v1_8_R3.BlockHay;
import net.minecraft.server.v1_8_R3.BlockHopper;
import net.minecraft.server.v1_8_R3.BlockHugeMushroom;
import net.minecraft.server.v1_8_R3.BlockIce;
import net.minecraft.server.v1_8_R3.BlockJukeBox;
import net.minecraft.server.v1_8_R3.BlockLadder;
import net.minecraft.server.v1_8_R3.BlockLeaves1;
import net.minecraft.server.v1_8_R3.BlockLeaves2;
import net.minecraft.server.v1_8_R3.BlockLever;
import net.minecraft.server.v1_8_R3.BlockLightStone;
import net.minecraft.server.v1_8_R3.BlockLog1;
import net.minecraft.server.v1_8_R3.BlockLog2;
import net.minecraft.server.v1_8_R3.BlockLongGrass;
import net.minecraft.server.v1_8_R3.BlockMelon;
import net.minecraft.server.v1_8_R3.BlockMinecartDetector;
import net.minecraft.server.v1_8_R3.BlockMinecartTrack;
import net.minecraft.server.v1_8_R3.BlockMobSpawner;
import net.minecraft.server.v1_8_R3.BlockMonsterEggs;
import net.minecraft.server.v1_8_R3.BlockMushroom;
import net.minecraft.server.v1_8_R3.BlockMycel;
import net.minecraft.server.v1_8_R3.BlockNetherWart;
import net.minecraft.server.v1_8_R3.BlockNetherbrick;
import net.minecraft.server.v1_8_R3.BlockNote;
import net.minecraft.server.v1_8_R3.BlockObsidian;
import net.minecraft.server.v1_8_R3.BlockOre;
import net.minecraft.server.v1_8_R3.BlockPackedIce;
import net.minecraft.server.v1_8_R3.BlockPiston;
import net.minecraft.server.v1_8_R3.BlockPistonExtension;
import net.minecraft.server.v1_8_R3.BlockPistonMoving;
import net.minecraft.server.v1_8_R3.BlockPortal;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockPotatoes;
import net.minecraft.server.v1_8_R3.BlockPowered;
import net.minecraft.server.v1_8_R3.BlockPoweredRail;
import net.minecraft.server.v1_8_R3.BlockPressurePlateBinary;
import net.minecraft.server.v1_8_R3.BlockPressurePlateWeighted;
import net.minecraft.server.v1_8_R3.BlockPrismarine;
import net.minecraft.server.v1_8_R3.BlockPumpkin;
import net.minecraft.server.v1_8_R3.BlockQuartz;
import net.minecraft.server.v1_8_R3.BlockRedFlowers;
import net.minecraft.server.v1_8_R3.BlockRedSandstone;
import net.minecraft.server.v1_8_R3.BlockRedstoneComparator;
import net.minecraft.server.v1_8_R3.BlockRedstoneLamp;
import net.minecraft.server.v1_8_R3.BlockRedstoneOre;
import net.minecraft.server.v1_8_R3.BlockRedstoneTorch;
import net.minecraft.server.v1_8_R3.BlockRedstoneWire;
import net.minecraft.server.v1_8_R3.BlockReed;
import net.minecraft.server.v1_8_R3.BlockRepeater;
import net.minecraft.server.v1_8_R3.BlockSand;
import net.minecraft.server.v1_8_R3.BlockSandStone;
import net.minecraft.server.v1_8_R3.BlockSapling;
import net.minecraft.server.v1_8_R3.BlockSeaLantern;
import net.minecraft.server.v1_8_R3.BlockSkull;
import net.minecraft.server.v1_8_R3.BlockSlime;
import net.minecraft.server.v1_8_R3.BlockSlowSand;
import net.minecraft.server.v1_8_R3.BlockSmoothBrick;
import net.minecraft.server.v1_8_R3.BlockSnow;
import net.minecraft.server.v1_8_R3.BlockSnowBlock;
import net.minecraft.server.v1_8_R3.BlockSoil;
import net.minecraft.server.v1_8_R3.BlockSponge;
import net.minecraft.server.v1_8_R3.BlockStainedGlass;
import net.minecraft.server.v1_8_R3.BlockStainedGlassPane;
import net.minecraft.server.v1_8_R3.BlockStairs;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockStationary;
import net.minecraft.server.v1_8_R3.BlockStem;
import net.minecraft.server.v1_8_R3.BlockStep;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.BlockStone;
import net.minecraft.server.v1_8_R3.BlockStoneButton;
import net.minecraft.server.v1_8_R3.BlockStoneStep2;
import net.minecraft.server.v1_8_R3.BlockTNT;
import net.minecraft.server.v1_8_R3.BlockTallPlant;
import net.minecraft.server.v1_8_R3.BlockThin;
import net.minecraft.server.v1_8_R3.BlockTorch;
import net.minecraft.server.v1_8_R3.BlockTrapdoor;
import net.minecraft.server.v1_8_R3.BlockTripwire;
import net.minecraft.server.v1_8_R3.BlockTripwireHook;
import net.minecraft.server.v1_8_R3.BlockVine;
import net.minecraft.server.v1_8_R3.BlockWallSign;
import net.minecraft.server.v1_8_R3.BlockWaterLily;
import net.minecraft.server.v1_8_R3.BlockWeb;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.BlockWoodButton;
import net.minecraft.server.v1_8_R3.BlockWoodStep;
import net.minecraft.server.v1_8_R3.BlockWorkbench;
import net.minecraft.server.v1_8_R3.BlockYellowFlowers;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.RegistryBlocks;
import net.minecraft.server.v1_8_R3.RegistryID;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import org.spigotmc.AsyncCatcher;

public class Block {
   private static final MinecraftKey a = new MinecraftKey("air");
   public static final RegistryBlocks<MinecraftKey, Block> REGISTRY = new RegistryBlocks(a);
   public static final RegistryID<IBlockData> d = new RegistryID();
   private CreativeModeTab creativeTab;
   public static final Block.StepSound e = new Block.StepSound("stone", 1.0F, 1.0F);
   public static final Block.StepSound f = new Block.StepSound("wood", 1.0F, 1.0F);
   public static final Block.StepSound g = new Block.StepSound("gravel", 1.0F, 1.0F);
   public static final Block.StepSound h = new Block.StepSound("grass", 1.0F, 1.0F);
   public static final Block.StepSound i = new Block.StepSound("stone", 1.0F, 1.0F);
   public static final Block.StepSound j = new Block.StepSound("stone", 1.0F, 1.5F);
   public static final Block.StepSound k = new Block.StepSound("stone", 1.0F, 1.0F) {
      public String getBreakSound() {
         return "dig.glass";
      }

      public String getPlaceSound() {
         return "step.stone";
      }
   };
   public static final Block.StepSound l = new Block.StepSound("cloth", 1.0F, 1.0F);
   public static final Block.StepSound m = new Block.StepSound("sand", 1.0F, 1.0F);
   public static final Block.StepSound n = new Block.StepSound("snow", 1.0F, 1.0F);
   public static final Block.StepSound o = new Block.StepSound("ladder", 1.0F, 1.0F) {
      public String getBreakSound() {
         return "dig.wood";
      }
   };
   public static final Block.StepSound p = new Block.StepSound("anvil", 0.3F, 1.0F) {
      public String getBreakSound() {
         return "dig.stone";
      }

      public String getPlaceSound() {
         return "random.anvil_land";
      }
   };
   public static final Block.StepSound q = new Block.StepSound("slime", 1.0F, 1.0F) {
      public String getBreakSound() {
         return "mob.slime.big";
      }

      public String getPlaceSound() {
         return "mob.slime.big";
      }

      public String getStepSound() {
         return "mob.slime.small";
      }
   };
   protected boolean r;
   protected int s;
   protected boolean t;
   protected int u;
   protected boolean v;
   protected float strength;
   protected float durability;
   protected boolean y;
   protected boolean z;
   protected boolean isTileEntity;
   protected double minX;
   protected double minY;
   protected double minZ;
   protected double maxX;
   protected double maxY;
   protected double maxZ;
   public Block.StepSound stepSound;
   public float I;
   protected final Material material;
   protected final MaterialMapColor K;
   public float frictionFactor;
   protected final BlockStateList blockStateList;
   private IBlockData blockData;
   private String name;

   public static int getId(Block p_getId_0_) {
      return REGISTRY.b(p_getId_0_);
   }

   public static int getCombinedId(IBlockData p_getCombinedId_0_) {
      Block block = p_getCombinedId_0_.getBlock();
      return getId(block) + (block.toLegacyData(p_getCombinedId_0_) << 12);
   }

   public static Block getById(int p_getById_0_) {
      return (Block)REGISTRY.a(p_getById_0_);
   }

   public static IBlockData getByCombinedId(int p_getByCombinedId_0_) {
      int i = p_getByCombinedId_0_ & 4095;
      int j = p_getByCombinedId_0_ >> 12 & 15;
      return getById(i).fromLegacyData(j);
   }

   public static Block asBlock(Item p_asBlock_0_) {
      return p_asBlock_0_ instanceof ItemBlock?((ItemBlock)p_asBlock_0_).d():null;
   }

   public static Block getByName(String p_getByName_0_) {
      MinecraftKey minecraftkey = new MinecraftKey(p_getByName_0_);
      if(REGISTRY.d(minecraftkey)) {
         return (Block)REGISTRY.get(minecraftkey);
      } else {
         try {
            return (Block)REGISTRY.a(Integer.parseInt(p_getByName_0_));
         } catch (NumberFormatException var2) {
            return null;
         }
      }
   }

   public boolean o() {
      return this.r;
   }

   public int p() {
      return this.s;
   }

   public int r() {
      return this.u;
   }

   public boolean s() {
      return this.v;
   }

   public Material getMaterial() {
      return this.material;
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return this.K;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData();
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      if(p_toLegacyData_1_ != null && !p_toLegacyData_1_.a().isEmpty()) {
         throw new IllegalArgumentException("Don\'t know how to convert " + p_toLegacyData_1_ + " back into data...");
      } else {
         return 0;
      }
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      return p_updateState_1_;
   }

   public Block(Material p_i424_1_, MaterialMapColor p_i424_2_) {
      this.y = true;
      this.stepSound = e;
      this.I = 1.0F;
      this.frictionFactor = 0.6F;
      this.material = p_i424_1_;
      this.K = p_i424_2_;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.r = this.c();
      this.s = this.c()?255:0;
      this.t = !p_i424_1_.blocksLight();
      this.blockStateList = this.getStateList();
      this.j(this.blockStateList.getBlockData());
   }

   protected Block(Material p_i425_1_) {
      this(p_i425_1_, p_i425_1_.r());
   }

   protected Block a(Block.StepSound p_a_1_) {
      this.stepSound = p_a_1_;
      return this;
   }

   protected Block e(int p_e_1_) {
      this.s = p_e_1_;
      return this;
   }

   protected Block a(float p_a_1_) {
      this.u = (int)(15.0F * p_a_1_);
      return this;
   }

   protected Block b(float p_b_1_) {
      this.durability = p_b_1_ * 3.0F;
      return this;
   }

   public boolean u() {
      return this.material.isSolid() && this.d();
   }

   public boolean isOccluding() {
      return this.material.k() && this.d() && !this.isPowerSource();
   }

   public boolean w() {
      return this.material.isSolid() && this.d();
   }

   public boolean d() {
      return true;
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_) {
      return !this.material.isSolid();
   }

   public int b() {
      return 3;
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_) {
      return false;
   }

   protected Block c(float p_c_1_) {
      this.strength = p_c_1_;
      if(this.durability < p_c_1_ * 5.0F) {
         this.durability = p_c_1_ * 5.0F;
      }

      return this;
   }

   protected Block x() {
      this.c(-1.0F);
      return this;
   }

   public float g(World p_g_1_, BlockPosition p_g_2_) {
      return this.strength;
   }

   protected Block a(boolean p_a_1_) {
      this.z = p_a_1_;
      return this;
   }

   public boolean isTicking() {
      return this.z;
   }

   public boolean isTileEntity() {
      return this.isTileEntity;
   }

   protected final void a(float p_a_1_, float p_a_2_, float p_a_3_, float p_a_4_, float p_a_5_, float p_a_6_) {
      this.minX = (double)p_a_1_;
      this.minY = (double)p_a_2_;
      this.minZ = (double)p_a_3_;
      this.maxX = (double)p_a_4_;
      this.maxY = (double)p_a_5_;
      this.maxZ = (double)p_a_6_;
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_, EnumDirection p_b_3_) {
      return p_b_1_.getType(p_b_2_).getBlock().getMaterial().isBuildable();
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_) {
      AxisAlignedBB axisalignedbb = this.a(p_a_1_, p_a_2_, p_a_3_);
      if(axisalignedbb != null && p_a_4_.b(axisalignedbb)) {
         p_a_5_.add(axisalignedbb);
      }

   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return new AxisAlignedBB((double)p_a_2_.getX() + this.minX, (double)p_a_2_.getY() + this.minY, (double)p_a_2_.getZ() + this.minZ, (double)p_a_2_.getX() + this.maxX, (double)p_a_2_.getY() + this.maxY, (double)p_a_2_.getZ() + this.maxZ);
   }

   public boolean c() {
      return true;
   }

   public boolean a(IBlockData p_a_1_, boolean p_a_2_) {
      return this.A();
   }

   public boolean A() {
      return true;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_) {
      this.b(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
   }

   public void postBreak(World p_postBreak_1_, BlockPosition p_postBreak_2_, IBlockData p_postBreak_3_) {
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
   }

   public int a(World p_a_1_) {
      return 10;
   }

   public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_) {
      AsyncCatcher.catchOp("block onPlace");
   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      AsyncCatcher.catchOp("block remove");
   }

   public int a(Random p_a_1_) {
      return 1;
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(this);
   }

   public float getDamage(EntityHuman p_getDamage_1_, World p_getDamage_2_, BlockPosition p_getDamage_3_) {
      float f = this.g(p_getDamage_2_, p_getDamage_3_);
      return f < 0.0F?0.0F:(!p_getDamage_1_.b(this)?p_getDamage_1_.a(this) / f / 100.0F:p_getDamage_1_.a(this) / f / 30.0F);
   }

   public final void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, int p_b_4_) {
      this.dropNaturally(p_b_1_, p_b_2_, p_b_3_, 1.0F, p_b_4_);
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      if(!p_dropNaturally_1_.isClientSide) {
         int i = this.getDropCount(p_dropNaturally_5_, p_dropNaturally_1_.random);

         for(int j = 0; j < i; ++j) {
            if(p_dropNaturally_1_.random.nextFloat() < p_dropNaturally_4_) {
               Item item = this.getDropType(p_dropNaturally_3_, p_dropNaturally_1_.random, p_dropNaturally_5_);
               if(item != null) {
                  a(p_dropNaturally_1_, p_dropNaturally_2_, new ItemStack(item, 1, this.getDropData(p_dropNaturally_3_)));
               }
            }
         }
      }

   }

   public static void a(World p_a_0_, BlockPosition p_a_1_, ItemStack p_a_2_) {
      if(!p_a_0_.isClientSide && p_a_0_.getGameRules().getBoolean("doTileDrops")) {
         float f = 0.5F;
         double d0 = (double)(p_a_0_.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
         double d1 = (double)(p_a_0_.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
         double d2 = (double)(p_a_0_.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
         EntityItem entityitem = new EntityItem(p_a_0_, (double)p_a_1_.getX() + d0, (double)p_a_1_.getY() + d1, (double)p_a_1_.getZ() + d2, p_a_2_);
         entityitem.p();
         p_a_0_.addEntity(entityitem);
      }

   }

   protected void dropExperience(World p_dropExperience_1_, BlockPosition p_dropExperience_2_, int p_dropExperience_3_) {
      if(!p_dropExperience_1_.isClientSide) {
         while(p_dropExperience_3_ > 0) {
            int i = EntityExperienceOrb.getOrbValue(p_dropExperience_3_);
            p_dropExperience_3_ -= i;
            p_dropExperience_1_.addEntity(new EntityExperienceOrb(p_dropExperience_1_, (double)p_dropExperience_2_.getX() + 0.5D, (double)p_dropExperience_2_.getY() + 0.5D, (double)p_dropExperience_2_.getZ() + 0.5D, i));
         }
      }

   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return 0;
   }

   public float a(Entity p_a_1_) {
      return this.durability / 5.0F;
   }

   public MovingObjectPosition a(World p_a_1_, BlockPosition p_a_2_, Vec3D p_a_3_, Vec3D p_a_4_) {
      this.updateShape(p_a_1_, p_a_2_);
      p_a_3_ = p_a_3_.add((double)(-p_a_2_.getX()), (double)(-p_a_2_.getY()), (double)(-p_a_2_.getZ()));
      p_a_4_ = p_a_4_.add((double)(-p_a_2_.getX()), (double)(-p_a_2_.getY()), (double)(-p_a_2_.getZ()));
      Vec3D vec3d = p_a_3_.a(p_a_4_, this.minX);
      Vec3D vec3d1 = p_a_3_.a(p_a_4_, this.maxX);
      Vec3D vec3d2 = p_a_3_.b(p_a_4_, this.minY);
      Vec3D vec3d3 = p_a_3_.b(p_a_4_, this.maxY);
      Vec3D vec3d4 = p_a_3_.c(p_a_4_, this.minZ);
      Vec3D vec3d5 = p_a_3_.c(p_a_4_, this.maxZ);
      if(!this.a(vec3d)) {
         vec3d = null;
      }

      if(!this.a(vec3d1)) {
         vec3d1 = null;
      }

      if(!this.b(vec3d2)) {
         vec3d2 = null;
      }

      if(!this.b(vec3d3)) {
         vec3d3 = null;
      }

      if(!this.c(vec3d4)) {
         vec3d4 = null;
      }

      if(!this.c(vec3d5)) {
         vec3d5 = null;
      }

      Vec3D vec3d6 = null;
      if(vec3d != null && (vec3d6 == null || p_a_3_.distanceSquared(vec3d) < p_a_3_.distanceSquared(vec3d6))) {
         vec3d6 = vec3d;
      }

      if(vec3d1 != null && (vec3d6 == null || p_a_3_.distanceSquared(vec3d1) < p_a_3_.distanceSquared(vec3d6))) {
         vec3d6 = vec3d1;
      }

      if(vec3d2 != null && (vec3d6 == null || p_a_3_.distanceSquared(vec3d2) < p_a_3_.distanceSquared(vec3d6))) {
         vec3d6 = vec3d2;
      }

      if(vec3d3 != null && (vec3d6 == null || p_a_3_.distanceSquared(vec3d3) < p_a_3_.distanceSquared(vec3d6))) {
         vec3d6 = vec3d3;
      }

      if(vec3d4 != null && (vec3d6 == null || p_a_3_.distanceSquared(vec3d4) < p_a_3_.distanceSquared(vec3d6))) {
         vec3d6 = vec3d4;
      }

      if(vec3d5 != null && (vec3d6 == null || p_a_3_.distanceSquared(vec3d5) < p_a_3_.distanceSquared(vec3d6))) {
         vec3d6 = vec3d5;
      }

      if(vec3d6 == null) {
         return null;
      } else {
         EnumDirection enumdirection = null;
         if(vec3d6 == vec3d) {
            enumdirection = EnumDirection.WEST;
         }

         if(vec3d6 == vec3d1) {
            enumdirection = EnumDirection.EAST;
         }

         if(vec3d6 == vec3d2) {
            enumdirection = EnumDirection.DOWN;
         }

         if(vec3d6 == vec3d3) {
            enumdirection = EnumDirection.UP;
         }

         if(vec3d6 == vec3d4) {
            enumdirection = EnumDirection.NORTH;
         }

         if(vec3d6 == vec3d5) {
            enumdirection = EnumDirection.SOUTH;
         }

         return new MovingObjectPosition(vec3d6.add((double)p_a_2_.getX(), (double)p_a_2_.getY(), (double)p_a_2_.getZ()), enumdirection, p_a_2_);
      }
   }

   private boolean a(Vec3D p_a_1_) {
      return p_a_1_ == null?false:p_a_1_.b >= this.minY && p_a_1_.b <= this.maxY && p_a_1_.c >= this.minZ && p_a_1_.c <= this.maxZ;
   }

   private boolean b(Vec3D p_b_1_) {
      return p_b_1_ == null?false:p_b_1_.a >= this.minX && p_b_1_.a <= this.maxX && p_b_1_.c >= this.minZ && p_b_1_.c <= this.maxZ;
   }

   private boolean c(Vec3D p_c_1_) {
      return p_c_1_ == null?false:p_c_1_.a >= this.minX && p_c_1_.a <= this.maxX && p_c_1_.b >= this.minY && p_c_1_.b <= this.maxY;
   }

   public void wasExploded(World p_wasExploded_1_, BlockPosition p_wasExploded_2_, Explosion p_wasExploded_3_) {
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_, ItemStack p_canPlace_4_) {
      return this.canPlace(p_canPlace_1_, p_canPlace_2_, p_canPlace_3_);
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_) {
      return this.canPlace(p_canPlace_1_, p_canPlace_2_);
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      return p_canPlace_1_.getType(p_canPlace_2_).getBlock().material.isReplaceable();
   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      return false;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, Entity p_a_3_) {
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return this.fromLegacyData(p_getPlacedState_7_);
   }

   public void attack(World p_attack_1_, BlockPosition p_attack_2_, EntityHuman p_attack_3_) {
   }

   public Vec3D a(World p_a_1_, BlockPosition p_a_2_, Entity p_a_3_, Vec3D p_a_4_) {
      return p_a_4_;
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
   }

   public final double B() {
      return this.minX;
   }

   public final double C() {
      return this.maxX;
   }

   public final double D() {
      return this.minY;
   }

   public final double E() {
      return this.maxY;
   }

   public final double F() {
      return this.minZ;
   }

   public final double G() {
      return this.maxZ;
   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return 0;
   }

   public boolean isPowerSource() {
      return false;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_) {
   }

   public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_) {
      return 0;
   }

   public void j() {
   }

   public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_) {
      p_a_2_.b(StatisticList.MINE_BLOCK_COUNT[getId(this)]);
      p_a_2_.applyExhaustion(0.025F);
      if(this.I() && EnchantmentManager.hasSilkTouchEnchantment(p_a_2_)) {
         ItemStack itemstack = this.i(p_a_4_);
         if(itemstack != null) {
            a(p_a_1_, p_a_3_, itemstack);
         }
      } else {
         int i = EnchantmentManager.getBonusBlockLootEnchantmentLevel(p_a_2_);
         this.b(p_a_1_, p_a_3_, p_a_4_, i);
      }

   }

   protected boolean I() {
      return this.d() && !this.isTileEntity;
   }

   protected ItemStack i(IBlockData p_i_1_) {
      int i = 0;
      Item item = Item.getItemOf(this);
      if(item != null && item.k()) {
         i = this.toLegacyData(p_i_1_);
      }

      return new ItemStack(item, 1, i);
   }

   public int getDropCount(int p_getDropCount_1_, Random p_getDropCount_2_) {
      return this.a(p_getDropCount_2_);
   }

   public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_) {
   }

   public boolean g() {
      return !this.material.isBuildable() && !this.material.isLiquid();
   }

   public Block c(String p_c_1_) {
      this.name = p_c_1_;
      return this;
   }

   public String getName() {
      return LocaleI18n.get(this.a() + ".name");
   }

   public String a() {
      return "tile." + this.name;
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_, int p_a_5_) {
      return false;
   }

   public boolean J() {
      return this.y;
   }

   protected Block K() {
      this.y = false;
      return this;
   }

   public int k() {
      return this.material.getPushReaction();
   }

   public void fallOn(World p_fallOn_1_, BlockPosition p_fallOn_2_, Entity p_fallOn_3_, float p_fallOn_4_) {
      p_fallOn_3_.e(p_fallOn_4_, 1.0F);
   }

   public void a(World p_a_1_, Entity p_a_2_) {
      p_a_2_.motY = 0.0D;
   }

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      return this.getDropData(p_getDropData_1_.getType(p_getDropData_2_));
   }

   public Block a(CreativeModeTab p_a_1_) {
      this.creativeTab = p_a_1_;
      return this;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_) {
   }

   public void k(World p_k_1_, BlockPosition p_k_2_) {
   }

   public boolean N() {
      return true;
   }

   public boolean a(Explosion p_a_1_) {
      return true;
   }

   public boolean b(Block p_b_1_) {
      return this == p_b_1_;
   }

   public static boolean a(Block p_a_0_, Block p_a_1_) {
      return p_a_0_ != null && p_a_1_ != null?(p_a_0_ == p_a_1_?true:p_a_0_.b(p_a_1_)):false;
   }

   public boolean isComplexRedstone() {
      return false;
   }

   public int l(World p_l_1_, BlockPosition p_l_2_) {
      return 0;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[0]);
   }

   public BlockStateList P() {
      return this.blockStateList;
   }

   protected final void j(IBlockData p_j_1_) {
      this.blockData = p_j_1_;
   }

   public final IBlockData getBlockData() {
      return this.blockData;
   }

   public String toString() {
      return "Block{" + REGISTRY.c(this) + "}";
   }

   public static void S() {
      a(0, (MinecraftKey)a, (new BlockAir()).c("air"));
      a(1, (String)"stone", (new BlockStone()).c(1.5F).b(10.0F).a(i).c("stone"));
      a(2, (String)"grass", (new BlockGrass()).c(0.6F).a(h).c("grass"));
      a(3, (String)"dirt", (new BlockDirt()).c(0.5F).a(g).c("dirt"));
      Block block = (new Block(Material.STONE)).c(2.0F).b(10.0F).a(i).c("stonebrick").a(CreativeModeTab.b);
      a(4, (String)"cobblestone", block);
      Block block1 = (new BlockWood()).c(2.0F).b(5.0F).a(f).c("wood");
      a(5, (String)"planks", block1);
      a(6, (String)"sapling", (new BlockSapling()).c(0.0F).a(h).c("sapling"));
      a(7, (String)"bedrock", (new Block(Material.STONE)).x().b(6000000.0F).a(i).c("bedrock").K().a(CreativeModeTab.b));
      a(8, (String)"flowing_water", (new BlockFlowing(Material.WATER)).c(100.0F).e(3).c("water").K());
      a(9, (String)"water", (new BlockStationary(Material.WATER)).c(100.0F).e(3).c("water").K());
      a(10, (String)"flowing_lava", (new BlockFlowing(Material.LAVA)).c(100.0F).a(1.0F).c("lava").K());
      a(11, (String)"lava", (new BlockStationary(Material.LAVA)).c(100.0F).a(1.0F).c("lava").K());
      a(12, (String)"sand", (new BlockSand()).c(0.5F).a(m).c("sand"));
      a(13, (String)"gravel", (new BlockGravel()).c(0.6F).a(g).c("gravel"));
      a(14, (String)"gold_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreGold"));
      a(15, (String)"iron_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreIron"));
      a(16, (String)"coal_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreCoal"));
      a(17, (String)"log", (new BlockLog1()).c("log"));
      a(18, (String)"leaves", (new BlockLeaves1()).c("leaves"));
      a(19, (String)"sponge", (new BlockSponge()).c(0.6F).a(h).c("sponge"));
      a(20, (String)"glass", (new BlockGlass(Material.SHATTERABLE, false)).c(0.3F).a(k).c("glass"));
      a(21, (String)"lapis_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreLapis"));
      a(22, (String)"lapis_block", (new Block(Material.ORE, MaterialMapColor.H)).c(3.0F).b(5.0F).a(i).c("blockLapis").a(CreativeModeTab.b));
      a(23, (String)"dispenser", (new BlockDispenser()).c(3.5F).a(i).c("dispenser"));
      Block block2 = (new BlockSandStone()).a(i).c(0.8F).c("sandStone");
      a(24, (String)"sandstone", block2);
      a(25, (String)"noteblock", (new BlockNote()).c(0.8F).c("musicBlock"));
      a(26, (String)"bed", (new BlockBed()).a(f).c(0.2F).c("bed").K());
      a(27, (String)"golden_rail", (new BlockPoweredRail()).c(0.7F).a(j).c("goldenRail"));
      a(28, (String)"detector_rail", (new BlockMinecartDetector()).c(0.7F).a(j).c("detectorRail"));
      a(29, (String)"sticky_piston", (new BlockPiston(true)).c("pistonStickyBase"));
      a(30, (String)"web", (new BlockWeb()).e(1).c(4.0F).c("web"));
      a(31, (String)"tallgrass", (new BlockLongGrass()).c(0.0F).a(h).c("tallgrass"));
      a(32, (String)"deadbush", (new BlockDeadBush()).c(0.0F).a(h).c("deadbush"));
      a(33, (String)"piston", (new BlockPiston(false)).c("pistonBase"));
      a(34, (String)"piston_head", (new BlockPistonExtension()).c("pistonBase"));
      a(35, (String)"wool", (new BlockCloth(Material.CLOTH)).c(0.8F).a(l).c("cloth"));
      a(36, (String)"piston_extension", new BlockPistonMoving());
      a(37, (String)"yellow_flower", (new BlockYellowFlowers()).c(0.0F).a(h).c("flower1"));
      a(38, (String)"red_flower", (new BlockRedFlowers()).c(0.0F).a(h).c("flower2"));
      Block block3 = (new BlockMushroom()).c(0.0F).a(h).a(0.125F).c("mushroom");
      a(39, (String)"brown_mushroom", block3);
      Block block4 = (new BlockMushroom()).c(0.0F).a(h).c("mushroom");
      a(40, (String)"red_mushroom", block4);
      a(41, (String)"gold_block", (new Block(Material.ORE, MaterialMapColor.F)).c(3.0F).b(10.0F).a(j).c("blockGold").a(CreativeModeTab.b));
      a(42, (String)"iron_block", (new Block(Material.ORE, MaterialMapColor.h)).c(5.0F).b(10.0F).a(j).c("blockIron").a(CreativeModeTab.b));
      a(43, (String)"double_stone_slab", (new BlockDoubleStep()).c(2.0F).b(10.0F).a(i).c("stoneSlab"));
      a(44, (String)"stone_slab", (new BlockStep()).c(2.0F).b(10.0F).a(i).c("stoneSlab"));
      Block block5 = (new Block(Material.STONE, MaterialMapColor.D)).c(2.0F).b(10.0F).a(i).c("brick").a(CreativeModeTab.b);
      a(45, (String)"brick_block", block5);
      a(46, (String)"tnt", (new BlockTNT()).c(0.0F).a(h).c("tnt"));
      a(47, (String)"bookshelf", (new BlockBookshelf()).c(1.5F).a(f).c("bookshelf"));
      a(48, (String)"mossy_cobblestone", (new Block(Material.STONE)).c(2.0F).b(10.0F).a(i).c("stoneMoss").a(CreativeModeTab.b));
      a(49, (String)"obsidian", (new BlockObsidian()).c(50.0F).b(2000.0F).a(i).c("obsidian"));
      a(50, (String)"torch", (new BlockTorch()).c(0.0F).a(0.9375F).a(f).c("torch"));
      a(51, (String)"fire", (new BlockFire()).c(0.0F).a(1.0F).a(l).c("fire").K());
      a(52, (String)"mob_spawner", (new BlockMobSpawner()).c(5.0F).a(j).c("mobSpawner").K());
      a(53, (String)"oak_stairs", (new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.OAK))).c("stairsWood"));
      a(54, (String)"chest", (new BlockChest(0)).c(2.5F).a(f).c("chest"));
      a(55, (String)"redstone_wire", (new BlockRedstoneWire()).c(0.0F).a(e).c("redstoneDust").K());
      a(56, (String)"diamond_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreDiamond"));
      a(57, (String)"diamond_block", (new Block(Material.ORE, MaterialMapColor.G)).c(5.0F).b(10.0F).a(j).c("blockDiamond").a(CreativeModeTab.b));
      a(58, (String)"crafting_table", (new BlockWorkbench()).c(2.5F).a(f).c("workbench"));
      a(59, (String)"wheat", (new BlockCrops()).c("crops"));
      Block block6 = (new BlockSoil()).c(0.6F).a(g).c("farmland");
      a(60, (String)"farmland", block6);
      a(61, (String)"furnace", (new BlockFurnace(false)).c(3.5F).a(i).c("furnace").a(CreativeModeTab.c));
      a(62, (String)"lit_furnace", (new BlockFurnace(true)).c(3.5F).a(i).a(0.875F).c("furnace"));
      a(63, (String)"standing_sign", (new BlockFloorSign()).c(1.0F).a(f).c("sign").K());
      a(64, (String)"wooden_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(f).c("doorOak").K());
      a(65, (String)"ladder", (new BlockLadder()).c(0.4F).a(o).c("ladder"));
      a(66, (String)"rail", (new BlockMinecartTrack()).c(0.7F).a(j).c("rail"));
      a(67, (String)"stone_stairs", (new BlockStairs(block.getBlockData())).c("stairsStone"));
      a(68, (String)"wall_sign", (new BlockWallSign()).c(1.0F).a(f).c("sign").K());
      a(69, (String)"lever", (new BlockLever()).c(0.5F).a(f).c("lever"));
      a(70, (String)"stone_pressure_plate", (new BlockPressurePlateBinary(Material.STONE, BlockPressurePlateBinary.EnumMobType.MOBS)).c(0.5F).a(i).c("pressurePlateStone"));
      a(71, (String)"iron_door", (new BlockDoor(Material.ORE)).c(5.0F).a(j).c("doorIron").K());
      a(72, (String)"wooden_pressure_plate", (new BlockPressurePlateBinary(Material.WOOD, BlockPressurePlateBinary.EnumMobType.EVERYTHING)).c(0.5F).a(f).c("pressurePlateWood"));
      a(73, (String)"redstone_ore", (new BlockRedstoneOre(false)).c(3.0F).b(5.0F).a(i).c("oreRedstone").a(CreativeModeTab.b));
      a(74, (String)"lit_redstone_ore", (new BlockRedstoneOre(true)).a(0.625F).c(3.0F).b(5.0F).a(i).c("oreRedstone"));
      a(75, (String)"unlit_redstone_torch", (new BlockRedstoneTorch(false)).c(0.0F).a(f).c("notGate"));
      a(76, (String)"redstone_torch", (new BlockRedstoneTorch(true)).c(0.0F).a(0.5F).a(f).c("notGate").a(CreativeModeTab.d));
      a(77, (String)"stone_button", (new BlockStoneButton()).c(0.5F).a(i).c("button"));
      a(78, (String)"snow_layer", (new BlockSnow()).c(0.1F).a(n).c("snow").e(0));
      a(79, (String)"ice", (new BlockIce()).c(0.5F).e(3).a(k).c("ice"));
      a(80, (String)"snow", (new BlockSnowBlock()).c(0.2F).a(n).c("snow"));
      a(81, (String)"cactus", (new BlockCactus()).c(0.4F).a(l).c("cactus"));
      a(82, (String)"clay", (new BlockClay()).c(0.6F).a(g).c("clay"));
      a(83, (String)"reeds", (new BlockReed()).c(0.0F).a(h).c("reeds").K());
      a(84, (String)"jukebox", (new BlockJukeBox()).c(2.0F).b(10.0F).a(i).c("jukebox"));
      a(85, (String)"fence", (new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.OAK.c())).c(2.0F).b(5.0F).a(f).c("fence"));
      Block block7 = (new BlockPumpkin()).c(1.0F).a(f).c("pumpkin");
      a(86, (String)"pumpkin", block7);
      a(87, (String)"netherrack", (new BlockBloodStone()).c(0.4F).a(i).c("hellrock"));
      a(88, (String)"soul_sand", (new BlockSlowSand()).c(0.5F).a(m).c("hellsand"));
      a(89, (String)"glowstone", (new BlockLightStone(Material.SHATTERABLE)).c(0.3F).a(k).a(1.0F).c("lightgem"));
      a(90, (String)"portal", (new BlockPortal()).c(-1.0F).a(k).a(0.75F).c("portal"));
      a(91, (String)"lit_pumpkin", (new BlockPumpkin()).c(1.0F).a(f).a(1.0F).c("litpumpkin"));
      a(92, (String)"cake", (new BlockCake()).c(0.5F).a(l).c("cake").K());
      a(93, (String)"unpowered_repeater", (new BlockRepeater(false)).c(0.0F).a(f).c("diode").K());
      a(94, (String)"powered_repeater", (new BlockRepeater(true)).c(0.0F).a(f).c("diode").K());
      a(95, (String)"stained_glass", (new BlockStainedGlass(Material.SHATTERABLE)).c(0.3F).a(k).c("stainedGlass"));
      a(96, (String)"trapdoor", (new BlockTrapdoor(Material.WOOD)).c(3.0F).a(f).c("trapdoor").K());
      a(97, (String)"monster_egg", (new BlockMonsterEggs()).c(0.75F).c("monsterStoneEgg"));
      Block block8 = (new BlockSmoothBrick()).c(1.5F).b(10.0F).a(i).c("stonebricksmooth");
      a(98, (String)"stonebrick", block8);
      a(99, (String)"brown_mushroom_block", (new BlockHugeMushroom(Material.WOOD, MaterialMapColor.l, block3)).c(0.2F).a(f).c("mushroom"));
      a(100, (String)"red_mushroom_block", (new BlockHugeMushroom(Material.WOOD, MaterialMapColor.D, block4)).c(0.2F).a(f).c("mushroom"));
      a(101, (String)"iron_bars", (new BlockThin(Material.ORE, true)).c(5.0F).b(10.0F).a(j).c("fenceIron"));
      a(102, (String)"glass_pane", (new BlockThin(Material.SHATTERABLE, false)).c(0.3F).a(k).c("thinGlass"));
      Block block9 = (new BlockMelon()).c(1.0F).a(f).c("melon");
      a(103, (String)"melon_block", block9);
      a(104, (String)"pumpkin_stem", (new BlockStem(block7)).c(0.0F).a(f).c("pumpkinStem"));
      a(105, (String)"melon_stem", (new BlockStem(block9)).c(0.0F).a(f).c("pumpkinStem"));
      a(106, (String)"vine", (new BlockVine()).c(0.2F).a(h).c("vine"));
      a(107, (String)"fence_gate", (new BlockFenceGate(BlockWood.EnumLogVariant.OAK)).c(2.0F).b(5.0F).a(f).c("fenceGate"));
      a(108, (String)"brick_stairs", (new BlockStairs(block5.getBlockData())).c("stairsBrick"));
      a(109, (String)"stone_brick_stairs", (new BlockStairs(block8.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.DEFAULT))).c("stairsStoneBrickSmooth"));
      a(110, (String)"mycelium", (new BlockMycel()).c(0.6F).a(h).c("mycel"));
      a(111, (String)"waterlily", (new BlockWaterLily()).c(0.0F).a(h).c("waterlily"));
      Block block10 = (new BlockNetherbrick()).c(2.0F).b(10.0F).a(i).c("netherBrick").a(CreativeModeTab.b);
      a(112, (String)"nether_brick", block10);
      a(113, (String)"nether_brick_fence", (new BlockFence(Material.STONE, MaterialMapColor.K)).c(2.0F).b(10.0F).a(i).c("netherFence"));
      a(114, (String)"nether_brick_stairs", (new BlockStairs(block10.getBlockData())).c("stairsNetherBrick"));
      a(115, (String)"nether_wart", (new BlockNetherWart()).c("netherStalk"));
      a(116, (String)"enchanting_table", (new BlockEnchantmentTable()).c(5.0F).b(2000.0F).c("enchantmentTable"));
      a(117, (String)"brewing_stand", (new BlockBrewingStand()).c(0.5F).a(0.125F).c("brewingStand"));
      a(118, (String)"cauldron", (new BlockCauldron()).c(2.0F).c("cauldron"));
      a(119, (String)"end_portal", (new BlockEnderPortal(Material.PORTAL)).c(-1.0F).b(6000000.0F));
      a(120, (String)"end_portal_frame", (new BlockEnderPortalFrame()).a(k).a(0.125F).c(-1.0F).c("endPortalFrame").b(6000000.0F).a(CreativeModeTab.c));
      a(121, (String)"end_stone", (new Block(Material.STONE, MaterialMapColor.d)).c(3.0F).b(15.0F).a(i).c("whiteStone").a(CreativeModeTab.b));
      a(122, (String)"dragon_egg", (new BlockDragonEgg()).c(3.0F).b(15.0F).a(i).a(0.125F).c("dragonEgg"));
      a(123, (String)"redstone_lamp", (new BlockRedstoneLamp(false)).c(0.3F).a(k).c("redstoneLight").a(CreativeModeTab.d));
      a(124, (String)"lit_redstone_lamp", (new BlockRedstoneLamp(true)).c(0.3F).a(k).c("redstoneLight"));
      a(125, (String)"double_wooden_slab", (new BlockDoubleWoodStep()).c(2.0F).b(5.0F).a(f).c("woodSlab"));
      a(126, (String)"wooden_slab", (new BlockWoodStep()).c(2.0F).b(5.0F).a(f).c("woodSlab"));
      a(127, (String)"cocoa", (new BlockCocoa()).c(0.2F).b(5.0F).a(f).c("cocoa"));
      a(128, (String)"sandstone_stairs", (new BlockStairs(block2.getBlockData().set(BlockSandStone.TYPE, BlockSandStone.EnumSandstoneVariant.SMOOTH))).c("stairsSandStone"));
      a(129, (String)"emerald_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreEmerald"));
      a(130, (String)"ender_chest", (new BlockEnderChest()).c(22.5F).b(1000.0F).a(i).c("enderChest").a(0.5F));
      a(131, (String)"tripwire_hook", (new BlockTripwireHook()).c("tripWireSource"));
      a(132, (String)"tripwire", (new BlockTripwire()).c("tripWire"));
      a(133, (String)"emerald_block", (new Block(Material.ORE, MaterialMapColor.I)).c(5.0F).b(10.0F).a(j).c("blockEmerald").a(CreativeModeTab.b));
      a(134, (String)"spruce_stairs", (new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.SPRUCE))).c("stairsWoodSpruce"));
      a(135, (String)"birch_stairs", (new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.BIRCH))).c("stairsWoodBirch"));
      a(136, (String)"jungle_stairs", (new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.JUNGLE))).c("stairsWoodJungle"));
      a(137, (String)"command_block", (new BlockCommand()).x().b(6000000.0F).c("commandBlock"));
      a(138, (String)"beacon", (new BlockBeacon()).c("beacon").a(1.0F));
      a(139, (String)"cobblestone_wall", (new BlockCobbleWall(block)).c("cobbleWall"));
      a(140, (String)"flower_pot", (new BlockFlowerPot()).c(0.0F).a(e).c("flowerPot"));
      a(141, (String)"carrots", (new BlockCarrots()).c("carrots"));
      a(142, (String)"potatoes", (new BlockPotatoes()).c("potatoes"));
      a(143, (String)"wooden_button", (new BlockWoodButton()).c(0.5F).a(f).c("button"));
      a(144, (String)"skull", (new BlockSkull()).c(1.0F).a(i).c("skull"));
      a(145, (String)"anvil", (new BlockAnvil()).c(5.0F).a(p).b(2000.0F).c("anvil"));
      a(146, (String)"trapped_chest", (new BlockChest(1)).c(2.5F).a(f).c("chestTrap"));
      a(147, (String)"light_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.ORE, 15, MaterialMapColor.F)).c(0.5F).a(f).c("weightedPlate_light"));
      a(148, (String)"heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.ORE, 150)).c(0.5F).a(f).c("weightedPlate_heavy"));
      a(149, (String)"unpowered_comparator", (new BlockRedstoneComparator(false)).c(0.0F).a(f).c("comparator").K());
      a(150, (String)"powered_comparator", (new BlockRedstoneComparator(true)).c(0.0F).a(0.625F).a(f).c("comparator").K());
      a(151, (String)"daylight_detector", new BlockDaylightDetector(false));
      a(152, (String)"redstone_block", (new BlockPowered(Material.ORE, MaterialMapColor.f)).c(5.0F).b(10.0F).a(j).c("blockRedstone").a(CreativeModeTab.d));
      a(153, (String)"quartz_ore", (new BlockOre(MaterialMapColor.K)).c(3.0F).b(5.0F).a(i).c("netherquartz"));
      a(154, (String)"hopper", (new BlockHopper()).c(3.0F).b(8.0F).a(j).c("hopper"));
      Block block11 = (new BlockQuartz()).a(i).c(0.8F).c("quartzBlock");
      a(155, (String)"quartz_block", block11);
      a(156, (String)"quartz_stairs", (new BlockStairs(block11.getBlockData().set(BlockQuartz.VARIANT, BlockQuartz.EnumQuartzVariant.DEFAULT))).c("stairsQuartz"));
      a(157, (String)"activator_rail", (new BlockPoweredRail()).c(0.7F).a(j).c("activatorRail"));
      a(158, (String)"dropper", (new BlockDropper()).c(3.5F).a(i).c("dropper"));
      a(159, (String)"stained_hardened_clay", (new BlockCloth(Material.STONE)).c(1.25F).b(7.0F).a(i).c("clayHardenedStained"));
      a(160, (String)"stained_glass_pane", (new BlockStainedGlassPane()).c(0.3F).a(k).c("thinStainedGlass"));
      a(161, (String)"leaves2", (new BlockLeaves2()).c("leaves"));
      a(162, (String)"log2", (new BlockLog2()).c("log"));
      a(163, (String)"acacia_stairs", (new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.ACACIA))).c("stairsWoodAcacia"));
      a(164, (String)"dark_oak_stairs", (new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.DARK_OAK))).c("stairsWoodDarkOak"));
      a(165, (String)"slime", (new BlockSlime()).c("slime").a(q));
      a(166, (String)"barrier", (new BlockBarrier()).c("barrier"));
      a(167, (String)"iron_trapdoor", (new BlockTrapdoor(Material.ORE)).c(5.0F).a(j).c("ironTrapdoor").K());
      a(168, (String)"prismarine", (new BlockPrismarine()).c(1.5F).b(10.0F).a(i).c("prismarine"));
      a(169, (String)"sea_lantern", (new BlockSeaLantern(Material.SHATTERABLE)).c(0.3F).a(k).a(1.0F).c("seaLantern"));
      a(170, (String)"hay_block", (new BlockHay()).c(0.5F).a(h).c("hayBlock").a(CreativeModeTab.b));
      a(171, (String)"carpet", (new BlockCarpet()).c(0.1F).a(l).c("woolCarpet").e(0));
      a(172, (String)"hardened_clay", (new BlockHardenedClay()).c(1.25F).b(7.0F).a(i).c("clayHardened"));
      a(173, (String)"coal_block", (new Block(Material.STONE, MaterialMapColor.E)).c(5.0F).b(10.0F).a(i).c("blockCoal").a(CreativeModeTab.b));
      a(174, (String)"packed_ice", (new BlockPackedIce()).c(0.5F).a(k).c("icePacked"));
      a(175, (String)"double_plant", new BlockTallPlant());
      a(176, (String)"standing_banner", (new BlockBanner.BlockStandingBanner()).c(1.0F).a(f).c("banner").K());
      a(177, (String)"wall_banner", (new BlockBanner.BlockWallBanner()).c(1.0F).a(f).c("banner").K());
      a(178, (String)"daylight_detector_inverted", new BlockDaylightDetector(true));
      Block block12 = (new BlockRedSandstone()).a(i).c(0.8F).c("redSandStone");
      a(179, (String)"red_sandstone", block12);
      a(180, (String)"red_sandstone_stairs", (new BlockStairs(block12.getBlockData().set(BlockRedSandstone.TYPE, BlockRedSandstone.EnumRedSandstoneVariant.SMOOTH))).c("stairsRedSandStone"));
      a(181, (String)"double_stone_slab2", (new BlockDoubleStoneStep2()).c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
      a(182, (String)"stone_slab2", (new BlockStoneStep2()).c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
      a(183, (String)"spruce_fence_gate", (new BlockFenceGate(BlockWood.EnumLogVariant.SPRUCE)).c(2.0F).b(5.0F).a(f).c("spruceFenceGate"));
      a(184, (String)"birch_fence_gate", (new BlockFenceGate(BlockWood.EnumLogVariant.BIRCH)).c(2.0F).b(5.0F).a(f).c("birchFenceGate"));
      a(185, (String)"jungle_fence_gate", (new BlockFenceGate(BlockWood.EnumLogVariant.JUNGLE)).c(2.0F).b(5.0F).a(f).c("jungleFenceGate"));
      a(186, (String)"dark_oak_fence_gate", (new BlockFenceGate(BlockWood.EnumLogVariant.DARK_OAK)).c(2.0F).b(5.0F).a(f).c("darkOakFenceGate"));
      a(187, (String)"acacia_fence_gate", (new BlockFenceGate(BlockWood.EnumLogVariant.ACACIA)).c(2.0F).b(5.0F).a(f).c("acaciaFenceGate"));
      a(188, (String)"spruce_fence", (new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.SPRUCE.c())).c(2.0F).b(5.0F).a(f).c("spruceFence"));
      a(189, (String)"birch_fence", (new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.BIRCH.c())).c(2.0F).b(5.0F).a(f).c("birchFence"));
      a(190, (String)"jungle_fence", (new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.JUNGLE.c())).c(2.0F).b(5.0F).a(f).c("jungleFence"));
      a(191, (String)"dark_oak_fence", (new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.DARK_OAK.c())).c(2.0F).b(5.0F).a(f).c("darkOakFence"));
      a(192, (String)"acacia_fence", (new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.ACACIA.c())).c(2.0F).b(5.0F).a(f).c("acaciaFence"));
      a(193, (String)"spruce_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(f).c("doorSpruce").K());
      a(194, (String)"birch_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(f).c("doorBirch").K());
      a(195, (String)"jungle_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(f).c("doorJungle").K());
      a(196, (String)"acacia_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(f).c("doorAcacia").K());
      a(197, (String)"dark_oak_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(f).c("doorDarkOak").K());
      REGISTRY.a();

      for(Block block13 : REGISTRY) {
         if(block13.material == Material.AIR) {
            block13.v = false;
         } else {
            boolean flag = false;
            boolean flag1 = block13 instanceof BlockStairs;
            boolean flag2 = block13 instanceof BlockStepAbstract;
            boolean flag3 = block13 == block6;
            boolean flag4 = block13.t;
            boolean flag5 = block13.s == 0;
            if(flag1 || flag2 || flag3 || flag4 || flag5) {
               flag = true;
            }

            block13.v = flag;
         }
      }

      for(Block block14 : REGISTRY) {
         Iterator iterator = block14.P().a().iterator();

         while(((Iterator)iterator).hasNext()) {
            IBlockData iblockdata = (IBlockData)iterator.next();
            int i = REGISTRY.b(block14) << 4 | block14.toLegacyData(iblockdata);
            d.a(iblockdata, i);
         }
      }

   }

   private static void a(int p_a_0_, MinecraftKey p_a_1_, Block p_a_2_) {
      REGISTRY.a(p_a_0_, p_a_1_, p_a_2_);
   }

   private static void a(int p_a_0_, String p_a_1_, Block p_a_2_) {
      a(p_a_0_, new MinecraftKey(p_a_1_), p_a_2_);
   }

   public int getExpDrop(World p_getExpDrop_1_, IBlockData p_getExpDrop_2_, int p_getExpDrop_3_) {
      return 0;
   }

   public static float range(float p_range_0_, float p_range_1_, float p_range_2_) {
      return p_range_1_ < p_range_0_?p_range_0_:(p_range_1_ > p_range_2_?p_range_2_:p_range_1_);
   }

   public static class StepSound {
      public final String a;
      public final float b;
      public final float c;

      public StepSound(String p_i450_1_, float p_i450_2_, float p_i450_3_) {
         this.a = p_i450_1_;
         this.b = p_i450_2_;
         this.c = p_i450_3_;
      }

      public float getVolume1() {
         return this.b;
      }

      public float getVolume2() {
         return this.c;
      }

      public String getBreakSound() {
         return "dig." + this.a;
      }

      public String getStepSound() {
         return "step." + this.a;
      }

      public String getPlaceSound() {
         return this.getBreakSound();
      }
   }
}

package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import org.bukkit.craftbukkit.v1_8_R3.util.BlockStateListPopulator;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockPumpkin extends BlockDirectional
{
    private ShapeDetector snowGolemPart;
    private ShapeDetector snowGolem;
    private ShapeDetector ironGolemPart;
    private ShapeDetector ironGolem;
    private static final Predicate<IBlockData> Q = new Predicate()
    {
        public boolean a(IBlockData p_a_1_)
        {
            return p_a_1_ != null && (p_a_1_.getBlock() == Blocks.PUMPKIN || p_a_1_.getBlock() == Blocks.LIT_PUMPKIN);
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.a((IBlockData)p_apply_1_);
        }
    };

    protected BlockPumpkin()
    {
        super(Material.PUMPKIN, MaterialMapColor.q);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
        this.a(true);
        this.a(CreativeModeTab.b);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        super.onPlace(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
        this.f(p_onPlace_1_, p_onPlace_2_);
    }

    public boolean e(World p_e_1_, BlockPosition p_e_2_)
    {
        return this.getDetectorSnowGolemPart().a(p_e_1_, p_e_2_) != null || this.getDetectorIronGolemPart().a(p_e_1_, p_e_2_) != null;
    }

    private void f(World p_f_1_, BlockPosition p_f_2_)
    {
        ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection;

        if ((shapedetector$shapedetectorcollection = this.getDetectorSnowGolem().a(p_f_1_, p_f_2_)) != null)
        {
            BlockStateListPopulator blockstatelistpopulator = new BlockStateListPopulator(p_f_1_.getWorld());

            for (int i = 0; i < this.getDetectorSnowGolem().b(); ++i)
            {
                ShapeDetectorBlock shapedetectorblock = shapedetector$shapedetectorcollection.a(0, i, 0);
                BlockPosition blockposition = shapedetectorblock.getPosition();
                blockstatelistpopulator.setTypeId(blockposition.getX(), blockposition.getY(), blockposition.getZ(), 0);
            }

            EntitySnowman entitysnowman = new EntitySnowman(p_f_1_);
            BlockPosition blockposition2 = shapedetector$shapedetectorcollection.a(0, 2, 0).getPosition();
            entitysnowman.setPositionRotation((double)blockposition2.getX() + 0.5D, (double)blockposition2.getY() + 0.05D, (double)blockposition2.getZ() + 0.5D, 0.0F, 0.0F);

            if (p_f_1_.addEntity(entitysnowman, SpawnReason.BUILD_SNOWMAN))
            {
                blockstatelistpopulator.updateList();

                for (int j = 0; j < 120; ++j)
                {
                    p_f_1_.addParticle(EnumParticle.SNOW_SHOVEL, (double)blockposition2.getX() + p_f_1_.random.nextDouble(), (double)blockposition2.getY() + p_f_1_.random.nextDouble() * 2.5D, (double)blockposition2.getZ() + p_f_1_.random.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
                }

                for (int j = 0; j < this.getDetectorSnowGolem().b(); ++j)
                {
                    ShapeDetectorBlock shapedetectorblock1 = shapedetector$shapedetectorcollection.a(0, j, 0);
                    p_f_1_.update(shapedetectorblock1.getPosition(), Blocks.AIR);
                }
            }
        }
        else if ((shapedetector$shapedetectorcollection = this.getDetectorIronGolem().a(p_f_1_, p_f_2_)) != null)
        {
            BlockStateListPopulator blockstatelistpopulator1 = new BlockStateListPopulator(p_f_1_.getWorld());

            for (int k = 0; k < this.getDetectorIronGolem().c(); ++k)
            {
                for (int l = 0; l < this.getDetectorIronGolem().b(); ++l)
                {
                    BlockPosition blockposition3 = shapedetector$shapedetectorcollection.a(k, l, 0).getPosition();
                    blockstatelistpopulator1.setTypeId(blockposition3.getX(), blockposition3.getY(), blockposition3.getZ(), 0);
                }
            }

            BlockPosition blockposition1 = shapedetector$shapedetectorcollection.a(1, 2, 0).getPosition();
            EntityIronGolem entityirongolem = new EntityIronGolem(p_f_1_);
            entityirongolem.setPlayerCreated(true);
            entityirongolem.setPositionRotation((double)blockposition1.getX() + 0.5D, (double)blockposition1.getY() + 0.05D, (double)blockposition1.getZ() + 0.5D, 0.0F, 0.0F);

            if (p_f_1_.addEntity(entityirongolem, SpawnReason.BUILD_IRONGOLEM))
            {
                blockstatelistpopulator1.updateList();

                for (int j1 = 0; j1 < 120; ++j1)
                {
                    p_f_1_.addParticle(EnumParticle.SNOWBALL, (double)blockposition1.getX() + p_f_1_.random.nextDouble(), (double)blockposition1.getY() + p_f_1_.random.nextDouble() * 3.9D, (double)blockposition1.getZ() + p_f_1_.random.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
                }

                for (int k1 = 0; k1 < this.getDetectorIronGolem().c(); ++k1)
                {
                    for (int l1 = 0; l1 < this.getDetectorIronGolem().b(); ++l1)
                    {
                        ShapeDetectorBlock shapedetectorblock2 = shapedetector$shapedetectorcollection.a(k1, l1, 0);
                        p_f_1_.update(shapedetectorblock2.getPosition(), Blocks.AIR);
                    }
                }
            }
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return p_canPlace_1_.getType(p_canPlace_2_).getBlock().material.isReplaceable() && World.a((IBlockAccess)p_canPlace_1_, (BlockPosition)p_canPlace_2_.down());
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection().opposite());
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING});
    }

    protected ShapeDetector getDetectorSnowGolemPart()
    {
        if (this.snowGolemPart == null)
        {
            this.snowGolemPart = ShapeDetectorBuilder.a().a(new String[] {" ", "#", "#"}).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SNOW))).b();
        }

        return this.snowGolemPart;
    }

    protected ShapeDetector getDetectorSnowGolem()
    {
        if (this.snowGolem == null)
        {
            this.snowGolem = ShapeDetectorBuilder.a().a(new String[] {"^", "#", "#"}).a('^', ShapeDetectorBlock.a(Q)).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SNOW))).b();
        }

        return this.snowGolem;
    }

    protected ShapeDetector getDetectorIronGolemPart()
    {
        if (this.ironGolemPart == null)
        {
            this.ironGolemPart = ShapeDetectorBuilder.a().a(new String[] {"~ ~", "###", "~#~"}).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.IRON_BLOCK))).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
        }

        return this.ironGolemPart;
    }

    protected ShapeDetector getDetectorIronGolem()
    {
        if (this.ironGolem == null)
        {
            this.ironGolem = ShapeDetectorBuilder.a().a(new String[] {"~^~", "###", "~#~"}).a('^', ShapeDetectorBlock.a(Q)).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.IRON_BLOCK))).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
        }

        return this.ironGolem;
    }
}

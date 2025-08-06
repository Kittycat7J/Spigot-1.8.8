package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BlockCake extends Block
{
    public static final BlockStateInteger BITES = BlockStateInteger.of("bites", 0, 6);

    protected BlockCake()
    {
        super(Material.CAKE);
        this.j(this.blockStateList.getBlockData().set(BITES, Integer.valueOf(0)));
        this.a(true);
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        float f = 0.0625F;
        float f1 = (float)(1 + ((Integer)p_updateShape_1_.getType(p_updateShape_2_).get(BITES)).intValue() * 2) / 16.0F;
        float f2 = 0.5F;
        this.a(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
    }

    public void j()
    {
        float f = 0.0625F;
        float f1 = 0.5F;
        this.a(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        float f = 0.0625F;
        float f1 = (float)(1 + ((Integer)p_a_3_.get(BITES)).intValue() * 2) / 16.0F;
        float f2 = 0.5F;
        return new AxisAlignedBB((double)((float)p_a_2_.getX() + f1), (double)p_a_2_.getY(), (double)((float)p_a_2_.getZ() + f), (double)((float)(p_a_2_.getX() + 1) - f), (double)((float)p_a_2_.getY() + f2), (double)((float)(p_a_2_.getZ() + 1) - f));
    }

    public boolean d()
    {
        return false;
    }

    public boolean c()
    {
        return false;
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        this.b(p_interact_1_, p_interact_2_, p_interact_3_, p_interact_4_);
        return true;
    }

    public void attack(World p_attack_1_, BlockPosition p_attack_2_, EntityHuman p_attack_3_)
    {
        this.b(p_attack_1_, p_attack_2_, p_attack_1_.getType(p_attack_2_), p_attack_3_);
    }

    private void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EntityHuman p_b_4_)
    {
        if (p_b_4_.j(false))
        {
            p_b_4_.b(StatisticList.H);
            int i = p_b_4_.getFoodData().foodLevel;
            FoodLevelChangeEvent foodlevelchangeevent = CraftEventFactory.callFoodLevelChangeEvent(p_b_4_, 2 + i);

            if (!foodlevelchangeevent.isCancelled())
            {
                p_b_4_.getFoodData().eat(foodlevelchangeevent.getFoodLevel() - i, 0.1F);
            }

            ((EntityPlayer)p_b_4_).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)p_b_4_).getBukkitEntity().getScaledHealth(), p_b_4_.getFoodData().foodLevel, p_b_4_.getFoodData().saturationLevel));
            int j = ((Integer)p_b_3_.get(BITES)).intValue();

            if (j < 6)
            {
                p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(BITES, Integer.valueOf(j + 1)), 3);
            }
            else
            {
                p_b_1_.setAir(p_b_2_);
            }
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return super.canPlace(p_canPlace_1_, p_canPlace_2_) ? this.e(p_canPlace_1_, p_canPlace_2_) : false;
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!this.e(p_doPhysics_1_, p_doPhysics_2_))
        {
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }
    }

    private boolean e(World p_e_1_, BlockPosition p_e_2_)
    {
        return p_e_1_.getType(p_e_2_.down()).getBlock().getMaterial().isBuildable();
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return null;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(BITES, Integer.valueOf(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Integer)p_toLegacyData_1_.get(BITES)).intValue();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {BITES});
    }

    public int l(World p_l_1_, BlockPosition p_l_2_)
    {
        return (7 - ((Integer)p_l_1_.getType(p_l_2_).get(BITES)).intValue()) * 2;
    }

    public boolean isComplexRedstone()
    {
        return true;
    }
}

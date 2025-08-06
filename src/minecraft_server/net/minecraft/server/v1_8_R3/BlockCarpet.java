package net.minecraft.server.v1_8_R3;

public class BlockCarpet extends Block
{
    public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.<EnumColor>of("color", EnumColor.class);

    protected BlockCarpet()
    {
        super(Material.WOOL);
        this.j(this.blockStateList.getBlockData().set(COLOR, EnumColor.WHITE));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        this.a(true);
        this.a(CreativeModeTab.c);
        this.b(0);
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return ((EnumColor)p_g_1_.get(COLOR)).e();
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public void j()
    {
        this.b(0);
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        this.b(0);
    }

    protected void b(int p_b_1_)
    {
        byte b0 = 0;
        float f = (float)(1 * (1 + b0)) / 16.0F;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return super.canPlace(p_canPlace_1_, p_canPlace_2_) && this.e(p_canPlace_1_, p_canPlace_2_);
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
    }

    private boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!this.e(p_e_1_, p_e_2_))
        {
            this.b(p_e_1_, p_e_2_, p_e_3_, 0);
            p_e_1_.setAir(p_e_2_);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean e(World p_e_1_, BlockPosition p_e_2_)
    {
        return !p_e_1_.isEmpty(p_e_2_.down());
    }

    public int getDropData(IBlockData p_getDropData_1_)
    {
        return ((EnumColor)p_getDropData_1_.get(COLOR)).getColorIndex();
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(COLOR, EnumColor.fromColorIndex(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((EnumColor)p_toLegacyData_1_.get(COLOR)).getColorIndex();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {COLOR});
    }
}

package net.minecraft.server.v1_8_R3;

public class BlockTransparent extends Block
{
    protected boolean R;

    protected BlockTransparent(Material p_i654_1_, boolean p_i654_2_)
    {
        super(p_i654_1_);
        this.R = p_i654_2_;
    }

    public boolean c()
    {
        return false;
    }
}

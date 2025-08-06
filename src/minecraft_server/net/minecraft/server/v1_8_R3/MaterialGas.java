package net.minecraft.server.v1_8_R3;

public class MaterialGas extends Material
{
    public MaterialGas(MaterialMapColor p_i803_1_)
    {
        super(p_i803_1_);
        this.i();
    }

    public boolean isBuildable()
    {
        return false;
    }

    public boolean blocksLight()
    {
        return false;
    }

    public boolean isSolid()
    {
        return false;
    }
}

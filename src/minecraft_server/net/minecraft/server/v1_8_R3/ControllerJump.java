package net.minecraft.server.v1_8_R3;

public class ControllerJump
{
    private EntityInsentient b;
    protected boolean a;

    public ControllerJump(EntityInsentient p_i1155_1_)
    {
        this.b = p_i1155_1_;
    }

    public void a()
    {
        this.a = true;
    }

    public void b()
    {
        this.b.i(this.a);
        this.a = false;
    }
}

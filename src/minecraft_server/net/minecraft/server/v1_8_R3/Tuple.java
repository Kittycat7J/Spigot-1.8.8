package net.minecraft.server.v1_8_R3;

public class Tuple<A, B>
{
    private A a;
    private B b;

    public Tuple(A p_i1111_1_, B p_i1111_2_)
    {
        this.a = p_i1111_1_;
        this.b = p_i1111_2_;
    }

    public A a()
    {
        return this.a;
    }

    public B b()
    {
        return this.b;
    }
}

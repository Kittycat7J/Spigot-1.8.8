package net.minecraft.server.v1_8_R3;

public class CommandException extends Exception
{
    private final Object[] a;

    public CommandException(String p_i863_1_, Object... p_i863_2_)
    {
        super(p_i863_1_);
        this.a = p_i863_2_;
    }

    public Object[] getArgs()
    {
        return this.a;
    }
}

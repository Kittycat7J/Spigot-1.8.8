package net.minecraft.server.v1_8_R3;

public class ExceptionInvalidNumber extends CommandException
{
    public ExceptionInvalidNumber()
    {
        this("commands.generic.num.invalid", new Object[0]);
    }

    public ExceptionInvalidNumber(String p_i872_1_, Object... p_i872_2_)
    {
        super(p_i872_1_, p_i872_2_);
    }
}

package net.minecraft.server.v1_8_R3;

public class ServerCommand
{
    public final String command;
    public final ICommandListener source;

    public ServerCommand(String p_i1058_1_, ICommandListener p_i1058_2_)
    {
        this.command = p_i1058_1_;
        this.source = p_i1058_2_;
    }
}

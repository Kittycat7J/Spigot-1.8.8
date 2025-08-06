package net.minecraft.server.v1_8_R3;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

class ServerConnection$7 implements GenericFutureListener < Future <? super Void >>
{
    ServerConnection$7(ServerConnection p_i1077_1_, NetworkManager p_i1077_2_, ChatComponentText p_i1077_3_)
    {
        this.c = p_i1077_1_;
        this.a = p_i1077_2_;
        this.b = p_i1077_3_;
    }

    public void operationComplete(Future <? super Void > p_operationComplete_1_) throws Exception
    {
        this.a.close(this.b);
    }
}

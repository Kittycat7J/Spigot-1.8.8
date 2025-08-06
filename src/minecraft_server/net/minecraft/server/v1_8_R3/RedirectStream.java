package net.minecraft.server.v1_8_R3;

import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedirectStream extends PrintStream
{
    private static final Logger a = LogManager.getLogger();
    private final String b;

    public RedirectStream(String p_i1060_1_, OutputStream p_i1060_2_)
    {
        super(p_i1060_2_);
        this.b = p_i1060_1_;
    }

    public void println(String p_println_1_)
    {
        this.a(p_println_1_);
    }

    public void println(Object p_println_1_)
    {
        this.a(String.valueOf(p_println_1_));
    }

    private void a(String p_a_1_)
    {
        StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
        StackTraceElement stacktraceelement = astacktraceelement[Math.min(3, astacktraceelement.length)];
        a.info("[{}]@.({}:{}): {}", new Object[] {this.b, stacktraceelement.getFileName(), Integer.valueOf(stacktraceelement.getLineNumber()), p_a_1_});
    }
}

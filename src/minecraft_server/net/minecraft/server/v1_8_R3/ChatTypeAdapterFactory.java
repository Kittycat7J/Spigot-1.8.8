package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class ChatTypeAdapterFactory implements TypeAdapterFactory
{
    public <T> TypeAdapter<T> create(Gson p_create_1_, TypeToken<T> p_create_2_)
    {
        Class oclass = p_create_2_.getRawType();

        if (!oclass.isEnum())
        {
            return null;
        }
        else
        {
            final HashMap hashmap = Maps.newHashMap();

            for (Object object : oclass.getEnumConstants())
            {
                hashmap.put(this.a(object), object);
            }

            return new TypeAdapter<T>()
            {
                public void write(JsonWriter p_write_1_, T p_write_2_) throws IOException
                {
                    if (p_write_2_ == null)
                    {
                        p_write_1_.nullValue();
                    }
                    else
                    {
                        p_write_1_.value(ChatTypeAdapterFactory.this.a(p_write_2_));
                    }
                }
                public T read(JsonReader p_read_1_) throws IOException
                {
                    if (p_read_1_.peek() == JsonToken.NULL)
                    {
                        p_read_1_.nextNull();
                        return (T)null;
                    }
                    else
                    {
                        return (T)hashmap.get(p_read_1_.nextString());
                    }
                }
            };
        }
    }

    private String a(Object p_a_1_)
    {
        return p_a_1_ instanceof Enum ? ((Enum)p_a_1_).name().toLowerCase(Locale.US) : p_a_1_.toString().toLowerCase(Locale.US);
    }
}

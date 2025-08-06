package net.minecraft.server.v1_8_R3;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtilities {
   public static final ListeningExecutorService a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));
   private static final AtomicInteger b = new AtomicInteger(0);
   private static final Logger c = LogManager.getLogger();

   public static String a(Map<String, Object> p_a_0_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(Entry entry : p_a_0_.entrySet()) {
         if(stringbuilder.length() > 0) {
            stringbuilder.append('&');
         }

         try {
            stringbuilder.append(URLEncoder.encode((String)entry.getKey(), "UTF-8"));
         } catch (UnsupportedEncodingException unsupportedencodingexception1) {
            unsupportedencodingexception1.printStackTrace();
         }

         if(entry.getValue() != null) {
            stringbuilder.append('=');

            try {
               stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException unsupportedencodingexception) {
               unsupportedencodingexception.printStackTrace();
            }
         }
      }

      return stringbuilder.toString();
   }

   public static String a(URL p_a_0_, Map<String, Object> p_a_1_, boolean p_a_2_) {
      return a(p_a_0_, a(p_a_1_), p_a_2_);
   }

   private static String a(URL p_a_0_, String p_a_1_, boolean p_a_2_) {
      try {
         Proxy proxy = MinecraftServer.getServer() == null?null:MinecraftServer.getServer().ay();
         if(proxy == null) {
            proxy = Proxy.NO_PROXY;
         }

         HttpURLConnection httpurlconnection = (HttpURLConnection)p_a_0_.openConnection(proxy);
         httpurlconnection.setRequestMethod("POST");
         httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         httpurlconnection.setRequestProperty("Content-Length", "" + p_a_1_.getBytes().length);
         httpurlconnection.setRequestProperty("Content-Language", "en-US");
         httpurlconnection.setUseCaches(false);
         httpurlconnection.setDoInput(true);
         httpurlconnection.setDoOutput(true);
         DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
         dataoutputstream.writeBytes(p_a_1_);
         dataoutputstream.flush();
         dataoutputstream.close();
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
         StringBuffer stringbuffer = new StringBuffer();

         String s;
         while((s = bufferedreader.readLine()) != null) {
            stringbuffer.append(s);
            stringbuffer.append('\r');
         }

         bufferedreader.close();
         return stringbuffer.toString();
      } catch (Exception exception) {
         if(!p_a_2_) {
            c.error((String)("Could not post to " + p_a_0_), (Throwable)exception);
         }

         return "";
      }
   }
}

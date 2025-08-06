package net.minecraft.server.v1_8_R3;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftEncryption {
   private static final Logger a = LogManager.getLogger();

   public static KeyPair b() {
      try {
         KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
         keypairgenerator.initialize(1024);
         return keypairgenerator.generateKeyPair();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         nosuchalgorithmexception.printStackTrace();
         a.error("Key pair generation failed!");
         return null;
      }
   }

   public static byte[] a(String p_a_0_, PublicKey p_a_1_, SecretKey p_a_2_) {
      try {
         return a("SHA-1", new byte[][]{p_a_0_.getBytes("ISO_8859_1"), p_a_2_.getEncoded(), p_a_1_.getEncoded()});
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         unsupportedencodingexception.printStackTrace();
         return null;
      }
   }

   private static byte[] a(String p_a_0_, byte[]... p_a_1_) {
      try {
         MessageDigest messagedigest = MessageDigest.getInstance(p_a_0_);

         for(byte[] abyte : p_a_1_) {
            messagedigest.update(abyte);
         }

         return messagedigest.digest();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         nosuchalgorithmexception.printStackTrace();
         return null;
      }
   }

   public static PublicKey a(byte[] p_a_0_) {
      try {
         X509EncodedKeySpec x509encodedkeyspec = new X509EncodedKeySpec(p_a_0_);
         KeyFactory keyfactory = KeyFactory.getInstance("RSA");
         return keyfactory.generatePublic(x509encodedkeyspec);
      } catch (NoSuchAlgorithmException var3) {
         ;
      } catch (InvalidKeySpecException var4) {
         ;
      }

      a.error("Public key reconstitute failed!");
      return null;
   }

   public static SecretKey a(PrivateKey p_a_0_, byte[] p_a_1_) {
      return new SecretKeySpec(b(p_a_0_, p_a_1_), "AES");
   }

   public static byte[] b(Key p_b_0_, byte[] p_b_1_) {
      return a(2, (Key)p_b_0_, (byte[])p_b_1_);
   }

   private static byte[] a(int p_a_0_, Key p_a_1_, byte[] p_a_2_) {
      try {
         return a(p_a_0_, p_a_1_.getAlgorithm(), p_a_1_).doFinal(p_a_2_);
      } catch (IllegalBlockSizeException illegalblocksizeexception) {
         illegalblocksizeexception.printStackTrace();
      } catch (BadPaddingException badpaddingexception) {
         badpaddingexception.printStackTrace();
      }

      a.error("Cipher data failed!");
      return null;
   }

   private static Cipher a(int p_a_0_, String p_a_1_, Key p_a_2_) {
      try {
         Cipher cipher = Cipher.getInstance(p_a_1_);
         cipher.init(p_a_0_, p_a_2_);
         return cipher;
      } catch (InvalidKeyException invalidkeyexception) {
         invalidkeyexception.printStackTrace();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         nosuchalgorithmexception.printStackTrace();
      } catch (NoSuchPaddingException nosuchpaddingexception) {
         nosuchpaddingexception.printStackTrace();
      }

      a.error("Cipher creation failed!");
      return null;
   }

   public static Cipher a(int p_a_0_, Key p_a_1_) {
      try {
         Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
         cipher.init(p_a_0_, p_a_1_, new IvParameterSpec(p_a_1_.getEncoded()));
         return cipher;
      } catch (GeneralSecurityException generalsecurityexception) {
         throw new RuntimeException(generalsecurityexception);
      }
   }
}

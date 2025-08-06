package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.ByteBufProcessor;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class PacketDataSerializer extends ByteBuf {
   private final ByteBuf a;

   public PacketDataSerializer(ByteBuf p_i250_1_) {
      this.a = p_i250_1_;
   }

   public static int a(int p_a_0_) {
      for(int i = 1; i < 5; ++i) {
         if((p_a_0_ & -1 << i * 7) == 0) {
            return i;
         }
      }

      return 5;
   }

   public void a(byte[] p_a_1_) {
      this.b(p_a_1_.length);
      this.writeBytes(p_a_1_);
   }

   public byte[] a() {
      byte[] abyte = new byte[this.e()];
      this.readBytes(abyte);
      return abyte;
   }

   public BlockPosition c() {
      return BlockPosition.fromLong(this.readLong());
   }

   public void a(BlockPosition p_a_1_) {
      this.writeLong(p_a_1_.asLong());
   }

   public IChatBaseComponent d() throws IOException {
      return IChatBaseComponent.ChatSerializer.a(this.c(32767));
   }

   public void a(IChatBaseComponent p_a_1_) throws IOException {
      this.a(IChatBaseComponent.ChatSerializer.a(p_a_1_));
   }

   public <T extends Enum<T>> T a(Class<T> p_a_1_) {
      return (T)((Enum[])p_a_1_.getEnumConstants())[this.e()];
   }

   public void a(Enum<?> p_a_1_) {
      this.b(p_a_1_.ordinal());
   }

   public int e() {
      int i = 0;
      int j = 0;

      while(true) {
         byte b0 = this.readByte();
         i |= (b0 & 127) << j++ * 7;
         if(j > 5) {
            throw new RuntimeException("VarInt too big");
         }

         if((b0 & 128) != 128) {
            break;
         }
      }

      return i;
   }

   public long f() {
      long i = 0L;
      int j = 0;

      while(true) {
         byte b0 = this.readByte();
         i |= (long)(b0 & 127) << j++ * 7;
         if(j > 10) {
            throw new RuntimeException("VarLong too big");
         }

         if((b0 & 128) != 128) {
            break;
         }
      }

      return i;
   }

   public void a(UUID p_a_1_) {
      this.writeLong(p_a_1_.getMostSignificantBits());
      this.writeLong(p_a_1_.getLeastSignificantBits());
   }

   public UUID g() {
      return new UUID(this.readLong(), this.readLong());
   }

   public void b(int p_b_1_) {
      while((p_b_1_ & -128) != 0) {
         this.writeByte(p_b_1_ & 127 | 128);
         p_b_1_ >>>= 7;
      }

      this.writeByte(p_b_1_);
   }

   public void b(long p_b_1_) {
      while((p_b_1_ & -128L) != 0L) {
         this.writeByte((int)(p_b_1_ & 127L) | 128);
         p_b_1_ >>>= 7;
      }

      this.writeByte((int)p_b_1_);
   }

   public void a(NBTTagCompound p_a_1_) {
      if(p_a_1_ == null) {
         this.writeByte(0);
      } else {
         try {
            NBTCompressedStreamTools.a((NBTTagCompound)p_a_1_, (DataOutput)(new ByteBufOutputStream(this)));
         } catch (Exception exception) {
            throw new EncoderException(exception);
         }
      }

   }

   public NBTTagCompound h() throws IOException {
      int i = this.readerIndex();
      byte b0 = this.readByte();
      if(b0 == 0) {
         return null;
      } else {
         this.readerIndex(i);
         return NBTCompressedStreamTools.a((DataInput)(new ByteBufInputStream(this)), (NBTReadLimiter)(new NBTReadLimiter(2097152L)));
      }
   }

   public void a(ItemStack p_a_1_) {
      if(p_a_1_ != null && p_a_1_.getItem() != null) {
         this.writeShort(Item.getId(p_a_1_.getItem()));
         this.writeByte(p_a_1_.count);
         this.writeShort(p_a_1_.getData());
         NBTTagCompound nbttagcompound = null;
         if(p_a_1_.getItem().usesDurability() || p_a_1_.getItem().p()) {
            p_a_1_ = p_a_1_.cloneItemStack();
            CraftItemStack.setItemMeta(p_a_1_, CraftItemStack.getItemMeta(p_a_1_));
            nbttagcompound = p_a_1_.getTag();
         }

         this.a(nbttagcompound);
      } else {
         this.writeShort(-1);
      }

   }

   public ItemStack i() throws IOException {
      ItemStack itemstack = null;
      short short1 = this.readShort();
      if(short1 >= 0) {
         byte b0 = this.readByte();
         short short2 = this.readShort();
         itemstack = new ItemStack(Item.getById(short1), b0, short2);
         itemstack.setTag(this.h());
         if(itemstack.getTag() != null) {
            CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
         }
      }

      return itemstack;
   }

   public String c(int p_c_1_) {
      int i = this.e();
      if(i > p_c_1_ * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + p_c_1_ * 4 + ")");
      } else if(i < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String s = new String(this.readBytes(i).array(), Charsets.UTF_8);
         if(s.length() > p_c_1_) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + p_c_1_ + ")");
         } else {
            return s;
         }
      }
   }

   public PacketDataSerializer a(String p_a_1_) {
      byte[] abyte = p_a_1_.getBytes(Charsets.UTF_8);
      if(abyte.length > 32767) {
         throw new EncoderException("String too big (was " + p_a_1_.length() + " bytes encoded, max " + 32767 + ")");
      } else {
         this.b(abyte.length);
         this.writeBytes(abyte);
         return this;
      }
   }

   public int capacity() {
      return this.a.capacity();
   }

   public ByteBuf capacity(int p_capacity_1_) {
      return this.a.capacity(p_capacity_1_);
   }

   public int maxCapacity() {
      return this.a.maxCapacity();
   }

   public ByteBufAllocator alloc() {
      return this.a.alloc();
   }

   public ByteOrder order() {
      return this.a.order();
   }

   public ByteBuf order(ByteOrder p_order_1_) {
      return this.a.order(p_order_1_);
   }

   public ByteBuf unwrap() {
      return this.a.unwrap();
   }

   public boolean isDirect() {
      return this.a.isDirect();
   }

   public int readerIndex() {
      return this.a.readerIndex();
   }

   public ByteBuf readerIndex(int p_readerIndex_1_) {
      return this.a.readerIndex(p_readerIndex_1_);
   }

   public int writerIndex() {
      return this.a.writerIndex();
   }

   public ByteBuf writerIndex(int p_writerIndex_1_) {
      return this.a.writerIndex(p_writerIndex_1_);
   }

   public ByteBuf setIndex(int p_setIndex_1_, int p_setIndex_2_) {
      return this.a.setIndex(p_setIndex_1_, p_setIndex_2_);
   }

   public int readableBytes() {
      return this.a.readableBytes();
   }

   public int writableBytes() {
      return this.a.writableBytes();
   }

   public int maxWritableBytes() {
      return this.a.maxWritableBytes();
   }

   public boolean isReadable() {
      return this.a.isReadable();
   }

   public boolean isReadable(int p_isReadable_1_) {
      return this.a.isReadable(p_isReadable_1_);
   }

   public boolean isWritable() {
      return this.a.isWritable();
   }

   public boolean isWritable(int p_isWritable_1_) {
      return this.a.isWritable(p_isWritable_1_);
   }

   public ByteBuf clear() {
      return this.a.clear();
   }

   public ByteBuf markReaderIndex() {
      return this.a.markReaderIndex();
   }

   public ByteBuf resetReaderIndex() {
      return this.a.resetReaderIndex();
   }

   public ByteBuf markWriterIndex() {
      return this.a.markWriterIndex();
   }

   public ByteBuf resetWriterIndex() {
      return this.a.resetWriterIndex();
   }

   public ByteBuf discardReadBytes() {
      return this.a.discardReadBytes();
   }

   public ByteBuf discardSomeReadBytes() {
      return this.a.discardSomeReadBytes();
   }

   public ByteBuf ensureWritable(int p_ensureWritable_1_) {
      return this.a.ensureWritable(p_ensureWritable_1_);
   }

   public int ensureWritable(int p_ensureWritable_1_, boolean p_ensureWritable_2_) {
      return this.a.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
   }

   public boolean getBoolean(int p_getBoolean_1_) {
      return this.a.getBoolean(p_getBoolean_1_);
   }

   public byte getByte(int p_getByte_1_) {
      return this.a.getByte(p_getByte_1_);
   }

   public short getUnsignedByte(int p_getUnsignedByte_1_) {
      return this.a.getUnsignedByte(p_getUnsignedByte_1_);
   }

   public short getShort(int p_getShort_1_) {
      return this.a.getShort(p_getShort_1_);
   }

   public int getUnsignedShort(int p_getUnsignedShort_1_) {
      return this.a.getUnsignedShort(p_getUnsignedShort_1_);
   }

   public int getMedium(int p_getMedium_1_) {
      return this.a.getMedium(p_getMedium_1_);
   }

   public int getUnsignedMedium(int p_getUnsignedMedium_1_) {
      return this.a.getUnsignedMedium(p_getUnsignedMedium_1_);
   }

   public int getInt(int p_getInt_1_) {
      return this.a.getInt(p_getInt_1_);
   }

   public long getUnsignedInt(int p_getUnsignedInt_1_) {
      return this.a.getUnsignedInt(p_getUnsignedInt_1_);
   }

   public long getLong(int p_getLong_1_) {
      return this.a.getLong(p_getLong_1_);
   }

   public char getChar(int p_getChar_1_) {
      return this.a.getChar(p_getChar_1_);
   }

   public float getFloat(int p_getFloat_1_) {
      return this.a.getFloat(p_getFloat_1_);
   }

   public double getDouble(int p_getDouble_1_) {
      return this.a.getDouble(p_getDouble_1_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_) {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_) {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_) {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuffer p_getBytes_2_) {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_) throws IOException {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
   }

   public int getBytes(int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_) throws IOException {
      return this.a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
   }

   public ByteBuf setBoolean(int p_setBoolean_1_, boolean p_setBoolean_2_) {
      return this.a.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
   }

   public ByteBuf setByte(int p_setByte_1_, int p_setByte_2_) {
      return this.a.setByte(p_setByte_1_, p_setByte_2_);
   }

   public ByteBuf setShort(int p_setShort_1_, int p_setShort_2_) {
      return this.a.setShort(p_setShort_1_, p_setShort_2_);
   }

   public ByteBuf setMedium(int p_setMedium_1_, int p_setMedium_2_) {
      return this.a.setMedium(p_setMedium_1_, p_setMedium_2_);
   }

   public ByteBuf setInt(int p_setInt_1_, int p_setInt_2_) {
      return this.a.setInt(p_setInt_1_, p_setInt_2_);
   }

   public ByteBuf setLong(int p_setLong_1_, long p_setLong_2_) {
      return this.a.setLong(p_setLong_1_, p_setLong_2_);
   }

   public ByteBuf setChar(int p_setChar_1_, int p_setChar_2_) {
      return this.a.setChar(p_setChar_1_, p_setChar_2_);
   }

   public ByteBuf setFloat(int p_setFloat_1_, float p_setFloat_2_) {
      return this.a.setFloat(p_setFloat_1_, p_setFloat_2_);
   }

   public ByteBuf setDouble(int p_setDouble_1_, double p_setDouble_2_) {
      return this.a.setDouble(p_setDouble_1_, p_setDouble_2_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_) {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_) {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_) {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuffer p_setBytes_2_) {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_);
   }

   public int setBytes(int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_) throws IOException {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
   }

   public int setBytes(int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_) throws IOException {
      return this.a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
   }

   public ByteBuf setZero(int p_setZero_1_, int p_setZero_2_) {
      return this.a.setZero(p_setZero_1_, p_setZero_2_);
   }

   public boolean readBoolean() {
      return this.a.readBoolean();
   }

   public byte readByte() {
      return this.a.readByte();
   }

   public short readUnsignedByte() {
      return this.a.readUnsignedByte();
   }

   public short readShort() {
      return this.a.readShort();
   }

   public int readUnsignedShort() {
      return this.a.readUnsignedShort();
   }

   public int readMedium() {
      return this.a.readMedium();
   }

   public int readUnsignedMedium() {
      return this.a.readUnsignedMedium();
   }

   public int readInt() {
      return this.a.readInt();
   }

   public long readUnsignedInt() {
      return this.a.readUnsignedInt();
   }

   public long readLong() {
      return this.a.readLong();
   }

   public char readChar() {
      return this.a.readChar();
   }

   public float readFloat() {
      return this.a.readFloat();
   }

   public double readDouble() {
      return this.a.readDouble();
   }

   public ByteBuf readBytes(int p_readBytes_1_) {
      return this.a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readSlice(int p_readSlice_1_) {
      return this.a.readSlice(p_readSlice_1_);
   }

   public ByteBuf readBytes(ByteBuf p_readBytes_1_) {
      return this.a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_) {
      return this.a.readBytes(p_readBytes_1_, p_readBytes_2_);
   }

   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
      return this.a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
   }

   public ByteBuf readBytes(byte[] p_readBytes_1_) {
      return this.a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readBytes(byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
      return this.a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
   }

   public ByteBuf readBytes(ByteBuffer p_readBytes_1_) {
      return this.a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readBytes(OutputStream p_readBytes_1_, int p_readBytes_2_) throws IOException {
      return this.a.readBytes(p_readBytes_1_, p_readBytes_2_);
   }

   public int readBytes(GatheringByteChannel p_readBytes_1_, int p_readBytes_2_) throws IOException {
      return this.a.readBytes(p_readBytes_1_, p_readBytes_2_);
   }

   public ByteBuf skipBytes(int p_skipBytes_1_) {
      return this.a.skipBytes(p_skipBytes_1_);
   }

   public ByteBuf writeBoolean(boolean p_writeBoolean_1_) {
      return this.a.writeBoolean(p_writeBoolean_1_);
   }

   public ByteBuf writeByte(int p_writeByte_1_) {
      return this.a.writeByte(p_writeByte_1_);
   }

   public ByteBuf writeShort(int p_writeShort_1_) {
      return this.a.writeShort(p_writeShort_1_);
   }

   public ByteBuf writeMedium(int p_writeMedium_1_) {
      return this.a.writeMedium(p_writeMedium_1_);
   }

   public ByteBuf writeInt(int p_writeInt_1_) {
      return this.a.writeInt(p_writeInt_1_);
   }

   public ByteBuf writeLong(long p_writeLong_1_) {
      return this.a.writeLong(p_writeLong_1_);
   }

   public ByteBuf writeChar(int p_writeChar_1_) {
      return this.a.writeChar(p_writeChar_1_);
   }

   public ByteBuf writeFloat(float p_writeFloat_1_) {
      return this.a.writeFloat(p_writeFloat_1_);
   }

   public ByteBuf writeDouble(double p_writeDouble_1_) {
      return this.a.writeDouble(p_writeDouble_1_);
   }

   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_) {
      return this.a.writeBytes(p_writeBytes_1_);
   }

   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_) {
      return this.a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
   }

   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
      return this.a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
   }

   public ByteBuf writeBytes(byte[] p_writeBytes_1_) {
      return this.a.writeBytes(p_writeBytes_1_);
   }

   public ByteBuf writeBytes(byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
      return this.a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
   }

   public ByteBuf writeBytes(ByteBuffer p_writeBytes_1_) {
      return this.a.writeBytes(p_writeBytes_1_);
   }

   public int writeBytes(InputStream p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
      return this.a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
   }

   public int writeBytes(ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
      return this.a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
   }

   public ByteBuf writeZero(int p_writeZero_1_) {
      return this.a.writeZero(p_writeZero_1_);
   }

   public int indexOf(int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_) {
      return this.a.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
   }

   public int bytesBefore(byte p_bytesBefore_1_) {
      return this.a.bytesBefore(p_bytesBefore_1_);
   }

   public int bytesBefore(int p_bytesBefore_1_, byte p_bytesBefore_2_) {
      return this.a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
   }

   public int bytesBefore(int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_) {
      return this.a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
   }

   public int forEachByte(ByteBufProcessor p_forEachByte_1_) {
      return this.a.forEachByte(p_forEachByte_1_);
   }

   public int forEachByte(int p_forEachByte_1_, int p_forEachByte_2_, ByteBufProcessor p_forEachByte_3_) {
      return this.a.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
   }

   public int forEachByteDesc(ByteBufProcessor p_forEachByteDesc_1_) {
      return this.a.forEachByteDesc(p_forEachByteDesc_1_);
   }

   public int forEachByteDesc(int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteBufProcessor p_forEachByteDesc_3_) {
      return this.a.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
   }

   public ByteBuf copy() {
      return this.a.copy();
   }

   public ByteBuf copy(int p_copy_1_, int p_copy_2_) {
      return this.a.copy(p_copy_1_, p_copy_2_);
   }

   public ByteBuf slice() {
      return this.a.slice();
   }

   public ByteBuf slice(int p_slice_1_, int p_slice_2_) {
      return this.a.slice(p_slice_1_, p_slice_2_);
   }

   public ByteBuf duplicate() {
      return this.a.duplicate();
   }

   public int nioBufferCount() {
      return this.a.nioBufferCount();
   }

   public ByteBuffer nioBuffer() {
      return this.a.nioBuffer();
   }

   public ByteBuffer nioBuffer(int p_nioBuffer_1_, int p_nioBuffer_2_) {
      return this.a.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
   }

   public ByteBuffer internalNioBuffer(int p_internalNioBuffer_1_, int p_internalNioBuffer_2_) {
      return this.a.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
   }

   public ByteBuffer[] nioBuffers() {
      return this.a.nioBuffers();
   }

   public ByteBuffer[] nioBuffers(int p_nioBuffers_1_, int p_nioBuffers_2_) {
      return this.a.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
   }

   public boolean hasArray() {
      return this.a.hasArray();
   }

   public byte[] array() {
      return this.a.array();
   }

   public int arrayOffset() {
      return this.a.arrayOffset();
   }

   public boolean hasMemoryAddress() {
      return this.a.hasMemoryAddress();
   }

   public long memoryAddress() {
      return this.a.memoryAddress();
   }

   public String toString(Charset p_toString_1_) {
      return this.a.toString(p_toString_1_);
   }

   public String toString(int p_toString_1_, int p_toString_2_, Charset p_toString_3_) {
      return this.a.toString(p_toString_1_, p_toString_2_, p_toString_3_);
   }

   public int hashCode() {
      return this.a.hashCode();
   }

   public boolean equals(Object p_equals_1_) {
      return this.a.equals(p_equals_1_);
   }

   public int compareTo(ByteBuf p_compareTo_1_) {
      return this.a.compareTo(p_compareTo_1_);
   }

   public String toString() {
      return this.a.toString();
   }

   public ByteBuf retain(int p_retain_1_) {
      return this.a.retain(p_retain_1_);
   }

   public ByteBuf retain() {
      return this.a.retain();
   }

   public int refCnt() {
      return this.a.refCnt();
   }

   public boolean release() {
      return this.a.release();
   }

   public boolean release(int p_release_1_) {
      return this.a.release(p_release_1_);
   }
}

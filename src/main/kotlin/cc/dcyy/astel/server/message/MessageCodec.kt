package cc.dcyy.astel.server.message

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import mu.KotlinLogging

/**
 * The Astel Message Protocol.
 *
 * - Magic Number:
 * ** 5Bytes, fixed at 'Astel'.
 *
 * - Version:
 * ** 1Bytes, current version is 1.
 *
 * - Serialization formats:
 * ** 1Bytes, 1=common.
 *
 * - Message Type:
 * ** 1=Auth, 2=Command
 *
 * - Content Length:
 * ** 4Bytes.
 *
 * - Content:
 * ** The message content, must match the length declared.
 *
 * This is an example, Sp means Space.
 * Str: Astel1120008get name
 * Hex Bytes: 41 73 74 65 6c 31 31 32 30 30 30 30 67 65 74 20 6e 61 6d 65
 * Str:       A  s  t  e  l  1  1  2  0  0  0  8  g  e  t  Sp n  a  m  e
 */
class MessageCodec : ByteToMessageCodec<Message>() {

    private val L = KotlinLogging.logger {}

    override fun encode(ctx: ChannelHandlerContext?, msg: Message?, out: ByteBuf?) {
        TODO("Not yet implemented")
    }

    override fun decode(ctx: ChannelHandlerContext?, inByteBuf: ByteBuf?, out: MutableList<Any?>?) {
        if (inByteBuf == null) {
            TODO("Not yet implemented")
        }
        val magicNumBytes = ByteArray(5)
        inByteBuf.readBytes(magicNumBytes)
        val magicNum = String(magicNumBytes, Charsets.UTF_8)

        // Dec: 49, Hex: 31, Char: 1
        val versionByte = inByteBuf.readByte()
        val serializationFormats = inByteBuf.readByte()

        // Dec: 50, Hex: 32, Char: 2
        val messageType = inByteBuf.readByte()

        // TODO
        // val contentLength = inByteBuf.readInt()
        val contentBytes = ByteArray(8)
        inByteBuf.readBytes(contentBytes)
        val content = String(contentBytes, Charsets.UTF_8)

        val mt = if (messageType.toInt() == 1) MessageType.Auth else MessageType.Command
        val message = Message(mt, content)
        L.debug { "Decoded message success. $message" }
        out?.add(message)
    }


}
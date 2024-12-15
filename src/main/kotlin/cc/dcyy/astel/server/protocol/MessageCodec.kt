package cc.dcyy.astel.server.protocol

import cc.dcyy.astel.IllegalMessageServiceError
import cc.dcyy.astel.Json
import cc.dcyy.astel.SomeObjectNullServiceError
import cc.dcyy.astel.UnexpectedServiceError
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
 * ** 1Byte, current version is 1.
 *
 * - Serialization formats:
 * ** 1Byte, 1=common, 2=json
 *
 * - Message Scope:
 * ** 1Byte, 0=Meaningless, 1=Admin, 2=Astel
 *
 * - Content Length:
 * ** 4Bytes.
 *
 * - Content:
 * ** The message content, must match the length declared.
 *
 * This is an example.
 * Char: Astel 1 1 2 8 get name
 * Hex Bytes: 41 73 74 65 6c 01 01 02 00 00 00 08 67 65 74 20 6e 61 6d 65
 */
class MessageCodec : ByteToMessageCodec<Message>() {

    companion object {
        private const val MAGIC_NUM = "Astel"
        private const val VERSION = 1
        private const val SERIALIZATION_COMMON = 1
        private const val SERIALIZATION_JSON = 2
        private const val SCOPE_MEANINGLESS = 0
        private const val SCOPE_ADMIN = 1
        private const val SCOPE_ASTEL = 2
    }

    private val L = KotlinLogging.logger {}

    override fun encode(ctx: ChannelHandlerContext?, msg: Message?, out: ByteBuf?) {
        if (ctx == null || msg == null || out == null) {
            ctx!!.writeAndFlush(SomeObjectNullServiceError)
            return
        }
        out.writeBytes(MAGIC_NUM.toByteArray())
        out.writeByte(VERSION)
        out.writeByte(SERIALIZATION_JSON)
        val scope = when (msg) {
            is AdminResponseMessage -> SCOPE_ADMIN
            is AstelResponseMessage -> SCOPE_ASTEL
            is ServiceErrorResponseMessage -> SCOPE_MEANINGLESS
            else -> {
                TODO("Not yet implemented")
            }
        }
        out.writeByte(scope)
        val messageBytes = Json.toJson(msg).encodeToByteArray()
        out.writeInt(messageBytes.size)
        out.writeBytes(messageBytes)
    }

    override fun decode(ctx: ChannelHandlerContext?, inByteBuf: ByteBuf?, out: MutableList<Any?>?) {
        try {
            if (ctx == null || inByteBuf == null || out == null) {
                out!!.add(SomeObjectNullServiceError)
                return
            }
            val magicNumBytes = ByteArray(5)
            inByteBuf.readBytes(magicNumBytes)
            val magicNum = String(magicNumBytes, Charsets.UTF_8)
            val version = inByteBuf.readByte().toInt()
            val serializationFormat = inByteBuf.readByte().toInt()
            val messageScope = inByteBuf.readByte().toInt()
            val contentLength = inByteBuf.readInt()
            val contentBytes = ByteArray(contentLength)
            inByteBuf.readBytes(contentBytes)
            val content = String(contentBytes, Charsets.UTF_8)

            if (magicNum != MAGIC_NUM || version != VERSION || serializationFormat != SERIALIZATION_COMMON || (messageScope != SCOPE_ADMIN && messageScope != SCOPE_ASTEL)) {
                L.warn { "Illegal message." }
                out.add(IllegalMessageServiceError)
                return
            }

            val message = if (messageScope == SCOPE_ADMIN) AdminRequestMessage(content) else AstelRequestMessage(content)
            out.add(message)
        } catch (e: Exception) {
            L.error { "Encoding catch exception. $e" }
            out!!.add(UnexpectedServiceError)
        }
    }

}
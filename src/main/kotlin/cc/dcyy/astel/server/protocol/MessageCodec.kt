package cc.dcyy.astel.server.protocol

import cc.dcyy.astel.Json
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
 * ** 1Byte, 1=Admin, 2=Astel
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

    private val magicNum = "Astel"
    private val version = 1
    private val serializationCommon = 1
    private val serializationJson = 2
    private val scopeAdmin = 1
    private val scopeAstel = 2

    private val L = KotlinLogging.logger {}

    override fun encode(ctx: ChannelHandlerContext?, msg: Message?, out: ByteBuf?) {
        if (ctx == null || msg == null || out == null) {
            TODO("Not yet implemented")
        }
        out.writeBytes(magicNum.toByteArray())
        out.writeByte(version)
        out.writeByte(serializationJson)
        val scope = when (msg) {
            is AdminResponseMessage -> scopeAdmin
            is AstelResponseMessage -> scopeAstel
            else -> {
                TODO("Not yet implemented")
            }
        }
        out.writeByte(scope)
        val messageBytes = Json.toJson(msg).encodeToByteArray()
        out.writeInt(messageBytes.size)
        out.writeBytes(messageBytes)
        L.debug { "Encoded message success." }
    }

    override fun decode(ctx: ChannelHandlerContext?, inByteBuf: ByteBuf?, out: MutableList<Any?>?) {
        if (inByteBuf == null) {
            TODO("Not yet implemented")
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

        // TODO Check Message.

        val message = if (messageScope == scopeAdmin) AdminRequestMessage(content) else AstelRequestMessage(content)
        L.debug { "Decoded message success. $message" }
        out?.add(message)
    }

}
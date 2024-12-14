package cc.dcyy.astel.server.handler.inbound

import cc.dcyy.astel.entrance.CommandExecutor
import cc.dcyy.astel.server.message.Message
import cc.dcyy.astel.server.message.MessageType.*
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import mu.KotlinLogging

class AstelInboundHandler : ChannelInboundHandlerAdapter() {

    private val L = KotlinLogging.logger {}

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        L.debug { "AstelInboundHandler..." }
        val message = msg as Message
        val response = when (message.type) {
            Auth -> TODO()
            Command -> CommandExecutor.execute(message.content)
        }
        ctx.writeAndFlush(response)
    }

}
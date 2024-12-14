package cc.dcyy.astel.server.handler.inbound

import cc.dcyy.astel.command.CommandExecutor
import cc.dcyy.astel.server.message.Message
import cc.dcyy.astel.server.message.MessageType
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import mu.KotlinLogging

class AstelInboundHandler : ChannelInboundHandlerAdapter() {

    private val L = KotlinLogging.logger {}

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        L.debug { "AstelInboundHandler..." }
        val message = msg as Message
        when (message.type) {
            MessageType.Auth -> TODO()
            MessageType.Command -> CommandExecutor.execute(message.content)
        }
        var response = CommandExecutor.execute(msg as String)
        ctx.writeAndFlush(response)
    }

}
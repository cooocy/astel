package cc.dcyy.astel.server.handler.inbound

import cc.dcyy.astel.core.entrance.CommandExecutor
import cc.dcyy.astel.server.protocol.AstelRequestMessage
import cc.dcyy.astel.server.protocol.AstelResponseMessage
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import mu.KotlinLogging

/**
 * Handler the AstelRequestMessage.
 * astelRequestMessage > | This | > astelResponseMessage
 */
class AstelInboundHandler : SimpleChannelInboundHandler<AstelRequestMessage>() {

    private val L = KotlinLogging.logger {}

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: AstelRequestMessage?) {
        if (ctx != null && msg != null) {
            val astelResponse = CommandExecutor.execute(msg.content)
            ctx.writeAndFlush(AstelResponseMessage(astelResponse.code, astelResponse.content))
        }
    }

}
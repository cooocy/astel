package cc.dcyy.astel.server.handler.inbound

import cc.dcyy.astel.server.protocol.AdminRequestMessage
import cc.dcyy.astel.server.protocol.AdminResponseMessage
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import mu.KotlinLogging

/**
 * Handler the AdminRequestMessage.
 * adminRequestMessage > | This | > adminResponseMessage
 */
class AdminInboundHandler : SimpleChannelInboundHandler<AdminRequestMessage>() {

    private val L = KotlinLogging.logger {}

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: AdminRequestMessage?) {
        L.debug { "AdminInboundHandler" }
        ctx?.writeAndFlush(AdminResponseMessage("Login OK."))
    }

}
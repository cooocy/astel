package cc.dcyy.astel.server.handler.inbound

import cc.dcyy.astel.server.protocol.Message
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import mu.KotlinLogging

class RecordRequestInboundHandler : SimpleChannelInboundHandler<Message>() {

    private val L = KotlinLogging.logger {}

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Message?) {
        L.info { "Received Request. Remote: ${ctx?.channel()?.remoteAddress()}, msg: $msg}" }
        ctx?.fireChannelRead(msg)
    }

}
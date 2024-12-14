package cc.dcyy.astel.server.handler.inbound

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import mu.KotlinLogging

class RecordRequestInboundHandler : ChannelInboundHandlerAdapter() {

    private val L = KotlinLogging.logger {}

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        L.info { "Received Request. Remote: ${ctx.channel().remoteAddress()}, msg: $msg}" }
        super.channelRead(ctx, msg)
    }

}
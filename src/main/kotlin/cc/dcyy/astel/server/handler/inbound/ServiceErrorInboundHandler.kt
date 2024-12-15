package cc.dcyy.astel.server.handler.inbound

import cc.dcyy.astel.ServiceError
import cc.dcyy.astel.server.protocol.ServiceErrorResponseMessage
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 * Handler ServiceError.
 * serviceError > | This | > serviceErrorResponseMessage
 */
class ServiceErrorInboundHandler : SimpleChannelInboundHandler<ServiceError>() {

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: ServiceError?) {
        if (ctx != null && msg != null) {
            ctx.writeAndFlush(ServiceErrorResponseMessage.new(msg))
        }
    }

}
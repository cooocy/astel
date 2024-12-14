package cc.dcyy.astel.server

import cc.dcyy.astel.server.handler.inbound.AstelInboundHandler
import cc.dcyy.astel.server.handler.inbound.RecordRequestInboundHandler
import cc.dcyy.astel.server.message.MessageCodec
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

fun main() {
    // The server only has one NioServerSocketChannel, no need to specify the boos group thread(1)
    val bossGroup = NioEventLoopGroup(1)
    val workerGroup = NioEventLoopGroup()
    val astelWorkerGroup = NioEventLoopGroup()
    try {
        val serverBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel::class.java).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true).childHandler(object : ChannelInitializer<SocketChannel>() {
            @Throws(Exception::class)
            override fun initChannel(socketChannel: SocketChannel) {
                // The Inbound Order: DecodeHandler > RecordRequestHandler > AstelHandler
                socketChannel.pipeline().addLast(MessageCodec())
                socketChannel.pipeline().addLast(RecordRequestInboundHandler())
                socketChannel.pipeline().addLast(astelWorkerGroup, AstelInboundHandler())

                // The Outbound Order:
            }
        })
        val channelFuture: ChannelFuture = serverBootstrap.bind(8080).sync()
        channelFuture.channel().closeFuture().sync()
    } finally {
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
        astelWorkerGroup.shutdownGracefully()
    }
}

package cc.dcyy.astel.server

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.socket.SocketChannel

fun main(args: Array<String>) {
    val eventLoopGroup = NioEventLoopGroup()
    val bootstrap = Bootstrap()
    try {
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel::class.java).handler(object : ChannelInitializer<SocketChannel>() {
            override fun initChannel(ch: SocketChannel) {
                ch.pipeline().addLast(object : ChannelInboundHandlerAdapter() {
                    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                        var bb = msg as ByteBuf
                        println("Server response: ${bb.toString(Charsets.UTF_8)}")
                    }
                })
            }
        })

        val channelFuture = bootstrap.connect("localhost", 8080).sync()
        val channel = channelFuture.channel()

        val bb = channel.alloc().buffer()
        bb.writeBytes("Astel".encodeToByteArray())
        bb.writeByte(1)
        bb.writeByte(1)
        bb.writeByte(2)
        val content = "set name cooocy".encodeToByteArray()
        bb.writeInt(content.size)
        bb.writeBytes(content)
        channel.writeAndFlush(bb).sync()

        channel.closeFuture().sync()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } finally {
        eventLoopGroup.shutdownGracefully()
    }
}
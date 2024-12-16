package cc.dcyy.astel.core.entrance

import cc.dcyy.astel.core.entry.Astel
import cc.dcyy.astel.AstelException
import cc.dcyy.astel.CommandArgsErrException
import cc.dcyy.astel.UnknownCommandException
import cc.dcyy.astel.ValueTypeNotMatchException
import cc.dcyy.astel.core.entry.Key
import cc.dcyy.astel.core.entry.Strings
import mu.KotlinLogging

object CommandExecutor {

    private val L = KotlinLogging.logger {}

    fun execute(input: String): AstelResponse {
        try {
            val request = parseRequest(input)
            return when (request.command) {
                Command.set -> doSet(request)
                Command.get -> doGet(request)
                Command.del -> TODO()
                Command.exists -> TODO()
                Command.expire -> TODO()
                Command.ttl -> TODO()
                Command.lpush -> TODO()
                Command.rpush -> TODO()
                Command.lpop -> TODO()
                Command.rpop -> TODO()
                Command.llen -> TODO()
                Command.lrange -> TODO()
            }
        } catch (e: AstelException) {
            L.error { "Execute catch expected exception. $e" }
            return AstelResponse.err(e)
        } catch (e: Exception) {
            L.error { "Execute catch unexpected exception. $e" }
            return AstelResponse.err(e)
        }
    }

    private fun parseRequest(input: String): AstelRequest {
        val formattedInput = input.replace(" +".toRegex(), " ")
        val parts = formattedInput.split(" ")
        val cmd = Command.entries.find { it.name == parts[0] } ?: throw UnknownCommandException()
        return AstelRequest(cmd, parts.drop(1))
    }


    private fun doSet(astelRequest: AstelRequest): AstelResponse {
        if (astelRequest.args.size != 2) {
            throw CommandArgsErrException()
        }
        Astel.put(Key.new(astelRequest.args[0] as String), Strings.new(astelRequest.args[1] as String))
        return AstelResponse.ok("Done.")
    }

    private fun doGet(astelRequest: AstelRequest): AstelResponse {
        if (astelRequest.args.size != 1) {
            throw CommandArgsErrException()
        }
        val value = Astel.get(Key.new(astelRequest.args[0] as String))
        if (value == null) {
            return AstelResponse.ok("")
        }
        if (value !is Strings) {
            throw ValueTypeNotMatchException()
        }
        return AstelResponse.ok(value.v)
    }

}
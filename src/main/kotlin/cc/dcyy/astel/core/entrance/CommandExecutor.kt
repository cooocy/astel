package cc.dcyy.astel.core.entrance

import cc.dcyy.astel.core.entry.Astel
import cc.dcyy.astel.AstelException
import cc.dcyy.astel.KeyNotFoundException
import cc.dcyy.astel.UnknownCommandException
import cc.dcyy.astel.ValueTypeNotMatchException
import cc.dcyy.astel.core.entry.Key
import cc.dcyy.astel.core.entry.Listl
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
                Command.del -> doDel(request)
                Command.exists -> doExists(request)
                Command.expire -> doExpire(request)
                Command.ttl -> doTtl(request)
                Command.lpush -> doLpush(request)
                Command.rpush -> doRpush(request)
                Command.lpop -> doLpop(request)
                Command.rpop -> doRpop(request)
                Command.llen -> doLlen(request)
                Command.lrange -> doLrange(request)
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
        astelRequest.checkArgsSize(2)
        Astel.put(Key.new(astelRequest.args[0]), Strings.new(astelRequest.args[1]))
        return AstelResponse.ok("Done.")
    }

    private fun doGet(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(1)
        val value = Astel.get(Key.new(astelRequest.args[0]))
        if (value == null) {
            return AstelResponse.ok("")
        }
        if (value !is Strings) {
            throw ValueTypeNotMatchException()
        }
        return AstelResponse.ok(value.v)
    }

    private fun doDel(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(1)
        Astel.remove(Key.new(astelRequest.args[0]))
        return AstelResponse.ok("Done.")
    }

    private fun doExists(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(1)
        var contains = Astel.contains(Key.new(astelRequest.args[0]))
        return AstelResponse.ok(contains)
    }

    private fun doExpire(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(2)
        val key = Key.new(astelRequest.args[0])
        val seconds = astelRequest.args[1].toLong()
        Astel.expire(key, seconds)
        return AstelResponse.ok("Done.")
    }

    private fun doTtl(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(1)
        return AstelResponse.ok(Astel.ttl(Key.new(astelRequest.args[0])))
    }

    private fun doLpush(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(2)
        val key = Key.new(astelRequest.args[0])
        val element = astelRequest.args[1]
        val v = Astel.get(key) ?: Astel.put(key, Listl.new(element))
        if (v !is Listl) {
            throw ValueTypeNotMatchException()
        }
        v.leftPush(element)
        return AstelResponse.ok("Done.")
    }

    private fun doLpop(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(1)
        val key = Key.new(astelRequest.args[0])
        val v = Astel.get(key) ?: throw KeyNotFoundException()
        if (v !is Listl) {
            throw ValueTypeNotMatchException()
        }
        val element = v.leftPop() ?: ""
        return AstelResponse.ok(element)
    }

    private fun doRpush(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(2)
        val key = Key.new(astelRequest.args[0])
        val element = astelRequest.args[1]
        val v = Astel.get(key) ?: Astel.put(key, Listl.new(element))
        if (v !is Listl) {
            throw ValueTypeNotMatchException()
        }
        v.rightPush(element)
        return AstelResponse.ok("Done.")
    }

    private fun doRpop(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(1)
        val key = Key.new(astelRequest.args[0])
        val v = Astel.get(key) ?: throw KeyNotFoundException()
        if (v !is Listl) {
            throw ValueTypeNotMatchException()
        }
        val element = v.rightPop() ?: ""
        return AstelResponse.ok(element)
    }

    private fun doLlen(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(1)
        val key = Key.new(astelRequest.args[0])
        val v = Astel.get(key) ?: throw KeyNotFoundException()
        if (v !is Listl) {
            throw ValueTypeNotMatchException()
        }
        return AstelResponse.ok(v.size())
    }

    private fun doLrange(astelRequest: AstelRequest): AstelResponse {
        astelRequest.checkArgsSize(3)
        val key = Key.new(astelRequest.args[0])
        val v = Astel.get(key) ?: throw KeyNotFoundException()
        if (v !is Listl) {
            throw ValueTypeNotMatchException()
        }
        val start = astelRequest.args[1].toInt()
        val end = astelRequest.args[2].toInt()
        return AstelResponse.ok(v.range(start, end))
    }

}

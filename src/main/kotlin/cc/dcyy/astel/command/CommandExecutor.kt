package cc.dcyy.astel.command

import cc.dcyy.astel.Astel
import cc.dcyy.astel.CommandErrorException
import cc.dcyy.astel.command.Command.*
import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.entry.Strings

object CommandExecutor {

    fun execute(input: String): Response {
        val request = parseRequest(input)
        return when (request.command) {
            set -> doSet(request)
            get -> doGet(request)
            del -> TODO()
            exists -> TODO()
            expire -> TODO()
            ttl -> TODO()
            lpush -> TODO()
            rpush -> TODO()
            lpop -> TODO()
            rpop -> TODO()
            llen -> TODO()
            lrange -> TODO()
        }
    }

    private fun parseRequest(input: String): Request {
        val formattedInput = input.replace(" +".toRegex(), " ")
        val parts = formattedInput.split(" ")
        val cmd = Command.entries.find { it.name == parts[0] } ?: throw CommandErrorException(Message.UNKNOWN_CMD)
        return Request(cmd, parts.drop(1))
    }


    private fun doSet(request: Request): Response {
        if (request.args.size != 2) {
            return Response.err(1, Message.ARGS_NUM_WRONG)
        }
        Astel.put(Key.new(request.args[0] as String), Strings.new(request.args[1] as String))
        return Response.ok("Done.")
    }

    private fun doGet(request: Request): Response {
        if (request.args.size != 1) {
            return Response.err(1, Message.ARGS_NUM_WRONG)
        }
        val value = Astel.get(Key.new(request.args[0] as String))
        if (value == null) {
            return Response.ok("")
        }
        if (value is Strings) {
            return Response.ok(value.value)
        }
        return Response.err(1, Message.KEY_NOT_STRINGS)
    }

}
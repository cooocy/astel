package cc.dcyy.astel.command

class Response private constructor(val code: Int, val value: String) {
    companion object {
        fun ok(value: String): Response {
            return Response(code = 0, value = value)
        }

        fun err(code: Int, err: String): Response {
            return Response(code, err)
        }
    }
}

object Message {
    const val UNKNOWN_CMD = "Unknown command."
    const val ARGS_NUM_WRONG = "Wrong number of arguments."
    const val KEY_NOT_STRINGS = "The specified key is not Strings."
}

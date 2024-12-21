package cc.dcyy.astel.core.entrance

import cc.dcyy.astel.AstelException
import cc.dcyy.astel.OK
import cc.dcyy.astel.ASTEL_UNEXPECTED
import cc.dcyy.astel.CommandArgsErrException

/**
 * The input(Request) and the output(Response).
 */

/**
 * The astel request.
 */
class AstelRequest(val command: Command, val args: List<String>) {

    /**
     * Check the args size.
     */
    fun checkArgsSize(size: Int) {
        if (args.size != size) {
            throw CommandArgsErrException()
        }
    }

}

/**
 * The astel response.
 */
class AstelResponse(val code: Int, val content: Any) {
    companion object {
        fun ok(content: Any): AstelResponse {
            return AstelResponse(OK, content)
        }

        fun err(ae: AstelException): AstelResponse {
            return AstelResponse(ae.code, ae.message)
        }

        fun err(e: Exception): AstelResponse {
            return AstelResponse(ASTEL_UNEXPECTED, e.message ?: "[AstelException] Unknown exception message.")
        }
    }
}
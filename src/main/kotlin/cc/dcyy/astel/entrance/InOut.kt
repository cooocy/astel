package cc.dcyy.astel.entrance

import cc.dcyy.astel.AstelException
import cc.dcyy.astel.OK
import cc.dcyy.astel.UNEXPECTED

/**
 * The input(Request) and the output(Response).
 */

/**
 * The astel request.
 */
class AstelRequest(val command: Command, val args: List<Any>)

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
            return AstelResponse(UNEXPECTED, e.message ?: "Unknown exception message.")
        }
    }
}
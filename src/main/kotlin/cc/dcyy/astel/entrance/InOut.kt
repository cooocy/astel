package cc.dcyy.astel.entrance

import cc.dcyy.astel.AstelException
import cc.dcyy.astel.OK
import cc.dcyy.astel.UNEXPECTED

/**
 * The input(Request) and the output(Response).
 */

/**
 * The request.
 */
class Request(val command: Command, val args: List<Any>)

class Response(val code: Int, val content: Any) {
    companion object {
        fun ok(content: Any): Response {
            return Response(OK, content)
        }

        fun err(ae: AstelException): Response {
            return Response(ae.code, ae.message)
        }

        fun err(e: Exception): Response {
            return Response(UNEXPECTED, e.message ?: "Unknown exception message.")
        }
    }
}
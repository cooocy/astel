package cc.dcyy.astel.server.protocol

import cc.dcyy.astel.ServiceError

/**
 * The Message transported between server and client.
 */
abstract class Message()

/**
 * The Request Message in Admin scope. e.g. Login.
 */
data class AdminRequestMessage(val password: String) : Message()

/**
 * The Response Message in Admin scope.
 */
data class AdminResponseMessage(val todo: String) : Message()

/**
 * The Request Message in Astel scope. e.g. set/get
 */
data class AstelRequestMessage(val content: String) : Message()

/**
 * The Response Message in Astel scope.
 */
data class AstelResponseMessage(val code: Int, val content: Any?) : Message()

/**
 * When Service Error, return this message to client.
 * See: Exceptions.
 */
class ServiceErrorResponseMessage private constructor(val code: Int, val content: String) : Message() {
    companion object {
        fun new(error: ServiceError): ServiceErrorResponseMessage {
            return ServiceErrorResponseMessage(error.code, error.message)
        }
    }
}
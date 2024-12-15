package cc.dcyy.astel.server.protocol

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
package cc.dcyy.astel.server.message

/**
 * A Message from Client.
 */
data class Message(val type: MessageType, val content: String)

enum class MessageType { Auth, Command }
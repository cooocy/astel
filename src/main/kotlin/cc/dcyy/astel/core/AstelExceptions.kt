package cc.dcyy.astel.core

const val OK = 0
const val UNEXPECTED = 99

abstract class AstelException(val code: Int, override val message: String) : RuntimeException(message)

class AstelFillException(message: String) : AstelException(1, message)

class SnapshotNotFoundException(message: String) : AstelException(2, message)

class UnknownCommandException() : AstelException(3, "Unknown command.")

class CommandArgsErrException() : AstelException(4, "Command argument error.")

class ValueTypeNotMatchException() : AstelException(5, "Value type not match.")
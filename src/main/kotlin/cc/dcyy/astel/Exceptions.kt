package cc.dcyy.astel

const val OK = 0
const val ASTEL_UNEXPECTED = 49
private const val SERVICE_UNEXPECTED = 79

/**
 * The Astel Exception, e.g. some exceptions in core package.
 * code: 1 ~ 49
 */
abstract class AstelException(val code: Int, override val message: String) : RuntimeException(message)

class UnknownCommandException() : AstelException(3, "[AstelException] Unknown command.")

class CommandArgsErrException() : AstelException(4, "[AstelException] Command argument error.")

class ValueTypeNotMatchException() : AstelException(5, "[AstelException] Value type not match.")

class KeyNotFoundException() : AstelException(6, "[AstelException] Key not found.")

class AstelFillException(message: String) : AstelException(30, message)

class SerializeToSnapshotException : AstelException(31, "[AstelException] Serialize to snapshot error.")

class DeserializeFromSnapshotException : AstelException(32, "[AstelException] Deserialize from snapshot error.")

/**
 * The Service Errors, e.g. Some exceptions about netty, network, message and so on.
 * code: 50 ~ 79
 */
abstract class ServiceError(val code: Int, val message: String)

object IllegalMessageServiceError : ServiceError(50, "[ServiceError] Illegal message.")

object SomeObjectNullServiceError : ServiceError(51, "[ServiceError] Some object null.")

object UnexpectedServiceError : ServiceError(SERVICE_UNEXPECTED, "[ServiceError] Unexpected error.")
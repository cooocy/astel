package cc.dcyy.astel

abstract class AstelException(message: String) : RuntimeException(message)

class AstelFillException(message: String) : AstelException(message)

class SnapshotNotFoundException(message: String) : AstelException(message)

class CommandErrorException(message: String) : AstelException(message)

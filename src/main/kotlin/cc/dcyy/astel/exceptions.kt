package cc.dcyy.astel

abstract class AstelException(message: String) : RuntimeException(message)

class AstelFillException(message: String) : AstelException(message)
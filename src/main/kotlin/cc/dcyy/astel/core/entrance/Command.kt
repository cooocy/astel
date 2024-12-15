package cc.dcyy.astel.core.entrance

enum class Command {
    set, get, del, exists, expire, ttl,       // Command of Strings.
    lpush, rpush, lpop, rpop, llen, lrange    // Command of Listl.
}

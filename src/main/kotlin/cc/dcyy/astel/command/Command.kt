package cc.dcyy.astel.command

enum class Command {
    set, get, del, exists, expire, ttl,       // Command of Strings.
    lpush, rpush, lpop, rpop, llen, lrange    // Command of Listl.
}

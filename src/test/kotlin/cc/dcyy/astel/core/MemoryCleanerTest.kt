package cc.dcyy.astel.core

import kotlin.test.Test

class MemoryCleanerTest {

    @Test
    fun testClean() {
        MemoryCleaner.clean(MemoryConfigs(0))
    }

}
package cc.dcyy.astel


import kotlin.test.Test

class MemoryCleanerTest {

    @Test
    fun testClean() {
        MemoryCleaner.clean(MemoryConfigs(0))
    }

}
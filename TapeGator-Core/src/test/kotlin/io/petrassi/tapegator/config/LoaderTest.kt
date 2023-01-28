package io.petrassi.tapegator.config

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LoaderTest {

    private val sut: Loader = Loader.create(FakeLoader::class.java.simpleName)

    @Test
    fun `Load from valid FakeData`() {
        // Given
        val clazz = FakeDataType::class.java
        val filename = ""

        // When
        val dataClass = sut.load(clazz, filename)

        // Then
        dataClass.data shouldBe FakeLoader.VALID_DATA_CONTENT
    }

    @Test
    fun `Load from valid FakeData using reified types`() {
        // Given
        val filename = ""

        // When
        val dataClass: FakeDataType = sut.load(filename)

        // Then
        dataClass.data shouldBe FakeLoader.VALID_DATA_CONTENT
    }
}

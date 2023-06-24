package io.petrassi.tapegator.factory

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.factory.config.FactoryConfig
import io.petrassi.tapegator.factory.mocking.FAKE_DATA_FIRST_IMPL_VALUE
import io.petrassi.tapegator.factory.mocking.FAKE_DATA_SECOND_IMPL_VALUE
import io.petrassi.tapegator.factory.mocking.FakeData
import io.petrassi.tapegator.factory.mocking.FakeEnumFactory
import org.junit.jupiter.api.Test

class EnumFactoryTest {
    @Test
    fun `Load default implementation from system`() {
        // Given
        val firstElement = FakeEnumFactory.values().first().get()

        // When
        val fakeData = FakeEnumFactory.create()

        // Then
        fakeData shouldBe firstElement
    }

    @Test
    fun `Load first implementation from enum`() {
        // When
        val fakeData = FakeEnumFactory.create(FakeEnumFactory.ONE)

        // Then
        fakeData.value shouldBe FAKE_DATA_FIRST_IMPL_VALUE
    }

    @Test
    fun `Load second implementation from enum`() {
        // When
        val fakeData = FakeEnumFactory.create(FakeEnumFactory.TWO)

        // Then
        fakeData.value shouldBe FAKE_DATA_SECOND_IMPL_VALUE
    }

    @Test
    fun `Load implementation should be case insensitive`() {
        // When
        val fakeData = FakeEnumFactory.create(FakeEnumFactory.ONE.toString().lowercase())

        // Then
        fakeData.value shouldBe FAKE_DATA_FIRST_IMPL_VALUE
    }

    @Test
    fun `Null implementation name should throw error`() {
        // Given
        val nullString: String? = null
        val className = FakeData::class.java.name

        // When
        val error = shouldThrow<IllegalArgumentException> {
            FakeEnumFactory.create(nullString)
        }

        // Then
        error.message shouldBe FactoryConfig.NULL_IMPL_ERROR.format(className)
    }

    @Test
    fun `Missing implementation name should throw error`() {
        // Given
        val nonExistentImplementationName = "I_do_not_exist123"
        val className = FakeData::class.java.name

        // When
        val error = shouldThrow<IllegalArgumentException> {
            FakeEnumFactory.create(nonExistentImplementationName)
        }

        // Then
        error.message shouldBe FactoryConfig.MISSING_IMPL_ERROR.format(
            nonExistentImplementationName,
            className
        )
    }
}
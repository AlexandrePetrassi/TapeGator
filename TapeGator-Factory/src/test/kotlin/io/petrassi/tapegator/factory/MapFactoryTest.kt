package io.petrassi.tapegator.factory

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.factory.config.FactoryConfig
import io.petrassi.tapegator.factory.mocking.*
import org.junit.jupiter.api.Test

class MapFactoryTest {

    @Test
    fun `Load default implementation from system`() {
        // Given
        val firstElement = FakeMapFactory.implementations.values.first().get()

        // When
        val fakeData = FakeMapFactory.create()

        // Then
        fakeData shouldBe firstElement
    }

    @Test
    fun `Load first implementation from enum`() {
        // When
        val fakeData = FakeMapFactory.create(POSSIBLE_FAKE_ONE)

        // Then
        fakeData.value shouldBe FAKE_DATA_FIRST_IMPL_VALUE
    }

    @Test
    fun `Load second implementation from enum`() {
        // When
        val fakeData = FakeMapFactory.create(POSSIBLE_FAKE_TWO)

        // Then
        fakeData.value shouldBe FAKE_DATA_SECOND_IMPL_VALUE
    }

    @Test
    fun `Load implementation should be case insensitive`() {
        // When
        val fakeData = FakeMapFactory.create(POSSIBLE_FAKE_TWO.lowercase())

        // Then
        fakeData.value shouldBe FAKE_DATA_SECOND_IMPL_VALUE
    }

    @Test
    fun `Null implementation name should throw error`() {
        // Given
        val nullString: String? = null
        val className = FakeData::class.java.name

        // When
        val error = shouldThrow<IllegalArgumentException> {
            FakeMapFactory.create(nullString)
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
            FakeMapFactory.create(nonExistentImplementationName)
        }

        // Then
        error.message shouldBe FactoryConfig.MISSING_IMPL_ERROR.format(
            nonExistentImplementationName,
            className
        )
    }

    @Test
    fun `Ambiguous implementation name should throw error`() {
        // When
        val error = shouldThrow<IllegalArgumentException> {
            FakeMapFactory.create(POSSIBLE_FAKE_ONE.lowercase())
        }

        // Then
        error.message shouldBe FactoryConfig.AMBIGUOUS_IMPLEMENTATION_ERROR.format(
            POSSIBLE_FAKE_ONE.lowercase(),
            FakeData::class.java.name,
            arrayOf(POSSIBLE_FAKE_ONE, POSSIBLE_FAKE_ONE1).contentToString()
        )
    }
}
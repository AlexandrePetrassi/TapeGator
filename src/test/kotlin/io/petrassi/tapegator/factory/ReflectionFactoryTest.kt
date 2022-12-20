package io.petrassi.tapegator.factory

import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.factory.mocking.FakeReflectionFactory
import io.petrassi.tapegator.factory.mocking.FAKE_DATA_DEFAULT_VALUE
import io.petrassi.tapegator.factory.mocking.FakeData
import org.junit.jupiter.api.Test

class ReflectionFactoryTest {
    @Test
    fun `Load default implementation from system`() {
        // When
        val fakeData = FakeReflectionFactory.create()

        // Then
        fakeData.value shouldBe FAKE_DATA_DEFAULT_VALUE
    }

    @Test
    fun `Load implementation from enum`() {
        // Given
        val className = FakeData::class.java.simpleName

        // When
        val fakeData = FakeReflectionFactory.create(className)

        // Then
        fakeData.value shouldBe FAKE_DATA_DEFAULT_VALUE
    }
}
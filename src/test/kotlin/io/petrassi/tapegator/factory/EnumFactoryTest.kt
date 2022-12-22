package io.petrassi.tapegator.factory

import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.factory.mocking.FAKE_DATA_FIRST_IMPL_VALUE
import io.petrassi.tapegator.factory.mocking.FAKE_DATA_SECOND_IMPL_VALUE
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
}
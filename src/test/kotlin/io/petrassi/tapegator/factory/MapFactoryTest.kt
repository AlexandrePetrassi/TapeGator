package io.petrassi.tapegator.factory

import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.factory.mocking.*
import org.junit.jupiter.api.Assertions.assertEquals
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
}
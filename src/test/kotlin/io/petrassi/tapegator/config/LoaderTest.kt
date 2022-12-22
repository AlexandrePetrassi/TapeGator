package io.petrassi.tapegator.config

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LoaderTest {

    data class RandomDataClass(val name: String)

    private val yamlFile = "randomDataClass.yaml"
    private val nameValue = "myName"

    @Test
    fun `Load from system`() {
        // Given
        val loader = Loader.create()

        // When
        val dataClass: RandomDataClass = loader.load(yamlFile)

        // Then
        assertEquals(dataClass.name, nameValue)
    }

    @Test
    fun `Load from enum`() {
        // Given
        val loader = Loader.create(LoaderFactory.JACKSON)

        // When
        val dataClass: RandomDataClass = loader.load(yamlFile)

        // Then
        assertEquals(dataClass.name, nameValue)
    }

    @Test
    fun `Load from name`() {
        // Given
        val loader = Loader.create("JaCkSoN")

        // When
        val dataClass: RandomDataClass = loader.load(yamlFile)

        // Then
        assertEquals(dataClass.name, nameValue)
    }
}

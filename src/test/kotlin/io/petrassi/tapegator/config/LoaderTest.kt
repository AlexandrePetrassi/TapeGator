package io.petrassi.tapegator.config

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LoaderTest {

    private val yamlFile = "randomDataClass.yaml"
    private val clazz = RandomDataClass::class.java
    private val nameValue = "myName"

    @Test
    fun `Load from system`() {
        // Given
        val loader = Loader.create()

        // When
        val dataClass = loader.load(clazz, yamlFile)

        // Then
        assertEquals(dataClass.name, nameValue)
    }

    @Test
    fun `Load from enum`() {
        // Given
        val loader = Loader.create(LoaderFactory.JACKSON)

        // When
        val dataClass = loader.load(clazz, yamlFile)

        // Then
        assertEquals(dataClass.name, nameValue)
    }

    @Test
    fun `Load from name`() {
        // Given
        val loader = Loader.create("JaCkSoN")

        // When
        val dataClass = loader.load(clazz, yamlFile)

        // Then
        assertEquals(dataClass.name, nameValue)
    }
}

data class RandomDataClass(val name: String)
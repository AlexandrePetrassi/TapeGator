package io.petrassi.tapegator.config

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.config.JacksonYamlLoader.FIND_ERROR
import io.petrassi.tapegator.config.JacksonYamlLoader.READ_ERROR
import org.junit.jupiter.api.Test

class JacksonYamlLoaderTest {

    private val implementation = JacksonYamlLoader::class.java.simpleName
    private val sut: Loader = Loader.create(implementation)

    data class ValidDataClass(val name: String)
    data class WrongDataClass(val wrongType: String)

    @Test
    fun `Load from file`() {
        // Given
        val filename = yamlFile

        // When
        val dataClass: ValidDataClass = sut.load(filename)

        // Then
        dataClass.name shouldBe nameValue
    }

    @Test
    fun `Load invalid file should throw error`() {
        // Given
        val filename = "I_Do_Not_Exist"
        val classname = ValidDataClass::class.java.name

        // When
        val error = shouldThrow<IllegalStateException> {
            sut.load<ValidDataClass>(filename)
        }

        // Then
        error.message shouldBe FIND_ERROR.format(filename, classname)
    }

    @Test
    fun `Load invalid type should throw error`() {
        // Given
        val filename = yamlFile
        val classname = WrongDataClass::class.java.name

        // When
        val error = shouldThrow<IllegalStateException> {
            sut.load<WrongDataClass>(filename)
        }

        // Then
        error.message shouldBe READ_ERROR.format(filename, classname)
    }

    companion object {
        private const val yamlFile = "randomDataClass.yaml"
        private const val nameValue = "myName"
    }
}

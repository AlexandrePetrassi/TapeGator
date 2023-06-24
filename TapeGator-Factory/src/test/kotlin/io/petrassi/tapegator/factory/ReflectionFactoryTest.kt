package io.petrassi.tapegator.factory

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.factory.config.FactoryConfig
import io.petrassi.tapegator.factory.mocking.*
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
    fun `Load implementation by name`() {
        // Given
        val className = FakeData::class.java.simpleName

        // When
        val fakeData = FakeReflectionFactory.create(className)

        // Then
        fakeData.value shouldBe FAKE_DATA_DEFAULT_VALUE
    }

    @Test
    fun `Constructorless object should return its singleton instance`() {
        // Given
        val className = SingletonImplementation::class.java.simpleName
        val expectedValue = SINGLETON_VALUE

        // When
        val result = FakeReflectionFactory.create(className)

        // Then
        result.value shouldBe expectedValue
    }

    @Test
    fun `Null implementation name should throw error`() {
        // Given
        val nullString: String? = null
        val className = Valuable::class.java.name

        // When
        val error = shouldThrow<IllegalArgumentException> {
            FakeReflectionFactory.create(nullString)
        }

        // Then
        error.message shouldBe FactoryConfig.NULL_IMPL_ERROR.format(className)
    }

    @Test
    fun `DataClass of incompatible type from factory's should throw error`() {
        // Given
        val badClassSimpleName = BadFakeData::class.java.simpleName
        val badClassFullName = BadFakeData::class.java.name
        val factoryType = FakeReflectionFactory.type.name

        // When
        val error = shouldThrow<ClassCastException> {
            FakeReflectionFactory.create(badClassSimpleName)
        }

        // Then
        error.message shouldBe FactoryConfig.CAST_EXCEPTION.format(badClassFullName, factoryType)
    }

    @Test
    fun `NonExistent implementation name should throw error`() {
        // Given
        val badClassSimpleName = "Non_ExistentClass123"
        val factoryPackage = FakeReflectionFactory.rootPackage

        // When
        val error = shouldThrow<ClassNotFoundException> {
            FakeReflectionFactory.create(badClassSimpleName)
        }

        // Then
        error.message shouldBe FactoryConfig.MISSING_CLASS_ERROR.format(badClassSimpleName, factoryPackage)
    }

    @Test
    fun `Faulty implementation should throw error`() {
        // Given
        val badClassSimpleName = FaultyData::class.java.simpleName
        val badClassFullName = FaultyData::class.java.name

        // When
        val error = shouldThrow<IllegalStateException> {
            FakeReflectionFactory.create(badClassSimpleName)
        }

        // Then
        error.message shouldBe FactoryConfig.CONSTRUCTOR_INNER_ERROR.format(badClassFullName)
    }

    @Test
    fun `Abstract implementation should throw error`() {
        // Given
        val badClassSimpleName = AbstractData::class.java.simpleName
        val badClassFullName = AbstractData::class.java.name

        // When
        val error = shouldThrow<IllegalStateException> {
            FakeReflectionFactory.create(badClassSimpleName)
        }

        // Then
        error.message shouldBe FactoryConfig.INSTANTIATION_ERROR.format(badClassFullName)
    }

    @Test
    fun `Implementation without empty constructor should throw error`() {
        // Given
        val badClassSimpleName = NoEmptyConstructorData::class.java.simpleName
        val badClassFullName = NoEmptyConstructorData::class.java.name

        // When
        val error = shouldThrow<IllegalStateException> {
            FakeReflectionFactory.create(badClassSimpleName)
        }

        // Then
        error.message shouldBe FactoryConfig.WRONG_ARGS_ERROR.format(badClassFullName)
    }
}

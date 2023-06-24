package io.petrassi.tapegator.factory.reflection

import io.kotest.matchers.shouldBe
import io.petrassi.tapegator.factory.reflection.TypeChecker.checkType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class TypeCheckerTest {

    private interface InterfaceOne
    private interface InterfaceTwo
    private open class SuperClass
    private class SubClass : SuperClass()
    private class ImplementOne : InterfaceOne
    private class ImplementOneTwo : InterfaceOne, InterfaceTwo
    private class ImplementAll : SuperClass(), InterfaceOne, InterfaceTwo

    @Test
    fun `Same classes should return true`() {
        // Given
        val clazz = SuperClass::class.java

        // When
        val result = clazz.checkType(clazz.name)

        // Then
        result shouldBe true
    }

    @Test
    fun `SubClass checking for SuperClass should return true`() {
        // Given
        val subClass = SubClass::class.java
        val superClass = SuperClass::class.java

        // When
        val result = subClass.checkType(superClass.name)

        // Then
        result shouldBe true
    }

    @Test
    fun `SuperClass checking for SubClass should return false`() {
        // Given
        val subClass = SubClass::class.java
        val superClass = SuperClass::class.java

        // When
        val result = superClass.checkType(subClass.name)

        // Then
        result shouldBe false
    }

    @Test
    fun `Class implementing expected interface should return true`() {
        // Given
        val implementer = ImplementOne::class.java
        val expectedInterface = InterfaceOne::class.java

        // When
        val result = implementer.checkType(expectedInterface.name)

        // Then
        result shouldBe true
    }

    @Test
    fun `Class implementing another interface should return false`() {
        // Given
        val implementer = ImplementOne::class.java
        val anotherInterface = InterfaceTwo::class.java

        // When
        val result = implementer.checkType(anotherInterface.name)

        // Then
        result shouldBe false
    }

    @Test
    fun `Class implementing multiple interfaces should return true for first interface`() {
        // Given
        val implementer = ImplementOneTwo::class.java
        val interfaceOne = InterfaceOne::class.java

        // When
        val first = implementer.checkType(interfaceOne.name)

        // Then
        first shouldBe true

    }

    @Test
    fun `Class implementing multiple interfaces should return true for second interface`() {
        // Given
        val implementer = ImplementOneTwo::class.java
        val interfaceTwo = InterfaceTwo::class.java

        // When
        val second = implementer.checkType(interfaceTwo.name)

        // Then
        second shouldBe true
    }

    @Test
    fun `Class implementing everything should return true for all`() {
        // Given
        val implementer = ImplementAll::class.java
        val superClass = SuperClass::class.java
        val interfaceOne = InterfaceOne::class.java
        val interfaceTwo = InterfaceTwo::class.java

        // When
        val result = implementer.checkType(superClass.name)
        val first  = implementer.checkType(interfaceOne.name)
        val second = implementer.checkType(interfaceTwo.name)

        // Then
        assertAll(
            { result shouldBe true } withMessage { "Should implement $superClass" },
            { first shouldBe true } withMessage { "Should implement $interfaceOne" },
            { second shouldBe true } withMessage { "Should implement $interfaceTwo" }
        )
    }

    private infix fun (() -> Unit).withMessage(message: () -> String): () -> Unit {
        return {
            try {
                this()
            } catch (e: AssertionError) {
                throw AssertionError("${message()}\n    ${e.message}", e)
            }
        }
    }
}
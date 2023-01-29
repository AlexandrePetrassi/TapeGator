package io.petrassi.tapegator.factory.reflection

import io.petrassi.tapegator.factory.config.FactoryConfig
import io.petrassi.tapegator.factory.config.FactoryConfig.CONSTRUCTOR_INNER_ERROR
import io.petrassi.tapegator.factory.config.FactoryConfig.INSTANTIATION_ERROR
import io.petrassi.tapegator.factory.config.FactoryConfig.MISSING_CLASS_ERROR
import io.petrassi.tapegator.factory.config.FactoryConfig.WRONG_ARGS_ERROR
import io.petrassi.tapegator.factory.reflection.TypeChecker.checkType
import java.lang.reflect.InvocationTargetException

/**
 * Internals for handling errors during instantiation of types via reflection.
 */
object ClassFinder {

    /**
     * Returns an instance of a class called [name].
     *
     * The returned object is type-casted to the type <[T]>. The type's name
     * must match the String [type] - this redundancy is necessary due to JVM
     * Type Erasure.
     *
     * The instanced class must be inside the package structure [pack], and the
     * class name must be [name].
     *
     * * Throws a [ClassNotFoundException] if the class is not found.
     * * Throws a [IllegalStateException] if the object creation fails.
     * * Throws a [ClassCastException] if the object is not of [type] <[T]>
     */
    @JvmStatic
    @Suppress("UNCHECKED_CAST", "ThrowsCount")
    fun <T> getByReflection(type: String, pack: String, name: String): T {
        val fullName = "$pack.$name"
        return try {
            tryGetClassOfType(type, fullName).tryGetInstance() as T
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException(MISSING_CLASS_ERROR.format(name, pack), e)
        } catch (e: InvocationTargetException) {
            throw IllegalStateException(CONSTRUCTOR_INNER_ERROR.format(fullName), e)
        } catch (e: InstantiationException) {
            throw IllegalStateException(INSTANTIATION_ERROR.format(fullName), e)
        } catch (e: NoSuchFieldException) {
            throw IllegalStateException(WRONG_ARGS_ERROR.format(fullName), e)
        }
    }

    /**
     * Returns an instance of a class named [name] type-casted to [type].
     *
     * Throws a [ClassCastException] if the instance is not a subtype or
     * implements [type].
     */
    private fun tryGetClassOfType(type: String, name: String): Class<*> {
        val result = Class.forName(name)
        if (result.checkType(type)) return result
        throw ClassCastException(FactoryConfig.CAST_EXCEPTION.format(name, type))
    }

    /**
     * Tries to get a parameterless constructor and instantiate the object.
     *
     *
     * If there is no parameterless constructor, then falls back to try and
     * retrieve the singleton instance stored in a static field named 'INSTANCE'
     * from the class.
     *
     *
     * throws [NoSuchFieldException] if both approaches fail.
     */
    @Suppress("UNCHECKED_CAST", "SwallowedException")
    private fun <T> Class<T>.tryGetInstance(): T = try {
        getConstructor().newInstance() as T
    } catch (e: NoSuchMethodException) {
        getField("INSTANCE").get(null) as T
    }
}
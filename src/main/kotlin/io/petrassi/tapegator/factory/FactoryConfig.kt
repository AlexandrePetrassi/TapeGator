package io.petrassi.tapegator.factory

import java.lang.ClassCastException

object FactoryConfig {

    private const val INSTANTIATION_ERROR: String =
        "Instantiation error for class [%s]"

    private const val CAST_EXCEPTION: String =
        "Error casting [%s] to correct type"

    const val KEY_ROOT = "tapegator.config."

    const val MISSING_IMPL_ERROR =
        "Implementation [%s] not found for type [%s]."

    const val NULL_IMPL_ERROR =
        "Implementation for [%s] must not be null."

    fun <T> getByReflection(pack: String, name: String): T = try {
        Class.forName("$pack.$name").getConstructor().newInstance() as T
    } catch (e: ClassCastException) {
        throw IllegalStateException(CAST_EXCEPTION.format(name), e)
    } catch (e : ReflectiveOperationException) {
        throw IllegalStateException(INSTANTIATION_ERROR.format("$pack.$name"), e)
    }
}
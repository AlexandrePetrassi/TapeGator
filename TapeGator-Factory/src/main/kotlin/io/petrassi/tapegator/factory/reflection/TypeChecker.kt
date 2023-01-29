package io.petrassi.tapegator.factory.reflection

internal object TypeChecker {
    fun Class<*>.checkType(target: String): Boolean =
        name == target || isSubType(target) || implements(target)

    private fun Class<*>.isSubType(target: String) =
        superclass != null && superclass.checkType(target)

    private fun Class<*>.implements(target: String) =
        interfaces.any { it.checkType(target) }
}



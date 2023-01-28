package io.petrassi.tapegator.factory.mocking

import com.sun.source.doctree.AttributeTree.ValueKind

const val FAKE_DATA_DEFAULT_VALUE = "Default"
const val FAKE_DATA_FIRST_IMPL_VALUE = "one"
const val FAKE_DATA_SECOND_IMPL_VALUE = "two"
const val POSSIBLE_FAKE_ONE = "ONE"
const val POSSIBLE_FAKE_TWO = "TWO"
const val POSSIBLE_FAKE_ONE1 = "oNe"
const val SINGLETON_VALUE = "Singleton"


data class FakeData(
    override val value: String = FAKE_DATA_DEFAULT_VALUE
) : Valuable

data class BadFakeData(
    val field: String = "FAKE"
)

data class FaultyData(
    override val value: String = FAKE_DATA_DEFAULT_VALUE
) : Valuable {
    init { error("This is a faulty implementation") }
}

abstract class AbstractData(
    override val value: String = FAKE_DATA_DEFAULT_VALUE
) : Valuable

data class NoEmptyConstructorData(
    override val value: String
) : Valuable

object SingletonImplementation : Valuable {
    override val value: String = SINGLETON_VALUE
}

interface Valuable {
    val value: String
}
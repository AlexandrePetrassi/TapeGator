package io.petrassi.tapegator.config

object FakeLoader : Loader {

    const val VALID_DATA_CONTENT = "Valid Data"

    @Suppress("UNCHECKED_CAST")
    override fun <T> load(clazz: Class<T>, filename: String): T {
        return FakeDataType() as T
    }

}




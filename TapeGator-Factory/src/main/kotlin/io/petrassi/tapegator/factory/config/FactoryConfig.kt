package io.petrassi.tapegator.factory.config

internal object FactoryConfig {

    const val INSTANTIATION_ERROR: String =
        "Instantiation error for class [%s]. The class must not be an abstract" +
                " class, an interface, an array class, a primitive type," +
                " or void."

    const val WRONG_ARGS_ERROR: String =
        "Error calling constructor for class [%s]. Empty constructor not" +
                " found. The class must have an empty constructor or be a" +
                " singleton object with a static field named 'INSTANCE'."

    const val CONSTRUCTOR_INNER_ERROR: String =
        "Looks like a exception was thrown while trying to instantiate the" +
                " class [%s]."

    const val MISSING_CLASS_ERROR: String =
        "The class [%s] was not found in the package [%s]. Please verify if the" +
                " class is defined in the right package."

    const val CAST_EXCEPTION: String =
        "Error casting [%s] to Factory's type: [%s]"

    const val KEY_ROOT = "tapegator.config."

    const val MISSING_IMPL_ERROR =
        "Implementation [%s] not found for type [%s]."

    const val NULL_IMPL_ERROR =
        "Implementation for [%s] must not be null."

    const val AMBIGUOUS_IMPLEMENTATION_ERROR =
        "The implementation name [%s] for type [%s] matches multiple" +
                " possible implementations: [%s].\n    Please, provide the" +
                " implementation name matching exactly the casing denoted in" +
                " the MapFactory."
}
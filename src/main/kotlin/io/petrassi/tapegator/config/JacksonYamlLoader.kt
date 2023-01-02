package io.petrassi.tapegator.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.IOException
import java.net.URL

internal object JacksonYamlLoader : Loader {

    private val classLoader = this::class.java.classLoader

    internal const val FIND_ERROR =
        "Error while trying to find the file [%s]. Please, check if the file" +
                " name is right or if the file really exists in the resources" +
                " folder."

    internal const val READ_ERROR =
        "Error while deserializing [%s]. Please, check if the contents you" +
                " are trying to load really matches the type [%s]."

    private val objectMapper: ObjectMapper by lazy {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.findAndRegisterModules()
        mapper
    }

    private fun toUrl(filename: String): URL =
        classLoader.getResource(filename) ?: error(FIND_ERROR.format(filename))
    override fun <T> load(clazz: Class<T>, filename: String): T = try {
        objectMapper.readValue(toUrl(filename), clazz)
    } catch (e: IOException) {
        val message = READ_ERROR.format(filename, clazz.name)
        throw IllegalStateException(message, e)
    }
}
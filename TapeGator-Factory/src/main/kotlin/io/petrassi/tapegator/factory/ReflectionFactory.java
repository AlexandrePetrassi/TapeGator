package io.petrassi.tapegator.factory;

import io.petrassi.tapegator.factory.config.FactoryConfig;
import io.petrassi.tapegator.factory.reflection.ClassFinder;

public interface ReflectionFactory<T> extends Factory<T> {

    default String getRootPackage() {
        return this.getClass().getPackageName();
    }

    String defaultImplementation();

    @Override
    default T create(String implementationName) {
        if(implementationName == null) {
            throw new IllegalArgumentException(String.format(FactoryConfig.NULL_IMPL_ERROR, getType().getName()));
        }

        if (implementationName.isBlank()) {
            return ClassFinder.getByReflection(getType().getName(), getRootPackage(), defaultImplementation());
        }

        return ClassFinder.getByReflection(getType().getName(), getRootPackage(), implementationName);
    }
}
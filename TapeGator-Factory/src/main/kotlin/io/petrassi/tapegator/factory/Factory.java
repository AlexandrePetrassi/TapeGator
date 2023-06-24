package io.petrassi.tapegator.factory;

import io.petrassi.tapegator.factory.config.FactoryConfig;

public interface Factory<T> {
    Class<T> getType();

    default String getKeyRoot() {
        return FactoryConfig.KEY_ROOT;
    }

    default String getKeyName(){
        return getType().getSimpleName();
    }

    /**
     * @return an instance of T supplied for an Enum entry which name matches the value from a system property
     */
    default T create() {
        String implementationName = System.getProperty(getKeyRoot() + getKeyName(), "");
        return create(implementationName);
    }

    T create(String implementationName);
}

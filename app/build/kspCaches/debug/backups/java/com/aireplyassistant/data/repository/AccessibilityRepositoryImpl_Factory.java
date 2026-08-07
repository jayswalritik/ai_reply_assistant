package com.aireplyassistant.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AccessibilityRepositoryImpl_Factory implements Factory<AccessibilityRepositoryImpl> {
  @Override
  public AccessibilityRepositoryImpl get() {
    return newInstance();
  }

  public static AccessibilityRepositoryImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AccessibilityRepositoryImpl newInstance() {
    return new AccessibilityRepositoryImpl();
  }

  private static final class InstanceHolder {
    static final AccessibilityRepositoryImpl_Factory INSTANCE = new AccessibilityRepositoryImpl_Factory();
  }
}

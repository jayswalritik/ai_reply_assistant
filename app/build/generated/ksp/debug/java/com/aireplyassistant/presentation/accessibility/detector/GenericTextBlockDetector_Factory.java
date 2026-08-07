package com.aireplyassistant.presentation.accessibility.detector;

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
public final class GenericTextBlockDetector_Factory implements Factory<GenericTextBlockDetector> {
  @Override
  public GenericTextBlockDetector get() {
    return newInstance();
  }

  public static GenericTextBlockDetector_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GenericTextBlockDetector newInstance() {
    return new GenericTextBlockDetector();
  }

  private static final class InstanceHolder {
    static final GenericTextBlockDetector_Factory INSTANCE = new GenericTextBlockDetector_Factory();
  }
}

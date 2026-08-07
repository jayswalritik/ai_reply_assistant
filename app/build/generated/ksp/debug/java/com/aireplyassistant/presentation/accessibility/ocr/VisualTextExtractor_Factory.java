package com.aireplyassistant.presentation.accessibility.ocr;

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
public final class VisualTextExtractor_Factory implements Factory<VisualTextExtractor> {
  @Override
  public VisualTextExtractor get() {
    return newInstance();
  }

  public static VisualTextExtractor_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static VisualTextExtractor newInstance() {
    return new VisualTextExtractor();
  }

  private static final class InstanceHolder {
    static final VisualTextExtractor_Factory INSTANCE = new VisualTextExtractor_Factory();
  }
}

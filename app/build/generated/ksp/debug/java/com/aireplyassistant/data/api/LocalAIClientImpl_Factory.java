package com.aireplyassistant.data.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
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
public final class LocalAIClientImpl_Factory implements Factory<LocalAIClientImpl> {
  private final Provider<OllamaApiService> ollamaApiProvider;

  private LocalAIClientImpl_Factory(Provider<OllamaApiService> ollamaApiProvider) {
    this.ollamaApiProvider = ollamaApiProvider;
  }

  @Override
  public LocalAIClientImpl get() {
    return newInstance(ollamaApiProvider.get());
  }

  public static LocalAIClientImpl_Factory create(Provider<OllamaApiService> ollamaApiProvider) {
    return new LocalAIClientImpl_Factory(ollamaApiProvider);
  }

  public static LocalAIClientImpl newInstance(OllamaApiService ollamaApi) {
    return new LocalAIClientImpl(ollamaApi);
  }
}

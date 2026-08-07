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
public final class CloudAIServiceImpl_Factory implements Factory<CloudAIServiceImpl> {
  private final Provider<GeminiApiService> geminiApiProvider;

  private final Provider<GroqApiService> groqApiProvider;

  private CloudAIServiceImpl_Factory(Provider<GeminiApiService> geminiApiProvider,
      Provider<GroqApiService> groqApiProvider) {
    this.geminiApiProvider = geminiApiProvider;
    this.groqApiProvider = groqApiProvider;
  }

  @Override
  public CloudAIServiceImpl get() {
    return newInstance(geminiApiProvider.get(), groqApiProvider.get());
  }

  public static CloudAIServiceImpl_Factory create(Provider<GeminiApiService> geminiApiProvider,
      Provider<GroqApiService> groqApiProvider) {
    return new CloudAIServiceImpl_Factory(geminiApiProvider, groqApiProvider);
  }

  public static CloudAIServiceImpl newInstance(GeminiApiService geminiApi, GroqApiService groqApi) {
    return new CloudAIServiceImpl(geminiApi, groqApi);
  }
}

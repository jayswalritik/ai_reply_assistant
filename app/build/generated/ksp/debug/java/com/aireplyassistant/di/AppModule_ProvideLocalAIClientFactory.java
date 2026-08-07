package com.aireplyassistant.di;

import com.aireplyassistant.data.api.OllamaApiService;
import com.aireplyassistant.domain.router.LocalAIClient;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideLocalAIClientFactory implements Factory<LocalAIClient> {
  private final Provider<OllamaApiService> ollamaApiProvider;

  private AppModule_ProvideLocalAIClientFactory(Provider<OllamaApiService> ollamaApiProvider) {
    this.ollamaApiProvider = ollamaApiProvider;
  }

  @Override
  public LocalAIClient get() {
    return provideLocalAIClient(ollamaApiProvider.get());
  }

  public static AppModule_ProvideLocalAIClientFactory create(
      Provider<OllamaApiService> ollamaApiProvider) {
    return new AppModule_ProvideLocalAIClientFactory(ollamaApiProvider);
  }

  public static LocalAIClient provideLocalAIClient(OllamaApiService ollamaApi) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLocalAIClient(ollamaApi));
  }
}

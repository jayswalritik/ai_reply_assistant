package com.aireplyassistant.domain.router;

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
public final class AIRouter_Factory implements Factory<AIRouter> {
  private final Provider<LocalAIClient> localAIClientProvider;

  private final Provider<CloudAIService> cloudAIServiceProvider;

  private AIRouter_Factory(Provider<LocalAIClient> localAIClientProvider,
      Provider<CloudAIService> cloudAIServiceProvider) {
    this.localAIClientProvider = localAIClientProvider;
    this.cloudAIServiceProvider = cloudAIServiceProvider;
  }

  @Override
  public AIRouter get() {
    return newInstance(localAIClientProvider.get(), cloudAIServiceProvider.get());
  }

  public static AIRouter_Factory create(Provider<LocalAIClient> localAIClientProvider,
      Provider<CloudAIService> cloudAIServiceProvider) {
    return new AIRouter_Factory(localAIClientProvider, cloudAIServiceProvider);
  }

  public static AIRouter newInstance(LocalAIClient localAIClient, CloudAIService cloudAIService) {
    return new AIRouter(localAIClient, cloudAIService);
  }
}

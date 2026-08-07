package com.aireplyassistant.di;

import com.aireplyassistant.domain.router.AIRouter;
import com.aireplyassistant.domain.router.CloudAIService;
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
public final class AppModule_ProvideAIRouterFactory implements Factory<AIRouter> {
  private final Provider<LocalAIClient> localProvider;

  private final Provider<CloudAIService> cloudProvider;

  private AppModule_ProvideAIRouterFactory(Provider<LocalAIClient> localProvider,
      Provider<CloudAIService> cloudProvider) {
    this.localProvider = localProvider;
    this.cloudProvider = cloudProvider;
  }

  @Override
  public AIRouter get() {
    return provideAIRouter(localProvider.get(), cloudProvider.get());
  }

  public static AppModule_ProvideAIRouterFactory create(Provider<LocalAIClient> localProvider,
      Provider<CloudAIService> cloudProvider) {
    return new AppModule_ProvideAIRouterFactory(localProvider, cloudProvider);
  }

  public static AIRouter provideAIRouter(LocalAIClient local, CloudAIService cloud) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAIRouter(local, cloud));
  }
}

package com.aireplyassistant.di;

import com.aireplyassistant.data.api.GeminiApiService;
import com.aireplyassistant.data.api.GroqApiService;
import com.aireplyassistant.domain.router.CloudAIService;
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
public final class AppModule_ProvideCloudAIServiceFactory implements Factory<CloudAIService> {
  private final Provider<GeminiApiService> geminiProvider;

  private final Provider<GroqApiService> groqProvider;

  private AppModule_ProvideCloudAIServiceFactory(Provider<GeminiApiService> geminiProvider,
      Provider<GroqApiService> groqProvider) {
    this.geminiProvider = geminiProvider;
    this.groqProvider = groqProvider;
  }

  @Override
  public CloudAIService get() {
    return provideCloudAIService(geminiProvider.get(), groqProvider.get());
  }

  public static AppModule_ProvideCloudAIServiceFactory create(
      Provider<GeminiApiService> geminiProvider, Provider<GroqApiService> groqProvider) {
    return new AppModule_ProvideCloudAIServiceFactory(geminiProvider, groqProvider);
  }

  public static CloudAIService provideCloudAIService(GeminiApiService gemini, GroqApiService groq) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCloudAIService(gemini, groq));
  }
}

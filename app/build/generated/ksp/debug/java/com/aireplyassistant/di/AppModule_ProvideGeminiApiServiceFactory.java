package com.aireplyassistant.di;

import com.aireplyassistant.data.api.GeminiApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
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
public final class AppModule_ProvideGeminiApiServiceFactory implements Factory<GeminiApiService> {
  private final Provider<Retrofit> rProvider;

  private AppModule_ProvideGeminiApiServiceFactory(Provider<Retrofit> rProvider) {
    this.rProvider = rProvider;
  }

  @Override
  public GeminiApiService get() {
    return provideGeminiApiService(rProvider.get());
  }

  public static AppModule_ProvideGeminiApiServiceFactory create(Provider<Retrofit> rProvider) {
    return new AppModule_ProvideGeminiApiServiceFactory(rProvider);
  }

  public static GeminiApiService provideGeminiApiService(Retrofit r) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGeminiApiService(r));
  }
}

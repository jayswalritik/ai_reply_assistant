package com.aireplyassistant.di;

import com.aireplyassistant.data.api.OllamaApiService;
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
public final class AppModule_ProvideOllamaApiServiceFactory implements Factory<OllamaApiService> {
  private final Provider<Retrofit> rProvider;

  private AppModule_ProvideOllamaApiServiceFactory(Provider<Retrofit> rProvider) {
    this.rProvider = rProvider;
  }

  @Override
  public OllamaApiService get() {
    return provideOllamaApiService(rProvider.get());
  }

  public static AppModule_ProvideOllamaApiServiceFactory create(Provider<Retrofit> rProvider) {
    return new AppModule_ProvideOllamaApiServiceFactory(rProvider);
  }

  public static OllamaApiService provideOllamaApiService(Retrofit r) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOllamaApiService(r));
  }
}

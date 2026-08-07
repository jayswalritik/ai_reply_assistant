package com.aireplyassistant.di;

import com.aireplyassistant.data.api.GroqApiService;
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
public final class AppModule_ProvideGroqApiServiceFactory implements Factory<GroqApiService> {
  private final Provider<Retrofit> rProvider;

  private AppModule_ProvideGroqApiServiceFactory(Provider<Retrofit> rProvider) {
    this.rProvider = rProvider;
  }

  @Override
  public GroqApiService get() {
    return provideGroqApiService(rProvider.get());
  }

  public static AppModule_ProvideGroqApiServiceFactory create(Provider<Retrofit> rProvider) {
    return new AppModule_ProvideGroqApiServiceFactory(rProvider);
  }

  public static GroqApiService provideGroqApiService(Retrofit r) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGroqApiService(r));
  }
}

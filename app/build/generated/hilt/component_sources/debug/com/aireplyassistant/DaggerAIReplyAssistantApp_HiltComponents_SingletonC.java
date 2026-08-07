package com.aireplyassistant;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.aireplyassistant.data.api.GeminiApiService;
import com.aireplyassistant.data.api.GroqApiService;
import com.aireplyassistant.data.api.OllamaApiService;
import com.aireplyassistant.di.AppModule_ProvideAIRouterFactory;
import com.aireplyassistant.di.AppModule_ProvideAccessibilityRepositoryFactory;
import com.aireplyassistant.di.AppModule_ProvideCloudAIServiceFactory;
import com.aireplyassistant.di.AppModule_ProvideGeminiApiServiceFactory;
import com.aireplyassistant.di.AppModule_ProvideGeminiRetrofitFactory;
import com.aireplyassistant.di.AppModule_ProvideGenerateRepliesUseCaseFactory;
import com.aireplyassistant.di.AppModule_ProvideGroqApiServiceFactory;
import com.aireplyassistant.di.AppModule_ProvideGroqRetrofitFactory;
import com.aireplyassistant.di.AppModule_ProvideLocalAIClientFactory;
import com.aireplyassistant.di.AppModule_ProvideOkHttpClientFactory;
import com.aireplyassistant.di.AppModule_ProvideOllamaApiServiceFactory;
import com.aireplyassistant.di.AppModule_ProvideOllamaRetrofitFactory;
import com.aireplyassistant.domain.repository.AccessibilityRepository;
import com.aireplyassistant.domain.router.AIRouter;
import com.aireplyassistant.domain.router.CloudAIService;
import com.aireplyassistant.domain.router.LocalAIClient;
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase;
import com.aireplyassistant.presentation.MainActivity;
import com.aireplyassistant.presentation.accessibility.AIAccessibilityService;
import com.aireplyassistant.presentation.accessibility.AIAccessibilityService_MembersInjector;
import com.aireplyassistant.presentation.accessibility.SelectionOverlayService;
import com.aireplyassistant.presentation.accessibility.SelectionOverlayService_MembersInjector;
import com.aireplyassistant.presentation.accessibility.detector.GenericTextBlockDetector;
import com.aireplyassistant.presentation.accessibility.ocr.VisualTextExtractor;
import com.aireplyassistant.presentation.keyboard.AIKeyboardService;
import com.aireplyassistant.presentation.keyboard.AIKeyboardService_MembersInjector;
import com.aireplyassistant.presentation.keyboard.KeyboardViewModel;
import com.aireplyassistant.presentation.keyboard.KeyboardViewModel_HiltModules;
import com.aireplyassistant.presentation.keyboard.KeyboardViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.aireplyassistant.presentation.keyboard.KeyboardViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerAIReplyAssistantApp_HiltComponents_SingletonC {
  private DaggerAIReplyAssistantApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public AIReplyAssistantApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements AIReplyAssistantApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public AIReplyAssistantApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements AIReplyAssistantApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public AIReplyAssistantApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements AIReplyAssistantApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public AIReplyAssistantApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements AIReplyAssistantApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AIReplyAssistantApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements AIReplyAssistantApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AIReplyAssistantApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements AIReplyAssistantApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public AIReplyAssistantApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements AIReplyAssistantApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public AIReplyAssistantApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends AIReplyAssistantApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends AIReplyAssistantApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    FragmentCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends AIReplyAssistantApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends AIReplyAssistantApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    ActivityCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(Collections.<String, Boolean>singletonMap(KeyboardViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, KeyboardViewModel_HiltModules.KeyModule.provide()));
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends AIReplyAssistantApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    Provider<KeyboardViewModel> keyboardViewModelProvider;

    ViewModelCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        SavedStateHandle savedStateHandleParam, ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.keyboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(Collections.<String, javax.inject.Provider<ViewModel>>singletonMap(KeyboardViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (keyboardViewModelProvider))));
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.aireplyassistant.presentation.keyboard.KeyboardViewModel
          return (T) new KeyboardViewModel(singletonCImpl.provideGenerateRepliesUseCaseProvider.get(), singletonCImpl.provideAccessibilityRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends AIReplyAssistantApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends AIReplyAssistantApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectAIAccessibilityService(AIAccessibilityService arg0) {
      injectAIAccessibilityService2(arg0);
    }

    @Override
    public void injectSelectionOverlayService(SelectionOverlayService arg0) {
      injectSelectionOverlayService2(arg0);
    }

    @Override
    public void injectAIKeyboardService(AIKeyboardService arg0) {
      injectAIKeyboardService2(arg0);
    }

    @CanIgnoreReturnValue
    private AIAccessibilityService injectAIAccessibilityService2(AIAccessibilityService instance) {
      AIAccessibilityService_MembersInjector.injectAccessibilityRepository(instance, singletonCImpl.provideAccessibilityRepositoryProvider.get());
      AIAccessibilityService_MembersInjector.injectVisualTextExtractor(instance, singletonCImpl.visualTextExtractorProvider.get());
      AIAccessibilityService_MembersInjector.injectGenericTextBlockDetector(instance, singletonCImpl.genericTextBlockDetectorProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private SelectionOverlayService injectSelectionOverlayService2(
        SelectionOverlayService instance2) {
      SelectionOverlayService_MembersInjector.injectAccessibilityRepository(instance2, singletonCImpl.provideAccessibilityRepositoryProvider.get());
      return instance2;
    }

    @CanIgnoreReturnValue
    private AIKeyboardService injectAIKeyboardService2(AIKeyboardService instance3) {
      AIKeyboardService_MembersInjector.injectGenerateRepliesUseCase(instance3, singletonCImpl.provideGenerateRepliesUseCaseProvider.get());
      AIKeyboardService_MembersInjector.injectAccessibilityRepository(instance3, singletonCImpl.provideAccessibilityRepositoryProvider.get());
      return instance3;
    }
  }

  private static final class SingletonCImpl extends AIReplyAssistantApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    Provider<OkHttpClient> provideOkHttpClientProvider;

    Provider<Retrofit> provideOllamaRetrofitProvider;

    Provider<OllamaApiService> provideOllamaApiServiceProvider;

    Provider<LocalAIClient> provideLocalAIClientProvider;

    Provider<Retrofit> provideGeminiRetrofitProvider;

    Provider<GeminiApiService> provideGeminiApiServiceProvider;

    Provider<Retrofit> provideGroqRetrofitProvider;

    Provider<GroqApiService> provideGroqApiServiceProvider;

    Provider<CloudAIService> provideCloudAIServiceProvider;

    Provider<AIRouter> provideAIRouterProvider;

    Provider<GenerateRepliesUseCase> provideGenerateRepliesUseCaseProvider;

    Provider<AccessibilityRepository> provideAccessibilityRepositoryProvider;

    Provider<VisualTextExtractor> visualTextExtractorProvider;

    Provider<GenericTextBlockDetector> genericTextBlockDetectorProvider;

    SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 5));
      this.provideOllamaRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 4));
      this.provideOllamaApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<OllamaApiService>(singletonCImpl, 3));
      this.provideLocalAIClientProvider = DoubleCheck.provider(new SwitchingProvider<LocalAIClient>(singletonCImpl, 2));
      this.provideGeminiRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 8));
      this.provideGeminiApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<GeminiApiService>(singletonCImpl, 7));
      this.provideGroqRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 10));
      this.provideGroqApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<GroqApiService>(singletonCImpl, 9));
      this.provideCloudAIServiceProvider = DoubleCheck.provider(new SwitchingProvider<CloudAIService>(singletonCImpl, 6));
      this.provideAIRouterProvider = DoubleCheck.provider(new SwitchingProvider<AIRouter>(singletonCImpl, 1));
      this.provideGenerateRepliesUseCaseProvider = DoubleCheck.provider(new SwitchingProvider<GenerateRepliesUseCase>(singletonCImpl, 0));
      this.provideAccessibilityRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AccessibilityRepository>(singletonCImpl, 11));
      this.visualTextExtractorProvider = DoubleCheck.provider(new SwitchingProvider<VisualTextExtractor>(singletonCImpl, 12));
      this.genericTextBlockDetectorProvider = DoubleCheck.provider(new SwitchingProvider<GenericTextBlockDetector>(singletonCImpl, 13));
    }

    @Override
    public void injectAIReplyAssistantApp(AIReplyAssistantApp aIReplyAssistantApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.aireplyassistant.domain.usecase.GenerateRepliesUseCase
          return (T) AppModule_ProvideGenerateRepliesUseCaseFactory.provideGenerateRepliesUseCase(singletonCImpl.provideAIRouterProvider.get());

          case 1: // com.aireplyassistant.domain.router.AIRouter
          return (T) AppModule_ProvideAIRouterFactory.provideAIRouter(singletonCImpl.provideLocalAIClientProvider.get(), singletonCImpl.provideCloudAIServiceProvider.get());

          case 2: // com.aireplyassistant.domain.router.LocalAIClient
          return (T) AppModule_ProvideLocalAIClientFactory.provideLocalAIClient(singletonCImpl.provideOllamaApiServiceProvider.get());

          case 3: // com.aireplyassistant.data.api.OllamaApiService
          return (T) AppModule_ProvideOllamaApiServiceFactory.provideOllamaApiService(singletonCImpl.provideOllamaRetrofitProvider.get());

          case 4: // @javax.inject.Named("OllamaRetrofit") retrofit2.Retrofit
          return (T) AppModule_ProvideOllamaRetrofitFactory.provideOllamaRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 5: // okhttp3.OkHttpClient
          return (T) AppModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 6: // com.aireplyassistant.domain.router.CloudAIService
          return (T) AppModule_ProvideCloudAIServiceFactory.provideCloudAIService(singletonCImpl.provideGeminiApiServiceProvider.get(), singletonCImpl.provideGroqApiServiceProvider.get());

          case 7: // com.aireplyassistant.data.api.GeminiApiService
          return (T) AppModule_ProvideGeminiApiServiceFactory.provideGeminiApiService(singletonCImpl.provideGeminiRetrofitProvider.get());

          case 8: // @javax.inject.Named("GeminiRetrofit") retrofit2.Retrofit
          return (T) AppModule_ProvideGeminiRetrofitFactory.provideGeminiRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 9: // com.aireplyassistant.data.api.GroqApiService
          return (T) AppModule_ProvideGroqApiServiceFactory.provideGroqApiService(singletonCImpl.provideGroqRetrofitProvider.get());

          case 10: // @javax.inject.Named("GroqRetrofit") retrofit2.Retrofit
          return (T) AppModule_ProvideGroqRetrofitFactory.provideGroqRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 11: // com.aireplyassistant.domain.repository.AccessibilityRepository
          return (T) AppModule_ProvideAccessibilityRepositoryFactory.provideAccessibilityRepository();

          case 12: // com.aireplyassistant.presentation.accessibility.ocr.VisualTextExtractor
          return (T) new VisualTextExtractor();

          case 13: // com.aireplyassistant.presentation.accessibility.detector.GenericTextBlockDetector
          return (T) new GenericTextBlockDetector();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}

package com.example.main_screen;

import com.example.domain.use_cases.GetAllRestaurantsUseCase;
import com.example.domain.use_cases.GetOffersUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
    "KotlinInternalInJava"
})
public final class HomeViewModel_Factory implements Factory<MainScreenViewModel> {
  private final Provider<GetAllRestaurantsUseCase> getRestaurantsProvider;

  private final Provider<GetOffersUseCase> getOffersProvider;

  public HomeViewModel_Factory(Provider<GetAllRestaurantsUseCase> getRestaurantsProvider,
      Provider<GetOffersUseCase> getOffersProvider) {
    this.getRestaurantsProvider = getRestaurantsProvider;
    this.getOffersProvider = getOffersProvider;
  }

  @Override
  public MainScreenViewModel get() {
    return newInstance(getRestaurantsProvider.get(), getOffersProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<GetAllRestaurantsUseCase> getRestaurantsProvider,
      Provider<GetOffersUseCase> getOffersProvider) {
    return new HomeViewModel_Factory(getRestaurantsProvider, getOffersProvider);
  }

  public static MainScreenViewModel newInstance(GetAllRestaurantsUseCase getRestaurants,
                                                GetOffersUseCase getOffers) {
    return new MainScreenViewModel(getRestaurants, getOffers);
  }
}

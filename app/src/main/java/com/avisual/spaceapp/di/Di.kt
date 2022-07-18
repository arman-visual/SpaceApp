package com.avisual.spaceapp.di

import com.avisual.data.repository.GalleryRepository
import com.avisual.data.repository.NeoRepository
import com.avisual.data.repository.RoverRepository
import com.avisual.data.source.*
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.database.Db
import com.avisual.spaceapp.data.database.RoomGalleryDataSource
import com.avisual.spaceapp.data.database.RoomNeoDataSource
import com.avisual.spaceapp.data.server.*
import com.avisual.spaceapp.ui.asteroidsNeo.detailNeo.DetailNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.showNeos.ShowNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel.StoredNeoViewModel
import com.avisual.spaceapp.ui.gallery.detailPhoto.viewModel.DetailPhotoViewModel
import com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel.SavedPhotosViewModel
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel
import com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel.ShowPhotosViewModel
import com.avisual.usecases.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { Db.getDatabase(get()) }
    single(named("baseUrlNasaImages")) { "https://images-api.nasa.gov/" }
    single(named("baseUrlApiNasa")) { "https://api.nasa.gov/" }
    single { NasaGalleryClient(get(named("baseUrlNasaImages"))) }
    single { NasaClient(get(named("baseUrlApiNasa"))) }
}

val dataSource = module {
    factory<GalleryLocalDataSource> { RoomGalleryDataSource(get()) }
    factory<GalleryRemoteDataSource> { ServerGalleryDataSource(get()) }
    factory<NeoLocalDataSource> { RoomNeoDataSource(get()) }
    factory<NeoRemoteDataSource> { ServerNeoDataSource(get()) }
    factory<RoverRemoteDataSource> { ServerRoverDataSource(get()) }
}

val repoModule = module {
    factory { GalleryRepository(get(), get()) }
    factory { NeoRepository(get(), get(), get(named("apiKey"))) }
    factory { RoverRepository(get(), get(named("apiKey"))) }
}

val scopesModule = module {
    viewModel { ShowGalleryViewModel(get()) }
    viewModel { DetailPhotoViewModel(get(), get(), get()) }
    viewModel { SavedPhotosViewModel(get(), get(), get()) }
    viewModel { ShowPhotosViewModel(get()) }
    viewModel { ShowNeoViewModel(get()) }
    viewModel { DetailNeoViewModel(get(), get(), get()) }
    viewModel { StoredNeoViewModel(get(), get()) }
}

val useCasesModule = module {
    factory { DeleteGalleryPhotoUseCase(get()) }
    factory { GetGalleryPhotoByIdUseCase(get()) }
    factory { GetGalleryPhotosByKeywordUseCase(get()) }
    factory { SaveGalleryPhotoUseCase(get()) }
    factory { GetAllStoredPhotosUseCase(get()) }
    factory { SaveNeoInDbUseCase(get()) }
    factory { GetNeoByIdUseCase(get()) }
    factory { GetStoredNeosUseCase(get()) }
    factory { RemoveNeoUseCase(get()) }
    factory { GetAllNeoByDateUseCase(get()) }
    factory { GetRoverPhotosByDateUseCase(get()) }
}

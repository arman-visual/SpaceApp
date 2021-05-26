package com.avisual.spaceapp.di

import com.avisual.data.repository.GalleryRepository
import com.avisual.data.repository.NeoRepository
import com.avisual.data.repository.RoverRepository
import com.avisual.data.source.*
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.database.Db
import com.avisual.spaceapp.data.database.RoomGalleryDataSource
import com.avisual.spaceapp.data.database.RoomNeoDataSource
import com.avisual.spaceapp.data.server.ServerGalleryDataSource
import com.avisual.spaceapp.data.server.ServerNeoDataSource
import com.avisual.spaceapp.data.server.ServerRoverDataSource
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.asteroidsNeo.DetailNeoFragment
import com.avisual.spaceapp.ui.asteroidsNeo.ShowNeoFragment
import com.avisual.spaceapp.ui.asteroidsNeo.StoredNeoFragment
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.DetailNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.StoredNeoViewModel
import com.avisual.spaceapp.ui.roverMars.DetailPhotoRoverFragment
import com.avisual.spaceapp.ui.roverMars.NavRoverMarsActivity
import com.avisual.spaceapp.ui.roverMars.ShowPhotosFragment
import com.avisual.spaceapp.ui.roverMars.viewModel.DetailPhotoRoverViewModel
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.searchGallery.DetailPhotoGalleryFragment
import com.avisual.spaceapp.ui.searchGallery.NavGalleryActivity
import com.avisual.spaceapp.ui.searchGallery.SavedPhotosFragment
import com.avisual.spaceapp.ui.searchGallery.ShowGalleryFragment
import com.avisual.spaceapp.ui.searchGallery.viewModel.DetailPhotoViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.SavedPhotosViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.ShowGalleryViewModel
import com.avisual.usecases.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { Db.getDatabase(get()) }
}

val dataSource = module {
    factory<GalleryLocalDataSource> { RoomGalleryDataSource(get()) }
    factory<GalleryRemoteDataSource> { ServerGalleryDataSource() }
    factory<NeoLocalDataSource> { RoomNeoDataSource(get()) }
    factory<NeoRemoteDataSource> { ServerNeoDataSource() }
    factory<RoverRemoteDataSource> { ServerRoverDataSource() }
}

val repoModule = module {
    factory { GalleryRepository(get(), get()) }
    factory { NeoRepository(get(), get(), get(named("apiKey"))) }
    factory { RoverRepository(get(), get(named("apiKey"))) }
}

val scopesModule = module {

    scope(named<NavGalleryActivity>()) {}
    scope(named<ShowGalleryFragment>()) {
        viewModel { ShowGalleryViewModel(get()) }
    }
    scope(named<DetailPhotoGalleryFragment>()) {
        viewModel { DetailPhotoViewModel(get(), get(), get()) }
    }
    scope(named<SavedPhotosFragment>()) {
        viewModel { SavedPhotosViewModel(get(), get(), get()) }
    }

    scope(named<NavRoverMarsActivity>()) {}
        scope(named<ShowPhotosFragment>()) {
            viewModel { ShowPhotosViewModel(get()) }
        }
        scope(named<DetailPhotoRoverFragment>()) {
            viewModel { DetailPhotoRoverViewModel(get()) }
        }

    scope(named<AsteroidsNeoActivity>()) {}
        scope(named<ShowNeoFragment>()) {
            viewModel { ShowNeoViewModel(get()) }
        }
        scope(named<DetailNeoFragment>()) {
            viewModel { DetailNeoViewModel(get(), get(), get()) }
        }
        scope(named<StoredNeoFragment>()) {
            viewModel { StoredNeoViewModel(get(), get()) }
        }
}

val useCasesModule = module {
    single { DeleteGalleryPhoto(get()) }
    single { GetGalleryPhotoById(get()) }
    single { GetGalleryPhotosByKeyword(get()) }
    single { SaveGalleryPhoto(get()) }
    single { GetAllStoredPhotos(get()) }
    single { SaveNeoInDb(get()) }
    single { GetNeoById(get()) }
    single { GetStoredNeos(get()) }
    single { RemoveNeo(get()) }
    single { GetAllNeoByDate(get()) }
    single { GetRoverPhotosByDate(get()) }
}

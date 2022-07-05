package com.avisual.spaceapp

import com.avisual.data.source.*
import com.avisual.domain.Neo
import com.avisual.domain.PhotoGallery
import com.avisual.domain.PhotoRover
import com.avisual.spaceapp.di.repoModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDI(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, mockedDataSource, repoModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "6869212" }
}

private val mockedDataSource = module {
    single<GalleryLocalDataSource> { FakeGalleryLocalDataSource() }
    single<GalleryRemoteDataSource> { FakeGalleryRemoteDataSource() }
    single<NeoLocalDataSource> { FakeNeoLocalDataSource() }
    single<NeoRemoteDataSource> { FakeNeoRemoteDataSource() }
    single<RoverRemoteDataSource> { FakeRoverRemoteDataSource() }
}
val fakePhoto =
    PhotoGallery(
        nasa_id = "U1",
        jsonAllSized = "XX",
        url = "wwww.fake.com",
        date_created = "22-02-2022",
        description = "big",
        media_type = "Photo",
        photographer = "R. Williams",
        title = "Test Photo"

    )

val fakeNeo = Neo(
    "AST1",
    "Apofis",
    true,
    2.3,
    "www.neo.org",
    300.00,
    550.00,
    "200km/s",
    "222km/s",
    "234234.23423",
    "234234234",
    "1000.000",
    "22-01-2022"
)

val fakeRover = PhotoRover(
    1,
    "www.nasa.org",
    "afternoon in mars",
    "fronta",
    1212,
    "12-02-2021",
    "",
    "",
    "Curiosity",
    "ON",
    1
)

val defaultFakeGalleryPhotos = listOf(
    fakePhoto.copy(nasa_id = "F1"),
    fakePhoto.copy(nasa_id = "F2"),
    fakePhoto.copy(nasa_id = "F3"),
    fakePhoto.copy(nasa_id = "F4"),
)

val defaultFakeNeoPhotos = listOf(
    fakeNeo.copy(id = "N1"),
    fakeNeo.copy(id = "N2"),
    fakeNeo.copy(id = "N3")
)

val defaultFakeRoverPhotos = listOf(
    fakeRover.copy(id = 1, earth_date = "23-02-12"),
    fakeRover.copy(id = 2, earth_date = "23-02-12"),
    fakeRover.copy(id = 3, earth_date = "23-02-12"),
)

var defaultStoredPhotos: MutableList<PhotoGallery> =
    mutableListOf(fakePhoto.copy(nasa_id = "S1"), fakePhoto.copy(nasa_id = "S2"))

class FakeGalleryLocalDataSource : GalleryLocalDataSource {

    var storedPhotos = defaultStoredPhotos

    override fun getStoredPhotosInDb(): Flow<List<PhotoGallery>>? = flowOf(storedPhotos) ?: null

    override suspend fun getFindByNasaId(nasaId: String): PhotoGallery? =
        storedPhotos.first { it.nasa_id == nasaId } ?: null

    override suspend fun saveGalleryPhoto(photoGallery: PhotoGallery) {
        storedPhotos.add(photoGallery)
    }

    override suspend fun deletePhoto(photoGallery: PhotoGallery) {
        storedPhotos.remove(photoGallery)
    }

}

class FakeGalleryRemoteDataSource : GalleryRemoteDataSource {

    var galleryPhotos = defaultFakeGalleryPhotos

    override suspend fun findPhotosGallery(keyword: String): List<PhotoGallery> = galleryPhotos

}

class FakeNeoLocalDataSource : NeoLocalDataSource {

    private var storedNeos: MutableList<Neo> = defaultFakeNeoPhotos.toMutableList()

    override fun getAllStoredNeo(): Flow<List<Neo>>? = flowOf(storedNeos) ?: null

    override suspend fun removeNeo(neo: Neo) {
        storedNeos.remove(neo)
    }

    override suspend fun insertNeo(neo: Neo) {
        storedNeos.add(neo)
    }

    override suspend fun getNeoById(id: String): Neo? {
        return storedNeos.first { it.id == id } ?: null
    }

}

class FakeNeoRemoteDataSource : NeoRemoteDataSource {

    private val neos = defaultFakeNeoPhotos
    override suspend fun getAllNeoByDate(startDate: String, apiKey: String): List<Neo>? {
        return defaultFakeNeoPhotos ?: null
    }

}

class FakeRoverRemoteDataSource : RoverRemoteDataSource {

    private val roverPhotos = defaultFakeRoverPhotos
    override suspend fun getPhotosRoverByDate(date: String, apiKey: String): List<PhotoRover>? {
        return roverPhotos ?: null
    }

}
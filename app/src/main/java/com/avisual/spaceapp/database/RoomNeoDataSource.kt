package com.avisual.spaceapp.database

import com.avisual.data.source.NeoLocalDataSource
import com.avisual.spaceapp.server.toDomainNeo
import com.avisual.spaceapp.server.toFrameworkNeo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.avisual.domain.Neo as DomainNeo

class RoomNeoDataSource(private val db: Db) : NeoLocalDataSource {

    private val neoDao = db.asteroidDao()

    override fun getAllStoredNeo(): Flow<List<DomainNeo>> =
        neoDao.getNeoWithFlow().map { listNeoFramework ->
            listNeoFramework.map { frameworkNeo -> frameworkNeo.toDomainNeo() }
        }

    override suspend fun removeAsteroid(asteroid: DomainNeo) {
        neoDao.delete(asteroid.toFrameworkNeo())
    }

    override suspend fun insertAsteroid(asteroid: DomainNeo) {
        neoDao.insert(asteroid.toFrameworkNeo())
    }

    override suspend fun getAsteroidById(id: String): DomainNeo {
        return neoDao.getById(id)?.toDomainNeo()!!
    }
}
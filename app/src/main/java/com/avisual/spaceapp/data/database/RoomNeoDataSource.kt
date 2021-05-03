package com.avisual.spaceapp.data.database

import com.avisual.data.source.NeoLocalDataSource
import com.avisual.spaceapp.data.toDomainNeo
import com.avisual.spaceapp.data.toFrameworkNeo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.avisual.domain.Neo as DomainNeo

class RoomNeoDataSource(db: Db) : NeoLocalDataSource {

    private val neoDao = db.asteroidDao()

    override fun getAllStoredNeo(): Flow<List<DomainNeo>> =
        neoDao.getNeoWithFlow().map { listNeoFramework ->
            listNeoFramework.map { frameworkNeo -> frameworkNeo.toDomainNeo() }
        }

    override suspend fun removeNeo(neo: DomainNeo) {
        neoDao.delete(neo.toFrameworkNeo())
    }

    override suspend fun insertNeo(neo: DomainNeo) {
        neoDao.insert(neo.toFrameworkNeo())
    }

    override suspend fun getNeoById(id: String): DomainNeo? {
        return neoDao.getById(id)?.toDomainNeo()
    }
}
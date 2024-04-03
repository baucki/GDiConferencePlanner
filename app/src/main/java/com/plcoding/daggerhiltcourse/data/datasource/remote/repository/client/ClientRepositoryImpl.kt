package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.client

import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import com.plcoding.daggerhiltcourse.data.model.Client
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val api: MyApi
): ClientRepository {

    override suspend fun fetchAllClients(): List<Client> {
        return api.fetchAllClients()
    }
}
package com.gdi.conferenceplanner.data.datasource.remote.repository.client

import com.gdi.conferenceplanner.data.datasource.remote.MyApi
import com.gdi.conferenceplanner.data.model.remote.responses.Client
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val api: MyApi
): ClientRepository {

    override suspend fun fetchAllClients(): List<Client> {
        return api.fetchAllClients()
    }
}
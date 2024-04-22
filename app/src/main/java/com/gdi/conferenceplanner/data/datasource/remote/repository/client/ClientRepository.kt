package com.gdi.conferenceplanner.data.datasource.remote.repository.client

import com.gdi.conferenceplanner.data.model.remote.responses.Client

interface ClientRepository {
    suspend fun fetchAllClients(): List<Client>
}
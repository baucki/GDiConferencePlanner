package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.client

import com.plcoding.daggerhiltcourse.data.model.Client

interface ClientRepository {
    suspend fun fetchAllClients(): List<Client>
}
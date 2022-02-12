package com.example.paginationwithcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paginationwithcompose.common.Constants
import com.example.paginationwithcompose.data.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM ${Constants.BREED_REMOTE_KEYS_TABLE} WHERE id =:id")
    suspend fun getRemoteKey(id: Int): RemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKeys: List<RemoteKeyEntity>)

    @Query("DELETE FROM ${Constants.BREED_REMOTE_KEYS_TABLE}")
    suspend fun deleteRemoteKeys()
}
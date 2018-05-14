package com.mirzaadil.assignmentpayconiq.database.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mirzaadil.assignmentpayconiq.database.entity.Model

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepositories(repositories : List<Model.RepositoryModel>)

    @Query("select * from repository")
    fun getAllRepositories(): List<Model.RepositoryModel>

    @Query("DELETE from repository")
    fun deleteAll()

}
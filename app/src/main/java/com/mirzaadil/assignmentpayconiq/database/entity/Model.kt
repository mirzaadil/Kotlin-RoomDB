package com.mirzaadil.assignmentpayconiq.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey


class Model {
    @Entity(tableName = "repository")
    data class RepositoryModel(
            @PrimaryKey(autoGenerate = true)
            var id: Int = 0,
            var name: String? = "",
            var desceiption: String? = "",
            var postId: String? = "",
            var image_url: String? = "",
            @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
            var image: ByteArray? = null

    )
}
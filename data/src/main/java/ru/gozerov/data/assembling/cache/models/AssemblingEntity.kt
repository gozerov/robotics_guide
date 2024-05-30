package ru.gozerov.data.assembling.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.data.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.ASSEMBLING_TABLE_NAME)
data class AssemblingEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val instruction: String,
    @ColumnInfo(name = "amount_ready") val readyAmount: Int,
    @ColumnInfo(name = "link_to_project") val linkToProject: String?,
    @ColumnInfo(name = "link_to_sound") val linkToSound: String?,
    @ColumnInfo(name = "user_id") val userId: Int
)
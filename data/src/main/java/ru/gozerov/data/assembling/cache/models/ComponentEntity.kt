package ru.gozerov.data.assembling.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.data.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.COMPONENT_TABLE_NAME)
data class ComponentEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val amount: Int,
    @ColumnInfo(name = "link_to_photo") val photoUrl: String?,
    @ColumnInfo(name = "link_to_sound") val linkToSound: String?
)
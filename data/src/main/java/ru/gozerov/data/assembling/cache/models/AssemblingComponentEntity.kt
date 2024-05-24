package ru.gozerov.data.assembling.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.data.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.ASSEMBLING_COMPONENT_TABLE_NAME)
data class AssemblingComponentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val assemblingId: Int,
    val componentId: Int
)

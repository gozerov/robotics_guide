package ru.gozerov.data.assembling.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.gozerov.data.assembling.cache.models.ComponentEntity

@Dao
interface ComponentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(components: List<ComponentEntity>)

    @Query("SELECT * FROM components WHERE id=:id")
    suspend fun getComponent(id: Int): ComponentEntity

}
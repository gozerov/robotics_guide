package ru.gozerov.data.assembling.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.gozerov.data.assembling.cache.models.AssemblingEntity

@Dao
interface AssemblingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssembling(assemblingEntity: AssemblingEntity)

    @Query("SELECT * FROM assemblings WHERE id=:id")
    suspend fun getAssembling(id: Int): AssemblingEntity?

}
package ru.gozerov.data.assembling.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.gozerov.data.assembling.cache.models.AssemblingComponentEntity

@Dao
interface AssemblingComponentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(components: List<AssemblingComponentEntity>)

    @Query("SELECT componentId FROM assemblingComponent WHERE assemblingId=:id")
    suspend fun getComponentsId(id: Int): List<Int>

    @Query("DELETE FROM assemblingComponent WHERE assemblingId=:id")
    suspend fun clearByAssemblingId(id: Int)

}
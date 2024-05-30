package ru.gozerov.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gozerov.data.assembling.cache.dao.AssemblingComponentDao
import ru.gozerov.data.assembling.cache.dao.AssemblingDao
import ru.gozerov.data.assembling.cache.dao.ComponentDao
import ru.gozerov.data.assembling.cache.models.AssemblingComponentEntity
import ru.gozerov.data.assembling.cache.models.AssemblingEntity
import ru.gozerov.data.assembling.cache.models.ComponentEntity

@Database(
    entities = [AssemblingEntity::class, ComponentEntity::class, AssemblingComponentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAssemblingDao(): AssemblingDao

    abstract fun getComponentDao(): ComponentDao

    abstract fun getAssemblingComponentDao(): AssemblingComponentDao

    companion object {

        private var database: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return database ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DatabaseConstants.DATABASE_NAME
            ).build()
        }
    }

}
package ru.gozerov.roboticsguide.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.gozerov.data.database.AppDatabase

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    fun providesAssemblingDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).getAssemblingDao()

    @Provides
    fun providesComponentDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).getComponentDao()

    @Provides
    fun providesAssemblingComponentDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).getAssemblingComponentDao()

}
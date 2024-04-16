package ru.gozerov.roboticsguide.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gozerov.data.AssemblingRepositoryImpl
import ru.gozerov.domain.repositories.AssemblingRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppBindModule {

    @Binds
    @Singleton
    fun bindAssemblingRepoImplAssemblingRepo(assemblingRepositoryImpl: AssemblingRepositoryImpl): AssemblingRepository


}
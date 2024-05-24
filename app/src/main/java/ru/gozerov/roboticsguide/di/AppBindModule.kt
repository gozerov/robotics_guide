package ru.gozerov.roboticsguide.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gozerov.data.assembling.AssemblingRepositoryImpl
import ru.gozerov.data.assembling.cache.AssemblingStorage
import ru.gozerov.data.assembling.cache.AssemblingStorageImpl
import ru.gozerov.data.login.LoginRepositoryImpl
import ru.gozerov.data.login.cache.LoginStorage
import ru.gozerov.data.login.cache.LoginStorageImpl
import ru.gozerov.domain.repositories.AssemblingRepository
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppBindModule {

    @Binds
    @Singleton
    fun bindAssemblingRepoImplAssemblingRepo(assemblingRepositoryImpl: AssemblingRepositoryImpl): AssemblingRepository

    @Binds
    @Singleton
    fun bindLoginRepoImplLoginRepo(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    fun bindLoginStorageImplLoginStorage(loginStorageImpl: LoginStorageImpl): LoginStorage

    @Binds
    @Singleton
    fun bindAssemblingStorageImplAssemblingStorage(assemblingStorageImpl: AssemblingStorageImpl): AssemblingStorage

}
package ru.gozerov.roboticsguide.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [RoomModule::class])
class CacheModule
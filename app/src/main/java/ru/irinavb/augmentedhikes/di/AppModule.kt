package ru.irinavb.augmentedhikes.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.irinavb.augmentedhikes.db.HikesDatabase
import ru.irinavb.augmentedhikes.utils.Constants.HIKES_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHikesDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        HikesDatabase::class.java,
        HIKES_DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideHikeDao(db: HikesDatabase) = db.getHikeDao()

    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context) = LocationServices
        .getFusedLocationProviderClient(context)
}
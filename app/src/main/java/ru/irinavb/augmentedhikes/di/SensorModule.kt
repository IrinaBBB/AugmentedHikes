package ru.irinavb.augmentedhikes.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.irinavb.augmentedhikes.utils.sensors.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    @Named("accelerometerSensor")
    @Singleton
    fun provideProximitySensor(app: Application): MeasurableSensor {
        return AccelerometerSensor(app)
    }

    @Provides
    @Named("gyroSensor")
    @Singleton
    fun provideGyroSensor(app: Application): MeasurableSensor {
        return GyroscopeSensor(app)
    }

    @Provides
    @Named("magnetometerSensor")
    @Singleton
    fun provideMagnetometerSensor(app: Application): MeasurableSensor {
        return MagnetometerSensor(app)
    }
}
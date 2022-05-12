package comprmto.rickyandmorty.di

import android.app.Application
import androidx.databinding.adapters.Converters
import androidx.room.Room
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import comprmto.rickyandmorty.data.local.RickAndMortyDatabase
import comprmto.rickyandmorty.data.remote.RickyAndMortyApi
import comprmto.rickyandmorty.data.repository.RickAndMortyImpl
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickyAndMortyApi(): RickyAndMortyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RickyAndMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRickAndMortyRepository(
        api: RickyAndMortyApi,
        db: RickAndMortyDatabase
    ): RickAndMortyRepository {
        return RickAndMortyImpl(api, db.rickMortyDao)
    }

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(app: Application): RickAndMortyDatabase {
        return Room.databaseBuilder(app, RickAndMortyDatabase::class.java, "FavoriteDatabase")
            .build()
    }

}
package com.sodja.weatherApp

import com.sodja.weatherApp.commons.Constants
import com.sodja.weatherApp.data.remote.WeatherApi
import com.sodja.weatherApp.domain.model.Weather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherApiTest {

    private lateinit var weatherApi: WeatherApi


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWeatherFromApiTestWithSuccess() = runTest {

        val response = weatherApi.getWeatherData("London", "1")
        assertThat(response, instanceOf(Weather::class.java))


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWeatherFromApiTestForFourDays() = runTest {

        val response = weatherApi.getWeatherData("London", "4")
        Assert.assertEquals(response.forecast.forecastday.size, 4)


    }

}
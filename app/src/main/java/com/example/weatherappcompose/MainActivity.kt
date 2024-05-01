package com.example.weatherappcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.screens.MainCard
import com.example.weatherappcompose.screens.TabLayout
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

const val API_KEY = "c17f77e1048e49ab9a2171751241804"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel(
                            "",
                            "",
                            "0.0",
                            "",
                            "",
                            "0.0",
                            "0.0",
                            "",
                        )
                    )
                }

                getData("Warsaw", this, daysList, currentDay)

                Image(
                    painter = painterResource(
                        id = R.drawable.clouds_bg_image
                    ),
                    contentDescription = "backgroundImage",
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .alpha(0.65f),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(currentDay)
                    TabLayout(daysList, currentDay)
                }

            }
        }
    }
}

private fun getData(
    city: String, context: Context,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {

    val url = "https://api.weatherapi.com/v1/forecast.json?" +
            "key=$API_KEY" +
            "&q=$city" +
            "&days=14" +
            "&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val list = getWeatherByDays(response)
            currentDay.value = list[0]
            daysList.value = list
        },
        { error ->
            Log.d("MyLog", "VolleyError : $error")
        }
    )
    queue.add(sRequest)
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return listOf()

    val convertedToJSONObject = JSONObject(response)
    val listOfDays = ArrayList<WeatherModel>()

    val cityName = convertedToJSONObject
        .getJSONObject("location")
        .getString("name")

    val days = convertedToJSONObject
        .getJSONObject("forecast")
        .getJSONArray("forecastday")

    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        listOfDays.add(
            WeatherModel(
                city = cityName,
                time = item.getString("date"),
                currentTemp = "",
                condition = item.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("text"),
                icon = item.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("icon"),
                maxTemp = item.getJSONObject("day").getString("maxtemp_c").toFloat().toInt()
                    .toString(),
                minTemp = item.getJSONObject("day").getString("mintemp_c").toFloat().toInt()
                    .toString(),
                hours = item.getJSONArray("hour").toString()

            )
        )
    }

    listOfDays[0] = listOfDays[0].copy(
        time = convertedToJSONObject.getJSONObject("current")
            .getString("last_updated"),
        currentTemp = convertedToJSONObject.getJSONObject("current")
            .getString("temp_c").toFloat().toInt().toString()
    )
    return listOfDays
}




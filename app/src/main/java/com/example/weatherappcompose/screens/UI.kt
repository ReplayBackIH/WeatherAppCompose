package com.example.weatherappcompose.screens

import android.app.LauncherActivity.ListItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.ui.theme.BlueLight


@Composable
fun MainList(list: List<WeatherModel>, currentDay: MutableState<WeatherModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            list
        )
        { _, item ->
            ListItem(item, currentDay)

        }
    }
}

@Composable
fun ListItem(weatherItem: WeatherModel, currentDay: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (weatherItem.hours.isEmpty()) return@clickable
                currentDay.value = weatherItem

            },
        colors = CardDefaults.cardColors(
            containerColor = BlueLight
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 3.dp,
                        bottom = 3.dp
                    )
            ) {
                Text(text = weatherItem.time)
                Text(text = weatherItem.condition, color = Color.White)
            }
            Text(
                text = if (weatherItem.currentTemp.isEmpty()) {
                    "${weatherItem.minTemp}°C / ${weatherItem.maxTemp}°C"
                } else {
                    weatherItem.currentTemp + "°C"
                },
                color = Color.White,
                style = TextStyle(fontSize = 25.sp)
            )
            AsyncImage(
                modifier = Modifier
                    .size(35.dp)
                    .padding(end = 8.dp),
                model = "https:${weatherItem.icon}",
                contentDescription = "image"
            )
        }
    }
}

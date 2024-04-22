package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherappcompose.screens.MainCard
import com.example.weatherappcompose.screens.TabLayout
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {

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
                    MainCard()
                    TabLayout()
                }

            }
        }
    }
}

package com.example.carsapp.ui.screens.car

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.carsapp.R
import com.example.carsapp.ui.screens.cars.CarsViewModel

@Composable
fun CarScreen(
    viewModel: CarsViewModel = hiltViewModel(),
    navController: NavController,
) {

    BackHandler(onBack = {
        navController.navigateUp()
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                goBack = {
                    navController.navigateUp()
                },
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding),
        ) {
            CarContent()
        }
    }
}

@Composable
fun TopBar(
    goBack: () -> Boolean,
) {
    TopAppBar(
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 6.dp),
                visibleState = MutableTransitionState(true),
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(24.dp),
                        onClick = {
                            goBack()
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CarContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = "https://i.ytimg.com/vi/aAdDnRnXDz8/maxresdefault.jpg",
            contentDescription = "",
            contentScale = ContentScale.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

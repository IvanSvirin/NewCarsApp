package com.example.carsapp.ui.screens.cars

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.carsapp.R
import com.example.carsapp.domain.model.Car
import com.example.carsapp.ui.theme.cardBg

@Composable
fun CarsScreen(
    viewModel: CarsViewModel = hiltViewModel(),
    onClick: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                viewModel
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding),
        ) {
            CarsContent(viewModel) {
                onClick()
            }
        }
    }
}

@Composable
fun TopBar(
    viewModel: CarsViewModel,
) {
    var openAddCarDialog by remember { mutableStateOf(false) }
    var openFilterDialog by remember { mutableStateOf(false) }

    TopAppBar(
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(start = 6.dp),
                visibleState = MutableTransitionState(true),
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(24.dp),
                        onClick = {
                            openAddCarDialog = true
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = ""
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(24.dp),
                        onClick = {
                            openFilterDialog = true
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
    if (openAddCarDialog) {
        AddCarDialog(openAddCarDialog, viewModel) {
            openAddCarDialog = false
        }
    }
    if (openFilterDialog) {
        FilterDialog(openFilterDialog, viewModel) {
            openFilterDialog = false
        }
    }
}

@Composable
fun FilterDialog(
    openFilterDialog: Boolean,
    viewModel: CarsViewModel,
    closeAction: () -> Unit
) {
    var openDialog by remember { mutableStateOf(openFilterDialog) }
    val filterStateMap = viewModel.filterState.collectAsState()

    Dialog(
        onDismissRequest = {
            openDialog = false
            closeAction()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(1.0F)
                    .padding(16.dp)
            ) {
                LazyColumn(modifier = Modifier.weight(0.8F)) {
                    filterStateMap.value.forEach { k, v ->
                        item {
                            FilterItem(v, k) {
                                filterStateMap.value[k] = it
                            }
                        }

                    }
                }
                Row(
                    modifier = Modifier
                        .weight(0.2F)
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 32.dp),
                        onClick = {
                            viewModel.updateFilter(filterStateMap.value)
                            openDialog = false
                            closeAction()
                        },
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        enabled = true,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.apply),
                                fontSize = 16.sp,
                                letterSpacing = 0.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun FilterItem(
    checkedStateArg: Boolean,
    text: String,
    onCheckedChange: (Boolean) -> Unit
) {
    val checkedState = remember { mutableStateOf(checkedStateArg) }

    Row(
        modifier = Modifier
            .fillMaxWidth(1.0F)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .weight(0.8F)
                .wrapContentHeight()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                letterSpacing = 0.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        Column(
            modifier = Modifier
                .weight(0.2F)
                .wrapContentHeight()
        ) {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    onCheckedChange(it)
                })
        }
    }
}

@Composable
fun AddCarDialog(
    openDialogArg: Boolean,
    viewModel: CarsViewModel,
    closeAction: () -> Unit
) {
    var openDialog by remember { mutableStateOf(openDialogArg) }
    var makeState = remember { mutableStateOf(TextFieldValue()) }
    var modelState = remember { mutableStateOf(TextFieldValue()) }
    var yearState = remember { mutableStateOf(TextFieldValue()) }

    Dialog(
        onDismissRequest = {
            openDialog = false
            closeAction()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = makeState.value,
                    onValueChange = {
                        makeState.value = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.make),
                            fontSize = 14.sp
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.LightGray,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                    ),
                    singleLine = true,
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = modelState.value,
                    onValueChange = {
                        modelState.value = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.model),
                            fontSize = 14.sp
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.LightGray,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                    ),
                    singleLine = true,
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = yearState.value,
                    onValueChange = {
                        yearState.value = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.year),
                            fontSize = 14.sp
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.LightGray,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                    ),
                    singleLine = true,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Button(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = {
                            openDialog = false
                            closeAction()
                        },
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        enabled = true,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                fontSize = 16.sp,
                                letterSpacing = 0.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 32.dp),
                        onClick = {
                            viewModel.addCar(
                                Car(
                                    0,
                                    if (yearState.value.text.isNotEmpty() && yearState.value.text.isDigitsOnly()) yearState.value.text.toInt() else 0,
                                    makeState.value.text,
                                    modelState.value.text,
                                    ""
                                )
                            )
                            openDialog = false
                            closeAction()
                        },
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        enabled = true,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.save),
                                fontSize = 16.sp,
                                letterSpacing = 0.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarsContent(
    viewModel: CarsViewModel,
    onClick: () -> Unit
) {
    val carsState = viewModel.filteredCarsState.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        carsState.forEach { car ->
            item {
                CarItem(car, viewModel, onClick)
            }
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CarItem(
    car: Car,
    viewModel: CarsViewModel,
    onClick: () -> Unit
) {
    var openEditMakeDialog by remember { mutableStateOf(false) }
    var openEditModelDialog by remember { mutableStateOf(false) }
    var openEditYearDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        backgroundColor = cardBg,
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1.0F)
                .height(160.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4F)
                    .clickable {
                        onClick()
                    }
            ) {
                AsyncImage(
                    model = "https://i.ytimg.com/vi/aAdDnRnXDz8/maxresdefault.jpg",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.6F)
            ) {
                val squareSize = 48.dp
                val sizePx = with(LocalDensity.current) { squareSize.toPx() }
                val anchors = mapOf(0f to 0, sizePx to 1)
                val makeSwipeableState = rememberSwipeableState(0)
                val makeAlphaState = remember { mutableStateOf(1) }
                val modelSwipeableState = rememberSwipeableState(0)
                val modelAlphaState = remember { mutableStateOf(1) }
                val yearSwipeableState = rememberSwipeableState(0)
                val yearAlphaState = remember { mutableStateOf(1) }
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .swipeable(
                            state = makeSwipeableState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                        )
                ) {
                    Text(modifier = Modifier.padding(10.dp), text = car.make)
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .alpha(if (makeSwipeableState.currentValue.toFloat() > 0 && makeAlphaState.value > 0) 1F else 0F)
                            .clickable {
                                makeAlphaState.value = 0
                                openEditMakeDialog = true
                            },
                        text = stringResource(id = R.string.edit),
                        color = Color.Blue
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .swipeable(
                            state = modelSwipeableState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                        )
                ) {
                    Text(modifier = Modifier.padding(10.dp), text = car.model)
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .alpha(if (modelSwipeableState.currentValue.toFloat() > 0 && modelAlphaState.value > 0) 1F else 0F)
                            .clickable {
                                modelAlphaState.value = 0
                                openEditModelDialog = true
                            },
                        text = stringResource(id = R.string.edit),
                        color = Color.Blue
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .swipeable(
                            state = yearSwipeableState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                        )
                ) {
                    Text(modifier = Modifier.padding(10.dp), text = car.year.toString())
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .alpha(if (yearSwipeableState.currentValue.toFloat() > 0 && yearAlphaState.value > 0) 1F else 0F)
                            .clickable {
                                yearAlphaState.value = 0
                                openEditYearDialog = true
                            },
                        text = stringResource(id = R.string.edit),
                        color = Color.Blue
                    )
                }
            }
        }
    }
    if (openEditMakeDialog) {
        EditDialog(openEditMakeDialog, viewModel, car, "make") {
            openEditMakeDialog = false
        }
    }
    if (openEditModelDialog) {
        EditDialog(openEditModelDialog, viewModel, car, "model") {
            openEditModelDialog = false
        }
    }
    if (openEditYearDialog) {
        EditDialog(openEditYearDialog, viewModel, car, "year") {
            openEditYearDialog = false
        }
    }
}

@Composable
fun EditDialog(
    openDialogArg: Boolean,
    viewModel: CarsViewModel,
    car: Car,
    propertyName: String,
    closeAction: () -> Unit
) {
    var openDialog by remember { mutableStateOf(openDialogArg) }
    var propertyState = remember { mutableStateOf(TextFieldValue()) }

    Dialog(
        onDismissRequest = {
            openDialog = false
            closeAction()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = propertyState.value,
                    onValueChange = {
                        propertyState.value = it
                    },
                    placeholder = {
                        Text(
                            text = when(propertyName) {
                                "make" -> stringResource(id = R.string.make)
                                "model" -> stringResource(id = R.string.model)
                                "year" -> stringResource(id = R.string.year)
                                else -> ""
                            },
                            fontSize = 14.sp
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.LightGray,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                    ),
                    singleLine = true,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Button(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = {
                            openDialog = false
                            closeAction()
                        },
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        enabled = true,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                fontSize = 16.sp,
                                letterSpacing = 0.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 32.dp),
                        onClick = {
                            when(propertyName) {
                                "make" -> car.make = propertyState.value.text
                                "model" -> car.model = propertyState.value.text
                                "year" -> car.year = propertyState.value.text.toInt()
                            }
                            viewModel.updateCar(car)
                            openDialog = false
                            closeAction()
                        },
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        enabled = true,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.save),
                                fontSize = 16.sp,
                                letterSpacing = 0.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }
            }
        }
    }
}

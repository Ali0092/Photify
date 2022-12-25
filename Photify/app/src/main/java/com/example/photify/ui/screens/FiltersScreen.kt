package com.example.photify.ui.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.photify.R
import com.skydoves.cloudy.Cloudy

lateinit var navigation :NavController

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("InvalidColorHexValue")
@Composable
fun FiltersScreen(navController: NavController) {

    navigation = navController

    val context= LocalContext.current

    val bitmapState = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val blurRadius = remember {
        mutableStateOf(0)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                //Converting Uri into Bitmap....
                bitmapState.value =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

            }
        }

    LaunchedEffect(Unit) {
        //Launching Intent ....
        galleryLauncher.launch("image/*")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val matrix = remember {
            mutableStateOf<ColorMatrix?>(null)
        }

        //ColorFilter that contains the ColorMatrix Argument....
        var filter : ColorFilter? = null
        filter= matrix.value?.let { ColorFilter.colorMatrix(it) }

        TopBar()
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Image Either Blured or Not.....
            if(bitmapState.value!=null){
                Column {
                    if(blurRadius.value!=0){
                        Cloudy(radius = blurRadius.value, modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = rememberImagePainter(data = bitmapState.value),
                                contentDescription = "Image",
                                contentScale = ContentScale.Fit,
                                colorFilter = filter
                            )
                        }

                    }else{
                        Image(
                            painter = rememberImagePainter(data = bitmapState.value),
                            contentDescription = "Image",
                            contentScale = ContentScale.Fit,
                            colorFilter = filter
                        )
                    }

                }
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .background(Color(0xFF383434))
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 10.dp, end = 10.dp)){

                //None Button....
                Card(
                    modifier = Modifier
                        .height(75.dp)
                        .width(60.dp),
                    shape = RoundedCornerShape(25.dp),
                    backgroundColor = Color.DarkGray,
                    elevation = 5.dp,
                    border = BorderStroke(2.dp, Color(0xFF398FE9))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                val temp = ColorMatrix()
                                temp.reset()
                                blurRadius.value = 0
                                matrix.value = temp
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.none_ic),
                            contentDescription = "None_icon",
                            Modifier.size(30.dp),

                            )
                        Text(text = "None", color = Color.White)
                    }

                }

                Spacer(modifier = Modifier.width(10.dp))

                //First Filter Group....
                firstFiltersGroup(filter = matrix,blurRadius)
                Spacer(modifier = Modifier.width(10.dp))

                //Second Filter Group....
                secondFiltersGroup(filter = matrix,blurRadius)
                Spacer(modifier = Modifier.width(10.dp))

                //Third Filter Group....
                thirdFiltersGroup(filter = matrix,blurRadius)
                Spacer(modifier = Modifier.width(10.dp))

                //Blur button...
                Card(
                    modifier = Modifier
                        .height(75.dp)
                        .width(60.dp),
                    shape = RoundedCornerShape(25.dp),
                    backgroundColor = Color.DarkGray,
                    elevation = 5.dp,
                    border = BorderStroke(2.dp, Color(0xFF398FE9))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                val temp = ColorMatrix()
                                temp.reset()
                                blurRadius.value = 15
                                matrix.value = temp
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tick_ic),
                            contentDescription = "None_icon",
                            Modifier.size(30.dp),

                            )
                        Text(text = "Blur", color = Color.White)
                    }

                }

            }

            Spacer(modifier = Modifier.height(10.dp))
            SelectionRow()
        }

    }
}




@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF383434)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Filters Screen",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.save_ic),
            contentDescription = "Save_Icon...",
            Modifier
                .size(30.dp)
                .padding(end = 10.dp),
        )
    }
}

@Composable
fun SelectionRow() {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier
                .size(35.dp)
                .clickable {
                    navigation.navigateUp()
                },
            backgroundColor = Color.DarkGray
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cross_ic),
                    contentDescription = "Cancel_Icon",
                    Modifier.size(30.dp),
                    Color.White
                )
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Filters",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Card(
            modifier = Modifier
                .size(35.dp)
                .clickable {
                    Toast
                        .makeText(context, "Filter has Set..", Toast.LENGTH_LONG)
                        .show()
                },
            backgroundColor = Color.DarkGray
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick_ic),
                    contentDescription = "Cancel_Icon",
                    Modifier.size(30.dp),
                    Color.White
                )
            }

        }
    }
}

@Composable
fun firstFiltersGroup(filter: MutableState<ColorMatrix?>,blurRadius:MutableState<Int>) {

    val salmon = ColorMatrix()
    salmon.setToScale(0.97f, 0.50f, 0.44f, 1f)
    val light_salmon = ColorMatrix()
    light_salmon.setToScale(0.97f, 0.62f, 0.47f, 1f)
    val warmth_color = ColorMatrix()
    warmth_color.setToScale(0.99f, 0.75f, 0.51f, 1.2f)
    val cool_color = ColorMatrix()
    cool_color.setToScale(0.41f, 0.70f, 0.99f, 1.5f)
    val indian_red = ColorMatrix()
    indian_red.setToScale(0.80f, 0.35f, 0.35f, 1f)

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(80.dp)
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp))
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color.Yellow,
                            Color.Red
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Group1",
                fontSize = 15.sp,
                color = Color.White
            )
        }
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = salmon
                    blurRadius.value = 0
                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(salmon)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = indian_red
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(indian_red)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = light_salmon
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(light_salmon)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = warmth_color
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(warmth_color)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clip(
                    RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp)
                )
                .clickable {
                    filter.value = cool_color
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(cool_color)

        )
    }
}

@Composable
fun secondFiltersGroup(filter: MutableState<ColorMatrix?>,blurRadius:MutableState<Int>) {

    val almond = ColorMatrix()
    almond.setToScale(1f, 0.91f, 0.80f, 1.5f)
    val pale_golden = ColorMatrix()
    pale_golden.setToScale(0.92f, 0.90f, 0.66f, 1f)
    val pale_green = ColorMatrix()
    pale_green.setToScale(0.59f, 0.98f, 0.59f, 1f)
    val blurry_wood = ColorMatrix()
    blurry_wood.setToScale(0.86f, 0.71f, 0.52f, 1f)
    val honey_dew = ColorMatrix()
    honey_dew.setToScale(0.93f, 1f, 0.93f, 1f)

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(80.dp)
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp))
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color.Green,
                            Color.Yellow,
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Group2",
                fontSize = 15.sp,
                color = Color.White
            )
        }
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = almond
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(almond)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = pale_golden
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(pale_golden)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = pale_green
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(pale_green)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = blurry_wood
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(blurry_wood)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clip(
                    RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp)
                )
                .clickable {
                    filter.value = honey_dew
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(honey_dew)

        )
    }
}

@Composable
fun thirdFiltersGroup(filter: MutableState<ColorMatrix?>,blurRadius:MutableState<Int>) {

    val linen = ColorMatrix()
    linen.setToScale(0.67f, 0.73f, 0.99f, 20f)
    val snow = ColorMatrix()
    snow.setToScale(1f, 0.97f, 0.97f, 1f)
    val grayscale = ColorMatrix()
    grayscale.setToSaturation(0f)
    val grayscale2 = ColorMatrix()
    grayscale2.setToSaturation(-10f)
    val grayscale3 = ColorMatrix()
    grayscale3.setToSaturation(-100f)


    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(80.dp)
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp))
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color.White,
                            Color.Gray,
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Group3",
                fontSize = 15.sp,
                color = Color.White
            )
        }
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = linen
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(linen)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = snow
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(snow)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = grayscale
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(grayscale)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clickable {
                    filter.value = grayscale2
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(grayscale2)
        )
        Image(
            painter = painterResource(id = R.drawable.filter_card),
            contentDescription = "Image",
            modifier = Modifier
                .height(80.dp)
                .width(60.dp)
                .clip(
                    RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp)
                )
                .clickable {
                    filter.value = grayscale3
                    blurRadius.value = 0

                },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(grayscale3)

        )
    }
}


package com.example.photify.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.photify.NavigationItem
import com.example.photify.R


lateinit var  navigationController :NavController
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {

    navigationController =navController

    Scaffold(
        topBar = {
            TopAppBar(navController = navController)
        },
        floatingActionButton = {
            Card(
                shape = CircleShape,
                backgroundColor = Color(0xFF0084C4),
                modifier = Modifier
                    .size(55.dp),
                elevation = 10.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable {
                            navController.navigate(NavigationItem.FiltersScreen.route)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_ic),
                        contentDescription = "Add_Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }

            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {


                Image(
                    painter = painterResource(id = R.drawable.card_img),
                    contentDescription = "Main_Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(25.dp))
                        .shadow(50.dp)
                )



                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "AI Features",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FeaturesRowItem(icon = R.drawable.cloud_ic, title = "SkyAI")
                    FeaturesRowItem(icon = R.drawable.background_ic, title = "BG")
                    FeaturesRowItem(icon = R.drawable.clothes_ic, title = "Dress")

                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Edit",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    EditRowItem(icon = R.drawable.filter_ic, title = "Filter")
                    EditRowItem(icon = R.drawable.edit_ic, title = "Edit")
                    EditRowItem(icon = R.drawable.crop_ic, title = "Crop")
                    EditRowItem(icon = R.drawable.text_ic, title = "Text")
                }
            }
        }
    )
}

@Composable
fun TopAppBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.setting_ic),
            contentDescription = "Setting_Icon",
            modifier = Modifier.padding(end = 10.dp)
        )
        Spacer(modifier = Modifier.weight(0.7f))
        Text(
            text = "Photify",
            textAlign = TextAlign.Center,
            color = Color(0xFF0084C4),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FeaturesRowItem(icon: Int, title: String) {
    val context =  LocalContext.current
    Card(
        modifier = Modifier
            .height(80.dp)
            .width(100.dp)
            .padding(start = 10.dp, end = 10.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().clickable {
                Toast.makeText(context,title.toString(),Toast.LENGTH_SHORT).show()
            }
        ) {
            Image(painter = painterResource(id = icon), contentDescription = "Clouds Icon..")
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = title, fontSize = 14.sp)
        }

    }
}

@Composable
fun EditRowItem(icon: Int, title: String) {
    Card(
        modifier = Modifier
            .width(80.dp)
            .height(65.dp)
            .padding(start = 7.dp, end = 7.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().clickable {
                navigationController.navigate(NavigationItem.FiltersScreen.route)
            }
        ) {
            Image(painter = painterResource(id = icon), contentDescription = "Clouds Icon..", modifier = Modifier.size(25.dp))
            Text(text = title, fontSize = 12.sp)
        }

    }
}
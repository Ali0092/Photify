package com.example.photify

sealed class NavigationItem(
   var route:String
){
    object HomeScreen : NavigationItem("main_screen")
    object EditScreen : NavigationItem("edit_screen")
    object FiltersScreen : NavigationItem("filters_screen")
}
package com.example.moodbloom.domain.models

data class MoodsModel(
    val title:String,
    val anim:Int,
    val type:String,
    val moodScore:Int=0,
)

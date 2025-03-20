package com.example.moodbloom.extension

import com.example.moodbloom.R

enum class ChartType(val value:String) {
    DAILY("Daily"), WEEKLY("Weekly"), MONTHLY("Monthly") ,
}

enum class MoodType(val title:String,val type: String,val moodScore:Int,val emoji:Int) {
    VHAPPY("Very Happy","type",6, R.raw.anim2_mood_very_happy),
    HAPPY("Happy","HAPPY",5,R.raw.anim2_mood_happy),
    NATURAL("Natural","NATURAL",4, R.raw.anim2_mood_natural),
    UNCERTAIN("Uncertain","UNCERTAIN",3,R.raw.anim2_mood_uncertain),
    SAD("Sad","SAD",2,R.raw.anim2_mood_sad),
    VSAD("Very Sad","VSAD",1,R.raw.anim2_mood_very_sad),
    ANGRY("Angry","ANGRY",0,R.raw.anim2_mood_angry),
}

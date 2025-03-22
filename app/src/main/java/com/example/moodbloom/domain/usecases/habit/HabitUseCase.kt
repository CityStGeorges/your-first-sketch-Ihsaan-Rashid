package com.example.moodbloom.domain.usecases.habit

import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.repository.HabitTrackerRepo
import com.example.moodbloom.utils.extension.ResponseStates
import javax.inject.Inject

class InsertHabitUseCase @Inject constructor(private val repo: HabitTrackerRepo) {
    suspend fun invoke(params: HabitTrackerModel): ResponseStates<String> {
        return repo.insertHabit(params)
    }
}
class UpdateHabitUseCase @Inject constructor(private val repo: HabitTrackerRepo) {
    suspend fun invoke(params: HabitTrackerModel): ResponseStates<String> {
        return repo.updateHabit(params)
    }
}
class DeleteHabitUseCase @Inject constructor(private val repo: HabitTrackerRepo) {
    suspend fun invoke(habitTitle: String,userId: String): ResponseStates<String> {
        return repo.deleteHabit(habitTitle,userId)
    }
}

class GetUserAllHabitsListUseCase @Inject constructor(private val repo: HabitTrackerRepo) {
    suspend fun invoke(userid: String): ResponseStates<List<HabitTrackerModel>> {
        return repo.getAllHabits(userid)
    }
}


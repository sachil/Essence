package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.sachil.essence.util.Utils

class SharedViewModel : ViewModel() {

    private val internalWeeklyPopularType = MutableLiveData<Utils.PopularType>()
    val weeklyPopularType: LiveData<Utils.PopularType> get() = internalWeeklyPopularType

    fun setWeeklyPopularType(popularType: Utils.PopularType) {
        internalWeeklyPopularType.value = popularType
    }

}
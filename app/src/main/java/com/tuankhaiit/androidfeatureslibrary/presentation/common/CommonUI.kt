package com.tuankhaiit.androidfeatureslibrary.presentation.common

sealed class CommonUI {
    data class Data<D>(val data: D?) : CommonUI()
    object Divider : CommonUI()
}

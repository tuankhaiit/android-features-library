package com.tuankhaiit.androidfeatureslibrary.presentation.common

sealed class CommonItemUI {
    data class Data<D>(val data: D?) : CommonItemUI()
    data class Header(val message: String) : CommonItemUI()
    object Divider : CommonItemUI()
}

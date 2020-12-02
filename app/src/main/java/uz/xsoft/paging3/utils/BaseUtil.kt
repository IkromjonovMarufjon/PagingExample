package uz.xsoft.paging3.utils

import timber.log.Timber

fun timberLog(message : String, tag : String) =
    Timber.tag(tag).d(message)
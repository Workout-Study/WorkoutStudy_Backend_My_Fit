package com.fitmate.myfit.common

class SliceUtil {

    companion object {
        fun getSliceLimit(pageSize: Int): Long {
            return (pageSize + 1).toLong()
        }
    }
}
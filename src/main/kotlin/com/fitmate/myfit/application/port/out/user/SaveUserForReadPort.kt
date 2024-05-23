package com.fitmate.myfit.application.port.out.user

import com.fitmate.myfit.domain.UserForRead

interface SaveUserForReadPort {
    fun saveUserForRead(userForRead: UserForRead): UserForRead
}
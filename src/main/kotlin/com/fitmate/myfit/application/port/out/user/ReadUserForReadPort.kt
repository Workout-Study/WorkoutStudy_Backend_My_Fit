package com.fitmate.myfit.application.port.out.user

import com.fitmate.myfit.domain.UserForRead
import java.util.*

interface ReadUserForReadPort {
    fun findByUserId(userId: Int): Optional<UserForRead>
}
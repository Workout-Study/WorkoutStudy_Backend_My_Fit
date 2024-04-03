package com.fitmate.myfit.application.port.out.fit.group

import com.fitmate.myfit.domain.FitGroupForRead
import java.util.*

interface ReadFitGroupForReadPort {
    fun findByFitGroupId(fitGroupId: Long): Optional<FitGroupForRead>
}
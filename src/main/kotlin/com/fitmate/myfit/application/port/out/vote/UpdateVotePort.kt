package com.fitmate.myfit.application.port.out.vote

import com.fitmate.myfit.domain.Vote

interface UpdateVotePort {
    fun updateVote(vote: Vote)
}
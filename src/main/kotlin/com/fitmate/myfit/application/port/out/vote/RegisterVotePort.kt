package com.fitmate.myfit.application.port.out.vote

import com.fitmate.myfit.domain.Vote

interface RegisterVotePort {
    fun registerVote(vote: Vote): Vote
}
package com.fitmate.myfit.adapter.out.api.uris

class UserServiceURI {

    companion object {
        const val USER_SERVICE_ROOT_URI = "http://auth-service:8084"

        const val USER_ROOT = "$USER_SERVICE_ROOT_URI/user"

        const val USER_INFO = "$USER_ROOT/user-info"
    }
}

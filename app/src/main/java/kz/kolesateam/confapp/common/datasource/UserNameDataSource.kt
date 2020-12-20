package kz.kolesateam.confapp.common.datasource

interface UserNameDataSource {

    fun getUserName(): String?

    fun saveUserName(
            userName: String
    )
}
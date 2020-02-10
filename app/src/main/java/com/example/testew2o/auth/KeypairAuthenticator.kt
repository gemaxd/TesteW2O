package com.example.testew2o.auth

import org.apache.commons.codec.digest.DigestUtils

/**
 * Parametros estaticos
 **/
val paramUser : String = "jesse"
val paramPw : String = "8RokZUqZ"

/***
 * esta classe é responsavel pela geração dos Hashs de comparação
 */
class KeypairAuthenticator {
    public fun hashString( input: String): String {
        var credentials = DigestUtils.md5Hex(input).toUpperCase()
        return credentials
    }

    public fun validateLogin(user: String, pw: String):Boolean{
        var userHash   = hashString(user + pw)
        var paramHash  = hashString(paramUser + paramPw)
        return (userHash == paramHash)
    }

}
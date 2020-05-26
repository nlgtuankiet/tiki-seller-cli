package tiki.seller.cli.interactor

import tiki.seller.cli.TokenInterceptor
import tiki.seller.cli.model.LoginRequest
import tiki.seller.cli.sellerService

suspend fun login(userName: String, password: String) {
  val loginResponse = sellerService
    .login(LoginRequest(email = userName, password = password))
  TokenInterceptor.token = loginResponse.token
}
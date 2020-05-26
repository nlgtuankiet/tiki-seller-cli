package tiki.seller.cli

import tiki.seller.cli.model.LoginRequest
import tiki.seller.cli.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// https://sandbox-sellercenter.tiki.vn/api/token/request
interface SellerCenterService {
  @POST("/api/token/request")
  suspend fun login(@Body request: LoginRequest): LoginResponse
}
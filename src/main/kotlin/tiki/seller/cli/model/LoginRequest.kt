package tiki.seller.cli.model

import com.google.gson.annotations.SerializedName

class LoginRequest(
  @SerializedName("email")
  val email: String,
  @SerializedName("password")
  val password: String
)

class LoginResponse {
  @SerializedName("token")
  lateinit var token: String
}
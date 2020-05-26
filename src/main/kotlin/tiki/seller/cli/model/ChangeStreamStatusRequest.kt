package tiki.seller.cli.model

import com.google.gson.annotations.SerializedName

class ChangeStreamStatusRequest(
  @SerializedName("status")
  val status: String
)
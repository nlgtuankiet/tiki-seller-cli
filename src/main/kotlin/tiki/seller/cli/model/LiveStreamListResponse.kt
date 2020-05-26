package tiki.seller.cli.model

import com.google.gson.annotations.SerializedName

class LiveStreamListResponse {
  @SerializedName("data")
  val data: List<LiveStreamDetail>? = null
}

class LiveStreamDetail {
  @SerializedName("id")
  lateinit var id: String
}
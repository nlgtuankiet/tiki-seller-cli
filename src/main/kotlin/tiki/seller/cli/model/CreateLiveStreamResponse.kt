package tiki.seller.cli.model

import com.google.gson.annotations.SerializedName

class CreateLiveStreamResponse {
  @SerializedName("id")
  lateinit var id: String

  @SerializedName("stream_linear")
  lateinit var streamLinear: StreamLinear

}

class StreamLinear {
  @SerializedName("stream_url")
  lateinit var streamUrl: String

  @SerializedName("stream_key")
  lateinit var streamKey: String

  @SerializedName("output_url")
  lateinit var outputUrl: String
}
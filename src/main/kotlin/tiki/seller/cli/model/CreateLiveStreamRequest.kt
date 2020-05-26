package tiki.seller.cli.model

import com.google.gson.annotations.SerializedName

class CreateLiveStreamRequest(
  @SerializedName("id")
  val id: String?,
  @SerializedName("hashtag")
  val hashTag: String?,
  @SerializedName("title")
  val title: String,
  @SerializedName("thumbnail_url")
  val thumbnailUrl: String,
  @SerializedName("product_ids")
  val productIds: List<Int>?,
  @SerializedName("is_public")
  val isPublic: Boolean,
  @SerializedName("start_time")
  val startTime: String?
)
package tiki.seller.cli.model

import com.google.gson.annotations.SerializedName

class ProductListResponse {
  @SerializedName("data")
  lateinit var data: List<Product>
}
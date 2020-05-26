package tiki.seller.cli.model

import com.google.gson.annotations.SerializedName

class Product {
  @SerializedName("id")
  lateinit var id: String
}
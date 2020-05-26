package tiki.seller.cli

import tiki.seller.cli.model.ChangeStreamStatusRequest
import tiki.seller.cli.model.CreateLiveStreamRequest
import tiki.seller.cli.model.CreateLiveStreamResponse
import tiki.seller.cli.model.LiveStreamDetail
import tiki.seller.cli.model.ProductListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TikiService {
  @POST("/livestreaming/v2/user/videos")
  suspend fun createLiveStream(@Body value: CreateLiveStreamRequest): CreateLiveStreamResponse

  @GET("/livestreaming/v2/user/videos/live")
  suspend fun getCurrentLiveStream(): LiveStreamDetail

  @PUT("/livestreaming/v2/user/videos/{videoId}/status")
  suspend fun changeVideoStatus(@Path("videoId") videoId: String, @Body body: ChangeStreamStatusRequest)

  @GET("/v2/seller/stores/tiki/products?limit=200&page=1")
  suspend fun products(): ProductListResponse
}
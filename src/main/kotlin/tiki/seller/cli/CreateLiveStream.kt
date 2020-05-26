package tiki.seller.cli

import kotlinx.coroutines.runBlocking
import tiki.seller.cli.interactor.login
import tiki.seller.cli.model.ChangeStreamStatusRequest
import tiki.seller.cli.model.CreateLiveStreamRequest
import tiki.seller.cli.model.CreateLiveStreamResponse
import tiki.seller.cli.model.Product
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.util.concurrent.Callable

@Command(name = "stream", description = ["List all version of a group"])
class CreateLiveStream : Callable<Int> {
  @Option(names = ["email"], description = ["email"], required = false)
  private lateinit var email: String

  @Option(names = ["password"], description = ["password"], required = false)
  private lateinit var password: String

  @Option(names = ["path"], description = ["path"], required = false)
  private lateinit var path: String

  @Option(names = ["hashTag"], description = ["hashTag"], required = false)
  private lateinit var hashTag: String

  @Option(names = ["title"], description = ["title"], required = false)
  private lateinit var title: String

  @Option(names = ["thumbnailUrl"], description = ["thumbnailUrl"], required = false)
  private lateinit var thumbnailUrl: String

  @Option(names = ["isPublic"], description = ["isPublic"], required = false)
  private var isPublic: Boolean = true

  @Option(names = ["startTime"], description = ["startTime"], required = false)
  private var startTime: String? = null

  @Option(names = ["productIds"], description = ["productIds"], required = false)
  private lateinit var productIdsString: String

  override fun call(): Int = runBlocking {

    if (this@CreateLiveStream::email.isInitialized.not()) {
      email = defaultUserName
    }
    if (this@CreateLiveStream::password.isInitialized.not()) {
      password = defaultPassword
    }
    if (this@CreateLiveStream::hashTag.isInitialized.not()) {
      hashTag = defaultStreamHashTag
    }
    if (this@CreateLiveStream::path.isInitialized.not()) {
      path = defaultStreamUrl
    }
    if (this@CreateLiveStream::title.isInitialized.not()) {
      title = defaultStreamTitle
    }
    if (this@CreateLiveStream::thumbnailUrl.isInitialized.not()) {
      thumbnailUrl = defaultThumbUrl
    }
    login(userName = email, password = password)
    if (this@CreateLiveStream::productIdsString.isInitialized.not()) {
      productIdsString = tikiService.products().data
        .let {
          val numberOrProduct = (1..30).random()
          val products = mutableSetOf<Product>()
          while (products.size != numberOrProduct) {
            products.add(it.random())
          }
          products.toList()
        }
        .joinToString(",") { it.id }
    }
    val productIds = productIdsString.split(",")
      .mapNotNull { kotlin.runCatching { it.toInt() }.getOrNull() }
    endAllLiveStream()
    val liveStreamResponse: CreateLiveStreamResponse = tikiService.createLiveStream(
      CreateLiveStreamRequest(
        id = null,
        hashTag = hashTag,
        title = title,
        thumbnailUrl = thumbnailUrl,
        productIds = productIds,
        isPublic = isPublic,
        startTime = startTime
      )
    )

    val videoId = liveStreamResponse.id
    tikiService.changeVideoStatus(
      videoId = videoId,
      body = ChangeStreamStatusRequest(
        status = "live"
      )
    )

    val streamUrl = liveStreamResponse.streamLinear.run {
      "$streamUrl/$streamKey"
    }

    val streamOutput = liveStreamResponse.streamLinear.outputUrl
    println("Stream to: $streamUrl")
    println("Stream output: $streamOutput")

    stream(path = path, streamUrl = streamUrl)
    0
  }

  private suspend fun endAllLiveStream() {
    kotlin.runCatching {
      val video = tikiService.getCurrentLiveStream()
      val id = video.id
      runCatching {
        tikiService.changeVideoStatus(
          videoId = id,
          body = ChangeStreamStatusRequest(
            status = "live"
          )
        )
      }
      runCatching {
        tikiService.changeVideoStatus(
          videoId = id,
          body = ChangeStreamStatusRequest(
            status = "finish"
          )
        )
      }
    }
  }
}
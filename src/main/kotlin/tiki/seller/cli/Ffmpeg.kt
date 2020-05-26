package tiki.seller.cli

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.BufferedReader
import java.io.InputStreamReader

suspend fun stream(path: String, streamUrl: String) = coroutineScope {
  val statement = "ffmpeg -re -i $path -c copy -f flv $streamUrl"

  val proc: Process = Runtime.getRuntime().exec(statement)
  val input = async(Dispatchers.IO) {
    val stdInput = BufferedReader(InputStreamReader(proc.inputStream))
    var s: String?
    while (stdInput.readLine().also { s = it } != null) {
      println(s)
    }
  }
  val outPut = async(Dispatchers.IO) {
    val stdError = BufferedReader(InputStreamReader(proc.errorStream))
    var s: String?
    while (stdError.readLine().also { s = it } != null) {
      println(s)
    }
  }
  input.await()
  outPut.await()
}
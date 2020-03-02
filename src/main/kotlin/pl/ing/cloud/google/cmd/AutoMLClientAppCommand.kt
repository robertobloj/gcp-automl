package pl.ing.cloud.google.cmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.google.cloud.automl.v1.*
import com.google.protobuf.ByteString
import pl.ing.cloud.common.PathResolverFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class AutoMLClientAppCommand : CliktCommand() {

    private val path by argument("--path", help="Specify a path with images to predict labels")
        .default("automl/img")
    private val projectId = "visionai-269310"
    private val modelId = "test_158279439174_20200227114922"

    override fun run() {
        val resolvedPath = PathResolverFactory.resolve(path)
        val client = PredictionServiceClient.create()
        val modelName = ModelName.of(projectId, "us-central1", modelId)
        File(resolvedPath.toString()).walk().forEach loop@{
            val file = Paths.get(it.absolutePath)
            if (Files.isDirectory(file)) {
                println("File is a dir: %s, skipping it...".format(it.absolutePath))
                return@loop
            }

            val absolutePath = Paths.get(it.absolutePath)
            val allBytes = Files.readAllBytes(absolutePath)
            val content = ByteString.copyFrom(allBytes)
            val image = Image.newBuilder().setImageBytes(content).build()
            val payload = ExamplePayload.newBuilder().setImage(image).build()
            val request = PredictRequest.newBuilder().setName(modelName.toString())
                .setPayload(payload).putParams("score_threshold", "0.8").build()
            val response = client.predict(request)
            for (payloadResponse in response.payloadList){
                println("Predicted class name: %s".format(payloadResponse.displayName))
                println("Predicted class score: %d".format(payloadResponse.classification.score))
            }
        }
        client.close()
    }
}

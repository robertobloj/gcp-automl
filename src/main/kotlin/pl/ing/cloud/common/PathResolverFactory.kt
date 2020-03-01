package pl.ing.cloud.common

import org.apache.logging.log4j.kotlin.Logging
import java.nio.file.Path
import java.nio.file.Paths

interface PathResolverFactory {
    companion object : Logging {
        fun resolve(name: String) : Path {
            val path = Paths.get(name)
            return when (path.isAbsolute) {
                true -> path
                false -> Paths.get(object {}.javaClass.classLoader.getResource(name)!!.toURI())
            }
        }
    }
}
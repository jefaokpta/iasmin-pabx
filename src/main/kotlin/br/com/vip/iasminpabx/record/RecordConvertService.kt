package br.com.vip.iasminpabx.record

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/22/24
 */
@Service
class RecordConvertService {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${audios.records}")
    private lateinit var RECORD_FOLDER: String

    fun convertToMp3(uniqueId: String) {
        findRecordFiles(uniqueId).forEach {
            executeFfmpegCommand(it)
            deleteWavFile(it)
        }
    }

    private fun findRecordFiles(uniqueId: String): List<String> {
        val folder = File(RECORD_FOLDER)
        return folder.walk()
            .filter { it.isFile && it.extension == "wav" && it.name.contains(uniqueId.replace(".", "-")) }
            .map { it.path.substringAfterLast("/") }
            .toList()
    }

    private fun executeFfmpegCommand(file: String){
        ProcessBuilder("ffmpeg", "-i", "$RECORD_FOLDER/$file",
            "-vn", "-acodec", "libmp3lame",
            "$RECORD_FOLDER/mp3s/${file.replace(".wav", ".mp3")}")
            .start()
            .waitFor()
    }

    private fun deleteWavFile(file: String){
        File("$RECORD_FOLDER/$file").delete()
    }

}
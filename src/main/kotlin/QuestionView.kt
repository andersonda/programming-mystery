import javafx.scene.control.ProgressBar
import javafx.scene.text.Font
import tornadofx.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.swing.JFileChooser
import kotlin.system.exitProcess

class QuestionView : View() {

    private var questionFile: File? = null
    private var answers: List<String>? = null

    private val questionLabel = label() {
        font = Font("Courier", 16.0)
    }

    override val root = vbox{
        paddingAll = 16
    }

    init {
        root += questionLabel
    }

    fun chooseQuestion() {
        val chooser = JFileChooser()
        chooser.currentDirectory = File("./src/main/java")
        if(chooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
            if(questionLabel.text.isBlank()){
                exitProcess(0)
            }
            return
        }

        questionFile = chooser.selectedFile
        questionLabel.text = questionFile!!.readText()
        currentStage?.sizeToScene()
    }

    fun loadAnswers(){
        javac(questionFile!!)
        println(questionFile!!.parentFile.name)
        val isInPackage = questionFile!!.readText().startsWith("package ${questionFile!!.parentFile.name}")
        answers = java(questionFile!!, isInPackage).lines()
        println(answers)
    }

    private fun javac(file: File) = "javac ${file.name}".runCommand(file.parentFile)

    private fun java(file: File, isInPackage: Boolean): String{
        var workingDir = file.parentFile
        var javaArg = file.name.dropLast(file.extension.length + 1)

        if(isInPackage){
            javaArg = "${workingDir.name}.$javaArg"
            workingDir = workingDir.parentFile
        }
        return "java $javaArg".runCommand(workingDir)!!
    }

    private fun String.runCommand(workingDir: File): String? {
        return try {
            val parts = this.split("\\s".toRegex())
            val proc = ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

            proc.waitFor(60, TimeUnit.SECONDS)
            proc.inputStream.bufferedReader().readText()
        } catch(e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
import javafx.embed.swing.SwingNode
import java.awt.Font
import tornadofx.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.swing.JFileChooser
import kotlin.system.exitProcess
import org.fife.ui.rtextarea.RTextScrollPane
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import java.awt.Dimension
import javax.swing.filechooser.FileNameExtensionFilter




class QuestionView : View() {

    private var questionFile: File? = null
    var answers: List<String>? = null
        private set
    var questionsAnswered: MutableList<Boolean>? = null
        private set

    private val questionCode = RSyntaxTextArea(50, 200)

    override val root = vbox{
        paddingAll = 16
    }

    init {
        questionCode.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_JAVA
        questionCode.isCodeFoldingEnabled = true

        val default = RSyntaxTextArea.getDefaultFont()
        questionCode.font = Font(default.fontName, default.style, 18)
        val sp = RTextScrollPane(questionCode)

        val swingNode = SwingNode()
        swingNode.content = sp
        root += swingNode
    }

    fun chooseQuestion() {
        val chooser = JFileChooser()
        chooser.dialogTitle = "Open Java File"
        chooser.currentDirectory = File("./src/main/sample-java")
        chooser.fileFilter = FileNameExtensionFilter("JAVA files", "java")
        if(chooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
            if(questionCode.text.isBlank()){
                exitProcess(0)
            }
            return
        }

        questionFile = chooser.selectedFile
        questionCode.text = questionFile!!.readText()
    }

    fun resize(direction: Int){
        val size = when{
            direction < 0 -> questionCode.font.size - 2
            direction > 0 -> questionCode.font.size + 2
            else -> 18
        }

        questionCode.font = Font(questionCode.font.fontName, questionCode.font.style, size)
    }

    fun loadAnswers(){
        javac(questionFile!!)
        val isInPackage = questionFile!!.readText().startsWith("package ${questionFile!!.parentFile.name}")
        answers = java(questionFile!!, isInPackage).lines().dropLastWhile { it.isBlank() } // remove last blank line
        questionsAnswered = MutableList(answers!!.size){ false }
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
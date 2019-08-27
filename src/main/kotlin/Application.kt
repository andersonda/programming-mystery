import javafx.geometry.Insets
import tornadofx.*
import java.io.File
import javax.swing.JFileChooser
import kotlin.system.exitProcess

class Application: App(ApplicationView::class)

class ApplicationView: View("Programming Mystery") {

    private val file by lazy {
        val chooser = JFileChooser()
        chooser.currentDirectory = File("./src/main/java")
        if(chooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
            exitProcess(0)
        }

        chooser.selectedFile.readText()
    }

    private val question = QuestionView(file)
    private val io = IOPanel()

    override val root = borderpane {
        left = question.root
        right = io.root
    }
}
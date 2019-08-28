import javafx.scene.text.Font
import tornadofx.*
import java.io.File
import javax.swing.JFileChooser
import kotlin.system.exitProcess

class QuestionView : View() {

    private val questionLabel = label() {
        font = Font("Courier", 16.0)
    }

    override val root = vbox{
        paddingAll = 16
    }

    init {
        newQuestion()
        root += questionLabel
    }

    fun newQuestion() {
        val chooser = JFileChooser()
        chooser.currentDirectory = File("./src/main/java")
        if(chooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
            if(questionLabel.text.isBlank()){
                exitProcess(0)
            }
            else return
        }

        questionLabel.text = chooser.selectedFile.readText()
        currentStage?.sizeToScene()
    }
}
import javafx.scene.text.Font
import tornadofx.*

class QuestionView(private val question: String) : View() {
    val controller: QuestionController by inject()

    override val root = vbox{
        paddingAll = 16
        label(question) {
            font = Font("Courier New", 16.0)
        }
    }
}

class QuestionController: Controller(){

}
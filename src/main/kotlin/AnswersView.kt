import javafx.scene.control.Label
import javafx.scene.text.Font
import tornadofx.*

class AnswersView : View("Answers") {
    val questionView: QuestionView by inject()

    override val root = gridpane {
        paddingAll = 64
        hgap = 16.0
    }

    fun loadAllAnswers() = questionView.answers!!.forEachIndexed { index, answer ->
        loadAnswer(index, answer)
    }

    fun loadAnswer(index: Int, answer: String){
        val promptLabel = Label("${index.english()} line of output: " )
        promptLabel.font = Font(20.0)
        val answerLabel = Label(answer)
        answerLabel.font = Font(20.0)

        root.add(promptLabel, 0, index)
        root.add(answerLabel, 1, index)
    }
}

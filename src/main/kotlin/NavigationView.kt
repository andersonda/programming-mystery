import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class NavigationView : View() {
    val io: IOPanel by inject()
    val teams: TeamView by inject()
    val questionView: QuestionView by inject()

    private val prev = button("Prev") {
        useMaxWidth = true
        font = Font(18.0)
        setOnAction {
            io.prevOutputLine()
            teams.loadResponses(questionView.questionResponses!![io.outputLine])
            when(questionView.questionResponses!![io.outputLine].isNotEmpty()){
                true -> io.navigation.disableCheck()
                false -> io.navigation.enableCheck()
            }
        }
    }
    private val check = button("Check") {
        useMaxWidth = true
        font = Font(18.0)
        setOnAction {
            teams.checkAnswers()
        }
    }
    private val next = button("Next") {
        useMaxWidth = true
        font = Font(18.0)
        setOnAction {
            io.nextOutputLine()
            teams.loadResponses(questionView.questionResponses!![io.outputLine])
            when(questionView.questionResponses!![io.outputLine].isNotEmpty()){
                true -> io.navigation.disableCheck()
                false -> io.navigation.enableCheck()
            }
        }
    }

    override val root = hbox {
        alignment = Pos.CENTER
        spacing = 8.0
        paddingAll = 8.0
    }

    init {
        root += prev
        root += check
        root += next
    }

    fun disableCheck(){
        check.isDisable = true
        teams.disableInput()
    }

    fun enableCheck(){
        check.isDisable = false
        teams.enableInput()
    }
}

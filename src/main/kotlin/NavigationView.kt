import javafx.geometry.Pos
import tornadofx.*

class NavigationView : View() {
    val io: IOPanel by inject()
    val teams: TeamView by inject()

    override val root = hbox {
        alignment = Pos.CENTER
        spacing = 8.0
        paddingAll = 8.0

        button("Prev") {
            useMaxWidth = true
            setOnAction {
                teams.resetResponses()
                io.prevOutputLine()
            }
        }
        button("Check") {
            useMaxWidth = true
            setOnAction {
                teams.checkAnswers()
            }
        }
        button("Next") {
            useMaxWidth = true
            setOnAction {
                teams.resetResponses()
                io.nextOutputLine()
            }
        }
    }
}

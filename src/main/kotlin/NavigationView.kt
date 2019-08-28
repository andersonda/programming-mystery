import javafx.geometry.Pos
import tornadofx.*

class NavigationView : View() {
    val applicationView: ApplicationView by inject()


    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 8.0

        hbox {
            alignment = Pos.CENTER
            spacing = 8.0
            paddingAll = 8.0

            button("Prev") {
                useMaxWidth = true
                setOnAction {
                    applicationView.io.prevOutputLine()
                }
            }
            button("Check") {
                useMaxWidth = true
            }
            button("Next") {
                useMaxWidth = true
                setOnAction {
                    applicationView.io.nextOutputLine()
                }
            }
        }

        hbox {
            alignment = Pos.CENTER
            spacing = 8.0
            paddingAll = 8.0

            button("Scores") {

            }

            button("New Question").setOnAction {
                applicationView.loadQuestion()
            }
        }
    }
}

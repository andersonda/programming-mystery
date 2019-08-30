import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*

class IOPanel : View() {

    private var outputLine = 0
    val navigation: NavigationView by inject()
    val question: QuestionView by inject()

    val teams: TeamView by inject()
    val prompt = textflow{
        padding = Insets(0.0, 0.0, 8.0, 0.0)
        text("What is the "){
            font = Font(18.0)
        }
        text(outputLine.english(question.answers!!.size - 1)){
            font = Font(18.0)
            fill = Color.BLUE
        }
        text(" line of output?"){
           font = Font(18.0)
        }
    }

    override val root = vbox {
        paddingAll = 16
        alignment = Pos.TOP_CENTER
    }

    init {
        root += imageview("incognito-512.png"){
            fitWidth = 256.0
            fitHeight = 256.0
        }
        root += label("Programming Mystery"){
            font = Font(20.0)
        }
        root += label("Version 0.1 ALPHA"){
            font = Font(10.0)
        }
        root += separator(Orientation.HORIZONTAL){
            paddingAll = 16
        }
        root += prompt
        root += teams.root
        root += separator(Orientation.HORIZONTAL){
            paddingAll = 16
        }
        root += navigation.root
    }

    fun nextOutputLine(){
        outputLine += 1
        (prompt.children[1] as Text).text = outputLine.english(question.answers!!.size - 1)
    }

    fun prevOutputLine(){
        outputLine -= 1
        (prompt.children[1] as Text).text = outputLine.english(question.answers!!.size - 1)
    }

    companion object {
        private fun Int.english(max: Int) = listOf("first", "second", "third", "fourth",
                "fifth", "sixth", "seventh", "eighth",
                "ninth", "tenth", "eleventh", "twelfth",
                "thirteenth", "fourteenth", "fifteenth", "sixteenth")[this % max]
    }
}

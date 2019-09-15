import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import tornadofx.*
import java.util.*

class IOPanelView : View() {

    var outputLine = 0
        private set
    val navigationView: NavigationView by inject()
    val questionView: QuestionView by inject()
    val teamView: TeamView by inject()
    val scoresView: ScoresView by inject()

    val prompt = textflow{
        textAlignment = TextAlignment.CENTER
        padding = Insets(0.0, 0.0, 8.0, 0.0)
        text("What is the "){
            font = Font(18.0)
        }
        text(outputLine.english()){
            font = Font(18.0)
            fill = Color.BLUE
        }
        text(" line of output?"){
           font = Font(18.0)
        }
    }

    override val root = vbox{
        alignment = Pos.TOP_CENTER
    }

    val image = imageview("incognito-512.png"){
        fitWidth = 256.0
        fitHeight = 256.0
    }

    init {
        val vb = vbox {
            paddingAll = 16
            alignment = Pos.TOP_CENTER
        }
        vb += prompt
        vb += teamView.root
        vb += separator(Orientation.HORIZONTAL){
            paddingAll = 16
        }
        vb += navigationView.root
        vb += separator(Orientation.HORIZONTAL){
            paddingAll = 16
        }
        vb += label("Scores"){
            font = Font(20.0)
            paddingAll = 16.0
        }
        vb += scoresView.root

        val sp = ScrollPane().apply{
            isFitToWidth = true
        }
        sp.content = vb

        root += image
        root += label("Programming Mystery"){
            font = Font(20.0)
        }

        val properties = Properties()
        properties.load(Application::class.java.getResourceAsStream("/version.properties"))
        root += label("Version ${properties.getProperty("version")}"){
            font = Font(10.0)
            padding = insets(0.0, 0.0, 16.0, 0.0)
        }
        root += sp
    }

    fun nextOutputLine(){
        val size = questionView.answers!!.size
        outputLine = (outputLine + 1) % size
        if(outputLine < 0) outputLine += size
        (prompt.children[1] as Text).text = outputLine.english()
    }

    fun prevOutputLine(){
        val size = questionView.answers!!.size
        outputLine = (outputLine - 1) % size
        if(outputLine < 0) outputLine += size
        (prompt.children[1] as Text).text = outputLine.english()
    }

    fun resetOutputLine() {
        outputLine = 0
        (prompt.children[1] as Text).text = outputLine.english()
    }

    fun makeMascotHappy() = image.apply {
        image = Image("incognito-512-happy.png")
    }

    fun makeMascotNeutral() = image.apply {
        image = Image("incognito-512.png")
    }

    fun makeMascotSad() = image.apply {
        image = Image("incognito-512-sad.png")
    }
}
